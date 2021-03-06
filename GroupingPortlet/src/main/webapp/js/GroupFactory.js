var app = app || {};

;(function($){
	var XHRPool = {};
	
	app.GroupFactory = {
		
		searchGroup: function(params){
			return $.post(app.URLFactory.getURL('groupSearchUrl'), params)
				.error(handleException);
		},
		
		//create group code block
		createGroup: function(data){
			return $.post(app.URLFactory.getURL('createGroupUrl'), data)
				.error(handleException);
		},
		
		//delete group
		deleteGroup: function(params){
			return $.post(app.URLFactory.getURL('deleteGroupUrl'),  $.extend({resourceType: 'deleteGroup'}, params))
				.error(handleException);
		},
		
		//Search SCG Component code block
		searchSplitComponents: function(params){
			return $.post(app.URLFactory.getURL('splitComponentSearchUrl'), params)
				.error(handleException);
		},
		
		//fetch all depts
		fetchDepts: function(params){
			return $.get(app.URLFactory.getURL('groupSearchUrl'), {resourceType: 'searchDept'})
				.error(handleException);
		},
		
		//fetch all classes against selected depts
		fetchClassCodes: function(params){
			return $.post(app.URLFactory.getURL('groupSearchUrl'), $.extend({resourceType: 'searchClass'}, params))
				.error(handleException);
		},
		
		// Featching Exiting Components 
		fetchExistingComponents : function(params){
			return $.get(app.URLFactory.getURL('existingCompUrl'), params)
				.error(handleException);
		},
		
		// adds new component to a split group
		addNewSplitComponent: function(params){
			return $.get(app.URLFactory.getURL('addComponentToGroup'), params)
				.error(handleException);
		},
		
		// updates default component for a  split group
		editDefaultComponent: function(params){
			return $.post(app.URLFactory.getURL('editDefaultComponentUrl'), params)
				.error(handleException);
		},
		
		// method to update group header
		updateHeader: function(params){
			return $.post(app.URLFactory.getURL('saveHeader'), params)
					.error(handleException);
		},
		
		//Remove Exiting component 
		removeComponents: function(params){
			return $.post(app.URLFactory.getURL('removeComponentURL'), params)
					.error(handleException);
		},
		
		//method to get group lock status 
		checkGroupLock: function(groupId){
			return $.post(app.URLFactory.getURL('groupLockStatusURL'), {groupId:groupId})
				.error(handleException);
		},
		
		//method to get group lock status 
		handleLock: function(mode, userId, groupId){
			return $.post(app.URLFactory.getURL('groupLockHandleURL'), {loggedInUser:userId, lockedPet: groupId, lockFunction:mode})
				.error(handleException);
		},
	};
	
	function handleException(jqXHR, textStatus, errorThrown){
		console.log(textStatus + " - " + jqXHR.status + " - " + errorThrown);
	}
})(jQuery);