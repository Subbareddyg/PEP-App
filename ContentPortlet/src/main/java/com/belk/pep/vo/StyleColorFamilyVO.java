package com.belk.pep.vo;

import java.io.Serializable;

/**
 * The Class StyleColorFamilyVO.
 */
public class StyleColorFamilyVO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The code. */
    private String  code;

    /** The color code start. */
    private String  colorCodeStart;

    /** The color code end. */
    private String  colorCodeEnd;


    /** The super color code. */
    private String  superColorCode;


    /** The super color desc. */
    private String  superColorDesc;


    /**
     * Instantiates a new style color family vo.
     */
    public StyleColorFamilyVO() {
        // TODO Auto-generated constructor stub
    }


    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }


    /**
     * Gets the color code end.
     *
     * @return the colorCodeEnd
     */
    public String getColorCodeEnd() {
        return colorCodeEnd;
    }


    /**
     * Gets the color code start.
     *
     * @return the colorCodeStart
     */
    public String getColorCodeStart() {
        return colorCodeStart;
    }


    /**
     * Gets the super color code.
     *
     * @return the superColorCode
     */
    public String getSuperColorCode() {
        return superColorCode;
    }


    /**
     * Gets the super color desc.
     *
     * @return the superColorDesc
     */
    public String getSuperColorDesc() {
        return superColorDesc;
    }


    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the color code end.
     *
     * @param colorCodeEnd the colorCodeEnd to set
     */
    public void setColorCodeEnd(String colorCodeEnd) {
        this.colorCodeEnd = colorCodeEnd;
    }

    /**
     * Sets the color code start.
     *
     * @param colorCodeStart the colorCodeStart to set
     */
    public void setColorCodeStart(String colorCodeStart) {
        this.colorCodeStart = colorCodeStart;
    }


    /**
     * Sets the super color code.
     *
     * @param superColorCode the superColorCode to set
     */
    public void setSuperColorCode(String superColorCode) {
        this.superColorCode = superColorCode;
    }


    /**
     * Sets the super color desc.
     *
     * @param superColorDesc the superColorDesc to set
     */
    public void setSuperColorDesc(String superColorDesc) {
        this.superColorDesc = superColorDesc;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "StyleColorFamilyVO [code=" + code + ", colorCodeStart="
                + colorCodeStart + ", colorCodeEnd=" + colorCodeEnd
                + ", superColorCode=" + superColorCode + ", superColorDesc="
                + superColorDesc + "]";
    }
}
