var app = app || {} ;

;(function($){
	'use strict'; 
	
		app.EditComponentLandingApp = {
			
			urlCollection : {existingCompUrl : '', groupType : ''},
			
			searchValue : [],
			
			loadExitingData : function(){
				var _super = this;
				app.GroupFactory.fetchExistingComponents({groupType: $('#groupType').val(), groupId: $('#groupId').val()})			
				.done(function(result){
						if(!result.length){
							$('#group-existing-component-area').html(app.GroupLandingApp.buildMessage('Error in fetching existing components', 'error'))
								.fadeIn('slow');
								return;
						}
							
					var response = $.parseJSON(result);
						//console.log(response.componentList);
						if(response.componentList){
							
							_super.searchValue = response;
							
							//processing data table generation
							//passing only components
							var componentList = response.componentList;
							//deleting componentList to pass only header
							delete response.componentList;

							var dtTable = new app.DataTable({dtContainer: '#exisiting-table-dataTable', rowTemplateId : '#existing-template'});
							dtTable.setDataHeader(_super.searchValue);
							dtTable.setData(componentList);
							
							$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
							
							dtTable.init(); //init data table
							
							//sending and asigning few group type specific values
							if($('#groupType').val().trim() == 'CPG'){
								//console.log(_super.searchValue.classId);
								$('#classId').val(_super.searchValue.classId);
							}
						}
					}).complete(function(){
						$('.overlay_pageLoading').addClass('hidden');
						_super.searchFormValidate($('#groupType').val().trim());
						_super.handleEvent();
					});
					
					
			},
			
			handleEvent : function(){
				var _super = this;
				$('#add-components').on('click', function(e){
					if($('.item-check:checked').length < 1){
						$('#error-massege').html("Please select atleast one item.");
						$('#errorBox').dialog({
						   autoOpen: true, 
						   modal: true,
						   resizable: false,
						   title : 'Add Component',
						   dialogClass: "dlg-custom",
						   buttons: {
							  OK: function() {$(this).dialog("close");}
						   },
						});
					}else{
						var checkString = [];
						 
						//serializing All checkbox value in one hidden field  
						$('.item-check:checked').each(function(){
							checkString.push($(this).val());
						});
						
						$('#splitCheckboxValue').val(checkString.toString());
						
						try{
							// AFUPYB3: added groupId as Param
							app.GroupFactory.addNewSplitComponent($('#selectedComponentForm').serialize() 
								+ '&groupType=' + $('#groupType').val()+ '&groupId=' + $('#groupId').val())
								.done(function(result){
									
									var resultJSON = $.parseJSON(result);
									
									app.GroupLandingApp.handleGroupCreationResponse(resultJSON, resultJSON.groupType, false);
									
									_super.loadExitingData();
									
									$('#search-components').trigger('submit');
									
									
								}).error(function(jqXHR, textStatus, errorThrown){
									$('#group-creation-messages').html(app.GroupLandingApp.buildMessage(jqXHR.status + ' - ' + textStatus + ' - ' + errorThrown, 'error'))
										.fadeIn('slow');
								}).complete(function(){
									//cleaning up message after 4 sec
									app.GroupLandingApp.cleanupMessage($('#group-creation-messages'), 4000);
								});
						}catch(ex){
							console.log(ex.message);
						}
					}
				});
				
				$('.item-check , #select-all').on('click',function(){
					
					if($(this).is(':checked')){
						$('#remove-existing-group').prop('disabled',false).css('opacity',1);
					}else{
						$('#remove-existing-group').prop('disabled',true).css('opacity',0.5);
					}
				});
					
				var editAjaxReq = null;
				$('#edit-header').on('click', function(e){			
					if(!$(this).hasClass('save')){
						$('.editable').each(function(){
							
							var required = $(this).data('required') || false; 
							switch($(this).data('type')){
								
								case 'date':
								
								var elm = '<input type="text" data-original-value="' + $(this).text() + '" id="' + $(this).data('field-name') 
										+ '" name="' + $(this).data('field-name') + '" value="' + $(this).text() +'"';
								
								if(!!required){
									elm = required ? elm + ' required="required" ' : '';
									$('[rel=' + $(this).data('field-name') + ']').after('<span class="red-text">&nbsp;(*)</span>')
								}
								
								elm +=  '/>';
								
								$(this).html(elm);		
								//console.log($(elm));
								
								if($(this).data('field-name') == 'startDate'){
									$('#' + $(this).data('field-name')).datepicker({
										showOn: "both",
										buttonImage: app.Global.defaults.contextPath + "/img/iconCalendar.gif",
										buttonImageOnly: true,
										buttonText: "",
										minDate: 0 ,
										onClose: function( selectedDate ) {
										 $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
										},
										onSelect : function(){
											$('#startDate').prop('readonly',true);
										}
									});
								}else if($(this).data('field-name') == 'endDate'){
									$('#' + $(this).data('field-name')).datepicker({
										showOn: "both",
										buttonImage: app.Global.defaults.contextPath + "/img/iconCalendar.gif",
										buttonImageOnly: true,
										buttonText: "",
										minDate: 0 ,
										onClose: function( selectedDate ) {
										 $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
										},
										onSelect : function(){
											$('#startDate').prop('readonly',true);
										}
									});
								}
									
								break;
						
								case 'text':
									var elm ='<input type="text" data-original-value="' + $(this).text() + '" id="' + $(this).data('field-name') 
										+ '" name="' + $(this).data('field-name') + '" value="' + $(this).text() + '"';
									
									if(!!required){
										elm = required ? elm + ' required="required" ' : '';
										$('[rel=' + $(this).data('field-name') + ']').after('<span class="red-text">&nbsp;(*)</span>');
									}
									
									elm += ' />';
									
									
									$(this).html(elm);
									break;
								case 'textarea':
									var elm = '<textarea type="text" data-original-value="' + $(this).text() + '" id="' + $(this).data('field-name') 
										+ '" name="' + $(this).data('field-name') + '"';
									
									if(!!required){
										elm = required ? elm + ' required="required" ' : '';
										$('[rel=' + $(this).data('field-name') + ']').after('<span class="red-text">&nbsp;(*)</span>');
									}
									elm += ' >' + $(this).text() + '</textarea>';
									$(this).html(elm);
							}
							
							var curChars = $('#groupName').val() || '';
							
							$('#nameCurChars').text(curChars.length);
						});
						
						//enabling cancel button
						$('.maxChars').show();
						$('#cancel-edit-header').show();
						$(this).val('Save').addClass('save');
					}else{
						if(!$('#fromHeaderEdit')[0].checkValidity()){
							$(this).attr('type', 'submit');
							$(this).click();
						}else{
							$(this).attr('type', 'button');
							$(this).val('Saving..').css({opacity: 0.5});
							editAjaxReq = app.GroupFactory.updateHeader($('#fromHeaderEdit').serialize())
								.done(function(result){
									//console.log(result);
									if(!result.length){
										$('#group-header-message-area').html(app.GroupLandingApp.buildMessage('Error in updating group header details', 'error'))
											.fadeIn('slow');
											return;
									}
									var response = result.length ? $.parseJSON(result): {};
									var message = '';
									
									if(response.status){
										if(response.status == 'SUCCESS'){
											message = app.GroupLandingApp.buildMessage(response.description ? response.description : 'Update Success', 'success');
										}else{
											message= app.GroupLandingApp.buildMessage(response.description ? response.description : 'Update Error', 'success');
										}
									}
									
									$('#group-header-message-area').html(message);
									
									app.GroupLandingApp.cleanupMessage($('#group-header-message-area'));
									
									$('#cancel-edit-header').trigger('save.success');
									
								}).error(function(jqXHR, textStatus, errorThrown){
									$('#group-header-message-area').html(
										app.GroupLandingApp.buildMessage(jqXHR.status + ' - ' +  textStatus + ' -' + errorThrown, 'error')
									);
									
									app.GroupLandingApp.cleanupMessage($('#group-header-message-area'));
								}).complete(function(){
									$('#edit-header').val('Edit').css({opacity: 1});
								});
						}
						
					}
					
					
					
					
				});
				
				$('#cancel-edit-header').on('click', function(e){
					/* console.log(editAjaxReq);
					if(!editAjaxReq){
						editAjaxReq.abort();
					} */
					$('.editable').each(function(){
						var data = $(this).find('input, textarea').data('original-value');
						if(data)
							$(this).text(data);
						
						
						$('span.red-text').remove();
						
					});
					//enabling cancel button
					$('#edit-header').val('Edit').removeClass('save').css({opacity: 1});
					$(this).hide();
					$('.maxChars').hide();
				});
				
				$('#cancel-edit-header').on('save.success', function(e){
					/* console.log(editAjaxReq);
					if(!editAjaxReq){
						editAjaxReq.abort();
					} */
					$('.editable').each(function(){
						var data = $(this).find('input, textarea').val();
						if(data)
							$(this).text(data);
						
						
						$('span.red-text').remove();
						
					});
					//enabling cancel button
					$('#edit-header').val('Edit').removeClass('save').css({opacity: 1});
					$(this).hide();
					$('.maxChars').hide();
				});
				
				//group description char limit and display
				$('#fromHeaderEdit').on('keyup', '#groupDesc', function(){
					var curChars = $('#groupDesc').val() || '';
					if(curChars.length <= app.Global.defaults.maxGroupDescChars){
						$('#descCurChars').text(curChars.length);
						return true;
					}else{
						$(this).val(curChars.substr(0, app.Global.defaults.maxGroupDescChars));
						return false;
					}
						
				});
				
				//group name char limit and display
				$('#fromHeaderEdit').on('keyup', '#groupName', function(){
					var curChars = $('#groupName').val() || '';
					
					if(curChars.length <= app.Global.defaults.maxGroupNameChars){
						$('#nameCurChars').text(curChars.length);
						return true;
					}else{
						$(this).val(curChars.substr(0, app.Global.defaults.maxGroupNameChars));
						return false;
					}
						
				});

				$(document).on('ready', function(e){
					$('#nameMaxChars').text(app.Global.defaults.maxGroupNameChars);
					$('#descMaxChars').text(app.Global.defaults.maxGroupDescChars);
				});
			},
			
			searchFormValidate : function(groupType){
				var _super = this;			
				switch(groupType){
				case  'SSG':
				case  'SCG':
					$('#frmComponentSearch input[type="text"], #btnDlgClass , #btnDlgDept').prop('disabled',true);
					var styleOrinValue =  this.searchValue.styleOrinNoSearch ?  this.searchValue.styleOrinNoSearch : '';
					var vendorNoValue =  this.searchValue.vendorStyleNoSearch ? this.searchValue.vendorStyleNoSearch : '' ;
													
					if(!styleOrinValue.length)
						$('#styleOrinNo , #styleOrinNoShowOnly').prop('disabled',false);
						
					else
						$('#styleOrinNo , #styleOrinNoShowOnly').val(styleOrinValue);
						
					
					if(!vendorNoValue.length)
						$('#vendorStyleNo').prop('disabled',false);
					$('#styleOrinNoShowOnly').on('blur',function(){
						_super.putValue();
					});
					break;
				case 'CPG' :
					$('#groupId-search , #groupName-search').prop('disabled',true);
					_super.putValue();
					break;
					
					
				default : 
				
				break;
				
				}
				
				$('#styleOrinNoShowOnly').on('keyup',function(e){
					_super.putValue();				
				});
				
				$('#styleOrinNoShowOnly').on('blur',function(){
					_super.putValue();
				});
				


				
			},
			putValue : function(){
				 $('#styleOrinNo').val($('#styleOrinNoShowOnly').val());
			},
			
			
			
			init: function(){
				this.loadExitingData();
				//this.handleEvent();
				
				
				app.GroupLandingApp.init();
				app.SplitGroupLanding.handleEvents();
			}
			
		}
	
	
	
})(jQuery);