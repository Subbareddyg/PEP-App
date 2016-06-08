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
					var response = $.parseJSON(result);
						//console.log(response.componentList);
						if(response.componentList){
							
							_super.searchValue = response;
							
							//processing data table generation
							//passing only components
							var componentList = response.componentList;
							//deleting componentList to pass only header
							delete response.componentList;
							
							
							
							/* app.DataTable.dtContainer = '#exisiting-table-dataTable';
							app.DataTable.dataHeader = _super.searchValue = response;
							app.DataTable.data = componentList; */
							
							var dtTable = new app.DataTable({dtContainer: '#exisiting-table-dataTable', rowTemplateId : '#sku-existing-template'});
							dtTable.setDataHeader(_super.searchValue);
							dtTable.setData(componentList);
							
							$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
							
							//app.DataTable.init();
							dtTable.init();
						}
					}).complete(function(){
						_super.searchFormValidate($('#groupType').val().trim());
						_super.handleEvent();
					});
					
					
			},
			
			handleEvent : function(){
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
									
									app.GroupLandingApp.handleGroupCreationResponse(resultJSON, resultJSON.groupType);
								}).error(function(jqXHR, textStatus, errorThrown){
									$('#group-creation-messages').html(app.GroupLandingApp.buildMessage(jqXHR.status + ' - ' + textStatus + ' - ' + errorThrown, 'error'))
										.fadeIn('slow');
								}).complete(function(){
									console.log('here');
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
			},
			putValue : function(){
				 $('#styleOrinNo').val($('#styleOrinNoShowOnly').val());
			},
			
			
			
			init: function(){
				this.loadExitingData();
				this.handleEvent();
				
				
				//app.GroupLandingApp.init();
				app.SplitGroupLanding.handleEvents();
			}
			
		}
	
	
	
})(jQuery);