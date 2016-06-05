package com.belk.pep.constants;

//import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.belk.pep.dto.GroupSearchDTO;
import com.belk.pep.form.GroupSearchForm;

/** The Class XqueryConstants.
 * 
 * @author AFUSOS3 */
public class XqueryConstants {

	/** The Constant ORACLE_DRIVER. */
	public static final String ORACLE_DRIVER = "driver";

	/** The Constant DATABASEURL. */
	public static final String DATABASE_URL = "databaseUrl";

	/** The Constant DATABASE_USERNAME. */
	public static final String DATABASE_USERNAME = "databaseUsername";

	/** The Constant DATABASE_PASSWORD. */
	public static final String DATABASE_PASSWORD = "databasePassword";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(XqueryConstants.class.getName());

	/** Instantiates a new xquery constants. */
	public XqueryConstants() {

	}

	/** This method is used to build the Query to retrieve Group Header Details
	 * from Database against Group Id.
	 * 
	 * @return String */
	public final String getGroupHeaderDetails() {
		final String getGroupHeaderDetailsQuery = " "
				+ " SELECT                                                                                "
				+ "      AGC.MDMID GROUP_ID,                                                               "
				+ "      AGC.GROUP_NAME,                                                                   "
				+ "      AGCXML.Description,                                                               "
				+ "      AGCXML.Effective_Start_Date,                                                      "
				+ "      AGCXML.Effective_End_Date,                                                        "
				+ "      AGCXML.CARS_Group_Type,                                                      "
				+ "      AGC.ENTRY_TYPE GROUP_TYPE, AGC.GROUP_OVERALL_STATUS_CODE,                          "
				+ "      AGC.CREATED_BY                                                                    "
				+ "        /*XML_DATA*/                                                                      "
				+ "      FROM ADSE_GROUP_CATALOG AGC,                                                      "
				+ "        XMLTABLE( 'let                                                                  "
				+ "        $Description:= /pim_entry/entry/Group_Ctg_Spec/Description,                     "
				+ "        $Effective_Start_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_Start_Date,   "
				+ "        $Effective_End_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_End_Date,        "
				+ "        $CARS_Group_Type:= /pim_entry/entry/Group_Ctg_Spec/CARS_Group_Type        "
				+ "        return                                         "
				+ "      <out>                                                                             "
				+ "          <Description>{$Description}</Description>                                     "
				+ "          <Effective_Start_Date>{$Effective_Start_Date}</Effective_Start_Date>          "
				+ "          <Effective_End_Date>{$Effective_End_Date}</Effective_End_Date>                "
				+ "          <CARS_Group_Type>{$CARS_Group_Type}</CARS_Group_Type>                "
				+ "      </out>' passing AGC.XML_DATA                                                      "
				+ "        Columns                                                                         "
				+ "        Description CLOB path '/out/Description',                               "
				+ "        Effective_Start_Date VARCHAR2(50) path '/out/Effective_Start_Date' ,            "
				+ "        Effective_End_Date VARCHAR2(50) path '/out/Effective_End_Date',                 "
				+ "        CARS_Group_Type VARCHAR2(50) path '/out/CARS_Group_Type') AGCXML          "
				+ "                                                                                        "
				+ "      WHERE                                                                             "
				+ "      MDMID = :groupIdSql AND DELETED_FLAG = 'false'                                     ";

		return getGroupHeaderDetailsQuery;
	}

	/** This method is used to build the Query to retrieve Split Color details to
	 * create Split Color Group.
	 * 
	 * @return String
	 * @param vendorStyleNo */
	public final String getSplitColorDetails(final String vendorStyleNo) {
		String getSplitColorDetailsQuery = " Select                                 "
				+ "  ITEM.MDMID, ITEM.PARENT_MDMID,                                                                      "
				+ "  ITEM.PRIMARYSUPPLIERVPN,                                                                            "
				+ "  PET_XML.PRODUCT_NAME,                                                                               "
				+ "  PET_XML.COLOR_CODE,                                                                                 "
				+ "  PET_XML.COLOR_DESC,                                                                                 "
				+ "  PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE, PET.ENTRY_TYPE                                    "
				+ "  FROM                                                                                                "
				+ "  ADSE_ITEM_CATALOG ITEM,                                                                             "
				+ "  ADSE_PET_CATALOG PET                                                                                "
				+ "      LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING MAPPING             "
				+ "      ON MAPPING.COMPONENT_STYLECOLOR_ID = PET.MDMID AND PET.ENTRY_TYPE =  MAPPING.COMPONENT_TYPE,    "
				+ "  XMLTABLE('let                                                                                       "
				+ "      $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,                      "
				+ "      $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code                              "
				+ "  return                                                                                              "
				+ "  <COLOR>                                                                                             "
				+ "      <COLOR_CODE>{$colorCode}</COLOR_CODE>                                                           "
				+ "      <COLOR_DESC>{$colordesc}</COLOR_DESC>                                                           "
				+ "      <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>                   "
				+ "  </COLOR>'                                                                                           "
				+ "  passing Pet.XML_DATA                                                                                "
				+ "  Columns                                                                                             "
				+ "      COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',                                                "
				+ "      COLOR_DESC VARCHAR2(20) path '/COLOR/COLOR_DESC',                                               "
				+ "      PRODUCT_NAME VARCHAR2(20) path '/COLOR/PRODUCT_NAME'                                            "
				+ "      ) PET_XML                                                                                       "
				+ "  WHERE                                                                                               "
				+ "  ITEM.ENTRY_TYPE in ('Style', 'StyleColor')                                                          "
				+ "  AND ITEM.DELETED_FLAG= 'false'                                                                      "
				+ "   AND PET.MDMID=ITEM.MDMID                                                                           ";
		if (vendorStyleNo != null) {
			getSplitColorDetailsQuery = getSplitColorDetailsQuery + " AND item.PRIMARYSUPPLIERVPN = :styleIdSql";
		} else {
			getSplitColorDetailsQuery = getSplitColorDetailsQuery + " AND ITEM.MDMID = :mdmidSql";
		}

		return getSplitColorDetailsQuery;
	}

