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
		//urlCollection: {SCGUrl: '', SSGUrl: '',  searchUrl: '', },
		
		regExpCollection: {validDepts: /^[0-9]{1,10}( *, *[0-9]{1,10})*$/, },
		
		depts: [],
		
		selectedDepts: [],
		
		classList: [],
		
		selectedClassList: [],
		
		//template parser for dept list 
		deptTemplate : $('#dept-row-template').length ? _.template($('#dept-row-template').html(), null,  {
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
		
		checkGroupLock: function(groupId, formId){
			$('.overlay_pageLoading').removeClass('hidden'); //showing loader icon
			try{
				app.GroupFactory.checkGroupLock(groupId)
				.done(function(result){
					if(!result.length){
						$('#error-massege').html('Invalid Response Received From Server');
						$('#errorBox').dialog("open");
						return;
					}
					
					var response = $.parseJSON(result);
					
					if(response.LockStatus && response.LockStatus == 'Y'){
						$('#error-massege').html((response.message && response.message.length) ? response.message : 'Group Is Locked by Another User');
						$('#errorBox').dialog("open");
					}else if(response.LockStatus && response.LockStatus != 'Y'){
						$('#' + formId)[0].submit();
					}else{
						$('#error-massege').html('Unknown Group Status Returned From Server');
						$('#errorBox').dialog("open");
					}
				})
				.complete(function(){
					$('.overlay_pageLoading').addClass('hidden'); //hiding loader icon
				});
			}catch(ex){
				console.log(ex.message);
			}
		},
		
		handleAutoLogout: function(){
			/** section to handle page timeout and auto logout after session timeout
			*
			*/			
			
			$.idleTimer(app.Global.defaults.pageTimeOutMS);
			
			$(document).bind("idle.idleTimer", function(){
				//setting timer to auto logout and redirect if the user still does not interact
				setTimeout(function(){grouping_logOut($('#userId').val())}, app.Global.defaults.logOutTimeoutMS - app.Global.defaults.pageTimeOutMS);
				
				//dialog to show page timeout
				$('#error-massege').html('Your session has been ended due to inactivity');
				$('#errorBox').dialog({
				   autoOpen: true, 
				   modal: true,
				   resizable: false,
				   title : 'Page Timeout',
				   dialogClass: "dlg-custom",
				   close: function(){
					grouping_logOut($('#userId').val());  
				   },
				   buttons: {
					  Close: function() {
						  $(this).dialog("close");
					  }
				   },
				});
			});
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
							$('#descMinChars').text(app.Global.defaults.minGroupDescChars);
							$('#descMaxChars').text(app.Global.defaults.maxGroupDescChars);
						},
						beforeClose: function(event, ui){
							_super.removeAddlFields();
							$('.add-RCG-field').hide();
							$('#group-creation-messages').html('&nbsp;');
							$('#createGroupForm')[0].reset();
							$('#endDate').datepicker('option','minDate', 0);
							$('#endDate').datepicker('option','maxDate', null);
							$('#startDate').datepicker('option','minDate', 0);
							$('#startDate').datepicker('option','maxDate', null);
							
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
				//dialog for search validation
				$('#errorBox').dialog({
				   autoOpen: false, 
				   modal: true,
				   resizable: false,
				   title : 'Search',
				   dialogClass: "dlg-custom",
				   buttons: {
					  OK: function() {$(this).dialog("close");}
				   },
				});
				
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
								
				
				$('#startDate').datepicker({
					showOn: "button",
					 showOn: "both",
					buttonImage: app.Global.defaults.contextPath + "/img/iconCalendar.gif",
					buttonImageOnly: true,
					buttonText: "",
					minDate: 0 ,
					onClose: function( selectedDate ) {
						if(selectedDate !== undefined && selectedDate.toString().length)
							$( "#endDate" ).datepicker( "option", "minDate", selectedDate );
					},
					onSelect : function(){
						$('#startDate').prop('readonly',true);
					}
				});
				
				$('#endDate').datepicker({
					showOn: "button",
					 showOn: "both",
					buttonImage: app.Global.defaults.contextPath + "/img/iconCalendar.gif",
					buttonImageOnly: true,
					buttonText: "",
					minDate: 0 ,					
					onClose: function( selectedDate ) {
						if(selectedDate !== undefined && selectedDate.toString().length)
							$( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
					},
					onSelect : function(){
						$('#endDate').prop('readonly',true);
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
							//window.location.href = _super.urlCollection.SCGUrl;
							window.location.href = app.URLFactory.getURL('SCGUrl');
							break;
						case 'SSG':
							//console.log(_super.SSGUrl);
							//window.location.href = _super.urlCollection.SSGUrl;
							window.location.href =  app.URLFactory.getURL('SSGUrl');
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
					 $('#dlgGroupCreate').dialog('close');
					$('#dlgGroupCreate').dialog( "option", "height", 370 );
					
					
					if($('#closeGrpDlg').hasClass('refresh')){
						$('#frmComponentSearch').trigger('submit'); //trigerring search internally																				
						$('#closeGrpDlg').val('Cancel');
						$('#closeGrpDlg').removeClass('refresh');
						$('#select-all').prop('checked', false);
						$('#btnCreateGroup').prop('disabled', false).css('opacity','1');
						$('#dlgGroupCreate').dialog( "option", "height", 370 );
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
				
				$('#clrSrchGroup').on('click', function(e){
					_super.selectedDepts = [];
					_super.selectedClassList = [];
					$('#frmGroupSearch')[0].reset();
					
				});
				
				//group search button action
				$('#search-groups').on('click', function(e){
					e.preventDefault(); //preventing default form submission
						
						var validInputFlag = true;
						//scanning for atleast one value in one field
						$('#frmGroupSearch').find('input[type=text]').each(function(){
							validInputFlag = !!$(this).val().trim().length;
							return !validInputFlag;
						});
						
						if(!validInputFlag){
							$('#error-massege').html('Atleast one field is required');
							$('#errorBox').dialog("open");
							return false;
						}
						
					var searchFiledsValue = $('#frmGroupSearch').serialize();
					
					try{
						app.DataTableAjax.searchParams = searchFiledsValue;
						app.DataTableAjax.columnAlias = [
							'groupId', 'groupName', 'groupType', 
							'imageStatus', 'contentStatus', 'startDate', 'endDate'
						];
						app.DataTableAjax.curPage = 1;
						
						$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
						
						app.DataTableAjax.init();
							
					}catch(ex){
						console.log(ex.message);
					}
					
					
				});
				
				//group search result item delete link action
				$('#dataTable').on('click', '.delete-item', function(){
					var _that = $(this);
					$('#error-massege').html("Are you sure you would like to delete?");
					$('#errorBox').dialog({
					   autoOpen: true, 
					   modal: true,
					   resizable: false,
					   dialogClass: "dlg-custom",
					   title: 'Delete Grouping',
					   buttons: {
						"Cancel": function() {$(this).dialog("close");},
						"Delete": function() {
							$(this).dialog("close"); //closing dialog
							
							var groupId = _that.data('group-id');
							var groupType = _that.data('group-type');
							
							$('.overlay_pageLoading').removeClass('hidden'); //showing as working
							
							//requesting factorty to validate and delete
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
									
									$('#group-deletion-messages').html(messageHtml).fadeIn('fast');
									
									//cleaning up message after 4 sec
									_super.cleanupMessage($('#group-deletion-messages'), 8000);
									
								})
								.complete(function(){
									$('.overlay_pageLoading').addClass('hidden'); //hiding loader icon
								});
						},
					   },
					});
					
					//if(!confirm('Are you sure you would like to delete?'))
					//	return;
				});

				
				$("#startDate , #endDate").on('keydown',function(e){
					e.preventDefault();
				});
				
				//group create button action
				$('#btnCreateGroup').on('click', function(e){
					//triming all whitespaces
					$('#createGroupForm').find('input[type=text], textarea').each(function(){
						$(this).val($(this).val().trim());
					});
					
					$('#startDate').prop('readonly', false); //for validation purpose making it editable again
					
					if($('#createGroupForm')[0].checkValidity()){
						e.preventDefault();  //preventing default form submission
						
						if($('#groupDesc').val().trim().length < app.Global.defaults.minGroupDescChars){
							$('#error-massege').html("Please enter at least " + app.Global.defaults.minGroupDescChars + " characters in description field.");
							$('#errorBox').dialog('open');
							return;
						}
						
						var checkString = [];
						 
						 //serializing All checkbox value in one hidden field  
						 $('.item-check:checked').each(function(){
							 checkString.push($(this).val());
						 });
						$('#splitCheckboxValue').val(checkString.toString());
						
						
						var selectedComponentLists =  $('#selectedComponentForm').serialize();
						var createFormValues = $('#createGroupForm').serialize();
						
						try{
							$('#btnCreateGroup').prop('disabled', true).css('opacity','0.5').val('Saving..');
							app.GroupFactory.createGroup(createFormValues + '&' + selectedComponentLists)
								.done(function(result){
									$('#btnCreateGroup').prop('disabled', false).css('opacity','1').val('Save');
									var resultJSON = $.parseJSON(result);
									//console.log(resultJSON);
									
									//handling and taking care of the response 
									_super.handleGroupCreationResponse(resultJSON, $('#groupType').val());

								}).error(function(){
									$('#btnCreateGroup').prop('disabled', false).css('opacity','1').val('Save');
									if($('#groupType').val()=='BCG'){
										$('#dlgGroupCreate').dialog( "option", "minHeight", 500 );
										$('#dlgGroupCreate').dialog( "option", "height", 500 );
									}else if($('#groupType').val()=='RCG'){
										$('#dlgGroupCreate').dialog( "option", "minHeight", 447 );
										$('#dlgGroupCreate').dialog( "option", "height", 447 );
									}else{
										$('#dlgGroupCreate').dialog( "option", "minHeight", 427 );
										$('#dlgGroupCreate').dialog( "option", "height", 427 );
									}
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
				
				$('#s-grouping-dept').on('blur', function(e){
					/** first checking if any valid comma seperated dept is already there or not. 
					* regardless generated by dialog or typed directly 
					*/
					if(!$(this).val().trim().length)
						return;
					
					if(_super.regExpCollection.validDepts.test($(this).val().trim())){
						//now looking the selected dept list to determine whether it has been generated or typed
						var deptArr = $(this).val().trim().split(',');
						var selectedDeptsFlatList = _super.pluckIds(_super.selectedDepts);
						var diff = _.difference(deptArr, selectedDeptsFlatList);
						
						if(diff.length){ //checking for identical array
							//selecting new depts as changed detected by direct input
							
							//now scanning and getting the items matching with new dept ids
							var matchedItems = _.filter(_super.depts, function(item){
								return _.contains(diff, item.deptId);
							});
							
							if(_.size(matchedItems)){
								//joining newly typed items
								_super.selectedDepts = _.union(_super.selectedDepts, matchedItems);
								
								//disabling class selector and textbox to prevent type untill classes are refetched
								$('#btnDlgClass').prop('disabled', true);
								$('#s-grouping-class').val('Fetching..').prop('disabled', true);
								
								//fetching classes again
								$.when(_super.fetchClasses(deptArr))
									.then(function(){
										//enabling class selector and textbox when classes are fetched
										$('#btnDlgClass').prop('disabled', false);
										$('#s-grouping-class').val('').prop('disabled', false);
									});
							}else{
								alert('Entered Dept Ids do not exist');
								$(this).val(_.difference(deptArr, diff).join(','));
							}
						}
					}else{
						alert('Invalid depts entered');
						$(this).val('');
					}
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
						var btnPrevText = $('#btnDepSearch').val();
						$('#btnDepSearch').val('Searching..');
						
						var dfd = $.Deferred();
						
						dfd.then(function(data){
							$('#dept-search-result').html(_super.deptTemplate({data: data, selected: _super.pluckIds(_super.selectedDepts)}));
						})
						.done(function(){
							$('#btnDepSearch').val(btnPrevText);
						});
						
						//making async call using jq dfd
						setTimeout(function(){dfd.resolve(_super.searchDepts())}, 1000);
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
						
						//sending request
						var curText = $(this).val();
						$(this).val('Saving..'); //changing text to show request is in progress
						
						$.when(_super.fetchClasses(selectedItems))
							.then(function(){
								var selectedDeptsFlatList = _super.pluckIds(_super.selectedDepts); //only dept ids in a flat array list
								
								$('#s-grouping-dept').val(selectedDeptsFlatList.join(','));
								$('#s-grouping-class').val(''); //clearing already selected class nos as dept changed
								
								//finally closeing the dialog
								$('#dlgdeptSearch').dialog('close');
								
								$('#btnSaveDept').val(curText);
							}); //resolving the dfd as async method
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
				
				$('#s-grouping-class').on('blur', function(e){
					if(!$(this).val().trim().length)
						return;

					if(_super.regExpCollection.validDepts.test($(this).val().trim())){
						
						if(!_super.selectedDepts.length){
							alert('Please Select Dept Before Selecting Class');
							return;
						}
						
						//now looking the selected dept list to determine whether it has been generated or typed
						var classArr = $(this).val().trim().split(',');
						
						//looking inside the available list to validate
						var selectedClassFlatList = _super.pluckIds(_super.selectedClassList, false);
						var diff = _.difference(classArr, selectedClassFlatList);
						
						if(diff.length){ //checking for identical array
							//selecting new depts as changed detected by direct input
							
							//now scanning and getting the items matching with new dept ids
							var matchedItems = _.filter(_super.classList, function(item){
								return _.contains(diff, item.classId);
							});
							
							//console.log(matchedItems);
							if(_.size(matchedItems)){
								//joining newly typed items
								_super.selectedClassList = _.union(_super.selectedClassList, matchedItems);
							}else{
								//alert('Entered Class(es) Does/Do Not Belong To The Selected Dept(s)');
								alert('Invalid Class Entered');
								$(this).val(_.difference(classArr, diff).join(','));
							}
							
						}
					}else{
						alert('Invalid Class Entered');
						$(this).val('');
					}	
				});
				
				
				//bootstrapping events when DOM is ready State
				$(document).on('ready', function(e){
					if(app.URLFactory.getURL('groupSearchUrl')){
						//code to fetch all depts
						
						app.GroupFactory.fetchDepts()
							.done(function(result){
								//console.log(result)
								var resultsJSON = $.parseJSON(result);
								
								if(resultsJSON.deptList){
									_super.depts = resultsJSON.deptList;
								}
								
								//enabling dept and class selection input controls
								$('#s-grouping-dept, #btnDlgDept, #s-grouping-class, #btnDlgClass').prop('disabled', false);
							});
					}
				}); 
				
				_super.handleAutoLogout();
				
			}catch(ex){
				console.log(ex.message);
			}	
		},
		
		searchDepts: function(){
			var reducedData = [];
			var _super = this;
			var srchTxt = $('#selectedDeptSearch').val().trim();
			
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
			return data;
		},
		
		fetchClasses: function(selectedItems){
			//collecting selected items to be filtered
			
			var _super = this;
						
			//scanning global list and filtering the selected items
			var selectedDepts = _.filter(_super.depts, function(item){
				return _.contains(selectedItems, item.deptId);
			});
						
			_super.selectedDepts = selectedDepts; //assigning to global selected list
						
			//now sending selected depts to fetch available class nos
			var selectedDeptsFlatList = _super.pluckIds(_super.selectedDepts); //only dept ids in a flat array list
						
			//sending request		
			return app.GroupFactory.fetchClassCodes({depts: selectedDeptsFlatList.join(',')})
					.done(function(result){
						var resultJSON = $.parseJSON(result);
						if(resultJSON.classList){
							//console.log(resultJSON);
							
							_super.classList = resultJSON.classList //assigning class based on current dept selections
							
							//$('#s-grouping-dept').val(selectedDeptsFlatList.join(','));
							//$('#s-grouping-class').val(''); //clearing already selected class nos as dept changed
							
							//finally closeing the dialog
							//$('#dlgdeptSearch').dialog('close');
						}
					})
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
			
			var timer = setTimeout(function(){
				jqAreaObj.fadeOut('slow');		
			}, timeoutMS);
		},
		
		handleGroupCreationResponse: function(responseJSON, groupType, redirect){
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
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_COMPONENT_CPG:
						//message = this.buildMessage('Split SKU Group Created and Component Added Successfully', 'success');
						message = this.buildMessage(responseJSON.groupCretionMsg, 'success');
						errorFlag = false;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_OUT_COMPONENT_CPG:
						//message = this.buildMessage('Split SKU Group Created but Component Add was Unsuccessful', 'error');
						message = this.buildMessage(responseJSON.groupCretionMsg, 'error');
						errorFlag = true;
						break;
					default:
						/* console.log('here');
						console.log(app.Global.constants.groupStatus.GROUP_NOT_CREATED);
						console.log(app.Global.constants.groupStatus.GROUP_NOT_CREATED); */
				}
			}

			//setting rediretion flag
			redirect = (redirect === undefined || redirect == true) ? true : false; 
			
			if((!errorFlag) && (groupType != 'SCG' && groupType != 'SSG') && redirect){
				//window.location.href = app.GroupLandingApp.urlCollection.addComponentUrl;
				window.location.href = app.URLFactory.getURL('addComponentUrl');
			}else{
				if($('#groupType').val()=='BCG'){
					$('#dlgGroupCreate').dialog( "option", "minHeight", 500 );
					$('#dlgGroupCreate').dialog( "option", "height", 500 );
				}else if($('#groupType').val()=='RCG'){
					$('#dlgGroupCreate').dialog( "option", "minHeight", 447 );
					$('#dlgGroupCreate').dialog( "option", "height", 447 );
				}else{
					$('#dlgGroupCreate').dialog( "option", "minHeight", 427 );
					$('#dlgGroupCreate').dialog( "option", "height", 427 );
				}
				
				$('#group-creation-messages').html(message).fadeIn('slow');
				
				if(!errorFlag){
					$('#btnCreateGroup').prop('disabled', true).css('opacity','0.5');
					$('#closeGrpDlg').val('Close');
					$('#closeGrpDlg').addClass('refresh');
				}
											
			}
		},
		
		init: function(){
			this.attachControls();
			this.handleEvents();
		},
	};
	
	app.SplitGroupLanding = {
		//
		componentDataSearchService: function(params){
			$('.overlay_pageLoading').removeClass('hidden');
			
			/* if(params != undefined && params.length)
				params = $('#frmComponentSearch').serialize() + '&' + params;
			else
				params = $('#frmComponentSearch').serialize(); */
			
			return app.GroupFactory.searchSplitComponents(params)						
				.complete(function(){
					$('.overlay_pageLoading').addClass('hidden');
					//$('.select-all').prop('checked', false);
					
					$("#search-result-panel").removeClass('hidden');
				});
		},
		
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
				
				$('#dataTable').on('click', '.item-check', function(){
					if($(this).is(':checked')){
						if($('.item-check').length == $('.item-check:checked').length)
							$('#select-all').prop('checked', true);			
					}else
						$('#select-all').prop('checked', false);
				});
				
				$('#dataTable').on('click', '.item-check', function(e){
					//console.log($(this).parent().parent().find('input[type=radio]'));
					
					if($(this).is(':checked'))
						$(this).parent().parent().find('input[type=radio]').prop('disabled', false);
					else
						$(this).parent().parent().find('input[type=radio]').prop('disabled', true);
				});
				
				//split color search component
				$('#frmComponentSearch').on('submit', function(e){
					e.preventDefault(); //preventing default form submission 
					
					var validationClass= $(this).attr('class');
					
					//dialog to show error when no field is entered
					if(validationClass.indexOf('CPG') > -1 || validationClass.indexOf('RCG') > -1 
						|| validationClass.indexOf('BCG') > -1 || validationClass.indexOf('GSG') > -1 )
					{
						var validInputFlag = true;
						//scanning for atleast one value in one field
						$(this).find('input[type=text]').each(function(){
							validInputFlag = !!$(this).val().trim().length;
							
							return !validInputFlag;
						});
						
						if(!validInputFlag){
							$('#error-massege').html('Atleast one field is required');
							$('#errorBox').dialog("open");
						}else if($('#alreadyInGroup').length && $('#alreadyInGroup').val() == 'Y' && $('#groupId-search').val().trim().length > 0){
							$('#error-massege').html("Grouping# " + $('#groupId').val() + " already belongs to another group!");
							$('#errorBox').dialog("open");			
						}else{
							//$('.overlay_pageLoading').removeClass('hidden');
							
							if($('#groupType').val().trim() == 'RCG' || $('#groupType').val().trim() == 'BCG'){
								var config = {
									dfdChildrenUrl: app.URLFactory.getURL('groupSearchUrl'), 
									dfdChildrenParams: {resourceType: 'getChildRCGBCG'},
									dfdChildrenStyleColorParams: {resourceType: 'getChildRCGBCGCPGStyleChild'},
									dfdStyleColorTemplate: '#row-template-style-color',
									dataServiceFunc: _super.componentDataSearchService.bind(this)
								};
								
								var dtTableAjax = new app.DataTableComponentAjax(config, $('#frmComponentSearch').serialize());
								dtTableAjax.init(); //init data table
								
							}else{
								//method to get results to draw the non ajax data Table for specific group types
								_super.componentDataSearchService($('#frmComponentSearch').serialize())	
									.done(function(result){
										if(!result.length)
											return;
										
										var response = $.parseJSON(result);
										//console.log(response.componentList);
										if(response.componentList){
											//processing data table generation
											//passing only components
											var componentList = response.componentList;
											//deleting componentList to pass only header
											delete response.componentList;
											
											var dtTable = new app.DataTable();
											dtTable.setDataHeader(response);
											dtTable.setData(componentList);
											
											//$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
											
											dtTable.init();
										}
										
										
										$("#search-result-panel").removeClass('hidden');								
									});
								}
							}
						}else{
						
						if($('#styleOrinNo').val().trim() == '' && $('#vendorStyleNo').val().trim() == ''){
							$('#error-massege').html('Atleast one field is required');
							$('#errorBox').dialog("open");
							
						//dialog to show error when both fields are entered
						} else if ($('#styleOrinNo').val().trim() && $('#vendorStyleNo').val().trim()){
							$('#error-massege').html("You cannot search for both fields at once. Please only fill in one fields before continuing.");
							$('#errorBox').dialog("open");
						}else{
							$('.overlay_pageLoading').removeClass('hidden');
							
							//app.GroupFactory.searchSplitComponents($('#frmComponentSearch').serialize())
							_super.componentDataSearchService($('#frmComponentSearch').serialize())
								.done(function(result){
									if(!result.length)
										return;
									
									var response = $.parseJSON(result);
									//console.log(response.componentList);
									if(response.componentList){
										//processing data table generation
										//passing only components
										var componentList = response.componentList;
										//deleting componentList to pass only header
										delete response.componentList;
										
										var dtTable = new app.DataTable();
										dtTable.setDataHeader(response);
										dtTable.setData(componentList);
										
										//$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
										
										dtTable.init();
									}
									
									//displaying search page
									$("#search-result-panel").removeClass('hidden');
																	
								})/* .complete(function(){
									$('.overlay_pageLoading').addClass('hidden');
									$('.select-all').prop('checked',false);
								}); */
						}	
					}
				});
				
				
				
				$('#split-components').on('click',function(){
					/**
					* new rule updated on 06272016
					* if selected item is only one then the its corresponding radio will be checked
					* in other ways first selcted item will have default radio selected
					*/
					
					if($('.item-check:checked').length == 1){
						$('.item-check:checked').parent().parent().find('input[type=radio]').prop('checked', true);
					}
					
					if($('.item-check:checked').length < 1){
						$('#error-massege').html("Please select atleast one item.");
						$('#errorBox').dialog('open');
					}else if(!$('input[name="defaultColor"]:enabled:checked').length){
						$('#error-massege').html("Please select default component.");
						$('#errorBox').dialog('open');					
					}else{
						$('#dlgGroupCreate').dialog('open');
					}
				})
				
			}catch(ex){
				console.log(ex.message);
			}
		},
		
		init: function(){
			//this.attachControls();
			app.GroupLandingApp.handleAutoLogout();
			this.handleEvents();
		},
	}
})(jQuery, _);