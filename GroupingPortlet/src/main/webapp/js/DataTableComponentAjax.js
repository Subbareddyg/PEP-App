var app = app || {};

(function($, _){
	'use strict';
	
	app.DataTableComponentAjax = {};
	
	var DataTableComponentAjax = function (config, jq, underscore){
		app.DataTable.call(this, config, jq, underscore);
		
		console.log(this.config);
	}
	
	DataTableComponentAjax.prototype = Object.create(app.DataTable.prototype);
	
	DataTableComponentAjax.prototype{
		constructor: DataTableComponentAjax,
	}
	
	app.DataTableComponentAjax = DataTableComponentAjax;
}