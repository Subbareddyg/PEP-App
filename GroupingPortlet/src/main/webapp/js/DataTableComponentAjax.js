var app = app || {};

(function($, _){
	'use strict';
	
	app.DataTableComponentAjax = {};
	
	var DataTableComponentAjax = function (config, searchParams, jq, underscore){
		app.DataTable.call(this, config, jq, underscore); //calling super constructor
		
		
		this.searchParams = searchParams;
		
		//extending parent default config with specific to this type
		this.config = _mergeConfig.call(this, {sortedColumn: 0, sortMethod: 'asc', recordLimit: 10, }, this.config);
	}
	
	//inherting base functionality from non ajax DataTable
	DataTableComponentAjax.prototype = Object.create(app.DataTable.prototype);
	
	//declaring constructor specific to this inherited type
	DataTableComponentAjax.prototype.constructor = DataTableComponentAjax;
	
	//method to get data via ajax call based on various params and limit
	DataTableComponentAjax.prototype.fetchRecordSet = function(params){	
		var _super = this;
		return this.config.dataServiceFunc(this.searchParams !== undefined ? (this.searchParams + '&' + _serializeObj(params)) : _serializeObj(params))
			.done(function(result){
				if(!result.length){
					console.warn('Empty response received');
					return;
				}
				var response = $.parseJSON(result);
				//console.log(response.componentList);
				if(response.componentList){
					//processing data table generation
					//passing only components
					var componentList = response.componentList;
					//deleting componentList to pass only header
					delete response.componentList;
				
					_super.setDataHeader(response);
					_super.setData(componentList);
					_super.totalRecords = response.totalRecords ? response.totalRecords : 0;
					
					//generating table and pagination
					_super.handleTableDraw();
				}
			});
	}
	
	DataTableComponentAjax.prototype.handleTableDraw = function(result){
		//generating table with template
		this.$(this.config.dtContainer).find('.row-container').html(this.template({data: this.data, dataHeader: this.dataHeader}));
		
		//generating pagination
		var recordsPerPage = (this.dataHeader.recordsPerPage != '' && !isNaN(this.dataHeader.recordsPerPage)) ?
			this.dataHeader.recordsPerPage: this.$(this.config.dtContainer).find('.record-limit').val();
		
		this.handlePagination(recordsPerPage, this.$(this.config.dtContainer).find('.paginator'));
	}
	
	//method to generate DataTable after calculating various params
	DataTableComponentAjax.prototype.generateDataTable = function(recordsPerPage, pageNumber, sortedColumn, ascDescOrder){
		//setting up default params if not provided.
		if(recordsPerPage === undefined){
			recordsPerPage = this.$(this.config.dtContainer).find('.record-limit').val() || 0;
			recordsPerPage = recordsPerPage ? recordsPerPage : this.config.recordLimit;
		}
		
		pageNumber = pageNumber === undefined ? this.curPage : pageNumber;
		
		if(sortedColumn === undefined){
			//sortedColumn = this.columnAlias[this.config.sortedColumn] !== undefined ? this.columnAlias[this.config.sortedColumn] : '';
			sortedColumn =  '';
		}
		
		//ascDescOrder = ascDescOrder === undefined ? this.config.sortMethod : ascDescOrder;
		ascDescOrder = ascDescOrder === undefined ? '' : ascDescOrder;
		
		//executing method to fetch data w.r.t. various params and generate the DataTable
		this.fetchRecordSet({recordsPerPage: recordsPerPage, pageNumber: pageNumber, sortedColumn: sortedColumn, ascDescOrder: ascDescOrder});
	}
	
	DataTableComponentAjax.prototype.handleSorting = function(e, _super){
		var sortColumn = _super.$(this).data('sort-column') || null;
		var sortMethod = _super.$(this).data('sorted-by') || 'asc';
				
		//console.log(sortColumn);
		//console.log(sortMethod);
		//console.log(_super.data);		
		if(sortColumn){
			var sortedDispClass = 'sort-up';
			//console.log(_super.data);
			//console.log(sortColumn);
			//console.log(sortMethod);
			
			_super.curPage = 1;
			
			_super.$(_super.config.dtContainer).find('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
			
			//generating the datatable using newly sorted method
			_super.generateDataTable(_super.dataHeader.recordsPerPage, 1, sortColumn, sortMethod);
			
			if(sortMethod == 'desc'){
				sortedDispClass = 'sort-down';
				sortMethod = 'asc';
			}else{
				sortMethod = 'desc';
			}
			
			_super.$(_super.config.dtContainer).find('a.sortable').removeClass('sort-up sort-down');
	
			_super.$(this).addClass(sortedDispClass); //removing sorting dir indicator 
	
			_super.$(this).data('sorted-by', sortMethod); //adding necessary sorting dir indicator	
		}
	}
		
	DataTableComponentAjax.prototype.handleRecordLimit = function(e, _super){
		_super.$(_super.config.dtContainer).find('.record-limit').val($(this).val()); //syncing all drop down values
		_super.$(_super.config.dtContainer).find('.select-all').prop('checked', false); //unchecking select all checkbox if previously checked
		
		if(!_super.totalRecords)
			return;
		
		_super.curPage = 1;
		
		_super.$(_super.config.dtContainer).find('.paginator').removeData('twbs-pagination'); //reconstructing the paginator
		
		_super.generateDataTable(_super.$(this).val(), _super.curPage, _super.dataHeader.sortedColumn, _super.dataHeader.ascDescOrder);
	}
	
	DataTableComponentAjax.prototype.dfdStyleColorTemplate = function(data){
		return this.$(this.config.dfdStyleColorTemplate).length ? this._.template($(this.config.dfdStyleColorTemplate).html(), null,  {
			interpolate :  /\{\{\=(.+?)\}\}/g,
			evaluate: /\{\{(.+?)\}\}/g
		})(data) : null;
	},
	
	DataTableComponentAjax.prototype.getDFDStyleColor = function(params){
		if(this.config.dfdChildrenUrl && this.config.dfdChildrenStyleColorParams){
			var params = _serializeObj(params) + '&' + _serializeObj(this.config.dfdChildrenStyleColorParams);
			
			if(this.searchParams !== undefined && this.searchParams.toString().length)
				params += '&' + this.searchParams.toString();
			
			return this.$.get(this.config.dfdChildrenUrl, params)
				.error(function(jqXHR, testStatus, errorThrown){
					console.log(jqXHR.status + " : " + testStatus  + " : " + errorThrown);
				});
		}else
			throw new Error('Required configuration is missing to fetch children from remote server');
	}
	
	DataTableComponentAjax.prototype.handleDFDStyleColor = function(){
		var _super = this;
		
		//delegate to handle expand of style to show dfd style color
		_super.$(this.config.dtContainer).on('click', '.parent-stylenode-expand-ajax', function(event, eventInfo){
			var _that = _super.$(this);
			var parentStyleORIN = _that.data('parentstyleorin');	
			var parentKey = _that.data('parentkey');
			/**
			* checking if already children are present and hidden 
			* then just showing them otherwise making ajax call to fetch before showing
			*/
			
			//changing display state to show working to get dfd children
			_that.removeClass('parent-stylenode-expand-ajax').addClass('parent-stylenode-expanding-ajax');
			
			if(_super.$('tr[data-parent-id=' + parentStyleORIN + '_' + parentKey + ']').length){
				//checking doShow param to make children instantly visible
				if(eventInfo === undefined || eventInfo.doShow == true){
					_super.$('tr[data-parent-id=' + parentStyleORIN + '_' + parentKey + ']').fadeIn('fast', function(){
						_that.removeClass('parent-stylenode-expanding-ajax').addClass('parent-stylenode-collapse-ajax');
					});
				}else{
					if(eventInfo !== undefined && eventInfo.doCheckAll == true)
						_super.$('tr[data-parent-id=' + parentStyleORIN + '_' + parentKey + ']').find('input[type=checkbox]').prop('checked', true);
					
					_that.removeClass('parent-stylenode-expanding-ajax').addClass('parent-stylenode-expand-ajax');
				}
				//breaking here
				return;
			}
			
			_super.getDFDStyleColor({parentStyleOrin: parentStyleORIN})
				.done(function(result){
					if(!result.length){
						console.warn('Invalid Response returned from server');
						return;
					}
					
					var response = _super.$.parseJSON(result);
					
					if(response.childList){
						var componentList = response.childList;
						//deleting componentList to pass only header
						delete response.childList;
						
						//generating children and appending
						var html = _super.dfdStyleColorTemplate({data: componentList, dataHeader: response, parentStyleORIN: parentStyleORIN, parentKey: parentKey, checkChildren: (eventInfo !== undefined && eventInfo.doCheckAll == true) ? true : false});
						_that.parent().parent().after(html);
						
						/** checking at least one child is available to select 
						* so that parent selection checkbox can be enabled
						*/
						
						_super.$('tr[data-parent-id=' + parentStyleORIN + '_' + parentKey + ']').each(function(){
							if(_super.$(this).find('input[type=checkbox]:enabled').length){
								_super.$('input[type=checkbox][data-chknode-id=' + parentStyleORIN + '_' + parentKey + ']').prop('disabled', false);
								return;
							}	
						});
						
						//checking doShow param to make children instantly visible
						if(eventInfo === undefined || eventInfo.doShow == true){
							_that.removeClass('parent-stylenode-expanding-ajax').addClass('parent-stylenode-collapse-ajax');
							_super.$('tr[data-parent-id=' + parentStyleORIN + '_' + parentKey + ']').fadeIn('fast');
						}else{
							_that.removeClass('parent-stylenode-expanding-ajax').addClass('parent-stylenode-expand-ajax');
						}					
					}else{
						console.warn('Invalid Response JSON returned from server');
					}
				});
		});
		
		//delegate to handle dfd style color
		_super.$(this.config.dtContainer).on('click', '.parent-stylenode-collapse-ajax', function(){
			var _that = _super.$(this);
			var parentStyleORIN = _that.data('parentstyleorin');	
			var parentKey = _that.data('parentkey');
			_super.$('tr[data-parent-id=' + parentStyleORIN + '_' + parentKey + ']').fadeOut('fast', function(){
				_that.removeClass('parent-stylenode-collapse-ajax').addClass('parent-stylenode-expand-ajax');
			});
		});
	
		//delegate to handle cehckboxes for styles such that to bring dfd style color children behind the scene and make them hidden
		_super.$(this.config.dtContainer).on('click', '.styles', function(){
			var domItem = _super.$(this).parent().parent().find('div.icon-tree'); 
			if(_super.$(this).is(':checked')){
				if(domItem.hasClass('parent-stylenode-expand-ajax')){
					domItem.trigger('click', {doShow: false, doCheckAll: true});
				}else if(domItem.hasClass('parent-stylenode-collapse-ajax')){
					
				}
			}else{
				_super.$(_super.config.dtContainer)
						.find('input[type=checkbox][data-generator-id=' + _super.$(this).data('chknode-id') + ']')
						.prop('checked', false);
			}
		});
		
		_super.$(this.config.dtContainer).on('click', '[data-item-type="SC"]', function(){
			if(_super.$(this).is(':checked')){
				_super.$('input[data-chknode-id=' + _super.$(this).data('generator-id') + ']').prop('checked', true);
			}
		});
	}
	
	DataTableComponentAjax.prototype.init = function(){
		this.destroyDelegates();
		
		this.generateDataTable();

		this.attachHandler();
		
		this.handleDFDStyleColor();
	}
	
	//private method to merge default and user supplied configs
	function _mergeConfig(config1, config2){ //must be called with App.DataTable context.
		return this.$.extend(config1, config2);
	}
	
	//private method to serialize form data or param objects
	function _serializeObj(obj){
		var str = "";
		for (var key in obj) {
			if (str != "") {
				str += "&";
			}
			str += key + "=" + encodeURIComponent(obj[key]);
		}
		return str;
	}
	
	app.DataTableComponentAjax = DataTableComponentAjax;
})(jQuery, _);