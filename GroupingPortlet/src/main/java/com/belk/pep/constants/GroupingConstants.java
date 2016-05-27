
package com.belk.pep.constants;

/**
 * This class is for handling all the constants for the Grouping Portlet .
 *
 * @author AFUPYB3
 */
public class GroupingConstants {
    
/** The Constant SYSTEM_FAILURE_MSG. */
public static final String SYSTEM_FAILURE_MSG = "System Failure.";

/** The Constant GROUPING_PROPERTIES_FILE_NAME. */
public static final String GROUPING_PROPERTIES_FILE_NAME="grouping.properties";

/*Below constants related to Service*/
public static final String MESS_PROP = "message.properties";
public static final String CREATE_GROUP_SERVICE_URL = "create.group.url";
public static final String ADD_COMPONENT_TO_SCG_SERVICE_URL = "add.componet.to.scg.url";
public static final String ADD_COMPONENT_TO_SSG_SERVICE_URL = "add.componet.to.ssg.url";
public static final String CREATE_GROUP_SERVICE_LIST = "list";
public static final String CREATE_GROUP_SERVICE_SUCCESS = "createGroup.success.message";
public static final String CREATE_GROUP_SERVICE_FAILURE = "createGroup.failure.message";
public static final String ADD_COMPONENT_SCG_SERVICE_SUCCESS = "add.component.scg.success.message";
public static final String ADD_COMPONENT_SCG_SERVICE_FAILURE = "add.component.scg.failure.message";
public static final String ADD_COMPONENT_SSG_SERVICE_SUCCESS = "add.component.ssg.success.message";
public static final String ADD_COMPONENT_SSG_SERVICE_FAILURE = "add.component.ssg.failure.message";
public static final String CREATE_GROUP_FORM = "createGroupForm";
public static final String GROUPING_PAGE = "groupingPage";
public static final String GROUPING_ADD_COMPONENT ="addComponent";
public static final String GROUPING_CREATE_SPLIT_COLOR_GROUP = "createSplitColorGroup";
public static final String GROUPING_CREATE_SPLIT_SKU_GROUP = "createSplitSKUGroup";
public static final String MSG_CODE = "code";
public static final String SUCCESS_CODE = "100";
public static final String FAILURE_CODE = "101";

/** Grouping Type **/
public static final String GROUP_TYPE_CONSOLIDATE_PRODUCT = "CPG";
public static final String GROUP_TYPE_REGULAR_COLLECTION = "RCG";
public static final String GROUP_TYPE_BEAUTY_COLLECTION = "BCG";
public static final String GROUP_TYPE_SPLIT_COLOR = "SCG";
public static final String GROUP_TYPE_SPLIT_SKU = "SSG";
public static final String GROUP_TYPE_GROUP_BY_SIZE = "GBS";

/** Create Group Service parameter**/
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
public static final String COMPONENT_ID = "id";
public static final String COMPONENT_IS_DEFAULT = "default";
public static final String COMPONENT_COLOR = "color";
public static final String COMPONENT_SIZE = "size";
public static final String GROUP_CREATION_MSG = "groupCretionMsg";
public static final String GROUP_CREATION_STATUS_CODE = "groupCreationStatus";
public static final String PRODUCT_NAME = "productName";
public static final String COLOR_CODE = "colorCode";
public static final String COLOR_NAME = "colorName";
public static final String ALREADY_IN_GROUP = "alreadyInGroup";
public static final String COMPONENT_DEFAULT_COLOR = "defaultColor";
public static final String COMPONENT_SELECTED_ITEMS = "selectedItems";
public static final String MESSAGE = "message";



public static final String VENDOR_STYLE_NO = "vendorStyleNo";
public static final String STYLE_ORIN_NO = "StyleOrinNo";

public static final String SPLIT_GROUP_TOTAL_RECORDS = "totalRecords";
public static final String SPLIT_GROUP_DEFAULT_SORT_COL = "sortedColumn";
public static final String SPLIT_GROUP_DEFAULT_SORT_ORDER = "sortedOrder";


/** Create Group Error Message Code**/
public static final String GROUP_CREATED = "200";
public static final String GROUP_NOT_CREATED = "201";
public static final String GROUP_CREATED_WITH_COMPONENT_SCG = "202";
public static final String GROUP_CREATED_WITH_OUT_COMPONENT_SCG = "203";
public static final String GROUP_CREATED_WITH_COMPONENT_SSG = "204";
public static final String GROUP_CREATED_WITH_OUT_COMPONENT_SSG = "205";

public static final String GROUP_TYPES = "group.types";
public static final String GROUP_STATUS_EXT = "group.status.";


/** PET Status **/
public static final String PET_STATUS_INITIATED = "01";
public static final String PET_STATUS_COMPLETED = "02";
public static final String PET_STATUS_APPROVED = "03";
public static final String PET_STATUS_REJECTED = "04";
public static final String PET_STATUS_DEACTIVATED = "05";
public static final String PET_STATUS_CLOSED = "06";
public static final String PET_STATUS_WAITING_TO_BE_CLOSED = "07";
public static final String PET_STATUS_READY_FOR_REVIEW = "08";

/** Sorting Order**/
public static final String SORT_ASC = "ASC";
public static final String SORT_DESC = "DESC";

public static final String MESSAGE_SPLITGROUP_VALIDATION_NO_DATA = "message.splitGroup.validation.no.data";
public static final String MESSAGE_SPLITGROUP_VALIDATION_INVALID_DATA = "message.splitGroup.validation.invalid.data";

public static final String GROUP_DETAILS_FORM = "groupDetailsForm";
public static final String SELECTED_ATTRIBUTE_LIST = "selectedAttributeList";




/*Need to check below constants -*/
/** The Constant DCA_ROLE**/
public static final String DCA_ROLE = "dca";

/** The Constant VENDOR. */
public static final String VENDOR = "vendor";

/** The Constant READ_ONLY_ROLE. */
public static final String READ_ONLY_ROLE = "readonly";


/** The Constant NO_VALUE. */
public static final String NO_VALUE = "no" ;

/** The Constant YES_VALUE. */
public static final String YES_VALUE = "yes" ;

/** The Constant USER_DATA. */
public static final String USER_DATA = "UserData" ;

/** The Constant IS_INTERNAL. */
public static final String IS_INTERNAL = "isInternal" ;

/** The Constant COMPLETION_DATE. */
public static final String COMPLETION_DATE = "completionDate" ;

/** The Constant CURRENT_PAGE_NUMBER. */
public static final String CURRENT_PAGE_NUMBER = "currentPageNumber";

/** The Constant FALSE_VALUE. */
public static final String FALSE_VALUE = "false";
/** The Constant USER_DATA_OBJ. */
public static final String USER_DATA_OBJ = "GroupingUserObj";

}
