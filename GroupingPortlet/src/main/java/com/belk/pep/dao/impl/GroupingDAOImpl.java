package com.belk.pep.dao.impl;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.GroupingDAO;
import com.belk.pep.dto.ClassDetails;
import com.belk.pep.dto.CreateGroupDTO;
import com.belk.pep.dto.DepartmentDetails;
import com.belk.pep.dto.GroupSearchDTO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.form.GroupSearchForm;
import com.belk.pep.util.GroupingUtil;

/**
 * This class responsible for handling all the DAO call to the VP Database.
 * 
 * @author AFUPYB3
 * 
 */

public class GroupingDAOImpl implements GroupingDAO {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(GroupingDAOImpl.class.getName());

	/** The session factory. */
	private SessionFactory sessionFactory;
	/** The xquery constants. */
	private XqueryConstants xqueryConstants = new XqueryConstants();

	/**
	 * 
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Sets the session factory.
	 * 
	 * @param sessionFactory
	 *            the new session factory
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method is used to get the Group Header Details from Database.
	 * 
	 * @param groupId
	 *            String
	 * @return CreateGroupDTO
	 * @author Cognizant
	 * @throws PEPFetchException
	 */
	public CreateGroupDTO getGroupHeaderDetails(final String groupId) throws PEPFetchException {
		LOGGER.info("Fetch Group Header Details--> getGroupHeaderDetails-->Start");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Group Id-->" + groupId);
		}

		final XqueryConstants xqueryConstants = new XqueryConstants();
		Session session = null;
		CreateGroupDTO createGroupDTO = null;
		try {
			createGroupDTO = new CreateGroupDTO();
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(xqueryConstants.getGroupHeaderDetails());
			query.setParameter("groupIdSql", groupId);
			// query.setFetchSize(100);

			LOGGER.info("Query-->getGroupHeaderDetails-->" + query);
			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			final List<Object> rows = query.list();

			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {
					final Map rowMap = (Map) row;
					final String groupName = rowMap.get("GROUP_NAME") != null ? rowMap.get("GROUP_NAME").toString() : "";
					// String groupDesc=rowMap.get("DESCRIPTION") != null ?
					// rowMap.get("DESCRIPTION").toString() : "";
					final Clob groupDescClob = (Clob) rowMap.get("DESCRIPTION");
					String groupDesc = GroupingUtil.clobToString(groupDescClob);
					groupDesc = groupDesc != null ? groupDesc : "";

					final String startDate = rowMap.get("EFFECTIVE_START_DATE") != null ? rowMap.get("EFFECTIVE_START_DATE").toString().trim()
							: "";
					final String endDate = rowMap.get("EFFECTIVE_END_DATE") != null ? rowMap.get("EFFECTIVE_END_DATE").toString().trim() : "";
					final String groupStatus = rowMap.get("GROUP_OVERALL_STATUS_CODE") != null ? rowMap.get("GROUP_OVERALL_STATUS_CODE")
							.toString().trim() : "";
					final String groupType = rowMap.get("GROUP_TYPE") != null ? rowMap.get("GROUP_TYPE").toString().trim() : "";
					final String carsGroupType = rowMap.get("CARS_GROUP_TYPE") != null ? rowMap.get("CARS_GROUP_TYPE").toString().trim() : "";

					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("getGroupHeaderDetails.groupName-->" + groupName);
						LOGGER.debug("getGroupHeaderDetails.startDate-->" + startDate);
						LOGGER.debug("getGroupHeaderDetails.groupDesc-->" + groupDesc);
						LOGGER.debug("getGroupHeaderDetails.groupType-->" + groupType);
						LOGGER.debug("getGroupHeaderDetails.carsGroupType-->" + carsGroupType);
					}
					createGroupDTO.setGroupId(groupId);
					createGroupDTO.setGroupName(groupName);
					createGroupDTO.setGroupDesc(groupDesc);
					createGroupDTO.setGroupLaunchDate(startDate);
					createGroupDTO.setEndDate(endDate);
					createGroupDTO.setGroupStatus(groupStatus);
					createGroupDTO.setGroupType(groupType);
					createGroupDTO.setCarsGroupType(carsGroupType);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getGroupHeaderDetails finally block..");
			session.flush();
			// tx.commit();
			session.close();
		}

