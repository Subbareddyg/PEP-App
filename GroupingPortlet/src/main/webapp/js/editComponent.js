var app = app || {} ;

;(function($){
	'use strict'; 
	
		app.EditComponentLandingApp = {
			
			//urlCollection : {existingCompUrl : '', groupType : ''},
			
			searchValue : [],
			
			htmlentitiesEncode: function(str){
				return str.replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/"/g, "&quot;").replace(/'/g, '&#039');
			},
			
			htmlentitiesDecode: function(str){
				return str.replace(/&amp;/g, "&;").replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, '"').replace(/&#039/g, "'");
			},
			
			loadExitingData : function(attachHandlers){
				var _super = this;
				return app.GroupFactory.fetchExistingComponents({groupType: $('#groupType').val(), groupId: $('#groupId').val()})			
				.done(function(result){
						if(!result.length){
							$('#group-existing-component-area').html(app.GroupLandingApp.buildMessage('Error in fetching existing components', 'error'))
								.fadeIn('slow');
								$('.hide_after_error').hide();
								return;
						}
							
					var response = $.parseJSON(result);
						//console.log(response.componentList);
						if(response.componentList){
							
							_super.searchValue = response;
							
							var dtTableConfig = {
								dtContainer: '#exisiting-table-dataTable', 
								rowTemplateId : '#existing-template', 
								dfdRowTemplateId: '#existing-template-group-children'
							};
							
							//sending and asigning few group type specific values
							if($('#groupType').val().trim() == 'CPG'){
								//console.log(_super.searchValue.classId);
								$('#classId').val(_super.searchValue.classId);
							}else if($('#groupType').val().trim() == 'RCG' || $('#groupType').val().trim() == 'BCG'){
								dtTableConfig = $.extend(
									dtTableConfig, {
										dfdChildrenUrl: app.URLFactory.getURL('groupSearchUrl'), 
										dfdChildrenParams: {resourceType: 'getChildRCGBCG'}
									}
								);
							}
							
							//processing data table generation
							//passing only components
							var componentList = response.componentList;
							//deleting componentList to pass only header
							delete response.componentList;
							

							var dtTable = new app.DataTable(dtTableConfig);
							//console.log(response);
							
							dtTable.setDataHeader(response);
							dtTable.setData(componentList);
							
							//if no component found disable save button at the bottom of the page
							if(componentList.length>=1){
								$('#save-existing-group').prop('disabled', false);
							}else{
								$('#save-existing-group').prop('disabled', true);
							}
								
							
							$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
							
							dtTable.init(); //init data table
						}
					}).complete(function(){
						$('.overlay_pageLoading').addClass('hidden');
						
						if(attachHandlers === undefined || attachHandlers === true){ // params to handle delegates
							_super.searchFormValidate($('#groupType').val().trim());
							_super.handleEvent();	
						}
							
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
						
						//logic for concatening different types components for various group types	
						if($('#groupType').val().trim() == 'RCG' || $('#groupType').val().trim() == 'BCG'){
							//iterating through all selected checkboxes
							$('.item-check:checked').each(function(){
								if($(this).data('item-type') == 'G'){ //case when group is found
									checkString.push($(this).val());
								}else if($(this).data('item-type') == 'S'){
									//getting its all selected children color
									var nodeChkId = $(this).data('chknode-id');
									var slctdStyleColors = [];
									
									$('[data-chkparent-id=' + nodeChkId +']:checked').each(function(){
										slctdStyleColors.push($(this).val());
									});
									
									checkString.push($(this).val() + ':' + slctdStyleColors.join('-'));
								}
							});
						}else{							
							//serializing All checkbox value in one hidden field  
							$('.item-check:checked').each(function(){
								checkString.push($(this).val());
							});
						}
						
						//setting selected and processed component data
						$('#splitCheckboxValue').val(checkString.toString());
						
						try{
							// AFUPYB3: added groupId as Param
							var serializedData = $('#selectedComponentForm').serialize() 
								+ '&groupType=' + $('#groupType').val().trim() + '&groupId=' + $('#groupId').val().trim();
							
							//sending and asigning few group type specific values	
							if($('#groupType').val().trim() == 'CPG'){
								//console.log(_super.searchValue.classId);
								serializedData += '&classId=' + _super.searchValue.classId;
							}
								
							app.GroupFactory.addNewSplitComponent(serializedData) //sending and asigning few group type specific values
								.done(function(result){
									if(!result.length){
										$('#group-existing-component-area').html(app.GroupLandingApp.buildMessage('Error in adding components', 'error'))
											.fadeIn('slow');
											$('.hide_after_error').hide();
											return;
									}
									
									var resultJSON = $.parseJSON(result);
									
									app.GroupLandingApp.handleGroupCreationResponse(resultJSON, resultJSON.groupType, false);
									
									//now refreshing existing component list
									_super.loadExitingData(false)
										.complete(function(){
											//updqating component search result
											$('#search-components').trigger('submit');
										});
									
								}).error(function(jqXHR, textStatus, errorThrown){
									$('#group-creation-messages').html(app.GroupLandingApp.buildMessage(jqXHR.status + ' - ' + textStatus + ' - ' + errorThrown, 'error'))
										.fadeIn('slow');
								}).complete(function(){
									//cleaning up message after 4 sec
									app.GroupLandingApp.cleanupMessage($('#group-creation-messages'), 8000);
								});
						}catch(ex){
							console.log(ex.message);
						}
					}
				});
				
				//code to handle enabling or disabling remove component button
				$('#existingComponentForm').on('click', '.item-check, #select-all', function(){
					//console.log($(this).is(':checked'));
					if($(this).is(':checked')){
						$('#remove-existing-component').prop('disabled', false);
					}else{
						if(!$('.item-check:checked').length)
							$('#remove-existing-component').prop('disabled', true);
						else
							$('#remove-existing-component').prop('disabled', false);
					}
				});
				
				//code to handle remove component 
				$('#remove-existing-component').on('click',function(){
					var _that = $(this);
					$('#error-massege').html("Are you sure you want to remove the(se) component(s)?");
					$('#errorBox').dialog({
					   autoOpen: true, 
					   modal: true,
					   resizable: false,
					   dialogClass: "dlg-custom",
					   title: 'Delete Component',
					   buttons: {
						"Cancel": function() {$(this).dialog("close");},
						"Delete": function() {
							$(this).dialog("close"); //closing dialog
							
							var checkString = [];
						 
							//serializing All checkbox value in one hidden field  
							$('.item-check:checked').each(function(){
								checkString.push($(this).val());
							});
							
							$('#existingSeletedItems').val(checkString.toString());
							
							$('#overlay_pageLoading').removeClass('hidden'); //showing as working
							
							//requesting factorty to validate and delete
							app.GroupFactory.removeComponents($('#existingComponentForm').serialize() 
								+ '&groupType='+ $('#groupType').val().trim() + '&groupId=' + $('#groupId').val().trim()
							)
							.done(function(result){
								//console.log(result);
								if(!result.length){
									$('#group-existing-component-area').html(app.GroupLandingApp.buildMessage('Error deleting components', 'error'))
										.fadeIn('slow');
										return;
								}
								
								var responseJSON = $.parseJSON(result);
								
								if(responseJSON.status == 'SUCCESS'){
									$('#group-existing-component-area').html(
										app.GroupLandingApp.buildMessage(
											responseJSON.description ? responseJSON.description : 'Component Deleted Successfully!', 
										'success')
									).fadeIn('slow');
									
									_super.loadExitingData(false); // on success reloading existing component
									
									//checking if already component search fired befre removeing component(s)
									//if yes then refreshing the same search result
									if(!$("#search-result-panel").hasClass('hidden')){
										$('#frmComponentSearch').trigger('submit');
									}
								}else if(responseJSON.status == 'FAIL'){
									$('#group-existing-component-area').html(
										app.GroupLandingApp.buildMessage(
											responseJSON.description ? responseJSON.description : 'Component deletion was unsuccessful!', 
										'error')
									).fadeIn('slow');
								}
								
							})
							.complete(function(){
								$('#overlay_pageLoading').addClass('hidden'); //hiding loader icon
								//cleaning up message after 4 sec
								app.GroupLandingApp.cleanupMessage($('#group-existing-component-area'), 8000);
							});	
						},
					   },
					});
				});
					
				var editAjaxReq = null;
				
				$('#edit-header').on('click', function(e){
					
					if($(this).hasClass('save')){
						//triming all whitespaces
						$('#fromHeaderEdit').find('input[type=text], textarea').each(function(){
							$(this).val($(this).val().trim());
						});
						
						if(!$('#fromHeaderEdit')[0].checkValidity()){
							$(this).attr('type', 'submit');
							$(this).click();
						}else{
							$(this).attr('type', 'button');
							
							if($('#groupDesc').val().trim().length < app.Global.defaults.minGroupDescChars){
								$('#error-massege').html("Please enter at least " + app.Global.defaults.minGroupDescChars + " characters in description field.");
								$('#errorBox').dialog('open');
								return;
							}
							
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
					}else{
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
									var txt = _super.htmlentitiesEncode($(this).text());
									
									var elm ='<input type="text" data-original-value="' + txt + '" id="' + $(this).data('field-name') 
										+ '" name="' + $(this).data('field-name') + '" value="' + $(this).text() + '"';
									
									if(!!required){
										elm = required ? elm + ' required="required" ' : '';
										$('[rel=' + $(this).data('field-name') + ']').after('<span class="red-text">&nbsp;(*)</span>');
									}
									
									elm += ' />';
									
									
									$(this).html(elm);
									break;
								case 'textarea':
									//changing text and sanitizing with replacable html entities
									var txt = _super.htmlentitiesEncode($(this).text());
									
									var elm = '<textarea type="text" data-original-value="' + txt + '" id="' + $(this).data('field-name') 
										+ '" name="' + $(this).data('field-name') + '"';
									
									if(!!required){
										elm = required ? elm + ' required="required" ' : '';
										$('[rel=' + $(this).data('field-name') + ']').after('<span class="red-text">&nbsp;(*)</span>');
									}
									elm += ' >' + $(this).text() + '</textarea>';
									$(this).html(elm);
							}
							
							var curChars = $('#groupName').val() || '';
							var curCharsText = $('#groupDesc').val() || '';
							
							$('#nameCurChars').text(curChars.length);
							
							$('#descCurChars').text(curCharsText.length);
							
							
						});
						
						//enabling cancel button
						$('.maxChars').show();
						$('#cancel-edit-header').prop('disabled',false).css('visibility','visible');
						$(this).val('Save').addClass('save');
					}	
				});
				
				$('#cancel-edit-header').on('click', function(e){
					/* console.log(editAjaxReq);
					if(!editAjaxReq){
						editAjaxReq.abort();
					} */
					$('.editable').each(function(){
						var data = $(this).find('input, textarea').data('original-value');
						
						$(this).text(_super.htmlentitiesDecode(data));
						
						
						$('span.red-text').remove();
						
					});
					//enabling cancel button
					$('#edit-header').val('Edit').removeClass('save').css({opacity: 1});
					$(this).prop('disabled',true).css('visibility','hidden');
					$('.maxChars').hide();
				});
				
				$('#cancel-edit-header').on('save.success', function(e){
					/* console.log(editAjaxReq);
					if(!editAjaxReq){
						editAjaxReq.abort();
					} */
					$('.editable').each(function(){
						var data = $(this).find('input, textarea').val();
						$(this).text(data);
						
						
						$('span.red-text').remove();
						
					});
					//enabling cancel button
					$('#edit-header').val('Edit').removeClass('save').css({opacity: 1});
					$(this).prop('disabled',true).css('visibility','hidden');
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

				//handler to save component selection
				$('#save-existing-group').on('click', function(){
					var params = $('#existingComponentForm').serialize();
					
					//grouping specific params
					if($('#groupType').val().trim() == 'BCG'){
						params += '&resourceType=priorityValueSave';
						/**
						* code to check all priorities have valid values before saving
						*/
						var validpriorities = false;
						$('input.tree[type=number]').each(function(){
							if($(this).val().trim().length > 0)
								validpriorities = true;
							else
								validpriorities = false;
						});
						
						if(!validpriorities){
							alert('Please enter valid priorities before saving');
							return;
						}
						
						//making prority list
						var priorityList = [];
						$('input.tree[type=number]').each(function(){
							priorityList.push($(this).attr('name').split('_')[0] + ':' + $(this).val());
						});
						
						params += '&priorities=' + priorityList.toString();
					}else if($('#groupType').val().trim() == 'SSG' || $('#groupType').val().trim() == 'SCG'){
						params += '&resourceType=defaultValueSave';
					}
					
					params += '&groupType=' + $('#groupType').val() + '&groupId=' + $('#groupId').val();
					
					app.GroupFactory.editDefaultComponent(params)
						.done(function(result){
								//console.log(result);
								if(!result.length){
									$('#group-existing-component-area').html(app.GroupLandingApp.buildMessage('Error Saving components', 'error'))
										.fadeIn('slow');
										return;
								}
								
								var responseJSON = $.parseJSON(result);
								
								if(responseJSON.status == 'SUCCESS'){
									$('#group-existing-component-area').html(
										app.GroupLandingApp.buildMessage(
											responseJSON.defaultValueStatusMessage ? responseJSON.defaultValueStatusMessage : 'Default Component Set Successfully!', 
										'success')
									).fadeIn('slow');
									
									_super.loadExitingData(false); // on success reloading existing component
								}else if(responseJSON.status == 'FAIL'){
									$('#group-existing-component-area').html(
										app.GroupLandingApp.buildMessage(
											responseJSON.defaultValueStatusMessage ? responseJSON.defaultValueStatusMessage : 'Default Component set was unsuccessful!', 
										'error')
									).fadeIn('slow');
								}
								
							})
							.complete(function(){
								$('#overlay_pageLoading').addClass('hidden'); //hiding loader icon
								//cleaning up message after 4 sec
								app.GroupLandingApp.cleanupMessage($('#group-existing-component-area'), 8000);
							});
				});
				
				$(document).on('ready', function(e){
					$('#nameMaxChars').text(app.Global.defaults.maxGroupNameChars);
					$('#descMinChars').text(app.Global.defaults.minGroupDescChars);
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
				case 'GSG':
					$('#groupId-search , #groupName-search').prop('disabled',true);
					_super.putValue();
					break;
				
				}
				$('#styleOrinNoShowOnly').bind('putIt',function(e){ 
					_super.putValue(); 
				})
				
				$('#styleOrinNoShowOnly').on('keyup',function(e){
					_super.putValue();
					
					if(e.keyCode == 13){
						$(this).trigger('putIt');
					}
				});
				
				$('#styleOrinNoShowOnly').on('keydown',function(e){
					_super.putValue();
					
					if(e.keyCode == 13){
						$(this).trigger('putIt');
					}
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