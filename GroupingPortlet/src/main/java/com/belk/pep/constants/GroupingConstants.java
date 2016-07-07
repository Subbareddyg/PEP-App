package com.belk.pep.constants;

/**
 * This class is for handling all the constants for the Grouping Portlet.
 * 
 * @author AFUPYB3
 */
public class GroupingConstants {

	/**
	 * 
	 */
	private GroupingConstants(){
		// To be implemented.
	}
	/** The Constant SYSTEM_FAILURE_MSG. */
	public static final String SYSTEM_FAILURE_MSG = "System Failure.";

	/** The Constant GROUPING_PROPERTIES_FILE_NAME. */
	public static final String GROUPING_PROPERTIES_FILE_NAME = "grouping.properties";

	/* Below constants related to Service */
	public static final String MESS_PROP = "message.properties";

	public static final String SERVICE_REQUEST_PROPERTY_CONTENT_TYPE = "service.request.property.contentType";
	public static final String SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE = "service.request.property.applicationType";
	public static final String SERVICE_REQUEST_METHOD = "service.request.property.request.method";

	public static final String CREATE_GROUP_SERVICE_URL = "create.group.url";
	public static final String ADD_COMPONENT_TO_SCG_SERVICE_URL = "add.componet.to.scg.url";
	public static final String ADD_COMPONENT_TO_SSG_SERVICE_URL = "add.componet.to.ssg.url";
	public static final String ADD_COMPONENT_TO_CPG_SERVICE_URL = "add.componet.to.cpg.url";
	public static final String ADD_COMPONENT_TO_COLLECTION_SERVICE_URL = "add.componet.to.collection.url";
	public static final String CREATE_GROUP_SERVICE_LIST = "list";
	public static final String CREATE_GROUP_SERVICE_SUCCESS = "createGroup.success.message";
	public static final String CREATE_GROUP_SERVICE_FAILURE = "createGroup.failure.message";
	public static final String ADD_COMPONENT_SCG_SERVICE_SUCCESS = "add.component.scg.success.message";
	public static final String ADD_COMPONENT_SCG_SERVICE_FAILURE = "add.component.scg.failure.message";
	public static final String ADD_COMPONENT_SSG_SERVICE_SUCCESS = "add.component.ssg.success.message";
	public static final String ADD_COMPONENT_SSG_SERVICE_FAILURE = "add.component.ssg.failure.message";
	public static final String ADD_COMPONENT_SUCCESS = "addComponent.success.message";
	public static final String ADD_COMPONENT_FAILURE = "addComponent.failure.message";
	public static final String CREATE_GROUP_FORM = "createGroupForm";
	public static final String GROUPING_PAGE = "groupingPage";
	public static final String GROUPING_ADD_COMPONENT = "addComponent";
	public static final String GROUPING_CREATE_SPLIT_COLOR_GROUP = "createSplitColorGroup";
	public static final String GROUPING_CREATE_SPLIT_SKU_GROUP = "createSplitSKUGroup";
	public static final String MSG_CODE = "code";
	public static final String SUCCESS_CODE = "100";
	public static final String FAILURE_CODE = "101";
	
	public static final String UPDATE_GROUP_SERVICE_URL = "update.group.url";

	/** Grouping Type. **/
	public static final String GROUP_TYPE_CONSOLIDATE_PRODUCT = "CPG";
	public static final String GROUP_TYPE_REGULAR_COLLECTION = "RCG";
	public static final String GROUP_TYPE_BEAUTY_COLLECTION = "BCG";
	public static final String GROUP_TYPE_SPLIT_COLOR = "SCG";
	public static final String GROUP_TYPE_SPLIT_SKU = "SSG";
	public static final String GROUP_TYPE_GROUP_BY_SIZE = "GSG";

