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
	private XqueryConstants() {
		// To be implemented.
	}

	/** This method is used to build the Query to retrieve Group Header Details
	 * from Database against Group Id.
	 * 
	 * @return String */
	public static  final String getGroupHeaderDetails() {
		StringBuilder getGroupHeaderDetailsQuery = new StringBuilder();
		getGroupHeaderDetailsQuery.append(" SELECT                                                                                ");
		getGroupHeaderDetailsQuery.append("      AGC.MDMID GROUP_ID,                                                               ");
		getGroupHeaderDetailsQuery.append("      AGC.GROUP_NAME,                                                                   ");
		getGroupHeaderDetailsQuery.append("      AGCXML.Description,                                                        ");
		getGroupHeaderDetailsQuery.append("      AGC.START_DATE,                                               ");
		getGroupHeaderDetailsQuery.append("      AGC.END_DATE,                                                 ");
		getGroupHeaderDetailsQuery.append("      AGCXML.CARS_Group_Type,                                               ");
		getGroupHeaderDetailsQuery.append("      AGC.ENTRY_TYPE GROUP_TYPE, AGC.GROUP_OVERALL_STATUS_CODE,                   ");
		getGroupHeaderDetailsQuery.append("      AGC.CREATED_BY,                                                             ");
		getGroupHeaderDetailsQuery.append("      AGC.EXIST_IN_GROUP                                                          ");
		getGroupHeaderDetailsQuery.append("      FROM ADSE_GROUP_CATALOG AGC,                                               ");
		getGroupHeaderDetailsQuery.append("        XMLTABLE( 'let                                                           ");
		getGroupHeaderDetailsQuery.append("        $Description:= /pim_entry/entry/Group_Ctg_Spec/Description,              ");
		getGroupHeaderDetailsQuery.append("        $CARS_Group_Type:= /pim_entry/entry/Group_Ctg_Spec/CARS_Group_Type ");
		getGroupHeaderDetailsQuery.append("        return                                  ");
		getGroupHeaderDetailsQuery.append("      <out>                                                                      ");
		getGroupHeaderDetailsQuery.append("          <Description>{$Description}</Description>                              ");
		getGroupHeaderDetailsQuery.append("          <CARS_Group_Type>{$CARS_Group_Type}</CARS_Group_Type>         ");
		getGroupHeaderDetailsQuery.append("      </out>' passing AGC.XML_DATA                                               ");
		getGroupHeaderDetailsQuery.append("        Columns                                                                  ");
		getGroupHeaderDetailsQuery.append("        Description CLOB path '/out/Description',                        ");
		getGroupHeaderDetailsQuery.append("        CARS_Group_Type VARCHAR2(50) path '/out/CARS_Group_Type') AGCXML   ");
		getGroupHeaderDetailsQuery.append("      WHERE                                                                      ");
		getGroupHeaderDetailsQuery.append("      MDMID = :groupIdSql AND DELETED_FLAG = 'false'                              ");


		return getGroupHeaderDetailsQuery.toString();
	}

	/** This method is used to build the Query to retrieve Split Color details to
	 * create Split Color Group.
	 * 
	 * @return String
	 * @param vendorStyleNo */
	public static  final String getSplitColorDetails(final String vendorStyleNo) {
		StringBuilder getSplitColorDetailsQuery = new StringBuilder();
		getSplitColorDetailsQuery.append(" Select                          ");
		getSplitColorDetailsQuery.append("   ITEM.MDMID, ITEM.PARENT_MDMID,                                                               ");
		getSplitColorDetailsQuery.append("   ITEM.PRIMARYSUPPLIERVPN,                                                                     ");
		getSplitColorDetailsQuery.append("   PET_XML.PRODUCT_NAME,                                                                        ");
		getSplitColorDetailsQuery.append("   PET_XML.COLOR_CODE,                                                                          ");
		getSplitColorDetailsQuery.append("   PET_XML.COLOR_DESC,                                                                          ");
		getSplitColorDetailsQuery.append("   PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE, PET.ENTRY_TYPE                             ");
		getSplitColorDetailsQuery.append("   FROM                                                                                         ");
		getSplitColorDetailsQuery.append("   ADSE_ITEM_CATALOG ITEM,                                                                      ");
		getSplitColorDetailsQuery.append("   ADSE_PET_CATALOG PET,                                                                          ");
		getSplitColorDetailsQuery.append("   XMLTABLE('let                                                                                ");
		getSplitColorDetailsQuery.append("       $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,               ");
		getSplitColorDetailsQuery.append("       $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code                       ");
		getSplitColorDetailsQuery.append("   return                                                                                       ");
		getSplitColorDetailsQuery.append("   <COLOR>                                                                                      ");
		getSplitColorDetailsQuery.append("       <COLOR_CODE>{$colorCode}</COLOR_CODE>                                                    ");
		getSplitColorDetailsQuery.append("       <COLOR_DESC>{$colordesc}</COLOR_DESC>                                                    ");
		getSplitColorDetailsQuery.append("       <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>            ");
		getSplitColorDetailsQuery.append("   </COLOR>'                                                                                    ");
		getSplitColorDetailsQuery.append("   passing Pet.XML_DATA                                                                         ");
		getSplitColorDetailsQuery.append("   Columns                                                                                      ");
		getSplitColorDetailsQuery.append("       COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',                                         ");
		getSplitColorDetailsQuery.append("       COLOR_DESC VARCHAR2(20) path '/COLOR/COLOR_DESC',                                        ");
		getSplitColorDetailsQuery.append("       PRODUCT_NAME VARCHAR2(20) path '/COLOR/PRODUCT_NAME'                                     ");
		getSplitColorDetailsQuery.append("       ) PET_XML                                                                                ");
		getSplitColorDetailsQuery.append("   WHERE                                                                                        ");
		getSplitColorDetailsQuery.append("   ITEM.ENTRY_TYPE in ('Style', 'StyleColor')                                                   ");
		getSplitColorDetailsQuery.append("   AND ITEM.DELETED_FLAG= 'false'                                                               ");
		getSplitColorDetailsQuery.append("    AND PET.MDMID=ITEM.MDMID                                                                    ");
		if (vendorStyleNo != null) {
			getSplitColorDetailsQuery.append(" AND item.PRIMARYSUPPLIERVPN = :styleIdSql");
		} else {
			getSplitColorDetailsQuery.append(" AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID) = :mdmidSql");
		}

		return getSplitColorDetailsQuery.toString();
	}

	/** This method is used to build the Query to retrieve Split SKU details to
	 * create Split SKU Group.
	 * 
	 * @param vendorStyleNo
	 * @return String */
	public static  final String getSplitSKUDetails(final String vendorStyleNo) {
		StringBuilder getSplitSKUDetailsQuery = new StringBuilder();
		getSplitSKUDetailsQuery.append("   Select                                                                      ");
		getSplitSKUDetailsQuery.append("  ITEM.MDMID,  ITEM.PARENT_MDMID,                                                               ");
		getSplitSKUDetailsQuery.append("  ITEM.PRIMARYSUPPLIERVPN,                                                                     ");
		getSplitSKUDetailsQuery.append("  ITEM_XML.NAME,                                                                               ");
		getSplitSKUDetailsQuery.append("  ITEM_XML.COLOR_CODE,                                                                         ");
		getSplitSKUDetailsQuery.append("  ITEM_XML.COLOR_NAME,                                                                         ");
		getSplitSKUDetailsQuery.append("  ITEM_XML.SIZEDESC, ITEM_XML.SIZE_CODE,                                                       ");
		getSplitSKUDetailsQuery.append("  PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE, PET.ENTRY_TYPE                           ");
		getSplitSKUDetailsQuery.append("  FROM                                                                                         ");
		getSplitSKUDetailsQuery.append("  ADSE_ITEM_CATALOG ITEM,                                                                      ");
		getSplitSKUDetailsQuery.append("  ADSE_PET_CATALOG PET,                                                                         ");
		getSplitSKUDetailsQuery.append("  XMLTABLE('let                                                                                ");
		getSplitSKUDetailsQuery.append("  $colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,            ");
		getSplitSKUDetailsQuery.append("  $colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description,     ");
		getSplitSKUDetailsQuery.append("  $size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,    ");
		getSplitSKUDetailsQuery.append("  $sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,    ");
		getSplitSKUDetailsQuery.append("  $name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                      ");
		getSplitSKUDetailsQuery.append("  return                                                                                       ");
		getSplitSKUDetailsQuery.append("      <SPEC>                                                                                   ");
		getSplitSKUDetailsQuery.append("          <COLOR_CODE>{$colorcode}</COLOR_CODE>                                                ");
		getSplitSKUDetailsQuery.append("          <COLOR_NAME>{$colorname}</COLOR_NAME>                                                ");
		getSplitSKUDetailsQuery.append("          <SIZE>{$size}</SIZE>                                                                 ");
		getSplitSKUDetailsQuery.append("          <SIZE_CODE>{$sizeCode}</SIZE_CODE>                                                                 ");
		getSplitSKUDetailsQuery.append("          <NAME>{$name}</NAME>                                                                 ");
		getSplitSKUDetailsQuery.append("      </SPEC>'                                                                                 ");
		getSplitSKUDetailsQuery.append("  passing ITEM.XML_DATA                                                                        ");
		getSplitSKUDetailsQuery.append("  Columns                                                                                      ");
		getSplitSKUDetailsQuery.append("      COLOR_CODE VARCHAR2(5) path '/SPEC/COLOR_CODE',                                          ");
		getSplitSKUDetailsQuery.append("      COLOR_NAME VARCHAR2(20) path '/SPEC/COLOR_NAME',                                         ");
		getSplitSKUDetailsQuery.append("      SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',                                                 ");
		getSplitSKUDetailsQuery.append("      SIZE_CODE VARCHAR2(20) path '/SPEC/SIZE_CODE',                                           ");
		getSplitSKUDetailsQuery.append("      NAME VARCHAR2(50) path '/SPEC/NAME') ITEM_XML                                            ");
		getSplitSKUDetailsQuery.append("  WHERE                                                                                        ");
		getSplitSKUDetailsQuery.append("      ITEM.ENTRY_TYPE in ('Style', 'StyleColor', 'SKU')                                        ");
		getSplitSKUDetailsQuery.append("      AND ITEM.DELETED_FLAG= 'false'                                                           ");
		getSplitSKUDetailsQuery.append("      AND PET.MDMID=ITEM.MDMID                                                                 ");
		if (vendorStyleNo != null) {
			getSplitSKUDetailsQuery.append(" AND item.PRIMARYSUPPLIERVPN = :styleIdSql");
		} else {
			getSplitSKUDetailsQuery.append(" AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID) = :mdmidSql");
		}

		return getSplitSKUDetailsQuery.toString();
	}

	/** Method to get the Search Group query.
	 * 
	 * @param groupSearchForm
	 * @return the query string
	 * 
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/19/2016
	 *         Added By: Cognizant */
	public static final String getGroupDetailsQuery(final GroupSearchForm groupSearchForm) {

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

		getGroupDetailsQuery.append(" WHERE                                                                                          ");
		getGroupDetailsQuery.append(" GRP.DELETED_FLAG = 'false'                                                                       ");
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
		if (!groupIdsList.isEmpty()) {
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
		if (!groupIdsList.isEmpty()) {
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

		if (!groupSearchDTOList.isEmpty()) {
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
		StringBuilder getExistSplitColorDetails = new StringBuilder();
		getExistSplitColorDetails.append("	SELECT                                                                           ");
		getExistSplitColorDetails.append("	DETAIL.COMPONENT_MDMID,                                                   ");
		getExistSplitColorDetails.append("	DETAIL.COMPONENT_DEFAULT,                                                 ");
		getExistSplitColorDetails.append("	ITEM.PARENT_MDMID, 			                                "); // if null, it is style
		getExistSplitColorDetails.append("	ITEM.MDMID,                                                               ");
		getExistSplitColorDetails.append("	ITEM.ENTRY_TYPE,                                                          ");
		getExistSplitColorDetails.append("	ITEM.PRIMARYSUPPLIERVPN,                                                  ");
		getExistSplitColorDetails.append("	PET_XML.PRODUCT_NAME,                                                     ");
		getExistSplitColorDetails.append("	PET_XML.COLOR_CODE,                                                       ");
		getExistSplitColorDetails.append("	PET_XML.COLOR_DESC                                                        ");
		getExistSplitColorDetails.append("	FROM                                                                      ");
		getExistSplitColorDetails.append("	ADSE_ITEM_CATALOG ITEM,                                                   ");
		getExistSplitColorDetails.append("	ADSE_GROUP_CHILD_MAPPING DETAIL,                                          ");
		getExistSplitColorDetails.append("	ADSE_PET_CATALOG PET,                                                     ");
		getExistSplitColorDetails.append("	XMLTABLE(                                                                 ");
		getExistSplitColorDetails.append("	  'let                                                                    ");
		getExistSplitColorDetails.append("	  $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,     ");
		getExistSplitColorDetails.append("	  $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code      ");
		getExistSplitColorDetails.append("	  return                                                                  ");
		getExistSplitColorDetails.append("	  <COLOR>                                                                 ");
		getExistSplitColorDetails.append("	  <COLOR_CODE>{$colorCode}</COLOR_CODE>                                   ");
		getExistSplitColorDetails.append("	  <COLOR_DESC>{$colordesc}</COLOR_DESC>                                   ");
		getExistSplitColorDetails.append("	  <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>  ");
		getExistSplitColorDetails.append("	  </COLOR>'                                                               ");
		getExistSplitColorDetails.append("	  passing Pet.XML_DATA Columns COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',  ");
		getExistSplitColorDetails.append("	  COLOR_DESC                              VARCHAR2(20) path '/COLOR/COLOR_DESC'  ");
		getExistSplitColorDetails.append("	  , PRODUCT_NAME                          VARCHAR2(20) path               ");
		getExistSplitColorDetails.append("	  '/COLOR/PRODUCT_NAME' ) PET_XML                                         ");
		getExistSplitColorDetails.append("	WHERE                                                                     ");
		getExistSplitColorDetails.append("	1=1                                                                       ");
		getExistSplitColorDetails.append("	AND PET.MDMID=ITEM.MDMID                                                  ");
		getExistSplitColorDetails.append("	AND (ITEM.MDMID = DETAIL.COMPONENT_MDMID           "); // /*To fetch Color data*/
		getExistSplitColorDetails.append("	  OR ITEM.MDMID = DETAIL.COMPONENT_STYLE_ID)  "); // /*To fetch Style data for that Color for Product name*/
		getExistSplitColorDetails.append("	AND DETAIL.MDMID=:groupidSql  ORDER BY ITEM.MDMID "); // /*StyleColor MDMID*/

		return getExistSplitColorDetails.toString();
	}

	/** This method is used to get the Existing Group Attribute Details for Split
	 * SKU from Database.
	 * 
	 * @return String */
	public static  final String getExistSplitSkuDetails() {
		StringBuilder getExistSplitSkuDetails = new StringBuilder();
		getExistSplitSkuDetails.append("	SELECT                                                ");
		getExistSplitSkuDetails.append("	DETAIL.COMPONENT_MDMID,                        ");
		getExistSplitSkuDetails.append("	DETAIL.COMPONENT_DEFAULT,                      ");
		getExistSplitSkuDetails.append("	ITEM.PARENT_MDMID,                             "); // if null, it is style
		getExistSplitSkuDetails.append("	ITEM.MDMID,                                    ");
		getExistSplitSkuDetails.append("	ITEM.ENTRY_TYPE,                               ");
		getExistSplitSkuDetails.append("	ITEM.PRIMARYSUPPLIERVPN,                       ");
		getExistSplitSkuDetails.append("	ITEM_XML.PRODUCT_NAME,                         ");
		getExistSplitSkuDetails.append("	ITEM_XML.COLOR_CODE,                           ");
		getExistSplitSkuDetails.append("	ITEM_XML.COLOR_DESC,                           ");
		getExistSplitSkuDetails.append("	ITEM_XML.SIZEDESC, ITEM_XML.SIZE_CODE          ");
		getExistSplitSkuDetails.append("	FROM                                           ");
		getExistSplitSkuDetails.append("	ADSE_ITEM_CATALOG ITEM,                        ");
		getExistSplitSkuDetails.append("	ADSE_GROUP_CHILD_MAPPING DETAIL,               ");
		getExistSplitSkuDetails.append("	ADSE_PET_CATALOG PET,                          ");
		getExistSplitSkuDetails.append("	XMLTABLE(                                      ");
		getExistSplitSkuDetails.append("	  'let                                         ");
		getExistSplitSkuDetails.append("	  $colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,        ");
		getExistSplitSkuDetails.append("	  $colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description, ");
		getExistSplitSkuDetails.append("	  $size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,");
		getExistSplitSkuDetails.append("	  $sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,");
		getExistSplitSkuDetails.append("	  $name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                  ");
		getExistSplitSkuDetails.append("	  return                                                         ");
		getExistSplitSkuDetails.append("	  <SPEC>                                                         ");
		getExistSplitSkuDetails.append("	  <COLOR_CODE>{$colorcode}</COLOR_CODE>                          ");
		getExistSplitSkuDetails.append("	  <COLOR_DESC>{$colorname}</COLOR_DESC>                          ");
		getExistSplitSkuDetails.append("	  <SIZE>{$size}</SIZE>                                           ");
		getExistSplitSkuDetails.append("	  <SIZE_CODE>{$sizeCode}</SIZE_CODE>                                           ");
		getExistSplitSkuDetails.append("	  <PRODUCT_NAME>{$name}</PRODUCT_NAME>                           ");
		getExistSplitSkuDetails.append("	  </SPEC>'                                                       ");
		getExistSplitSkuDetails.append("	  passing ITEM.XML_DATA Columns                                  ");
		getExistSplitSkuDetails.append("	  COLOR_CODE 			VARCHAR2(5) path '/SPEC/COLOR_CODE',     ");
		getExistSplitSkuDetails.append("	  COLOR_DESC            VARCHAR2(20) path '/SPEC/COLOR_DESC',    ");
		getExistSplitSkuDetails.append("	  SIZEDESC              VARCHAR2(20) path '/SPEC/SIZE',          ");
		getExistSplitSkuDetails.append("	  SIZE_CODE              VARCHAR2(20) path '/SPEC/SIZE_CODE',          ");
		getExistSplitSkuDetails.append("	  PRODUCT_NAME          VARCHAR2(50) path '/SPEC/PRODUCT_NAME')  ");
		getExistSplitSkuDetails.append("	  ITEM_XML                                                       ");
		getExistSplitSkuDetails.append("	WHERE                                                            ");
		getExistSplitSkuDetails.append("	1=1                                                              ");
		getExistSplitSkuDetails.append("	AND PET.MDMID=ITEM.MDMID                                         "); // To fetch Color data
		getExistSplitSkuDetails.append("	AND (ITEM.MDMID = DETAIL.COMPONENT_MDMID)                         ");
		
		getExistSplitSkuDetails.append("	AND DETAIL.MDMID=:groupidSql                                      "); // StyleSKU MDMID

		return getExistSplitSkuDetails.toString();
	}

	/** This method is used to get the Existing Group Attribute Details for Consolidate Product Grouping.
	 * 
	 * @return String */
	public static  final String getExistCPGDetails() {
		StringBuilder getExistCPGDetails = new StringBuilder();
		getExistCPGDetails.append("	SELECT                                                ");
		getExistCPGDetails.append("	DETAIL.COMPONENT_STYLE_ID,                        ");
		getExistCPGDetails.append("	DETAIL.COMPONENT_DEFAULT,                      ");
		getExistCPGDetails.append("	ITEM.PARENT_MDMID,      "); // -- if null, it is style
		getExistCPGDetails.append("	ITEM.MDMID,                                    ");
		getExistCPGDetails.append("	ITEM.ENTRY_TYPE,                               ");
		getExistCPGDetails.append("	ITEM.PRIMARYSUPPLIERVPN,                       ");
		getExistCPGDetails.append("	PET_XML.PRODUCT_NAME,                          ");
		getExistCPGDetails.append("	PET_XML.COLOR_CODE,                            ");
		getExistCPGDetails.append("	PET_XML.COLOR_DESC,                            ");
		getExistCPGDetails.append("	ITEM.CLASS_ID                                  ");
		getExistCPGDetails.append("	FROM                                           ");
		getExistCPGDetails.append("	ADSE_ITEM_CATALOG ITEM,                        ");
		getExistCPGDetails.append("	ADSE_GROUP_CHILD_MAPPING DETAIL,               ");
		getExistCPGDetails.append("	ADSE_PET_CATALOG PET,                          ");
		getExistCPGDetails.append("	XMLTABLE(                                      ");
		getExistCPGDetails.append("	  'let                                         ");                                             
		getExistCPGDetails.append("	  $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,     " );                     
		getExistCPGDetails.append("	  $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code      ");                   
		getExistCPGDetails.append("	  return                                                                  ");                       
		getExistCPGDetails.append("	  <COLOR>                                                                 ");                           
		getExistCPGDetails.append("	  <COLOR_CODE>{$colorCode}</COLOR_CODE>                                   ");                       
		getExistCPGDetails.append("	  <COLOR_DESC>{$colordesc}</COLOR_DESC>                                   ");                       
		getExistCPGDetails.append("	  <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>  ");                   
		getExistCPGDetails.append("	  </COLOR>'                                                               ");
		getExistCPGDetails.append("	  passing Pet.XML_DATA Columns COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',  ");
		getExistCPGDetails.append("	  COLOR_DESC                              VARCHAR2(20) path '/COLOR/COLOR_DESC',  ");
		getExistCPGDetails.append("	  PRODUCT_NAME                          VARCHAR2(20) path '/COLOR/PRODUCT_NAME'   ");
		getExistCPGDetails.append("	   ) PET_XML                  ");
		getExistCPGDetails.append("                                                     ");
		getExistCPGDetails.append("	WHERE                                              ");
		getExistCPGDetails.append("	1=1                                                ");
		getExistCPGDetails.append("	AND PET.MDMID=ITEM.MDMID                           ");
		getExistCPGDetails.append("	AND ITEM.MDMID like CONCAT(DETAIL.COMPONENT_MDMID,'%')    ");
		getExistCPGDetails.append("	AND DETAIL.MDMID=:groupidSql ORDER BY ITEM.ENTRY_TYPE  ");


		return getExistCPGDetails.toString();
	}



	/** This method is used to get the Existing Group Attribute Details for Group By Size Grouping.
	 * 
	 * @return String */
	public static  final String getExistGBSDetails() {
		StringBuilder getExistGBSDetails = new StringBuilder();
		getExistGBSDetails.append("	Select                           ");
		getExistGBSDetails.append("		DETAIL.COMPONENT_MDMID, DETAIL.COMPONENT_DEFAULT, ");
		getExistGBSDetails.append("		ITEM.MDMID,  ITEM.PARENT_MDMID, PET.ENTRY_TYPE,   ");                                                          
		getExistGBSDetails.append("		ITEM.PRIMARYSUPPLIERVPN,                          ");                                          
		getExistGBSDetails.append("		ITEM_XML.NAME,                                    ");                                          
		getExistGBSDetails.append("		ITEM_XML.COLOR_CODE,                              ");                                          
		getExistGBSDetails.append("		ITEM_XML.COLOR_NAME,                              ");                                          
		getExistGBSDetails.append("		ITEM_XML.SIZEDESC, ITEM_XML.SIZE_CODE,            ");                                          
		getExistGBSDetails.append("		PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE, DETAIL.DISPLAY_SEQUENCE");                          
		getExistGBSDetails.append("		FROM                                              ");                                          
		getExistGBSDetails.append("		ADSE_ITEM_CATALOG ITEM,                           ");                                          
		getExistGBSDetails.append("		ADSE_PET_CATALOG PET,                             ");
		getExistGBSDetails.append("	ADSE_GROUP_CHILD_MAPPING DETAIL,                      ");
		getExistGBSDetails.append("		XMLTABLE('let                                     ");                                          
		getExistGBSDetails.append("		$colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,         ");  
		getExistGBSDetails.append("		$colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description,  "); 
		getExistGBSDetails.append("		$size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description, ");  
		getExistGBSDetails.append("		$sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,           ");
		getExistGBSDetails.append("		$name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short    ");                                        
		getExistGBSDetails.append("		return                                              ");                                        
		getExistGBSDetails.append("		    <SPEC>                                          ");                                        
		getExistGBSDetails.append("		        <COLOR_CODE>{$colorcode}</COLOR_CODE>       ");                                        
		getExistGBSDetails.append("		        <COLOR_NAME>{$colorname}</COLOR_NAME>       ");                                        
		getExistGBSDetails.append("		        <SIZE>{$size}</SIZE>                        ");                                        
		getExistGBSDetails.append("		        <SIZE_CODE>{$sizeCode}</SIZE_CODE>          ");                                                      
		getExistGBSDetails.append("		        <NAME>{$name}</NAME>                        ");                                        
		getExistGBSDetails.append("		    </SPEC>'                                        ");                                        
		getExistGBSDetails.append("		passing ITEM.XML_DATA                               ");                                        
		getExistGBSDetails.append("		Columns                                             ");                                        
		getExistGBSDetails.append("		    COLOR_CODE VARCHAR2(5) path '/SPEC/COLOR_CODE', ");                                        
		getExistGBSDetails.append("		    COLOR_NAME VARCHAR2(20) path '/SPEC/COLOR_NAME',");                                        
		getExistGBSDetails.append("		    SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',        ");                                        
		getExistGBSDetails.append("		    SIZE_CODE VARCHAR2(20) path '/SPEC/SIZE_CODE',  ");                                        
		getExistGBSDetails.append("		    NAME VARCHAR2(50) path '/SPEC/NAME') ITEM_XML   ");                                        
		getExistGBSDetails.append("		WHERE                                               ");
		getExistGBSDetails.append("	  1=1                                                   ");           
		getExistGBSDetails.append("	  AND PET.MDMID = ITEM.MDMID                            ");
		getExistGBSDetails.append("	  AND ITEM.ENTRY_TYPE in ('SKU')                        ");
		getExistGBSDetails.append("	  AND (NVL(ITEM.PARENT_MDMID,ITEM.MDMID) = DETAIL.COMPONENT_MDMID)             ");           
		getExistGBSDetails.append("	  AND DETAIL.MDMID =:groupidSql     ");
	


		return getExistGBSDetails.toString();
	}

	/** This method is used to get the New Search Group Attribute Details for Consolidate Product Grouping.
	 * @return String 
	 **/
	public static  final String getNewCPGDetails(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch,	final String supplierSiteIdSearch, final String upcNoSearch, 
			final String deptNoSearch, final String classNoSearch) {
		StringBuilder getNewCPGDetails = new StringBuilder();
		getNewCPGDetails.append("	SELECT                                     ");
		getNewCPGDetails.append("	SEARCH.PARENT_MDMID,                                                         ");
		getNewCPGDetails.append("	SEARCH.MDMID,                                                                ");
		getNewCPGDetails.append("	SEARCH.ENTRY_TYPE, SEARCH.CLASS_ID,                                           ");
		getNewCPGDetails.append("	SEARCH.PRIMARYSUPPLIERVPN,                                                   ");
		getNewCPGDetails.append("	XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_Style_Spec/Product_Name' PASSING SEARCH.xml_Data RETURNING CONTENT ) AS VARCHAR2(1000)) PRODUCT_NAME,                                                        ");
		getNewCPGDetails.append("	XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code' PASSING SEARCH.xml_Data RETURNING CONTENT ) AS VARCHAR2(100)) COLOR_CODE,                                                          ");
		getNewCPGDetails.append("  (SELECT exist_in_group FROM adse_pet_catalog WHERE mdmid=SEARCH.parent_mdmid) ALREADY_IN_GROUP,                                    ");
		getNewCPGDetails.append("	XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description' PASSING SEARCH.xml_Data RETURNING CONTENT ) AS VARCHAR2(100)) COLOR_DESC,                                                           ");
		getNewCPGDetails.append("	  (CASE WHEN (SELECT AGCM.MDMID FROM ADSE_GROUP_CHILD_MAPPING AGCM WHERE AGCM.MDMID = :groupIdSql AND SEARCH.PARENT_MDMID =AGCM.COMPONENT_STYLE_ID) IS NULL THEN 'N' ELSE 'Y' END) EXIST_IN_SAME_GROUP                                           ");
		getNewCPGDetails.append("	FROM                                                                         ");
		getNewCPGDetails.append("	  ADSE_ITEM_CATALOG SEARCH                                                   ");
		getNewCPGDetails.append("	WHERE                                                                        ");
		getNewCPGDetails.append("	1=1                                                                          ");
		getNewCPGDetails.append("	AND SEARCH.DELETED_FLAG ='false' AND SEARCH.ENTRY_TYPE = 'Style'    ");
		getNewCPGDetails.append("	AND EXISTS (SELECT MDMID FROM ADSE_PET_CATALOG APC WHERE APC.MDMID = SEARCH.MDMID) ");

		if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
			getNewCPGDetails.append(" AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
		} 
		if (null != styleOrin && !("").equals(styleOrin.trim())) {
			getNewCPGDetails.append(" AND SEARCH.MDMID = :mdmidSql  ");
		}
		if (null != deptNoSearch && !("").equals(deptNoSearch.trim())) {
			getNewCPGDetails.append(" AND SEARCH.DEPT_ID IN (" + deptNoForInSearch + ")");
		}
		if (null != classNoSearch && !("").equals(classNoSearch.trim())) {
			getNewCPGDetails.append(" AND SEARCH.CLASS_ID IN (" + classNoForInSearch + ")");
		}
		if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
			getNewCPGDetails.append(" AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
		}
		if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
			getNewCPGDetails.append(" AND SEARCH.MDMID  = " +
					"(SELECT " +
					"CASE                                " +
					"when PRIMARY_UPC is null then MDMID          " +    
					"when PRIMARY_UPC = ' '  then MDMID         " +   
					"else     PARENT_MDMID   end PARENT_MDMID " +
					"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
		}
		getNewCPGDetails.append("	UNION ALL                                ");
		getNewCPGDetails.append("	SELECT                                     ");
		getNewCPGDetails.append("	SEARCH.PARENT_MDMID,                                                         ");
		getNewCPGDetails.append("	SEARCH.MDMID,                                                                ");
		getNewCPGDetails.append("	SEARCH.ENTRY_TYPE, SEARCH.CLASS_ID,                                           ");
		getNewCPGDetails.append("	SEARCH.PRIMARYSUPPLIERVPN,                                                   ");
		getNewCPGDetails.append("	XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_Style_Spec/Product_Name' PASSING SEARCH.xml_Data RETURNING CONTENT ) AS VARCHAR2(1000)) PRODUCT_NAME,                                                        ");
		getNewCPGDetails.append("	XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code ' PASSING SEARCH.xml_Data RETURNING CONTENT ) AS VARCHAR2(100)) COLOR_CODE,                                                          ");
		getNewCPGDetails.append("  (SELECT exist_in_group FROM adse_pet_catalog WHERE mdmid=SEARCH.parent_mdmid) ALREADY_IN_GROUP,                                    ");
		getNewCPGDetails.append("	XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description' PASSING SEARCH.xml_Data RETURNING CONTENT ) AS VARCHAR2(1000)) COLOR_DESC,                                                           ");
		getNewCPGDetails.append("	  (CASE WHEN (SELECT AGCM.MDMID FROM ADSE_GROUP_CHILD_MAPPING AGCM WHERE AGCM.MDMID = :groupIdSql AND SEARCH.PARENT_MDMID =AGCM.COMPONENT_STYLE_ID) IS NULL THEN 'N' ELSE 'Y' END) EXIST_IN_SAME_GROUP                                           ");
		getNewCPGDetails.append("	FROM                                                                         ");
		getNewCPGDetails.append("	  ADSE_ITEM_CATALOG SEARCH                                                   ");
		getNewCPGDetails.append("	WHERE                                                                        ");
		getNewCPGDetails.append("	1=1                                                                          ");
		getNewCPGDetails.append("	AND SEARCH.DELETED_FLAG ='false' AND SEARCH.ENTRY_TYPE = 'StyleColor'    ");
		getNewCPGDetails.append("	AND EXISTS (SELECT MDMID FROM ADSE_PET_CATALOG APC WHERE APC.MDMID = SEARCH.MDMID) ");

		if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
			getNewCPGDetails.append(" AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
		} 
		if (null != styleOrin && !("").equals(styleOrin.trim())) {
			getNewCPGDetails.append(" AND SEARCH.PARENT_MDMID = :mdmidSql ");
		}
		if (null != deptNoSearch && !("").equals(deptNoSearch.trim())) {
			getNewCPGDetails.append(" AND SEARCH.DEPT_ID IN (" + deptNoForInSearch + ")");
		}
		if (null != classNoSearch && !("").equals(classNoSearch.trim())) {
			getNewCPGDetails.append(" AND SEARCH.CLASS_ID IN (" + classNoForInSearch + ")");
		}
		if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
			getNewCPGDetails.append(" AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
		}
		if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
			getNewCPGDetails.append(" AND SEARCH.PARENT_MDMID  = " +
					"(SELECT " +
					"CASE                                " +
					"when PRIMARY_UPC is null then MDMID          " +    
					"when PRIMARY_UPC = ' '  then MDMID         " +   
					"else     PARENT_MDMID   end PARENT_MDMID " +
					"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
		}
		getNewCPGDetails.append(" ORDER BY MDMID");


		return getNewCPGDetails.toString();
	}

	/** This method is used to get the SKU Count for a Style Orin.
	 * @return String 
	 **/
	public static  final String getSKUCount(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch,	final String supplierSiteIdSearch, final String upcNoSearch, 
			final String deptNoSearch, final String classNoSearch) {
		StringBuilder getNewGBSDetails = new StringBuilder();
		getNewGBSDetails.append("	Select                                                                                 ");
		getNewGBSDetails.append("		COUNT(ITEM.PARENT_MDMID) COUNT, ITEM.PARENT_MDMID                                           ");                 
		getNewGBSDetails.append("		FROM ADSE_ITEM_CATALOG ITEM,                                                                "); 
		getNewGBSDetails.append("		ADSE_PET_CATALOG PET                                                                        "); 
		getNewGBSDetails.append("		WHERE                                                                                       "); 
		getNewGBSDetails.append("		    ITEM.ENTRY_TYPE in ('SKU')                                                              ");
		getNewGBSDetails.append("		    AND ITEM.DELETED_FLAG= 'false'                                                          "); 
		getNewGBSDetails.append("		    AND PET.MDMID=ITEM.MDMID                                                                ");

		if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
			getNewGBSDetails.append(" AND ITEM.PRIMARYSUPPLIERVPN =:styleIdSql ");
		} 
		if (null != styleOrin && !("").equals(styleOrin.trim())) {
			getNewGBSDetails.append(" AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID) =:mdmidSql ");
		}
		if (null != deptNoSearch && !("").equals(deptNoSearch.trim())) {
			getNewGBSDetails.append(" AND ITEM.DEPT_ID IN (" + deptNoForInSearch + ")");
		}
		if (null != classNoSearch && !("").equals(classNoSearch.trim())) {
			getNewGBSDetails.append(" AND ITEM.CLASS_ID IN (" + classNoForInSearch + ")");
		}
		if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
			getNewGBSDetails.append(" AND ITEM.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
		}
		if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
			getNewGBSDetails.append(" AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID)  = " +
					"(SELECT " +
					"CASE                                " +
					"when PRIMARY_UPC is null then MDMID          " +    
					"when PRIMARY_UPC = ' '  then MDMID         " +   
					"else     PARENT_MDMID   end PARENT_MDMID " +
					"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
		}
		getNewGBSDetails.append(" group by ITEM.PARENT_MDMID having COUNT(ITEM.PARENT_MDMID) > 1");
		

		return getNewGBSDetails.toString();
	}

	/** This method is used to get the New Search Group Attribute Details for GBS Grouping.
	 * @return String 
	 **/
	public static  final String getNewGBSDetails(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch,	final String supplierSiteIdSearch, final String upcNoSearch, 
			final String deptNoSearch, final String classNoSearch) {
		StringBuilder getNewGBSDetails = new StringBuilder();
		getNewGBSDetails.append("	Select                                                                                 ");
		getNewGBSDetails.append("		ITEM.MDMID,  ITEM.PARENT_MDMID, PET.ENTRY_TYPE,                                             ");                 
		getNewGBSDetails.append("		ITEM.PRIMARYSUPPLIERVPN,                                                                    "); 
		getNewGBSDetails.append("		ITEM_XML.NAME,                                                                              "); 
		getNewGBSDetails.append("		ITEM_XML.COLOR_CODE,                                                                        "); 
		getNewGBSDetails.append("		ITEM_XML.COLOR_NAME,                                                                        "); 
		getNewGBSDetails.append("		ITEM_XML.SIZEDESC, ITEM_XML.SIZE_CODE,                                                      "); 
		getNewGBSDetails.append("		PET.EXIST_IN_GROUP ALREADY_IN_GROUP, PET.PET_STATE,                                          ");
		getNewGBSDetails.append("		CASE WHEN AGCM.MDMID is NULL THEN 'N'                                          ");
		getNewGBSDetails.append("		ELSE 'Y' END EXIST_IN_SAME_GROUP                                          ");
		getNewGBSDetails.append("		FROM                                                                                        "); 
		getNewGBSDetails.append("		ADSE_ITEM_CATALOG ITEM                                                                     "); 
		getNewGBSDetails.append("		LEFT  OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM                                              ");
		getNewGBSDetails.append("		ON ITEM.PARENT_MDMID =AGCM.COMPONENT_STYLE_ID                                                             ");
		getNewGBSDetails.append("		AND AGCM.MDMID = :groupIdSql ,                                                                     ");
		getNewGBSDetails.append("		ADSE_PET_CATALOG PET,                                                                       ");  
		getNewGBSDetails.append("		XMLTABLE('let                                                                               "); 
		getNewGBSDetails.append("		$colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,           "); 
		getNewGBSDetails.append("		$colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description,    ");
		getNewGBSDetails.append("		$size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,   "); 
		getNewGBSDetails.append("		$sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,             ");
		getNewGBSDetails.append("		$name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                     "); 
		getNewGBSDetails.append("		return                                                                                      "); 
		getNewGBSDetails.append("		    <SPEC>                                                                                  "); 
		getNewGBSDetails.append("		        <COLOR_CODE>{$colorcode}</COLOR_CODE>                                               "); 
		getNewGBSDetails.append("		        <COLOR_NAME>{$colorname}</COLOR_NAME>                                               "); 
		getNewGBSDetails.append("		        <SIZE>{$size}</SIZE>                                                                "); 
		getNewGBSDetails.append("		        <SIZE_CODE>{$sizeCode}</SIZE_CODE>                                                  ");               
		getNewGBSDetails.append("		        <NAME>{$name}</NAME>                                                                "); 
		getNewGBSDetails.append("		    </SPEC>'                                                                                "); 
		getNewGBSDetails.append("		passing ITEM.XML_DATA                                                                       "); 
		getNewGBSDetails.append("		Columns                                                                                     "); 
		getNewGBSDetails.append("		    COLOR_CODE VARCHAR2(5) path '/SPEC/COLOR_CODE',                                         "); 
		getNewGBSDetails.append("		    COLOR_NAME VARCHAR2(20) path '/SPEC/COLOR_NAME',                                        "); 
		getNewGBSDetails.append("		    SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',                                                "); 
		getNewGBSDetails.append("		    SIZE_CODE VARCHAR2(20) path '/SPEC/SIZE_CODE',                                          "); 
		getNewGBSDetails.append("		    NAME VARCHAR2(50) path '/SPEC/NAME') ITEM_XML                                           "); 
		getNewGBSDetails.append("		WHERE                                                                                       "); 
		getNewGBSDetails.append("		    ITEM.ENTRY_TYPE = 'SKU'                                                              ");
		getNewGBSDetails.append("		    AND ITEM.DELETED_FLAG= 'false'                                                          "); 
		getNewGBSDetails.append("		    AND PET.MDMID=ITEM.PARENT_MDMID  AND EXISTS (SELECT MDMID FROM ADSE_PET_CATALOG APC WHERE APC.MDMID = ITEM.MDMID)       ");

		if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
			getNewGBSDetails.append(" AND ITEM.PRIMARYSUPPLIERVPN =:styleIdSql ");
		} 
		if (null != styleOrin && !("").equals(styleOrin.trim())) {
			getNewGBSDetails.append(" AND (ITEM.PARENT_MDMID =:mdmidSql OR ITEM.MDMID = :mdmidSql) ");
		}
		if (null != deptNoSearch && !("").equals(deptNoSearch.trim())) {
			getNewGBSDetails.append(" AND ITEM.DEPT_ID IN (" + deptNoForInSearch + ")");
		}
		if (null != classNoSearch && !("").equals(classNoSearch.trim())) {
			getNewGBSDetails.append(" AND ITEM.CLASS_ID IN (" + classNoForInSearch + ")");
		}
		if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
			getNewGBSDetails.append(" AND ITEM.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
		}
		if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
			getNewGBSDetails.append(" AND NVL(ITEM.PARENT_MDMID,ITEM.MDMID)  = " +
					"(SELECT " +
					"CASE                                " +
					"when PRIMARY_UPC is null then MDMID          " +    
					"when PRIMARY_UPC = ' '  then MDMID         " +   
					"else     PARENT_MDMID   end PARENT_MDMID " +
					"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
		}
		


		return getNewGBSDetails.toString();
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
		getRegularBeautySearchResult.append("   XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_Style_Spec/Product_Name' PASSING SEARCH.xml_Data RETURNING CONTENT) AS VARCHAR2(1000)) PRODUCT_NAME,                                                           ");
		getRegularBeautySearchResult.append("   XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code' PASSING SEARCH.xml_Data RETURNING CONTENT) AS VARCHAR2(100)) COLOR_CODE,                                                             ");
		getRegularBeautySearchResult.append("   (SELECT exist_in_group  FROM adse_pet_catalog  WHERE mdmid=SEARCH.parent_mdmid) ALREADY_IN_GROUP,                                            ");
		getRegularBeautySearchResult.append("   XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description' PASSING SEARCH.xml_Data RETURNING CONTENT) AS VARCHAR2(100)) COLOR_DESC,                                                             ");
		getRegularBeautySearchResult.append("   (CASE                                                                            ");
		getRegularBeautySearchResult.append("     WHEN (SELECT AGCM.MDMID FROM ADSE_GROUP_CHILD_MAPPING AGCM WHERE AGCM.MDMID = :groupIdSql AND SEARCH.PARENT_MDMID =AGCM.COMPONENT_STYLE_ID) IS NULL THEN 'N' ELSE 'Y' END) EXIST_IN_SAME_GROUP,                                                        ");
		getRegularBeautySearchResult.append("   NULL GROUP_ID  FROM                                                                 ");		
		getRegularBeautySearchResult.append("   ADSE_ITEM_CATALOG SEARCH                                                   ");
		getRegularBeautySearchResult.append(" WHERE 1                                   =1                                 ");
		getRegularBeautySearchResult.append(" AND SEARCH.DELETED_FLAG                   ='false'                           ");
		getRegularBeautySearchResult.append(" AND SEARCH.ENTRY_TYPE                    = 'Style'           ");
		getRegularBeautySearchResult.append(" AND EXISTS (SELECT MDMID FROM ADSE_PET_CATALOG APC WHERE APC.MDMID = SEARCH.MDMID) ");
		if (!StringUtils.isEmpty(vendorStyleNo)) {
			getRegularBeautySearchResult.append("   AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
			count = 1;
		}
		if (!StringUtils.isEmpty(styleOrin)) {
			getRegularBeautySearchResult.append("   AND (SEARCH.PARENT_MDMID = :mdmidSql OR SEARCH.MDMID = :mdmidSql )");
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
			getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = " +
					"(SELECT " +
					"CASE                                " +
					"when PRIMARY_UPC is null then MDMID          " +    
					"when PRIMARY_UPC = ' '  then MDMID         " +   
					"else     PARENT_MDMID   end PARENT_MDMID " +
					"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
			count = 1;
		}	
		if(count == 0 || !groupIdSearch.isEmpty() || !groupNameSearch.isEmpty())
		{
			getRegularBeautySearchResult.append("   AND 1 = 0 ");
		}
		getRegularBeautySearchResult.append(" UNION ALL                                                                    ");
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
		getRegularBeautySearchResult.append("   CASE WHEN EXISTS (SELECT MDMID FROM ADSE_GROUP_CHILD_MAPPING CHILD_GROUP WHERE CHILD_GROUP.COMPONENT_GROUPING_ID=SEARCH.MDMID) THEN 'Y' ELSE 'N' END   ");
		getRegularBeautySearchResult.append(" FROM ADSE_GROUP_CATALOG SEARCH                                               ");
		getRegularBeautySearchResult.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM                                ");
		getRegularBeautySearchResult.append(" ON SEARCH.MDMID =AGCM.COMPONENT_GROUPING_ID                                  ");
		getRegularBeautySearchResult.append(" AND AGCM.MDMID  = :groupIdSql                                                ");
		getRegularBeautySearchResult.append(" AND ROWNUM      =1                                                           ");
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
			getRegularBeautySearchResult.append(" AND UPPER(SEARCH.GROUP_NAME) LIKE '%");
			getRegularBeautySearchResult.append(groupNameSearch.toUpperCase());
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
				getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID) =:mdmidSql ");
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
				getRegularBeautySearchResult.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = " +
						"(SELECT " +
						"CASE                                " +
						"when PRIMARY_UPC is null then MDMID          " +    
						"when PRIMARY_UPC = ' '  then MDMID         " +   
						"else     PARENT_MDMID   end PARENT_MDMID " +
						"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
			}
			getRegularBeautySearchResult.append("     )                                                                        ");	
		}

		if(StringUtils.isEmpty(orderByColumn) || "orinNumber".equals(orderByColumn))
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
		else if("vendorStyle".equals(orderByColumn))
		{
			getRegularBeautySearchResult.append(" ORDER BY PRIMARYSUPPLIERVPN ");
			getRegularBeautySearchResult.append(sortingOrder);
		}
		else if("productName".equals(orderByColumn))
		{
			getRegularBeautySearchResult.append(" ORDER BY PRODUCT_NAME ");
			getRegularBeautySearchResult.append(sortingOrder);			
		}
		else if("colorCode".equals(orderByColumn))
		{
			getRegularBeautySearchResult.append(" ORDER BY COLOR_CODE ");
			getRegularBeautySearchResult.append(sortingOrder);
		}
		else if("colorName".equals(orderByColumn))
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
		
		getRegularBeautySearchResultCountQuery.append(" SELECT COUNT(MDMID) TOTAL_COUNT FROM ");
		getRegularBeautySearchResultCountQuery.append(" (SELECT DISTINCT SEARCH.PARENT_MDMID, ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.MDMID,                                                                   ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.ENTRY_TYPE,                                                              ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.CLASS_ID,                                                                ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.PRIMARYSUPPLIERVPN,                                                      ");
		getRegularBeautySearchResultCountQuery.append("   XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_Style_Spec/Product_Name' PASSING SEARCH.xml_Data RETURNING CONTENT) AS VARCHAR2(1000)) PRODUCT_NAME,                                                           ");
		getRegularBeautySearchResultCountQuery.append("   XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code' PASSING SEARCH.xml_Data RETURNING CONTENT) AS VARCHAR2(100)) COLOR_CODE,                                                             ");
		getRegularBeautySearchResultCountQuery.append("   (SELECT exist_in_group  FROM adse_pet_catalog  WHERE mdmid=SEARCH.parent_mdmid) ALREADY_IN_GROUP,                                            ");
		getRegularBeautySearchResultCountQuery.append("   XMLCAST(XMLQUERY('//pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description' PASSING SEARCH.xml_Data RETURNING CONTENT) AS VARCHAR2(100)) COLOR_DESC,                                                             ");
		getRegularBeautySearchResultCountQuery.append("   (CASE                                                                            ");
		getRegularBeautySearchResultCountQuery.append("     WHEN (SELECT AGCM.MDMID FROM ADSE_GROUP_CHILD_MAPPING AGCM WHERE AGCM.MDMID = :groupIdSql AND SEARCH.PARENT_MDMID =AGCM.COMPONENT_STYLE_ID) IS NULL THEN 'N' ELSE 'Y' END) EXIST_IN_SAME_GROUP,                                                        ");
		getRegularBeautySearchResultCountQuery.append("   NULL GROUP_ID  FROM                                                                 ");		
		getRegularBeautySearchResultCountQuery.append("   ADSE_ITEM_CATALOG SEARCH                                                   ");
		getRegularBeautySearchResultCountQuery.append(" WHERE 1                                   =1                                 ");
		getRegularBeautySearchResultCountQuery.append(" AND SEARCH.DELETED_FLAG                   ='false'                           ");
		getRegularBeautySearchResultCountQuery.append(" AND SEARCH.ENTRY_TYPE                    = 'Style'           ");
		getRegularBeautySearchResultCountQuery.append(" AND EXISTS (SELECT MDMID FROM ADSE_PET_CATALOG APC WHERE APC.MDMID = SEARCH.MDMID) ");
		if (!StringUtils.isEmpty(vendorStyleNo)) {
			getRegularBeautySearchResultCountQuery.append("   AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
			count = 1;
		}
		if (!StringUtils.isEmpty(styleOrin)) {
			getRegularBeautySearchResultCountQuery.append("   AND (SEARCH.PARENT_MDMID = :mdmidSql OR SEARCH.MDMID = :mdmidSql )");
			count = 1;
		}	
		if (!StringUtils.isEmpty(deptNoForInSearch)) {
			getRegularBeautySearchResultCountQuery.append("   AND SEARCH.DEPT_ID IN (" + deptNoSearch + ") ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(classNoForInSearch)) {
			getRegularBeautySearchResultCountQuery.append("   AND SEARCH.CLASS_ID IN (" + classNoSearch + ") ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(supplierSiteIdSearch)) {
			getRegularBeautySearchResultCountQuery.append("   AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
			count = 1;
		}	
		if (!StringUtils.isEmpty(upcNoSearch)) {
			getRegularBeautySearchResultCountQuery.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = " +
					"(SELECT " +
					"CASE                                " +
					"when PRIMARY_UPC is null then MDMID          " +    
					"when PRIMARY_UPC = ' '  then MDMID         " +   
					"else     PARENT_MDMID   end PARENT_MDMID " +
					"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
			count = 1;
		}	
		if(count == 0 || !groupIdSearch.isEmpty() || !groupNameSearch.isEmpty())
		{
			getRegularBeautySearchResultCountQuery.append("   AND 1 = 0 ");
		}
		getRegularBeautySearchResultCountQuery.append(" UNION ALL                                                                    ");
		getRegularBeautySearchResultCountQuery.append(" SELECT DISTINCT NULL PARENT_MDMID,                                           ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.MDMID,                                                              ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.ENTRY_TYPE,                                                         ");
		getRegularBeautySearchResultCountQuery.append("   NULL CLASS_ID,                                                             ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.DEF_PRIMARYSUPPLIERVPN,                                             ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.GROUP_NAME,                                                         ");
		getRegularBeautySearchResultCountQuery.append("   NULL COLOR_CODE,                                                           ");
		getRegularBeautySearchResultCountQuery.append("   SEARCH.EXIST_IN_GROUP ALREADY_IN_GROUP,                                    ");
		getRegularBeautySearchResultCountQuery.append("   NULL COLOR_DESC,                                                           ");
		getRegularBeautySearchResultCountQuery.append("   CASE                                                                       ");
		getRegularBeautySearchResultCountQuery.append("     WHEN SEARCH.MDMID = :groupIdSql                                          ");
		getRegularBeautySearchResultCountQuery.append("     THEN 'Y'                                                                 ");
		getRegularBeautySearchResultCountQuery.append("     WHEN AGCM.MDMID IS NULL                                                  ");
		getRegularBeautySearchResultCountQuery.append("     THEN 'N'                                                                 ");
		getRegularBeautySearchResultCountQuery.append("     ELSE 'Y'                                                                 ");
		getRegularBeautySearchResultCountQuery.append("   END EXIST_IN_SAME_GROUP,                                                   ");
		getRegularBeautySearchResultCountQuery.append("   CASE WHEN EXISTS (SELECT MDMID FROM ADSE_GROUP_CHILD_MAPPING CHILD_GROUP WHERE CHILD_GROUP.COMPONENT_GROUPING_ID=SEARCH.MDMID) THEN 'Y' ELSE 'N' END   ");
		getRegularBeautySearchResultCountQuery.append(" FROM ADSE_GROUP_CATALOG SEARCH                                               ");
		getRegularBeautySearchResultCountQuery.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM                                ");
		getRegularBeautySearchResultCountQuery.append(" ON SEARCH.MDMID =AGCM.COMPONENT_GROUPING_ID                                  ");
		getRegularBeautySearchResultCountQuery.append(" AND AGCM.MDMID  = :groupIdSql                                                ");
		getRegularBeautySearchResultCountQuery.append(" AND ROWNUM      =1                                                           ");
		getRegularBeautySearchResultCountQuery.append(" LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING SEARCH_CHILD                        ");
		getRegularBeautySearchResultCountQuery.append(" ON SEARCH_CHILD.MDMID=SEARCH.MDMID                                           ");
		getRegularBeautySearchResultCountQuery.append(" WHERE 1              =1                                                      ");
		if(!StringUtils.isEmpty(groupIdSearch))
		{
			getRegularBeautySearchResultCountQuery.append(" AND SEARCH.MDMID     = ");
			getRegularBeautySearchResultCountQuery.append(groupIdSearch);
		}
		if(!StringUtils.isEmpty(groupNameSearch))
		{
			getRegularBeautySearchResultCountQuery.append(" AND UPPER(SEARCH.GROUP_NAME) LIKE '%");
			getRegularBeautySearchResultCountQuery.append(groupNameSearch.toUpperCase());
			getRegularBeautySearchResultCountQuery.append("%'");
		}


		getRegularBeautySearchResultCountQuery.append(" AND SEARCH.DELETED_FLAG           = 'false'                                  ");
		if(count == 1)
		{
			getRegularBeautySearchResultCountQuery.append(" AND SEARCH_CHILD.COMPONENT_MDMID IN                                          ");
			getRegularBeautySearchResultCountQuery.append("   (SELECT SEARCH.MDMID                                                       ");
			getRegularBeautySearchResultCountQuery.append("   FROM ADSE_ITEM_CATALOG SEARCH                                              ");
			getRegularBeautySearchResultCountQuery.append("   WHERE 1                                   =1                               ");
			getRegularBeautySearchResultCountQuery.append("   AND SEARCH.DELETED_FLAG                   ='false'                         ");
			getRegularBeautySearchResultCountQuery.append("   AND SEARCH.ENTRY_TYPE                    IN ('Style','StyleColor')         ");
			if (!StringUtils.isEmpty(vendorStyleNo)) {
				getRegularBeautySearchResultCountQuery.append("   AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
			}		
			if (!StringUtils.isEmpty(styleOrin)) {
				getRegularBeautySearchResultCountQuery.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID) =:mdmidSql ");
			}		
			if (!StringUtils.isEmpty(deptNoForInSearch)) {
				getRegularBeautySearchResultCountQuery.append("   AND SEARCH.DEPT_ID IN (" + deptNoSearch + ") ");
			}	
			if (!StringUtils.isEmpty(classNoForInSearch)) {
				getRegularBeautySearchResultCountQuery.append("   AND SEARCH.CLASS_ID IN (" + classNoSearch + ") ");
			}
			if (!StringUtils.isEmpty(supplierSiteIdSearch)) {
				getRegularBeautySearchResultCountQuery.append("   AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
			}		
			if (!StringUtils.isEmpty(upcNoSearch)) {
				getRegularBeautySearchResultCountQuery.append("   AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = " +
						"(SELECT " +
						"CASE                                " +
						"when PRIMARY_UPC is null then MDMID          " +    
						"when PRIMARY_UPC = ' '  then MDMID         " +   
						"else     PARENT_MDMID   end PARENT_MDMID " +
						"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
			}
			getRegularBeautySearchResultCountQuery.append("     )                                                                        ");	
		}
		getRegularBeautySearchResultCountQuery.append("   )                                                                          ");
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
	
	/** This method is used to get the New Search Group Attribute Details for Consolidate Product Grouping.
	 * @return String 
	 **/
	public static  final String getChildQueryForStyleCPGBCGRCG(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch,	final String supplierSiteIdSearch, final String upcNoSearch, 
			final String deptNoSearch, final String classNoSearch) {
		StringBuilder getChildQueryForStyleCPGBCGRCG = new StringBuilder();
		getChildQueryForStyleCPGBCGRCG.append("	SELECT                                                              ");
		getChildQueryForStyleCPGBCGRCG.append("	SEARCH.PARENT_MDMID,                                                         ");
		getChildQueryForStyleCPGBCGRCG.append("	SEARCH.MDMID,                                                                ");
		getChildQueryForStyleCPGBCGRCG.append("	SEARCH.ENTRY_TYPE, SEARCH.CLASS_ID,                                           ");
		getChildQueryForStyleCPGBCGRCG.append("	SEARCH.PRIMARYSUPPLIERVPN,                                                   ");
		getChildQueryForStyleCPGBCGRCG.append("	PET_XML.PRODUCT_NAME,                                                        ");
		getChildQueryForStyleCPGBCGRCG.append("	PET_XML.COLOR_CODE,                                                          ");
		getChildQueryForStyleCPGBCGRCG.append("  PET.EXIST_IN_GROUP ALREADY_IN_GROUP,                                    ");
		getChildQueryForStyleCPGBCGRCG.append("	PET_XML.COLOR_DESC,                                                           ");
		getChildQueryForStyleCPGBCGRCG.append("	  CASE WHEN AGCM.MDMID is NULL THEN 'N'                                      ");
		getChildQueryForStyleCPGBCGRCG.append("	  ELSE 'Y' END EXIST_IN_SAME_GROUP                                           ");
		getChildQueryForStyleCPGBCGRCG.append("	FROM                                                                         ");
		getChildQueryForStyleCPGBCGRCG.append("	ADSE_PET_CATALOG PET,                                                        ");
		getChildQueryForStyleCPGBCGRCG.append("	XMLTABLE(                                                                    ");
		getChildQueryForStyleCPGBCGRCG.append("	  'let                                                                       ");
		getChildQueryForStyleCPGBCGRCG.append("	  $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description, ");
		getChildQueryForStyleCPGBCGRCG.append("	  $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code         ");
		getChildQueryForStyleCPGBCGRCG.append("	  return                                                                     ");
		getChildQueryForStyleCPGBCGRCG.append("	  <COLOR>                                                                    ");
		getChildQueryForStyleCPGBCGRCG.append("	  <COLOR_CODE>{$colorCode}</COLOR_CODE>                                      ");
		getChildQueryForStyleCPGBCGRCG.append("	  <COLOR_DESC>{$colordesc}</COLOR_DESC>                                      ");
		getChildQueryForStyleCPGBCGRCG.append("	  <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>     ");
		getChildQueryForStyleCPGBCGRCG.append("	  </COLOR>'                                                                  ");
		getChildQueryForStyleCPGBCGRCG.append("	  passing Pet.XML_DATA Columns                                               ");
		getChildQueryForStyleCPGBCGRCG.append("	  COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',                           ");
		getChildQueryForStyleCPGBCGRCG.append("	  COLOR_DESC VARCHAR2(20) path '/COLOR/COLOR_DESC',                          ");
		getChildQueryForStyleCPGBCGRCG.append("	  PRODUCT_NAME VARCHAR2(20) path '/COLOR/PRODUCT_NAME' ) PET_XML,            ");
		getChildQueryForStyleCPGBCGRCG.append("	  ADSE_ITEM_CATALOG SEARCH                                                   ");
		getChildQueryForStyleCPGBCGRCG.append("	  LEFT  OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM         			    ");
		getChildQueryForStyleCPGBCGRCG.append("	    ON SEARCH.MDMID =AGCM.COMPONENT_STYLECOLOR_ID             				");
		getChildQueryForStyleCPGBCGRCG.append("	    AND AGCM.MDMID = :groupIdSql                 				");
		getChildQueryForStyleCPGBCGRCG.append("	WHERE                                                                        ");
		getChildQueryForStyleCPGBCGRCG.append("	1=1                                                                          ");
		getChildQueryForStyleCPGBCGRCG.append("	AND PET.MDMID=SEARCH.MDMID                                                   ");
		getChildQueryForStyleCPGBCGRCG.append("	AND SEARCH.DELETED_FLAG ='false' AND SEARCH.ENTRY_TYPE in ('StyleColor')  AND SEARCH.PARENT_MDMID = :styleOrin ");


		if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
			getChildQueryForStyleCPGBCGRCG.append(" AND SEARCH.PRIMARYSUPPLIERVPN =:styleIdSql ");
		} 
		if (null != styleOrin && !("").equals(styleOrin.trim())) {
			getChildQueryForStyleCPGBCGRCG.append(" AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID) =:mdmidSql ");
		}
		if (null != deptNoSearch && !("").equals(deptNoSearch.trim())) {
			getChildQueryForStyleCPGBCGRCG.append(" AND SEARCH.DEPT_ID IN (" + deptNoForInSearch + ")");
		}
		if (null != classNoSearch && !("").equals(classNoSearch.trim())) {
			getChildQueryForStyleCPGBCGRCG.append(" AND SEARCH.CLASS_ID IN (" + classNoForInSearch + ")");
		}
		if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
			getChildQueryForStyleCPGBCGRCG.append(" AND SEARCH.PRIMARY_SUPPLIER_ID =:supplierIdSql ");
		}
		if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
			getChildQueryForStyleCPGBCGRCG.append(" AND NVL(SEARCH.PARENT_MDMID,SEARCH.MDMID)  = " +
					"(SELECT " +
					"CASE                                " +
					"when PRIMARY_UPC is null then MDMID          " +    
					"when PRIMARY_UPC = ' '  then MDMID         " +   
					"else     PARENT_MDMID   end PARENT_MDMID " +
					"FROM ADSE_ITEM_CATALOG WHERE NUMBER_04 =:upcNoSql) ");
		}
		getChildQueryForStyleCPGBCGRCG.append(" ORDER BY SEARCH.MDMID");


		return getChildQueryForStyleCPGBCGRCG.toString();
	}
	
	
	/**
	 * Query to fetch max priority
	 * @return String
	 */
	public static final String getMaxPriorityQuery() {
		StringBuilder fetchMaxPrirotiy = new StringBuilder();
		fetchMaxPrirotiy.append("	SELECT       ");
		fetchMaxPrirotiy.append("	MAX(NVL(CAST(DISPLAY_SEQUENCE AS NUMBER), 0)) MAX_PRIORITY ");
		fetchMaxPrirotiy.append("	FROM ADSE_GROUP_CHILD_MAPPING   ");
		fetchMaxPrirotiy.append(" 	WHERE MDMID = :groupingId ");
		return fetchMaxPrirotiy.toString();
	}
	
	
	/**
	 * Query to fetch all components at Style or Group level
	 * @return String
	 */
	public static final String getComponentList() {
		StringBuilder fetchComponent = new StringBuilder();
		fetchComponent.append("	SELECT  DISTINCT   ");
		fetchComponent.append("	COMPONENT_MDMID ");
		fetchComponent.append("	FROM ADSE_GROUP_CHILD_MAPPING   ");
		fetchComponent.append(" 	WHERE MDMID =:groupingId  AND DISPLAY_SEQUENCE IS NOT NULL");
		return fetchComponent.toString();
	}
       

}
