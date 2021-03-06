/**
 * 
 */
package com.belk.pep.vo;

import java.io.Serializable;

/**
 * @author AFUSOS3
 *
 */
public class ProductDetailsVO implements Serializable {

   
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The product name. */
    private String productName;
    
    
    /** The product description. */
    private String productDescription;   
    
    
    /** The style id. */
    private String styleId;
    


    /**
     * Gets the product name.
     *
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }




    /**
     * Sets the product name.
     *
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }





    /**
     * Gets the product description.
     *
     * @return the productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }





    /**
     * Sets the product description.
     *
     * @param productDescription the productDescription to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }





    /**
     * Gets the style id.
     *
     * @return the styleId
     */
    public String getStyleId() {
        return styleId;
    }




    /**
     * Sets the style id.
     *
     * @param styleId the styleId to set
     */
    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }




    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ProductDetailsVO [productName=" + productName
            + ", productDescription=" + productDescription + ", styleId="
            + styleId + "]";
    }

}
