package com.belk.pep.form;


/**
 * This class is used to save the Service Component data.
 * @author AFUPYB3
 *
 */
public class Component {
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
    
    
    // Group detail Component Form.
    private String id;
    private String defaultAttr;
    private String color;
    private String size;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the defaultAttr
     */
    public String getDefaultAttr() {
        return defaultAttr;
    }
    /**
     * @param defaultAttr the defaultAttr to set
     */
    public void setDefaultAttr(String defaultAttr) {
        this.defaultAttr = defaultAttr;
    }
    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }
    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }
    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }
    
    
  }
