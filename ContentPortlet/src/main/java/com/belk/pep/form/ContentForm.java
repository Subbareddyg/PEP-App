package com.belk.pep.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.belk.pep.vo.CarBrandVO;
import com.belk.pep.vo.ChildSkuVO;
import com.belk.pep.vo.ColorAttributesVO;
import com.belk.pep.vo.ContentHistoryVO;
import com.belk.pep.vo.ContentManagementVO;
import com.belk.pep.vo.CopyAttributeVO;
import com.belk.pep.vo.CopyAttributesVO;
import com.belk.pep.vo.GlobalAttributesVO;
import com.belk.pep.vo.GroupingVO;
import com.belk.pep.vo.HistoryVO;
import com.belk.pep.vo.LegacyAttributesVO;
import com.belk.pep.vo.OmniChannelBrandVO;
import com.belk.pep.vo.ProductAttributesVO;
import com.belk.pep.vo.ProductDetailsVO;
import com.belk.pep.vo.SkuAttributesVO;
import com.belk.pep.vo.StyleAndItsChildDisplay;
import com.belk.pep.vo.StyleInformationVO;
import com.belk.pep.vo.StyleVO;


// TODO: Auto-generated Javadoc
/**
 * The Class ContentForm.
 */
/**
 * @author AFUSXG6
 *
 */
