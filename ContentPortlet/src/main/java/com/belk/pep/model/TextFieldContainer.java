
package com.belk.pep.model;

/**
 * The Class TextFieldContainer.
 *
 * @author AFUSOS3
 */
public class TextFieldContainer {

    /** The attribute id. */
    private String attributeId;


    /** The name. */
    private String name;

    private String value;

    /**
     * Instantiates a new text field container.
     */
    public TextFieldContainer() {


    }

    /**
     * Gets the attribute id.
     *
     * @return the attributeId
     */
    public String getAttributeId() {
        return attributeId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the attribute id.
     *
     * @param attributeId the attributeId to set
     */
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value.
     *
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }


}