	/** Create Group Service parameter. **/
	public static final String GROUP_ID = "groupId";
	public static final String GROUP_TYPE = "groupType";
	public static final String GROUP_NAME = "groupName";
	public static final String GROUP_DESC = "groupDesc";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String GROUP_STATUS = "status";
	public static final String COMPONENT_LIST = "componentList";
	public static final String PRIORITY_LIST = "priorities";
	public static final String COMPONENT_LIST_SUB = "childList";
	public static final String COMPONENT_ID_ATTR = "componentId";
	public static final String COMPONENT_ATTR = "component";
	public static final String COMPONENT_IS_DEFAULT_ATTR = "defaultValue";
	public static final String COMPONENT_ID = "id";
	public static final String COMPONENT_SKU = "SKU";
	public static final String COMPONENT_IS_DEFAULT = "default";
	public static final String COMPONENT_TYPE = "type";
	public static final String COMPONENT_COLOR = "color";
	public static final String COMPONENT_COLOR_LIST = "colorList";
	public static final String COMPONENT_SIZE = "size";
	public static final String GROUP_CREATION_MSG = "groupCretionMsg";
	public static final String GROUP_CREATION_STATUS_CODE = "groupCreationStatus";
	public static final String PRODUCT_NAME = "productName";
	public static final String COLOR_CODE = "colorCode";
	public static final String COLOR_NAME = "colorName";
	public static final String ALREADY_IN_GROUP = "alreadyInGroup";
	public static final String ALREADY_IN_SAME_GROUP = "alreadyInSameGroup";
	public static final String COMPONENT_DEFAULT_COLOR = "defaultColor";
	public static final String COMPONENT_SELECTED_ITEMS = "selectedItems";
	public static final String CARS_GROUPING_TYPE = "carsGroupType";
	public static final String MESSAGE = "message";
	public static final String CHILD_GROUP = "childGroup";
	public static final String COMPONENT_TYPE_STYLE = "Style";
	public static final String COMPONENT_TYPE_STYLE_COLOR = "StyleColor";
	public static final String COMPONENT_TYPE_SKU = "SKU";
	
	public static final String VENDOR_STYLE_NO = "vendorStyleNo";
	public static final String STYLE_ORIN_NO_SEARCH_PARAM = "styleOrinNo";
	public static final String STYLE_ORIN_NO = "StyleOrinNo";
	public static final String STYLE_MDMID = "mdmid";
	public static final String VENDOR_STYLE_NO_SEARCH = "vendorStyleNoSearch";
	public static final String STYLE_ORIN_NO_SEARCH = "styleOrinNoSearch";

	public static final String SPLIT_GROUP_TOTAL_RECORDS = "totalRecords";
	public static final String SPLIT_GROUP_DEFAULT_SORT_COL = "sortedColumn";
	public static final String SPLIT_GROUP_DEFAULT_SORT_ORDER = "sortedOrder";

	/** Create Group Error Message Code. **/
	public static final String GROUP_CREATED = "200";
	public static final String GROUP_NOT_CREATED = "201";
	public static final String GROUP_CREATED_WITH_COMPONENT_SCG = "202";
	public static final String GROUP_CREATED_WITH_OUT_COMPONENT_SCG = "203";
	public static final String GROUP_CREATED_WITH_COMPONENT_SSG = "204";
	public static final String GROUP_CREATED_WITH_OUT_COMPONENT_SSG = "205";
	public static final String COMPONENT_ADDEDD_SUCCESSFULLY = "206";
	public static final String COMPONENT_ADDITION_FAILED = "207";

	public static final String GROUP_TYPES = "group.types";
	public static final String GROUP_STATUS_EXT = "group.status.";

	/** PET Status. **/
	public static final String PET_STATUS_INITIATED = "01";
	public static final String PET_STATUS_COMPLETED = "02";
	public static final String PET_STATUS_APPROVED = "03";
	public static final String PET_STATUS_REJECTED = "04";
	public static final String PET_STATUS_DEACTIVATED = "05";
	public static final String PET_STATUS_CLOSED = "06";
	public static final String PET_STATUS_WAITING_TO_BE_CLOSED = "07";
	public static final String PET_STATUS_READY_FOR_REVIEW = "08";
	public static final String PET_STATUS_PUBLISH_TO_WEB = "09";
	public static final String PET_STATUS_PRE_CUT_INITIATED = "10";