public class ContentForm implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1921358722341034417L;

    /** The style information vo. */
    private  StyleInformationVO styleInformationVO;

    /** The product details vo. */
    private ProductDetailsVO  productDetailsVO;

    /** The style and its child display. */
    private StyleAndItsChildDisplay  styleAndItsChildDisplay;

    /** The global attributes display. */
    private GlobalAttributesVO globalAttributesDisplay;

    /** The content management display. */
    private ContentManagementVO contentManagementDisplay;

    /** The product attributes display. */
    private   ProductAttributesVO productAttributesDisplay;

    /** The legacy attributes display. */
    private   LegacyAttributesVO  legacyAttributesDisplay;

    /** The child sku display list. */
    private  List<ChildSkuVO>  childSkuDisplayList;

    /** The lstomni channel brand vo. */
    private List<OmniChannelBrandVO> lstOmniChannelBrandVO = null;

    /** The lst car brand vo. */
    private List<CarBrandVO> lstCarBrandVO = null;

    /** The car brand selected. */
    private String carBrandSelected;

    /** The mapped category in pet catalog. */
    private String mappedCategoryIdInPetCatalog;//A pet  can be  mapped to one category at a time in pet catalog

    /** The mapped category name in pet catalog. */
    private String mappedCategoryNameInPetCatalog;

    /** The omni brand selected. */
    private String omniBrandSelected;

    /** The category mapping flag. */
    private String categoryMappingFlag;


    /** The content history display. */
    private  List<ContentHistoryVO>   contentHistoryList;

    /** The no child sku data present. */
    private  String   noChildSkuDataPresent;

    /** The content history display. */
    private  ContentHistoryVO   contentHistoryDisplay;

    /** The content history data. */
    private  String  contentHistoryData;

    /** The copy attribute list. */
    private  List<CopyAttributesVO>   copyAttributeDisplayList;

    /** The copy attribute data. */
    private  String  copyAttributeData;

    /** The no pets found in adse database. */
    private  String noPetsFoundInADSEDatabase;


    /** The category reference data. */
    private Map<String ,String> categoryReferenceData=null;

    /** The color data set. */
    private Map<String ,String> colorDataSet=null;

    /** The web service response message. */
    private  String webServiceResponseMessage;

    /** The ajax webservice response message. */
    private  String ajaxWebserviceResponseMessage;

    /** The update content status message. */
    private  String updateContentStatusMessage;

    /** The iph mapping message. */
    private  String iphMappingMessage;

    /** The style data submission data message. */
    private  String styleDataSubmissionDataMessage;

    /** The disable save button. */
    private  String disableSaveButton="false";

    /** The role name. */
    private String roleName;

    /** The pep user id. */
    private String pepUserId;

    /** The fetch style info error message. */
    private  String  fetchStyleInfoErrorMessage;

    /** The ready for review message. */
    private String    readyForReviewMessage;

    /** The blek exclusive selected. */
    private String blekExclusiveSelected;

    /** The ready for review message style color. */
    private String readyForReviewMessageStyleColor;

    /** The no style attribute exists message. */
    private String  noStyleAttributeExistsMessage;

    /** The display style attribute section. */
    private String  displayStyleAttributeSection;

    /** The no style color attribute exists message. */
    private String  noStyleColorAttributeExistsMessage;









    /** The display style color attribute section. */
    private String  displayStyleColorAttributeSection;


    /** The save style attribute message. */
    private String  saveStyleAttributeMessage;

    /** The style color attributes. */
    private ColorAttributesVO styleColorAttributes;

    /** The orin. */
    private String orin;

    /** The grouping number. */
    private String  groupingNumber;

    /** The department number. */
    private String  departmentNumber;

    /** The vendor name. */
    private String  vendorName;



    /** The style number. */
    private String styleNumber;

    /** The user name. */
    private String userName;



    /** The style name. */
    private String styleName;

    /** The department class. */
    private String departmentClass;


    /** The vendor number. */
    private String  vendorNumber;

    /** The item description. */
    private String  itemDescription;


    /** The omnichannel vendor indicator. */
    private Boolean omnichannelVendorIndicator;


    /** The vendor provided image indicator. */
    private Boolean vendorProvidedImageIndicator;

    /** The vendor provided sample indicator. */
    private Boolean vendorProvidedSampleIndicator;

    /** The product name. */
    private String  productName;

    /** The product description. */
    private String  productDescription;

    /** The style list. */
    List<StyleVO> styleList;

    /** The color attributes. */
    ColorAttributesVO  colorAttributes;

    /** The sku attributes. */
    SkuAttributesVO skuAttributes;

    /** The product attributes. */
    ProductAttributesVO productAttributes;

    /** The grouping. */
    GroupingVO    grouping;

    /** The history. */
    HistoryVO history;

    /** The copy attributes. */
    CopyAttributesVO copyAttributes;

    /** The omnichannel color family map. */
    private Map<String, String> omnichannelColorFamilyMap = new HashMap<String, String>();

    /** The secondary color one map. */
    private Map<String, String>  secondaryColorOneMap = new HashMap<String, String>();

    /** The secondary color two map. */
    private Map<String, String>  secondaryColorTwoMap = new HashMap<String, String>();

    /** The secondary color three map. */
    private Map<String, String>  secondaryColorThreeMap = new HashMap<String, String>();



    /** The secondary color four map. */
    private Map<String, String>  secondaryColorFourMap = new HashMap<String, String>();

    /**
     * Instantiates a new content form.
     */
    public ContentForm() {
        super();

    }

    /**
     * Gets the ajax webservice response message.
     *
     * @return the ajaxWebserviceResponseMessage
     */
    public String getAjaxWebserviceResponseMessage() {
        return ajaxWebserviceResponseMessage;
    }

    /**
     * Gets the car brand selected.
     *
     * @return the carBrandSelected
     */
    public String getCarBrandSelected() {
        return carBrandSelected;
    }

    /**
     * Gets the category mapping flag.
     *
     * @return the categoryMappingFlag
     */
    public String getCategoryMappingFlag() {
        return categoryMappingFlag;
    }









    /**
     * Gets the category reference data.
     *
     * @return the categoryReferenceData
     */
    public Map<String, String> getCategoryReferenceData() {
        if(categoryReferenceData==null)
        {
            categoryReferenceData= new LinkedHashMap<String, String>();
        }
        return categoryReferenceData;
    }









    /**
     * Gets the child sku display list.
     *
     * @return the childSkuDisplayList
     */
    public List<ChildSkuVO> getChildSkuDisplayList() {
        if(childSkuDisplayList==null)
        {
            childSkuDisplayList = new ArrayList<ChildSkuVO>();
        }
        return childSkuDisplayList;
    }


    /**
     * Gets the color attributes.
     *
     * @return the colorAttributes
     */
    public ColorAttributesVO getColorAttributes() {
        if(colorAttributes==null)
        {
            colorAttributes= new ColorAttributesVO();
        }
        return colorAttributes;
    }

    /**
     * Gets the color data set.
     *
     * @return the colorDataSet
     */
    public Map<String, String> getColorDataSet() {
        return colorDataSet;
    }

    /**
     * Gets the content history data.
     *
     * @return the contentHistoryData
     */
    public String getContentHistoryData() {
        return contentHistoryData;
    }

    /**
     * Gets the content history display.
     *
     * @return the contentHistoryDisplay
     */
    public ContentHistoryVO getContentHistoryDisplay() {
        if(contentHistoryDisplay==null)
        {
            contentHistoryDisplay= new ContentHistoryVO();
        }
        return contentHistoryDisplay;
    }

    /**
     * Gets the content history list.
     *
     * @return the content history list
     */
    public List<ContentHistoryVO> getContentHistoryList() {
        return contentHistoryList;
    }

    /**
     * Gets the content management display.
     *
     * @return the contentManagementDisplay
     */
    public ContentManagementVO getContentManagementDisplay() {
        if(contentManagementDisplay==null)
        {
            contentManagementDisplay= new ContentManagementVO();
        }
        return contentManagementDisplay;
    }

    /**
     * Gets the copy attribute data.
     *
     * @return the copyAttributeData
     */
    public String getCopyAttributeData() {
        return copyAttributeData;
    }

    /**
     * Gets the copy attribute display list.
     *
     * @return the copyAttributeDisplayList
     */
    public List<CopyAttributesVO> getCopyAttributeDisplayList() {
        return copyAttributeDisplayList;
    }



    /**
     * Gets the copy attributes.
     *
     * @return the copyAttributes
     */
    public CopyAttributesVO getCopyAttributes() {
        if(copyAttributes==null)
        {
            copyAttributes = new CopyAttributesVO();
        }
        return copyAttributes;
    }

    /**
     * Gets the department class.
     *
     * @return the departmentClass
     */
    public String getDepartmentClass() {
        return departmentClass;
    }

    /**
     * Gets the department number.
     *
     * @return the departmentNumber
     */
    public String getDepartmentNumber() {
        return departmentNumber;
    }

    /**
     * Gets the disable save button.
     *
     * @return the disableSaveButton
     */
    public String getDisableSaveButton() {
        return disableSaveButton;
    }

    /**
     * Gets the display style attribute section.
     *
     * @return the displayStyleAttributeSection
     */
    public String getDisplayStyleAttributeSection() {
        return displayStyleAttributeSection;
    }

    /**
     * Gets the display style color attribute section.
     *
     * @return the displayStyleColorAttributeSection
     */
    public String getDisplayStyleColorAttributeSection() {
        return displayStyleColorAttributeSection;
    }

    /**
     * Gets the fetch style info error message.
     *
     * @return the fetchStyleInfoErrorMessage
     */
    public String getFetchStyleInfoErrorMessage() {
        return fetchStyleInfoErrorMessage;
    }

    /**
     * Gets the global attributes display.
     *
     * @return the globalAttributesDisplay
     */
    public GlobalAttributesVO getGlobalAttributesDisplay() {
        if(globalAttributesDisplay==null)
        {
            globalAttributesDisplay= new GlobalAttributesVO();
        }
        return globalAttributesDisplay;
    }



    /**
     * Gets the grouping.
     *
     * @return the grouping
     */
    public GroupingVO getGrouping() {
        if(grouping==null)
        {
            grouping = new GroupingVO();
        }
        return grouping;
    }

    /**
     * Gets the grouping number.
     *
     * @return the groupingNumber
     */
    public String getGroupingNumber() {
        return groupingNumber;
    }


    /**
     * Gets the history.
     *
     * @return the history
     */
    public HistoryVO getHistory() {
        if(history==null)
        {
            history= new HistoryVO();
        }
        return history;
    }

    /**
     * Gets the iph mapping message.
     *
     * @return the iphMappingMessage
     */
    public String getIphMappingMessage() {
        return iphMappingMessage;
    }









    /**
     * Gets the item description.
     *
     * @return the itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Gets the legacy attributes display.
     *
     * @return the legacyAttributesDisplay
     */
    public LegacyAttributesVO getLegacyAttributesDisplay() {
        return legacyAttributesDisplay;
    }

    /**
     * Gets the lst car brand vo.
     *
     * @return the lstCarBrandVO
     */
    public List<CarBrandVO> getLstCarBrandVO() {
        return lstCarBrandVO;
    }

    /**
     * Gets the lst omni channel brand vo.
     *
     * @return the lstOmniChannelBrandVO
     */
    public List<OmniChannelBrandVO> getLstOmniChannelBrandVO() {
        return lstOmniChannelBrandVO;
    }

    /**
     * Gets the mapped category id in pet catalog.
     *
     * @return the mappedCategoryIdInPetCatalog
     */
    public String getMappedCategoryIdInPetCatalog() {
        return mappedCategoryIdInPetCatalog;
    }

    /**
     * Gets the mapped category name in pet catalog.
     *
     * @return the mappedCategoryNameInPetCatalog
     */
    public String getMappedCategoryNameInPetCatalog() {
        return mappedCategoryNameInPetCatalog;
    }

    /**
     * Gets the no child sku data present.
     *
     * @return the noChildSkuDataPresent
     */
    public String getNoChildSkuDataPresent() {
        return noChildSkuDataPresent;
    }

    /**
     * Gets the no pets found in adse database.
     *
     * @return the noPetsFoundInADSEDatabase
     */
    public String getNoPetsFoundInADSEDatabase() {
        return noPetsFoundInADSEDatabase;
    }

    /**
     * Gets the no style attribute exists message.
     *
     * @return the noStyleAttributeExistsMessage
     */
    public String getNoStyleAttributeExistsMessage() {
        return noStyleAttributeExistsMessage;
    }



    /**
     * Gets the no style color attribute exists message.
     *
     * @return the noStyleColorAttributeExistsMessage
     */
    public String getNoStyleColorAttributeExistsMessage() {
        return noStyleColorAttributeExistsMessage;
    }

    /**
     * Gets the omni brand selected.
     *
     * @return the omniBrandSelected
     */
    public String getOmniBrandSelected() {
        return omniBrandSelected;
    }

    /**
     * Gets the omnichannel color family map.
     *
     * @return the omnichannelColorFamilyMap
     */
    public Map<String, String> getOmnichannelColorFamilyMap() {
        return omnichannelColorFamilyMap;
    }

    /**
     * Gets the omnichannel vendor indicator.
     *
     * @return the omnichannelVendorIndicator
     */
    public Boolean getOmnichannelVendorIndicator() {
        return omnichannelVendorIndicator;
    }

    /**
     * Gets the orin.
     *
     * @return the orin
     */
    public String getOrin() {
        return orin;
    }

    /**
     * Get the pep user id.
     *
     * @return pepUserId
     */
    public String getPepUserId() {
        return pepUserId;
    }

    /**
     * Gets the product attributes.
     *
     * @return the productAttributes
     */
    public ProductAttributesVO getProductAttributes() {
        if(productAttributes==null)
        {
            productAttributes= new ProductAttributesVO();
        }
        return productAttributes;
    }

    /**
     * Gets the product attributes display.
     *
     * @return the productAttributesDisplay
     */
    public ProductAttributesVO getProductAttributesDisplay() {
        return productAttributesDisplay;
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
     * Gets the product details vo.
     *
     * @return the productDetailsVO
     */
    public ProductDetailsVO getProductDetailsVO() {
        if(productDetailsVO == null)
        {
            productDetailsVO = new ProductDetailsVO();
        }
        return productDetailsVO;
    }

    /**
     * Gets the product name.
     *
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Gets the ready for review message.
     *
     * @return the readyForReviewMessage
     */
    public String getReadyForReviewMessage() {
        return readyForReviewMessage;
    }

    /**
     * Gets the ready for review message style color.
     *
     * @return the readyForReviewMessageStyleColor
     */
    public String getReadyForReviewMessageStyleColor() {
        return readyForReviewMessageStyleColor;
    }

    /**
     * Gets the role name.
     *
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Gets the save style attribute message.
     *
     * @return the saveStyleAttributeMessage
     */
    public String getSaveStyleAttributeMessage() {
        return saveStyleAttributeMessage;
    }

    /**
     * Gets the secondary color four map.
     *
     * @return the secondaryColorFourMap
     */
    public Map<String, String> getSecondaryColorFourMap() {
        return secondaryColorFourMap;
    }

    /**
     * Gets the secondary color one map.
     *
     * @return the secondaryColorOneMap
     */
    public Map<String, String> getSecondaryColorOneMap() {
        return secondaryColorOneMap;
    }

    /**
     * Gets the secondary color three map.
     *
     * @return the secondaryColorThreeMap
     */
    public Map<String, String> getSecondaryColorThreeMap() {
        return secondaryColorThreeMap;
    }

    /**
     * Gets the secondary color two map.
     *
     * @return the secondaryColorTwoMap
     */
    public Map<String, String> getSecondaryColorTwoMap() {
        return secondaryColorTwoMap;
    }


    /**
     * Gets the sku attributes.
     *
     * @return the skuAttributes
     */
    public SkuAttributesVO getSkuAttributes() {
        if(skuAttributes==null)
        {
            skuAttributes = new SkuAttributesVO();
        }
        return skuAttributes;
    }

    /**
     * Gets the style and its child display.
     *
     * @return the styleAndItsChildDisplay
     */
    public StyleAndItsChildDisplay getStyleAndItsChildDisplay() {
        if(styleAndItsChildDisplay == null)
        {
            styleAndItsChildDisplay = new StyleAndItsChildDisplay();
        }
        return styleAndItsChildDisplay;
    }

    /**
     * Gets the style color attributes.
     *
     * @return the styleColorAttributes
     */
    public ColorAttributesVO getStyleColorAttributes() {
        return styleColorAttributes;
    }

    /**
     * Gets the style data submission data message.
     *
     * @return the styleDataSubmissionDataMessage
     */
    public String getStyleDataSubmissionDataMessage() {
        return styleDataSubmissionDataMessage;
    }

    /**
     * Gets the style information vo.
     *
     * @return the style information vo
     */
    public StyleInformationVO getStyleInformationVO() {
        if(styleInformationVO == null)
        {
            styleInformationVO = new StyleInformationVO();
        }
        return styleInformationVO;
    }

    /**
     * Gets the style list.
     *
     * @return the styleList
     */
    public List<StyleVO> getStyleList() {

        if(styleList==null)
        {
            styleList= new ArrayList<StyleVO>();
        }
        return styleList;
    }

    /**
     * Gets the style name.
     *
     * @return the styleName
     */
    public String getStyleName() {
        return styleName;
    }

    /**
     * Gets the style number.
     *
     * @return the styleNumber
     */
    public String getStyleNumber() {
        return styleNumber;
    }

    /**
     * Gets the update content status message.
     *
     * @return the updateContentStatusMessage
     */
    public String getUpdateContentStatusMessage() {
        return updateContentStatusMessage;
    }

    /**
     * Gets the vendor name.
     *
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * Gets the vendor number.
     *
     * @return the vendorNumber
     */
    public String getVendorNumber() {
        return vendorNumber;
    }

    /**
     * Gets the vendor provided image indicator.
     *
     * @return the vendorProvidedImageIndicator
     */
    public Boolean getVendorProvidedImageIndicator() {
        return vendorProvidedImageIndicator;
    }

    /**
     * Gets the vendor provided sample indicator.
     *
     * @return the vendorProvidedSampleIndicator
     */
    public Boolean getVendorProvidedSampleIndicator() {
        return vendorProvidedSampleIndicator;
    }

    /**
     * Gets the web service response message.
     *
     * @return the webServiceResponseMessage
     */
    public String getWebServiceResponseMessage() {
        return webServiceResponseMessage;
    }

    /**
     * Sets the ajax webservice response message.
     *
     * @param ajaxWebserviceResponseMessage the ajaxWebserviceResponseMessage to set
     */
    public void setAjaxWebserviceResponseMessage(
        String ajaxWebserviceResponseMessage) {
        this.ajaxWebserviceResponseMessage = ajaxWebserviceResponseMessage;
    }

    /**
     * Sets the car brand selected.
     *
     * @param carBrandSelected the carBrandSelected to set
     */
    public void setCarBrandSelected(String carBrandSelected) {
        this.carBrandSelected = carBrandSelected;
    }

    /**
     * Sets the category mapping flag.
     *
     * @param categoryMappingFlag the categoryMappingFlag to set
     */
    public void setCategoryMappingFlag(String categoryMappingFlag) {
        this.categoryMappingFlag = categoryMappingFlag;
    }

    /**
     * Sets the category reference data.
     *
     * @param categoryReferenceData the categoryReferenceData to set
     */
    public void setCategoryReferenceData(Map<String, String> categoryReferenceData) {
        this.categoryReferenceData = categoryReferenceData;
    }


    /**
     * Sets the child sku display list.
     *
     * @param childSkuDisplayList the childSkuDisplayList to set
     */
    public void setChildSkuDisplayList(List<ChildSkuVO> childSkuDisplayList) {
        this.childSkuDisplayList = childSkuDisplayList;
    }






    /**
     * Sets the color attributes.
     *
     * @param colorAttributes the colorAttributes to set
     */
    public void setColorAttributes(ColorAttributesVO colorAttributes) {
        this.colorAttributes = colorAttributes;
    }

    /**
     * Sets the color data set.
     *
     * @param colorDataSet the colorDataSet to set
     */
    public void setColorDataSet(Map<String, String> colorDataSet) {
        this.colorDataSet = colorDataSet;
    }

    /**
     * Sets the content history data.
     *
     * @param contentHistoryData the contentHistoryData to set
     */
    public void setContentHistoryData(String contentHistoryData) {
        this.contentHistoryData = contentHistoryData;
    }

    /**
     * Sets the content history display.
     *
     * @param contentHistoryDisplay the contentHistoryDisplay to set
     */
    public void setContentHistoryDisplay(ContentHistoryVO contentHistoryDisplay) {
        this.contentHistoryDisplay = contentHistoryDisplay;
    }




    /**
     * Sets the content history list.
     *
     * @param contentHistoryList the new content history list
     */
    public void setContentHistoryList(List<ContentHistoryVO> contentHistoryList) {
        this.contentHistoryList = contentHistoryList;
    }

    /**
     * Sets the content management display.
     *
     * @param contentManagementDisplay the contentManagementDisplay to set
     */
    public void setContentManagementDisplay(
        ContentManagementVO contentManagementDisplay) {
        this.contentManagementDisplay = contentManagementDisplay;
    }

    /**
     * Sets the copy attribue data.
     *
     * @param copyAttributeData the copyAttributeData to set
     */
    public void setCopyAttribueData(String copyAttributeData) {
        this.copyAttributeData = copyAttributeData;
    }

    /**
     * Sets the copy attribute data.
     *
     * @param copyAttributeData the copyAttributeData to set
     */
    public void setCopyAttributeData(String copyAttributeData) {
        this.copyAttributeData = copyAttributeData;
    }

    /**
     * Sets the copy attribute display list.
     *
     * @param copyAttributeDisplayList the copyAttributeDisplayList to set
     */
    public void setCopyAttributeDisplayList(List<CopyAttributesVO> copyAttributeDisplayList) {
        this.copyAttributeDisplayList = copyAttributeDisplayList;
    }

    /**
     * Sets the copy attributes.
     *
     * @param copyAttributes the copyAttributes to set
     */
    public void setCopyAttributes(CopyAttributesVO copyAttributes) {
        this.copyAttributes = copyAttributes;
    }

    /**
     * Sets the department class.
     *
     * @param departmentClass the departmentClass to set
     */
    public void setDepartmentClass(String departmentClass) {
        this.departmentClass = departmentClass;
    }



    /**
     * Sets the department number.
     *
     * @param departmentNumber the departmentNumber to set
     */
    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    /**
     * Sets the disable save button.
     *
     * @param disableSaveButton the disableSaveButton to set
     */
    public void setDisableSaveButton(String disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    /**
     * Sets the display style attribute section.
     *
     * @param displayStyleAttributeSection the displayStyleAttributeSection to set
     */
    public void setDisplayStyleAttributeSection(String displayStyleAttributeSection) {
        this.displayStyleAttributeSection = displayStyleAttributeSection;
    }

    /**
     * Sets the display style color attribute section.
     *
     * @param displayStyleColorAttributeSection the displayStyleColorAttributeSection to set
     */
    public void setDisplayStyleColorAttributeSection(
        String displayStyleColorAttributeSection) {
        this.displayStyleColorAttributeSection = displayStyleColorAttributeSection;
    }

    /**
     * Sets the fetch style info error message.
     *
     * @param fetchStyleInfoErrorMessage the fetchStyleInfoErrorMessage to set
     */
    public void setFetchStyleInfoErrorMessage(String fetchStyleInfoErrorMessage) {
        this.fetchStyleInfoErrorMessage = fetchStyleInfoErrorMessage;
    }

    /**
     * Sets the global attributes display.
     *
     * @param globalAttributesDisplay the globalAttributesDisplay to set
     */
    public void setGlobalAttributesDisplay(
        GlobalAttributesVO globalAttributesDisplay) {
        this.globalAttributesDisplay = globalAttributesDisplay;
    }

    /**
     * Sets the grouping.
     *
     * @param grouping the grouping to set
     */
    public void setGrouping(GroupingVO grouping) {
        this.grouping = grouping;
    }

    /**
     * Sets the grouping number.
     *
     * @param groupingNumber the groupingNumber to set
     */
    public void setGroupingNumber(String groupingNumber) {
        this.groupingNumber = groupingNumber;
    }

    /**
     * Sets the history.
     *
     * @param history the history to set
     */
    public void setHistory(HistoryVO history) {
        this.history = history;
    }

    /**
     * Sets the iph mapping message.
     *
     * @param iphMappingMessage the iphMappingMessage to set
     */
    public void setIphMappingMessage(String iphMappingMessage) {
        this.iphMappingMessage = iphMappingMessage;
    }

    /**
     * Sets the item description.
     *
     * @param itemDescription the itemDescription to set
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * Sets the legacy attributes display.
     *
     * @param legacyAttributesDisplay the legacyAttributesDisplay to set
     */
    public void setLegacyAttributesDisplay(
        LegacyAttributesVO legacyAttributesDisplay) {
        this.legacyAttributesDisplay = legacyAttributesDisplay;
    }

    /**
     * Sets the lst car brand vo.
     *
     * @param lstCarBrandVO the lstCarBrandVO to set
     */
    public void setLstCarBrandVO(List<CarBrandVO> lstCarBrandVO) {
        this.lstCarBrandVO = lstCarBrandVO;
    }



    /**
     * Sets the lst omni channel brand vo.
     *
     * @param lstOmniChannelBrandVO the lstOmniChannelBrandVO to set
     */
    public void setLstOmniChannelBrandVO(
        List<OmniChannelBrandVO> lstOmniChannelBrandVO) {
        this.lstOmniChannelBrandVO = lstOmniChannelBrandVO;
    }

    /**
     * Sets the mapped category id in pet catalog.
     *
     * @param mappedCategoryIdInPetCatalog the mappedCategoryIdInPetCatalog to set
     */
    public void setMappedCategoryIdInPetCatalog(String mappedCategoryIdInPetCatalog) {
        this.mappedCategoryIdInPetCatalog = mappedCategoryIdInPetCatalog;
    }

    /**
     * Sets the mapped category name in pet catalog.
     *
     * @param mappedCategoryNameInPetCatalog the mappedCategoryNameInPetCatalog to set
     */
    public void setMappedCategoryNameInPetCatalog(
        String mappedCategoryNameInPetCatalog) {
        this.mappedCategoryNameInPetCatalog = mappedCategoryNameInPetCatalog;
    }


    /**
     * Sets the no child sku data present.
     *
     * @param noChildSkuDataPresent the noChildSkuDataPresent to set
     */
    public void setNoChildSkuDataPresent(String noChildSkuDataPresent) {
        this.noChildSkuDataPresent = noChildSkuDataPresent;
    }

    /**
     * Sets the no pets found in adse database.
     *
     * @param noPetsFoundInADSEDatabase the noPetsFoundInADSEDatabase to set
     */
    public void setNoPetsFoundInADSEDatabase(String noPetsFoundInADSEDatabase) {
        this.noPetsFoundInADSEDatabase = noPetsFoundInADSEDatabase;
    }

    /**
     * Sets the no style attribute exists message.
     *
     * @param noStyleAttributeExistsMessage the noStyleAttributeExistsMessage to set
     */
    public void setNoStyleAttributeExistsMessage(
        String noStyleAttributeExistsMessage) {
        this.noStyleAttributeExistsMessage = noStyleAttributeExistsMessage;
    }

    /**
     * Sets the no style color attribute exists message.
     *
     * @param noStyleColorAttributeExistsMessage the noStyleColorAttributeExistsMessage to set
     */
    public void setNoStyleColorAttributeExistsMessage(
        String noStyleColorAttributeExistsMessage) {
        this.noStyleColorAttributeExistsMessage = noStyleColorAttributeExistsMessage;
    }

    /**
     * Sets the omni brand selected.
     *
     * @param omniBrandSelected the omniBrandSelected to set
     */
    public void setOmniBrandSelected(String omniBrandSelected) {
        this.omniBrandSelected = omniBrandSelected;
    }

    /**
     * Sets the omnichannel color family map.
     *
     * @param omnichannelColorFamilyMap the omnichannelColorFamilyMap to set
     */
    public void setOmnichannelColorFamilyMap(
        Map<String, String> omnichannelColorFamilyMap) {
        this.omnichannelColorFamilyMap = omnichannelColorFamilyMap;
    }

    /**
     * Sets the omnichannel vendor indicator.
     *
     * @param omnichannelVendorIndicator the omnichannelVendorIndicator to set
     */
    public void setOmnichannelVendorIndicator(Boolean omnichannelVendorIndicator) {
        this.omnichannelVendorIndicator = omnichannelVendorIndicator;
    }

    /**
     * Sets the orin.
     *
     * @param orin the orin to set
     */
    public void setOrin(String orin) {
        this.orin = orin;
    }

    /**
     * Sets the pep user id.
     *
     * @param pepUserId the new pep user id
     */
    public void setPepUserId(String pepUserId) {
        this.pepUserId = pepUserId;
    }

    /**
     * Sets the product attributes.
     *
     * @param productAttributes the productAttributes to set
     */
    public void setProductAttributes(ProductAttributesVO productAttributes) {
        this.productAttributes = productAttributes;
    }

    /**
     * Sets the product attributes display.
     *
     * @param productAttributesDisplay the productAttributesDisplay to set
     */
    public void setProductAttributesDisplay(
        ProductAttributesVO productAttributesDisplay) {
        this.productAttributesDisplay = productAttributesDisplay;
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
     * Sets the product details vo.
     *
     * @param productDetailsVO the productDetailsVO to set
     */
    public void setProductDetailsVO(ProductDetailsVO productDetailsVO) {
        this.productDetailsVO = productDetailsVO;
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
     * Sets the ready for review message.
     *
     * @param readyForReviewMessage the readyForReviewMessage to set
     */
    public void setReadyForReviewMessage(String readyForReviewMessage) {
        this.readyForReviewMessage = readyForReviewMessage;
    }

    /**
     * Sets the ready for review message style color.
     *
     * @param readyForReviewMessageStyleColor the readyForReviewMessageStyleColor to set
     */
    public void setReadyForReviewMessageStyleColor(
        String readyForReviewMessageStyleColor) {
        this.readyForReviewMessageStyleColor = readyForReviewMessageStyleColor;
    }

    /**
     * Sets the role name.
     *
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Sets the save style attribute message.
     *
     * @param saveStyleAttributeMessage the saveStyleAttributeMessage to set
     */
    public void setSaveStyleAttributeMessage(String saveStyleAttributeMessage) {
        this.saveStyleAttributeMessage = saveStyleAttributeMessage;
    }

    /**
     * Sets the secondary color four map.
     *
     * @param secondaryColorFourMap the secondaryColorFourMap to set
     */
    public void setSecondaryColorFourMap(Map<String, String> secondaryColorFourMap) {
        this.secondaryColorFourMap = secondaryColorFourMap;
    }

    /**
     * Sets the secondary color one map.
     *
     * @param secondaryColorOneMap the secondaryColorOneMap to set
     */
    public void setSecondaryColorOneMap(Map<String, String> secondaryColorOneMap) {
        this.secondaryColorOneMap = secondaryColorOneMap;
    }

    /**
     * Sets the secondary color three map.
     *
     * @param secondaryColorThreeMap the secondaryColorThreeMap to set
     */
    public void setSecondaryColorThreeMap(Map<String, String> secondaryColorThreeMap) {
        this.secondaryColorThreeMap = secondaryColorThreeMap;
    }

    /**
     * Sets the secondary color two map.
     *
     * @param secondaryColorTwoMap the secondaryColorTwoMap to set
     */
    public void setSecondaryColorTwoMap(Map<String, String> secondaryColorTwoMap) {
        this.secondaryColorTwoMap = secondaryColorTwoMap;
    }

    /**
     * Sets the sku attributes.
     *
     * @param skuAttributes the skuAttributes to set
     */
    public void setSkuAttributes(SkuAttributesVO skuAttributes) {
        this.skuAttributes = skuAttributes;
    }

    /**
     * Sets the style and its child display.
     *
     * @param styleAndItsChildDisplay the styleAndItsChildDisplay to set
     */
    public void setStyleAndItsChildDisplay(StyleAndItsChildDisplay styleAndItsChildDisplay) {
        this.styleAndItsChildDisplay = styleAndItsChildDisplay;
    }

    /**
     * Sets the style color attributes.
     *
     * @param styleColorAttributes the styleColorAttributes to set
     */
    public void setStyleColorAttributes(ColorAttributesVO styleColorAttributes) {
        this.styleColorAttributes = styleColorAttributes;
    }

    /**
     * Sets the style data submission data message.
     *
     * @param styleDataSubmissionDataMessage the styleDataSubmissionDataMessage to set
     */
    public void setStyleDataSubmissionDataMessage(
        String styleDataSubmissionDataMessage) {
        this.styleDataSubmissionDataMessage = styleDataSubmissionDataMessage;
    }

    /**
     * Sets the style information vo.
     *
     * @param styleInformationVO the new style information vo
     */
    public void setStyleInformationVO(StyleInformationVO styleInformationVO) {
        this.styleInformationVO = styleInformationVO;
    }

    /**
     * Sets the style list.
     *
     * @param styleList the styleList to set
     */
    public void setStyleList(List<StyleVO> styleList) {
        this.styleList = styleList;
    }

    /**
     * Sets the style name.
     *
     * @param styleName the styleName to set
     */
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    /**
     * Sets the style number.
     *
     * @param styleNumber the styleNumber to set
     */
    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }









    /**
     * Sets the update content status message.
     *
     * @param updateContentStatusMessage the updateContentStatusMessage to set
     */
    public void setUpdateContentStatusMessage(String updateContentStatusMessage) {
        this.updateContentStatusMessage = updateContentStatusMessage;
    }



    /**
     * Sets the vendor name.
     *
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Sets the vendor number.
     *
     * @param vendorNumber the vendorNumber to set
     */
    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }






    /**
     * Sets the vendor provided image indicator.
     *
     * @param vendorProvidedImageIndicator the vendorProvidedImageIndicator to set
     */
    public void setVendorProvidedImageIndicator(Boolean vendorProvidedImageIndicator) {
        this.vendorProvidedImageIndicator = vendorProvidedImageIndicator;
    }

    /**
     * Sets the vendor provided sample indicator.
     *
     * @param vendorProvidedSampleIndicator the vendorProvidedSampleIndicator to set
     */
    public void setVendorProvidedSampleIndicator(
        Boolean vendorProvidedSampleIndicator) {
        this.vendorProvidedSampleIndicator = vendorProvidedSampleIndicator;
    }

    /**
     * Sets the web service response message.
     *
     * @param webServiceResponseMessage the webServiceResponseMessage to set
     */
    public void setWebServiceResponseMessage(String webServiceResponseMessage) {
        this.webServiceResponseMessage = webServiceResponseMessage;
    }

    
    
    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    /**
     * Gets the blek exclusive selected.
     *
     * @return the blek exclusive selected
     */
    public String getBlekExclusiveSelected() {
        return blekExclusiveSelected;
    }

    /**
     * Sets the blek exclusive selected.
     *
     * @param blekExclusiveSelected the new blek exclusive selected
     */
    public void setBlekExclusiveSelected(String blekExclusiveSelected) {
        this.blekExclusiveSelected = blekExclusiveSelected;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ContentForm [styleInformationVO=" + styleInformationVO
                + ", productDetailsVO=" + productDetailsVO
                + ", styleAndItsChildDisplay=" + styleAndItsChildDisplay
                + ", globalAttributesDisplay=" + globalAttributesDisplay
                + ", contentManagementDisplay=" + contentManagementDisplay
                + ", productAttributesDisplay=" + productAttributesDisplay
                + ", legacyAttributesDisplay=" + legacyAttributesDisplay
                + ", childSkuDisplayList=" + childSkuDisplayList
                + ", noChildSkuDataPresent=" + noChildSkuDataPresent
                + ", contentHistoryDisplay=" + contentHistoryDisplay
                + ", contentHistoryData=" + contentHistoryData
                + ", copyAttributeDisplayList=" + copyAttributeDisplayList
                + ", copyAttributeData=" + copyAttributeData
                + ", noPetsFoundInADSEDatabase=" + noPetsFoundInADSEDatabase
                + ", categoryReferenceData=" + categoryReferenceData
                + ", webServiceResponseMessage=" + webServiceResponseMessage
                + ", ajaxWebserviceResponseMessage="
                + ajaxWebserviceResponseMessage + ", updateContentStatusMessage="
                + updateContentStatusMessage + ", styleDataSubmissionDataMessage="
                + styleDataSubmissionDataMessage + ", disableSaveButton="
                + disableSaveButton + ", roleName=" + roleName + ", pepUserId="
                + pepUserId + ", fetchStyleInfoErrorMessage="
                + fetchStyleInfoErrorMessage + ", readyForReviewMessage="
                + readyForReviewMessage + ", readyForReviewMessageStyleColor="
                + readyForReviewMessageStyleColor
                + ", noStyleAttributeExistsMessage="
                + noStyleAttributeExistsMessage + ", displayStyleAttributeSection="
                + displayStyleAttributeSection
                + ", noStyleColorAttributeExistsMessage="
                + noStyleColorAttributeExistsMessage
                + ", displayStyleColorAttributeSection="
                + displayStyleColorAttributeSection
                + ", saveStyleAttributeMessage=" + saveStyleAttributeMessage
                + ", styleColorAttributes=" + styleColorAttributes + ", orin="
                + orin + ", groupingNumber=" + groupingNumber
                + ", departmentNumber=" + departmentNumber + ", vendorName="
                + vendorName + ", styleNumber=" + styleNumber + ", styleName="
                + styleName + ", departmentClass=" + departmentClass
                + ", vendorNumber=" + vendorNumber + ", itemDescription="
                + itemDescription + ", omnichannelVendorIndicator="
                + omnichannelVendorIndicator + ", vendorProvidedImageIndicator="
                + vendorProvidedImageIndicator + ", vendorProvidedSampleIndicator="
                + vendorProvidedSampleIndicator + ", productName=" + productName
                + ", productDescription=" + productDescription + ", styleList="
                + styleList + ", colorAttributes=" + colorAttributes
                + ", skuAttributes=" + skuAttributes + ", productAttributes="
                + productAttributes + ", grouping=" + grouping + ", history="
                + history + ", copyAttributes=" + copyAttributes
                + ", omnichannelColorFamilyMap=" + omnichannelColorFamilyMap
                + ", secondaryColorOneMap=" + secondaryColorOneMap
                + ", secondaryColorTwoMap=" + secondaryColorTwoMap
                + ", secondaryColorThreeMap=" + secondaryColorThreeMap
                + ", secondaryColorFourMap=" + secondaryColorFourMap + "]";
    }


    /**
     * Attribute added to show the Copy attribute details in screen.
     * 
     * Attribute added For PIM Phase 2 - Regular Item Copy Attribute
     * Date: 05/16/2016
     * Added By: Cognizant
     */
    private CopyAttributeVO copyAttributeVO;

    /**
     * @return the copyAttributeVO
     */
    public CopyAttributeVO getCopyAttributeVO() {
        return copyAttributeVO;
    }

    /**
     * @param copyAttributeVO the copyAttributeVO to set
     */
    public void setCopyAttributeVO(CopyAttributeVO copyAttributeVO) {
        this.copyAttributeVO = copyAttributeVO;
    }



}
