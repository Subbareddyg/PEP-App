;var app = app || {};

(function($, _){
	'use strict';
	
	app.DataTable = {};
	
	var DataTable = function (config, jq, underscore){
		this.$ = jq !== undefined ? jq : $;
		this._ = underscore !== undefined ? underscore : _;
		
		this.config = {
			rowTemplateId: '#row-template',

			dtContainer: '#dataTable',
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
		
		setData: function(data){
			this.data = data;
		},
		
		template: function(data){
			return this.$(this.config.rowTemplateId).length ? this._.template($(this.config.rowTemplateId).html(), null,  {
				interpolate :  /\{\{\=(.+?)\}\}/g,
				evaluate: /\{\{(.+?)\}\}/g
			})(data) : null;
		},

		attachHandler: function(){
			var _super = this;
			
			_super.$(this.config.dtContainer).on('click', '.parent-node-expand', function(){
				var nodeID = _super.$(this).data('node-id');
				_super.$('tr[data-parent-id=' + nodeID + ']').fadeIn('falst');
				_super.$(this).removeClass('parent-node-expand').addClass('parent-node-collapse');
			});

			_super.$(this.config.dtContainer).on('click', '.parent-node-collapse', function(){
				var nodeID = _super.$(this).data('node-id');
				_super.$('tr[data-parent-id=' + nodeID + ']').fadeOut('falst');
					_super.$(this).removeClass('parent-node-collapse').addClass('parent-node-expand');
			});
			
			//handler to select all the items
			
			
			_super.$(this.config.dtContainer).on('click', '.select-all', function(e){
				
				if(_super.$(this).is(':checked')){
					
					console.log(_super.$(_super.config.dtContainer) + ' .item-check');
					
					_super.$(_super.config.dtContainer).find('.item-check').prop('checked', true);
					_super.$(_super.config.dtContainer).find('input[type="radio"]').prop('disabled', false);
				}else{
					_super.$(_super.config.dtContainer).find('.item-check').prop('checked', false);					
					if(_super.$(_super.config.dtContainer).find('input[type="radio"]').hasClass('trueDefult')){
						_super.$(_super.config.dtContainer).find('input[type="radio"]').prop('disabled', false);
					}else{
						_super.$(_super.config.dtContainer).find('input[type="radio"]').prop('disabled', true);
					}
				}
			});
			
			_super.$(this.config.dtContainer).on('click', '.item-check', function(){
				if(_super.$(this).is(':checked')){
					_super.$(this).parent().parent().find('input[type=radio]').prop('disabled', false);
					
					if(_super.$(_super.config.dtContainer).find('.item-check').length == 
						_super.$(_super.config.dtContainer).find('.item-check:checked').length){
						
						_super.$(_super.config.dtContainer).find('.select-all').prop('checked', true);	
					}
									
				}else{
					if(_super.$(_super.config.dtContainer).find('input[type="radio"]').hasClass('trueDefult')){
						_super.$(this).parent().parent().find('input[type=radio]').prop('disabled', false);
					}else{
						_super.$(this).parent().parent().find('input[type=radio]').prop('disabled', true);
					}
					
					_super.$(_super.config.dtContainer).find('.select-all').prop('checked', false);
				}	
			});
			
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

			_super.$(this.config.dtContainer).on('change', '.record-limit', function(){
				_super.$(_super.config.dtContainer).find('.record-limit').val($(this).val()); //syncing all drop down values
				
				if(!_super.totalRecords)
					return;
				
				//console.log($(this).val());
				_super.curPage = 1;
				
				_super.$(_super.config.dtContainer).find('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
				
				_super.generateDataTable();
			})
		},
		
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
				jqArea.twbsPagination({
					totalPages: _super.totalPages ? _super.totalPages : 1,
					visiblePages: 10,
					onPageClick: function (event, page) {
						//$('#page-content').text('Page ' + page);
						if(page != _super.curPage){
							_super.curPage = page;
							_super.generateDataTable(_super.dataHeader.recordsPerPage, page, _super.dataHeader.sortedColumn, _super.dataHeader.ascDescOrder);
						}
					}
				});
			}catch(ex){
				throw new Exception(ex.message);
			}
			
		},

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

		getData: function(limit, offset){
			if(offset == 1)
				return this.data.slice(offset -1, limit);
			else{
				var endIndex = offset * limit;
				return this.data.slice(endIndex - limit, endIndex);
			}	
		},
		
		clearSorting: function(){
			this.$(this.config.dtContainer).off('click', '.sortable');  //clearing previously set delegation for safety
			this.$(this.config.dtContainer).find('a.sortable').removeClass('sort-up sort-down');
			this.$(this.config.dtContainer).find('a.sortable').data('sorted-by', null);
		},

		init: function(){
			
		//	console.log(this);
			this.totalRecords = this._.size(this.data); //counting total records
			
			//clearing any sorting mechanism used to sort previsouly;
			this.clearSorting();
			
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