	/** This method is used to build the Query to retrieve Split SKU details to
	 * create Split SKU Group.
	 * 
	 * @param vendorStyleNo
	 * @return String */
	public final String getSplitSKUDetails(final String vendorStyleNo) {
		String getSplitSKUDetailsQuery = "   Select                                                                      "
				+ "  ITEM.MDMID,                                                                                         "
				+ "  ITEM.PRIMARYSUPPLIERVPN,                                                                            "
				+ "  ITEM_XML.NAME,                                                                                      "
				+ "  ITEM_XML.COLOR_CODE,                                                                                "
				+ "  ITEM_XML.COLOR_NAME,                                                                                "
				+ "  ITEM_XML.SIZEDESC,                                                                                  "
				+ "  PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE, PET.ENTRY_TYPE                                  "
				+ "  FROM                                                                                                "
				+ "  ADSE_ITEM_CATALOG ITEM,                                                                             "
				+ "  ADSE_PET_CATALOG PET                                                                                "
				+ "      LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING MAPPING                                                            "
				+ "      ON MAPPING.COMPONENT_SKU_ID = PET.MDMID AND PET.ENTRY_TYPE =  MAPPING.COMPONENT_TYPE,           "
				+ "  XMLTABLE('let                                                                                       "
				+ "  $colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,                   "
				+ "  $colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description,     "
				+ "  $size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,           "
				+ "  $name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                             "
				+ "  return                                                                                              "
				+ "      <SPEC>                                                                                          "
				+ "          <COLOR_CODE>{$colorcode}</COLOR_CODE>                                                       "
				+ "          <COLOR_NAME>{$colorname}</COLOR_NAME>                                                       "
				+ "          <SIZE>{$size}</SIZE>                                                                        "
				+ "          <NAME>{$name}</NAME>                                                                        "
				+ "      </SPEC>'                                                                                        "
				+ "  passing ITEM.XML_DATA                                                                               "
				+ "  Columns                                                                                             "
				+ "      COLOR_CODE VARCHAR2(5) path '/SPEC/COLOR_CODE',                                                 "
				+ "      COLOR_NAME VARCHAR2(20) path '/SPEC/COLOR_NAME',                                                "
				+ "      SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',                                                        "
				+ "      NAME VARCHAR2(50) path '/SPEC/NAME') ITEM_XML                                                   "
				+ "  WHERE                                                                                               "
				+ "      ITEM.ENTRY_TYPE in ('Style', 'StyleColor', 'SKU')                                               "
				+ "      AND ITEM.DELETED_FLAG= 'false'                                                                  "
				+ "      AND PET.MDMID=ITEM.MDMID                                                                        ";
		if (vendorStyleNo != null) {
			getSplitSKUDetailsQuery = getSplitSKUDetailsQuery + " AND item.PRIMARYSUPPLIERVPN = :styleIdSql";
		} else {
			getSplitSKUDetailsQuery = getSplitSKUDetailsQuery + " AND ITEM.MDMID = :mdmidSql";
		}

		return getSplitSKUDetailsQuery;
	}

