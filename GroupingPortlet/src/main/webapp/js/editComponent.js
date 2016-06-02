
var app = app || {} ;

;(function($){
	
		app.componentLandingApp = {
			
			urlCollection : {existingCompUrl : ''},
			
			addExitingData : function(){
				app.GroupFactory.fetchComponents({groupType: $('#groupType').val(), groupId: $('#groupId').val()})				
				.done(function(results){
					console.log(results);
				})
			},
			
			handleEvent : function(){
				
			},
			
			init: function(){
				this.addExitingData();
				this.handleEvent();
			}
			
		}
	
	
	
})(jQuery);