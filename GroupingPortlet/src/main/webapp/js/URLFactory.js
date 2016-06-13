var app = app || {};

;(function(){
	app.URLFactory = {
		urlCollection: {}, // url can be placed  in this collection on ad hoc basic
		
		getURL: function(URLKey){
			if(this.urlCollection[URLKey] !== undefined){
				return this.urlCollection[URLKey];
			}else
				throw new Error('The requested URL not defined');
		}
	}
})();