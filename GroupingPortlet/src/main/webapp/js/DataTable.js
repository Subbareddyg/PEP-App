;var app = app || {};

(function($, _){
	'use strict';
	
	app.DataTable = {
		dataHeader: {},
		
		data: [],

		curPage: 1,

		totalPages: 1,
		
		totalRecords: 0,

		template: $('#row-template').length ? _.template($('#row-template').html(), null,  {
			interpolate :  /\{\{\=(.+?)\}\}/g,
			evaluate: /\{\{(.+?)\}\}/g
		}) : null,

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
			
			$('#dataTable').on('click', '.sortable', function(){
				
				var sortColumn = $(this).data('sort-column') || null;
				var sortMethod = $(this).data('sorted-by') || 'asc';
				
				//console.log(sortColumn);
				//console.log(sortMethod);
				//console.log(_super.data);
				
				if(sortColumn){
					var sortedData = _.sortBy(_super.data, sortColumn);
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
					
					//console.log(_super.data);
					//console.log(sortColumn);
					//console.log(sortMethod);
					
					_super.curPage = 1;
					
					$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
					
					_super.generateDataTable();
					
					$('a.sortable').removeClass('sort-up sort-down');
					
					$(this).addClass(sortedDispClass); //removing sorting dir indicator 
					
					$(this).data('sorted-by', sortMethod); //adding necessary sorting dir indicator
				}
					
			});

			$('.record-limit').on('change', function(){
				$('.record-limit').val($(this).val()); //syncing all drop down values
				
				if(!_super.totalRecords)
					return;
				
				//console.log($(this).val());
				_super.curPage = 1;
				
				$('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
				
				_super.generateDataTable();
			})
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

		generateDataTable: function(){
			//getting current record limit
			var limit = limit ? limit : ($('.record-limit').val() || 1);
			//console.log(this.curPage);
			var dataOffset = this.getData(parseInt(limit), this.curPage);
			$('#row-container').html(this.template({data: dataOffset, dataHeader: this.dataHeader}));
			
			//$('#row-container').html(this.template({data: this.data}));

			this.handlePagination(limit, $('.paginator'));
		},

		getData: function(limit, offset){
			if(offset == 1)
				return this.data.slice(offset -1, limit);
			else{
				var endIndex = offset * limit;
				/* console.log(limit);
				console.log(offset);
				console.log(endIndex); */
				return this.data.slice(endIndex - limit, endIndex);
			}	
		},
		
		clearSorting: function(){
			$('#dataTable').off('click', '.sortable');
			$('a.sortable').removeClass('sort-up sort-down');
			$('a.sortable').data('sorted-by', null);
		},

		init: function(){
			this.totalRecords = _.size(this.data); //counting total records
			
			//clearing any sorting mechanism used to sort previsouly;
			this.clearSorting();
			
			this.generateDataTable();

			this.attachHandler();
		}
	};

})(jQuery, _);