;var app = app || {};

(function($, _){
	'use strict';
	
	app.DataTable = {
		dataHeader: {},
		
		data: [],

		curPage: 1,

		totalPages: 1,

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

			$('.pagination-container').on('click', '.page', function(){
				var page = parseInt($(this).text());
				//console.log(page);
				if(page != _super.curPage){
					_super.curPage = page;
					_super.generateDataTable();
				}
			});

			$('.pagination-container').on('click', '.prev', function(){
				if(_super.curPage > 1){
					_super.curPage -= 1;
					_super.generateDataTable();
				}
			});

			$('.pagination-container').on('click', '.next', function(){
				if(_super.curPage < _super.totalPages){
					_super.curPage += 1;
					_super.generateDataTable();
				}
			});

			$('.record-limit').on('change', function(){
				//console.log($(this).val());
				$('.record-limit').val($(this).val());
				_super.curPage = 1;
				_super.generateDataTable();
			})
		},

		handlePagination: function(limit, jqArea){
			limit = (!isNaN(limit) || limit != 0) ? limit : 1;

			//getting data count
			var totalRecords = _.size(this.data);
			
			//var pages = Math.ceil(totalRecords / limit);
			this.totalPages = Math.ceil(totalRecords / limit);

			//generating pages
			var paginationContent = '';

			//if its first page then disable the previous button pager
			if(this.curPage == 1)
				paginationContent += '<li class="disabled"><a href="#" aria-label="Previous" class="prev"><span aria-hidden="true">&laquo;</span></a></li>';
			else
				paginationContent += '<li><a href="#" aria-label="Previous" class="prev"><span aria-hidden="true">&laquo;</span></a></li>';

			for(var i = 1; i <= this.totalPages; i++){
				if(i == this.curPage)
					paginationContent += '<li><a href="#" class="page active">' + i + '</a></li>';
				else
					paginationContent += '<li><a href="#" class="page">' + i + '</a></li>';
			}

			//if its last page then disable the next button pager
			if(this.curPage == this.totalPages)
				paginationContent += '<li class="disabled"><a href="#" aria-label="Next" class="next"><span aria-hidden="true">&raquo;</span></a></li>';
			else
				paginationContent += '<li><a href="#" aria-label="Next" class="next"><span aria-hidden="true">&raquo;</span></a></li>';


			/* console.log(paginationContent);
			console.log(pages);
			console.log(totalRecords);
			console.log(jqArea);*/

			jqArea.html(paginationContent);
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

		init: function(){
			this.generateDataTable();

			this.attachHandler();
		}
	};

})(jQuery, _);