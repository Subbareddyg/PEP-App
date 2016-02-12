package com.belk.pep.model;

import java.io.Serializable;

public class LockPages implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public String userid;
    public String orinNumber;
    public String pageName;
    
    
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getOrinNumber() {
        return orinNumber;
    }
    public void setOrinNumber(String orinNumber) {
        this.orinNumber = orinNumber;
    }
    public String getPageName() {
        return pageName;
    }
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
    
    
    
    
    
    
    
    
    

}