	/** Sorting Order. **/
	public static final String SORT_ASC = "ASC";
	public static final String SORT_DESC = "DESC";

	public static final String MESSAGE_SPLITGROUP_VALIDATION_NO_DATA = "message.splitGroup.validation.no.data";
	public static final String MESSAGE_SPLITGROUP_VALIDATION_INVALID_DATA = "message.splitGroup.validation.invalid.data";
	public static final String MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_ONE = "message.cpg.validation.diff.classid.one";
	public static final String MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_TWO = "message.cpg.validation.diff.classid.two";
	public static final String MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_THREE = "message.cpg.validation.diff.classid.three";
	public static final String MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_FOUR = "message.cpg.validation.diff.classid.four";
	public static final String MESSAGE_GBS_VALIDATION_NOT_ELIGIBLE = "message.gbs.validation.not.eligible";

	public static final String GROUP_DETAILS_FORM = "groupDetailsForm";
	public static final String SELECTED_ATTRIBUTE_LIST = "selectedAttributeList";
	public static final String EXISTING_ATTRIBUTE_LIST = "existingAttributeList";
	
	public static final int DATE_FIELD_LENGTH = 10;
	
	/** CARS TYPE. **/
	public static final String CARS_GROUP_TYPE_GBS="PATTERN-SGBS-VS";
	public static final String CARS_GROUP_TYPE_CPG="PATTERN-CONS-VS"; 
	public static final String CARS_GROUP_TYPE_BCG="OUTFIT"; 
	public static final String CARS_GROUP_TYPE_RCG_ONE="PATTERN-SPLIT-VS";
	public static final String CARS_GROUP_TYPE_RCG_TWO="PATTERN-SSKU-VS"; 


	/* Need to check below constants - */
	/** The Constant DCA_ROLE. **/
	public static final String DCA_ROLE = "dca";

	/** The Constant VENDOR. */
	public static final String VENDOR = "vendor";

	/** The Constant READ_ONLY_ROLE. */
	public static final String READ_ONLY_ROLE = "readonly";

	/** The Constant NO_VALUE. */
	public static final String NO_VALUE = "no";

	/** The Constant YES_VALUE. */
	public static final String YES_VALUE = "yes";

	/** The Constant USER_DATA. */
	public static final String USER_DATA = "UserData";

	/** The Constant IS_INTERNAL. */
	public static final String IS_INTERNAL = "isInternal";

	/** The Constant COMPLETION_DATE. */
	public static final String COMPLETION_DATE = "completionDate";

	/** The Constant CURRENT_PAGE_NUMBER. */
	public static final String CURRENT_PAGE_NUMBER = "currentPageNumber";

	/** The Constant FALSE_VALUE. */
	public static final String FALSE_VALUE = "false";
	/** The Constant USER_DATA_OBJ. */
	public static final String USER_DATA_OBJ = "GroupingUserObj";

	public static final String LAN_ID = "LAN_ID";
	

	/**
	 * Grouping Search constants.
	 */
	public static final String GROUPING_SERACH_DISPLAY_FORM = "groupingSearchDisplayForm";
	public static final String CHILD_LIST = "childList";
	public static final String MAIN_LIST = "mainList";
	public static final String GROUP_ID_SEARCH = "groupIDSearch";
	public static final String GROUP_NAME_SEARCH = "groupNameSearch";
	public static final String DEPT_SEARCH = "deptSearch";
	public static final String CLASS_SEARCH = "classSearch";
	public static final String VENDOR_SEARCH = "vendorSearch";
	public static final String SUPPLIER_SEARCH = "supplierSearch";
	public static final String UPC_SEARCH = "UPCSearch";
	public static final String TOTAL_RECORD_COUNT = "totalRecordCount";
	public static final String RECORDS_PER_PAGE = "recordsPerPage";
	public static final String PAGE_NUMBER = "pageNumber";
	public static final String SORTED_COLUMN = "sortedColumn";
	public static final String ASC_DESC_ORDER = "ascDescOrder";
	public static final String GROUP_CONTENT_STATUS = "groupContentStatus";
	public static final String GROUP_IMAGE_STATUS = "groupImageStatus";
	public static final String DEPT_ID = "deptId";
	public static final String DEPT_DESC = "deptDesc";
	public static final String DEPT_LIST = "deptList";
	public static final String CLASS_ID = "classId";
	public static final String CLASS_DESC = "classDesc";
	public static final String CLASS_LIST = "classList";
	public static final String GROUP_TYPE_CODE = "groupTypeCode";

