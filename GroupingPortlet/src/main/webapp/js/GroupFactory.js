var app = app || {};

;(function($){
	app.GroupFactory = {
		searchGroup: function(params){
			return $.post(app.GroupLandingApp.urlCollection.groupSearchUrl, params)
				.error(handleException);
		},
		
		//create group code block
		createGroup: function(data){
			return $.post(app.GroupLandingApp.urlCollection.createGroupUrl, data)
				.error(handleException);
		},
	};
	
	function handleException(jqXHR, textStatus, errorThrown){
		console.log(jqXHR.status + " - " + textStatus + " - " + errorThrown);
	}
})(jQuery);