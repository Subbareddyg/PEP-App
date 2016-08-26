;var app = app || {};

(function($, _){
	'use strict';
	
	app.DataTable = {};
	
	var DataTable = function (config, jq, underscore){
		this.$ = jq !== undefined ? jq : $;
		this._ = underscore !== undefined ? underscore : _;
		
		this.config = {
			rowTemplateId: '#row-template',
			
			dfdRowTemplateId: '#row-template-group-children',

			dtContainer: '#dataTable',
			
			dfdChildrenParams: {},
			
			dfdChildrenUrl: null,
			
		};
		
		this.data = [];
		
		this.dataHeader = {};
		
		this.curPage = 1;

		this.totalPages = 1;
		
		this.totalRecords = 0;
		
		
		//merging config
		this.config = _mergeConfig.call(this, this.config, config);
	}
	
	
	DataTable.prototype = {
		constructor: DataTable,

		setDataHeader: function(data){
			this.dataHeader = data;
		},
		
		//setter
		setData: function(data){
			this.data = data;
		},
		
		getTotalRecords: function(){
			return this.totalRecords;
		},
		
		template: function(data){
			return this.$(this.config.rowTemplateId).length ? this._.template($(this.config.rowTemplateId).html(), null,  {
				interpolate :  /\{\{\=(.+?)\}\}/g,
				evaluate: /\{\{(.+?)\}\}/g
			})(data) : null;
		},
		
		dfdTemplate: function(data){
			return this.$(this.config.dfdRowTemplateId).length ? this._.template($(this.config.dfdRowTemplateId).html(), null,  {
				interpolate :  /\{\{\=(.+?)\}\}/g,
				evaluate: /\{\{(.+?)\}\}/g
			})(data) : null;
		},
		
		//method to call the impl service which returns dfd children via an ajax call
		getDfdChildren: function(parentId, disableFlag){
			if(this.config.dfdChildrenUrl && this.config.dfdChildrenParams){
				this.config.dfdChildrenParams['groupId'] = parentId;
				this.config.dfdChildrenParams['disableFlag'] = disableFlag;
				return this.$.get(this.config.dfdChildrenUrl, this.config.dfdChildrenParams)
					.error(function(jqXHR, testStatus, errorThrown){
						console.log(jqXHR.status + " : " + testStatus  + " : " + errorThrown);
					});
			}else
				throw new Error('Required configuration is missing to fetch children from remote server');
		},
		
		handleSorting: function(e, _super){
			var sortColumn = _super.$(this).data('sort-column') || null;
			var sortMethod = _super.$(this).data('sorted-by') || 'asc';
			if(sortColumn){
				var sortedData = _super._.sortBy(_super.data, sortColumn);
				var sortedDispClass = 'sort-up';
				
				if(sortMethod == 'desc'){
					sortedData = sortedData.reverse();
					sortedDispClass = 'sort-down';
					sortMethod = 'asc';
				}else{
					sortMethod = 'desc';
				}
				
				//resetting and regenerating the pagination and data display
				_super.data = sortedData;
				
				_super.curPage = 1;
				
				_super.$(_super.config.dtContainer).find('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
				
				_super.generateDataTable();
				
				_super.$(_super.config.dtContainer).find('a.sortable').removeClass('sort-up sort-down');
				
				_super.$(this).addClass(sortedDispClass); //removing sorting dir indicator 
				
				_super.$(this).data('sorted-by', sortMethod); //adding necessary sorting dir indicator
			}
				
		},
		
		handleRecordLimit: function(e, _super){
			_super.$(_super.config.dtContainer).find('.record-limit').val($(this).val()); //syncing all drop down values
			_super.$(_super.config.dtContainer).find('.select-all').prop('checked', false); //unchecking select all checkbox if previously checked
			
			if(!_super.totalRecords)
				return;
			
			//console.log($(this).val());
			_super.curPage = 1;
			
			_super.$(_super.config.dtContainer).find('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
			
			_super.generateDataTable();
		},
		
		//central method to attach all necessary delegate or handlers for various events
		attachHandler: function(){
			var _super = this;
			
			//delegate to expand already hidden immediate children 
			_super.$(this.config.dtContainer).on('click', '.parent-node-expand', function(){
				var nodeID = _super.$(this).data('node-id');
				_super.$('tr[data-parent-id=' + nodeID + ']').fadeIn('fast');
				_super.$(this).removeClass('parent-node-expand').addClass('parent-node-collapse');
			});
			
			//delegate to collapse already hidden immediate children
			_super.$(this.config.dtContainer).on('click', '.parent-node-collapse', function(){
				var nodeID = _super.$(this).data('node-id');
				_super.$('tr[data-parent-id=' + nodeID + ']').fadeOut('fast');
				_super.$(this).removeClass('parent-node-collapse').addClass('parent-node-expand');
			});
			
			//delegate to handle and fetch children via deferred ajax call
			_super.$(this.config.dtContainer).on('click', '.parent-node-expand-ajax', function(e, eventInfo){
				var _that = _super.$(this);
				var entryType = _that.data('entry-type') || '';
				var parentNodeId = _that.data('parentnode-id');
				var childrenDisableFlag = _that.data('children-disable') || false;
				var nodeID = _that.data('node-id');
				var level = _that.data('level');
				var totChildrenCount = _super.$(_super.config.dtContainer)
					.find('input[type=checkbox][data-parent-group ^= ' + parentNodeId + '_]')
					.length;
				
				//changing display state to show working to get dfd children
				_that.removeClass('parent-node-expand-ajax').addClass('parent-node-expanding-ajax');
				
				if(totChildrenCount){
					if(eventInfo !== undefined && eventInfo.doCheckAll == true){
						_super.$(_super.config.dtContainer)
						.find('input[type=checkbox][data-parent-group ^= ' + parentNodeId + '_]')
						.not(':disabled')
						.prop('checked', true);
					}
					
					if(eventInfo === undefined || eventInfo.doShow == true){
						_super.$('tr.direct-' + nodeID).fadeIn('fast');
						_super.$('tr[data-parent-id=' + nodeID + ']').fadeIn('fast');
						_that.removeClass('parent-node-expanding-ajax').addClass('parent-node-collapse-ajax');
					}
					
					/**
					* counting enabled children from all the children
					* if all the items are disabled making parent group check box disabled itself
					*/
					var totChildren = _super.$(_super.config.dtContainer)
						.find('input[type=checkbox][data-parent-group ^= ' + parentNodeId + '_]');
					var enabledChildren = totChildren.not(':disabled');
					
					
					if(eventInfo === undefined && !enabledChildren.length){console.log()
						_that.parent().prev('td').find('.groups').prop('checked', false).prop('disabled', true);
					}
					
					return;
				}
				
				_super.getDfdChildren(_that.data('parentnode-id'), childrenDisableFlag)
					.done(function(result){
						//console.log(result);
						if(!result.length /* || (result.charAt(0) != '[' || result.charAt(0) != '{')*/){
							console.warn('Invalid Response returned from server');
							return;
						}
						
						var response = _super.$.parseJSON(result);
						
						if(response.componentList){
							var componentList = response.componentList;
							//deleting componentList to pass only header
							delete response.componentList;
							
							//generating children and appending
							var html = _super.dfdTemplate({data: componentList, dataHeader: response, parentNode: nodeID, level: level, doShow: (eventInfo === undefined || eventInfo.doShow) ? true: false, groupChildrenDisableFlag: (entryType.length && app.Global.constants.BCGEntryTypes.indexOf(entryType) < 0) ? true : false});
							
							_that.parent().parent().after(html);
							
							/**
							* counting enabled children from all the children
							* if all the items are disabled making parent group check box disabled itself
							*/
							var totChildren = _super.$(_super.config.dtContainer)
								.find('input[type=checkbox][data-parent-group ^= ' + parentNodeId + '_]');
							var enabledChildren = totChildren.not(':disabled');
							
							if(eventInfo === undefined && !enabledChildren.length){
								_that.parent().prev('td').find('.groups').prop('checked', false).prop('disabled', true);
							}
							
							if(eventInfo !== undefined && eventInfo.doCheckAll == true){
								_super.$(_super.config.dtContainer)
								.find('input[type=checkbox][data-parent-group ^= ' + parentNodeId + '_]')
								.not(':disabled')
								.prop('checked', true);
							}
							
							//if the parent entry type is not in the allowed list then disable all children
							if(entryType.length && app.Global.constants.BCGEntryTypes.indexOf(entryType) < 0){
								totChildren.prop('disabled', true);
							}
							
							if(eventInfo === undefined || eventInfo.doShow == true){
								_super.$('tr[data-parent-id=' + nodeID + ']').fadeIn('fast');
								_that.removeClass('parent-node-expanding-ajax').addClass('parent-node-collapse-ajax');
							}else{
								_that.removeClass('parent-node-expanding-ajax').addClass('parent-node-expand-ajax');
							}					
						}else{
							console.warn('Invalid Response JSON returned from server');
						}
					});
			});
			
			//delegate to handle and fetch children via deferred ajax call
			_super.$(this.config.dtContainer).on('click', '.parent-node-collapse-ajax', function(){
				var _that = _super.$(this);
				var nodeID = _super.$(_that).data('node-id');
				
				//getting all 3rd level nodes if any and removing after hiding
				_super.$('tr.dfd-children-'+nodeID).each(function(){
					var parentNodeId = _super.$(this).find('.icon-tree').data('node-id');
					_super.$('tr.dfd-children-'+parentNodeId).fadeOut('fast', function(){
						_super.$('tr.dfd-children-'+parentNodeId).remove();
					});
					//console.log(_super.$('tr.dfd-children-'+parentNodeId));
				});
				
				//getting all 2nd level nodes if any and removing after hiding
				_super.$('tr.dfd-children-'+nodeID).fadeOut('fast', function(){
					_that.removeClass('parent-node-collapse-ajax').addClass('parent-node-expand-ajax');
					_super.$('tr.dfd-children-'+nodeID).remove();
				});
				
				_that.parent().prev('td').find('input.item-check').prop('checked', false);
			});
			
			//delegate to select all the items						
			_super.$(this.config.dtContainer).on('click', '.select-all', function(e){
				
				if(_super.$(this).is(':checked')){				
					_super.$(_super.config.dtContainer).find('.item-check:enabled').prop('checked', true);
					_super.$(_super.config.dtContainer).find('.item-check:enabled').parent().parent().find('input[type="radio"]').prop('disabled', false);
				}else{
					_super.$(_super.config.dtContainer).find('.item-check:enabled').prop('checked', false);					
					if(_super.$(_super.config.dtContainer).find('input[type="radio"]').hasClass('trueDefult')){
						_super.$(_super.config.dtContainer).find('input[type="radio"]').prop('disabled', false);
					}else{
						_super.$(_super.config.dtContainer).find('input[type="radio"]').prop('checked', false).prop('disabled', true);
					}
				}
			});
			
			//delegate to check and take necessary action for each indv check box
			_super.$(this.config.dtContainer).on('click', '.item-check', function(){
				if(_super.$(this).is(':checked')){
					_super.$(this).parent().parent().find('input[type=radio]').prop('disabled', false);
					
					/**
					  -- special kind of logic for dfd or direct hierarchical children. 
					  -- when any child is checked parent will be automatically checked
					*/
					if(_super.$(this).data('item-type') == 'SC'){
						//checking parent checkbox
						$('input.item-check[data-chknode-id=' + _super.$(this).data('chkparent-id') + ']').prop('checked', true);
					}else if(_super.$(this).data('item-type') == 'S'){
						$('input.item-check[data-chkparent-id=' + _super.$(this).data('chknode-id') + ']:enabled').prop('checked', true);
					}
					
					if(_super.$(_super.config.dtContainer).find('.item-check:enabled').length == 
						_super.$(_super.config.dtContainer).find('.item-check:enabled:checked').length){
						
						_super.$(_super.config.dtContainer).find('.select-all').prop('checked', true);	
					}
				}else{
					if(_super.$(_super.config.dtContainer).find('input[type="radio"]').hasClass('trueDefult')){
						_super.$(this).parent().parent().find('input[type=radio]').prop('disabled', false);
					}else{
						_super.$(this).parent().parent().find('input[type=radio]').prop('checked', false).prop('disabled', true);
					}
					
					/**
					  -- special kind of logic for dfd or direct hierarchical children. 
					  -- when any child is checked parent will be automatically checked
					*/
					
					if(_super.$(this).data('item-type') == 'SC'){
						//checking parent checkbox
						if(!$('input.item-check[data-chkparent-id=' + _super.$(this).data('chkparent-id') + ']:checked').length)
							$('input.item-check[data-chknode-id=' + _super.$(this).data('chkparent-id') + ']').prop('checked', false);
					}else if(_super.$(this).data('item-type') == 'S'){
						$('input.item-check[data-chkparent-id=' + _super.$(this).data('chknode-id') + ']').prop('checked', false);
					}
					
					_super.$(_super.config.dtContainer).find('.select-all').prop('checked', false);
				}
			});
			
			//delegate to handle automatic parent style select/deselect based on style color selection for group children
			_super.$(this.config.dtContainer).on('click', '.group-style-color', function(e){
				var parentGroupID = _super.$(this).data('parent-group');
				var parentStyleORIN = _super.$(this).data('parent-style');
				
				if(_super.$(this).is(':checked')){
					_super.$(_super.config.dtContainer).find('tr.dfd-children-' + parentGroupID).find('input[type=checkbox][value=' + parentStyleORIN + ']')
						.prop('checked', true);
				}else{
					if(!_super.$(_super.config.dtContainer).find('tr.dfd-children-' + parentGroupID).find('input[type=checkbox][data-parent-style=' + parentStyleORIN + ']:checked').length)
						_super.$(_super.config.dtContainer).find('tr.dfd-children-' + parentGroupID).find('input[type=checkbox][value=' + parentStyleORIN + ']')
						.prop('checked', false);
				}
				
				_super.$(this).trigger('group.childrenSelected', {parentGroupID: parentGroupID, checked: _super.$(this).is(':checked')});
			});
			
			//delegate to handle automatic parent style select/deselect based on style color selection for group children
			_super.$(this.config.dtContainer).on('click', '.group-style', function(e){
				var parentGroupID = _super.$(this).data('parent-group');
				var styleOrin = _super.$(this).val();
				
				if(_super.$(this).is(':checked')){
					_super.$(_super.config.dtContainer).find('tr.dfd-children-' + parentGroupID)
						.find('input[type=checkbox][data-parent-style=' + styleOrin + ']')
						.not(':disabled')
						.prop('checked', true);
				}else{
					_super.$(_super.config.dtContainer).find('tr.dfd-children-' + parentGroupID)
						.find('input[type=checkbox][data-parent-style=' + styleOrin + ']')
						.prop('checked', false);
				}
				
				_super.$(this).trigger('group.childrenSelected', {parentGroupID: parentGroupID, checked: _super.$(this).is(':checked')});
			});
			
			_super.$(this.config.dtContainer).on('group.childrenSelected', '.group-style, .group-style-color', function(e, info){
				//console.log(info);
				if(info && info.parentGroupID && info.parentGroupID.length){
					var parentGroupId = info.parentGroupID.indexOf('_') >=0 ? info.parentGroupID.split('_')[0] : null;
					
					if(parentGroupId && info.checked === true){
						_super.$(_super.config.dtContainer).find('input[type=checkbox][value=' + parentGroupId + ']')
							.not(':disabled')
							.prop('checked', info.checked);
					}else if(parentGroupId && info.checked === false){
						if(!_super.$(_super.config.dtContainer).find('input[type=checkbox][data-parent-group ^=' +  parentGroupId + '_]:checked').length)
							_super.$(_super.config.dtContainer).find('input[type=checkbox][value=' + parentGroupId + ']').prop('checked', false);
					}
				}
			});
			
			_super.$(this.config.dtContainer).on('click', '.groups', function(e){
				var groupId = _super.$(this).val() || '';
				var domItem = _super.$(this).parent().parent().find('div.icon-tree');
				
				
				var totChildren = _super.$(_super.config.dtContainer)
						.find('input[type=checkbox][data-parent-group ^= ' + groupId + '_]');
				var enabledChildren = totChildren.not(':disabled');
				
				if(_super.$(this).is(':checked')){
					if(domItem.hasClass('parent-node-expand-ajax')){
						domItem.trigger('click', {doShow: false, doCheckAll: true});
					}else if(domItem.hasClass('parent-node-collapse-ajax')){
						enabledChildren.prop('checked', true);
					}	
				}else{
					totChildren.prop('checked', false);	
				}
			})
			
			//delegate to handle sorting by attrubutes
			_super.$(this.config.dtContainer).on('click', '.sortable', function(e){
				_super.handleSorting.call(this, e, _super);
			});
			
			//delegate to handle record limit per page
			_super.$(this.config.dtContainer).on('change', '.record-limit', function(e){
				_super.handleRecordLimit.call(this, e, _super);
			});
		},
		
		//method pagination controls
		handlePagination: function(limit, jqArea){
			limit = (!isNaN(limit) || limit != 0) ? limit : 1;
			
			var _super = this;
			
			_super.totalPages = Math.ceil(this.totalRecords / limit);
			
			//updating pager text showing current record count of total record 
			if(this.totalRecords > 0){
				if(this.curPage == this.totalPages)
					_super.$(this.config.dtContainer).find('.pagination-text').text('Showing ' + this.totalRecords +' of ' + this.totalRecords + ' records');
				else
					_super.$(this.config.dtContainer).find('.pagination-text').text('Showing ' + (this.curPage * limit) +' of ' + this.totalRecords + ' records');
			}else
				_super.$(this.config.dtContainer).find('.pagination-text').text('Showing 0 of 0 record');
			
			//constructing pagination
			try{
				jqArea.twbsPagination({
					totalPages: _super.totalPages ? _super.totalPages : 1,
					visiblePages: 10,
					onPageClick: function (event, page) {
						if(page != _super.curPage){
							_super.curPage = page;
							_super.generateDataTable(_super.dataHeader.recordsPerPage, page, _super.dataHeader.sortedColumn, _super.dataHeader.ascDescOrder);
						}
					}
				});
			}catch(ex){
				throw new Error(ex.message);
			}
			
		},
		
		//central method to draw entire control
		generateDataTable: function(){
			//getting current record limit
			var limit = limit ? limit : (this.$(this.config.dtContainer).find('.record-limit').val() || 1);
			//getting current data offset
			var dataOffset = this.getData(parseInt(limit), this.curPage);
			
			//console.log(this.template({data: dataOffset, dataHeader: this.dataHeader}));
			//console.log(this.$(this.config.dtContainer).length);
			
			this.$(this.config.dtContainer).find('.row-container').html(this.template({data: dataOffset, dataHeader: this.dataHeader}));

			this.handlePagination(limit, this.$(this.config.dtContainer).find('.paginator'));
		},
		
		//method to get sliced data as per selected page and per page limit
		getData: function(limit, offset){
			if(offset == 1)
				return this.data.slice(offset -1, limit);
			else{
				var endIndex = offset * limit;
				return this.data.slice(endIndex - limit, endIndex);
			}	
		},
		
		//housekeeper to release and destroy delegation when regenrating or destroying data table
		destroyDelegates: function(){
			this.$(this.config.dtContainer).find('.select-all').prop('checked', false); //unchecking select all checkbox if previously checked
			this.$(this.config.dtContainer).off();  //clearing previously set delegation for duplication
			/* this.$(this.config.dtContainer).off('click', '.sortable');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).off('click', '.parent-node-expand-ajax');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).off('click', '.parent-node-collapse-ajax');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).off('blur', '.tree');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).off('blur', '.tree'); */
			this.$(this.config.dtContainer).find('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
			this.$(this.config.dtContainer).find('.paginator').find('li').off(); //clearing all pagination handlers
			this.$(this.config.dtContainer).find('.paginator').off(); //clearing all pagination handlers
			this.$(this.config.dtContainer).find('a.sortable').removeClass('sort-up sort-down');
			this.$(this.config.dtContainer).find('a.sortable').data('sorted-by', null);
			
		},
		
		//bootstrapper method
		init: function(){
			this.totalRecords = this._.size(this.data); //counting total records
			
			//console.log(this.totalRecords);
			//console.log(this);
			
			//housekeeping if any required for previous instance
			this.destroyDelegates();
			
			this.generateDataTable();

			this.attachHandler();
		}
	};
	
	//private method to merge default and user supplied configs
	function _mergeConfig(config1, config2){ //must be called with App.DataTable context.
		return this.$.extend(config1, config2);
	}
	
	app.DataTable = DataTable;
	
})(jQuery, _);