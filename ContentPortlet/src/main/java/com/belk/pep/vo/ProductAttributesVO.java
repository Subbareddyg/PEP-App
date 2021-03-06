
package com.belk.pep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ProductAttributesVO.
 *
 * @author AFUSOS3
 */
public class ProductAttributesVO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1212658922234957613L;


    /** The drop down list. */
    private  List<?> dropDownList=null;

    /** The radiobutton list. */
    private  List<?> radiobuttonList=null;

    /** The checkbox list. */
    private  List<?> checkboxList=null;

    /** The text field list. */
    private  List<?> textFieldList=null;
    
  //Code for Alphabetical order sorting -START
    private List<?> displayList= null;
    
    public List<?> getDisplayList() {
        return displayList;
    }

    public void setDisplayList(List<?> displayList) {
        this.displayList = displayList;
    }
  //Code for Alphabetical order sorting -END

    /**
     * Instantiates a new product attributes vo.
     */
    public ProductAttributesVO() {

    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductAttributesVO other = (ProductAttributesVO) obj;
        if (checkboxList == null) {
            if (other.checkboxList != null) {
                return false;
            }
        }
        else if (!checkboxList.equals(other.checkboxList)) {
            return false;
        }
        if (dropDownList == null) {
            if (other.dropDownList != null) {
                return false;
            }
        }
        else if (!dropDownList.equals(other.dropDownList)) {
            return false;
        }
        if (radiobuttonList == null) {
            if (other.radiobuttonList != null) {
                return false;
            }
        }
        else if (!radiobuttonList.equals(other.radiobuttonList)) {
            return false;
        }
        if (textFieldList == null) {
            if (other.textFieldList != null) {
                return false;
            }
        }
        else if (!textFieldList.equals(other.textFieldList)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the checkbox list.
     *
     * @return the checkboxList
     */
    public List<?> getCheckboxList() {
        if(checkboxList==null)
        {
            checkboxList = new ArrayList<>();
        }
        return checkboxList;
    }

    /**
     * Gets the drop down list.
     *
     * @return the dropDownList
     */
    public List<?> getDropDownList() {
        if(dropDownList==null)
        {
            dropDownList = new ArrayList<>();
        }
        return dropDownList;
    }


    /**
     * Gets the radiobutton list.
     *
     * @return the radiobuttonList
     */
    public List<?> getRadiobuttonList() {
        if(radiobuttonList==null)
        {
            radiobuttonList = new ArrayList<>();
        }
        return radiobuttonList;
    }

    /**
     * Gets the text field list.
     *
     * @return the textFieldList
     */
    public List<?> getTextFieldList() {
        if(textFieldList==null)
        {
            textFieldList = new ArrayList<>();
        }
        return textFieldList;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                (prime * result)
                + (checkboxList == null ? 0 : checkboxList.hashCode());
        result =
                (prime * result)
                + (dropDownList == null ? 0 : dropDownList.hashCode());
        result =
                (prime * result)
                + (radiobuttonList == null ? 0 : radiobuttonList.hashCode());
        result =
                (prime * result)
                + (textFieldList == null ? 0 : textFieldList.hashCode());
        return result;
    }

    /**
     * Sets the checkbox list.
     *
     * @param checkboxList the checkboxList to set
     */
    public void setCheckboxList(List<?> checkboxList) {
        this.checkboxList = checkboxList;
    }

    /**
     * Sets the drop down list.
     *
     * @param dropDownList the dropDownList to set
     */
    public void setDropDownList(List<?> dropDownList) {
        this.dropDownList = dropDownList;
    }

    /**
     * Sets the radiobutton list.
     *
     * @param radiobuttonList the radiobuttonList to set
     */
    public void setRadiobuttonList(List<?> radiobuttonList) {
        this.radiobuttonList = radiobuttonList;
    }

    /**
     * Sets the text field list.
     *
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
        return "ProductAttributesVO [dropDownList=" + dropDownList
                + ", radiobuttonList=" + radiobuttonList + ", checkboxList="
                + checkboxList + ", textFieldList=" + textFieldList + "]";
    }


}
