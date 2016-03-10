package com.belk.pep.form;

import java.io.Serializable;
import java.util.List;

import com.belk.pep.vo.AttributeDetailsVO;
import com.belk.pep.vo.AttributeSearchVO;
import com.belk.pep.vo.AttributeValueVO;
import com.belk.pep.vo.CategoryListVO;


/**
 * The Class ContentForm.
 */
/**
 * @author AFUSXG6
 *
 */
public class AttributeMaintenanceForm implements Serializable{  
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1921358722341034417L;

    /**
     * Instantiates a new content form.
     */
    public AttributeMaintenanceForm() {
        super();
    }
     
    /** The attribute detail. */
    private List<AttributeDetailsVO> attributeDetail;
    
    /** The attribute value vo. */
    private AttributeValueVO attributeValueVO;
    
    /** The attribute search vo. */
    private AttributeSearchVO attributeSearchVO;

    /** The category list vo. */
    private List<CategoryListVO> categoryListVO; 
    
    /** The search result error message. */
    private String searchResultErrorMessage;
    
    /** The update status message. */
    private String updateStatusMessage;
    
    /** The enable update details button. */
    private boolean enableUpdateDetailsButton;
    
    /** The category attribute id. */
    private String categoryAttributeId;
    
    
    /**
     * Gets the category attribute id.
     *
     * @return the category attribute id
     */
    public String getCategoryAttributeId() {
        return categoryAttributeId;
    }

    /**
     * Sets the category attribute id.
     *
     * @param categoryAttributeId the new category attribute id
     */
    public void setCategoryAttributeId(String categoryAttributeId) {
        this.categoryAttributeId = categoryAttributeId;
    }

    /**
     * Gets the category list vo.
     *
     * @return the category list vo
     */
    public List<CategoryListVO> getCategoryListVO() {
        return categoryListVO;
    }

    /**
     * Sets the category list vo.
     *
     * @param categoryListVO the new category list vo
     */
    public void setCategoryListVO(List<CategoryListVO> categoryListVO) {
        this.categoryListVO = categoryListVO;
    }

    /**
     * Gets the attribute detail.
     *
     * @return the attribute detail
     */
    public List<AttributeDetailsVO> getAttributeDetail() {
        return attributeDetail;
    }

    /**
     * Sets the attribute detail.
     *
     * @param attributeDetail the new attribute detail
     */
    public void setAttributeDetail(List<AttributeDetailsVO> attributeDetail) {
        this.attributeDetail = attributeDetail;
    }

    /**
     * Gets the attribute value vo.
     *
     * @return the attribute value vo
     */
    public AttributeValueVO getAttributeValueVO() {
        return attributeValueVO;
    }

    /**
     * Sets the attribute value vo.
     *
     * @param attributeValueVO the new attribute value vo
     */
    public void setAttributeValueVO(AttributeValueVO attributeValueVO) {
        this.attributeValueVO = attributeValueVO;
    }

    /**
     * Gets the attribute search vo.
     *
     * @return the attribute search vo
     */
    public AttributeSearchVO getAttributeSearchVO() {
        return attributeSearchVO;
    }

    /**
     * Sets the attribute search vo.
     *
     * @param attributeSearchVO the new attribute search vo
     */
    public void setAttributeSearchVO(AttributeSearchVO attributeSearchVO) {
        this.attributeSearchVO = attributeSearchVO;
    }

    /**
     * Gets the search result error message.
     *
     * @return the search result error message
     */
    public String getSearchResultErrorMessage() {
        return searchResultErrorMessage;
    }

    /**
     * Sets the search result error message.
     *
     * @param searchResultErrorMessage the new search result error message
     */
    public void setSearchResultErrorMessage(String searchResultErrorMessage) {
        this.searchResultErrorMessage = searchResultErrorMessage;
    }

    /**
     * Gets the update status message.
     *
     * @return the update status message
     */
    public String getUpdateStatusMessage() {
        return updateStatusMessage;
    }

    /**
     * Sets the update status message.
     *
     * @param updateStatusMessage the new update status message
     */
    public void setUpdateStatusMessage(String updateStatusMessage) {
        this.updateStatusMessage = updateStatusMessage;
    }

    /**
     * Checks if is enable update details button.
     *
     * @return true, if is enable update details button
     */
    public boolean isEnableUpdateDetailsButton() {
        return enableUpdateDetailsButton;
    }

    /**
     * Sets the enable update details button.
     *
     * @param enableUpdateDetailsButton the new enable update details button
     */
    public void setEnableUpdateDetailsButton(boolean enableUpdateDetailsButton) {
        this.enableUpdateDetailsButton = enableUpdateDetailsButton;
    }
}
