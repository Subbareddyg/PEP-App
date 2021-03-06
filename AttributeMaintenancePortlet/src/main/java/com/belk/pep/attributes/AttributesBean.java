
package com.belk.pep.attributes;


/**
 * The Class AttributesBean.
 *
 * @author AFUSOS3
 */
public class AttributesBean {

  
        /** The attribute name. */
        private String attributeName;
        
        /** The attribute value. */
        private String attributeValue;
        
        /**
         * Instantiates a new attributes bean.
         *
         * @param attributeNameLabel the attribute name label
         * @param attributeValueLabel the attribute value label
         */
        public AttributesBean(String attributeNameLabel,String attributeValueLabel){
              this.setAttributeName(attributeNameLabel);
              this.setAttributeValue(attributeValueLabel);
              
        }

        /**
         * Sets the attribute name.
         *
         * @param attributeName the new attribute name
         */
        public void setAttributeName(String attributeName) {
              this.attributeName = attributeName;
        }

        /**
         * Gets the attribute name.
         *
         * @return the attribute name
         */
        public String getAttributeName() {
              return attributeName;
        }

        /**
         * Sets the attribute value.
         *
         * @param attributeValue the new attribute value
         */
        public void setAttributeValue(String attributeValue) {
              this.attributeValue = attributeValue;
        }

        /**
         * Gets the attribute value.
         *
         * @return the attribute value
         */
        public String getAttributeValue() {
              return attributeValue;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "AttributesBean [attributeName=" + attributeName
                + ", attributeValue=" + attributeValue + "]";
        }

    
    
}
