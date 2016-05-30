function grouping_logOut(username){			
	
	if(username.indexOf('@') === -1) 
	{
	   window.location = "/wps/portal/home/InternalLogin";
	} else {
		
		window.location = "/wps/portal/home/ExternalVendorLogin";
	}
	
};

var app = app || {};

;(function($, _){
	'use strict';
	app.GroupLandingApp = {
		urlCollection: {SCGUrl: '', SSGUrl: '',  searchUrl: '', },
		
		depts: [],
		
		selectedDepts: [],
		
		classList: [],
		
		selectedClassList: [],
		
		//template parser for dept list 
		deptTemplate :$('#dept-row-template').length ?  _.template($('#dept-row-template').html(), null,  {
			interpolate :  /\{\{\=(.+?)\}\}/g,
			evaluate: /\{\{(.+?)\}\}/g
		}) : null,
		
		//template parser for class list
		classTemplate : $('#class-row-template').length ? _.template($('#class-row-template').html(), null,  {
			interpolate :  /\{\{\=(.+?)\}\}/g,
			evaluate: /\{\{(.+?)\}\}/g
		}) : null,
		
		pluckIds: function(collection, isFirst){
			//console.log(_.map(_.map(collection, _.values), _.last));
			if(isFirst === undefined)
				return _.map(_.map(collection, _.values), _.last);
			else
				return _.map(_.map(collection, _.values), _.first);
		},
		
		attachControls: function(){
			var dlgCommonAttr = {
				modal: true,
				autoOpen: false,
				resizable: true,
				dialogClass: "dlg-custom",
			};
			
			var _super = this;
			try{
				//attaching dialog for greate group popup for certain types
				$('#dlgGroupCreate').dialog($.extend(dlgCommonAttr, {
						title: "Create Grouping", 
						width: 480,
						height: 370,
						minWidth: 480,
						minHeight: 370,
						create: function(event, ui){
							$('#nameMaxChars').text(app.Global.defaults.maxGroupNameChars);
							$('#descMaxChars').text(app.Global.defaults.maxGroupDescChars);
						},
						beforeClose: function(event, ui){
							_super.removeAddlFields();
							$('.add-RCG-field').hide();
						},
					})
				);
				
				//dialog for dept search
				$('#dlgdeptSearch').dialog($.extend(dlgCommonAttr, {
						title: "Department", 
						width: 335,
						height: 340,
						minWidth: 335,
						minHeight: 340,
						open: function(event, ui){
							//building the dept list
							if(_super.selectedDepts.length){
								//console.log(_.map(_.map(_super.selectedDepts, _.values), _.last));
								$('#dept-search-result').html(_super.deptTemplate({data: _super.selectedDepts, selected: _super.pluckIds(_super.selectedDepts) }));
							}else{
								$('#dept-search-result').html(_super.deptTemplate({data: _super.depts, selected: []}));
							}
							
							//focusing the search text box when opened
							$('#selectedDeptSearch').focus();
						},
						beforeClose: function(event, ui){
							//clearing the search text box when closed
							$('#selectedDeptSearch').val('');
						},
					})
				);
				
				//dialog for class search
				$('#dlgClassSearch').dialog($.extend(dlgCommonAttr, {
						title: "Class Number", 
						width: 335,
						height: 340,
						minWidth: 335,
						minHeight: 340,
						open: function(event, ui){
							//building the dept list
							$('#class-result-area').html(_super.classTemplate({data: _super.classList, selected: _super.pluckIds(_super.selectedClassList, false) }));
						},
						/* beforeClose: function(event, ui){
							//clearing the search text box when closed
							$('#selectedDeptSearch').val('');
						},*/
					})
				);
				
				//attching date pickers
				/*$( "#startDate, #endDate" ).datepicker({
					showOn: "button",
					buttonImage: app.Global.defaults.contextPath + "/img/iconCalendar.gif",
					buttonImageOnly: false,
					//minDate: 0 ,
					buttonText: ""
				});*/
				
				$('#startDate , #endDate').prop("readonly", true);
				
				$('#startDate').datepicker({
					showOn: "button",
					 showOn: "both",
					buttonImage: app.Global.defaults.contextPath + "/img/iconCalendar.gif",
					buttonImageOnly: true,
					buttonText: "",
					minDate: 0 ,
					onClose: function( selectedDate ) {
					 $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
					}
				});
				
				$('#endDate').datepicker({
					showOn: "button",
					 showOn: "both",
					buttonImage: app.Global.defaults.contextPath + "/img/iconCalendar.gif",
					buttonImageOnly: true,
					buttonText: "",
					
					onClose: function( selectedDate ) {
					$( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
					}
				});
				
			}catch(ex){
				console.log(ex.message);
			}
		},
		
		handleEvents: function(){
			try{
				var _super = this;
				//attaching dialog for greate group popup for certain types
				$('#btnGroupCreate').on('click', function(e){
					//getting selected group type
					var groupType = $('#groupTypeSelector :selected').val();
					//console.log(groupType);
					
					switch(groupType){
						case 'SCG':
							//console.log(_super.SCGUrl);
							window.location.href = _super.urlCollection.SCGUrl;
							break;
						case 'SSG':
							//console.log(_super.SSGUrl);
							window.location.href = _super.urlCollection.SSGUrl;
							break;
						case 'BCG':
							_super.addAddlFields();
							$('#dlgGroupCreate').dialog( "option", "minHeight", 457 );
							$('#dlgGroupCreate').dialog( "option", "height", 457 );
							$('#groupTypeDropDown option[value="' +groupType +'"]').prop('selected', true);
							$('#groupType').val(groupType);
							$('#dlgGroupCreate').dialog('open');
							break;
						case 'RCG' :
							$('#dlgGroupCreate').dialog( "option", "height", 400 );
							$('#groupTypeDropDown option[value="' +groupType +'"]').prop('selected', true);
							$('#groupType').val(groupType);
							$('#dlgGroupCreate').dialog('open');
							$('.add-RCG-field').show();
							break;
						default:
							$('#dlgGroupCreate').dialog( "option", "height", 370 );
							$('#groupTypeDropDown option[value="' +groupType +'"]').prop('selected', true);
							$('#groupType').val(groupType);
							$('#dlgGroupCreate').dialog('open');
					}
				});
				
				//close group creation dlg-custom
				$('#closeGrpDlg').on('click', function(e){
					$('#createGroupForm')[0].reset();
					$('#dlgGroupCreate').dialog('close');
					if($('#closeGrpDlg').hasClass('refresh')){
						$('.overlay_pageLoading').removeClass('hidden');
						app.GroupFactory.searchSCGComponents($('#frmComponentSearch').serialize())						
							.done(function(result){
								var response = $.parseJSON(result);
								//console.log(response.componentList);
								if(response.componentList){
									//processing data table generation
									//passing only components
									var componentList = response.componentList;
									//deleting componentList to pass only header
									delete response.componentList;
									
									app.DataTable.dataHeader = response;
									app.DataTable.data = componentList;
									app.DataTable.init();
								}								
								
								$("#search-result-panel").removeClass('hidden');
																
							}).complete(function(){
								$('#closeGrpDlg').val('Cancel');
								$('#group-creation-messages').html('&nbsp;');
								$('#closeGrpDlg').removeClass('refresh');
								$('#select-all').prop('checked', false);
								$('#btnCreateGroup').prop('disabled', false).css('opacity','1');
								$('#dlgGroupCreate').dialog( "option", "height", 370 );
								$('.overlay_pageLoading').addClass('hidden');
								
							});
					}				
				});
				
				//group name char limit and display
				$('#groupName').on('keyup', function(){
					var curChars = $('#groupName').val() || '';
					
					if(curChars.length <= app.Global.defaults.maxGroupNameChars){
						$('#nameCurChars').text(curChars.length);
						return true;
					}else{
						$(this).val(curChars.substr(0, app.Global.defaults.maxGroupNameChars));
						return false;
					}
						
				});
				
				//group description char limit and display
				$('#groupDesc').on('keyup', function(){
					var curChars = $('#groupDesc').val() || '';
					if(curChars.length <= app.Global.defaults.maxGroupDescChars){
						$('#descCurChars').text(curChars.length);
						return true;
					}else{
						$(this).val(curChars.substr(0, app.Global.defaults.maxGroupDescChars));
						return false;
					}
						
				});
				
				//group search button action
				$('#search-groups').on('click', function(e){
					e.preventDefault(); //preventing default form submission
					
					var searchFiledsValue = $('#frmGroupSearch').serialize();
					
					try{
						app.DataTable.searchParams = searchFiledsValue;
						app.DataTable.columnAlias = ['groupId', 'groupName', 'groupType', 'groupImageStatus', 'groupContentStatus', 'startDate', 'endDate'];
						app.DataTable.curPage = 1;
						
						$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
						
						app.DataTable.init();
							
					}catch(ex){
						console.log(ex.message);
					}
					
					
				});
				
				//group search result item delete link action
				$('#dataTable').on('click', '.delete-item', function(){
					if(!confirm('Are you sure you would like to delete?'))
						return;
					
					var groupId = $(this).data('group-id');
					var groupType = $(this).data('group-type');
				
					app.GroupFactory.deleteGroup({groupId: groupId, groupType: groupType})
						.done(function(result){
							
							var responseJSON = $.parseJSON(result);
							//console.log(responseJSON);
							var messageHtml = _super.buildMessage('Unknown Response!', 'error');
							
							if(responseJSON.deleteStatus !== undefined && responseJSON.deleteStatusMessage !== undefined){
								if(responseJSON.deleteStatus){
									messageHtml = _super.buildMessage(responseJSON.deleteStatusMessage, 'success');
									
									//triggering search again after successful deletion
									$('#search-groups').click();
								}	
								else
									messageHtml = _super.buildMessage(responseJSON.deleteStatusMessage, 'error');
							}
							
							$('#group-deletion-messages').html(messageHtml);
							
							//cleaning up message after 4 sec
							_super.cleanupMessage($('#group-deletion-messages'), 4000);
						});
				});

				
				
				//group create button action
				$('#btnCreateGroup').on('click', function(e){
					if($('#createGroupForm')[0].checkValidity()){
						e.preventDefault();  //preventing default form submission
						var checkString = []
						 
						 //serializing All checkbox value in one hidden field  
						 $('.item-check:checked').each(function(){
							 checkString.push($(this).val());
						 });
						$('#splitCheckboxValue').val(checkString.toString());
						
						
						var selectedComponentLists =  $('#selectedComponentForm').serialize();
						var createFormValues = $('#createGroupForm').serialize();
						
						try{
							app.GroupFactory.createGroup(createFormValues + '&' + selectedComponentLists)
								.done(function(result){
									var resultJSON = $.parseJSON(result);
									//console.log(resultJSON);
									
									//handling and taking care of the response 
									_super.handleGroupCreationResponse(resultJSON, $('#groupType').val());
								}).error(function(){
									$('#group-creation-messages').html(_super.buildMessage('Group Creation Error', 'error'));
								});
								
						}catch(ex){
							console.log(ex.message);
						}
					}
				});
				
				/**
					* Code to handle dept list and their selection procedures
				*/
				
				//dept search btn action
				$('#btnDlgDept').on('click', function(e){
					$('#dlgdeptSearch').dialog('open');
				});
				
				//close button action
				$('#btnCloseDept').on('click', function(e){
					$('#dlgdeptSearch').dialog('close');
				});
				
				//clear button action
				$('#btnClearDept').on('click', function(e){
					$('.dept-items').prop('checked', false);
				});
				
				$('#srchDeptsFrm').on('submit', function(e){
					e.preventDefault();
					if(_super.depts.length){
					
						var srchTxt = $('#selectedDeptSearch').val().trim();
						
						var btnPrevText = $('#btnDepSearch').val();
						$('#btnDepSearch').val('Searching..');
					
					
						setTimeout(function(){
							var reducedData = [];
							
							if(_super.selectedDepts.length){
								//firstly removing already selected items from search
								reducedData = _.filter(_super.depts, function(item){
									var matchedItem = _.find(_super.selectedDepts, item);
									
									if(matchedItem === undefined)
										return true;
									else
										return false;
								});
							}else
								reducedData = _super.depts;
							
							
							var data = _.filter(reducedData, function(item){
								if(item.deptDesc.toLowerCase().indexOf(srchTxt.toLowerCase()) != -1 
									|| item.deptId.toLowerCase().indexOf(srchTxt.toLowerCase()) != -1)
								{
									//console.log(item);
									return true;
								}else
									return false;
							});
							
							//adding firstly selected items then searched items if any
							if(_super.selectedDepts.length)
								data = _.union(_super.selectedDepts, data);
							
							
							//console.log(data);
								
							
							$('#dept-search-result').html(_super.deptTemplate({data: data, selected: _super.pluckIds(_super.selectedDepts)}));
							
							
							$('#btnDepSearch').val(btnPrevText);
						}, 1000);
					}
				});
				
				$('#btnSaveDept').on('click', function(e){
					if(!$('.dept-items:checked').length)
						alert('Please select at least one department');
					else{
						
						//collecting selected items to be filtered
						var selectedItems = [];
						$('.dept-items:checked').each(function(){
							selectedItems.push($(this).val());
						});
						
						//scanning global list and filtering the selected items
						var selectedDepts = _.filter(_super.depts, function(item){
							return _.contains(selectedItems, item.deptId);
						});
						
						_super.selectedDepts = selectedDepts;
						
						//now sending selected depts to fetch available class nos
						var selectedDeptsFlatList = _super.pluckIds(_super.selectedDepts); //only dept ids in a flat array list
						
						//sending request
						var curText = $(this).val();
						$(this).val('Saving..'); //changing text to show request is in progress
						
						app.GroupFactory.fetchClassCodes({depts: selectedDeptsFlatList.join(',')})
							.done(function(result){
								var resultJSON = $.parseJSON(result);
								if(resultJSON.classList){
									//console.log(resultJSON);
									
									_super.classList = resultJSON.classList //assigning class based on current dept selections
									
									$('#s-grouping-dept').val(selectedDeptsFlatList.join(','));
									$('#s-grouping-class').val(''); //clearing already selected class nos as dept changed
									
									//finally closeing the dialog
									$('#dlgdeptSearch').dialog('close');
								}
							}).complete(function(){
								$('#btnSaveDept').val(curText);
							});
					}
				});
				
				
				/**
				 * area to handle class selection based on selected depts
				*/
				
				//search btn action
				$('#btnDlgClass').on('click', function(e){
					$('#dlgClassSearch').dialog('open');
				});
				
				//close button action
				$('#btnCloseClass').on('click', function(e){
					$('#dlgClassSearch').dialog('close');
				});
				
				//clear button action
				$('#btnClearClass').on('click', function(e){
					$('.class-items').prop('checked', false);
				});
				
				$('#btnSaveClass').on('click', function(e){
					if($('.class-items:checked').length){
						//collecting selected items to be filtered
						var selectedItems = [];
						$('.class-items:checked').each(function(){
							selectedItems.push($(this).val());
						});
						
						//scanning global list and filtering the selected items
						var selectedClassItems = _.filter(_super.classList, function(item){
							return _.contains(selectedItems, item.classId);
						});
						
						_super.selectedClassList = selectedClassItems;
						
						$('#s-grouping-class').val(_super.pluckIds(_super.selectedClassList, false).join(','));
					}
					
					$('#dlgClassSearch').dialog('close');
					
				});
				
				//bootstrapping events when DOM is ready State
				$(document).on('ready', function(e){
					
					//code to fetch all depts
					app.GroupFactory.fetchDepts()
						.done(function(result){
							//console.log(result)
							var resultsJSON = $.parseJSON(result);
							
							if(resultsJSON.deptList){
								_super.depts = resultsJSON.deptList;
							}
						});
				});
				
			}catch(ex){
				console.log(ex.message);
			}	
		},
		
		addAddlFields: function(){
			$('.optional-fields').removeClass('optional-fields').addClass('added-fields');
			$('#startDate').prop('required', true);
		},
		
		removeAddlFields: function(){
			$('.added-fields').removeClass('added-fields').addClass('optional-fields');
			$('#startDate').prop('required', false);
		},
		
		buildMessage: function (msg, dlgType){
			var highlightClass = dlgType == 'error' ? 'ui-state-error ui-custom-message-styling' : 'ui-state-highlight ui-custom-message-styling';
			
			return '<div class="ui-widget">'
				+ '<div class="ui-corner-all ' +  highlightClass + '">'
				+ 	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>'
				+		'<strong>' + msg + '</strong>'
				+ 	'</p>'
				+ '</div>'
				+ '</div>';
		},
		
		cleanupMessage: function(jqAreaObj, timeoutMS){
			timeoutMS = timeoutMS === undefined ? 8000 : timeoutMS;
			
			setTimeout(function(){
				jqAreaObj.fadeOut('slow');		
			}, timeoutMS);
		},
		
		handleGroupCreationResponse: function(responseJSON, groupType){
			var message = this.buildMessage('Unknown response', 'error');
			var errorFlag = true;
			if(responseJSON.groupCreationStatus){
				
				switch(parseInt(responseJSON.groupCreationStatus)){
					case app.Global.constants.groupStatus.GROUP_CREATED:
						//message = this.buildMessage('Group Created Successfully', 'success');
						message = this.buildMessage(responseJSON.groupCretionMsg, 'success');
						errorFlag = false;
						break;
					case app.Global.constants.groupStatus.GROUP_NOT_CREATED:
						//message = this.buildMessage('Group Creation Unsuccessful', 'error');
						message = this.buildMessage(responseJSON.groupCretionMsg, 'error');
						errorFlag = true;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_COMPONENT_SCG:
						//message = this.buildMessage('Split Color Group Created and Component Added Successfully', 'success');
						message = this.buildMessage(responseJSON.groupCretionMsg, 'success');
						errorFlag = false;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_OUT_COMPONENT_SCG:
						//message = this.buildMessage('Split Color Group Created but Component add was Unsuccessful', 'error');
						message = this.buildMessage(responseJSON.groupCretionMsg, 'error');
						errorFlag = true;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_COMPONENT_SSG:
						//message = this.buildMessage('Split SKU Group Created and Component Added Successfully', 'success');
						message = this.buildMessage(responseJSON.groupCretionMsg, 'success');
						errorFlag = false;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_OUT_COMPONENT_SSG:
						//message = this.buildMessage('Split SKU Group Created but Component Add was Unsuccessful', 'error');
						message = this.buildMessage(responseJSON.groupCretionMsg, 'error');
						errorFlag = true;
						break;
					default:
						console.log('here');
						console.log(app.Global.constants.groupStatus.GROUP_NOT_CREATED);
						console.log(app.Global.constants.groupStatus.GROUP_NOT_CREATED);
				}
			}
			
			//console.log(errorFlag);
			//console.log(groupType);
			//console.log((!errorFlag) && (groupType != 'SCG' && groupType != 'SSG'));
			
			if((!errorFlag) && (groupType != 'SCG' && groupType != 'SSG')){
				window.location.href = app.GroupLandingApp.urlCollection.addComponentUrl;
			}else
				$('#group-creation-messages').html(message);
				$('#closeGrpDlg').val('Close');
				$('#closeGrpDlg').addClass('refresh');
				$('#btnCreateGroup').prop('disabled', true).css('opacity','0.5')
				$('#dlgGroupCreate').dialog( "option", "minHeight", 400 );
				$('#dlgGroupCreate').dialog( "option", "height", 400 );
		},
		
		init: function(){
			this.attachControls();
			this.handleEvents();
		},
	};
	
	app.SplitGroupLanding = {
		
		handleEvents: function(){
			try{
				var _super = this;
				
				//handler to select all the items
				$('#select-all').on('click', function(e){
					if($(this).is(':checked')){
						$('.item-check').prop('checked', true);
						$('#selectedComponentForm input[type="radio"]').prop('disabled', false);
					}else{
						$('.item-check').prop('checked', false);
						$('#selectedComponentForm input[type="radio"]').prop('disabled', true);
					}
				});
				
				$('#row-container').on('click', '.item-check', function(){
					if($(this).is(':checked')){
						if($('.item-check').length == $('.item-check:checked').length)
							$('#select-all').prop('checked', true);			
					}else
						$('#select-all').prop('checked', false);
				});
				
				$('#row-container').on('click', '.item-check', function(e){
					//console.log($(this).parent().parent().find('input[type=radio]'));
					
					if($(this).is(':checked'))
						$(this).parent().parent().find('input[type=radio]').prop('disabled', false);
					else
						$(this).parent().parent().find('input[type=radio]').prop('disabled', true);
				});
				
				//split color search component
				$('#search-components').on('click', function(e){
					
					//dialog to show error when no field is entered
					if($('#styleOrinNo').val().trim() == '' && $('#vendorStyleNo').val().trim() == ''){
						$('#error-massege').html('Atleast one field is required');
						$('#errorBox').dialog({
							   autoOpen: true, 
							   modal: true,
							   resizable: false,
							   dialogClass: "dlg-custom",
							   buttons: {
								  OK: function() {$(this).dialog("close");}
							   },
							});
					//dialog to show error when both fields are entered
					} else if ($('#styleOrinNo').val().trim() && $('#vendorStyleNo').val().trim()){
						$('#error-massege').html("You cannot search for both fields at once. Please only fill in one fields before continuing.");
						$('#errorBox').dialog({
						   autoOpen: true, 
						   modal: true,
						   resizable: false,
						   dialogClass: "dlg-custom",
						   buttons: {
							  OK: function() {$(this).dialog("close");}
						   },
						});
					}else{
						$('.overlay_pageLoading').removeClass('hidden');
						app.GroupFactory.searchSCGComponents($('#frmComponentSearch').serialize())						
							.done(function(result){
								var response = $.parseJSON(result);
								//console.log(response.componentList);
								if(response.componentList){
									//processing data table generation
									//passing only components
									var componentList = response.componentList;
									//deleting componentList to pass only header
									delete response.componentList;
									
									app.DataTable.dataHeader = response;
									app.DataTable.data = componentList;
									app.DataTable.init();
								}
								
								
								$("#search-result-panel").removeClass('hidden');
																
							}).complete(function(){
								
								$('.overlay_pageLoading').addClass('hidden');
								
							});
							
					}
				});
				
				$('#split-components').on('click',function(){
					
					if($('.item-check:checked').length < 1){
						$('#error-massege').html("Please select atleast one item.");
						$('#errorBox').dialog({
						   autoOpen: true, 
						   modal: true,
						   resizable: false,
						   dialogClass: "dlg-custom",
						   buttons: {
							  OK: function() {$(this).dialog("close");}
						   },
						});
					}else{
						$('#dlgGroupCreate').dialog('open');
					}
					
					/*$('.item-check').each(function(){
						$(this).val();
						
					})*/
					
					
				})
				
			}catch(ex){
				console.log(ex.message);
			}
		},
		
		init: function(){
			//this.attachControls();
			this.handleEvents();
		},
	}
})(jQuery, _);