	/**
	 * Delete group constants.
	 */
	//Delete Group Service URl constants.
	public static final String DELETE_GROUP_SERVICE_URL = "deleteGroup.service.url";
	public static final String DELETE_GROUP_SERVICE_FAILURE = "deleteGroup.failure.message";
	public static final String DELETE_GROUP_SERVICE_SUCCESS = "deleteGroup.success.message";
	public static final String DELETE_STATUS_MESSAGE = "deleteStatusMessage";
	public static final String DELETE_STATUS = "deleteStatus";
	//Emtpy string marker.
	public static final String EMPTY = "";
	public static final String DEFAULT_CHARSET ="UTF-8";
	public static final String REMOVE_COMPNT_GROUP_SERVICE_URL = "remove.componet.cpg.url";
	public static final String SET_COMPONENT_PRIORITY_SERVICE_URL = "set.component.prioroty.url";
	public static final String ORDER="order";

	public static final String DEFAULT_VALUE_STATUS_MESSAGE = "defaultValueStatusMessage";

	public static final String SET_DEFAULT_COLOR_SERVICE_URL = "set.default.color.url";
	public static final String DELETE_COMPNT_SUCCESS="component.success.message";
	public static final String DELETE_COMPNT_FAILURE="component.failure.message";
	
	public static final String DEFAULT_COMPNT_SUCCESS="default.color.success.message";
	public static final String DEFAULT_COMPNT_FAILURE="default.color.failure.message";
	
	public static final String PRIORITY_COMPNT_SUCCESS="priority.success.message";
	public static final String PRIORITY_COMPNT_FAILURE="priority.failure.message";

	public static final String DESCRIPTION_ATTR = "description";
	
	public static final String IS_GROUP ="isGroup";
	public static final String PRIORITY ="priority";
	public static final String HAVE_CHILD_GROUP ="haveChildGroup";

	public static final String SUCCESS ="SUCCESS";
	public static final String FAIL ="FAIL";
	public static final String STATUS ="status";
	
	public static final String RESOURCE_TYPE = "resourceType";
	public static final String RESOURCE_TYPE_FOR_SET_PRIORITY = "priorityValueSave";
	public static final String RESOURCE_TYPE_FOR_SET_DEFAULT = "defaultValueSave";
	
	//constants for PET page locked scenarios
	public static final String SEARCH_LOCKED_TYPE_CONTENT = "Content";
	public static final String SEARCH_LOCKED_TYPE_IMAGE = "Image";
	
	public static final String LOCK_JSON_COMPONENT_PET_LOCKED_USER = "petLockedUser";
	public static final String LOCK_JSON_COMPONENT_PET_LOCKED_STATUS = "LockStatus";
	public static final String GROUP_LOCKED_MESSAGE_ONE = "group.locked.message.one";
	public static final String GROUP_LOCKED_MESSAGE_TWO = "group.locked.message.two";
	public static final String NO_USER = "NoUser";
	public static final String LOCKED_PET = "lockedPet";
	public static final String LOGGED_IN_USER = "loggedInUser";
	public static final String LOCKED_FUNCTION = "lockFunction";
	public static final String LOCKED_FUNCTION_RELEASE = "release";
	public static final String LOCKED_FUNCTION_RELEASE_LOCK = "releaseLock";
	
	public static final String PARENT_STYLE_ORIN = "parentStyleOrin";
}
