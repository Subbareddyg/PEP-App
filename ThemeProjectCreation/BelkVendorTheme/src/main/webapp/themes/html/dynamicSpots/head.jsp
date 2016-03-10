<%@ page session="false" buffer="none" %> 
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../includePortalTaglibs.jspf" %>
<portal-core:constants/><portal-core:defineObjects/> <portal-core:stateBase/>
<portal-core:lazy-set var="themeWebDAVBaseURI" elExpression="wp.themeList.current.metadata['com.ibm.portal.theme.template.ref']"/>
<portal-core:lazy-set var="currentNavNode" elExpression="wp.selectionModel.selected"/>
<title><c:out value='${wp.title}'/></title>
<portal-core:portletsHeadMarkupElements method="html" filter="title"/>
<portal-navigation:urlGeneration navigationNode='${wp.identification[currentNavNode]}'>
<link id="com.ibm.lotus.NavStateUrl" rel="alternate" href="<%wpsURL.write(escapeXmlWriter);%>" />
<link rel="bookmark" title='<c:out value='${currentNavNode.title}'/>' href='<%wpsURL.write(escapeXmlWriter);%>' hreflang="${wp.preferredLocale}"/>
</portal-navigation:urlGeneration>
<link href="<r:url uri="${themeWebDAVBaseURI}images/favicon.ico" keepNavigationalState="false" lateBinding="false" protected="false"/>" rel="shortcut icon" type="image/x-icon" />

<!--  need to add css  -->
<style type="text/css">
body{
background-color:#52527a !important;
}
</style>


<link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/css/lib/belkVendorPortal.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
<%-- <link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/css/lib/cars.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
<link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/css/lib/cars_print.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
<link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/css/lib/dialogSearch.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
<link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/css/lib/popup.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
<link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/css/lib/vendorImage.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' /> --%>
<script type="text/javascript">
	var context='';
</script>
<link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/extjs-resources/css/ext-all.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
	 <link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/css/vendorImage.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
	 <link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/css/popup.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
	 <link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/css/cars.css?v=2008111301" allowRelativeURL="true" mode="download" lateBinding="false"/>' />
	 <link rel="stylesheet" type="text/css" href='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/googiespell/googiespell.css" allowRelativeURL="true" mode="download" lateBinding="false"/>' />

<!--  add code for load  JS - START -->

<script type="text/javascript" src='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/js/lib/belkVendorPortal.js" allowRelativeURL="true" mode="download" lateBinding="false"/>'></script>
<script type="text/javascript" src='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/js/libs/jq-plugins-adapter.js" allowRelativeURL="true" mode="download" lateBinding="false"/>'></script>
<script type="text/javascript" src='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/js/libs/ext-all.js" allowRelativeURL="true" mode="download" lateBinding="false"/>'></script>
<script type="text/javascript" src='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/js/libs/jquery.scrollTo-min.js" allowRelativeURL="true" mode="download" lateBinding="false"/>'></script>
<script type="text/javascript" src='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/js/belk.cars.common.js?v=2008111301" allowRelativeURL="true" mode="download" lateBinding="false"/>'></script>
<script type="text/javascript" src='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/js/belk.cars.login.js" allowRelativeURL="true" mode="download" lateBinding="false"/>'></script>
<script type="text/javascript" src='<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/cars/js/libs/jquery-1.3.min.js" allowRelativeURL="true" mode="download" lateBinding="false"/>'></script>