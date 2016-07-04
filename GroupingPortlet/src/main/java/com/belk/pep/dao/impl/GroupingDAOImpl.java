package com.belk.pep.dao.impl;

import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.belk.pep.constants.GroupingConstants;
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
import com.belk.pep.form.StyleAttributeForm;
import com.belk.pep.util.DateUtil;
import com.belk.pep.util.GroupingUtil;
import com.belk.pep.util.PetLock;
import com.belk.pep.util.PetLockPK;

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
	 * @param sessionFactory the new session factory
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method is used to get the Group Header Details from Database.
	 * 
	 * @param groupId String
	 * @return CreateGroupDTO
	 * @author Cognizant
	 * @throws PEPFetchException
	 */
	public CreateGroupDTO getGroupHeaderDetails(final String groupId) throws PEPFetchException, ParseException {
		LOGGER.info("Fetch Group Header Details--> getGroupHeaderDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Group Id-->" + groupId);
		}

		Session session = null;
		CreateGroupDTO createGroupDTO = null;
		try {
			createGroupDTO = new CreateGroupDTO();
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.

			final Query query = session.createSQLQuery(XqueryConstants.getGroupHeaderDetails());
			query.setParameter("groupIdSql", groupId);

			LOGGER.info("Query-->getGroupHeaderDetails-->" + query);
			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();

			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {
					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					String startDate = null;
					String endDate = null;
					java.util.Date startDateDt = null;
					java.util.Date endDateDt = null;
					String isAlreadyInGroup = "";
					final String groupName = rowMap.get("GROUP_NAME") != null ? rowMap.get("GROUP_NAME").toString() : "";

					final Clob groupDescClob = (Clob) rowMap.get("DESCRIPTION");
					String groupDesc = GroupingUtil.clobToString(groupDescClob);

					final String groupStatus = rowMap.get("GROUP_OVERALL_STATUS_CODE") != null ? rowMap.get("GROUP_OVERALL_STATUS_CODE")
							.toString().trim() : "";
					final String groupType = rowMap.get("GROUP_TYPE") != null ? rowMap.get("GROUP_TYPE").toString().trim() : "";
					final String carsGroupType = rowMap.get("CARS_GROUP_TYPE") != null ? rowMap.get("CARS_GROUP_TYPE").toString().trim()
							: "";

					if (GroupingConstants.GROUP_TYPE_BEAUTY_COLLECTION.equals(groupType)) {
						startDateDt = (Date) rowMap.get("START_DATE");
						endDateDt = (Date) rowMap.get("END_DATE");

						if (null != startDateDt) {
							startDate = DateUtil.DateToStringMMddyyyy(startDateDt);
						}
						if (null != endDateDt) {
							endDate = DateUtil.DateToStringMMddyyyy(endDateDt);
						}
					}
					if (GroupingConstants.GROUP_TYPE_BEAUTY_COLLECTION.equals(groupType)
							|| GroupingConstants.GROUP_TYPE_REGULAR_COLLECTION.equals(groupType)) {
						isAlreadyInGroup = rowMap.get("EXIST_IN_GROUP") != null ? rowMap.get("EXIST_IN_GROUP").toString() : "N";
					}

					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("getGroupHeaderDetails.groupName-->" + groupName + "startDate-->" + startDate + "groupDesc-->"
								+ groupDesc + "groupType-->" + groupType + "carsGroupType-->" + carsGroupType);
					}
					createGroupDTO.setGroupId(groupId);
					createGroupDTO.setGroupName(groupName);
					createGroupDTO.setGroupDesc(groupDesc);
					createGroupDTO.setGroupLaunchDate(startDate);
					createGroupDTO.setEndDate(endDate);
					createGroupDTO.setGroupStatus(groupStatus);
					createGroupDTO.setGroupType(groupType);
					createGroupDTO.setCarsGroupType(carsGroupType);
					createGroupDTO.setIsAlreadyInGroup(isAlreadyInGroup);
				}
			}
		} catch (PEPFetchException e) {
			LOGGER.error("inside PEPFetchException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (ParseException e) {
			LOGGER.error("inside ParseException-->" + e);
			throw new ParseException(e.getMessage(), 0);
		} finally {
			LOGGER.info("recordsFetched. getGroupHeaderDetails finally block..");
			if (session != null) {

				session.close();
			}
		}

		LOGGER.info("Fetch Group Header Details--> getGroupHeaderDetails-->End.");
		return createGroupDTO;
	}

	/**
	 * This method is used to get the Group Attribute Details for Split Color
	 * from Database.
	 * 
	 * @param vendorStyleNumber String
	 * @param styleOrin String
	 * @return List
	 */
	public List<GroupAttributeForm> getSplitColorDetails(final String vendorStyleNumber, final String styleOrin) throws PEPFetchException {
		LOGGER.info("Fetch Split Color Details--> getSplitColorDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor Style No-->" + vendorStyleNumber);
			LOGGER.debug("Style Orin No-->" + styleOrin);
		}
		String vendorStyleNo = vendorStyleNumber;
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;

		try {
			session = sessionFactory.openSession();
			vendorStyleNo = null == vendorStyleNo ? null : "".equals(vendorStyleNo.trim()) ? null : vendorStyleNo.trim();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getSplitColorDetails(vendorStyleNo));

			if (vendorStyleNo != null) {
				query.setParameter("styleIdSql", vendorStyleNo);
			} else {
				query.setParameter("mdmidSql", styleOrin);
			}
			LOGGER.info("Query-->getSplitColorDetails-->" + query);

			groupAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
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
					groupAttributeForm.setParentMdmid(parentMdmid);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getSplitColorDetails finally block..");
			if (session != null) {

				session.close();
			}

		}

		LOGGER.info("Fetch Split Color Details--> getSplitColorDetails-->End.");
		return groupAttributeFormList;
	}

	/**
	 * This method is used to get the Group Attribute Details for Split SKU from
	 * Database.
	 * 
	 * @param vendorStyleNumber String
	 * @param styleOrin String
	 * @return List
	 */
	public List<GroupAttributeForm> getSplitSKUDetails(final String vendorStyleNumber, final String styleOrin) throws PEPFetchException {

		LOGGER.info("Fetch Split Color Details--> getSplitSKUDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor Style No-->" + vendorStyleNumber);
			LOGGER.debug("Style Orin No-->" + styleOrin);
		}
		String vendorStyleNo = vendorStyleNumber;
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;

		try {
			session = sessionFactory.openSession();
			vendorStyleNo = null == vendorStyleNo ? null : "".equals(vendorStyleNo.trim()) ? null : vendorStyleNo.trim();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getSplitSKUDetails(vendorStyleNo));

			if (vendorStyleNo != null) {
				query.setParameter("styleIdSql", vendorStyleNo);
			} else {
				query.setParameter("mdmidSql", styleOrin);
			}
			LOGGER.info("Query-->getSplitSKUDetails-->" + query);

			groupAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {
					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("NAME") != null ? rowMap.get("NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_NAME") != null ? rowMap.get("COLOR_NAME").toString() : "";
					final String sizeDesc = rowMap.get("SIZEDESC") != null ? rowMap.get("SIZEDESC").toString() : "";
					final String sizeCode = rowMap.get("SIZE_CODE") != null ? rowMap.get("SIZE_CODE").toString() : "";
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
					groupAttributeForm.setSizeCode(sizeCode);
					groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
					groupAttributeForm.setPetStatus(petStatus);
					groupAttributeForm.setEntryType(entryType);
					groupAttributeForm.setParentMdmid(parentMdmid);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getSplitSKUDetails finally block..");
			if (session != null) {

				session.close();
			}

		}

		LOGGER.info("Fetch Split SKU Details--> getSplitSKUDetails-->End.");
		return groupAttributeFormList;
	}

	/**
	 * This method is used to get the Existing Group Attribute Details for
	 * Consolidated Product Grouping from Database.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @return
	 * @throws PEPFetchException
	 */
	public List<StyleAttributeForm> getNewCPGDetails(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId)
			throws PEPFetchException {
		LOGGER.info("Fetch New CPG attribute Details. getNewCPGDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor vendorStyleNumber-->" + vendorStyleNo);
			LOGGER.debug("Style Orin No-->" + styleOrin);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		StyleAttributeForm styleAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		List<StyleAttributeForm> styleAttributeFormList = null;

		try {
			String deptNoForInSearch = GroupingUtil.getInValForQuery(deptNoSearch);
			String classNoSearchSearch = GroupingUtil.getInValForQuery(classNoSearch);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("For query deptNoForInSearch-->" + deptNoForInSearch);
				LOGGER.debug("For query classNoSearchSearch-->" + classNoSearchSearch);
			}
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getNewCPGDetails(vendorStyleNo, styleOrin, deptNoForInSearch,
					classNoSearchSearch, supplierSiteIdSearch, upcNoSearch, deptNoSearch, classNoSearch));

			query.setParameter("groupIdSql", groupId);
			if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
				query.setParameter("styleIdSql", vendorStyleNo);
			}
			if (null != styleOrin && !("").equals(styleOrin.trim())) {
				query.setParameter("mdmidSql", styleOrin);
			}

			if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
				query.setParameter("supplierIdSql", supplierSiteIdSearch);
			}
			if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
				query.setParameter("upcNoSql", upcNoSearch);
			}

			LOGGER.info("Query-->getNewCPGDetails-->" + query);

			styleAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			// PROOF
			/*
			 * query.setFirstResult(((1 - 1) * 10)); query.setMaxResults(10);
			 */

			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();
					styleAttributeForm = new StyleAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
					final String componentStyleId = rowMap.get("COMPONENT_STYLE_ID") != null ? rowMap.get("COMPONENT_STYLE_ID").toString()
							: "";
					final String isDefaultAttr = rowMap.get("COMPONENT_DEFAULT") != null ? rowMap.get("COMPONENT_DEFAULT").toString() : "";
					final String classId = rowMap.get("CLASS_ID") != null ? rowMap.get("CLASS_ID").toString() : "";
					final String isAlreadyInGroup = rowMap.get("ALREADY_IN_GROUP") != null ? rowMap.get("ALREADY_IN_GROUP").toString() : "";
					final String isAlreadyInSameGroup = rowMap.get("EXIST_IN_SAME_GROUP") != null ? rowMap.get("EXIST_IN_SAME_GROUP")
							.toString() : "";

					if (("Style").equals(entryType)) {
						groupAttributeFormList = new ArrayList<>();
						styleAttributeForm.setOrinNumber(mdmid);
						styleAttributeForm.setStyleNumber(styleNo);
						styleAttributeForm.setProdName(productName);
						styleAttributeForm.setColorCode(colorCode);
						styleAttributeForm.setColorName(colorDesc);
						styleAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
						styleAttributeForm.setIsAlreadyInSameGroup(isAlreadyInSameGroup);
						styleAttributeForm.setSize("");
						styleAttributeForm.setPetStatus("");
						styleAttributeForm.setEntryType(entryType);
						styleAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setComponentStyleId(componentStyleId);
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormList);
						styleAttributeForm.setIsDefault(isDefaultAttr);
						styleAttributeForm.setClassId(classId);

						styleAttributeFormList.add(styleAttributeForm);
					} else {
						groupAttributeForm.setOrinNumber(mdmid);
						groupAttributeForm.setStyleNumber(styleNo);
						groupAttributeForm.setProdName(productName);
						groupAttributeForm.setColorCode(colorCode);
						groupAttributeForm.setColorName(colorDesc);
						groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
						groupAttributeForm.setIsAlreadyInSameGroup(isAlreadyInSameGroup);
						groupAttributeForm.setSize("");
						groupAttributeForm.setPetStatus("");
						groupAttributeForm.setEntryType(entryType);
						groupAttributeForm.setParentMdmid(parentMdmid);
						groupAttributeForm.setComponentStyleId(componentStyleId);
						groupAttributeForm.setIsDefault(isDefaultAttr);
						groupAttributeForm.setClassId(classId);

						for (int i = 0; i < styleAttributeFormList.size(); i++) {
							StyleAttributeForm styleAttributeFormSub = styleAttributeFormList.get(i);

							if ((styleAttributeFormSub.getOrinNumber()).equals(parentMdmid)) {
								LOGGER.debug("styleAttributeFormSub.getOrinNumber()-->" + styleAttributeFormSub.getOrinNumber());
								LOGGER.debug("Style Available.parentMdmid-->" + parentMdmid);
								groupAttributeForm.setProdName(styleAttributeFormSub.getProdName()); // Replacing
																										// Product
																										// Name
																										// from
																										// Style
								groupAttributeFormList = styleAttributeFormSub.getGroupAttributeFormList();
								groupAttributeFormList.add(groupAttributeForm);
								break;
							}
						}

					}
				}
			}
		} catch (Exception ex) {
			LOGGER.error("inside PEPFetchException-->" + ex);
			throw new PEPFetchException(ex.getMessage());
		} finally {
			LOGGER.info("recordsFetched. getNewCPGDetails finally block..");
			if (session != null) {

				session.close();
			}

		}

		LOGGER.info("Fetch New CPG attribute Details. getNewCPGDetails-->End.");
		return styleAttributeFormList;
	}

	/**
	 * This method is used to get the SKU Count for a Style Orin.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @return
	 * @throws PEPFetchException
	 */
	public List<String> getSKUCount(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId)
			throws PEPFetchException {
		LOGGER.info("Fetch New GBS attribute Details. getSKUCount-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor vendorStyleNumber-->" + vendorStyleNo);
			LOGGER.debug("Style Orin No-->" + styleOrin);
		}
		Session session = null;
		List<String> orinList = null;

		try {
			String deptNoForInSearch = GroupingUtil.getInValForQuery(deptNoSearch);
			String classNoSearchSearch = GroupingUtil.getInValForQuery(classNoSearch);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("For query deptNoForInSearch-->" + deptNoForInSearch);
				LOGGER.debug("For query classNoSearchSearch-->" + classNoSearchSearch);
			}
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getSKUCount(vendorStyleNo, styleOrin, deptNoForInSearch,
					classNoSearchSearch, supplierSiteIdSearch, upcNoSearch, deptNoSearch, classNoSearch));

			// query.setParameter("groupIdSql", groupId);
			if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
				query.setParameter("styleIdSql", vendorStyleNo);
			}
			if (null != styleOrin && !("").equals(styleOrin.trim())) {
				query.setParameter("mdmidSql", styleOrin);
			}
			if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
				query.setParameter("supplierIdSql", supplierSiteIdSearch);
			}
			if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
				query.setParameter("upcNoSql", upcNoSearch);
			}
			LOGGER.info("Query-->getSKUCount-->" + query);

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());
				orinList = new ArrayList<>();

				for (final Object row : rows) {
					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;

					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";

					orinList.add(parentMdmid);

				}
			}
		} catch (Exception ex) {
			LOGGER.error("inside PEPFetchException-->" + ex);
			throw new PEPFetchException(ex.getMessage());
		} finally {
			LOGGER.info("recordsFetched. getSKUCount finally block..");
			if (session != null) {
				session.close();
			}
		}

		LOGGER.info("Fetch New GBS attribute Details. getSKUCount-->End.");
		return orinList;
	}

	/**
	 * This method is used to get the Existing Group Attribute Details for GBS
	 * Grouping from Database.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @return
	 * @throws PEPFetchException
	 */
	public List<GroupAttributeForm> getNewGBSDetails(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId,
			List<String> orinList) throws PEPFetchException {
		LOGGER.info("Fetch New GBS attribute Details. getNewGBSDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor vendorStyleNumber-->" + vendorStyleNo);
			LOGGER.debug("Style Orin No-->" + styleOrin);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		// StyleAttributeForm styleAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		// List<StyleAttributeForm> styleAttributeFormList = null;

		try {
			String deptNoForInSearch = GroupingUtil.getInValForQuery(deptNoSearch);
			String classNoSearchSearch = GroupingUtil.getInValForQuery(classNoSearch);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("For query deptNoForInSearch-->" + deptNoForInSearch);
				LOGGER.debug("For query classNoSearchSearch-->" + classNoSearchSearch);
			}
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getNewGBSDetails(vendorStyleNo, styleOrin, deptNoForInSearch,
					classNoSearchSearch, supplierSiteIdSearch, upcNoSearch, deptNoSearch, classNoSearch));

			query.setParameter("groupIdSql", groupId);
			if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
				query.setParameter("styleIdSql", vendorStyleNo);
			}
			if (null != styleOrin && !("").equals(styleOrin.trim())) {
				query.setParameter("mdmidSql", styleOrin);
			}
			if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
				query.setParameter("supplierIdSql", supplierSiteIdSearch);
			}
			if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
				query.setParameter("upcNoSql", upcNoSearch);
			}
			LOGGER.info("Query-->getNewGBSDetails-->" + query);

			// styleAttributeFormList = new ArrayList<>();
			groupAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";

					if (!orinList.contains(parentMdmid)) {

						final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
						final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
						String productName = rowMap.get("NAME") != null ? rowMap.get("NAME").toString() : "";
						if (productName.indexOf(":") != -1) {
							productName = productName.substring(0, productName.indexOf(":"));
						}
						final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
						final String colorDesc = rowMap.get("COLOR_NAME") != null ? rowMap.get("COLOR_NAME").toString() : "";
						final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
						final String sizeDesc = rowMap.get("SIZEDESC") != null ? rowMap.get("SIZEDESC").toString() : "";
						final String sizeCode = rowMap.get("SIZE_CODE") != null ? rowMap.get("SIZE_CODE").toString() : "";
						final String isAlreadyInGroup = rowMap.get("ALREADY_IN_GROUP") != null ? rowMap.get("ALREADY_IN_GROUP").toString()
								: "";
						final String petState = rowMap.get("PET_STATE") != null ? rowMap.get("PET_STATE").toString() : "";
						final String isAlreadyInSameGroup = rowMap.get("EXIST_IN_SAME_GROUP") != null ? rowMap.get("EXIST_IN_SAME_GROUP")
								.toString() : "";

						groupAttributeForm.setOrinNumber(mdmid);
						groupAttributeForm.setStyleNumber(styleNo);
						groupAttributeForm.setProdName(productName);
						groupAttributeForm.setColorCode(colorCode);
						groupAttributeForm.setColorName(colorDesc);
						groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
						groupAttributeForm.setSize(sizeDesc);
						groupAttributeForm.setSizeCode(sizeCode);
						groupAttributeForm.setPetStatus(petState);
						groupAttributeForm.setEntryType(entryType);
						groupAttributeForm.setParentMdmid(parentMdmid);
						groupAttributeForm.setIsAlreadyInSameGroup(isAlreadyInSameGroup);

						groupAttributeFormList.add(groupAttributeForm);
					}

				}
			}
		} catch (Exception ex) {
			LOGGER.error("inside PEPFetchException-->" + ex);
			throw new PEPFetchException(ex.getMessage());
		} finally {
			LOGGER.info("recordsFetched. getNewGBSDetails finally block..");
			if (session != null) {
				session.close();
			}
		}

		LOGGER.info("Fetch New GBS attribute Details. getNewGBSDetails-->End.");
		return groupAttributeFormList;
	}

	/**
	 * Method to get the groups for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return List
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/19/2016 Added
	 *         By: Cognizant
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GroupSearchDTO> groupSearch(final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering GroupingDAO.groupSearch() method.");
		Session session = null;
		final List<GroupSearchDTO> groupList = new ArrayList<>();
		GroupSearchDTO groupSearchDTO = null;
		List<Object> rows = null;

		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(XqueryConstants.getGroupDetailsQuery(groupSearchForm));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (groupSearchForm.getGroupId() == null || "".equals(groupSearchForm.getGroupId().trim())) {
				query.setFirstResult((groupSearchForm.getPageNumber() - 1) * groupSearchForm.getRecordsPerPage());
				query.setMaxResults(groupSearchForm.getRecordsPerPage());
			}
			rows = query.list();

			if (rows != null) {
				for (final Object rowObj : rows) {
					@SuppressWarnings("rawtypes")
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
		} catch (Exception ex) {
			LOGGER.error("inside groupSearch.Exception-->" + ex);
		} finally {
			if (session != null) {

				session.close();
			}
		}
		LOGGER.info("Exiting GroupingDAO.groupSearch() method.");

		return groupList;
	}

	/**
	 * Method to get the groups count for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return int
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/27/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int groupSearchCount(final GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering GroupingDAO.groupSearchCount() method.");
		Session session = null;
		BigDecimal rowCount = new BigDecimal(0);
		List<Object> rows = null;

		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(XqueryConstants.getGroupDetailsCountQuery(groupSearchForm));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			rows = query.list();

			if (rows != null) {
				for (final Object rowObj : rows) {
					@SuppressWarnings("rawtypes")
					final Map row = (Map) rowObj;
					rowCount = (BigDecimal) (row.get("TOTAL_COUNT") == null ? "" : row.get("TOTAL_COUNT"));
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Grouping Attribute Count -- \nCOUNT OF RECORDS: " + rowCount);
					}
				}
			}
		} finally {
			if (session != null) {

				session.close();
			}

		}
		LOGGER.info("Exiting GroupingDAO.groupSearchCount() method.");

		return rowCount.intValue();
	}

	/**
	 * Method to get the groups for search group.
	 * 
	 * @param groupSearchDTOList List
	 * @param groupSearchForm GroupSearchForm
	 * @return List
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/19/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GroupSearchDTO> groupSearchParent(final List<GroupSearchDTO> groupSearchDTOList, final GroupSearchForm groupSearchForm)
			throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering GroupingDAO.groupSearchParent() method.");
		Session session = null;
		final List<GroupSearchDTO> groupList = new ArrayList<>();
		GroupSearchDTO groupSearchDTO = null;
		List<Object> rows = null;

		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(XqueryConstants.getGroupDetailsQueryParent(groupSearchDTOList, groupSearchForm));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			query.setFirstResult(((groupSearchForm.getPageNumber() - 1) * groupSearchForm.getRecordsPerPage()) - 1);
			query.setMaxResults(groupSearchForm.getRecordsPerPage());

			rows = query.list();

			if (rows != null) {
				for (final Object rowObj : rows) {
					@SuppressWarnings("rawtypes")
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
			if (session != null) {

				session.close();
			}

		}
		LOGGER.info("Exiting GroupingDAO.groupSearchParent() method.");
		return groupList;
	}

	/**
	 * Method to get the parents groups count for search group.
	 * 
	 * @param groupSearchDTOList List
	 * @param groupSearchForm GroupSearchForm
	 * @return int
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/27/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int groupSearchParentCount(final List<GroupSearchDTO> groupSearchDTOList, final GroupSearchForm groupSearchForm)
			throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering GroupingDAO.groupSearchParentCount() method.");
		Session session = null;
		BigDecimal rowCount = new BigDecimal(0);
		List<Object> rows = null;

		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(XqueryConstants.getGroupDetailsCountQueryParent(groupSearchDTOList));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			rows = query.list();
			if (rows != null) {
				for (final Object rowObj : rows) {
					@SuppressWarnings("rawtypes")
					final Map row = (Map) rowObj;
					rowCount = (BigDecimal) (row.get("TOTAL_COUNT_PARENT") == null ? "" : row.get("TOTAL_COUNT_PARENT"));
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Parent Grouping Attribute Count -- \nCOUNT OF PARENT RECORDS: " + rowCount);
					}
				}
			}
		} finally {
			if (session != null) {

				session.close();
			}

		}
		LOGGER.info("Exiting GroupingDAO.groupSearchParentCount() method.");

		return rowCount.intValue();
	}

	/**
	 * Method to get the departments for search group.
	 * 
	 * @return ArrayList
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/25/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 */
	@Override
	public ArrayList<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException {
		LOGGER.info("Entering getDeptDetailsByDepNoFromADSE() in GroupingDAOImpl class..");
		Session session = null;
		final ArrayList<DepartmentDetails> adseDepartmentList = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			final Query query1 = session.createSQLQuery(XqueryConstants.getLikeDepartmentDetailsForString());
			if (query1 != null) {
				query1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

				@SuppressWarnings("unchecked")
				final List<Object> rows1 = query1.list();
				if (rows1 != null && rows1.size() > 0) {
					for (final Object row : rows1) {
						@SuppressWarnings("rawtypes")
						final Map rowObj = (Map) row;
						final DepartmentDetails departmentDetails = new DepartmentDetails();
						departmentDetails.setId(rowObj.get("DEPTID") == null ? "" : rowObj.get("DEPTID").toString());
						departmentDetails.setDesc(rowObj.get("DEPTNAME") == null ? "" : rowObj.get("DEPTNAME").toString());
						adseDepartmentList.add(departmentDetails);
					}
				}
			}
		} finally {
			if (session != null) {

				session.close();
			}

		}
		LOGGER.info("Exiting getDeptDetailsByDepNoFromADSE() in GroupingDAOImpl class..");
		return adseDepartmentList;
	}

	/**
	 * Method to get the classes for search group.
	 * 
	 * @param departmentNumbers String
	 * @return List
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/25/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 */
	@Override
	public List<ClassDetails> getClassDetailsByDepNos(final String departmentNumbers) throws PEPPersistencyException {
		LOGGER.info("Entering getClassDetailsByDepNos() in GroupingDAOImpl class..");
		Session session = null;
		final List<ClassDetails> classDetailsList = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(XqueryConstants.getClassDetailsUsingDeptnumbers(departmentNumbers));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			for (final Object row : rows) {
				@SuppressWarnings("rawtypes")
				final Map rowObj = (Map) row;
				final ClassDetails classDetails = new ClassDetails();
				classDetails.setId(rowObj.get("CLASS_ID") == null ? "" : rowObj.get("CLASS_ID").toString());
				classDetails.setDesc(rowObj.get("CLASS_NAME") == null ? "" : rowObj.get("CLASS_NAME").toString());
				classDetailsList.add(classDetails);
			}
		} finally {
			if (session != null) {

				session.close();
			}

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
	public List<GroupAttributeForm> getExistSplitColorDetails(final String groupId) throws PEPFetchException {
		LOGGER.info("Fetch Split Color Existing Details. getExistSplitColorDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor groupId-->" + groupId);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;

		try {
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getExistSplitColorDetails());

			query.setParameter("groupidSql", groupId);
			LOGGER.info("Query-->getExistSplitColorDetails-->" + query);

			groupAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String isDefaultColor = rowMap.get("COMPONENT_DEFAULT") != null ? rowMap.get("COMPONENT_DEFAULT").toString() : "";

					groupAttributeForm.setOrinNumber(mdmid);
					groupAttributeForm.setStyleNumber(styleNo);
					groupAttributeForm.setProdName(productName);
					groupAttributeForm.setColorCode(colorCode);
					groupAttributeForm.setColorName(colorDesc);
					groupAttributeForm.setIsAlreadyInGroup("");
					groupAttributeForm.setSize("");
					groupAttributeForm.setPetStatus("");
					groupAttributeForm.setEntryType(entryType);
					groupAttributeForm.setIsDefault(isDefaultColor);
					groupAttributeForm.setParentMdmid(parentMdmid);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getExistSplitColorDetails finally block..");
			if (session != null) {

				session.close();
			}

		}

		LOGGER.info("Fetch Split Color Existing Details. getExistSplitColorDetails-->End.");
		return groupAttributeFormList;
	}

	/**
	 * This method is used to get the Existing Group Attribute Details for Split
	 * Sku from Database.
	 * 
	 * @param groupId
	 * @return groupAttributeFormList
	 */
	public List<GroupAttributeForm> getExistSplitSkuDetails(final String groupId) throws PEPFetchException {
		LOGGER.info("Fetch Split Color Existing Details. getExistSplitSkuDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor groupId-->" + groupId);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;

		try {
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getExistSplitSkuDetails());

			query.setParameter("groupidSql", groupId);
			LOGGER.info("Query-->getExistSplitSkuDetails-->" + query);

			groupAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String sizeDesc = rowMap.get("SIZEDESC") != null ? rowMap.get("SIZEDESC").toString() : "";
					final String sizeCode = rowMap.get("SIZE_CODE") != null ? rowMap.get("SIZE_CODE").toString() : "";
					final String isDefaultSize = rowMap.get("COMPONENT_DEFAULT") != null ? rowMap.get("COMPONENT_DEFAULT").toString() : "";

					groupAttributeForm.setOrinNumber(mdmid);
					groupAttributeForm.setStyleNumber(styleNo);
					groupAttributeForm.setProdName(productName);
					groupAttributeForm.setColorCode(colorCode);
					groupAttributeForm.setColorName(colorDesc);
					groupAttributeForm.setIsAlreadyInGroup("");
					groupAttributeForm.setSize(sizeDesc);
					groupAttributeForm.setSizeCode(sizeCode);
					groupAttributeForm.setPetStatus("");
					groupAttributeForm.setEntryType(entryType);
					groupAttributeForm.setIsDefault(isDefaultSize);
					groupAttributeForm.setParentMdmid(parentMdmid);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getExistSplitSkuDetails finally block..");
			if (session != null) {

				session.close();
			}

		}

		LOGGER.info("Fetch Split Color Existing Details. getExistSplitColorDetails-->End.");
		return groupAttributeFormList;
	}

	/**
	 * This method is used to get the Existing Group Attribute Details for
	 * Consolidated Product Grouping from Database.
	 * 
	 * @param groupId
	 * @return groupAttributeFormList
	 */
	public List<StyleAttributeForm> getExistCPGDetails(final String groupId) throws PEPFetchException {
		LOGGER.info("Fetch Split Color Existing Details. getExistCPGDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor groupId-->" + groupId);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		StyleAttributeForm styleAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		List<StyleAttributeForm> styleAttributeFormList = null;

		try {
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getExistCPGDetails());

			query.setParameter("groupidSql", groupId);
			LOGGER.info("Query-->getExistCPGDetails-->" + query);

			styleAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();
					styleAttributeForm = new StyleAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
					final String componentStyleId = rowMap.get("COMPONENT_STYLE_ID") != null ? rowMap.get("COMPONENT_STYLE_ID").toString()
							: "";
					final String isDefaultAttr = rowMap.get("COMPONENT_DEFAULT") != null ? rowMap.get("COMPONENT_DEFAULT").toString() : "";
					final String classId = rowMap.get("CLASS_ID") != null ? rowMap.get("CLASS_ID").toString() : "";

					if (("Style").equals(entryType)) {
						groupAttributeFormList = new ArrayList<>();
						styleAttributeForm.setOrinNumber(mdmid);
						styleAttributeForm.setStyleNumber(styleNo);
						styleAttributeForm.setProdName(productName);
						styleAttributeForm.setColorCode(colorCode);
						styleAttributeForm.setColorName(colorDesc);
						styleAttributeForm.setIsAlreadyInGroup("");
						styleAttributeForm.setSize("");
						styleAttributeForm.setPetStatus("");
						styleAttributeForm.setEntryType(entryType);
						styleAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setComponentStyleId(componentStyleId);
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormList);
						styleAttributeForm.setIsDefault(isDefaultAttr);
						styleAttributeForm.setClassId(classId);
						styleAttributeFormList.add(styleAttributeForm);
					} else {
						groupAttributeForm.setOrinNumber(mdmid);
						groupAttributeForm.setStyleNumber(styleNo);
						groupAttributeForm.setProdName(productName);
						groupAttributeForm.setColorCode(colorCode);
						groupAttributeForm.setColorName(colorDesc);
						groupAttributeForm.setIsAlreadyInGroup("");
						groupAttributeForm.setSize("");
						groupAttributeForm.setPetStatus("");
						groupAttributeForm.setEntryType(entryType);
						groupAttributeForm.setParentMdmid(parentMdmid);
						groupAttributeForm.setComponentStyleId(componentStyleId);
						groupAttributeForm.setIsDefault(isDefaultAttr);
						groupAttributeForm.setClassId(classId);

						for (int i = 0; i < styleAttributeFormList.size(); i++) {
							StyleAttributeForm styleAttributeFormSub = styleAttributeFormList.get(i);

							if ((styleAttributeFormSub.getOrinNumber()).equals(parentMdmid)) {

								groupAttributeForm.setProdName(styleAttributeFormSub.getProdName()); // Replacing
																										// Product
																										// Name
																										// from
																										// Style
								groupAttributeFormList = styleAttributeFormSub.getGroupAttributeFormList();
								groupAttributeFormList.add(groupAttributeForm);
								break;
							}
						}
					}
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getExistCPGDetails finally block..");
			if (session != null) {
				session.close();
			}

		}

		LOGGER.info("Fetch Split Color Existing Details. getExistCPGDetails-->End.");
		return styleAttributeFormList;
	}

	/**
	 * This method is used to get the Existing Group Attribute Details for GSS
	 * from Database.
	 * 
	 * @param groupId
	 * @return groupAttributeFormList
	 */
	public List<GroupAttributeForm> getExistGBSDetails(final String groupId) throws PEPFetchException {
		LOGGER.info("Fetch Split Color Existing Details. getExistGBSDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Vendor groupId-->" + groupId);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;

		try {
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getExistGBSDetails());

			query.setParameter("groupidSql", groupId);
			LOGGER.info("Query-->getExistGBSDetails-->" + query);

			groupAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					String productName = rowMap.get("NAME") != null ? rowMap.get("NAME").toString() : "";
					if (productName.indexOf(":") != -1) {
						productName = productName.substring(0, productName.indexOf(":"));
					}
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_NAME") != null ? rowMap.get("COLOR_NAME").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
					final String sizeDesc = rowMap.get("SIZEDESC") != null ? rowMap.get("SIZEDESC").toString() : "";
					final String sizeCode = rowMap.get("SIZE_CODE") != null ? rowMap.get("SIZE_CODE").toString() : "";
					final String isAlreadyInGroup = rowMap.get("ALREADY_IN_GROUP") != null ? rowMap.get("ALREADY_IN_GROUP").toString() : "";
					final String petState = rowMap.get("PET_STATE") != null ? rowMap.get("PET_STATE").toString() : "";
					String isDefault = rowMap.get("COMPONENT_DEFAULT") != null ? rowMap.get("COMPONENT_DEFAULT").toString() : "";

					isDefault = ("").equalsIgnoreCase(isDefault.trim()) ? "No" : ("false").equalsIgnoreCase(isDefault.trim()) ? "No"
							: ("true").equalsIgnoreCase(isDefault.trim()) ? "Yes" : "No";

					groupAttributeForm.setOrinNumber(mdmid);
					groupAttributeForm.setStyleNumber(styleNo);
					groupAttributeForm.setProdName(productName);
					groupAttributeForm.setColorCode(colorCode);
					groupAttributeForm.setColorName(colorDesc);
					groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
					groupAttributeForm.setSize(sizeDesc);
					groupAttributeForm.setSizeCode(sizeCode);
					groupAttributeForm.setPetStatus(petState);
					groupAttributeForm.setEntryType(entryType);
					groupAttributeForm.setParentMdmid(parentMdmid);
					groupAttributeForm.setIsDefault(isDefault);

					groupAttributeFormList.add(groupAttributeForm);
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getExistGBSDetails finally block..");
			if (session != null) {
				session.close();
			}
		}

		LOGGER.info("Fetch Split Color Existing Details. getExistGBSDetails-->End.");
		return groupAttributeFormList;
	}

	/**
	 * This method is used to get the search result Details for Regular/Beauty
	 * Collection Grouping.
	 * 
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
	 * @param pageNumber
	 * @param recordsPerPage
	 * @return List<StyleAttributeForm>
	 * @throws PEPFetchException
	 */
	@Override
	public List<StyleAttributeForm> getRegularBeautySearchResult(final String vendorStyleNo, final String styleOrin,
			final String deptNoSearch, final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch,
			final String groupId, final String groupIdSearch, final String groupNameSearch, final String sortedColumn,
			final String sortingOrder, final int pageNumber, final int recordsPerPage) throws PEPFetchException {
		LOGGER.info("Fetch New Regular/Beauty Collection attribute Details. getRegularBeautySearchResult-->Start.");
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		StyleAttributeForm styleAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		List<StyleAttributeForm> styleAttributeFormList = null;

		try {
			String deptNoForInSearch = GroupingUtil.getInValForQuery(deptNoSearch);
			String classNoSearchSearch = GroupingUtil.getInValForQuery(classNoSearch);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("For query deptNoForInSearch-->" + deptNoForInSearch);
				LOGGER.debug("For query classNoSearchSearch-->" + classNoSearchSearch);
				LOGGER.debug("styleOrin-->" + styleOrin);
			}
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(XqueryConstants.getRegularBeautySearchResult(vendorStyleNo, styleOrin,
					deptNoForInSearch, classNoSearchSearch, supplierSiteIdSearch, upcNoSearch, deptNoSearch, classNoSearch, groupIdSearch,
					groupNameSearch, sortedColumn, sortingOrder));

			query.setParameter("groupIdSql", groupId);
			if (null != vendorStyleNo && !("").equals(vendorStyleNo.trim())) {
				query.setParameter("styleIdSql", vendorStyleNo);
			}
			if (null != styleOrin && !("").equals(styleOrin.trim())) {
				query.setParameter("mdmidSql", styleOrin);
			}

			if (null != supplierSiteIdSearch && !("").equals(supplierSiteIdSearch.trim())) {
				query.setParameter("supplierIdSql", supplierSiteIdSearch);
			}
			if (null != upcNoSearch && !("").equals(upcNoSearch.trim())) {
				query.setParameter("upcNoSql", upcNoSearch);
			}

			LOGGER.info("Query-->getRegularBeautySearchResult-->" + query);

			styleAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			// PROOF
			/*
			 * query.setFirstResult(((1 - 1) * 10)); query.setMaxResults(10);
			 */

			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();
					styleAttributeForm = new StyleAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
					final String classId = rowMap.get("CLASS_ID") != null ? rowMap.get("CLASS_ID").toString() : "";
					final String isAlreadyInGroup = rowMap.get("ALREADY_IN_GROUP") != null ? rowMap.get("ALREADY_IN_GROUP").toString() : "";
					final String isAlreadyInSameGroup = rowMap.get("EXIST_IN_SAME_GROUP") != null ? rowMap.get("EXIST_IN_SAME_GROUP")
							.toString() : "";
					final String haveChildGroup = rowMap.get("GROUP_ID") != null ? rowMap.get("GROUP_ID").toString() : "";

					if (!("Style").equals(entryType) && !("StyleColor").equals(entryType)) {
						groupAttributeFormList = new ArrayList<>();
						styleAttributeForm.setOrinNumber(mdmid);
						styleAttributeForm.setStyleNumber(styleNo);
						styleAttributeForm.setProdName(productName);
						styleAttributeForm.setColorCode(colorCode);
						styleAttributeForm.setColorName(colorDesc);
						styleAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
						styleAttributeForm.setIsAlreadyInSameGroup(isAlreadyInSameGroup);
						styleAttributeForm.setSize("");
						styleAttributeForm.setPetStatus("");
						styleAttributeForm.setEntryType(entryType);
						styleAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormList);
						styleAttributeForm.setClassId(classId);
						styleAttributeForm.setIsGroup("Y");
						styleAttributeForm.setHaveChildGroup(haveChildGroup);
						styleAttributeFormList.add(styleAttributeForm);
					} else if (("Style").equals(entryType)) {
						groupAttributeFormList = new ArrayList<>();
						styleAttributeForm.setOrinNumber(mdmid);
						styleAttributeForm.setStyleNumber(styleNo);
						styleAttributeForm.setProdName(productName);
						styleAttributeForm.setColorCode(colorCode);
						styleAttributeForm.setColorName(colorDesc);
						styleAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
						styleAttributeForm.setIsAlreadyInSameGroup(isAlreadyInSameGroup);
						styleAttributeForm.setSize("");
						styleAttributeForm.setPetStatus("");
						styleAttributeForm.setEntryType(entryType);
						styleAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormList);
						styleAttributeForm.setClassId(classId);
						styleAttributeForm.setIsGroup("N");
						styleAttributeForm.setHaveChildGroup("N");
						styleAttributeFormList.add(styleAttributeForm);
					} else {
						groupAttributeForm.setOrinNumber(mdmid);
						groupAttributeForm.setStyleNumber(styleNo);
						groupAttributeForm.setProdName(productName);
						groupAttributeForm.setColorCode(colorCode);
						groupAttributeForm.setColorName(colorDesc);
						groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
						groupAttributeForm.setIsAlreadyInSameGroup(isAlreadyInSameGroup);
						groupAttributeForm.setSize("");
						groupAttributeForm.setPetStatus("");
						groupAttributeForm.setEntryType(entryType);
						groupAttributeForm.setParentMdmid(parentMdmid);
						groupAttributeForm.setClassId(classId);
						groupAttributeForm.setIsGroup("N");
						groupAttributeForm.setHaveChildGroup("N");
						for (int i = 0; i < styleAttributeFormList.size(); i++) {
							StyleAttributeForm styleAttributeFormSub = styleAttributeFormList.get(i);

							if ((styleAttributeFormSub.getOrinNumber()).equals(parentMdmid)) {
								groupAttributeForm.setProdName(styleAttributeFormSub.getProdName());
								groupAttributeFormList = styleAttributeFormSub.getGroupAttributeFormList();
								groupAttributeFormList.add(groupAttributeForm);
								break;
							}
						}

					}
				}
			}
		} catch (Exception ex) {
			LOGGER.error("inside PEPFetchException-->" + ex);
			throw new PEPFetchException(ex.getMessage());
		} finally {
			LOGGER.info("recordsFetched. getRegularBeautySearchResult finally block..");
			if (session != null) {

				session.close();
			}

		}

		LOGGER.info("getRegularBeautySearchResult-->End.");
		return styleAttributeFormList;
	}

	/**
	 * This method is used to get the search result Details count for
	 * Regular/Beauty Collection Grouping.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoForInSearch
	 * @param classNoForInSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @param groupIdSearch
	 * @param groupNameSearch
	 * @return List<StyleAttributeForm>
	 * @throws PEPServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getRegularBeautySearchResultCount(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch,
			final String classNoForInSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId,
			final String groupIdSearch, final String groupNameSearch) {

		LOGGER.info("Entering GroupingDAO.getRegularBeautySearchResultCount() method.");
		Session session = null;
		BigDecimal rowCount = new BigDecimal(0);
		List<Object> rows = null;

		try {
			String deptNoSearch = GroupingUtil.getInValForQuery(deptNoForInSearch);
			String classNoSearch = GroupingUtil.getInValForQuery(classNoForInSearch);
			session = sessionFactory.openSession();
			final Query query = session.createSQLQuery(XqueryConstants.getRegularBeautySearchResultCountQuery(vendorStyleNo, styleOrin,
					deptNoForInSearch, classNoForInSearch, supplierSiteIdSearch, upcNoSearch, deptNoSearch, classNoSearch, groupIdSearch,
					groupNameSearch));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			rows = query.list();
			if (rows != null) {
				for (final Object rowObj : rows) {
					@SuppressWarnings("rawtypes")
					final Map row = (Map) rowObj;
					rowCount = (BigDecimal) (row.get("TOTAL_COUNT") == null ? "" : row.get("TOTAL_COUNT"));
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Parent Grouping Attribute Count -- \nCOUNT OF RECORDS: " + rowCount);
					}
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}

		}
		LOGGER.info("Exiting GroupingDAO.getRegularBeautySearchResultCount() method.");

		return rowCount.intValue();
	}

	/**
	 * This method is used to get the Existing Regular/Beauty Collection
	 * Grouping details from Database.
	 * 
	 * @param groupId
	 * @return groupAttributeFormList
	 */
	@Override
	public List<StyleAttributeForm> getExistRegularBeautyDetails(final String groupId) throws PEPFetchException {
		LOGGER.info("getExistRegularBeautyDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("groupId-->" + groupId);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		StyleAttributeForm styleAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		List<StyleAttributeForm> styleAttributeFormList = null;

		try {
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getExistRegularBeautyDetailsQuery());

			query.setParameter("groupId", groupId);
			query.setParameter("groupId", groupId);
			LOGGER.info("Query-->getExistRegularBeautyDetails-->" + query);

			styleAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();
					styleAttributeForm = new StyleAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
					final String productName = rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";
					final String isDefaultAttr = rowMap.get("COMPONENT_DEFAULT") != null ? rowMap.get("COMPONENT_DEFAULT").toString() : "";
					final String classId = rowMap.get("CLASS_ID") != null ? rowMap.get("CLASS_ID").toString() : "";
					final String priority = rowMap.get("DISPLAY_SEQUENCE") != null ? rowMap.get("DISPLAY_SEQUENCE").toString() : "";

					if (!("Style").equals(entryType) && !("StyleColor").equals(entryType)) {
						groupAttributeFormList = new ArrayList<>();
						styleAttributeForm.setOrinNumber(mdmid);
						styleAttributeForm.setStyleNumber(styleNo);
						styleAttributeForm.setProdName(productName);
						styleAttributeForm.setColorCode(colorCode);
						styleAttributeForm.setColorName(colorDesc);
						styleAttributeForm.setIsAlreadyInGroup("");
						styleAttributeForm.setSize("");
						styleAttributeForm.setPetStatus("");
						styleAttributeForm.setEntryType(entryType);
						styleAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormList);
						styleAttributeForm.setIsDefault(isDefaultAttr);
						styleAttributeForm.setClassId(classId);
						styleAttributeForm.setIsGroup("Y");
						styleAttributeForm.setPriority(priority);
						styleAttributeFormList.add(styleAttributeForm);
					} else if (("Style").equals(entryType)) {
						groupAttributeFormList = new ArrayList<>();
						styleAttributeForm.setOrinNumber(mdmid);
						styleAttributeForm.setStyleNumber(styleNo);
						styleAttributeForm.setProdName(productName);
						styleAttributeForm.setColorCode(colorCode);
						styleAttributeForm.setColorName(colorDesc);
						styleAttributeForm.setIsAlreadyInGroup("");
						styleAttributeForm.setSize("");
						styleAttributeForm.setPetStatus("");
						styleAttributeForm.setEntryType(entryType);
						styleAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormList);
						styleAttributeForm.setIsDefault(isDefaultAttr);
						styleAttributeForm.setClassId(classId);
						styleAttributeForm.setIsGroup("N");
						styleAttributeForm.setPriority(priority);
						styleAttributeFormList.add(styleAttributeForm);
					} else if (("StyleColor").equals(entryType)) {
						groupAttributeForm.setOrinNumber(mdmid);
						groupAttributeForm.setStyleNumber(styleNo);
						groupAttributeForm.setProdName(productName);
						groupAttributeForm.setColorCode(colorCode);
						groupAttributeForm.setColorName(colorDesc);
						groupAttributeForm.setIsAlreadyInGroup("");
						groupAttributeForm.setSize("");
						groupAttributeForm.setPetStatus("");
						groupAttributeForm.setEntryType(entryType);
						groupAttributeForm.setParentMdmid(parentMdmid);
						groupAttributeForm.setIsDefault(isDefaultAttr);
						groupAttributeForm.setClassId(classId);
						styleAttributeForm.setIsGroup("N");
						styleAttributeForm.setPriority(priority);

						for (int i = 0; i < styleAttributeFormList.size(); i++) {
							StyleAttributeForm styleAttributeFormSub = styleAttributeFormList.get(i);

							if ((styleAttributeFormSub.getOrinNumber()).equals(parentMdmid)) {

								groupAttributeForm.setProdName(styleAttributeFormSub.getProdName());
								groupAttributeFormList = styleAttributeFormSub.getGroupAttributeFormList();
								groupAttributeFormList.add(groupAttributeForm);
								break;
							}
						}
					}
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getExistRegularBeautyDetails finally block..");
			if (session != null) {
				session.close();
			}

		}

		LOGGER.info("getExistRegularBeautyDetails-->End.");
		return styleAttributeFormList;
	}

	/**
	 * This method is used to get the Child Regular/Beauty Collection Grouping
	 * details from Database.
	 * 
	 * @param groupId
	 * @return List<StyleAttributeForm>
	 */
	@Override
	public List<StyleAttributeForm> getRegularBeautyChildDetails(final String groupId) throws PEPFetchException {
		LOGGER.info("getRegularBeautyChildDetails-->Start.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("groupId-->" + groupId);
		}
		Session session = null;
		GroupAttributeForm groupAttributeForm = null;
		StyleAttributeForm styleAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = null;
		List<StyleAttributeForm> styleAttributeFormList = null;

		try {
			session = sessionFactory.openSession();
			// Hibernate provides a createSQLQuery method to let you call your
			// native SQL statement directly.
			final Query query = session.createSQLQuery(XqueryConstants.getRegularBeautyDetailsChildQuery());

			query.setParameter("groupId", groupId);
			query.setParameter("groupId", groupId);
			LOGGER.info("Query-->getRegularBeautyChildDetails-->" + query);

			styleAttributeFormList = new ArrayList<>();

			// execute select SQL statement
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			final List<Object> rows = query.list();
			if (rows != null) {
				LOGGER.info("recordsFetched..." + rows.size());

				for (final Object row : rows) {

					@SuppressWarnings("rawtypes")
					final Map rowMap = (Map) row;
					groupAttributeForm = new GroupAttributeForm();
					styleAttributeForm = new StyleAttributeForm();

					final String mdmid = rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
					final String styleNo = rowMap.get("VENDOR_STYLE") != null ? rowMap.get("VENDOR_STYLE").toString() : "";
					final String productName = rowMap.get("PRODUCTNAME") != null ? rowMap.get("PRODUCTNAME").toString() : "";
					final String colorCode = rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
					final String colorDesc = rowMap.get("COLO_DESC") != null ? rowMap.get("COLO_DESC").toString() : "";
					final String entryType = rowMap.get("ENTRY_TYPE") != null ? rowMap.get("ENTRY_TYPE").toString() : "";
					final String parentMdmid = rowMap.get("PARENT_MDMID") != null ? rowMap.get("PARENT_MDMID").toString() : "";

					if (!("Style").equals(entryType) && !("StyleColor").equals(entryType)) {
						groupAttributeFormList = new ArrayList<>();
						styleAttributeForm.setOrinNumber(mdmid);
						styleAttributeForm.setStyleNumber(styleNo);
						styleAttributeForm.setProdName(productName);
						styleAttributeForm.setColorCode(colorCode);
						styleAttributeForm.setColorName(colorDesc);
						styleAttributeForm.setIsAlreadyInGroup("");
						styleAttributeForm.setSize("");
						styleAttributeForm.setPetStatus("");
						styleAttributeForm.setEntryType(entryType);
						styleAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormList);
						styleAttributeForm.setIsGroup("Y");
						styleAttributeFormList.add(styleAttributeForm);
					} else if (("Style").equals(entryType)) {
						groupAttributeFormList = new ArrayList<>();
						styleAttributeForm.setOrinNumber(mdmid);
						styleAttributeForm.setStyleNumber(styleNo);
						styleAttributeForm.setProdName(productName);
						styleAttributeForm.setColorCode(colorCode);
						styleAttributeForm.setColorName(colorDesc);
						styleAttributeForm.setIsAlreadyInGroup("");
						styleAttributeForm.setSize("");
						styleAttributeForm.setPetStatus("");
						styleAttributeForm.setEntryType(entryType);
						styleAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormList);
						styleAttributeForm.setIsGroup("N");
						styleAttributeFormList.add(styleAttributeForm);
					} else if (("StyleColor").equals(entryType)) {
						groupAttributeForm.setOrinNumber(mdmid);
						groupAttributeForm.setStyleNumber(styleNo);
						groupAttributeForm.setProdName(productName);
						groupAttributeForm.setColorCode(colorCode);
						groupAttributeForm.setColorName(colorDesc);
						groupAttributeForm.setIsAlreadyInGroup("");
						groupAttributeForm.setSize("");
						groupAttributeForm.setPetStatus("");
						groupAttributeForm.setEntryType(entryType);
						groupAttributeForm.setParentMdmid(parentMdmid);
						styleAttributeForm.setIsGroup("N");

						for (int i = 0; i < styleAttributeFormList.size(); i++) {
							StyleAttributeForm styleAttributeFormSub = styleAttributeFormList.get(i);

							if ((styleAttributeFormSub.getOrinNumber()).equals(parentMdmid)) {

								groupAttributeForm.setProdName(styleAttributeFormSub.getProdName());
								groupAttributeFormList = styleAttributeFormSub.getGroupAttributeFormList();
								groupAttributeFormList.add(groupAttributeForm);
								break;
							}
						}
					}
				}
			}
		} finally {
			LOGGER.info("recordsFetched. getRegularBeautyChildDetails finally block..");
			if (session != null) {
				session.close();
			}

		}

		LOGGER.info("getRegularBeautyChildDetails-->End.");
		return styleAttributeFormList;
	}

	/**
	 * This method is used check the LOCK status of a pet.
	 * 
	 * @param Orin
	 * @param pepUserId
	 * @param searchPetLockedtype
	 * @return
	 * @throws PEPPersistencyException
	 */
	public ArrayList<PetLock> isPETLocked(final String Orin, final String pepUserId, final String searchPetLockedtype)
			throws PEPPersistencyException {

		LOGGER.info("pepUserId in the isPETLocked layer-->" + pepUserId);

		LOGGER.info("pepUserId in the Orin layer-->" + Orin);
		LOGGER.info("pepUserId in the searchPetLockedtype layer-->" + searchPetLockedtype);
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ArrayList<PetLock> petLockDetails = new ArrayList<>();
		try {
			Query query = session.getNamedQuery("PetLock.isPetLocked");
			query.setString("petId", Orin);
			// query.setString("pepFunction", searchPetLockedtype);
			@SuppressWarnings("unchecked")
			List<PetLock> petLock = query.list();
			if (null != petLock && petLock.size() > 0) {
				LOGGER.info("isPETLocked  in DAO IMPL  " + petLock.size());
				for (int i = 0; i < petLock.size(); i++) {
					petLockDetails.add(petLock.get(i));
				}
			}

		} finally {
			if(null != tx){
				tx.commit();
			}
			if(null != session){
				session.flush();
				session.close();
			}
		}
		return petLockDetails;
	}

	/**
	 * This method is used to lock a PET while using.
	 * 
	 * @param orin
	 * @param pepUserID
	 * @param pepfunction
	 * @return
	 * @throws PEPPersistencyException
	 */
	public boolean lockPET(final String orin, final String pepUserID, final String pepfunction) throws PEPPersistencyException {
		LOGGER.info("This is  in DAO IMPL lockRecord.." + pepUserID);
		boolean isUpdated = false;
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		PetLock ptLock = new PetLock();
		PetLockPK petLock = new PetLockPK();
		try {
			petLock.setPetId(orin);
			petLock.setPepUser(pepUserID);
			petLock.setLockDate(new Date());
			ptLock.setPepFunction(pepfunction);
			ptLock.setLockStatus("Yes");
			ptLock.setId(petLock);
			LOGGER.info("petLock -->" + petLock);
			session.saveOrUpdate(ptLock);
		} finally {
			if(null != tx){
				tx.commit();
			}
			if(null != session){
				session.flush();
				session.close();
			}
		}
		LOGGER.info("This is from lockPET...Exit");
		return isUpdated;
	}

	/**
	 * This Method is used to release Group Locking.
	 * 
	 * @param orin
	 * @param pepUserID
	 * @param pepFunction
	 * @return
	 * @throws PEPPersistencyException
	 */
	public boolean releseLockedPet(final String orin, final String pepUserID, final String pepFunction) throws PEPPersistencyException {
		LOGGER.info("releseLockedPet Grouping DAO Impl:: lockPET-->Enter");
		boolean isPetReleased = false;

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.getNamedQuery("PetLock.deleteLockedPet");
			query.setString("petId", orin);
			// query.setString("pepUser", pepUserID);
			// query.setString("pepFunction", pepFunction);
			query.executeUpdate();

		} finally {
			if(null != tx){
				tx.commit();
			}
			if(null != session){
				session.flush();
				session.close();
			}
		}
		return isPetReleased;
	}

}
