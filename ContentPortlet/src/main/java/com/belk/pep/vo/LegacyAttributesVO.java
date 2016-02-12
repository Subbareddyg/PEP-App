
package com.belk.pep.vo;

import java.io.Serializable;
import java.util.List;

/**
 * The Class LegacyAttributesVO.
 *
 * @author AFUSOS3
 */
public class LegacyAttributesVO  implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3419915584374744387L;

    /** The drop down list. */
    private  List<?> dropDownList=null;

    /** The radiobutton list. */
    private  List<?> radiobuttonList=null;

    /** The checkbox list. */
    private  List<?> checkboxList=null;

    /** The text field list. */
    private  List<?> textFieldList=null;
// Added by Karanjeet Sharma for the total list
    private List<?> displayList= null;
    
    public List<?> getDisplayList() {
        return displayList;
    }

    public void setDisplayList(List<?> displayList) {
        this.displayList = displayList;
    }
    // Changes ends here.

    /**
     *
     */
    public LegacyAttributesVO() {

    }

    /**
     * @return the checkboxList
     */
    public List<?> getCheckboxList() {
        return checkboxList;
    }

    /**
     * @return the dropDownList
     */
    public List<?> getDropDownList() {
        return dropDownList;
    }

    /**
     * @return the radiobuttonList
     */
    public List<?> getRadiobuttonList() {
        return radiobuttonList;
    }

    /**
     * @return the textFieldList
     */
    public List<?> getTextFieldList() {
        return textFieldList;
    }

    /**
     * @param checkboxList the checkboxList to set
     */
    public void setCheckboxList(List<?> checkboxList) {
        this.checkboxList = checkboxList;
    }

    /**
     * @param dropDownList the dropDownList to set
     */
    public void setDropDownList(List<?> dropDownList) {
        this.dropDownList = dropDownList;
    }

    /**
     * @param radiobuttonList the radiobuttonList to set
     */
    public void setRadiobuttonList(List<?> radiobuttonList) {
        this.radiobuttonList = radiobuttonList;
    }

    /**
     * @param textFieldList the textFieldList to set
     */
    public void setTextFieldList(List<?> textFieldList) {
        this.textFieldList = textFieldList;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LegacyAttributeVO [dropDownList=" + dropDownList
                + ", radiobuttonList=" + radiobuttonList + ", checkboxList="
                + checkboxList + ", textFieldList=" + textFieldList + "]";
    }

}
