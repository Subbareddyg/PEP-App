
package com.belk.pep.model;

import java.util.Date;
import java.util.List;


/**
 * The Class PetsFound.
 *
 * @author AFUAXK4
 */
public class PetsFoundHierarchy implements Comparable<PetsFoundHierarchy>{

    /**
     * Instantiates a new pets found.
     */
    public PetsFoundHierarchy() {
       
    }

    /** The parent style orin. */
    private String parentStyleOrin;
    
    
    /** The dept id. */
    private Integer deptId;        
      
    
    /** The completion date. */
    private Date completionDate;
        
    public Date getCompletionDate() {
        return completionDate;
    }


    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /** The parent. */
    private PetsFound parent;
    
    public PetsFound getParent() {
        return parent;
    }


    public void setParent(PetsFound parent) {
        this.parent = parent;
    }

    /** The child list. */
    private List<PetsFound> childList;
    
    public List<PetsFound> getChildList() {
        return childList;
    }


    public void setChildList(List<PetsFound> childList) {
        this.childList = childList;
    }
   

    public String getReq_Type() {
        return req_Type;
    }


    public void setReq_Type(String req_Type) {
        this.req_Type = req_Type;
    }
    
    private String req_Type;


    /**
     * Gets the parent style orin.
     *
     * @return the parentStyleOrin
     */
    public String getParentStyleOrin() {
        return parentStyleOrin;
    }


    /**
     * Sets the parent style orin.
     *
     * @param parentStyleOrin the parentStyleOrin to set
     */
    public void setParentStyleOrin(String parentStyleOrin) {
        this.parentStyleOrin = parentStyleOrin;
    }    


    /**
     * Gets the dept id.
     *
     * @return the deptId
     */
    public Integer getDeptId() {
        return deptId;
    }


    /**
     * Sets the dept id.
     *
     * @param deptId the deptId to set
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public int compareTo(PetsFoundHierarchy arg1) { 
        //System.out.println("HERE--------ORIN---"+this.getParentStyleOrin()+"--"+arg1.getParentStyleOrin());
        int value1 = this.getReq_Type().compareTo(arg1.getReq_Type());
        if(value1 == 0)
        {
            int value2 = this.getCompletionDate().compareTo(arg1.getCompletionDate());
            if(value2 == 0)
            {
                int value3 = this.getDeptId().compareTo(arg1.getDeptId());
                if(value3 == 0)
                {
                    int valueFirst = Integer.parseInt(this.getParentStyleOrin());
                    int valueLast = Integer.parseInt(arg1.getParentStyleOrin());
                    return Double.compare(valueFirst, valueLast);
                }
                else
                {
                    return value3;
                }
            }
            else
            {
                return value2;
            }
        }
        return value1;
    } 
    
}
