;var app = app || {};

(function($, _){
	'use strict';
	
	app.DataTable = {
		
		searchParams: '',
		
		dataHeader: {},
		
		defaultConfig: {
			sortedColumn: 0,
			sortMethod: 'asc',
			recordLimit: 10,
		},
		
		columnAlias: [],
		
		data: [],

		curPage: 1,

		totalPages: 1,
		
		totalRecords: 0,

		template: _.template($('#row-template').html(), null,  {
			interpolate :  /\{\{\=(.+?)\}\}/g,
			evaluate: /\{\{(.+?)\}\}/g
		}),

		attachHandler: function(){
			var _super = this;
			
			$('#dataTable').on('click', '.parent-node-expand', function(){
				var nodeID = $(this).data('node-id');
				$('tr[data-parent-id=' + nodeID + ']').fadeIn('falst');
				$(this).removeClass('parent-node-expand').addClass('parent-node-collapse');
			});

			$('#dataTable').on('click', '.parent-node-collapse', function(){
				var nodeID = $(this).data('node-id');
				$('tr[data-parent-id=' + nodeID + ']').fadeOut('falst');
					$(this).removeClass('parent-node-collapse').addClass('parent-node-expand');
			});

			$('.record-limit').on('change', function(){
				$('.record-limit').val($(this).val()); //syncing all drop down values
				
				if(!_super.totalRecords)
					return;
				
				//console.log($(this).val());
				_super.curPage = 1;
				
				$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
				
				_super.generateDataTable($(this).val(), _super.curPage, _super.dataHeader.sortedColumn, _super.dataHeader.ascDescOrder);
			});
		},

		handlePagination: function(limit, jqArea){
			limit = (!isNaN(limit) || limit != 0) ? limit : 1;
			
			//var pages = Math.ceil(totalRecords / limit);
			this.totalPages = Math.ceil(this.totalRecords / limit);
			
			//updating pager text showing current record count of total record 
			if(this.totalRecords > 0){
				if(this.curPage == this.totalPages)
					$('.pagination-text').text('Showing ' + this.totalRecords +' of ' + this.totalRecords + ' records');
				else
					$('.pagination-text').text('Showing ' + (this.curPage * limit) +' of ' + this.totalRecords + ' records');
			}else
				$('.pagination-text').text('Showing 0 of 0 record');
			
			
			var _super = this;
			
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
		},

		generateDataTable: function(recordsPerPage, pageNumber, sortedColumn, ascDescOrder){
			//setting up default params if not provided.
			if(recordsPerPage === undefined){
				recordsPerPage = $('.record-limit').val() || 0;
				recordsPerPage = recordsPerPage ? recordsPerPage : this.defaultConfig.recordLimit;
			}
			
			pageNumber = pageNumber === undefined ? this.curPage : pageNumber;
			
			if(sortedColumn === undefined){
				sortedColumn = this.columnAlias[this.defaultConfig.sortedColumn] !== undefined ? this.columnAlias[this.defaultConfig.sortedColumn] : ''
			}
			
			ascDescOrder = ascDescOrder === undefined ? this.defaultConfig.sortMethod : ascDescOrder;
			
			//console.log(this.curPage);
			$('.overlay_pageLoading').removeClass('hidden'); //showing overlay
			var _super = this;
			this.fetchRecordSet({recordsPerPage: recordsPerPage, pageNumber: pageNumber, sortedColumn: sortedColumn, ascDescOrder: ascDescOrder})
				.done(function(result){
					var response = $.parseJSON(result);
					//console.log(response.componentList);
					if(response.mainList){
						//processing data table generation
						//passing only components
						var mainList = response.mainList;
						//deleting componentList to pass only header
						delete response.mainList;
						
						_super.dataHeader = response;
						_super.data = mainList;
						_super.totalRecords = response.totalRecordCount ? response.totalRecordCount : 0;
						
						//generating table and pagination
						_super.handleTableDraw();
					}
				})
				.complete(function(){
					$('#search-result-panel').removeClass('hidden');
					$('.overlay_pageLoading').addClass('hidden');
				});
		},
		
		fetchRecordSet: function(params){
			return app.GroupFactory.searchGroup(this.searchParams + '&' + this.serializeObj(params));
		},
		
		handleTableDraw: function(result){
			//generating table with template
			$('#row-container').html(this.template({data: this.data, dataHeader: this.dataHeader}));
			
			//generating pagination
			this.handlePagination(this.dataHeader.recordsPerPage, $('.paginator'));
		},
		
		serializeObj: function(obj){
			var str = "";
			for (var key in obj) {
				if (str != "") {
					str += "&";
				}
				str += key + "=" + encodeURIComponent(obj[key]);
			}
			
			return str
		},

		init: function(){
			this.generateDataTable();

			this.attachHandler();
		}
	};

})(jQuery, _);