		LOGGER.info("Fetch Group Header Details--> getGroupHeaderDetails-->End");
		return createGroupDTO;
	}

	/**
	 * This method is used to get the Group Component Details from Database.
	 * 
	 * @param groupId
	 * @return CreateGroupForm
	 * @author Cognizant
	 */
	/*
	 * public CreateGroupForm getGroupComponentDetails(String groupId,
	 * CreateGroupForm createGroupForm){ LOGGER.info(
	 * "Fetch Group Component Details Details--> getGroupComponentDetails-->Start"
	 * ); if(LOGGER.isDebugEnabled()){ LOGGER.debug("Group Id-->"+groupId); }
	 * 
	 * Session session = null; Transaction tx = null; List<GroupAttributeForm>
	 * groupAttributeFormList = null; XqueryConstants xqueryConstants= new
	 * XqueryConstants(); try{ session = sessionFactory.openSession(); tx =
	 * session.beginTransaction(); //Hibernate provides a createSQLQuery method
	 * to let you call your native SQL statement directly. Query query =
	 * session.createSQLQuery(xqueryConstants.getGroupHeaderDetails());
	 * query.setString(1, groupId); query.setFetchSize(100);
	 * 
	 * LOGGER.info("Query-->getGroupComponentDetails-->" + query); // execute
	 * select SQL statement List<Object[]> rows = query.list(); if (rows !=
	 * null) { LOGGER.info("recordsFetched..." + rows.size());
	 * 
	 * for(Object[] row : rows){ String
	 * groupName=row[0]!=null?row[0].toString():null; String
	 * groupDesc=row[1]!=null?row[1].toString():null; String
	 * startDate=row[2]!=null?row[2].toString():null; String
	 * endDate=row[3]!=null?row[3].toString():null;
	 * 
	 * createGroupForm.setGroupId(groupId);
	 * createGroupForm.setGroupName(groupName);
	 * createGroupForm.setGroupDesc(groupDesc);
	 * createGroupForm.setGroupLaunchDate(startDate);
	 * createGroupForm.setEndDate(endDate); } } }catch(Exception e){
	 * e.printStackTrace(); } finally {
	 * LOGGER.info("recordsFetched. getGroupComponentDetails finally block.." );
	 * session.flush(); tx.commit(); session.close(); }
	 * 
	 * LOGGER.info("Fetch Group Header Details--> getGroupComponentDetails-->End"
	 * ); return createGroupForm; }
	 */

	/**
	 * This method is used to get the Group Attribute Details for Split Color
	 * from Database.
	 * 
	 * @param vendorStyleNumber
	 *            String
	 * @param styleOrin
	 *            String
	 * @return List<GroupAttributeForm>
	 */
	public List<GroupAttributeForm> getSplitColorDetails(final String vendorStyleNumber, final String styleOrin) {
		LOGGER.info("Fetch Split Color Details--> getSplitColorDetails-->Start");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor Style No-->" + vendorStyleNumber);
			LOGGER.debug("Style Orin No-->" + styleOrin);
		}
		String vendorStyleNo = vendorStyleNumber;
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		final XqueryConstants xqueryConstants = new XqueryConstants();
		try {
			session = sessionFactory.openSession();
			vendorStyleNo = null == vendorStyleNo ? null : vendorStyleNo.trim().equals("") ? null : vendorStyleNo.trim();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(xqueryConstants.getSplitColorDetails(vendorStyleNo));

			if (vendorStyleNo != null) {
				query.setParameter("styleIdSql", vendorStyleNo);
			} else {
				query.setParameter("mdmidSql", styleOrin);
			}
			query.setFetchSize(100);
			LOGGER.info("Query-->getSplitColorDetails-->" + query);

			groupAttributeFormList = new ArrayList<GroupAttributeForm>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {
					// LOGGER.info("In..." + rows.size());
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					// String parentMdmid=rowMap.get("PARENT_MDMID") != null ?
					// rowMap.get("PARENT_MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String isAlreadyInGroup = rowMap.get("ALREADY_IN_GROUP") != null ? rowMap.get("ALREADY_IN_GROUP").toString()
							: "N";
					final String petStatus = rowMap.get("PET_STATE") != null ? rowMap.get("PET_STATE").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";

					groupAttributeForm.setOrinNumber(mdmid);
					groupAttributeForm.setStyleNumber(styleNo);
					groupAttributeForm.setProdName(productName);
					groupAttributeForm.setColorCode(colorCode);
					groupAttributeForm.setColorName(colorDesc);
					groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
					groupAttributeForm.setSize("");
					groupAttributeForm.setPetStatus(petStatus);
					groupAttributeForm.setEntryType(entryType);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getSplitColorDetails finally block..");
			session.flush();
			session.close();
		}

		LOGGER.info("Fetch Split Color Details--> getSplitColorDetails-->End");
		return groupAttributeFormList;
	}

	/**
	 * This method is used to get the Group Attribute Details for Split SKU from
	 * Database.
	 * 
	 * @param vendorStyleNumber
	 *            String
	 * @param styleOrin
	 *            String
	 * @return List<GroupAttributeForm>
	 */
	public List<GroupAttributeForm> getSplitSKUDetails(final String vendorStyleNumber, final String styleOrin) {

		LOGGER.info("Fetch Split Color Details--> getSplitSKUDetails-->Start");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor Style No-->" + vendorStyleNumber);
			LOGGER.debug("Style Orin No-->" + styleOrin);
		}
		String vendorStyleNo = vendorStyleNumber;
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		final XqueryConstants xqueryConstants = new XqueryConstants();
		try {
			session = sessionFactory.openSession();
			vendorStyleNo = null == vendorStyleNo ? null : vendorStyleNo.trim().equals("") ? null : vendorStyleNo.trim();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(xqueryConstants.getSplitSKUDetails(vendorStyleNo));

			if (vendorStyleNo != null) {
				query.setParameter("styleIdSql", vendorStyleNo);
			} else {
				query.setParameter("mdmidSql", styleOrin);
			}
			query.setFetchSize(100);
			LOGGER.info("Query-->getSplitSKUDetails-->" + query);

			groupAttributeFormList = new ArrayList<GroupAttributeForm>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("NAME") != null ? rowMap.get("NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_NAME") != null ? rowMap.get("COLOR_NAME").toString() : "";
					final String sizeDesc = rowMap.get("SIZEDESC") != null ? rowMap.get("SIZEDESC").toString() : "";
					final String isAlreadyInGroup = rowMap.get("ALREADY_IN_GROUP") != null ? rowMap.get("ALREADY_IN_GROUP").toString()
							: "N";
					final String petStatus = rowMap.get("PET_STATE") != null ? rowMap.get("PET_STATE").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";

					groupAttributeForm.setOrinNumber(mdmid);
					groupAttributeForm.setStyleNumber(styleNo);
					groupAttributeForm.setProdName(productName);
					groupAttributeForm.setColorCode(colorCode);
					groupAttributeForm.setColorName(colorDesc);
					groupAttributeForm.setSize(sizeDesc);
					groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
					groupAttributeForm.setPetStatus(petStatus);
					groupAttributeForm.setEntryType(entryType);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getSplitSKUDetails finally block..");
			session.flush();
			session.close();
		}

		LOGGER.info("Fetch Split SKU Details--> getSplitSKUDetails-->End");
		return groupAttributeFormList;
	}

	/**
	 * Method to get the groups for search group.
	 * 
	 * @param groupSearchForm
	 *            GroupSearchForm
	 * @return List<GroupSearchDTO>
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/19/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@Override
	public List<GroupSearchDTO> groupSearch(final GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering GroupingDAO.groupSearch() method.");
		Session session = null;
		final List<GroupSearchDTO> groupList = new ArrayList<GroupSearchDTO>();
		GroupSearchDTO groupSearchDTO = null;
		List<Object> rows = null;
		final XqueryConstants xqueryConstants = new XqueryConstants();
		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(xqueryConstants.getGroupDetailsQuery(groupSearchForm));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (groupSearchForm.getGroupId() == null || groupSearchForm.getGroupId().trim().equals("")) {
				query.setFirstResult((groupSearchForm.getPageNumber() - 1) * groupSearchForm.getRecordsPerPage());
				query.setMaxResults(groupSearchForm.getRecordsPerPage());
			}
			rows = query.list();

			if (rows != null) {
				for (final Object rowObj : rows) {
					final Map row = (Map) rowObj;
					groupSearchDTO = new GroupSearchDTO();
					groupSearchDTO.setGroupId(row.get("GROUP_ID") == null ? "" : row.get("GROUP_ID").toString());
					groupSearchDTO.setGroupName(row.get("GROUP_NAME") == null ? "" : row.get("GROUP_NAME").toString());
					groupSearchDTO.setGroupType(row.get("GROUP_TYPE") == null ? "" : row.get("GROUP_TYPE").toString());
					groupSearchDTO.setGroupContentStatus(row.get("CONTENT_STATE") == null ? "" : row.get("CONTENT_STATE").toString());
					groupSearchDTO.setStartDate(row.get("START_DATE") == null ? "" : row.get("START_DATE").toString());
					groupSearchDTO.setEndDate(row.get("END_DATE") == null ? "" : row.get("END_DATE").toString());
					groupSearchDTO.setGroupImageStatus(row.get("IMAGE_STATE") == null ? "" : row.get("IMAGE_STATE").toString());
					groupSearchDTO.setChildGroup(row.get("CHILD_GROUP") == null ? "" : row.get("CHILD_GROUP").toString());

					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Grouping Attribute Values -- \nGROUP ID: " + groupSearchDTO.getGroupId() + "\nGROUP NAME: "
								+ groupSearchDTO.getGroupName() + "\nGROUP TYPE: " + groupSearchDTO.getGroupType()
								+ "\nGROUP CONTENT STATUS: " + groupSearchDTO.getGroupContentStatus() + "\nGROUP IMAGE STATUS: "
								+ groupSearchDTO.getGroupImageStatus() + "\nSTART DATE: " + groupSearchDTO.getStartDate() + "\nEND DATE: "
								+ groupSearchDTO.getEndDate());
					}

					groupList.add(groupSearchDTO);
				}
			}
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.info("Exiting GroupingDAO.groupSearch() method.");

		return groupList;
	}

	/**
	 * Method to get the groups count for search group.
	 * 
	 * @param groupSearchForm
	 *            GroupSearchForm
	 * @return int
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/27/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@Override
	public int groupSearchCount(final GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering GroupingDAO.groupSearchCount() method.");
		Session session = null;
		BigDecimal rowCount = new BigDecimal(0);
		List<Object> rows = null;
		final XqueryConstants xqueryConstants = new XqueryConstants();
		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(xqueryConstants.getGroupDetailsCountQuery(groupSearchForm));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			rows = query.list();

			if (rows != null) {
				for (final Object rowObj : rows) {
					final Map row = (Map) rowObj;
					rowCount = (BigDecimal) (row.get("TOTAL_COUNT") == null ? "" : row.get("TOTAL_COUNT"));
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Grouping Attribute Count -- \nCOUNT OF RECORDS: " + rowCount);
					}
				}
			}
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.info("Exiting GroupingDAO.groupSearchCount() method.");

		return rowCount.intValue();
	}

	/**
	 * Method to get the groups for search group.
	 * 
	 * @param groupSearchDTOList
	 *            List<GroupSearchDTO>
	 * @param groupSearchForm
	 *            GroupSearchForm
	 * @return List<GroupSearchDTO>
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/19/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@Override
	public List<GroupSearchDTO> groupSearchParent(final List<GroupSearchDTO> groupSearchDTOList, final GroupSearchForm groupSearchForm)
			throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering GroupingDAO.groupSearchParent() method.");
		Session session = null;
		final List<GroupSearchDTO> groupList = new ArrayList<GroupSearchDTO>();
		GroupSearchDTO groupSearchDTO = null;
		List<Object> rows = null;
		final XqueryConstants xqueryConstants = new XqueryConstants();
		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(xqueryConstants.getGroupDetailsQueryParent(groupSearchDTOList, groupSearchForm));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (groupSearchForm.getPageNumber() == 1) {
				query.setFirstResult((groupSearchForm.getPageNumber() - 1) * groupSearchForm.getRecordsPerPage() - 1);
				query.setMaxResults(groupSearchForm.getRecordsPerPage());
			} else {
				query.setFirstResult(((groupSearchForm.getPageNumber() - 1) * groupSearchForm.getRecordsPerPage()) - 1);
				query.setMaxResults(groupSearchForm.getRecordsPerPage());
			}
			rows = query.list();

			if (rows != null) {
				for (final Object rowObj : rows) {
					final Map row = (Map) rowObj;
					groupSearchDTO = new GroupSearchDTO();
					groupSearchDTO.setGroupId(row.get("GROUP_ID") == null ? "" : row.get("GROUP_ID").toString());
					groupSearchDTO.setGroupName(row.get("GROUP_NAME") == null ? "" : row.get("GROUP_NAME").toString());
					groupSearchDTO.setGroupType(row.get("GROUP_TYPE") == null ? "" : row.get("GROUP_TYPE").toString());
					groupSearchDTO.setGroupContentStatus(row.get("CONTENT_STATE") == null ? "" : row.get("CONTENT_STATE").toString());
					groupSearchDTO.setStartDate(row.get("START_DATE") == null ? "" : row.get("START_DATE").toString());
					groupSearchDTO.setEndDate(row.get("END_DATE") == null ? "" : row.get("END_DATE").toString());
					groupSearchDTO.setComponentGroupId(row.get("COMPONENT_GROUPING_ID") == null ? "" : row.get("COMPONENT_GROUPING_ID")
							.toString());
					groupSearchDTO.setGroupImageStatus(row.get("IMAGE_STATE") == null ? "" : row.get("IMAGE_STATE").toString());
					groupSearchDTO.setChildGroup(row.get("CHILD_GROUP") == null ? "" : row.get("CHILD_GROUP").toString());

					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Grouping Attribute Values -- \nGROUP ID: " + groupSearchDTO.getGroupId() + "\nGROUP NAME: "
								+ groupSearchDTO.getGroupName() + "\nGROUP TYPE: " + groupSearchDTO.getGroupType()
								+ "\nGROUP CONTENT STATUS: " + groupSearchDTO.getGroupContentStatus() + "\nGROUP IMAGE STATUS: "
								+ groupSearchDTO.getGroupImageStatus() + "\nSTART DATE: " + groupSearchDTO.getStartDate() + "\nEND DATE: "
								+ groupSearchDTO.getEndDate() + "\nCOMPONENT GROUP ID: " + groupSearchDTO.getComponentGroupId());
					}

					groupList.add(groupSearchDTO);
				}
			}
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.info("Exiting GroupingDAO.groupSearchParent() method.");
		return groupList;
	}

	/**
	 * Method to get the parents groups count for search group.
	 * 
	 * @param groupSearchDTOList
	 *            List<GroupSearchDTO>
	 * @param groupSearchForm
	 *            GroupSearchForm
	 * @return int
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/27/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@Override
	public int groupSearchParentCount(final List<GroupSearchDTO> groupSearchDTOList, final GroupSearchForm groupSearchForm)
			throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering GroupingDAO.groupSearchParentCount() method.");
		Session session = null;
		BigDecimal rowCount = new BigDecimal(0);
		List<Object> rows = null;
		final XqueryConstants xqueryConstants = new XqueryConstants();
		try {
			session = sessionFactory.openSession();
			final Query query = session
					.createSQLQuery(xqueryConstants.getGroupDetailsCountQueryParent(groupSearchDTOList, groupSearchForm));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			rows = query.list();
			if (rows != null) {
				for (final Object rowObj : rows) {
					final Map row = (Map) rowObj;
					rowCount = (BigDecimal) (row.get("TOTAL_COUNT_PARENT") == null ? "" : row.get("TOTAL_COUNT_PARENT"));
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Parent Grouping Attribute Count -- \nCOUNT OF PARENT RECORDS: " + rowCount);
					}
				}
			}
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.info("Exiting GroupingDAO.groupSearchParentCount() method.");

		return rowCount.intValue();
	}

	/**
	 * Method to get the departments for search group.
	 * 
	 * @return ArrayList<DepartmentDetails>
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/25/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 */
	@Override
	public ArrayList<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException {
		LOGGER.info("Entering getDeptDetailsByDepNoFromADSE() in GroupingDAOImpl class..");
		Session session = null;
		final ArrayList<DepartmentDetails> adseDepartmentList = new ArrayList<DepartmentDetails>();
		try {
			session = sessionFactory.openSession();
			final Query query1 = session.createSQLQuery(xqueryConstants.getLikeDepartmentDetailsForString());
			query1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (query1 != null) {
				final List<Object> rows1 = query1.list();
				if (rows1 != null && rows1.size() > 0) {
					for (final Object row : rows1) {
						final Map rowObj = (Map) row;
						final DepartmentDetails departmentDetails = new DepartmentDetails();
						departmentDetails.setId(rowObj.get("DEPTID") == null ? "" : rowObj.get("DEPTID").toString());
						departmentDetails.setDesc(rowObj.get("DEPTNAME") == null ? "" : rowObj.get("DEPTNAME").toString());
						adseDepartmentList.add(departmentDetails);
					}
				}
			}
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.info("Exiting getDeptDetailsByDepNoFromADSE() in GroupingDAOImpl class..");
		return adseDepartmentList;
	}

	/**
	 * Method to get the classes for search group.
	 * 
	 * @param departmentNumbers
	 *            String
	 * @return List<ClassDetails>
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/25/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 */
	@Override
	public List<ClassDetails> getClassDetailsByDepNos(final String departmentNumbers) throws PEPPersistencyException {
		LOGGER.info("Entering getClassDetailsByDepNos() in GroupingDAOImpl class..");
		Session session = null;
		final List<ClassDetails> classDetailsList = new ArrayList<ClassDetails>();
		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(xqueryConstants.getClassDetailsUsingDeptnumbers(departmentNumbers));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			final List<Object> rows = query.list();
			for (final Object row : rows) {
				final Map rowObj = (Map) row;
				final ClassDetails classDetails = new ClassDetails();
				classDetails.setId(rowObj.get("CLASS_ID") == null ? "" : rowObj.get("CLASS_ID").toString());
				classDetails.setDesc(rowObj.get("CLASS_NAME") == null ? "" : rowObj.get("CLASS_NAME").toString());
				classDetailsList.add(classDetails);
			}
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.info("Exiting getClassDetailsByDepNos() in GroupingDAOImpl class..");
		return classDetailsList;

	}

	/**
	 * This method is used to get the Existing Group Attribute Details for Split
	 * Color from Database.
	 * 
	 * @param groupId
	 * @return groupAttributeFormList
	 */
	public List<GroupAttributeForm> getExistSplitColorDetails(final String groupId) {
		LOGGER.info("Fetch Split Color Existing Details. getExistSplitColorDetails-->Start");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor groupId-->" + groupId);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		final XqueryConstants xqueryConstants = new XqueryConstants();
		try {
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(xqueryConstants.getExistSplitColorDetails());

			query.setParameter("groupidSql", groupId);
			query.setFetchSize(100);
			LOGGER.info("Query-->getExistSplitColorDetails-->" + query);

			groupAttributeFormList = new ArrayList<GroupAttributeForm>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {
					// LOGGER.info("In..." + rows.size());
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";

					groupAttributeForm.setOrinNumber(mdmid);
					groupAttributeForm.setStyleNumber(styleNo);
					groupAttributeForm.setProdName(productName);
					groupAttributeForm.setColorCode(colorCode);
					groupAttributeForm.setColorName(colorDesc);
					groupAttributeForm.setIsAlreadyInGroup("");
					groupAttributeForm.setSize("");
					groupAttributeForm.setPetStatus("");
					groupAttributeForm.setEntryType(entryType);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getExistSplitColorDetails finally block..");
			session.flush();
			session.close();
		}

		LOGGER.info("Fetch Split Color Existing Details. getExistSplitColorDetails-->End");
		return groupAttributeFormList;
	}

	/**
	 * This method is used to get the Existing Group Attribute Details for Split
	 * Sku from Database.
	 * 
	 * @param groupId
	 * @return groupAttributeFormList
	 */
	public List<GroupAttributeForm> getExistSplitSkuDetails(final String groupId) {
		LOGGER.info("Fetch Split Color Existing Details. getExistSplitSkuDetails-->Start");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor groupId-->" + groupId);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		final XqueryConstants xqueryConstants = new XqueryConstants();
		try {
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(xqueryConstants.getExistSplitSkuDetails());

			query.setParameter("groupidSql", groupId);
			query.setFetchSize(100);
			LOGGER.info("Query-->getExistSplitSkuDetails-->" + query);

			groupAttributeFormList = new ArrayList<GroupAttributeForm>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {
					// LOGGER.info("In..." + rows.size());
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String sizeDesc = rowMap.get("SIZEDESC") != null ? rowMap.get("SIZEDESC").toString() : "";

					groupAttributeForm.setOrinNumber(mdmid);
					groupAttributeForm.setStyleNumber(styleNo);
					groupAttributeForm.setProdName(productName);
					groupAttributeForm.setColorCode(colorCode);
					groupAttributeForm.setColorName(colorDesc);
					groupAttributeForm.setIsAlreadyInGroup("");
					groupAttributeForm.setSize("");
					groupAttributeForm.setPetStatus("");
					groupAttributeForm.setEntryType(entryType);
					groupAttributeForm.setSize(sizeDesc);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getExistSplitSkuDetails finally block..");
			session.flush();
			session.close();
		}

		LOGGER.info("Fetch Split Color Existing Details. getExistSplitColorDetails-->End");
		return groupAttributeFormList;
	}

}
