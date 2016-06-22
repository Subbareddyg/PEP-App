package com.belk.pep.constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.belk.pep.dto.GroupSearchDTO;
import com.belk.pep.form.GroupSearchForm;

/** The Class XqueryConstants.
 * 
 * @author AFUPYB3 */
public class XqueryConstants {



	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(XqueryConstants.class.getName());

	/** Instantiates a new xquery constants. */
	public XqueryConstants() {
		// To be implemented.
	}

	/** This method is used to build the Query to retrieve Group Header Details
	 * from Database against Group Id.
	 * 
	 * @return String */
	public static  final String getGroupHeaderDetails() {
		final String getGroupHeaderDetailsQuery = " "
				+ " SELECT                                                                                "
				+ "      AGC.MDMID GROUP_ID,                                                               "
				+ "      AGC.GROUP_NAME,                                                                   "
				+ "      AGCXML.Description,                                                               "
				+ "      AGC.START_DATE,                                                      "
				+ "      AGC.END_DATE,                                                        "
				+ "      AGCXML.CARS_Group_Type,                                                      "
				+ "      AGC.ENTRY_TYPE GROUP_TYPE, AGC.GROUP_OVERALL_STATUS_CODE,                          "
				+ "      AGC.CREATED_BY                                                                    "
				+ "      FROM ADSE_GROUP_CATALOG AGC,                                                      "
				+ "        XMLTABLE( 'let                                                                  "
				+ "        $Description:= /pim_entry/entry/Group_Ctg_Spec/Description,                     "
				+ "        $CARS_Group_Type:= /pim_entry/entry/Group_Ctg_Spec/CARS_Group_Type        "
				+ "        return                                         "
				+ "      <out>                                                                             "
				+ "          <Description>{$Description}</Description>                                     "
				+ "          <CARS_Group_Type>{$CARS_Group_Type}</CARS_Group_Type>                "
				+ "      </out>' passing AGC.XML_DATA                                                      "
				+ "        Columns                                                                         "
				+ "        Description CLOB path '/out/Description',                               "
				+ "        CARS_Group_Type VARCHAR2(50) path '/out/CARS_Group_Type') AGCXML          "
				+ "      WHERE                                                                             "
				+ "      MDMID = :groupIdSql AND DELETED_FLAG = 'false'                                     ";


		return getGroupHeaderDetailsQuery;
	}

	/** This method is used to build the Query to retrieve Split Color details to
	 * create Split Color Group.
	 * 
	 * @return String
	 * @param vendorStyleNo */
	public static  final String getSplitColorDetails(final String vendorStyleNo) {
		String getSplitColorDetailsQuery = " Select                                 "
				+ "  ITEM.MDMID, ITEM.PARENT_MDMID,                                                                      "
				+ "  ITEM.PRIMARYSUPPLIERVPN,                                                                            "
				+ "  PET_XML.PRODUCT_NAME,                                                                               "
				+ "  PET_XML.COLOR_CODE,                                                                                 "
				+ "  PET_XML.COLOR_DESC,                                                                                 "
				+ "  PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE, PET.ENTRY_TYPE                                    "
				+ "  FROM                                                                                                "
				+ "  ADSE_ITEM_CATALOG ITEM,                                                                             "
				+ "  ADSE_PET_CATALOG PET,                                                                                 "
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
			getSplitColorDetailsQuery = getSplitColorDetailsQuery + " AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID) = :mdmidSql";
		}