	/** Method to get the Search Group query.
	 * 
	 * @param groupSearchForm
	 * @return the query string
	 * 
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/19/2016
	 *         Added By: Cognizant */
	public final String getGroupDetailsQuery(final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering getGroupDetailsQuery() in Grouping XQueryConstant class.");

		final StringBuffer getGroupDetailsQuery = new StringBuffer();
		getGroupDetailsQuery.append(" SELECT GRP.MDMID GROUP_ID, GRP.GROUP_NAME, GRP.ENTRY_TYPE GROUP_TYPE,"
				+ " CONTENT_STATE.THEVALUE CONTENT_STATE, GRP.START_DATE START_DATE, "
				+ "GRP.END_DATE END_DATE, IMAGE_STATE.THEVALUE IMAGE_STATE, EXIST_IN_GROUP CHILD_GROUP "
				+ "FROM ADSE_GROUP_CATALOG GRP INNER JOIN ");
		getGroupDetailsQuery.append(" ADSE_REFERENCE_DATA CONTENT_STATE ON GROUP_CONTENT_STATUS_CODE = CONTENT_STATE.MDMID ");
		getGroupDetailsQuery.append(" AND CONTENT_STATE.ENTRY_TYPE = 'ContentState_Lookup' ");
		getGroupDetailsQuery.append(" INNER JOIN ");
		getGroupDetailsQuery.append(" ADSE_REFERENCE_DATA IMAGE_STATE ON GROUP_IMAGE_STATUS_CODE = IMAGE_STATE.MDMID ");
		getGroupDetailsQuery.append(" AND IMAGE_STATE.ENTRY_TYPE = 'ImageState_Lookup' ");

		if ((groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals(""))
				|| (groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals(""))
				|| (groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals(""))
				|| (groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals(""))
				|| (groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals(""))) {
			getGroupDetailsQuery.append(" ,ADSE_GROUP_CHILD_MAPPING GCM, ADSE_ITEM_CATALOG AIC ");
		}

		getGroupDetailsQuery.append(" WHERE                                                                                          ");
		getGroupDetailsQuery.append(" GRP.DELETED_FLAG = 'false'                                                                       ");
		if (groupSearchForm.getGroupId() != null && !groupSearchForm.getGroupId().trim().equals("")) {
			getGroupDetailsQuery.append(" AND GRP.MDMID = '");
			getGroupDetailsQuery.append(groupSearchForm.getGroupId());
			getGroupDetailsQuery.append("'");
		}
		if (groupSearchForm.getGroupName() != null && !groupSearchForm.getGroupName().trim().equals("")) {
			getGroupDetailsQuery.append(" AND UPPER(GROUP_NAME) LIKE '%");
			getGroupDetailsQuery.append(groupSearchForm.getGroupName().toUpperCase());
			getGroupDetailsQuery.append("%'");
		}
		if ((groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals(""))
				|| (groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals(""))
				|| (groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals(""))
				|| (groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals(""))
				|| (groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals(""))) {

			getGroupDetailsQuery.append(" AND GRP.MDMID = GCM.MDMID ");
			getGroupDetailsQuery.append(" AND GCM.COMPONENT_STYLE_ID = AIC.MDMID ");
			getGroupDetailsQuery.append(" AND AIC.DELETED_FLAG = 'false' ");
			getGroupDetailsQuery.append(" AND AIC.ENTRY_TYPE = 'Style' ");
			if (groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals("")) {
				getGroupDetailsQuery.append(" AND AIC.DEPT_ID IN (");
				getGroupDetailsQuery.append(getNumbersInCorrectFormat(groupSearchForm.getDepts()));
				getGroupDetailsQuery.append(")");
			}
			if (groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals("")) {
				getGroupDetailsQuery.append(" AND AIC.CLASS_ID IN (");
				getGroupDetailsQuery.append(getNumbersInCorrectFormat(groupSearchForm.getClasses()));
				getGroupDetailsQuery.append(")");
			}
			if (groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals("")) {
				getGroupDetailsQuery.append(" AND AIC.MDMID = '");
				getGroupDetailsQuery.append(groupSearchForm.getOrinNumber());
				getGroupDetailsQuery.append("'");
			}
			if (groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals("")) {
				getGroupDetailsQuery.append(" AND AIC.PRIMARY_SUPPLIER_ID = '");
				getGroupDetailsQuery.append(groupSearchForm.getSupplierSiteId());
				getGroupDetailsQuery.append("'");
			}
			if (groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals("")) {
				getGroupDetailsQuery.append(" AND AIC.PRIMARYSUPPLIERVPN = '");
				getGroupDetailsQuery.append(groupSearchForm.getVendor());
				getGroupDetailsQuery.append("'");
			}
		}

		String sortOrder = "";
		if (groupSearchForm.getAscDescOrder() != null || !groupSearchForm.getAscDescOrder().trim().equals("")) {
			sortOrder = groupSearchForm.getAscDescOrder();
		}
		if (groupSearchForm.getSortColumn() == null || groupSearchForm.getSortColumn().trim().equals("")) {
			getGroupDetailsQuery.append(" ORDER BY GRP.ENTRY_TYPE, GRP.MDMID " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("groupId")) {
			getGroupDetailsQuery.append(" ORDER BY GRP.MDMID " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("groupName")) {
			getGroupDetailsQuery.append(" ORDER BY GROUP_NAME " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("startDate")) {
			getGroupDetailsQuery.append(" ORDER BY GRP.START_DATE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("endDate")) {
			getGroupDetailsQuery.append(" ORDER BY GRP.END_DATE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("groupType")) {
			getGroupDetailsQuery.append(" ORDER BY GRP.ENTRY_TYPE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("imageStatus")) {
			getGroupDetailsQuery.append(" ORDER BY IMAGE_STATE.THEVALUE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("contentStatus")) {
			getGroupDetailsQuery.append(" ORDER BY CONTENT_STATE.THEVALUE " + sortOrder);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SEARCH GROUP QUERY -- \n" + getGroupDetailsQuery.toString());
		}
		LOGGER.info("Exiting getGroupDetailsQuery() in Grouping XQueryConstant class.");
		return getGroupDetailsQuery.toString();
	}

	/** Method to get the Search Group Result Count query.
	 * 
	 * @return the query string
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/19/2016
	 *         Added By: Cognizant */
	public final String getGroupDetailsCountQuery(final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering getGroupDetailsCountQuery() in Grouping XQueryConstant class.");

		final StringBuffer getGroupDetailsCountQuery = new StringBuffer();
		getGroupDetailsCountQuery.append(" SELECT COUNT(*) TOTAL_COUNT FROM ADSE_GROUP_CATALOG GRP INNER JOIN ");
		getGroupDetailsCountQuery.append(" ADSE_REFERENCE_DATA CONTENT_STATE ON GROUP_CONTENT_STATUS_CODE = CONTENT_STATE.MDMID ");
		getGroupDetailsCountQuery.append(" AND CONTENT_STATE.ENTRY_TYPE = 'ContentState_Lookup' ");
		getGroupDetailsCountQuery.append(" INNER JOIN ");
		getGroupDetailsCountQuery.append(" ADSE_REFERENCE_DATA IMAGE_STATE ON GROUP_IMAGE_STATUS_CODE = IMAGE_STATE.MDMID ");
		getGroupDetailsCountQuery.append(" AND IMAGE_STATE.ENTRY_TYPE = 'ImageState_Lookup' ");
		if ((groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals(""))
				|| (groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals(""))
				|| (groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals(""))
				|| (groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals(""))
				|| (groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals(""))) {
			getGroupDetailsCountQuery.append(" ,ADSE_GROUP_CHILD_MAPPING GCM, ADSE_ITEM_CATALOG AIC ");
		}
		getGroupDetailsCountQuery
				.append(" WHERE                                                                                          ");
		getGroupDetailsCountQuery
				.append(" GRP.DELETED_FLAG = 'false'                                                                       ");
		if (groupSearchForm.getGroupId() != null && !groupSearchForm.getGroupId().trim().equals("")) {
			getGroupDetailsCountQuery.append(" AND GRP.MDMID = '");
			getGroupDetailsCountQuery.append(groupSearchForm.getGroupId());
			getGroupDetailsCountQuery.append("'");
		}
		if (groupSearchForm.getGroupName() != null && !groupSearchForm.getGroupName().trim().equals("")) {
			getGroupDetailsCountQuery.append(" AND UPPER(GROUP_NAME) LIKE '%");
			getGroupDetailsCountQuery.append(groupSearchForm.getGroupName().toUpperCase());
			getGroupDetailsCountQuery.append("%'");
		}
		if ((groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals(""))
				|| (groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals(""))
				|| (groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals(""))
				|| (groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals(""))
				|| (groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals(""))) {

			getGroupDetailsCountQuery.append(" AND GRP.MDMID = GCM.MDMID ");
			getGroupDetailsCountQuery.append(" AND GCM.COMPONENT_STYLE_ID = AIC.MDMID ");
			getGroupDetailsCountQuery.append(" AND AIC.DELETED_FLAG = 'false' ");
			getGroupDetailsCountQuery.append(" AND AIC.ENTRY_TYPE = 'Style' ");
			if (groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals("")) {
				getGroupDetailsCountQuery.append(" AND AIC.DEPT_ID IN (");
				getGroupDetailsCountQuery.append(getNumbersInCorrectFormat(groupSearchForm.getDepts()));
				getGroupDetailsCountQuery.append(")");
			}
			if (groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals("")) {
				getGroupDetailsCountQuery.append(" AND AIC.CLASS_ID IN (");
				getGroupDetailsCountQuery.append(getNumbersInCorrectFormat(groupSearchForm.getClasses()));
				getGroupDetailsCountQuery.append(")");
			}
			if (groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals("")) {
				getGroupDetailsCountQuery.append(" AND AIC.MDMID = '");
				getGroupDetailsCountQuery.append(groupSearchForm.getOrinNumber());
				getGroupDetailsCountQuery.append("'");
			}
			if (groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals("")) {
				getGroupDetailsCountQuery.append(" AND AIC.PRIMARY_SUPPLIER_ID = '");
				getGroupDetailsCountQuery.append(groupSearchForm.getSupplierSiteId());
				getGroupDetailsCountQuery.append("'");
			}
			if (groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals("")) {
				getGroupDetailsCountQuery.append(" AND AIC.PRIMARYSUPPLIERVPN = '");
				getGroupDetailsCountQuery.append(groupSearchForm.getVendor());
				getGroupDetailsCountQuery.append("'");
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SEARCH GROUP COUNT QUERY -- \n" + getGroupDetailsCountQuery.toString());
		}
		LOGGER.info("Exiting getGroupDetailsCountQuery() in Grouping XQueryConstant class.");
		return getGroupDetailsCountQuery.toString();
	}

	/** Method to get the Search Group Parent query.
	 * 
	 * @return the query string
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/19/2016
	 *         Added By: Cognizant */
	public final String getGroupDetailsQueryParent(final List<GroupSearchDTO> groupSearchDTOList, final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering getGroupDetailsQueryParent() in Grouping XQueryConstant class.");

		final StringBuffer getGroupDetailsQueryParent = new StringBuffer();
		getGroupDetailsQueryParent.append(" SELECT AGC.MDMID GROUP_ID, AGC.GROUP_NAME GROUP_NAME, AGC.ENTRY_TYPE GROUP_TYPE,"
				+ " CONTENT_STATE.THEVALUE CONTENT_STATE, START_DATE START_DATE, END_DATE END_DATE, "
				+ "GCM.COMPONENT_GROUPING_ID COMPONENT_GROUPING_ID, " + "IMAGE_STATE.THEVALUE IMAGE_STATE, EXIST_IN_GROUP CHILD_GROUP  ");
		getGroupDetailsQueryParent.append(" FROM ADSE_GROUP_CATALOG AGC INNER JOIN ");
		getGroupDetailsQueryParent.append(" ADSE_REFERENCE_DATA CONTENT_STATE ON GROUP_CONTENT_STATUS_CODE = CONTENT_STATE.MDMID ");
		getGroupDetailsQueryParent.append(" AND CONTENT_STATE.ENTRY_TYPE = 'ContentState_Lookup' ");
		getGroupDetailsQueryParent.append(" INNER JOIN ");
		getGroupDetailsQueryParent.append(" ADSE_REFERENCE_DATA IMAGE_STATE ON GROUP_IMAGE_STATUS_CODE = IMAGE_STATE.MDMID ");
		getGroupDetailsQueryParent.append(" AND IMAGE_STATE.ENTRY_TYPE = 'ImageState_Lookup' ");
		getGroupDetailsQueryParent
				.append(" , ADSE_GROUP_CHILD_MAPPING GCM                                                                                        ");
		getGroupDetailsQueryParent.append(" WHERE AGC.DELETED_FLAG = 'false'                   ");
		getGroupDetailsQueryParent
				.append(" AND AGC.MDMID = GCM.MDMID                                                                                                              ");
		final List<String> groupIdsList = getCommaSeparatedValues(groupSearchDTOList);
		if (groupIdsList.size() > 0) {
			int count = 0;
			for (Iterator<String> iterator = groupIdsList.iterator(); iterator.hasNext();) {
				final String strGroupIds = (String) iterator.next();
				if (count == 0) {
					getGroupDetailsQueryParent.append(" AND (GCM.COMPONENT_GROUPING_ID IN (" + strGroupIds + ")");
				} else {
					getGroupDetailsQueryParent.append(" OR GCM.COMPONENT_GROUPING_ID IN (" + strGroupIds + ")");
				}
				count++;
			}
			getGroupDetailsQueryParent.append(" )");
		}

		getGroupDetailsQueryParent.append(" AND COMPONENT_TYPE = 'Group'                       ");
		String sortOrder = "";
		if (groupSearchForm.getAscDescOrder() != null || !groupSearchForm.getAscDescOrder().trim().equals("")) {
			sortOrder = groupSearchForm.getAscDescOrder();
		}
		if (groupSearchForm.getSortColumn() == null || groupSearchForm.getSortColumn().trim().equals("")) {
			getGroupDetailsQueryParent.append(" ORDER BY AGC.ENTRY_TYPE, AGC.MDMID " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("groupId")) {
			getGroupDetailsQueryParent.append(" ORDER BY AGC.MDMID " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("groupName")) {
			getGroupDetailsQueryParent.append(" ORDER BY AGC.GROUP_NAME " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("startDate")) {
			getGroupDetailsQueryParent.append(" ORDER BY START_DATE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("endDate")) {
			getGroupDetailsQueryParent.append(" ORDER BY END_DATE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("groupType")) {
			getGroupDetailsQueryParent.append(" ORDER BY AGC.ENTRY_TYPE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("imageStatus")) {
			getGroupDetailsQueryParent.append(" ORDER BY IMAGE_STATE.THEVALUE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && groupSearchForm.getSortColumn().equals("contentStatus")) {
			getGroupDetailsQueryParent.append(" ORDER BY CONTENT_STATE.THEVALUE " + sortOrder);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SEARCH GROUP PARENT QUERY -- \n" + getGroupDetailsQueryParent.toString());
		}
		LOGGER.info("Exiting getGroupDetailsQueryParent() in Grouping XQueryConstant class.");
		return getGroupDetailsQueryParent.toString();
	}

	/** Method to get the Search Group Parent count query.
	 * 
	 * @return the query string
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/19/2016
	 *         Added By: Cognizant */
	public final String getGroupDetailsCountQueryParent(final List<GroupSearchDTO> groupSearchDTOList, final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering getGroupDetailsCountQueryParent() in Grouping XQueryConstant class.");

		final StringBuffer getGroupDetailsQueryParent = new StringBuffer();
		getGroupDetailsQueryParent.append(" SELECT COUNT(*) TOTAL_COUNT_PARENT ");
		getGroupDetailsQueryParent.append(" FROM ADSE_GROUP_CATALOG AGC INNER JOIN ");
		getGroupDetailsQueryParent.append(" ADSE_REFERENCE_DATA CONTENT_STATE ON GROUP_CONTENT_STATUS_CODE = CONTENT_STATE.MDMID ");
		getGroupDetailsQueryParent.append(" AND CONTENT_STATE.ENTRY_TYPE = 'ContentState_Lookup' ");
		getGroupDetailsQueryParent.append(" INNER JOIN ");
		getGroupDetailsQueryParent.append(" ADSE_REFERENCE_DATA IMAGE_STATE ON GROUP_IMAGE_STATUS_CODE = IMAGE_STATE.MDMID ");
		getGroupDetailsQueryParent.append(" AND IMAGE_STATE.ENTRY_TYPE = 'ImageState_Lookup' ");
		getGroupDetailsQueryParent
				.append(" , ADSE_GROUP_CHILD_MAPPING GCM                                                                                        ");
		getGroupDetailsQueryParent.append(" WHERE AGC.DELETED_FLAG = 'false'                  ");
		getGroupDetailsQueryParent
				.append(" AND AGC.MDMID = GCM.MDMID                                                                                                              ");
		final List<String> groupIdsList = getCommaSeparatedValues(groupSearchDTOList);
		if (groupIdsList.size() > 0) {
			int count = 0;
			for (Iterator<String> iterator = groupIdsList.iterator(); iterator.hasNext();) {
				final String strGroupIds = (String) iterator.next();
				if (count == 0) {
					getGroupDetailsQueryParent.append(" AND (GCM.COMPONENT_GROUPING_ID IN (" + strGroupIds + ")");
				} else {
					getGroupDetailsQueryParent.append(" OR GCM.COMPONENT_GROUPING_ID IN (" + strGroupIds + ")");
				}
				count++;
			}
			getGroupDetailsQueryParent.append(" )");
		}

		getGroupDetailsQueryParent.append(" AND COMPONENT_TYPE = 'Group'                   ");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SEARCH GROUP PARENT COUNT QUERY -- \n" + getGroupDetailsQueryParent.toString());
		}
		LOGGER.info("Exiting getGroupDetailsCountQueryParent() in Grouping XQueryConstant class.");
		return getGroupDetailsQueryParent.toString();
	}

	/** Method to get the Search Group Parent query Group List.
	 * 
	 * @return List<String>
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/20/2016
	 *         Added By: Cognizant */
	public final List<String> getCommaSeparatedValues(final List<GroupSearchDTO> groupSearchDTOList) {
		LOGGER.info("Entering getCommaSeparatedValues() in Grouping XQueryConstant class.");
		final List<String> listGroupIds = new ArrayList<String>();
		StringBuilder strGroupIdList = new StringBuilder();

		if (groupSearchDTOList.size() > 0) {
			int count = 0;
			for (GroupSearchDTO groupSearchDTO : groupSearchDTOList) {

				strGroupIdList.append("'" + groupSearchDTO.getGroupId() + "',");

				count++;

				if (count > 999) {

					listGroupIds.add(strGroupIdList.substring(0, strGroupIdList.length() - 1));
					count = 0;
					strGroupIdList = new StringBuilder();
				}
			}
		}
		if (strGroupIdList.length() > 0) {
			listGroupIds.add(strGroupIdList.substring(0, strGroupIdList.length() - 1));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Group Ids list size -- " + listGroupIds.size());
		}
		LOGGER.info("Exiting getCommaSeparatedValues() in Grouping XQueryConstant class.");
		return listGroupIds;
	}

	/** Gets the departments in correct format.
	 * 
	 * @param deptId
	 * @return finalString
	 * 
	 *         Method added For PIM Phase 2 - Search Group Modified by Cognizant
	 *         Date: 05/24/2016 */
	public final String getNumbersInCorrectFormat(final String ids) {
		LOGGER.info("Entering getNumbersInCorrectFormat() in Grouping XQueryConstant class.");
		String finalString = "";
		if (null != ids && ids.trim().length() > 0) {
			final String[] breakId = ids.split(",");
			for (int count = 0; count < breakId.length; count++) {
				finalString = finalString + "'" + breakId[count].trim() + "',";
			}
			if (finalString.length() > 0) {
				finalString = finalString.substring(0, finalString.length() - 1);
			}
		} else {
			finalString = "''";
		}
		LOGGER.info("Exiting getNumbersInCorrectFormat() in Grouping XQueryConstant class.");
		return finalString;

	}

	/** Gets the departments for Group Search.
	 * 
	 * @param deptId
	 * @return String
	 * 
	 *         Method added For PIM Phase 2 - Search Group Modified by Cognizant
	 *         Date: 02/25/2016 */
	public final String getLikeDepartmentDetailsForString() {
		LOGGER.info("Entering getLikeDepartmentDetailsForString() in Grouping XQueryConstant class.");

		final String getAllDepartmentQuery = " select DeptId DEPTID, DeptName DEPTNAME" + " from  ADSE_MERCHANDISE_HIERARCHY dept, "
				+ " XMLTABLE('for $dept in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec"
				+ " where fn:count($dept/System/Removal_Flag) eq 0 or $dept/System/Removal_Flag eq \"false\""
				+ "  return <out><dept_id>{$dept/Identifiers/RMS_Id}</dept_id>" + " <dept_name>{$dept/Name}</dept_name></out>' "
				+ "  passing dept.XML_DATA as \"XML_DATA\"" + " columns" + " DeptName varchar2(64) path '/out/dept_name',"
				+ " DeptId varchar2(64) path '/out/dept_id'" + " ) DEPT_NAME"
				+ " where dept.ENTRY_TYPE = 4 order by cast(DeptId as Integer)";

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SEARCH DEPARTMENT QUERY -- \n" + getAllDepartmentQuery);
		}
		LOGGER.info("Exiting getLikeDepartmentDetailsForString() in Grouping XQueryConstant class.");
		return getAllDepartmentQuery;

	}

	/** Gets the class for Group Search. Method added For PIM Phase 2 - Search
	 * Group Modified by Cognizant Date: 02/25/2016
	 * 
	 * @param deptids
	 * @return String */
	public final String getClassDetailsUsingDeptnumbers(final String deptids) {
		LOGGER.info("Entering getClassDetailsUsingDeptnumbers() in Grouping XQueryConstant class.");
		final StringBuilder queryfragment = new StringBuilder();

		queryfragment.append("  SELECT CLS_NAME.ClsId CLASS_ID, CLS_NAME.ClsName CLASS_NAME, CLS_NAME.Removal_Flag  ");
		queryfragment
				.append("  FROM ADSE_MERCHANDISE_HIERARCHY cls,                                                                              ");
		queryfragment
				.append("    XMLTABLE('                                                                                                      ");
		queryfragment
				.append("    for $cls in $XML_DATA/pim_category/entry                                                                        ");
		queryfragment
				.append("    return                                                                                                          ");
		queryfragment
				.append("    <out>                                                                                                           ");
		queryfragment
				.append("      <cls_id>{$XML_DATA/pim_category/entry/Merchandise_Hier_Spec/Identifiers/RMS_Id}</cls_id>                      ");
		queryfragment
				.append("      <cls_name>{$XML_DATA/pim_category/entry/Merchandise_Hier_Spec/Name}</cls_name>                                ");
		queryfragment
				.append("      <dept_id>{fn:tokenize($XML_DATA/pim_category/merchandise_category_header/full_path,\"\\||///\")[5]}</dept_id>    ");
		queryfragment
				.append("      <Removal_Flag>{$XML_DATA/pim_category/entry/Merchandise_Hier_Spec/System/Removal_Flag}</Removal_Flag>         ");
		queryfragment
				.append("    </out>'                                                                                                         ");
		queryfragment
				.append("    passing cls.XML_DATA AS \"XML_DATA\" columns                                                                      ");
		queryfragment
				.append("      ClsName VARCHAR2(64) path '/out/cls_name',                                                                    ");
		queryfragment
				.append("      ClsId VARCHAR2(64) path '/out/cls_id',                                                                        ");
		queryfragment
				.append("      DeptId VARCHAR2(10) path '/out/dept_id',                                                                      ");
		queryfragment
				.append("      Removal_Flag VARCHAR2(10) path '/out/Removal_Flag') CLS_NAME                                                 ");
		queryfragment
				.append("  WHERE cls.ENTRY_TYPE = 5                                                                                          ");
		queryfragment
				.append("  AND (CLS_NAME.Removal_Flag = 'false' or CLS_NAME.Removal_Flag is null)                                            ");
		queryfragment.append("  AND deptId in ( ");
		queryfragment.append(getNumbersInCorrectFormat(deptids));
		queryfragment.append("  )                                                                                       ");
		queryfragment
				.append("  ORDER BY ClsId                                                                                                    ");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SEARCH CLASS QUERY -- \n" + queryfragment.toString());
		}
		LOGGER.info("Exiting getClassDetailsUsingDeptnumbers() in Grouping XQueryConstant class.");
		return queryfragment.toString();

	}

	/** This method is used to get the Existing Group Attribute Details for Split
	 * Color from Database.
	 * 
	 * @return String */
	public final String getExistSplitColorDetails() {
		String getExistSplitColorDetails = "	SELECT                                                                           "
				+ "	DETAIL.COMPONENT_MDMID,                                                          "
				+ "	DETAIL.COMPONENT_DEFAULT,                                                        "
				+ "	ITEM.PARENT_MDMID, 			                                       " // if
				// null,
				// it
				// is
				// style
				+ "	ITEM.MDMID,                                                                      "
				+ "	ITEM.ENTRY_TYPE,                                                                 "
				+ "	ITEM.PRIMARYSUPPLIERVPN,                                                         "
				+ "	PET_XML.PRODUCT_NAME,                                                            "
				+ "	PET_XML.COLOR_CODE,                                                              "
				+ "	PET_XML.COLOR_DESC                                                               "
				+ "	FROM                                                                             "
				+ "	ADSE_ITEM_CATALOG ITEM,                                                          "
				+ "	ADSE_GROUP_CHILD_MAPPING DETAIL,                                                 "
				+ "	ADSE_PET_CATALOG PET,                                                            "
				+ "	XMLTABLE(                                                                        "
				+ "	  'let                                                                           "
				+ "	  $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,     "
				+ "	  $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code             "
				+ "	  return                                                                         "
				+ "	  <COLOR>                                                                        "
				+ "	  <COLOR_CODE>{$colorCode}</COLOR_CODE>                                          "
				+ "	  <COLOR_DESC>{$colordesc}</COLOR_DESC>                                          "
				+ "	  <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>  "
				+ "	  </COLOR>'                                                                      "
				+ "	  passing Pet.XML_DATA Columns COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',  "
				+ "	  COLOR_DESC                              VARCHAR2(20) path '/COLOR/COLOR_DESC'  "
				+ "	  , PRODUCT_NAME                          VARCHAR2(20) path                      "
				+ "	  '/COLOR/PRODUCT_NAME' ) PET_XML                                                "
				+ "	WHERE                                                                            "
				+ "	1=1                                                                              "
				+ "	AND PET.MDMID=ITEM.MDMID                                                         "
				+ "	AND (ITEM.MDMID = DETAIL.COMPONENT_MDMID                  " // /*To
																				// fetch
																				// Color
																				// data*/
				+ "	  OR ITEM.MDMID = DETAIL.COMPONENT_STYLE_ID)  " // /*To
																	// fetch
																	// Style
																	// data for
																	// that
																	// Color for
																	// Product
																	// name*/
				+ "	AND DETAIL.MDMID=:groupidSql  "; // /*StyleColor MDMID*/

		return getExistSplitColorDetails;
	}

	/** This method is used to get the Existing Group Attribute Details for Split
	 * SKU from Database.
	 * 
	 * @return String */
	public final String getExistSplitSkuDetails() {
		String getExistSplitSkuDetails = "	SELECT                                                "
				+ "	DETAIL.COMPONENT_MDMID,                               "
				+ "	DETAIL.COMPONENT_DEFAULT,                             "
				+ "	ITEM.PARENT_MDMID,                                    " // if
																			// null,
																			// it
																			// is
																			// style
				+ "	ITEM.MDMID,                                           " + "	ITEM.ENTRY_TYPE,                                      "
				+ "	ITEM.PRIMARYSUPPLIERVPN,                              " + "	ITEM_XML.PRODUCT_NAME,                                "
				+ "	ITEM_XML.COLOR_CODE,                                  " + "	ITEM_XML.COLOR_DESC,                                  "
				+ "	ITEM_XML.SIZEDESC                                     " + "	FROM                                                  "
				+ "	ADSE_ITEM_CATALOG ITEM,                               " + "	ADSE_GROUP_CHILD_MAPPING DETAIL,                      "
				+ "	ADSE_PET_CATALOG PET,                                 " + "	XMLTABLE(                                             "
				+ "	  'let                                                "
				+ "	  $colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,               "
				+ "	  $colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description, "
				+ "	  $size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,       "
				+ "	  $name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                         "
				+ "	  return                                                                "
				+ "	  <SPEC>                                                                "
				+ "	  <COLOR_CODE>{$colorcode}</COLOR_CODE>                                 "
				+ "	  <COLOR_DESC>{$colorname}</COLOR_DESC>                                 "
				+ "	  <SIZE>{$size}</SIZE>                                                  "
				+ "	  <PRODUCT_NAME>{$name}</PRODUCT_NAME>                                  "
				+ "	  </SPEC>'                                                              "
				+ "	  passing ITEM.XML_DATA Columns                                         "
				+ "	  COLOR_CODE 			VARCHAR2(5) path '/SPEC/COLOR_CODE',            "
				+ "	  COLOR_DESC            VARCHAR2(20) path '/SPEC/COLOR_DESC',           "
				+ "	  SIZEDESC              VARCHAR2(20) path '/SPEC/SIZE',                 "
				+ "	  PRODUCT_NAME          VARCHAR2(50) path '/SPEC/PRODUCT_NAME')         "
				+ "	  ITEM_XML                                                              "
				+ "	WHERE                                                                   "
				+ "	1=1                                                                     "
				+ "	AND PET.MDMID=ITEM.MDMID                                                " // To
																								// fetch
																								// Color
																								// data
				+ "	AND (ITEM.MDMID = DETAIL.COMPONENT_MDMID                                "
				/* +
				 * "	  OR ITEM.MDMID = DETAIL.COMPONENT_STYLE_ID)                          " */// To
																								// fetch
																								// Style
																								// data
																								// for
																								// that
																								// Color
																								// for
																								// Product
																								// name
				+ "	AND DETAIL.MDMID=:groupidSql                                             "; // StyleSKU
																								// MDMID

		return getExistSplitSkuDetails;
	}

}
