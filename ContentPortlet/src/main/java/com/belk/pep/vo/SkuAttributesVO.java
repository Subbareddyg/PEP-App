
package com.belk.pep.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class SkuAttributesVO.
 *
 * @author AFUSOS3
 */
public class SkuAttributesVO  implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8332027093206935184L;

    /**
     * Instantiates a new sku attributes vo.
     */
    public SkuAttributesVO() {
        
    }
    
    /** The sku orin number. */
    private String skuOrinNumber;
    
    /** The source domestic. */
    private String sourceDomestic;
    
    /** The nrf size code. */
    private String nrfSizeCode;    
    
    /** The vendor size description. */
    private  String vendorSizeDescription;
    
    /** The omnichannel size description. */
    private  String omnichannelSizeDescription;    
    
    /** The belk04 upc. */
    private  String belk04Upc;    

    /** The vendor upc. */
    private  String vendorUpc;

    /** The product weight. */
    private String productWeight;
    
    /** The product weight uom. */
    private String productWeightUom;
    
    /** The product length. */
    private String productLength;

    /** The product height. */
    private String productHeight;

    /** The product width. */
    private String productWidth;

    /** The product dimension uom. */
    private String productDimensionUom;
    
    /** The gwp. */
    private String gwp;

    /** The pwp. */
    private String pwp;

    /** The pyg. */
    private String pyg;
    
    /** The launch date. */
    private String launchDate;

    /** The gift box. */
    private String giftBox;
    
    /**
     * Gets the source domestic.
     *
     * @return the sourceDomestic
     */
    public String getSourceDomestic() {
        return sourceDomestic;
    }

    /**
     * Sets the source domestic.
     *
     * @param sourceDomestic the sourceDomestic to set
     */
    public void setSourceDomestic(String sourceDomestic) {
        this.sourceDomestic = sourceDomestic;
    }

    /**
     * Gets the nrf size code.
     *
     * @return the nrfSizeCode
     */
    public String getNrfSizeCode() {
        return nrfSizeCode;
    }

    /**
     * Sets the nrf size code.
     *
     * @param nrfSizeCode the nrfSizeCode to set
     */
    public void setNrfSizeCode(String nrfSizeCode) {
        this.nrfSizeCode = nrfSizeCode;
    }

    /**
     * Gets the vendor size description.
     *
     * @return the vendorSizeDescription
     */
    public String getVendorSizeDescription() {
        return vendorSizeDescription;
    }

    /**
     * Sets the vendor size description.
     *
     * @param vendorSizeDescription the vendorSizeDescription to set
     */
    public void setVendorSizeDescription(String vendorSizeDescription) {
        this.vendorSizeDescription = vendorSizeDescription;
    }

    /**
     * Gets the omnichannel size description.
     *
     * @return the omnichannelSizeDescription
     */
    public String getOmnichannelSizeDescription() {
        return omnichannelSizeDescription;
    }

    /**
     * Sets the omnichannel size description.
     *
     * @param omnichannelSizeDescription the omnichannelSizeDescription to set
     */
    public void setOmnichannelSizeDescription(String omnichannelSizeDescription) {
        this.omnichannelSizeDescription = omnichannelSizeDescription;
    }

    /**
     * Gets the belk04 upc.
     *
     * @return the belk04Upc
     */
    public String getBelk04Upc() {
        return belk04Upc;
    }

    /**
     * Sets the belk04 upc.
     *
     * @param belk04Upc the belk04Upc to set
     */
    public void setBelk04Upc(String belk04Upc) {
        this.belk04Upc = belk04Upc;
    }

    /**
     * Gets the vendor upc.
     *
     * @return the vendorUpc
     */
    public String getVendorUpc() {
        return vendorUpc;
    }

    /**
     * Sets the vendor upc.
     *
     * @param vendorUpc the vendorUpc to set
     */
    public void setVendorUpc(String vendorUpc) {
        this.vendorUpc = vendorUpc;
    }

    /**
     * Gets the sku orin number.
     *
     * @return the skuOrinNumber
     */
    public String getSkuOrinNumber() {
        return skuOrinNumber;
    }

    /**
     * Sets the sku orin number.
     *
     * @param skuOrinNumber the skuOrinNumber to set
     */
    public void setSkuOrinNumber(String skuOrinNumber) {
        this.skuOrinNumber = skuOrinNumber;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SkuAttributesVO [skuOrinNumber=" + skuOrinNumber
            + ", sourceDomestic=" + sourceDomestic + ", nrfSizeCode="
            + nrfSizeCode + ", vendorSizeDescription=" + vendorSizeDescription
            + ", omnichannelSizeDescription=" + omnichannelSizeDescription
            + ", belk04Upc=" + belk04Upc + ", vendorUpc=" + vendorUpc + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
            prime * result + ((belk04Upc == null) ? 0 : belk04Upc.hashCode());
        result =
            prime * result
                + ((nrfSizeCode == null) ? 0 : nrfSizeCode.hashCode());
        result =
            prime
                * result
                + ((omnichannelSizeDescription == null) ? 0
                    : omnichannelSizeDescription.hashCode());
        result =
            prime * result
                + ((skuOrinNumber == null) ? 0 : skuOrinNumber.hashCode());
        result =
            prime * result
                + ((sourceDomestic == null) ? 0 : sourceDomestic.hashCode());
        result =
            prime
                * result
                + ((vendorSizeDescription == null) ? 0 : vendorSizeDescription
                    .hashCode());
        result =
            prime * result + ((vendorUpc == null) ? 0 : vendorUpc.hashCode());
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
        SkuAttributesVO other = (SkuAttributesVO) obj;
        if (belk04Upc == null) {
            if (other.belk04Upc != null)
                return false;
        }
        else if (!belk04Upc.equals(other.belk04Upc))
            return false;
        if (nrfSizeCode == null) {
            if (other.nrfSizeCode != null)
                return false;
        }
        else if (!nrfSizeCode.equals(other.nrfSizeCode))
            return false;
        if (omnichannelSizeDescription == null) {
            if (other.omnichannelSizeDescription != null)
                return false;
        }
        else if (!omnichannelSizeDescription
            .equals(other.omnichannelSizeDescription))
            return false;
        if (skuOrinNumber == null) {
            if (other.skuOrinNumber != null)
                return false;
        }
        else if (!skuOrinNumber.equals(other.skuOrinNumber))
            return false;
        if (sourceDomestic == null) {
            if (other.sourceDomestic != null)
                return false;
        }
        else if (!sourceDomestic.equals(other.sourceDomestic))
            return false;
        if (vendorSizeDescription == null) {
            if (other.vendorSizeDescription != null)
                return false;
        }
        else if (!vendorSizeDescription.equals(other.vendorSizeDescription))
            return false;
        if (vendorUpc == null) {
            if (other.vendorUpc != null)
                return false;
        }
        else if (!vendorUpc.equals(other.vendorUpc))
            return false;
        return true;
    }

    /**
     * Gets the product weight.
     *
     * @return the product weight
     */
    public String getProductWeight() {
        return productWeight;
    }

    /**
     * Sets the product weight.
     *
     * @param productWeight the new product weight
     */
    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    /**
     * Gets the product weight uom.
     *
     * @return the product weight uom
     */
    public String getProductWeightUom() {
        return productWeightUom;
    }

    /**
     * Sets the product weight uom.
     *
     * @param productWeightUom the new product weight uom
     */
    public void setProductWeightUom(String productWeightUom) {
        this.productWeightUom = productWeightUom;
    }

    /**
     * Gets the product length.
     *
     * @return the product length
     */
    public String getProductLength() {
        return productLength;
    }

    /**
     * Sets the product length.
     *
     * @param productLength the new product length
     */
    public void setProductLength(String productLength) {
        this.productLength = productLength;
    }

    /**
     * Gets the product height.
     *
     * @return the product height
     */
    public String getProductHeight() {
        return productHeight;
    }

    /**
     * Sets the product height.
     *
     * @param productHeight the new product height
     */
    public void setProductHeight(String productHeight) {
        this.productHeight = productHeight;
    }

    /**
     * Gets the product width.
     *
     * @return the product width
     */
    public String getProductWidth() {
        return productWidth;
    }

    /**
     * Sets the product width.
     *
     * @param productWidth the new product width
     */
    public void setProductWidth(String productWidth) {
        this.productWidth = productWidth;
    }

    /**
     * Gets the product dimension uom.
     *
     * @return the product dimension uom
     */
    public String getProductDimensionUom() {
        return productDimensionUom;
    }

    /**
     * Sets the product dimension uom.
     *
     * @param productDimensionUom the new product dimension uom
     */
    public void setProductDimensionUom(String productDimensionUom) {
        this.productDimensionUom = productDimensionUom;
    }

    /**
     * Gets the gwp.
     *
     * @return the gwp
     */
    public String getGwp() {
        return gwp;
    }

    /**
     * Sets the gwp.
     *
     * @param gwp the new gwp
     */
    public void setGwp(String gwp) {
        this.gwp = gwp;
    }

    /**
     * Gets the pwp.
     *
     * @return the pwp
     */
    public String getPwp() {
        return pwp;
    }

    /**
     * Sets the pwp.
     *
     * @param pwp the new pwp
     */
    public void setPwp(String pwp) {
        this.pwp = pwp;
    }

    /**
     * Gets the pyg.
     *
     * @return the pyg
     */
    public String getPyg() {
        return pyg;
    }

    /**
     * Sets the pyg.
     *
     * @param pyg the new pyg
     */
    public void setPyg(String pyg) {
        this.pyg = pyg;
    }

    /**
     * Gets the launch date.
     *
     * @return the launch date
     */
    public String getLaunchDate() {
        return launchDate;
    }

    /**
     * Sets the launch date.
     *
     * @param launchDate the new launch date
     */
    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    /**
     * Gets the gift box.
     *
     * @return the gift box
     */
    public String getGiftBox() {
        return giftBox;
    }

    /**
     * Sets the gift box.
     *
     * @param giftBox the new gift box
     */
    public void setGiftBox(String giftBox) {
        this.giftBox = giftBox;
    }

    
}
