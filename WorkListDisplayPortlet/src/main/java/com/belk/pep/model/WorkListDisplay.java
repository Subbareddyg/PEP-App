package com.belk.pep.model;

import java.util.ArrayList;
import java.util.List;



/**
 * The Class WorkListDisplay.
 */
public class WorkListDisplay {

    /**
     * Instantiates a new work list display.
     */
    public WorkListDisplay() {
        
        
       
    }
    
    /** The style list. */
    List<WorkFlow>     workListDisplay = new ArrayList<WorkFlow>();

    /**
     * Gets the work list display.
     *
     * @return the workListDisplay
     */
    public List<WorkFlow> getWorkListDisplay() {
        return workListDisplay;
    }

    /**
     * Sets the work list display.
     *
     * @param workListDisplay the workListDisplay to set
     */
    public void setWorkListDisplay(List<WorkFlow> workListDisplay) {
        this.workListDisplay = workListDisplay;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
            prime * result
                + ((workListDisplay == null) ? 0 : workListDisplay.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WorkListDisplay other = (WorkListDisplay) obj;
        if (workListDisplay == null) {
            if (other.workListDisplay != null)
                return false;
        }
        else if (!workListDisplay.equals(other.workListDisplay))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WorkListDisplay [workListDisplay=" + workListDisplay + "]";
    }

    




}
