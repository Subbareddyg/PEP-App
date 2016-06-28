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
		getDfdChildren: function(parentId){
			if(this.config.dfdChildrenUrl && this.config.dfdChildrenParams){
				this.config.dfdChildrenParams['groupId'] = parentId;
				return this.$.get(this.config.dfdChildrenUrl, this.config.dfdChildrenParams)
					.error(function(jqXHR, testStatus, errorThrown){
						console.log(jqXHR.status + " : " + testStatus  + " : " + errorThrown);
					});
			}else
				throw new Error('Required configuration is missing to fetch children from remote server');
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
			_super.$(this.config.dtContainer).on('click', '.parent-node-expand-ajax', function(){
				var _that = _super.$(this);
				
				//changing display state to show working to get dfd children
				_that.removeClass('parent-node-expand-ajax').addClass('parent-node-expanding-ajax');
				
				_super.getDfdChildren(_that.data('parentnode-id'))
					.done(function(result){
						//console.log(result);
						if(!result.length /* || (result.charAt(0) != '[' || result.charAt(0) != '{')*/){
							console.warn('Invalid Response returned from server');
							return;
						}
						
						var response = _super.$.parseJSON(result);
						
						if(response.componentList){
							var nodeID = _that.data('node-id');
							var level = _that.data('level');
							
							_super.$('tr[data-parent-id=' + nodeID + ']').fadeIn('fast');
							_that.removeClass('parent-node-expanding-ajax').addClass('parent-node-collapse-ajax');
							
							var componentList = response.componentList;
							//deleting componentList to pass only header
							delete response.componentList;
							
							//generating children and appending
							var html = _super.dfdTemplate({data: componentList, dataHeader: response, parentNode: nodeID, level: level});
							_that.parent().parent().after(html);
												
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
			});
			
			//delegate to select all the items						
			_super.$(this.config.dtContainer).on('click', '.select-all', function(e){
				
				if(_super.$(this).is(':checked')){				
					_super.$(_super.config.dtContainer).find('.item-check:enabled').prop('checked', true);
					_super.$(_super.config.dtContainer).find('input[type="radio"]').prop('disabled', false);
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
						$('input.item-check[data-chkparent-id=' + _super.$(this).data('chknode-id') + ']').prop('checked', true);
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
			
			//delegate to handle sorting by attrubutes
			_super.$(this.config.dtContainer).on('click', '.sortable', function(){
				
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
					
			});
			
			//delegate to handle record limit per page
			_super.$(this.config.dtContainer).on('change', '.record-limit', function(){
				_super.$(_super.config.dtContainer).find('.record-limit').val($(this).val()); //syncing all drop down values
				_super.$(_super.config.dtContainer).find('.select-all').prop('checked', false); //unchecking select all checkbox if previously checked
				
				if(!_super.totalRecords)
					return;
				
				//console.log($(this).val());
				_super.curPage = 1;
				
				_super.$(_super.config.dtContainer).find('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
				
				_super.generateDataTable();
			});
			
			//delegate to handle priority number validation
			_super.$(this.config.dtContainer).on('keyup', '.tree', function(){
				if(!/^[0-9]{1,}$/.test(_super.$(this).val().trim())){
					_super.$(this).val('');
				}
			});
			
			//delegate to handle priority number validation
			_super.$(this.config.dtContainer).on('blur', '.tree', function(){
				if(_super.$(this).val().trim() == 0 || _super.$(this).val().trim() > _super.totalRecords){
					if(_super.totalRecords == 1)
						alert('Priority should be only 1');
					else
						alert('Priority should have to be between 1 to ' + _super.totalRecords);
					
					_super.$(this).val('');
				}
			});
		},
		
		//method pagination controls
		handlePagination: function(limit, jqArea){
			limit = (!isNaN(limit) || limit != 0) ? limit : 1;
			
			var _super = this;
			
			//var pages = Math.ceil(totalRecords / limit);
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
				//console.log('Generating totalpages: ' + _super.totalPages);
				jqArea.twbsPagination({
					totalPages: _super.totalPages ? _super.totalPages : 1,
					visiblePages: 10,
					onPageClick: function (event, page) {
						//$('#page-content').text('Page ' + page);
						//console.log(page);
						//console.log(_super.curPage);
						
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
			this.$(this.config.dtContainer).off();  //clearing previously set delegation for duplication
			/* this.$(this.config.dtContainer).off('click', '.sortable');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).off('click', '.parent-node-expand-ajax');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).off('click', '.parent-node-collapse-ajax');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).off('blur', '.tree');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).off('blur', '.tree'); */
			//this.$(this.config.dtContainer).find('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
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