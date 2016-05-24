var app = app || {};

;(function($){
	'use strict';
	app.GroupLandingApp = {
		urlCollection: {SCGUrl: '', SSGUrl: '',  searchUrl: '', },
		
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
						},
					})
				);
				
				//attching date pickers
				$( "#startDate, #endDate" ).datepicker({
					showOn: "button",
					buttonImage: app.Global.defaults.contextPath + "/img/iconCalendar.gif",
					buttonImageOnly: false,
					buttonText: ""
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
							$('#dlgGroupCreate').dialog( "option", "minHeight", 437 );
							$('#dlgGroupCreate').dialog( "option", "height", 437 );
							$('#groupTypeDropDown option[value="' +groupType +'"]').prop('selected', true);
							$('#dlgGroupCreate').dialog('open');
							break;
						default:
							$('#dlgGroupCreate').dialog( "option", "height", 370 );
							$('#groupTypeDropDown option[value="' +groupType +'"]').prop('selected', true);
							$('#dlgGroupCreate').dialog('open');
					}
				});
				
				//close group creation dlg-custom
				$('#closeGrpDlg').on('click', function(e){
					$('#createGroupForm')[0].reset();
					$('#dlgGroupCreate').dialog('close');
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
						app.GroupFactory.searchGroup(searchFiledsValue);
					}catch(ex){
						console.log(ex.message);
					}
					
					
				});
				
				//group create button action
				$('#btnCreateGroup').on('click', function(e){
					if($('#createGroupForm')[0].checkValidity()){
						e.preventDefault();  //preventing default form submission 
					
						var createFormValues = $('#createGroupForm').serialize();
					
						try{
							app.GroupFactory.createGroup(createFormValues)
								.done(function(result){
									//console.log(result);
									var resultJSON = $.parseJSON(result);
									console.log(resultJSON);
								
								_super.handleGroupCreationResponse(resultJSON, $('#groupType').val());
									
									//redirecting to the add component page when group creation is successful
									//if(resultJSON.groupCreationStatus && resultJSON.groupCreationStatus == app.Global.constants.groupStatus.GROUP_CREATED)
										//window.location.href = app.GroupLandingApp.urlCollection.addComponentUrl;
								}).error(function(){
									$('#group-creation-messages').html(_super.buildMessage('Group Creation Error', 'error'));
								});
								
						}catch(ex){
							console.log(ex.message);
						}
					}
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
			var highlightClass = dlgType == 'error' ? 'ui-state-error ui-custom-message-styling-error' : 'ui-state-highlight ui-custom-message-styling-info';
			
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
						message = this.buildMessage('Group Created Successfully', 'success');
						errorFlag = false;
						break;
					case app.Global.constants.groupStatus.GROUP_NOT_CREATED:
						message = this.buildMessage('Group Creation Unsuccessful', 'error');
						errorFlag = true;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_COMPONENT_SCG:
						message = this.buildMessage('Split Color Group Created and Component Added Successfully', 'success');
						errorFlag = false;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_OUT_COMPONENT_SCG:
						message = this.buildMessage('Split Color Group Created but Component add was Unsuccessful', 'error');
						errorFlag = true;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_COMPONENT_SSG:
						message = this.buildMessage('Split SKU Group Created and Component Added Successfully', 'success');
						errorFlag = false;
						break;
					case app.Global.constants.groupStatus.GROUP_CREATED_WITH_OUT_COMPONENT_SSG:
						message = this.buildMessage('Split SKU Group Created but Component Add was Unsuccessful', 'error');
						errorFlag = true;
						break;
					default:
						console.log('here');
						console.log(app.Global.constants.groupStatus.GROUP_NOT_CREATED);
						console.log(app.Global.constants.groupStatus.GROUP_NOT_CREATED);
				}
			}
			
			if((!errorFlag) && (groupType != 'SCG' || groupType != 'SSG')){
				window.location.href = app.GroupLandingApp.urlCollection.addComponentUrl;
			}else
				$('#group-creation-messages').html(message);
		},
		
		init: function(){
			this.attachControls();
			this.handleEvents();
		},
	};
	
	app.SKUGroupLanding = {
		
		handleEvents: function(){
			try{
				var _super = this;
				
				//handler to select all the items
				$('#select-all').on('click', function(e){
					if($(this).is(':checked')){
						$('.item-check').prop('checked', true);
					}else{
						$('.item-check').prop('checked', false);
					}
				});
				
				$('.item-check').on('click', function(){
					if($(this).is(':checked')){
						if($('.item-check').length == $('.item-check:checked').length)
							$('#select-all').prop('checked', true);			
					}else
						$('#select-all').prop('checked', false);
				});
				
				//split color search component
				$('#search-components').on('click', function(e){
					if($('#VendorStyleNum').val().trim()=='' && $('#StyleNum').val().trim()==''){
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
							
							} else if ($('#VendorStyleNum').val().trim() && $('#StyleNum').val().trim()){
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
								$("#search-result-panel").removeClass('hidden');
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
})(jQuery);