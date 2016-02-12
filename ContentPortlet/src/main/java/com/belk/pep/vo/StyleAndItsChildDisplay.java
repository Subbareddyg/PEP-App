/**
 *
 */
package com.belk.pep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class StyleAndItsChildDisplay.
 *
 * @author AFUSOS3
 */
public class StyleAndItsChildDisplay  implements Serializable{



    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;


    /** The complex pack entry. */
    private String  complexPackEntry;

    /** The pack color entry. */
    private String  packColorEntry;


    /** The style entry. */
    private String  styleEntry;


    /** The style color entry. */
    private String  styleColorEntry;

    /** The style list. */
    List<StyleVO>      styleList = new ArrayList<StyleVO>();


    /**
     * Gets the complex pack entry.
     *
     * @return the complexPackEntry
     */
    public String getComplexPackEntry() {
        return complexPackEntry;
    }

    /**
     * Gets the pack color entry.
     *
     * @return the packColorEntry
     */
    public String getPackColorEntry() {
        return packColorEntry;
    }

    /**
     * Gets the style color entry.
     *
     * @return the styleColorEntry
     */
    public String getStyleColorEntry() {
        return styleColorEntry;
    }

    /**
     * Gets the style entry.
     *
     * @return the styleEntry
     */
    public String getStyleEntry() {
        return styleEntry;
    }

    /**
     * Gets the style list.
     *
     * @return the styleList
     */
    public List<StyleVO> getStyleList() {
        if(styleList==null)
        {
            styleList =new ArrayList<StyleVO>();
        }
        return styleList;
    }

    /**
     * Sets the complex pack entry.
     *
     * @param complexPackEntry the complexPackEntry to set
     */
    public void setComplexPackEntry(String complexPackEntry) {
        this.complexPackEntry = complexPackEntry;
    }

    /**
     * Sets the pack color entry.
     *
     * @param packColorEntry the packColorEntry to set
     */
    public void setPackColorEntry(String packColorEntry) {
        this.packColorEntry = packColorEntry;
    }

    /**
     * Sets the style color entry.
     *
     * @param styleColorEntry the styleColorEntry to set
     */
    public void setStyleColorEntry(String styleColorEntry) {
        this.styleColorEntry = styleColorEntry;
    }






    /**
     * Sets the style entry.
     *
     * @param styleEntry the styleEntry to set
     */
    public void setStyleEntry(String styleEntry) {
        this.styleEntry = styleEntry;
    }

    /**
     * Sets the style list.
     *
     * @param styleList the styleList to set
     */
    public void setStyleList(List<StyleVO> styleList) {
        this.styleList = styleList;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "StyleAndItsChildDisplay [complexPackEntry=" + complexPackEntry
                + ", packColorEntry=" + packColorEntry + ", styleEntry="
                + styleEntry + ", styleColorEntry=" + styleColorEntry
                + ", styleList=" + styleList + "]";
    }


}
