var app = app || {};

;(function(){
	app.Global = {
		defaults: {
			contextPath: '',
			maxGroupNameChars: 150,
			maxGroupDescChars: 7000,
		},
		
		constants: {
			groupStatus: {
				GROUP_CREATED: 200,
				GROUP_NOT_CREATED: 201,
				GROUP_CREATED_WITH_COMPONENT_SCG: 202,
				GROUP_CREATED_WITH_OUT_COMPONENT_SCG: 203,
				GROUP_CREATED_WITH_COMPONENT_SSG: 204,
				GROUP_CREATED_WITH_OUT_COMPONENT_SSG: 205,
			},
		},
	}
})();