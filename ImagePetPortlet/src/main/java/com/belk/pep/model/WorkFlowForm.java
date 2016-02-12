package com.belk.pep.model;

import java.util.List;

public class WorkFlowForm {
    private String orinNumber;

    private String actionParameter;

    private List<WorkFlow> workFlowList;

    private String[] selectedItems;

    private String[] selectedStyles;
    private String[] selectedSkus;

    /**
     * @return the actionParameter
     */
    public String getActionParameter() {
        return actionParameter;
    }

    /**
     * @return the orinNumber
     */
    public String getOrinNumber() {
        return orinNumber;
    }

    public String[] getSelectedItems() {
        return selectedItems;
    }

    /**
     * @return the selectedSkus
     */
    public String[] getSelectedSkus() {
        return selectedSkus;
    }

    public String[] getSelectedStyles() {
        return selectedStyles;
    }

    /**
     * @return the workFlowList
     */
    public List<WorkFlow> getWorkFlowList() {
        return workFlowList;
    }

    /**
     * @param actionParameter
     *            the actionParameter to set
     */
    public void setActionParameter(String actionParameter) {
        this.actionParameter = actionParameter;
    }

    /**
     * @param orinNumber
     *            the orinNumber to set
     */
    public void setOrinNumber(String orinNumber) {
        this.orinNumber = orinNumber;
    }

    public void setSelectedItems(String[] selectedItems) {
        this.selectedItems = selectedItems;
    }

    /**
     * @param selectedSkus
     *            the selectedSkus to set
     */
    public void setSelectedSkus(String[] selectedSkus) {
        this.selectedSkus = selectedSkus;
    }

    public void setSelectedStyles(String[] selectedStyles) {
        this.selectedStyles = selectedStyles;
    }

    /**
     * @param workFlowList
     *            the workFlowList to set
     */
    public void setWorkFlowList(List<WorkFlow> workFlowList) {
        this.workFlowList = workFlowList;
    }

}
