package com.belk.pep.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import javax.naming.NamingException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.belk.pep.constants.CreatePETPortalConstants;
import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.CreatePETDao;
import com.belk.pep.exception.checked.OrinNotFoundException;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.model.Sku;
import com.belk.pep.model.Style;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.util.PropertyLoader;

/**
 * 
 * The Class CreatePETDaoImpl.
 */
public class CreatePETDaoImpl implements CreatePETDao {

	/** The session factory. */
	private SessionFactory sessionFactory;
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(CreatePETDaoImpl.class.getName());

	/* Query Execution */
	public ArrayList<WorkFlow> fetchPETDetails(String orinNo)
	throws NamingException, SQLException, PEPFetchException,OrinNotFoundException {

		LOGGER.info("CreatePETDaoImpl:: fetchUserDetails Updated ** ");

		Query query = null;
		Session session = null;
		Transaction tx = null;
		XqueryConstants queryCons = new XqueryConstants();	
		ArrayList<WorkFlow> workFlowList = new ArrayList<WorkFlow>();
		try {		
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			query = session.createSQLQuery(queryCons.getPetDetails(orinNo));
			//query.setParameter("orinNo", orinNo); 		    query.setFetchSize(100);
			ArrayList<Style> styleList = new ArrayList<Style>();
			ArrayList<Sku> skuListArr = new ArrayList<Sku>();
			ArrayList<Style> styleListFinal = new ArrayList<Style>();
			ArrayList<Object[]> list = (ArrayList<Object[]>) query.list();

			String parentOrin=null;
			WorkFlow workFlow = null;
			for (Object[] row : list) {					
				parentOrin = row[0].toString();
				boolean flag = false;
				// Pets  for sku orin search
				if((CreatePETPortalConstants.SKU_ENTRY).equalsIgnoreCase(row[10].toString())&& orinNo.equalsIgnoreCase(row[1].toString())){
					LOGGER.info("CreatePETDaoImpl::SKU .... 1 ");
					Sku skuEntry = new Sku();
					skuEntry.setOrin(row[1].toString());
					skuEntry.setOrinDesc(row[2].toString());
					skuEntry.setSupplierSiteId(row[3].toString());
					skuEntry.setSupplierSiteName(row[5].toString());
					skuEntry.setColorCode(row[6].toString());
					skuEntry.setColorDes(row[7].toString());				
					if (null != row[8]) {
						skuEntry.setSizeCode(row[8].toString());
					} 				
					if (null != row[9]) {
						skuEntry.setSizeDesc(row[9].toString()); 
					}
					LOGGER.info("workFlowList ....***********--");
					workFlowList = new ArrayList<WorkFlow>();
					WorkFlow skuWorkFlow =  new WorkFlow();					
					skuWorkFlow.setSkuObj(skuEntry);
					skuWorkFlow.setEntryType(row[10].toString());
					workFlowList.add(skuWorkFlow);
					
				}

				else if ((CreatePETPortalConstants.SKU_ENTRY).equalsIgnoreCase(row[10].toString())) {
					
					LOGGER.info("CreatePETDaoImpl::SKU .... 2-- if block  "+	styleList.size());				
					int count = 0;					
					if ((styleList.size() == 0)) {				
						Style style = new Style();
						style.setOrin(parentOrin);
						style.setParentOrin(String.valueOf(row[0]));
						style.setOrinDesc(row[2].toString());
						style.setSupplierSiteId(row[3].toString());
						LOGGER.info("CreatePETDaoImpl:: styleList.size() == 0 ****");
						style.setSupplierSiteName(row[5].toString());
						style.setColorCode(row[6].toString());
						style.setColorDes(row[7].toString());
						style.setEntryType(row[10].toString());
						styleList.add(style);
					} else {
						LOGGER.info("CreatePETDaoImpl::SKU .... 2-- else block ");
						
						boolean flagSign = false;						
						if (null != styleList) {
							Iterator styleIterator = styleList.iterator();							
							while (styleIterator.hasNext()) {							
								Style styleIt = (Style) styleIterator.next();								
								if (styleIt.getColorCode().equalsIgnoreCase(
										row[6].toString())) {
									flag = true;
									flagSign = false;
									count++;
									// add skus here								
									LOGGER.info("Flag in while"
											+ styleIt.getColorCode() + "row6"
											+ row[6].toString());
									continue;
								} else {
									LOGGER.info("Flag set to false");
									flag = false;
									flagSign = true;
									// add style here.
								}
								LOGGER.info("Flag set Break");
							}
						}
						if (flagSign && (flag == false)) {
							Style style = new Style();
							LOGGER.info("CreatePETDaoImpl:flagSign && (flag == false ... ");
							style.setOrin(parentOrin);
							style.setParentOrin(String.valueOf(row[0]));
							style.setOrinDesc(row[2].toString());
							style.setSupplierSiteId(row[3].toString());
							LOGGER.info("Style Colorr in flagSign * "+ row[5].toString());
							style.setSupplierSiteName(row[5].toString());
							style.setColorCode(row[6].toString());
							style.setColorDes(row[7].toString());
							style.setEntryType(row[10].toString());
							styleList.add(style);
						}
						if (flag && (count == 1) && (styleList.size() != 1)) {
							// Add sku here .	
						}
					}
				}
				if ((CreatePETPortalConstants.STYLE_ENTRY)
						.equalsIgnoreCase(row[10].toString())) {
					System.out.println("STYLE ENTTY ***********" );
					workFlow = new WorkFlow();
					workFlow.setOrin(String.valueOf(row[0]));
					workFlow.setOrinDesc(row[2].toString());
					workFlow.setSupplierSiteName(row[3].toString() + " "+ row[5].toString());						
					workFlow.setEntryType(row[10].toString());
					workFlowList.add(workFlow);
					}
				
				 if ((CreatePETPortalConstants.COMPLEX_PACK)
						.equalsIgnoreCase(row[10].toString())) {
					 System.out.println("COMPLEX_PACK *******" );
					workFlow = new WorkFlow();
					workFlow.setOrin(String.valueOf(row[0]));
					workFlow.setOrinDesc(row[2].toString());
					workFlow.setSupplierSiteName(row[3].toString() + " "+ row[5].toString());	
					
					workFlow.setColorDes((row[7] !=null) ? row[7] .toString() : "");
					
					workFlow.setEntryType(row[10].toString());
					
					workFlowList.add(workFlow);
					}
			}
			
			Iterator styleIterator = styleList.iterator();
			while (styleIterator.hasNext()) {
				Style style = (Style) styleIterator.next();				
				for (Object[] row : list) {			

					if ((CreatePETPortalConstants.SKU_ENTRY)
							.equalsIgnoreCase(row[10].toString())) {
						if (row[6].toString().equalsIgnoreCase(
								style.getColorCode())) {
							Sku skuObj = new Sku();	
							
							LOGGER.info("CreatePETDaoImpl::SKU .... 5");
							skuObj.setOrin(row[1].toString());
							skuObj.setOrinDesc(row[2].toString());
							skuObj.setSupplierSiteId(row[3].toString());
							skuObj.setSupplierSiteName(row[5].toString());
							skuObj.setColorCode(row[6].toString());
							skuObj.setColorDes(row[7].toString());
							LOGGER.info("CreatePETDaoImpl::SKU .... 7");
							if (null != row[8]) {
								skuObj.setSizeCode(row[8].toString());
							} 
							LOGGER.info("CreatePETDaoImpl::SKU .... 8");
							if (null != row[9]) {
								skuObj.setSizeDesc(row[9].toString()); }
							// To uncomment once data is populated
							skuObj.setEntryType(row[10].toString());
							
							skuListArr.add(skuObj);
							style.setSkuList(skuListArr);
						}
					}
				}
				skuListArr = new ArrayList<Sku>();
				styleListFinal.add(style);
			}
			LOGGER.info("Size in DAO IMPL" + workFlowList.size());		
			
			Iterator workFlowEach = workFlowList.iterator();
			while (workFlowEach.hasNext()) {
				WorkFlow workflow = (WorkFlow) workFlowEach.next();
				Iterator styleEach = styleListFinal.iterator();
				while (styleEach.hasNext()) {
					Style styles = (Style) styleEach.next();
					LOGGER.info("workflow Value DAOIMPL  " + styles.getSkuList());
					Iterator skue = styles.getSkuList().iterator();
					// Iterator skuListEach = skuListArr.iterator();

					while (skue.hasNext()) {
						Sku sku = (Sku) skue.next();
						LOGGER.info("sku.getColorCode()" + sku.getColorCode());
					}

					if (workflow.getOrin().equalsIgnoreCase(
							styles.getParentOrin())) {
						workFlow.setStyleList(styleListFinal);
					}
				}
				ArrayList<WorkFlow> workFlowL = new ArrayList<WorkFlow>();
				workFlowL.add(workFlow);
			}
			
			
			//Added this to fix defect# 85
			if(workFlowList.size() == 0) {				
				throw new OrinNotFoundException();				
			}
			
			Iterator workIt = workFlowList.iterator();
						
			WorkFlow	 wf = (WorkFlow) workIt.next();			
			Iterator styleEach = wf.getStyleList().iterator();
			while (styleEach.hasNext()) {
				Style styles = (Style) styleEach.next();
						
				Iterator skuEach = styles.getSkuList().iterator();
				while (skuEach.hasNext()) {
					Sku ss = (Sku) skuEach.next();				
					LOGGER.info("CreatepetDaoImpl::StyleColorCode ****"+ ss.getColorCode() + "orin" + ss.getOrin());
				}
			}
		}    catch (OrinNotFoundException e) {			
			throw e;
		}   catch (Exception e) {
			
			e.printStackTrace();
			LOGGER.info("fetchPETDetails  exception block " + e.getMessage());
		}
		finally{
			session.flush();
			tx.commit();
			session.close();        	
		}
		LOGGER.info("Createpet DaoImpl:::END Method in dao "+workFlowList.size());
		return workFlowList;
		/* Retrieve Pet Details */
	}

