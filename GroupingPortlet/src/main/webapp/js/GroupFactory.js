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
		
		//delete group
		deleteGroup: function(params){
			return $.post(app.GroupLandingApp.urlCollection.deleteGroupUrl,  $.extend({resourceType: 'deleteGroup'}, params))
				.error(handleException);
		},
		
		//Search SCG Component code block
		searchSplitComponents: function(params){
			return $.post(app.GroupLandingApp.urlCollection.splitComponentSearchUrl, params)
				.error(handleException);
		},
		
		//fetch all depts
		fetchDepts: function(params){
			return $.get(app.GroupLandingApp.urlCollection.groupSearchUrl, {resourceType: 'searchDept'})
				.error(handleException);
		},
		
		//fetch all classes against selected depts
		fetchClassCodes: function(params){
			return $.post(app.GroupLandingApp.urlCollection.groupSearchUrl, $.extend({resourceType: 'searchClass'}, params))
				.error(handleException);
		},
		
		// Featching Exiting Components 
		fetchComponents : function(params){
			return $.get(app.componentLandingApp.urlCollection.existingCompUrl,params)
					.error(handleException);
		}
		
		
	};
	
	function handleException(jqXHR, textStatus, errorThrown){
		console.log(textStatus + " - " + jqXHR.status + " - " + errorThrown);
	}
})(jQuery);