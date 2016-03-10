package com.belk.pep.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import com.belk.pep.dao.impl.WorkListDisplayDAOImpl;
import com.belk.pep.model.StyleColor;
import com.belk.pep.model.WorkFlow;



public class HashmapWorkFlow {
	private static TreeMap workFlowDB = new TreeMap();
	/** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(HashmapWorkFlow.class
        .getName());

	
	/* (non-Javadoc)
	 * @see com.sample.IContact#addContact(com.sample.ContactForm)
	 */
	public int saveWorkFlow(WorkFlow workFlow) {
		LOGGER.info("Inside saveContact " + workFlow);
		workFlowDB.put(workFlow.getDeptId(),workFlow);	
		LOGGER.info("Inside saveContact " + workFlowDB);
		return 1;
	}
	
	/* (non-Javadoc)
	 * @see com.sample.IContact#deleteContact(int)
	 */
	public int deleteWorkFlow(String id) {
		LOGGER.info("Inside deleteContact" + id);
		workFlowDB.remove(id);
		LOGGER.info("Inside deleteContact " + workFlowDB);

		return 0;
	}
	/* (non-Javadoc)
	 * @see com.sample.IContact#getContact(int)
	 */
	public WorkFlow getWorkFlow(String id) {
		return (WorkFlow)workFlowDB.get(id);
	}
	/* (non-Javadoc)
	 * @see com.sample.IContact#getContacts()
	 */
	public ArrayList getWorkFlows() {
		
		ArrayList workFlowList = new ArrayList();
		ArrayList stylelist = new ArrayList();
		
	
	
		//Style a = new Style();
	
	
	
WorkFlow workflow1 =  new WorkFlow();
workflow1.setOrinNumber("plus1");
workflow1.setDeptId("A_J@belk.com");
workflow1.setProductName("AJ");
workflow1.setEntryType("AELK");
workflow1.setVendorStyle("ACollection");
workflow1.setVendorName("AnItiated");
workflow1.setVendorStyle("Aroposed");
workflow1.setImageStatus("ImageStatus");
workflow1.setContentStatus("ContentStatus");
workflow1.setCompletionDate("2015-09-30");	


WorkFlow workflow2 =  new WorkFlow();
workflow2.setOrinNumber("plus1");
workflow2.setDeptId("A_J@belk.com");
workflow2.setProductName("AJ");
workflow2.setEntryType("AELK");
workflow2.setVendorStyle("ACollection");
workflow2.setVendorName("AnItiated");
workflow2.setVendorStyle("Aroposed");
workflow2.setImageStatus("ImageStatus");
workflow2.setContentStatus("ContentStatus");
workflow2.setCompletionDate("2015-09-30");

WorkFlow workflow3 =  new WorkFlow();
workflow3.setOrinNumber("plus1");
workflow3.setDeptId("A_J@belk.com");
workflow3.setProductName("AJ");
workflow3.setEntryType("AELK");
workflow3.setVendorStyle("ACollection");
workflow3.setVendorName("AnItiated");
workflow3.setVendorStyle("Aroposed");
workflow3.setImageStatus("ImageStatus");
workflow3.setContentStatus("ContentStatus");
workflow3.setCompletionDate("2015-09-30");

WorkFlow workflow4 =  new WorkFlow();
workflow4.setOrinNumber("plus1");
workflow4.setDeptId("A_J@belk.com");
workflow4.setProductName("AJ");
workflow4.setEntryType("AELK");
workflow4.setVendorStyle("ACollection");
workflow4.setVendorName("AnItiated");
workflow4.setVendorStyle("Aroposed");
workflow4.setImageStatus("ImageStatus");
workflow4.setContentStatus("ContentStatus");
workflow4.setCompletionDate("2015-09-30");

WorkFlow workflow5 =  new WorkFlow();
workflow5.setOrinNumber("plus1");
workflow5.setDeptId("A_J@belk.com");
workflow5.setProductName("AJ");
workflow5.setEntryType("AELK");
workflow5.setVendorStyle("ACollection");
workflow5.setVendorName("AnItiated");
workflow5.setVendorStyle("Aroposed");
workflow5.setImageStatus("ImageStatus");
workflow5.setContentStatus("ContentStatus");
workflow5.setCompletionDate("2015-09-30");
		
		
		/*Iterator workFlowIt = workFlowDB.values().iterator();
		while(workFlowIt.hasNext()){			
			WorkFlow workflow =(WorkFlow)workFlowIt.next();	
			
			Style sss=workflow.getStyleObj();
			workflow.setStyleList(stylelist);
			workFlowList.add(workflow);
			LOGGER.info("WORKFLOW 3");
		}*/
		/*for(int i=0;i<1000;i++){
		    if(i<246){
		    workFlowList.add(workflow1);
		    }else
		    if(i<496){
	            workFlowList.add(workflow2);
	            }else
		    if(i<746){
	            workFlowList.add(workflow3);
	            }else
		    if(i<996){
	            workFlowList.add(workflow4);
	            }else{
	                workFlowList.add(workflow5);  
	            }
		    
		}*/
/*for(int i=0;i<510;i++){
    if(i<100){
    workFlowList.add(workflow1);
    }else
    if(i<200){
        workFlowList.add(workflow2);
        }else
    if(i<300){
        workFlowList.add(workflow3);
        }else
    if(i<400){
        workFlowList.add(workflow4);
        }else{
            workFlowList.add(workflow5);  
        }
    
}*/
		//Iterator workFlowInd = workFlowList.iterator();
		/*while(workFlowInd.hasNext()){			
			WorkFlow workflowInd =(WorkFlow)workFlowInd.next();
			LOGGER.info("workFlow in hashFlow");
			LOGGER.info("workFlow Individual"+workflowInd);
			//LOGGER.info("workFlow Individual style"+workflowInd.getStyleList().get(0));
			
		}*/

List<StyleColor> styleColorList = new ArrayList<StyleColor>();

StyleColor styleColor = new StyleColor();

styleColor.setOrinNumber("plus1");
styleColor.setDeptId("A_J@belk.com");
styleColor.setProductName("AJ");
styleColor.setEntryType("AELK");
styleColor.setVendorStyle("ACollection");
styleColor.setVendorName("AnItiated");
styleColor.setVendorStyle("Aroposed");
styleColor.setImageStatus("ImageStatus");
styleColor.setContentStatus("ContentStatus");
styleColor.setCompletionDate("2015-09-30");

StyleColor styleColor1 = new StyleColor();

styleColor1.setOrinNumber("plus1");
styleColor1.setDeptId("A_J@belk.com");
styleColor1.setProductName("AJ");
styleColor1.setEntryType("AELK");
styleColor1.setVendorStyle("ACollection");
styleColor1.setVendorName("AnItiated");
styleColor1.setVendorStyle("Aroposed");
styleColor1.setImageStatus("ImageStatus");
styleColor1.setContentStatus("ContentStatus");
styleColor1.setCompletionDate("2015-09-30");


styleColorList.add(styleColor);
styleColorList.add(styleColor1);
workflow1.setStyleColor(styleColorList);
workFlowList.add(workflow1);
workflow2.setStyleColor(styleColorList);
workFlowList.add(workflow2);
/*workFlowList.add(workflow3);
workFlowList.add(workflow4);
workFlowList.add(workflow5);*/



for(int i=0;i<510;i++){
if(i<100){
workFlowList.add(workflow3);
}else
if(i<200){
    workFlowList.add(workflow3);
    }else
if(i<300){
    workFlowList.add(workflow4);
    }else
if(i<400){
    workFlowList.add(workflow4);
    }else{
        workFlowList.add(workflow5);  
    }

}
		
		return workFlowList;
	}

}