	/**
	 * Gets the session factory.
	 * 
	 * @return the sessionFactory
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

	/**
	 * Sets the session factory.
	 * 
	 * @param sessionFactory
	 *            the new session factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Validates the orin number with DB.
	 * 
	 * @return the success or failure message
	 */
	public String validateOrin(String orinNo) throws PEPFetchException {		

		Query query = null;
		String validateOrinMsg = null;
		Session session = null;
		Transaction tx = null;

		XqueryConstants queryCons = new XqueryConstants();

		LOGGER.info("CreatePETDaoImpl::::validateOrin::orinNo updted now" + orinNo);

		try {		
			session = sessionFactory.openSession();
			tx = session.beginTransaction();		
			query = session.createSQLQuery(queryCons.validateOrin());
			query.setParameter("orinNo", orinNo); 
		    query.setFetchSize(10);
			LOGGER.info("CreatepetDaoImpl:: validateOrin updated  " + query);
			@SuppressWarnings("unchecked")
			ArrayList<String> list = (ArrayList<String>) query.list();			
			
			for (String validOrin : list) {			
				LOGGER.info("CreatepetDaoImpl:: validateOrin validOrin inslide loop " + validOrin);
				validateOrinMsg = validOrin;
			}
			list = null;
		} catch (Exception e) {
			e.printStackTrace();

		}

		finally{
			LOGGER.info(" CreatepetDaoImpl::validateOrinMsg::from DB "
					+ validateOrinMsg);
			session.flush();
			tx.commit();
			session.close();        	
		}      

		return validateOrinMsg;
	}
}