		return getSplitColorDetailsQuery;
	}

	/** This method is used to build the Query to retrieve Split SKU details to
	 * create Split SKU Group.
	 * 
	 * @param vendorStyleNo
	 * @return String */
	public static  final String getSplitSKUDetails(final String vendorStyleNo) {
		String getSplitSKUDetailsQuery = "   Select                                                                      "
				+ "  ITEM.MDMID,  ITEM.PARENT_MDMID,                                                                      "
				+ "  ITEM.PRIMARYSUPPLIERVPN,                                                                            "
				+ "  ITEM_XML.NAME,                                                                                      "
				+ "  ITEM_XML.COLOR_CODE,                                                                                "
				+ "  ITEM_XML.COLOR_NAME,                                                                                "
				+ "  ITEM_XML.SIZEDESC, ITEM_XML.SIZE_CODE,                                                              "
				+ "  PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE, PET.ENTRY_TYPE                                  "
				+ "  FROM                                                                                                "
				+ "  ADSE_ITEM_CATALOG ITEM,                                                                             "
				+ "  ADSE_PET_CATALOG PET,                                                                                "
				+ "  XMLTABLE('let                                                                                       "
				+ "  $colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,                   "
				+ "  $colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description,     "
				+ "  $size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,           "
				+ "  $sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,           "
				+ "  $name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                             "
				+ "  return                                                                                              "
				+ "      <SPEC>                                                                                          "
				+ "          <COLOR_CODE>{$colorcode}</COLOR_CODE>                                                       "
				+ "          <COLOR_NAME>{$colorname}</COLOR_NAME>                                                       "
				+ "          <SIZE>{$size}</SIZE>                                                                        "
				+ "          <SIZE_CODE>{$sizeCode}</SIZE_CODE>                                                                        "
				+ "          <NAME>{$name}</NAME>                                                                        "
				+ "      </SPEC>'                                                                                        "
				+ "  passing ITEM.XML_DATA                                                                               "
				+ "  Columns                                                                                             "
				+ "      COLOR_CODE VARCHAR2(5) path '/SPEC/COLOR_CODE',                                                 "
				+ "      COLOR_NAME VARCHAR2(20) path '/SPEC/COLOR_NAME',                                                "
				+ "      SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',                                                        "
				+ "      SIZE_CODE VARCHAR2(20) path '/SPEC/SIZE_CODE',                                                  "
				+ "      NAME VARCHAR2(50) path '/SPEC/NAME') ITEM_XML                                                   "
				+ "  WHERE                                                                                               "
				+ "      ITEM.ENTRY_TYPE in ('Style', 'StyleColor', 'SKU')                                               "
				+ "      AND ITEM.DELETED_FLAG= 'false'                                                                  "
				+ "      AND PET.MDMID=ITEM.MDMID                                                                        ";
		if (vendorStyleNo != null) {
			getSplitSKUDetailsQuery = getSplitSKUDetailsQuery + " AND item.PRIMARYSUPPLIERVPN = :styleIdSql";
		} else {
			getSplitSKUDetailsQuery = getSplitSKUDetailsQuery + " AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID) = :mdmidSql";
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
	public static  final String getGroupDetailsQuery(final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering getGroupDetailsQuery() in Grouping XQueryConstant class.");

		final StringBuilder getGroupDetailsQuery = new StringBuilder();
		getGroupDetailsQuery.append(" SELECT DISTINCT GRP.MDMID GROUP_ID, GRP.GROUP_NAME, GRP.ENTRY_TYPE GROUP_TYPE,"
				+ " CONTENT_STATE.THEVALUE CONTENT_STATE, GRP.START_DATE START_DATE, "
				+ "GRP.END_DATE END_DATE, IMAGE_STATE.THEVALUE IMAGE_STATE, EXIST_IN_GROUP CHILD_GROUP "
				+ "FROM ADSE_GROUP_CATALOG GRP INNER JOIN ");
		getGroupDetailsQuery.append(" ADSE_REFERENCE_DATA CONTENT_STATE ON GROUP_CONTENT_STATUS_CODE = CONTENT_STATE.MDMID ");
		getGroupDetailsQuery.append(" AND CONTENT_STATE.ENTRY_TYPE = 'ContentState_Lookup' ");
		getGroupDetailsQuery.append(" INNER JOIN ");
		getGroupDetailsQuery.append(" ADSE_REFERENCE_DATA IMAGE_STATE ON GROUP_IMAGE_STATUS_CODE = IMAGE_STATE.MDMID ");
		getGroupDetailsQuery.append(" AND IMAGE_STATE.ENTRY_TYPE = 'ImageState_Lookup' ");

		if ((groupSearchForm.getVendor() != null && !"".equals(groupSearchForm.getVendor().trim()))
				|| (groupSearchForm.getDepts() != null && !"".equals(groupSearchForm.getDepts().trim()))
				|| (groupSearchForm.getClasses() != null && !"".equals(groupSearchForm.getClasses().trim()))
				|| (groupSearchForm.getOrinNumber() != null && !"".equals(groupSearchForm.getOrinNumber().trim()))
				|| (groupSearchForm.getSupplierSiteId() != null && !"".equals(groupSearchForm.getSupplierSiteId().trim()))) {
			getGroupDetailsQuery.append(" ,ADSE_GROUP_CHILD_MAPPING GCM, ADSE_ITEM_CATALOG AIC ");
		}

		getGroupDetailsQuery
				.append(" WHERE                                                                                          ");
		getGroupDetailsQuery
				.append(" GRP.DELETED_FLAG = 'false'                                                                       ");
		if (groupSearchForm.getGroupId() != null && !"".equals(groupSearchForm.getGroupId().trim())) {
			getGroupDetailsQuery.append(" AND GRP.MDMID = '");
			getGroupDetailsQuery.append(groupSearchForm.getGroupId());
			getGroupDetailsQuery.append("'");
		}
		if (groupSearchForm.getGroupName() != null && !"".equals(groupSearchForm.getGroupName().trim())) {
			getGroupDetailsQuery.append(" AND UPPER(GROUP_NAME) LIKE '%");
			getGroupDetailsQuery.append(groupSearchForm.getGroupName().toUpperCase());
			getGroupDetailsQuery.append("%'");
		}
		if ((groupSearchForm.getVendor() != null && !"".equals(groupSearchForm.getVendor().trim()))
				|| (groupSearchForm.getDepts() != null && !"".equals(groupSearchForm.getDepts().trim()))
				|| (groupSearchForm.getClasses() != null && !"".equals(groupSearchForm.getClasses().trim()))
				|| (groupSearchForm.getOrinNumber() != null && !"".equals(groupSearchForm.getOrinNumber().trim()))
				|| (groupSearchForm.getSupplierSiteId() != null && !"".equals(groupSearchForm.getSupplierSiteId().trim()))) {

			getGroupDetailsQuery.append(" AND GRP.MDMID = GCM.MDMID ");
			getGroupDetailsQuery.append(" AND GCM.COMPONENT_STYLE_ID = AIC.MDMID ");
			getGroupDetailsQuery.append(" AND AIC.DELETED_FLAG = 'false' ");
			getGroupDetailsQuery.append(" AND AIC.ENTRY_TYPE = 'Style' ");
			if (groupSearchForm.getDepts() != null && !"".equals(groupSearchForm.getDepts().trim())) {
				getGroupDetailsQuery.append(" AND AIC.DEPT_ID IN (");
				getGroupDetailsQuery.append(getNumbersInCorrectFormat(groupSearchForm.getDepts()));
				getGroupDetailsQuery.append(")");
			}
			if (groupSearchForm.getClasses() != null && !"".equals(groupSearchForm.getClasses().trim())) {
				getGroupDetailsQuery.append(" AND AIC.CLASS_ID IN (");
				getGroupDetailsQuery.append(getNumbersInCorrectFormat(groupSearchForm.getClasses()));
				getGroupDetailsQuery.append(")");
			}
			if (groupSearchForm.getOrinNumber() != null && !"".equals(groupSearchForm.getOrinNumber().trim())) {
				getGroupDetailsQuery.append(" AND AIC.MDMID = '");
				getGroupDetailsQuery.append(groupSearchForm.getOrinNumber());
				getGroupDetailsQuery.append("'");
			}
			if (groupSearchForm.getSupplierSiteId() != null && !"".equals(groupSearchForm.getSupplierSiteId().trim())) {
				getGroupDetailsQuery.append(" AND AIC.PRIMARY_SUPPLIER_ID = '");
				getGroupDetailsQuery.append(groupSearchForm.getSupplierSiteId());
				getGroupDetailsQuery.append("'");
			}
			if (groupSearchForm.getVendor() != null && !"".equals(groupSearchForm.getVendor().trim())) {
				getGroupDetailsQuery.append(" AND AIC.PRIMARYSUPPLIERVPN = '");
				getGroupDetailsQuery.append(groupSearchForm.getVendor());
				getGroupDetailsQuery.append("'");
			}
		}

		String sortOrder = "";
		if (groupSearchForm.getAscDescOrder() != null || !"".equals(groupSearchForm.getAscDescOrder().trim())) {
			sortOrder = groupSearchForm.getAscDescOrder();
		}
		if (groupSearchForm.getSortColumn() == null || "".equals(groupSearchForm.getSortColumn().trim())) {
			getGroupDetailsQuery.append(" ORDER BY GRP.ENTRY_TYPE, GRP.MDMID " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "groupId".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQuery.append(" ORDER BY GRP.MDMID " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "groupName".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQuery.append(" ORDER BY GROUP_NAME " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "startDate".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQuery.append(" ORDER BY GRP.START_DATE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "endDate".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQuery.append(" ORDER BY GRP.END_DATE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "groupType".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQuery.append(" ORDER BY GRP.ENTRY_TYPE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "imageStatus".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQuery.append(" ORDER BY IMAGE_STATE.THEVALUE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "contentStatus".equals(groupSearchForm.getSortColumn())) {
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
	public static  final String getGroupDetailsCountQuery(final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering getGroupDetailsCountQuery() in Grouping XQueryConstant class.");

		final StringBuilder getGroupDetailsCountQuery = new StringBuilder();
		getGroupDetailsCountQuery.append(" SELECT COUNT(DISTINCT GRP.MDMID) TOTAL_COUNT FROM ADSE_GROUP_CATALOG GRP INNER JOIN ");
		getGroupDetailsCountQuery.append(" ADSE_REFERENCE_DATA CONTENT_STATE ON GROUP_CONTENT_STATUS_CODE = CONTENT_STATE.MDMID ");
		getGroupDetailsCountQuery.append(" AND CONTENT_STATE.ENTRY_TYPE = 'ContentState_Lookup' ");
		getGroupDetailsCountQuery.append(" INNER JOIN ");
		getGroupDetailsCountQuery.append(" ADSE_REFERENCE_DATA IMAGE_STATE ON GROUP_IMAGE_STATUS_CODE = IMAGE_STATE.MDMID ");
		getGroupDetailsCountQuery.append(" AND IMAGE_STATE.ENTRY_TYPE = 'ImageState_Lookup' ");
		if ((groupSearchForm.getVendor() != null && !"".equals(groupSearchForm.getVendor().trim()))
				|| (groupSearchForm.getDepts() != null && !"".equals(groupSearchForm.getDepts().trim()))
				|| (groupSearchForm.getClasses() != null && !"".equals(groupSearchForm.getClasses().trim()))
				|| (groupSearchForm.getOrinNumber() != null && !"".equals(groupSearchForm.getOrinNumber().trim()))
				|| (groupSearchForm.getSupplierSiteId() != null && !"".equals(groupSearchForm.getSupplierSiteId().trim()))) {
			getGroupDetailsCountQuery.append(" ,ADSE_GROUP_CHILD_MAPPING GCM, ADSE_ITEM_CATALOG AIC ");
		}
		getGroupDetailsCountQuery
				.append(" WHERE                                                                                          ");
		getGroupDetailsCountQuery
				.append(" GRP.DELETED_FLAG = 'false'                                                                       ");
		if (groupSearchForm.getGroupId() != null && !"".equals(groupSearchForm.getGroupId().trim())) {
			getGroupDetailsCountQuery.append(" AND GRP.MDMID = '");
			getGroupDetailsCountQuery.append(groupSearchForm.getGroupId());
			getGroupDetailsCountQuery.append("'");
		}
		if (groupSearchForm.getGroupName() != null && !"".equals(groupSearchForm.getGroupName().trim())) {
			getGroupDetailsCountQuery.append(" AND UPPER(GROUP_NAME) LIKE '%");
			getGroupDetailsCountQuery.append(groupSearchForm.getGroupName().toUpperCase());
			getGroupDetailsCountQuery.append("%'");
		}
		if ((groupSearchForm.getVendor() != null && !"".equals(groupSearchForm.getVendor().trim()))
				|| (groupSearchForm.getDepts() != null && !"".equals(groupSearchForm.getDepts().trim()))
				|| (groupSearchForm.getClasses() != null && !"".equals(groupSearchForm.getClasses().trim()))
				|| (groupSearchForm.getOrinNumber() != null && !"".equals(groupSearchForm.getOrinNumber().trim()))
				|| (groupSearchForm.getSupplierSiteId() != null && !"".equals(groupSearchForm.getSupplierSiteId().trim()))) {

			getGroupDetailsCountQuery.append(" AND GRP.MDMID = GCM.MDMID ");
			getGroupDetailsCountQuery.append(" AND GCM.COMPONENT_STYLE_ID = AIC.MDMID ");
			getGroupDetailsCountQuery.append(" AND AIC.DELETED_FLAG = 'false' ");
			getGroupDetailsCountQuery.append(" AND AIC.ENTRY_TYPE = 'Style' ");
			if (groupSearchForm.getDepts() != null && !"".equals(groupSearchForm.getDepts().trim())) {
				getGroupDetailsCountQuery.append(" AND AIC.DEPT_ID IN (");
				getGroupDetailsCountQuery.append(getNumbersInCorrectFormat(groupSearchForm.getDepts()));
				getGroupDetailsCountQuery.append(")");
			}
			if (groupSearchForm.getClasses() != null && !"".equals(groupSearchForm.getClasses().trim())) {
				getGroupDetailsCountQuery.append(" AND AIC.CLASS_ID IN (");
				getGroupDetailsCountQuery.append(getNumbersInCorrectFormat(groupSearchForm.getClasses()));
				getGroupDetailsCountQuery.append(")");
			}
			if (groupSearchForm.getOrinNumber() != null && !"".equals(groupSearchForm.getOrinNumber().trim())) {
				getGroupDetailsCountQuery.append(" AND AIC.MDMID = '");
				getGroupDetailsCountQuery.append(groupSearchForm.getOrinNumber());
				getGroupDetailsCountQuery.append("'");
			}
			if (groupSearchForm.getSupplierSiteId() != null && !"".equals(groupSearchForm.getSupplierSiteId().trim())) {
				getGroupDetailsCountQuery.append(" AND AIC.PRIMARY_SUPPLIER_ID = '");
				getGroupDetailsCountQuery.append(groupSearchForm.getSupplierSiteId());
				getGroupDetailsCountQuery.append("'");
			}
			if (groupSearchForm.getVendor() != null && !"".equals(groupSearchForm.getVendor().trim())) {
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
	public static  final String getGroupDetailsQueryParent(final List<GroupSearchDTO> groupSearchDTOList, final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering getGroupDetailsQueryParent() in Grouping XQueryConstant class.");

		final StringBuilder getGroupDetailsQueryParent = new StringBuilder();
		getGroupDetailsQueryParent.append(" SELECT DISTINCT AGC.MDMID GROUP_ID, AGC.GROUP_NAME GROUP_NAME, AGC.ENTRY_TYPE GROUP_TYPE,"
				+ " CONTENT_STATE.THEVALUE CONTENT_STATE, START_DATE START_DATE, END_DATE END_DATE, "
				+ "GCM.COMPONENT_GROUPING_ID COMPONENT_GROUPING_ID, "
				+ "IMAGE_STATE.THEVALUE IMAGE_STATE, EXIST_IN_GROUP CHILD_GROUP  ");
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

		getGroupDetailsQueryParent.append(" AND PEP_COMPONENT_TYPE = 'Group'                       ");
		String sortOrder = "";
		if (groupSearchForm.getAscDescOrder() != null || !"".equals(groupSearchForm.getAscDescOrder().trim())) {
			sortOrder = groupSearchForm.getAscDescOrder();
		}
		if (groupSearchForm.getSortColumn() == null || "".equals(groupSearchForm.getSortColumn().trim())) {
			getGroupDetailsQueryParent.append(" ORDER BY AGC.ENTRY_TYPE, AGC.MDMID " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "groupId".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQueryParent.append(" ORDER BY AGC.MDMID " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "groupName".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQueryParent.append(" ORDER BY AGC.GROUP_NAME " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "startDate".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQueryParent.append(" ORDER BY START_DATE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "endDate".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQueryParent.append(" ORDER BY END_DATE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "groupType".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQueryParent.append(" ORDER BY AGC.ENTRY_TYPE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "imageStatus".equals(groupSearchForm.getSortColumn())) {
			getGroupDetailsQueryParent.append(" ORDER BY IMAGE_STATE.THEVALUE " + sortOrder);
		} else if (groupSearchForm.getSortColumn() != null && "contentStatus".equals(groupSearchForm.getSortColumn()) ){
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
	public static  final String getGroupDetailsCountQueryParent(final List<GroupSearchDTO> groupSearchDTOList) {

		LOGGER.info("Entering getGroupDetailsCountQueryParent() in Grouping XQueryConstant class.");

		final StringBuilder getGroupDetailsQueryParent = new StringBuilder();
		getGroupDetailsQueryParent.append(" SELECT COUNT(DISTINCT AGC.MDMID) TOTAL_COUNT_PARENT ");
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

		getGroupDetailsQueryParent.append(" AND PEP_COMPONENT_TYPE = 'Group'                   ");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SEARCH GROUP PARENT COUNT QUERY -- \n" + getGroupDetailsQueryParent.toString());
		}
		LOGGER.info("Exiting getGroupDetailsCountQueryParent() in Grouping XQueryConstant class.");
		return getGroupDetailsQueryParent.toString();
	}

	/** Method to get the Search Group Parent query Group List.
	 * 
	 * @return List
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/20/2016
	 *         Added By: Cognizant */
	public static  final List<String> getCommaSeparatedValues(final List<GroupSearchDTO> groupSearchDTOList) {
		LOGGER.info("Entering getCommaSeparatedValues() in Grouping XQueryConstant class.");
		final List<String> listGroupIds = new ArrayList<>();
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
	public static  final String getNumbersInCorrectFormat(final String ids) {
		LOGGER.info("Entering getNumbersInCorrectFormat() in Grouping XQueryConstant class.");
		String finalString = "";
		if (null != ids && ids.trim().length() > 0) {
			final String[] breakId = ids.split(",");
			for (int count = 0; count < breakId.length; count++) {
				StringBuilder strBuilder = new StringBuilder();
				strBuilder.append(finalString);
				strBuilder.append("'");
				strBuilder.append(breakId[count].trim());
				strBuilder.append("',");
				finalString =strBuilder.toString();
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
	public static  final String getLikeDepartmentDetailsForString() {
		LOGGER.info("Entering getLikeDepartmentDetailsForString() in Grouping XQueryConstant class.");

		final String getAllDepartmentQuery = " select DeptId DEPTID, DeptName DEPTNAME" + " from  ADSE_MERCHANDISE_HIERARCHY dept, "
				+ " XMLTABLE('for $dept in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec"
				+ " where fn:count($dept/System/Removal_Flag) eq 0 or $dept/System/Removal_Flag eq \"false\""
				+ "  return <out><dept_id>{$dept/Identifiers/RMS_Id}</dept_id>" + " <dept_name>{$dept/Name}</dept_name></out>' "
				+ "  passing dept.XML_DATA as \"XML_DATA\"" + " columns" + " DeptName varchar2(64) path '/out/dept_name',"
				+ " DeptId varchar2(64) path '/out/dept_id'" + " ) DEPT_NAME"
				+ " where dept.ENTRY_TYPE = '4' order by cast(DeptId as Integer)";

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
	public static  final String getClassDetailsUsingDeptnumbers(final String deptids) {
		LOGGER.info("Entering getClassDetailsUsingDeptnumbers() in Grouping XQueryConstant class.");
		final StringBuilder queryfragment = new StringBuilder();

		queryfragment.append("  SELECT /*+ INDEX(cls MERCH_HIER_IDX2) */ CLS_NAME.ClsId CLASS_ID, CLS_NAME.ClsName CLASS_NAME, CLS_NAME.Removal_Flag  ");
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
				.append("  WHERE cls.ENTRY_TYPE = '5'                                                                                         ");
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
	public static  final String getExistSplitColorDetails() {
		String getExistSplitColorDetails = "	SELECT                                                                           "
				+ "	DETAIL.COMPONENT_MDMID,                                                          "
				+ "	DETAIL.COMPONENT_DEFAULT,                                                        "
				+ "	ITEM.PARENT_MDMID, 			                                       " // if null, it is style
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
				+ "	AND (ITEM.MDMID = DETAIL.COMPONENT_MDMID                  " // /*To fetch Color data*/
				+ "	  OR ITEM.MDMID = DETAIL.COMPONENT_STYLE_ID)  " // /*To fetch Style data for that Color for Product name*/
				+ "	AND DETAIL.MDMID=:groupidSql  ORDER BY ITEM.MDMID "; // /*StyleColor MDMID*/

		return getExistSplitColorDetails;
	}

	/** This method is used to get the Existing Group Attribute Details for Split
	 * SKU from Database.
	 * 
	 * @return String */
	public static  final String getExistSplitSkuDetails() {
		String getExistSplitSkuDetails = "	SELECT                                                "
				+ "	DETAIL.COMPONENT_MDMID,                               "
				+ "	DETAIL.COMPONENT_DEFAULT,                             "
				+ "	ITEM.PARENT_MDMID,                                    " // if null, it is style
				+ "	ITEM.MDMID,                                           "
				+ "	ITEM.ENTRY_TYPE,                                      "
				+ "	ITEM.PRIMARYSUPPLIERVPN,                              "
				+ "	ITEM_XML.PRODUCT_NAME,                                "
				+ "	ITEM_XML.COLOR_CODE,                                  "
				+ "	ITEM_XML.COLOR_DESC,                                  "
				+ "	ITEM_XML.SIZEDESC, ITEM_XML.SIZE_CODE                 "
				+ "	FROM                                                  "
				+ "	ADSE_ITEM_CATALOG ITEM,                               "
				+ "	ADSE_GROUP_CHILD_MAPPING DETAIL,                      "
				+ "	ADSE_PET_CATALOG PET,                                 "
				+ "	XMLTABLE(                                             "
				+ "	  'let                                                "
				+ "	  $colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,               "
				+ "	  $colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description, "
				+ "	  $size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,       "
				+ "	  $sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,       "
				+ "	  $name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                         "
				+ "	  return                                                                "
				+ "	  <SPEC>                                                                "
				+ "	  <COLOR_CODE>{$colorcode}</COLOR_CODE>                                 "
				+ "	  <COLOR_DESC>{$colorname}</COLOR_DESC>                                 "
				+ "	  <SIZE>{$size}</SIZE>                                                  "
				+ "	  <SIZE_CODE>{$sizeCode}</SIZE_CODE>                                                  "
				+ "	  <PRODUCT_NAME>{$name}</PRODUCT_NAME>                                  "
				+ "	  </SPEC>'                                                              "
				+ "	  passing ITEM.XML_DATA Columns                                         "
				+ "	  COLOR_CODE 			VARCHAR2(5) path '/SPEC/COLOR_CODE',            "
				+ "	  COLOR_DESC            VARCHAR2(20) path '/SPEC/COLOR_DESC',           "
				+ "	  SIZEDESC              VARCHAR2(20) path '/SPEC/SIZE',                 "
				+ "	  SIZE_CODE              VARCHAR2(20) path '/SPEC/SIZE_CODE',                 "
				+ "	  PRODUCT_NAME          VARCHAR2(50) path '/SPEC/PRODUCT_NAME')         "
				+ "	  ITEM_XML                                                              "
				+ "	WHERE                                                                   "
				+ "	1=1                                                                     "
				+ "	AND PET.MDMID=ITEM.MDMID                                                " // To fetch Color data
				+ "	AND (ITEM.MDMID = DETAIL.COMPONENT_MDMID)                                "
				/* + "	  OR ITEM.MDMID = DETAIL.COMPONENT_STYLE_ID)                          " */// To fetch Style data for that Color for Product name
				+ "	AND DETAIL.MDMID=:groupidSql                                             "; // StyleSKU MDMID

		return getExistSplitSkuDetails;
	}

	/** This method is used to get the Existing Group Attribute Details for Consolidate Product Grouping.
	 * 
	 * @return String */
	public static  final String getExistCPGDetails() {
		String getExistCPGDetails = "	SELECT                                                "
				+"	DETAIL.COMPONENT_STYLE_ID,                               "
				+"	DETAIL.COMPONENT_DEFAULT,                             "
				+"	ITEM.PARENT_MDMID,             " // -- if null, it is style
				+"	ITEM.MDMID,                                           "
				+"	ITEM.ENTRY_TYPE,                                      "
				+"	ITEM.PRIMARYSUPPLIERVPN,                              "
				+"	PET_XML.PRODUCT_NAME,                                 "
				+"	PET_XML.COLOR_CODE,                                   "
				+"	PET_XML.COLOR_DESC,                                   "
				+"	ITEM.CLASS_ID                                         "
				+"	FROM                                                  "
				+"	ADSE_ITEM_CATALOG ITEM,                               "
				+"	ADSE_GROUP_CHILD_MAPPING DETAIL,                      "
				+"	ADSE_PET_CATALOG PET,                                 "
				+"	XMLTABLE(                                             "
				+"	  'let                                                "                                             
				+"	  $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,     "                       
				+"	  $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code             "                   
				+"	  return                                                                         "                       
				+"	  <COLOR>                                                                        "                           
				+"	  <COLOR_CODE>{$colorCode}</COLOR_CODE>                                          "                       
				+"	  <COLOR_DESC>{$colordesc}</COLOR_DESC>                                          "                       
				+"	  <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>  "                   
				+"	  </COLOR>'                                                                      "
				+"	  passing Pet.XML_DATA Columns COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',  "
				+"	  COLOR_DESC                              VARCHAR2(20) path '/COLOR/COLOR_DESC',  "
				+"	  PRODUCT_NAME                          VARCHAR2(20) path '/COLOR/PRODUCT_NAME'   "
				+"	   ) PET_XML                         "
				+"                                                            "
				+"	WHERE                                                     "
				+"	1=1                                                       "
				+"	AND PET.MDMID=ITEM.MDMID                                  "
				+"	AND ITEM.MDMID like CONCAT(DETAIL.COMPONENT_MDMID,'%')    "
				+"	AND DETAIL.MDMID=:groupidSql ORDER BY ITEM.ENTRY_TYPE         ";


		return getExistCPGDetails;
	}
	


	/** This method is used to get the Existing Group Attribute Details for Group By Size Grouping.
	 * 
	 * @return String */
	public static  final String getExistGBSDetails() {
		String getExistGBSDetails = 		"	Select                           "
				+"		DETAIL.COMPONENT_MDMID, DETAIL.COMPONENT_DEFAULT,        "
				+"		ITEM.MDMID,  ITEM.PARENT_MDMID, PET.ENTRY_TYPE,          "                                                          
				+"		ITEM.PRIMARYSUPPLIERVPN,                                 "                                          
				+"		ITEM_XML.NAME,                                           "                                          
				+"		ITEM_XML.COLOR_CODE,                                     "                                          
				+"		ITEM_XML.COLOR_NAME,                                     "                                          
				+"		ITEM_XML.SIZEDESC, ITEM_XML.SIZE_CODE,                   "                                          
				+"		PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE       "                          
				+"		FROM                                                     "                                          
				+"		ADSE_ITEM_CATALOG ITEM,                                  "                                          
				+"		ADSE_PET_CATALOG PET,                                    "
				+"	ADSE_GROUP_CHILD_MAPPING DETAIL,                             "
				+"		XMLTABLE('let                                            "                                          
				+"		$colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,                "  
				+"		$colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description,  "  
				+"		$size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,        "  
				+"		$sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,                  "
				+"		$name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short    "                                        
				+"		return                                                     "                                        
				+"		    <SPEC>                                                 "                                        
				+"		        <COLOR_CODE>{$colorcode}</COLOR_CODE>              "                                        
				+"		        <COLOR_NAME>{$colorname}</COLOR_NAME>              "                                        
				+"		        <SIZE>{$size}</SIZE>                               "                                        
				+"		        <SIZE_CODE>{$sizeCode}</SIZE_CODE>                 "                                                      
				+"		        <NAME>{$name}</NAME>                               "                                        
				+"		    </SPEC>'                                               "                                        
				+"		passing ITEM.XML_DATA                                      "                                        
				+"		Columns                                                    "                                        
				+"		    COLOR_CODE VARCHAR2(5) path '/SPEC/COLOR_CODE',        "                                        
				+"		    COLOR_NAME VARCHAR2(20) path '/SPEC/COLOR_NAME',       "                                        
				+"		    SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',               "                                        
				+"		    SIZE_CODE VARCHAR2(20) path '/SPEC/SIZE_CODE',         "                                        
				+"		    NAME VARCHAR2(50) path '/SPEC/NAME') ITEM_XML          "                                        
				+"		WHERE                                                      "
				+"	  1=1                                                          "           
				+"	  AND PET.MDMID = ITEM.MDMID                                   "
				+"	  AND ITEM.ENTRY_TYPE in ('SKU')                               "
				+"	  AND (NVL(ITEM.PARENT_MDMID,ITEM.MDMID) = DETAIL.COMPONENT_MDMID)                    "           
				+"	  AND DETAIL.MDMID =:groupidSql     ";
				//+"	  ORDER BY ITEM.PARENT_MDMID     ";


		return getExistGBSDetails;
	}

	/** This method is used to get the New Search Group Attribute Details for Consolidate Product Grouping.
	 * @return String 
	 **/
	public static  final String getNewCPGDetails(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch,	final String supplierSiteIdSearch, final String upcNoSearch, 
			final String deptNoSearch, final String classNoSearch) {
		String getNewCPGDetails = "	SELECT                                                              "
				+"	SEARCH.PARENT_MDMID,                                                                "
				+"	SEARCH.MDMID,                                                                       "
				+"	SEARCH.ENTRY_TYPE, SEARCH.CLASS_ID,                                                  "
				+"	SEARCH.PRIMARYSUPPLIERVPN,                                                          "
				+"	PET_XML.PRODUCT_NAME,                                                               "
				+"	PET_XML.COLOR_CODE,                                                                 "
				+"  PET.EXIST_IN_GROUP ALREADY_IN_GROUP,                                           "
				+"	PET_XML.COLOR_DESC,                                                                  "
				+"	  CASE WHEN AGCM.MDMID is NULL THEN 'N'                                             "
				+"	  ELSE 'Y' END EXIST_IN_SAME_GROUP                                                  "
				+"	FROM                                                                                "
				+"	ADSE_PET_CATALOG PET,                                                               "
				+"	XMLTABLE(                                                                           "
				+"	  'let                                                                              "
				+"	  $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,        "
				+"	  $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code                "
				+"	  return                                                                            "
				+"	  <COLOR>                                                                           "
				+"	  <COLOR_CODE>{$colorCode}</COLOR_CODE>                                             "
				+"	  <COLOR_DESC>{$colordesc}</COLOR_DESC>                                             "
				+"	  <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>     "
				+"	  </COLOR>'                                                                         "
				+"	  passing Pet.XML_DATA Columns                                                      "
				+"	  COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',                                  "
				+"	  COLOR_DESC VARCHAR2(20) path '/COLOR/COLOR_DESC',                                 "
				+"	  PRODUCT_NAME VARCHAR2(20) path '/COLOR/PRODUCT_NAME' ) PET_XML,                   "
				+"	  ADSE_ITEM_CATALOG SEARCH                                                          "
				+"	  LEFT  OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM         			           "
				+"	    ON SEARCH.MDMID =AGCM.COMPONENT_STYLE_ID             				       "
				+"	    AND AGCM.MDMID = :groupIdSql                 							   "
				+"	WHERE                                                                               "
				+"	1=1                                                                                 "
				+"	AND PET.MDMID=SEARCH.MDMID                                                          "
				+"	AND SEARCH.DELETED_FLAG ='false' AND SEARCH.ENTRY_TYPE in ('Style','StyleColor')    ";


		if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
			getNewCPGDetails = getNewCPGDetails + " AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ";
		} 
		if (null != styleOrin && !("").equals(styleOrin.trim())) {
			getNewCPGDetails = getNewCPGDetails + " AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID) =:mdmidSql ";
		}
		if (null != deptNoSearch && !("").equals(deptNoSearch.trim())) {
			getNewCPGDetails = getNewCPGDetails + " AND SEARCH.DEPT_ID IN (" + deptNoForInSearch + ")";
		}
		if (null != classNoSearch && !("").equals(classNoSearch.trim())) {
			getNewCPGDetails = getNewCPGDetails + " AND SEARCH.CLASS_ID IN (" + classNoForInSearch + ")";
		}
		if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
			getNewCPGDetails = getNewCPGDetails + " AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ";
		}
		if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
			getNewCPGDetails = getNewCPGDetails + " AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = " +
					"(SELECT PARENT_MDMID FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql)";
		}
		getNewCPGDetails = getNewCPGDetails + " ORDER BY SEARCH.MDMID";


		return getNewCPGDetails;
	}

	/** This method is used to get the SKU Count for a Style Orin.
	 * @return String 
	 **/
	public static  final String getSKUCount(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch,	final String supplierSiteIdSearch, final String upcNoSearch, 
			final String deptNoSearch, final String classNoSearch) {
		String getNewGBSDetails = "	Select                                                                                 "
				+"		COUNT(ITEM.PARENT_MDMID) COUNT, ITEM.PARENT_MDMID                                                  "                 
				+"		FROM ADSE_ITEM_CATALOG ITEM,                                                                       " 
				+"		ADSE_PET_CATALOG PET                                                                               " 
				+"		WHERE                                                                                              " 
				+"		    ITEM.ENTRY_TYPE in ('SKU')                                                                     "
				+"		    AND ITEM.DELETED_FLAG= 'false'                                                                 " 
				+"		    AND PET.MDMID=ITEM.MDMID                                                                       ";
		
				if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND ITEM.PRIMARYSUPPLIERVPN =:styleIdSql ";
				} 
				if (null != styleOrin && !("").equals(styleOrin.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID) =:mdmidSql ";
				}
				if (null != deptNoSearch && !("").equals(deptNoSearch.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND ITEM.DEPT_ID IN (" + deptNoForInSearch + ")";
				}
				if (null != classNoSearch && !("").equals(classNoSearch.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND ITEM.CLASS_ID IN (" + classNoForInSearch + ")";
				}
				if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND ITEM.PRIMARY_SUPPLIER_ID =:supplierIdSql ";
				}
				if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID)  = " +
							"(SELECT PARENT_MDMID FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql)";
				}
				getNewGBSDetails = getNewGBSDetails + " group by ITEM.PARENT_MDMID having COUNT(ITEM.PARENT_MDMID) > 1";
				//getNewGBSDetails = getNewGBSDetails + " ORDER BY ITEM.PARENT_MDMID";

		return getNewGBSDetails;
	}

	/** This method is used to get the New Search Group Attribute Details for GBS Grouping.
	 * @return String 
	 **/
	public static  final String getNewGBSDetails(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch,	final String supplierSiteIdSearch, final String upcNoSearch, 
			final String deptNoSearch, final String classNoSearch) {
		String getNewGBSDetails = "	Select                                                                                 "
				+"		ITEM.MDMID,  ITEM.PARENT_MDMID, PET.ENTRY_TYPE,                                                    "                 
				+"		ITEM.PRIMARYSUPPLIERVPN,                                                                           " 
				+"		ITEM_XML.NAME,                                                                                     " 
				+"		ITEM_XML.COLOR_CODE,                                                                               " 
				+"		ITEM_XML.COLOR_NAME,                                                                               " 
				+"		ITEM_XML.SIZEDESC, ITEM_XML.SIZE_CODE,                                                             " 
				+"		PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE,                                                 "
				+"		CASE WHEN AGCM.MDMID is NULL THEN 'N'                                                 "
				+"		ELSE 'Y' END EXIST_IN_SAME_GROUP                                                 "
				+"		FROM                                                                                               " 
				+"		ADSE_ITEM_CATALOG ITEM                                                                            " 
				+"		LEFT  OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM                                                     "
				+"		ON ITEM.PARENT_MDMID =AGCM.COMPONENT_STYLE_ID                                                                    "
				+"		AND AGCM.MDMID = :groupIdSql ,                                                                            "
				+"		ADSE_PET_CATALOG PET,                                                                              "  
				+"		XMLTABLE('let                                                                                      " 
				+"		$colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,                  " 
				+"		$colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description,    " 
				+"		$size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,          " 
				+"		$sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,                    "
				+"		$name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                            " 
				+"		return                                                                                             " 
				+"		    <SPEC>                                                                                         " 
				+"		        <COLOR_CODE>{$colorcode}</COLOR_CODE>                                                      " 
				+"		        <COLOR_NAME>{$colorname}</COLOR_NAME>                                                      " 
				+"		        <SIZE>{$size}</SIZE>                                                                       " 
				+"		        <SIZE_CODE>{$sizeCode}</SIZE_CODE>                                                         "               
				+"		        <NAME>{$name}</NAME>                                                                       " 
				+"		    </SPEC>'                                                                                       " 
				+"		passing ITEM.XML_DATA                                                                              " 
				+"		Columns                                                                                            " 
				+"		    COLOR_CODE VARCHAR2(5) path '/SPEC/COLOR_CODE',                                                " 
				+"		    COLOR_NAME VARCHAR2(20) path '/SPEC/COLOR_NAME',                                               " 
				+"		    SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',                                                       " 
				+"		    SIZE_CODE VARCHAR2(20) path '/SPEC/SIZE_CODE',                                                 " 
				+"		    NAME VARCHAR2(50) path '/SPEC/NAME') ITEM_XML                                                  " 
				+"		WHERE                                                                                              " 
				+"		    ITEM.ENTRY_TYPE in ('SKU')                                                                     "
				+"		    AND ITEM.DELETED_FLAG= 'false'                                                                 " 
				+"		    AND PET.MDMID=ITEM.MDMID                                                                       ";
		
				if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND ITEM.PRIMARYSUPPLIERVPN =:styleIdSql ";
				} 
				if (null != styleOrin && !("").equals(styleOrin.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID) =:mdmidSql ";
				}
				if (null != deptNoSearch && !("").equals(deptNoSearch.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND ITEM.DEPT_ID IN (" + deptNoForInSearch + ")";
				}
				if (null != classNoSearch && !("").equals(classNoSearch.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND ITEM.CLASS_ID IN (" + classNoForInSearch + ")";
				}
				if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND ITEM.PRIMARY_SUPPLIER_ID =:supplierIdSql ";
				}
				if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
					getNewGBSDetails = getNewGBSDetails + " AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID)  = " +
							"(SELECT PARENT_MDMID FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql)";
				}
				//getNewGBSDetails = getNewGBSDetails + " ORDER BY ITEM.PARENT_MDMID";


		return getNewGBSDetails;
	}
	
	/**
	 *  This method is used to get the search result Details query for Regular/Beauty Collection Grouping.
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @param groupIdSearch
	 * @param groupNameSearch
	 * @param sortedColumn
	 * @param sortingOrder
	 * @return String
	 */
	public static final String getRegularBeautySearchResult(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch,	final String supplierSiteIdSearch, final String upcNoSearch, 
			final String deptNoSearch, final String classNoSearch, final String groupIdSearch, final String groupNameSearch,
			final String orderByColumn, final String sortingOrder) {
		
		LOGGER.info("Entering getRegularBeautySearchResult() in Grouping XQueryConstant class.");
		int count = 0;
		StringBuilder getRegularBeautySearchResult = new StringBuilder();
		getRegularBeautySearchResult.append(" SELECT DISTINCT SEARCH.PARENT_MDMID, ");
		getRegularBeautySearchResult.append("   SEARCH.MDMID,                                                                   ");
		getRegularBeautySearchResult.append("   SEARCH.ENTRY_TYPE,                                                              ");
		getRegularBeautySearchResult.append("   SEARCH.CLASS_ID,                                                                ");
		getRegularBeautySearchResult.append("   SEARCH.PRIMARYSUPPLIERVPN,                                                      ");
		getRegularBeautySearchResult.append("   PET_XML.PRODUCT_NAME,                                                           ");
		getRegularBeautySearchResult.append("   PET_XML.COLOR_CODE,                                                             ");
		getRegularBeautySearchResult.append("   PET.EXIST_IN_GROUP ALREADY_IN_GROUP,                                            ");
		getRegularBeautySearchResult.append("   PET_XML.COLOR_DESC,                                                             ");
		getRegularBeautySearchResult.append("   CASE                                                                            ");
		getRegularBeautySearchResult.append("     WHEN AGCM.MDMID IS NULL                                                       ");
		getRegularBeautySearchResult.append("     THEN 'N'                                                                      ");
		getRegularBeautySearchResult.append("     ELSE 'Y'                                                                      ");
		getRegularBeautySearchResult.append("   END EXIST_IN_SAME_GROUP,                                                        ");
		getRegularBeautySearchResult.append("   NULL GROUP_ID                                                                   ");
		getRegularBeautySearchResult.append(" FROM ADSE_PET_CATALOG PET,                                                        ");
		getRegularBeautySearchResult.append("   XMLTABLE( 'let                                                                  ");                    
		getRegularBeautySearchResult.append(" $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,        ");        
		getRegularBeautySearchResult.append(" $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code                ");        
		getRegularBeautySearchResult.append(" return                                                                            ");        
		getRegularBeautySearchResult.append(" <COLOR>                                                                           ");        
		getRegularBeautySearchResult.append(" <COLOR_CODE>{$colorCode}</COLOR_CODE>                                             ");        
		getRegularBeautySearchResult.append(" <COLOR_DESC>{$colordesc}</COLOR_DESC>                                             ");        
		getRegularBeautySearchResult.append(" <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>     ");        
		getRegularBeautySearchResult.append(" </COLOR>' passing Pet.XML_DATA Columns COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE', COLOR_DESC VARCHAR2(20) path '/COLOR/COLOR_DESC', PRODUCT_NAME VARCHAR2(20) path '/COLOR/PRODUCT_NAME' ) PET_XML, ");
		getRegularBeautySearchResult.append("   ADSE_ITEM_CATALOG SEARCH                                                   ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM                                ");
		getRegularBeautySearchResult.append(" ON SEARCH.MDMID                           =AGCM.COMPONENT_STYLE_ID           ");
		getRegularBeautySearchResult.append(" AND AGCM.MDMID                            = :groupIdSql                      ");
		getRegularBeautySearchResult.append(" AND ROWNUM                                =1                                 ");
		getRegularBeautySearchResult.append(" WHERE 1                                   =1                                 ");
		getRegularBeautySearchResult.append(" AND PET.MDMID                             =SEARCH.MDMID                      ");
		getRegularBeautySearchResult.append(" AND SEARCH.DELETED_FLAG                   ='false'                           ");
		getRegularBeautySearchResult.append(" AND SEARCH.ENTRY_TYPE                    IN ('Style','StyleColor')           ");
		if (!StringUtils.isEmpty(vendorStyleNo)) {
			getRegularBeautySearchResult.append("   AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
			count = 1;
		}
		if (!StringUtils.isEmpty(styleOrin)) {
			getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID) =:mdmidSql ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(deptNoSearch)) {
			getRegularBeautySearchResult.append("   AND SEARCH.DEPT_ID IN (" + deptNoForInSearch + ") ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(classNoSearch)) {
			getRegularBeautySearchResult.append("   AND SEARCH.CLASS_ID IN (" + classNoForInSearch + ") ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(supplierSiteIdSearch)) {
			getRegularBeautySearchResult.append("   AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(upcNoSearch)) {
			getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = (SELECT PARENT_MDMID FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
			count = 1;
		}	
		if(count == 0 || !groupIdSearch.isEmpty() || !groupNameSearch.isEmpty())
		{
			getRegularBeautySearchResult.append("   AND 1 = 0 ");
		}
		getRegularBeautySearchResult.append(" UNION                                                                        ");
		getRegularBeautySearchResult.append(" SELECT DISTINCT NULL PARENT_MDMID,                                           ");
		getRegularBeautySearchResult.append("   SEARCH.MDMID,                                                              ");
		getRegularBeautySearchResult.append("   SEARCH.ENTRY_TYPE,                                                         ");
		getRegularBeautySearchResult.append("   NULL CLASS_ID,                                                             ");
		getRegularBeautySearchResult.append("   SEARCH.DEF_PRIMARYSUPPLIERVPN,                                             ");
		getRegularBeautySearchResult.append("   SEARCH.GROUP_NAME,                                                         ");
		getRegularBeautySearchResult.append("   NULL COLOR_CODE,                                                           ");
		getRegularBeautySearchResult.append("   SEARCH.EXIST_IN_GROUP ALREADY_IN_GROUP,                                    ");
		getRegularBeautySearchResult.append("   NULL COLOR_DESC,                                                           ");
		getRegularBeautySearchResult.append("   CASE                                                                       ");
		getRegularBeautySearchResult.append("     WHEN SEARCH.MDMID = :groupIdSql                                          ");
		getRegularBeautySearchResult.append("     THEN 'Y'                                                                 ");
		getRegularBeautySearchResult.append("     WHEN AGCM.MDMID IS NULL                                                  ");
		getRegularBeautySearchResult.append("     THEN 'N'                                                                 ");
		getRegularBeautySearchResult.append("     ELSE 'Y'                                                                 ");
		getRegularBeautySearchResult.append("   END EXIST_IN_SAME_GROUP,                                                   ");
		getRegularBeautySearchResult.append("   CASE WHEN CHILD_GROUP.COMPONENT_GROUPING_ID is NOT NULL THEN 'Y' ELSE 'N' END        ");
		getRegularBeautySearchResult.append(" FROM ADSE_GROUP_CATALOG SEARCH                                               ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM                                ");
		getRegularBeautySearchResult.append(" ON SEARCH.MDMID =AGCM.COMPONENT_GROUPING_ID                                  ");
		getRegularBeautySearchResult.append(" AND AGCM.MDMID  = :groupIdSql                                                ");
		getRegularBeautySearchResult.append(" AND ROWNUM      =1                                                           ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING CHILD_GROUP                         ");
		getRegularBeautySearchResult.append(" ON SEARCH.MDMID                        =CHILD_GROUP.MDMID                    ");
		getRegularBeautySearchResult.append(" AND CHILD_GROUP.COMPONENT_GROUPING_ID IS NOT NULL                            ");
		getRegularBeautySearchResult.append(" AND ROWNUM                             =1                                    ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING SEARCH_CHILD                        ");
		getRegularBeautySearchResult.append(" ON SEARCH_CHILD.MDMID=SEARCH.MDMID                                           ");
		getRegularBeautySearchResult.append(" WHERE 1              =1                                                      ");
		if(!StringUtils.isEmpty(groupIdSearch))
		{
			getRegularBeautySearchResult.append(" AND SEARCH.MDMID     = ");
			getRegularBeautySearchResult.append(groupIdSearch);
		}
		if(!StringUtils.isEmpty(groupNameSearch))
		{
			getRegularBeautySearchResult.append(" AND SEARCH.GROUP_NAME LIKE '%");
			getRegularBeautySearchResult.append(groupNameSearch);
			getRegularBeautySearchResult.append("%'");
		}
		
		
		getRegularBeautySearchResult.append(" AND SEARCH.DELETED_FLAG           = 'false'                                  ");
		if(count == 1)
		{
			getRegularBeautySearchResult.append(" AND SEARCH_CHILD.COMPONENT_MDMID IN                                          ");
			getRegularBeautySearchResult.append("   (SELECT SEARCH.MDMID                                                       ");
			getRegularBeautySearchResult.append("   FROM ADSE_ITEM_CATALOG SEARCH                                              ");
			getRegularBeautySearchResult.append("   WHERE 1                                   =1                               ");
			getRegularBeautySearchResult.append("   AND SEARCH.DELETED_FLAG                   ='false'                         ");
			getRegularBeautySearchResult.append("   AND SEARCH.ENTRY_TYPE                    IN ('Style','StyleColor')         ");
			if (!StringUtils.isEmpty(vendorStyleNo)) {
				getRegularBeautySearchResult.append("   AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
			}		
			if (!StringUtils.isEmpty(styleOrin)) {
				getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID) =:mdmidSql ");;
			}		
			if (!StringUtils.isEmpty(deptNoSearch)) {
				getRegularBeautySearchResult.append("   AND SEARCH.DEPT_ID IN (" + deptNoForInSearch + ") ");
			}	
			if (!StringUtils.isEmpty(classNoSearch)) {
				getRegularBeautySearchResult.append("   AND SEARCH.CLASS_ID IN (" + classNoForInSearch + ") ");
			}
			if (!StringUtils.isEmpty(supplierSiteIdSearch)) {
				getRegularBeautySearchResult.append("   AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
			}		
			if (!StringUtils.isEmpty(upcNoSearch)) {
				getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = (SELECT PARENT_MDMID FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
			}
			getRegularBeautySearchResult.append("     )                                                                        ");	
		}
		 
		if(StringUtils.isEmpty(orderByColumn) || orderByColumn.equals("orinNumber"))
		{
			getRegularBeautySearchResult.append("  ORDER BY MDMID ");
			if(StringUtils.isEmpty(sortingOrder))
			{
				getRegularBeautySearchResult.append(" ASC");
			}
			else
			{
				getRegularBeautySearchResult.append(sortingOrder);
			}
				
		}
		else if(orderByColumn.equals("vendorStyle"))
		{
			getRegularBeautySearchResult.append(" ORDER BY PRIMARYSUPPLIERVPN ");
			getRegularBeautySearchResult.append(sortingOrder);
		}
		else if(orderByColumn.equals("productName"))
		{
			getRegularBeautySearchResult.append(" ORDER BY PRODUCT_NAME ");
			getRegularBeautySearchResult.append(sortingOrder);			
		}
		else if(orderByColumn.equals("colorCode"))
		{
			getRegularBeautySearchResult.append(" ORDER BY COLOR_CODE ");
			getRegularBeautySearchResult.append(sortingOrder);
		}
		else if(orderByColumn.equals("colorName"))
		{
			getRegularBeautySearchResult.append(" ORDER BY COLOR_DESC ");
			getRegularBeautySearchResult.append(sortingOrder);
		}

		LOGGER.info("Exiting getRegularBeautySearchResult() in Grouping XQueryConstant class.");
		return getRegularBeautySearchResult.toString();
	}
	
	/**
	 *  This method is used to get the search result Details count query for Regular/Beauty Collection Grouping.
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param deptNoForInSearch
	 * @param classNoForInSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @param groupIdSearch
	 * @param groupNameSearch
	 * @return String
	 */
	public static final String getRegularBeautySearchResultCountQuery(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String deptNoSearch,
			final String classNoSearch, final String groupIdSearch, final String groupNameSearch) {

		LOGGER.info("Entering getRegularBeautySearchResultCountQuery() in Grouping XQueryConstant class.");
		int count = 0;
		final StringBuilder getRegularBeautySearchResultCountQuery = new StringBuilder();
		StringBuilder getRegularBeautySearchResult = new StringBuilder();
		getRegularBeautySearchResult.append(" SELECT COUNT(MDMID) TOTAL_COUNT FROM ");
		getRegularBeautySearchResult.append(" (SELECT DISTINCT SEARCH.PARENT_MDMID, ");
		getRegularBeautySearchResult.append("   SEARCH.MDMID,                                                                   ");
		getRegularBeautySearchResult.append("   SEARCH.ENTRY_TYPE,                                                              ");
		getRegularBeautySearchResult.append("   SEARCH.CLASS_ID,                                                                ");
		getRegularBeautySearchResult.append("   SEARCH.PRIMARYSUPPLIERVPN,                                                      ");
		getRegularBeautySearchResult.append("   PET_XML.PRODUCT_NAME,                                                           ");
		getRegularBeautySearchResult.append("   PET_XML.COLOR_CODE,                                                             ");
		getRegularBeautySearchResult.append("   PET.EXIST_IN_GROUP ALREADY_IN_GROUP,                                            ");
		getRegularBeautySearchResult.append("   PET_XML.COLOR_DESC,                                                             ");
		getRegularBeautySearchResult.append("   CASE                                                                            ");
		getRegularBeautySearchResult.append("     WHEN AGCM.MDMID IS NULL                                                       ");
		getRegularBeautySearchResult.append("     THEN 'N'                                                                      ");
		getRegularBeautySearchResult.append("     ELSE 'Y'                                                                      ");
		getRegularBeautySearchResult.append("   END EXIST_IN_SAME_GROUP,                                                        ");
		getRegularBeautySearchResult.append("   NULL GROUP_ID                                                                   ");
		getRegularBeautySearchResult.append(" FROM ADSE_PET_CATALOG PET,                                                        ");
		getRegularBeautySearchResult.append("   XMLTABLE( 'let                                                                  ");                    
		getRegularBeautySearchResult.append(" $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,        ");        
		getRegularBeautySearchResult.append(" $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code                ");        
		getRegularBeautySearchResult.append(" return                                                                            ");        
		getRegularBeautySearchResult.append(" <COLOR>                                                                           ");        
		getRegularBeautySearchResult.append(" <COLOR_CODE>{$colorCode}</COLOR_CODE>                                             ");        
		getRegularBeautySearchResult.append(" <COLOR_DESC>{$colordesc}</COLOR_DESC>                                             ");        
		getRegularBeautySearchResult.append(" <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>     ");        
		getRegularBeautySearchResult.append(" </COLOR>' passing Pet.XML_DATA Columns COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE', COLOR_DESC VARCHAR2(20) path '/COLOR/COLOR_DESC', PRODUCT_NAME VARCHAR2(20) path '/COLOR/PRODUCT_NAME' ) PET_XML, ");
		getRegularBeautySearchResult.append("   ADSE_ITEM_CATALOG SEARCH                                                   ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM                                ");
		getRegularBeautySearchResult.append(" ON SEARCH.MDMID                           =AGCM.COMPONENT_STYLE_ID           ");
		getRegularBeautySearchResult.append(" AND AGCM.MDMID                            = :groupIdSql                      ");
		getRegularBeautySearchResult.append(" AND ROWNUM                                =1                                 ");
		getRegularBeautySearchResult.append(" WHERE 1                                   =1                                 ");
		getRegularBeautySearchResult.append(" AND PET.MDMID                             =SEARCH.MDMID                      ");
		getRegularBeautySearchResult.append(" AND SEARCH.DELETED_FLAG                   ='false'                           ");
		getRegularBeautySearchResult.append(" AND SEARCH.ENTRY_TYPE                    IN ('Style','StyleColor')           ");
		if (!StringUtils.isEmpty(vendorStyleNo)) {
			getRegularBeautySearchResult.append("   AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
			count = 1;
		}
		if (!StringUtils.isEmpty(styleOrin)) {
			getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID) =:mdmidSql ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(deptNoForInSearch)) {
			getRegularBeautySearchResult.append("   AND SEARCH.DEPT_ID IN (" + deptNoSearch + ") ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(classNoForInSearch)) {
			getRegularBeautySearchResult.append("   AND SEARCH.CLASS_ID IN (" + classNoSearch + ") ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(supplierSiteIdSearch)) {
			getRegularBeautySearchResult.append("   AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(upcNoSearch)) {
			getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = (SELECT PARENT_MDMID FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
			count = 1;
		}	
		if(count == 0 || !groupIdSearch.isEmpty() || !groupNameSearch.isEmpty())
		{
			getRegularBeautySearchResult.append("   AND 1 = 0 ");
		}
		getRegularBeautySearchResult.append(" UNION                                                                        ");
		getRegularBeautySearchResult.append(" SELECT DISTINCT NULL PARENT_MDMID,                                           ");
		getRegularBeautySearchResult.append("   SEARCH.MDMID,                                                              ");
		getRegularBeautySearchResult.append("   SEARCH.ENTRY_TYPE,                                                         ");
		getRegularBeautySearchResult.append("   NULL CLASS_ID,                                                             ");
		getRegularBeautySearchResult.append("   SEARCH.DEF_PRIMARYSUPPLIERVPN,                                             ");
		getRegularBeautySearchResult.append("   SEARCH.GROUP_NAME,                                                         ");
		getRegularBeautySearchResult.append("   NULL COLOR_CODE,                                                           ");
		getRegularBeautySearchResult.append("   SEARCH.EXIST_IN_GROUP ALREADY_IN_GROUP,                                    ");
		getRegularBeautySearchResult.append("   NULL COLOR_DESC,                                                           ");
		getRegularBeautySearchResult.append("   CASE                                                                       ");
		getRegularBeautySearchResult.append("     WHEN SEARCH.MDMID = :groupIdSql                                          ");
		getRegularBeautySearchResult.append("     THEN 'Y'                                                                 ");
		getRegularBeautySearchResult.append("     WHEN AGCM.MDMID IS NULL                                                  ");
		getRegularBeautySearchResult.append("     THEN 'N'                                                                 ");
		getRegularBeautySearchResult.append("     ELSE 'Y'                                                                 ");
		getRegularBeautySearchResult.append("   END EXIST_IN_SAME_GROUP,                                                   ");
		getRegularBeautySearchResult.append("   CASE WHEN CHILD_GROUP.COMPONENT_GROUPING_ID is NOT NULL THEN 'Y' ELSE 'N' END        ");
		getRegularBeautySearchResult.append(" FROM ADSE_GROUP_CATALOG SEARCH                                               ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM                                ");
		getRegularBeautySearchResult.append(" ON SEARCH.MDMID =AGCM.COMPONENT_GROUPING_ID                                  ");
		getRegularBeautySearchResult.append(" AND AGCM.MDMID  = :groupIdSql                                                ");
		getRegularBeautySearchResult.append(" AND ROWNUM      =1                                                           ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING CHILD_GROUP                         ");
		getRegularBeautySearchResult.append(" ON SEARCH.MDMID                        =CHILD_GROUP.MDMID                    ");
		getRegularBeautySearchResult.append(" AND CHILD_GROUP.COMPONENT_GROUPING_ID IS NOT NULL                            ");
		getRegularBeautySearchResult.append(" AND ROWNUM                             =1                                    ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING SEARCH_CHILD                        ");
		getRegularBeautySearchResult.append(" ON SEARCH_CHILD.MDMID=SEARCH.MDMID                                           ");
		getRegularBeautySearchResult.append(" WHERE 1              =1                                                      ");
		if(!StringUtils.isEmpty(groupIdSearch))
		{
			getRegularBeautySearchResult.append(" AND SEARCH.MDMID     = ");
			getRegularBeautySearchResult.append(groupIdSearch);
		}
		if(!StringUtils.isEmpty(groupNameSearch))
		{
			getRegularBeautySearchResult.append(" AND SEARCH.GROUP_NAME LIKE '%");
			getRegularBeautySearchResult.append(groupNameSearch);
			getRegularBeautySearchResult.append("%'");
		}
		
		
		getRegularBeautySearchResult.append(" AND SEARCH.DELETED_FLAG           = 'false'                                  ");
		if(count == 1)
		{
			getRegularBeautySearchResult.append(" AND SEARCH_CHILD.COMPONENT_MDMID IN                                          ");
			getRegularBeautySearchResult.append("   (SELECT SEARCH.MDMID                                                       ");
			getRegularBeautySearchResult.append("   FROM ADSE_ITEM_CATALOG SEARCH                                              ");
			getRegularBeautySearchResult.append("   WHERE 1                                   =1                               ");
			getRegularBeautySearchResult.append("   AND SEARCH.DELETED_FLAG                   ='false'                         ");
			getRegularBeautySearchResult.append("   AND SEARCH.ENTRY_TYPE                    IN ('Style','StyleColor')         ");
			if (!StringUtils.isEmpty(vendorStyleNo)) {
				getRegularBeautySearchResult.append("   AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
			}		
			if (!StringUtils.isEmpty(styleOrin)) {
				getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID) =:mdmidSql ");;
			}		
			if (!StringUtils.isEmpty(deptNoForInSearch)) {
				getRegularBeautySearchResult.append("   AND SEARCH.DEPT_ID IN (" + deptNoSearch + ") ");
			}	
			if (!StringUtils.isEmpty(classNoForInSearch)) {
				getRegularBeautySearchResult.append("   AND SEARCH.CLASS_ID IN (" + classNoSearch + ") ");
			}
			if (!StringUtils.isEmpty(supplierSiteIdSearch)) {
				getRegularBeautySearchResult.append("   AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
			}		
			if (!StringUtils.isEmpty(upcNoSearch)) {
				getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = (SELECT PARENT_MDMID FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
			}
			getRegularBeautySearchResult.append("     )                                                                        ");
		}
		getRegularBeautySearchResult.append("   )                                                                          ");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getRegularBeautySearchResultCountQuery -- \n" + getRegularBeautySearchResultCountQuery.toString());
		}
		LOGGER.info("Exiting getRegularBeautySearchResultCountQuery() in Grouping XQueryConstant class.");
		return getRegularBeautySearchResultCountQuery.toString();
	}

	
	/**
	 *  This method is used to get the existing components query for Regular/Beauty Collection Grouping.
	 * @return String
	 */
	public static  final String getExistRegularBeautyDetailsQuery() {
		LOGGER.info("Entering getExistRegularBeautyDetailsQuery() in Grouping XQueryConstant class.");
		StringBuilder getExistRegularBeautyDetailsQuery = new StringBuilder();
		getExistRegularBeautyDetailsQuery.append(" SELECT DISTINCT ");
		getExistRegularBeautyDetailsQuery.append(" COMPONENT_DEFAULT,                    ");
		getExistRegularBeautyDetailsQuery.append(" PARENT_MDMID,                         ");
		getExistRegularBeautyDetailsQuery.append(" MDMID,                                ");
		getExistRegularBeautyDetailsQuery.append(" ENTRY_TYPE,                           ");
		getExistRegularBeautyDetailsQuery.append(" PRIMARYSUPPLIERVPN,                   ");
		getExistRegularBeautyDetailsQuery.append(" PRODUCT_NAME,                         ");
		getExistRegularBeautyDetailsQuery.append(" COLOR_CODE,                           ");
		getExistRegularBeautyDetailsQuery.append(" COLOR_DESC,                           ");
		getExistRegularBeautyDetailsQuery.append(" CLASS_ID,                             ");
		getExistRegularBeautyDetailsQuery.append(" DISPLAY_SEQUENCE FROM                 ");
		getExistRegularBeautyDetailsQuery.append(" (SELECT                               ");
		getExistRegularBeautyDetailsQuery.append("   DETAIL.COMPONENT_DEFAULT,           ");
		getExistRegularBeautyDetailsQuery.append("   ITEM.PARENT_MDMID,                  ");
		getExistRegularBeautyDetailsQuery.append("   ITEM.MDMID,                         ");
		getExistRegularBeautyDetailsQuery.append("   ITEM.ENTRY_TYPE,                    ");
		getExistRegularBeautyDetailsQuery.append("   ITEM.PRIMARYSUPPLIERVPN,            ");
		getExistRegularBeautyDetailsQuery.append("   PET_XML.PRODUCT_NAME,               ");
		getExistRegularBeautyDetailsQuery.append("   PET_XML.COLOR_CODE,                 ");
		getExistRegularBeautyDetailsQuery.append("   PET_XML.COLOR_DESC,                 ");
		getExistRegularBeautyDetailsQuery.append("   ITEM.CLASS_ID,                      ");
		getExistRegularBeautyDetailsQuery.append("   DETAIL.PEP_COMPONENT_TYPE,          ");
		getExistRegularBeautyDetailsQuery.append("   DETAIL.DISPLAY_SEQUENCE             ");
		getExistRegularBeautyDetailsQuery.append(" FROM ADSE_ITEM_CATALOG ITEM,          ");
		getExistRegularBeautyDetailsQuery.append("   ADSE_GROUP_CHILD_MAPPING DETAIL,    ");
		getExistRegularBeautyDetailsQuery.append("   ADSE_PET_CATALOG PET,               ");
		getExistRegularBeautyDetailsQuery.append("   XMLTABLE( 'let                      ");                                                                              
		getExistRegularBeautyDetailsQuery.append(" $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,   ");                              
		getExistRegularBeautyDetailsQuery.append(" $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code           ");                          
		getExistRegularBeautyDetailsQuery.append(" return                                  ");                                                                   
		getExistRegularBeautyDetailsQuery.append(" <COLOR>                                 ");                                                                       
		getExistRegularBeautyDetailsQuery.append(" <COLOR_CODE>{$colorCode}</COLOR_CODE>   ");                                                                   
		getExistRegularBeautyDetailsQuery.append(" <COLOR_DESC>{$colordesc}</COLOR_DESC>   ");                                                                   
		getExistRegularBeautyDetailsQuery.append(" <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>    ");                      
		getExistRegularBeautyDetailsQuery.append(" </COLOR>' passing Pet.XML_DATA Columns COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE', COLOR_DESC VARCHAR2(20) path '/COLOR/COLOR_DESC', ");
		getExistRegularBeautyDetailsQuery.append("   PRODUCT_NAME    VARCHAR2(20) path '/COLOR/PRODUCT_NAME' ) PET_XML "); 
		getExistRegularBeautyDetailsQuery.append(" WHERE 1      =1                                            ");
		getExistRegularBeautyDetailsQuery.append(" AND PET.MDMID=ITEM.MDMID                                   ");
		getExistRegularBeautyDetailsQuery.append(" AND ITEM.MDMID LIKE CONCAT(DETAIL.COMPONENT_MDMID,'%')     ");
		getExistRegularBeautyDetailsQuery.append(" AND DETAIL.PEP_COMPONENT_TYPE = 'Style' ");
		getExistRegularBeautyDetailsQuery.append(" AND CASE WHEN (ITEM.ENTRY_TYPE = 'StyleColor' ");
		getExistRegularBeautyDetailsQuery.append(" AND ITEM.MDMID = DETAIL.COMPONENT_STYLECOLOR_ID OR DETAIL.COMPONENT_STYLECOLOR_ID is NULL) THEN 1 ");
		getExistRegularBeautyDetailsQuery.append(" WHEN ITEM.ENTRY_TYPE = 'Style' THEN 1 ");
		getExistRegularBeautyDetailsQuery.append(" ELSE 0 END = 1 ");
		getExistRegularBeautyDetailsQuery.append(" AND DETAIL.MDMID= :groupId                                ");
		getExistRegularBeautyDetailsQuery.append(" UNION                                                      ");
		getExistRegularBeautyDetailsQuery.append(" SELECT                                                     ");
		getExistRegularBeautyDetailsQuery.append("   DETAIL.COMPONENT_DEFAULT,                                ");
		getExistRegularBeautyDetailsQuery.append("   NULL PARENT_MDMID,                                       ");
		getExistRegularBeautyDetailsQuery.append("   GRP.MDMID,                                               ");
		getExistRegularBeautyDetailsQuery.append("   GRP.ENTRY_TYPE,                                          ");
		getExistRegularBeautyDetailsQuery.append("   GRP.DEF_PRIMARYSUPPLIERVPN,                              ");
		getExistRegularBeautyDetailsQuery.append("   GRP.GROUP_NAME PRODUCT_NAME,                             ");
		getExistRegularBeautyDetailsQuery.append("   NULL COLOR_CODE,                                         ");
		getExistRegularBeautyDetailsQuery.append("   NULL COLOR_DESC,                                         ");
		getExistRegularBeautyDetailsQuery.append("   NULL CLASS_ID,                                           ");
		getExistRegularBeautyDetailsQuery.append("   DETAIL.PEP_COMPONENT_TYPE,                               ");
		getExistRegularBeautyDetailsQuery.append("   DETAIL.DISPLAY_SEQUENCE                                  ");
		getExistRegularBeautyDetailsQuery.append(" FROM ADSE_GROUP_CATALOG GRP,                               ");
		getExistRegularBeautyDetailsQuery.append("   ADSE_GROUP_CHILD_MAPPING DETAIL                          ");
		getExistRegularBeautyDetailsQuery.append(" WHERE 1      =1                                            ");
		getExistRegularBeautyDetailsQuery.append(" AND DETAIL.COMPONENT_MDMID = GRP.MDMID                     ");
		getExistRegularBeautyDetailsQuery.append(" AND DETAIL.PEP_COMPONENT_TYPE = 'Group' ");
		getExistRegularBeautyDetailsQuery.append(" AND DETAIL.MDMID= :groupId)                               ");
		getExistRegularBeautyDetailsQuery.append(" ORDER BY MDMID                                       ");

		LOGGER.info("Entering getExistRegularBeautyDetailsQuery() in Grouping XQueryConstant class.");
		return getExistRegularBeautyDetailsQuery.toString();
	}
	
	/**
	 *  This method is used to get the group child query for Regular/Beauty Collection Grouping.
	 * @return String
	 */
	public static  final String getRegularBeautyDetailsChildQuery() {
		LOGGER.info("Entering getRegularBeautyDetailsChildQuery() in Grouping XQueryConstant class.");
		StringBuilder getExistRegularBeautyDetailsChildQuery = new StringBuilder();
		getExistRegularBeautyDetailsChildQuery.append(" SELECT DISTINCT PARENT_MDMID,  ");
		getExistRegularBeautyDetailsChildQuery.append("   MDMID,                                                                 ");
		getExistRegularBeautyDetailsChildQuery.append("   PRODUCTNAME,                                                           ");
		getExistRegularBeautyDetailsChildQuery.append("   COMPLETION_DATE,                                                       ");
		getExistRegularBeautyDetailsChildQuery.append("   ENTRY_TYPE,                                                            ");
		//getExistRegularBeautyDetailsChildQuery.append("   COMPONENT_TYPE,                                                        ");
		getExistRegularBeautyDetailsChildQuery.append("   VENDOR_STYLE,                                                          ");
		getExistRegularBeautyDetailsChildQuery.append("   EXIST_IN_GROUP ,                                                       ");
		getExistRegularBeautyDetailsChildQuery.append("   COLOR_CODE, COLO_DESC                                                  ");
		getExistRegularBeautyDetailsChildQuery.append(" FROM                                                                     ");
		getExistRegularBeautyDetailsChildQuery.append("   (SELECT NULL PARENT_MDMID,                                             ");
		getExistRegularBeautyDetailsChildQuery.append("     AGC.MDMID,                                                           ");
		getExistRegularBeautyDetailsChildQuery.append("     AGC.GROUP_NAME PRODUCTNAME,                                          ");
		getExistRegularBeautyDetailsChildQuery.append("     TO_CHAR(AGC.COMPLETION_DATE,'YYYY-MM-DD') COMPLETION_DATE,           ");
		getExistRegularBeautyDetailsChildQuery.append("     AGC.ENTRY_TYPE,                                                      ");
		getExistRegularBeautyDetailsChildQuery.append("     AGCM.PEP_COMPONENT_TYPE COMPONENT_TYPE,                              ");
		getExistRegularBeautyDetailsChildQuery.append("     AGC.DEF_PRIMARYSUPPLIERVPN VENDOR_STYLE,                             ");
		getExistRegularBeautyDetailsChildQuery.append("     AGC.EXIST_IN_GROUP ,                                                 ");
		getExistRegularBeautyDetailsChildQuery.append("     NULL color_code , NULL COLO_DESC                                     ");
		getExistRegularBeautyDetailsChildQuery.append("   FROM ADSE_GROUP_CATALOG AGC ,                                          ");
		getExistRegularBeautyDetailsChildQuery.append("     ADSE_GROUP_CHILD_MAPPING AGCM                                        ");
		getExistRegularBeautyDetailsChildQuery.append("   WHERE AGC.MDMID             = AGCM.COMPONENT_GROUPING_ID               ");
		getExistRegularBeautyDetailsChildQuery.append("   AND AGCM.PEP_COMPONENT_TYPE ='Group'                                   ");
		getExistRegularBeautyDetailsChildQuery.append("   AND AGCM.MDMID              = :groupId                                 ");
		getExistRegularBeautyDetailsChildQuery.append("   UNION                                                                  ");
		getExistRegularBeautyDetailsChildQuery.append("   SELECT AIC.PARENT_MDMID,                                               ");
		getExistRegularBeautyDetailsChildQuery.append("     AIC.MDMID,                                                           ");
		getExistRegularBeautyDetailsChildQuery.append("     PET_XML.PRODUCT_NAME PRODUCTNAME,                                    ");
		getExistRegularBeautyDetailsChildQuery.append("     CASE                                                                 ");
		getExistRegularBeautyDetailsChildQuery.append("       WHEN AIC.ENTRY_TYPE = 'Style'                                      ");
		getExistRegularBeautyDetailsChildQuery.append("       THEN TO_CHAR(APC.PET_EARLIEST_COMP_DATE, 'YYYY-MM-DD')             ");
		getExistRegularBeautyDetailsChildQuery.append("       ELSE PET_XML.completion_date                                       ");
		getExistRegularBeautyDetailsChildQuery.append("     END AS completion_date,                                              ");
		getExistRegularBeautyDetailsChildQuery.append("     AIC.ENTRY_TYPE,                                                      ");
		getExistRegularBeautyDetailsChildQuery.append("     AGCM.PEP_COMPONENT_TYPE COMPONENT_TYPE,                              ");
		getExistRegularBeautyDetailsChildQuery.append("     AIC.PRIMARYSUPPLIERVPN VENDOR_STYLE,                                 ");
		getExistRegularBeautyDetailsChildQuery.append("     APC.EXIST_IN_GROUP,                                                  ");
		getExistRegularBeautyDetailsChildQuery.append("     PET_XML.color_code, PET_XML.COLO_DESC                                ");
		getExistRegularBeautyDetailsChildQuery.append("   FROM ADSE_GROUP_CHILD_MAPPING AGCM,                                    ");
		getExistRegularBeautyDetailsChildQuery.append("     ADSE_ITEM_CATALOG AIC                                                ");
		getExistRegularBeautyDetailsChildQuery.append("   INNER JOIN ADSE_PET_CATALOG APC                                        ");
		getExistRegularBeautyDetailsChildQuery.append("   ON AIC.MDMID=APC.MDMID                                                 ");
		getExistRegularBeautyDetailsChildQuery.append("   LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT                             ");
		getExistRegularBeautyDetailsChildQuery.append("   ON ASCT.MDMID = AIC.PRIMARY_SUPPLIER_ID,                               ");
		getExistRegularBeautyDetailsChildQuery.append("     XMLTABLE(                                                            ");
		getExistRegularBeautyDetailsChildQuery.append("     'let                                                                 ");                   
		getExistRegularBeautyDetailsChildQuery.append(" $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,   ");                             
		getExistRegularBeautyDetailsChildQuery.append(" $colordesc:= $pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description             ");           
		getExistRegularBeautyDetailsChildQuery.append(" return                                                                                     ");                                                   
		getExistRegularBeautyDetailsChildQuery.append(" <out>                                                                                      ");           
		getExistRegularBeautyDetailsChildQuery.append(" <completion_date>{$completionDate}</completion_date>                                       ");           
		getExistRegularBeautyDetailsChildQuery.append(" <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                     ");           
		getExistRegularBeautyDetailsChildQuery.append(" <color_code>{$pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code}</color_code>      ");     
		getExistRegularBeautyDetailsChildQuery.append(" <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>         ");  
		getExistRegularBeautyDetailsChildQuery.append(" <COLO_DESC>{$colordesc}</COLO_DESC>                                                        ");           
		getExistRegularBeautyDetailsChildQuery.append(" </out>'                                                                                    ");
		getExistRegularBeautyDetailsChildQuery.append("     passing APC.xml_data AS \"pets\" Columns completion_date VARCHAR2(10) path '/out/completion_date',PRODUCT_NAME VARCHAR2(100) path '/out/PRODUCT_NAME', req_type VARCHAR2(20) path '/out/req_type', color_code VARCHAR2(100) path '/out/color_code', COLO_DESC VARCHAR2(50) path '/out/COLO_DESC' ) (+)PET_XML ");
		getExistRegularBeautyDetailsChildQuery.append("   WHERE NVL(AIC.PARENT_MDMID,AIC.MDMID) = AGCM.COMPONENT_STYLE_ID    ");
		getExistRegularBeautyDetailsChildQuery.append("   AND AGCM.PEP_COMPONENT_TYPE!          ='Group'                     ");
		getExistRegularBeautyDetailsChildQuery.append("   AND AIC.ENTRY_TYPE                   IN ('Style','StyleColor')     ");
		getExistRegularBeautyDetailsChildQuery.append("   AND (                                                              ");
		getExistRegularBeautyDetailsChildQuery.append("     CASE                                                             ");
		getExistRegularBeautyDetailsChildQuery.append("       WHEN AIC.PARENT_MDMID IS NOT NULL                              ");
		getExistRegularBeautyDetailsChildQuery.append("       AND AIC.MDMID         !=AGCM.COMPONENT_STYLECOLOR_ID           ");
		getExistRegularBeautyDetailsChildQuery.append("       THEN 0                                                         ");
		getExistRegularBeautyDetailsChildQuery.append("       ELSE 1                                                         ");
		getExistRegularBeautyDetailsChildQuery.append("     END)         =1                                                  ");
		getExistRegularBeautyDetailsChildQuery.append("   AND AGCM.MDMID = :groupId                                          ");
		getExistRegularBeautyDetailsChildQuery.append("   )                                                                  ");
		getExistRegularBeautyDetailsChildQuery.append(" ORDER BY MDMID                                                  ");

		LOGGER.info("Entering getRegularBeautyDetailsChildQuery() in Grouping XQueryConstant class.");
		return getExistRegularBeautyDetailsChildQuery.toString();
	}

}
