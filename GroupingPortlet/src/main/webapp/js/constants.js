var app = app || {};

;(function(){
	app.Global = {
		defaults: {
			contextPath: '',
			maxGroupNameChars: 150,
			minGroupDescChars: 40,
			maxGroupDescChars: 2000,			
			pageTimeOutMS: 1800000,  //time in MS for pageTimeout
			logOutTimeoutMS: 3600000,   //time in MS for total Logout
		},
		
		constants: {
			groupStatus: {
				GROUP_CREATED: 200,
				GROUP_NOT_CREATED: 201,
				GROUP_CREATED_WITH_COMPONENT_SCG: 202,
				GROUP_CREATED_WITH_OUT_COMPONENT_SCG: 203,
				GROUP_CREATED_WITH_COMPONENT_SSG: 204,
				GROUP_CREATED_WITH_OUT_COMPONENT_SSG: 205,
				GROUP_CREATED_WITH_COMPONENT_CPG: 206,
				GROUP_CREATED_WITH_OUT_COMPONENT_CPG: 207,
			},
			BCGEntryTypes: 'CPG|SCG|SSG',
		},
	}
})();