package com.belk.pep.vo;

// TODO: Auto-generated Javadoc
/**
 * The Class CarBrandVO.
 */
public class CarBrandVO {
    
    /** The car brand code. */
    private String carBrandCode;
    
    /** The car brand desc. */
    private String carBrandDesc;
    
    /** The selected brand. */
    private String selectedBrand;
    
    private String selectedCarBrandCode; //VP34
    
    public String getSelectedCarBrandCode() {
		return selectedCarBrandCode;
	}

	public void setSelectedCarBrandCode(String selectedCarBrandCode) {
		this.selectedCarBrandCode = selectedCarBrandCode;
	}

	/**
     * Gets the car brand code.
     *
     * @return the car brand code
     */
    public String getCarBrandCode() {
        return carBrandCode;
    }

    /**
     * Sets the car brand code.
     *
     * @param carBrandCode the new car brand code
     */
    public void setCarBrandCode(String carBrandCode) {
        this.carBrandCode = carBrandCode;
    }

    /**
     * Gets the car brand desc.
     *
     * @return the car brand desc
     */
    public String getCarBrandDesc() {
        return carBrandDesc;
    }

    /**
     * Sets the car brand desc.
     *
     * @param carBrandDesc the new car brand desc
     */
    public void setCarBrandDesc(String carBrandDesc) {
        this.carBrandDesc = carBrandDesc;
    }

    /**
     * Gets the selected brand.
     *
     * @return the selected brand
     */
    public String getSelectedBrand() {
        return selectedBrand;
    }

    /**
     * Sets the selected brand.
     *
     * @param selectedBrand the new selected brand
     */
    public void setSelectedBrand(String selectedBrand) {
        this.selectedBrand = selectedBrand;
    }

    
}
