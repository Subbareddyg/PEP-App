
package com.belk.pep.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ItemPrimaryHierarchyVO.
 *
 * @author AFUSOS3
 */
public class ItemPrimaryHierarchyVO implements Comparable<ItemPrimaryHierarchyVO> {



    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5735711260361673121L;


    /** The Category id. */
    private String CategoryId;

    /** The Category name. */
    private String CategoryName;

    /** The pet category id. */
    private String petCategoryId;

    /** The pet category name. */
    private String petCategoryName;

    /** The merch category id. */
    private String merchandiseCategoryId;


    /** The merch category name. */
    private String  merchandiseCategoryName;
    
    /** The category full path. */
    private String categoryFullPath;


    /**
     * Instantiates a new item primary hierarchy vo.
     */
    public ItemPrimaryHierarchyVO() {

    }


    /**
     * Gets the category id.
     *
     * @return the categoryId
     */
    public String getCategoryId() {
        return CategoryId;
    }

    /**
     * Gets the category name.
     *
     * @return the categoryName
     */
    public String getCategoryName() {
        return CategoryName;
    }

    /**
     * Gets the merchandise category id.
     *
     * @return the merchandiseCategoryId
     */
    public String getMerchandiseCategoryId() {
        return merchandiseCategoryId;
    }

    /**
     * Gets the merchandise category name.
     *
     * @return the merchandiseCategoryName
     */
    public String getMerchandiseCategoryName() {
        return merchandiseCategoryName;
    }

    /**
     * Gets the pet category id.
     *
     * @return the petCategoryId
     */
    public String getPetCategoryId() {
        return petCategoryId;
    }

    /**
     * Gets the pet category name.
     *
     * @return the petCategoryName
     */
    public String getPetCategoryName() {
        return petCategoryName;
    }


    /**
     * Sets the category id.
     *
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    /**
     * Sets the category name.
     *
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    /**
     * Sets the merchandise category id.
     *
     * @param merchandiseCategoryId the merchandiseCategoryId to set
     */
    public void setMerchandiseCategoryId(String merchandiseCategoryId) {
        this.merchandiseCategoryId = merchandiseCategoryId;
    }

    /**
     * Sets the merchandise category name.
     *
     * @param merchandiseCategoryName the merchandiseCategoryName to set
     */
    public void setMerchandiseCategoryName(String merchandiseCategoryName) {
        this.merchandiseCategoryName = merchandiseCategoryName;
    }

    /**
     * Sets the pet category id.
     *
     * @param petCategoryId the petCategoryId to set
     */
    public void setPetCategoryId(String petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    /**
     * Sets the pet category name.
     *
     * @param petCategoryName the petCategoryName to set
     */
    public void setPetCategoryName(String petCategoryName) {
        this.petCategoryName = petCategoryName;
    }


    /**
     * Gets the category full path.
     *
     * @return the category full path
     */
    public String getCategoryFullPath() {
        return categoryFullPath;
    }


    /**
     * Sets the category full path.
     *
     * @param categoryFullPath the new category full path
     */
    public void setCategoryFullPath(String categoryFullPath) {
        this.categoryFullPath = categoryFullPath;
    }

    @Override
    public int compareTo(ItemPrimaryHierarchyVO obj) {
        int value = 0;
        if(null != obj && obj.getCategoryFullPath() != null && this.getCategoryFullPath() != null)
        {
           // value = obj.getDisplayName().compareTo(this.getDisplayName());
            value = this.getCategoryFullPath().compareTo(obj.getCategoryFullPath());
        }
        return value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ItemPrimaryHierarchyVO [CategoryId=" + CategoryId
                + ", CategoryName=" + CategoryName + ", petCategoryId="
                + petCategoryId + ", petCategoryName=" + petCategoryName
                + ", merchandiseCategoryId=" + merchandiseCategoryId
                + ", merchandiseCategoryName=" + merchandiseCategoryName + "]";
    }




}
