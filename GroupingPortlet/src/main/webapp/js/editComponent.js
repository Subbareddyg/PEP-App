var app = app || {} ;

;(function($){
	'use strict'; 
	
		app.EditComponentLandingApp = {
			
			urlCollection : {existingCompUrl : '', groupType : ''},
			
			searchValue : [],
			
			loadExitingData : function(){
				var _super = this;
				app.GroupFactory.fetchExistingComponents({groupType: $('#groupId').val(), groupId: $('#groupType').val()})				
				.done(function(result){
					var response = $.parseJSON(result);
						//console.log(response.componentList);
						if(response.componentList){
							//processing data table generation
							//passing only components
							var componentList = response.componentList;
							//deleting componentList to pass only header
							delete response.componentList;
							
							app.DataTable.dtContainer = '#exisiting-table-dataTable';
							app.DataTable.dataHeader = _super.searchValue = response;
							app.DataTable.data = componentList;
							
							$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
							
							app.DataTable.init();
						}
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
									console.log(result);
								});
						}catch(ex){
							console.log(ex.message);
						}
					}
				});
				
				
			},
			
			searchFormValidate : function(){
				$('#frmComponentSearch input[type="text"], #btnDlgClass , #btnDlgDept').prop('disabled',true);
				var styleOrinValue =  this.searchValue.styleOrinNoSearch ?  this.searchValue.styleOrinNoSearch : '';
				var vendorNoValue =  this.searchValue.vendorStyleNoSearch ? this.searchValue.vendorStyleNoSearch : '' ;
				
				if(!styleOrinValue.length)
					$('#styleOrinNo').prop('disabled',false);
				else
					$('#styleOrinNo').val(styleOrinValue);
				
				if(!vendorNoValue.length)
					$('#vendorStyleNo').prop('disabled',false);
				else
					$('#vendorStyleNo').val(vendorNoValue);
				
			},
			
			init: function(){
				this.loadExitingData();
				this.handleEvent();
				this.searchFormValidate();
				app.SplitGroupLanding.handleEvents();
			}
			
		}
	
	
	
})(jQuery);