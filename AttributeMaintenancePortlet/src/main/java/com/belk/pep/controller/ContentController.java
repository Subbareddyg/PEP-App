
package com.belk.pep.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.mvc.EventAwareController;
import org.springframework.web.portlet.mvc.ResourceAwareController;

import com.belk.pep.attributes.AttributesBean;
import com.belk.pep.attributes.ItemIdBean;
import com.belk.pep.common.model.Common_BelkUser;
import com.belk.pep.common.model.Common_Vpuser;
import com.belk.pep.common.model.ContentPetDetails;
import com.belk.pep.constants.ContentScreenConstants;
import com.belk.pep.delegate.ContentDelegate;
import com.belk.pep.exception.checked.PEPDelegateException;
import com.belk.pep.form.ContentForm;
import com.belk.pep.jsonconverter.DataObject;
import com.belk.pep.jsonconverter.UpdateContentStatusDataObject;
import com.belk.pep.model.PetsFound;
import com.belk.pep.model.WebserviceResponse;
import com.belk.pep.util.ExtractColorCode;
import com.belk.pep.vo.ChildSkuVO;
import com.belk.pep.vo.ColorAttributesVO;
import com.belk.pep.vo.ContentHistoryVO;
import com.belk.pep.vo.ContentManagementVO;
import com.belk.pep.vo.CopyAttributesVO;
import com.belk.pep.vo.GlobalAttributesVO;
import com.belk.pep.vo.ItemPrimaryHierarchyVO;
import com.belk.pep.vo.PetAttributeVO;
import com.belk.pep.vo.ProductAttributesVO;
import com.belk.pep.vo.ProductDetailsVO;
import com.belk.pep.vo.SkuAttributesVO;
import com.belk.pep.vo.SkuVO;
import com.belk.pep.vo.StyleAndItsChildDisplay;
import com.belk.pep.vo.StyleColorVO;
import com.belk.pep.vo.StyleInformationVO;
import com.belk.pep.vo.StyleVO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;




/**
 * The Class ContentController.
 */
@Controller
@RequestMapping("VIEW")
public class ContentController implements ResourceAwareController,EventAwareController{


    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(ContentController.class.getName());

    // String private String


    /** The omnichannel color family map. */
    private static Map<String, String> omnichannelColorFamilyMap = new HashMap<String, String>();

    static {
        omnichannelColorFamilyMap.put("ColorOne", "Color 1");
        omnichannelColorFamilyMap.put("ColorTwo", "Color 2");
    }

    /** The secondary color one map. */
    private static Map<String, String> secondaryColorOneMap = new HashMap<String, String>();

    static {
        secondaryColorOneMap.put("ColorOne", "Color 1");
        secondaryColorOneMap.put("ColorTwo", "Color 2");

    }

    /** The secondary color two map. */
    private static Map<String, String> secondaryColorTwoMap = new HashMap<String, String>();

    static {
        secondaryColorTwoMap.put("ColorOne", "Color 1");
        secondaryColorTwoMap.put("ColorTwo", "Color 2");

    }

    /** The secondary color three map. */
    private static Map<String, String> secondaryColorThreeMap = new HashMap<String, String>();

    static {
        secondaryColorThreeMap.put("ColorOne", "Color 1");
        secondaryColorThreeMap.put("ColorTwo", "Color 2");

    }

    /** The secondary color four map. */
    private static Map<String, String> secondaryColorFourMap = new HashMap<String, String>();


    static {
        secondaryColorFourMap.put("ColorOne", "Color 1");
        secondaryColorFourMap.put("ColorTwo", "Color 2");
    }
    /** The content delegate. */
    private ContentDelegate contentDelegate;


    /** The content form. */
    private ContentForm contentDisplayForm;
    /** The style attributes. */
    private GlobalAttributesVO styleAttributes;


    /** The style color attribute. */
    private  ColorAttributesVO  styleColorAttribute;
    /** The response message web service. */
    private String responseMessageWebService = "";

    /** The model and view. */
    private ModelAndView modelAndView=null ;
    /** The style data submission flag. */
    private boolean  styleDataSubmissionFlag;


    /** The disable save button flag. */
    private boolean  disableSaveButtonFlag;

    /** The category id from drop down. */
    private String  categoryIdFromDropDown;


    /**
     * Check webservice response.
     */
    private void checkWebserviceResponse() {
        // Check  the response from the webservice
        if(getResponseMessageWebService().contains("successfully")){
            LOGGER.info("getResponseMessageWebService---successfully");

            modelAndView.addObject("responseSuccessMessageWebService", getResponseMessageWebService());
        }
        else if(getResponseMessageWebService().contains("updation failed"))
        {
            LOGGER.info("getResponseMessageWebService---updation failed");
            modelAndView.addObject("responseSuccessMessageWebService", getResponseMessageWebService());
        }
        else if(getResponseMessageWebService().contains("HTTP Error"))
        {
            LOGGER.info("getResponseMessageWebService---HTTP Error");
            modelAndView.addObject("responseFailMessageWebService", getResponseMessageWebService());
        }

    }

    /**
     * Disable save button.
     *
     * @param request the request
     * @param contentDisplayForm the content display form
     * @param disableSaveButtonFlag the disable save button flag
     */
    private void disableSaveButton(RenderRequest request,ContentForm contentDisplayForm, boolean disableSaveButtonFlag) {
        final String disableSaveButton=request.getParameter(ContentScreenConstants.DISABLE_SAVE_BUTTON_KEY);
        if(StringUtils.isNotBlank(disableSaveButton))
        {
            contentDisplayForm.setDisableSaveButton(disableSaveButton);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }

    }










    /**
     * Display category specific attribute data.
     *
     * @param contentDisplayForm2 the content display form2
     * @param request the request
     * @param response the response
     */
    private void displayCategorySpecificAttributeData(
        ContentForm contentDisplayForm2, RenderRequest request,
        RenderResponse response,String orinNumber) {
        LOGGER.info("start of displayCategorySpecificAttributeData......");
        List<PetAttributeVO> categorySpecificAttributesObjectList = null;
        List<String> dropDownValueList=null;

        ProductAttributesVO productAttributesDisplay= new ProductAttributesVO();

        final List<PetAttributeVO> categorySpecificAttributesObjectDropDownList = new ArrayList<PetAttributeVO>();

        final List<PetAttributeVO> dropDownList = new ArrayList<PetAttributeVO>();

        final List<PetAttributeVO> categorySpecificAttributesObjectTextFieldList =new ArrayList<PetAttributeVO>();

        final List<PetAttributeVO> categorySpecificAttributesObjectRadioButtonList = new ArrayList<PetAttributeVO>();

        final List<PetAttributeVO> categorySpecificAttributesObjectCheckBoxList =new ArrayList<PetAttributeVO>();
        try {
            //String categoryId=request.getParameter("dynamicCategoryId");
            String categoryId= getCategoryIdFromDropDown();
            categorySpecificAttributesObjectList = contentDelegate.getPetAttributeDetails(categoryId,orinNumber);

            LOGGER.info("categorySpecificAttributesObjectList. size...."+categorySpecificAttributesObjectList.size());
        }
        catch (final PEPDelegateException e) {

            e.printStackTrace();
        }
        formHtmlDynamicDisplay();
        if(categorySpecificAttributesObjectList!=null)
        {

            for(final PetAttributeVO   categorySpecificAttributeObject :categorySpecificAttributesObjectList)
            {

                final String attributeFieldType=categorySpecificAttributeObject.getAttributeFieldType();
                LOGGER.info("attributeFieldType..."+attributeFieldType);
                final String  attributeName =categorySpecificAttributeObject.getAttributeName();
                LOGGER.info("attributeName..."+attributeName);
                final String  attributeFieldValue=categorySpecificAttributeObject.getAttributeFieldValue();
                LOGGER.info("attributeFieldValue..."+attributeFieldValue);
                String attributeType=categorySpecificAttributeObject.getAttributeType();

                final String  secondarySpecValue=categorySpecificAttributeObject.getSecondarySpecValue();
                /*  final Map<String, List<?>> mapOfDisplayFields =categorySpecificAttributeObject.getMapOfDisplayFields();
                //loop a Map
                for (final Map.Entry<String, List<?>> entry : mapOfDisplayFields.entrySet()) {
                    LOGGER.info("Key : " + entry.getKey() + " Value : " + entry.getValue());
                }*/

                switch (attributeFieldType)
                {
                case "Drop Down":

                    categorySpecificAttributesObjectDropDownList.add(categorySpecificAttributeObject);


                    break;
                case "Radio Button":
                    categorySpecificAttributesObjectRadioButtonList.add(categorySpecificAttributeObject);

                    break;
                case "Check Boxes":
                    categorySpecificAttributesObjectCheckBoxList.add(categorySpecificAttributeObject);


                    break;

                case "Text Field":

                    categorySpecificAttributesObjectTextFieldList.add(categorySpecificAttributeObject);


                    break;

                default:

                    break;
                }








            }



            if((categorySpecificAttributesObjectDropDownList!=null) && (categorySpecificAttributesObjectDropDownList.size()>0))
            {
                for(PetAttributeVO petAttributeDropDownObject  :categorySpecificAttributesObjectDropDownList)
                {
                    String htmlDisplayName=petAttributeDropDownObject.getDisplayName();
                    String htmlFieldValue=petAttributeDropDownObject.getAttributeFieldValue();
                    LOGGER.info("htmlFieldValue --------- " +htmlFieldValue);
                    //Convert the field values separated by pipe to the hash map of values for displaying in the drop down
                    Map<String ,String > dropdownValueMap=  new ConcurrentHashMap<String, String>();

                    if(StringUtils.isNotBlank(htmlFieldValue))
                    {
                        htmlFieldValue = htmlFieldValue.replace("|", "~");
                        dropDownValueList = Arrays.asList(htmlFieldValue.split("~"));

                        //Iterate over the  dropDownValueList to put the key value  in the Hash Map
                        for(String dropDownValue:dropDownValueList)
                        {
                            dropdownValueMap.put(dropDownValue, dropDownValue);
                        }
                        petAttributeDropDownObject.setDropDownValuesMap(dropdownValueMap);


                    }

                    LOGGER.info("dropdownValueMap---size-------" +dropdownValueMap.size());
                    LOGGER.info("petAttributeDropDownObject.getDropDownValuesMap()----------" +petAttributeDropDownObject.getDropDownValuesMap());
                    dropDownList.add(petAttributeDropDownObject);

                }
                productAttributesDisplay.setDropDownList(dropDownList);

            }

            if((categorySpecificAttributesObjectCheckBoxList!=null) && (categorySpecificAttributesObjectCheckBoxList.size()>0))
            {


                productAttributesDisplay.setCheckboxList(categorySpecificAttributesObjectCheckBoxList);
            }

            if((categorySpecificAttributesObjectRadioButtonList!=null) && (categorySpecificAttributesObjectRadioButtonList.size()>0))
            {
                productAttributesDisplay.setRadiobuttonList(categorySpecificAttributesObjectRadioButtonList);
            }

            if((categorySpecificAttributesObjectTextFieldList!=null) && (categorySpecificAttributesObjectTextFieldList.size()>0))
            {
                productAttributesDisplay.setTextFieldList(categorySpecificAttributesObjectTextFieldList);
            }

            contentDisplayForm.setProductAttributesDisplay(productAttributesDisplay);


            LOGGER.info("categorySpecificAttributesObjectDropDownList --------- " + categorySpecificAttributesObjectDropDownList.size());
            LOGGER.info("categorySpecificAttributesObjectRadioButtonList --------- " + categorySpecificAttributesObjectRadioButtonList.size());
            LOGGER.info("categorySpecificAttributesObjectCheckBoxList --------- " + categorySpecificAttributesObjectCheckBoxList.size());
            LOGGER.info("categorySpecificAttributesObjectTextFieldList --------- " + categorySpecificAttributesObjectTextFieldList.size());
            LOGGER.info("dropDownValueList having pipe value-------- " +dropDownValueList.size());
            LOGGER.info("dropDownList-------- " +dropDownList.size());




            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

        }
        LOGGER.info("End of displayCategorySpecificAttributeData.... ");
    }








    /**
     * Display content history data.
     *
     * @param orinNumber the orin number
     * @param contentDisplayForm the content display form
     * @param request the request
     * @param response the response
     */
    private void displayContentHistoryData(String orinNumber,ContentForm contentDisplayForm, RenderRequest request,RenderResponse response) {
        //Get the Content History from the ADSE  tables and prepare  the data for displaying on the Content History Section
        final ContentHistoryVO  contentHistory= contentDelegate.getContentHistory(orinNumber);
        if(contentHistory!=null)
        {
            contentDisplayForm.setContentHistoryDisplay(contentHistory);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }
        else
        {
            //set the message to display on the screen No Content History Data Exists
            contentDisplayForm.setContentHistoryData("No Content History data exists");

        }

    }




    /**
     * Display content management.
     *
     * @param orinNumber the orin number
     * @param contentDisplayForm2 the content display form2
     * @param request the request
     * @param response the response
     */
    private void displayContentManagement(String orinNumber,ContentForm contentDisplayForm, RenderRequest request, RenderResponse response) {
        //Get the content management data from the ADSE_PET_CATALOG table and prep the data for displaying on the Pet Content Management Section
        final ContentManagementVO  contentManagementVO= contentDelegate.getContentManagmentInfo(orinNumber);
        if(contentManagementVO!=null)
        {
            contentDisplayForm.setContentManagementDisplay(contentManagementVO);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }


    }




    /**
     * Display copy attribute data.
     *
     * @param orinNumber the orin number
     * @param contentDisplayForm the content display form
     * @param request the request
     * @param response the response
     */
    private void displayCopyAttributeData(String orinNumber,ContentForm contentDisplayForm, RenderRequest request, RenderResponse response) {
        //Get the Copy Attribute data from the ADSE  tables and prepare  the data for displaying on the Content Copy Attribute  Section
        final List<CopyAttributesVO>  copyAttributeList= contentDelegate.getCopyAttributes(orinNumber);
        if((copyAttributeList!=null) && (copyAttributeList.size()>0) )
        {
            contentDisplayForm.setCopyAttributeDisplayList(copyAttributeList);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }
        else
        {
            //set the message to display on the screen no  copy attribute  data exists
            contentDisplayForm.setCopyAttribueData("No Copy Attrbute Data exists");;
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }

    }




    /**
     * Display iph categories.
     *
     * @param orinNumber the orin number
     * @param contentDisplayForm the content display form
     * @param request the request
     * @param response the response
     */
    private void displayIPHCategories(String orinNumber,ContentForm contentDisplayForm, RenderRequest request,RenderResponse response) {
        List<ItemPrimaryHierarchyVO> iphCategoryList;
        try {
            iphCategoryList = contentDelegate.getIPHCategoriesFromADSEPetCatalog(orinNumber);
            if((iphCategoryList!=null) && (iphCategoryList.size()>0))
            {
                final Map<String, String> categoryReferenceData =  getIPHCategory(iphCategoryList,orinNumber);
                contentDisplayForm.setCategoryReferenceData(categoryReferenceData);

                modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
            }
        }
        catch (final PEPDelegateException e) {

            e.printStackTrace();
        }



    }




    /**
     * Display pets as parent child.
     *
     * @param petList the pet list
     * @return the style and its child display
     */
    private StyleAndItsChildDisplay displayPetsAsParentChild(List<PetsFound> petList) {
        String childsParentOrinNumber = null;
        String  parentOrinNumber = null;
        final List<SkuVO> skuList = new ArrayList<SkuVO>();
        final List<StyleColorVO> styleColorList = new ArrayList<StyleColorVO>();
        final List<StyleVO> styleList = new ArrayList<StyleVO>();
        final List<StyleVO> styleListForContentDisplay = new ArrayList<StyleVO>();
        final StyleAndItsChildDisplay styleAndItsChildDisplay= new StyleAndItsChildDisplay();
        for(final PetsFound pet:petList)
        {
            final String entryType=pet.getEntryType();
            if(entryType.equalsIgnoreCase("SKU"))
            {
                childsParentOrinNumber=pet.getParentStyleOrin();
                final String orinNumber=pet.getOrinNumber();
                final String color= pet.getColor();
                final String vendorSize=pet.getVendorSize();
                final String omniChannelSizeDescription=pet.getOmniSizeDescription();
                final String contentState=pet.getContentState();
                final String colorCode= pet.getColorCode();
                final String completionDate=pet.getCompletionDate();
                final SkuVO sku = new SkuVO();
                sku.setEntryType(entryType);
                sku.setStyleId(childsParentOrinNumber);
                sku.setOrin(orinNumber);
                sku.setColor(color);
                sku.setColorCode(colorCode);
                sku.setVendorSize(vendorSize);
                sku.setOmniChannelSizeDescription(omniChannelSizeDescription);
                sku.setCompletionDate(completionDate);
                sku.setContentStatus(contentState);
                skuList.add(sku);//Add all the SKU to the  SKU list

            }

            if(entryType.equalsIgnoreCase("StyleColor"))
            {
                childsParentOrinNumber=pet.getParentStyleOrin();
                final String orinNumber=pet.getOrinNumber();
                final String color= pet.getColor();
                final String vendorSize=pet.getVendorSize();
                final String omniSizeDescription=pet.getOmniSizeDescription();
                final String contentState=pet.getContentState();
                final String completionDate=pet.getCompletionDate();
                final StyleColorVO styleColor = new StyleColorVO();
                styleColor.setEntryType(entryType);
                styleColor.setParentStyleOrinNumber(childsParentOrinNumber);
                styleColor.setOrinNumber(orinNumber);
                styleColor.setColor(color);
                styleColor.setVendorSize(vendorSize);
                styleColor.setOmniSizeDescription(omniSizeDescription);
                styleColor.setCompletionDate(completionDate);
                styleColor.setContentStatus(contentState);

                styleColorList.add(styleColor);//Add all the StyleColor to the  Style Color list
                LOGGER.info("styleColorList size.."+styleColorList.size());

            }
            if(entryType.equalsIgnoreCase("Style"))
            {

                final String orinNumber= pet.getOrinNumber();
                pet.getVendorStyle();
                parentOrinNumber=pet.getParentStyleOrin();
                final String color= pet.getColor();
                final String vendorSize=pet.getVendorSize();
                final String omniSizeDescription=pet.getOmniSizeDescription();
                final String contentState=pet.getContentState();
                final String completionDate=pet.getCompletionDate();
                final StyleVO style =new StyleVO();
                style.setEntryType(entryType);
                style.setColor(color);
                style.setOrinNumber(orinNumber);
                style.setParentOrinNumber(orinNumber);//changed the Parent MDMID of the Style is null
                style.setVendorSize(vendorSize);
                style.setCompletionDate(completionDate);
                style.setContentStatus(contentState);
                style.setOmniSizeDescription(omniSizeDescription);
                styleList.add(style);//Add all the Style to the  Style  list

            }


        }
        LOGGER.info("styleList length="+styleList.size());
        LOGGER.info("styleColorList length="+styleColorList.size());
        //Check for the Parent Child association
        for(final StyleVO style :styleList)
        {
            parentOrinNumber = style.getParentOrinNumber();
            LOGGER.info("parentOrinNumber.."+parentOrinNumber);
            final List<StyleColorVO> subStyleColorList = new ArrayList<StyleColorVO>();
            List<SkuVO> childSkuList = null;
            String styleColorOrinNumber1=null;

            for(final StyleColorVO styleColor :styleColorList)
            {
                //Get the Orin Number of the Style Color,Style Color is the child of the Parent Style
                childsParentOrinNumber =styleColor.getParentStyleOrinNumber();
                childSkuList = new  ArrayList<SkuVO>();
                childsParentOrinNumber =styleColor.getParentStyleOrinNumber();
                styleColorOrinNumber1=styleColor.getOrinNumber();
                final String colorCodeFromStyleColorOrinNumber=ExtractColorCode.getLastThreeDigitNRFColorCode(styleColorOrinNumber1);
                System.out.println("styleColorOrinNumber1.."+styleColorOrinNumber1);
                //Check if the Style Parent  Orin Number is same as  the  Style Color Child Orin Number
                if(parentOrinNumber.equalsIgnoreCase(childsParentOrinNumber))
                {
                    for(final SkuVO sku  :skuList)
                    {
                        final String skuColorCode=sku.getColorCode();
                        // Create the Style Color parent and SKU  Child relationship
                        if(colorCodeFromStyleColorOrinNumber.equalsIgnoreCase(skuColorCode)){
                            //Add all the sku with the matching color code with that of the Parent Style Color  Number in a list
                            childSkuList.add(sku);
                        }


                    }

                    //Add all the list of child SKUs to its respective parent Style Color
                    styleColor.setSkuList(childSkuList);
                    subStyleColorList.add(styleColor);
                    //Add all the list of child Style Colors  to its respective parent Style
                    style.setStyleColorList(subStyleColorList);
                    System.out.println("get the SKUList size..."+styleColor.getSkuList().size());

                }

            }
            if(subStyleColorList.size()>0){
                LOGGER.info("Size of the Color List .."+subStyleColorList.size());
                style.setStyleColorList(subStyleColorList);//Add all the child Style Colors to the Parent Style
            }else{
                LOGGER.info("This is from Else part just return the style list with out any child style color");

            }
            styleListForContentDisplay.add(style);//Add  all the Styles with children Style Color to the declared style list for content  display

        }


        LOGGER.info("styleColorList.."+styleListForContentDisplay.size());
        styleAndItsChildDisplay.setStyleList(styleListForContentDisplay);//Add all the Styles to the content list display form

        LOGGER.info("styAndItsChildDisplay size.."+styleAndItsChildDisplay.getStyleList().size());
        return styleAndItsChildDisplay;

    }




    /**
     * Display product information.
     *
     * @param orinNumber the orin number
     * @param contentDisplayForm the content display form
     * @param request the request
     * @param response the response
     */
    private void displayProductInformation(String orinNumber,ContentForm contentDisplayForm, RenderRequest request,RenderResponse response) {
        LOGGER.info("Start of displayProductInformation.... ");

        final ProductDetailsVO   productInformation= contentDelegate.getProductInformation(orinNumber);
        if(productInformation!=null)
        {
            LOGGER.info("----------------------getProductName from ADSE tables----------------------"+productInformation.getProductName());
            LOGGER.info("----------------------getProductDescription from ADSE tables----------------------"+productInformation.getProductDescription());
            contentDisplayForm.setProductDetailsVO(productInformation);
            LOGGER.info("-------------contentDisplayForm---------getProductName from ADSE tables----------------------"+contentDisplayForm.getProductDetailsVO().getProductName());
            LOGGER.info("-------------contentDisplayForm---------getProductDescription from ADSE tables----------------------"+contentDisplayForm.getProductDetailsVO().getProductDescription());
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

        }
        LOGGER.info("End of displayProductInformation.... ");
    }




    /**
     * Display save style attribute webservice message.
     *
     * @param request the request
     * @param contentDisplayForm the content display form
     */
    private void displaySaveStyleAttributeWebserviceMessage(RenderRequest request, ContentForm contentDisplayForm) {
        final String  saveStyleAttributeMessage= request.getParameter(ContentScreenConstants.SAVE_STYLE_ATTRIBUTES_KEY);
        LOGGER.info("saveStyleAttributeMessage = "+ saveStyleAttributeMessage);
        if(StringUtils.isNotBlank(saveStyleAttributeMessage))
        {
            LOGGER.info("saveStyleAttributeMessage = "+ saveStyleAttributeMessage);
            contentDisplayForm.setSaveStyleAttributeMessage(saveStyleAttributeMessage);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

        }

    }




    /**
     * Display sku data.
     *
     * @param orinNumber the orin number
     * @param contentDisplayForm the content display form
     * @param request the request
     * @param response the response
     */
    private void displaySKUData(String orinNumber,ContentForm contentDisplayForm, RenderRequest request,RenderResponse response) {
        //Get the SKU data from the ADSE  tables and prep the data for displaying on the Child SKU Section
        final List<ChildSkuVO>  skuList= contentDelegate.getChildSkuInfo(orinNumber);
        if((skuList!=null) && (skuList.size()>0) )
        {
            contentDisplayForm.setChildSkuDisplayList(skuList);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }
        else
        {
            //set the message to display on the screen No Child SKU data exists
            contentDisplayForm.setNoChildSkuDataPresent("No Child SKU data exists");
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }

    }




    /**
     * Display style attribute data.
     *
     * @param contentDisplayForm the content display form
     * @param request the request
     * @param response the response
     */
    private void displayStyleAttributeData(ContentForm contentDisplayForm,RenderRequest request, RenderResponse response) {
        LOGGER.info("----------Start of displayStyleAttributeData----------");
        final String displayStyleAttributeSectionFlag= request.getParameter(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_KEY);
        if(StringUtils.isNotBlank(displayStyleAttributeSectionFlag))
        {
            LOGGER.info("----------displayStyleAttributeSectionFlag---------"+displayStyleAttributeSectionFlag);
            if(displayStyleAttributeSectionFlag.equals(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_FLAG_YES))
            {
                contentDisplayForm.setDisplayStyleAttributeSection(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_FLAG_YES);
            }
            else if(displayStyleAttributeSectionFlag.equals(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_FLAG_NO))
            {

                contentDisplayForm.setDisplayStyleAttributeSection(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_FLAG_NO);
                final GlobalAttributesVO styleAttributes= getStyleAttributes();
                LOGGER.info("----------styleAttributes---------"+styleAttributes);
                if(styleAttributes!=null)
                {
                    contentDisplayForm.setGlobalAttributesDisplay(getStyleAttributes());
                    LOGGER.info("----------contentDisplayForm.getGlobalAttributesDisplay()---------"+contentDisplayForm.getGlobalAttributesDisplay());
                }
                else
                {
                    final String noStyleAttributeExistsMessage= request.getParameter(ContentScreenConstants.NO_STYLE_ATTRIBUTE_EXISTS_KEY);
                    contentDisplayForm.setNoStyleAttributeExistsMessage(noStyleAttributeExistsMessage);

                }

            }
        }
        LOGGER.info("----------End of displayStyleAttributeData----------");




    }




    /**
     * Display style color attribute data.
     *
     * @param contentDisplayForm the content display form
     * @param request the request
     * @param response the response
     */
    private void displayStyleColorAttributeData(ContentForm contentDisplayForm,RenderRequest request,RenderResponse response) {
        LOGGER.info("----------Start of displayStyleColorAttributeData----------");
        final String displayStyleColorAttributeSectionFlag= request.getParameter(ContentScreenConstants.HIDE_STYLE_COLOR_ATTRIBUTE_SECTION_KEY);
        if(StringUtils.isNotBlank(displayStyleColorAttributeSectionFlag))
        {
            LOGGER.info("----------displayStyleColorAttributeSectionFlag---------"+displayStyleColorAttributeSectionFlag);
            if(displayStyleColorAttributeSectionFlag.equals(ContentScreenConstants.HIDE_STYLE_COLOR_ATTRIBUTE_SECTION_FLAG_YES))
            {
                contentDisplayForm.setDisplayStyleColorAttributeSection(ContentScreenConstants.HIDE_STYLE_COLOR_ATTRIBUTE_SECTION_FLAG_YES);
            }
            else if(displayStyleColorAttributeSectionFlag.equals(ContentScreenConstants.HIDE_STYLE_COLOR_ATTRIBUTE_SECTION_FLAG_NO))
            {

                contentDisplayForm.setDisplayStyleColorAttributeSection(ContentScreenConstants.HIDE_STYLE_COLOR_ATTRIBUTE_SECTION_FLAG_NO);
                final ColorAttributesVO styleColorAttributes= getStyleColorAttribute();
                LOGGER.info("----------styleColorAttributes---------"+styleColorAttributes);
                if(styleColorAttributes!=null)
                {
                    contentDisplayForm.setStyleColorAttributes(styleColorAttributes);
                    contentDisplayForm.setOmnichannelColorFamilyMap(omnichannelColorFamilyMap);
                    contentDisplayForm.setSecondaryColorOneMap(secondaryColorOneMap);
                    contentDisplayForm.setSecondaryColorTwoMap(secondaryColorTwoMap);
                    contentDisplayForm.setSecondaryColorThreeMap(secondaryColorThreeMap);
                    contentDisplayForm.setSecondaryColorFourMap(secondaryColorFourMap);

                    LOGGER.info("----------contentDisplayForm.getStyleColorAttributes()---------"+contentDisplayForm.getStyleColorAttributes());
                }
                else
                {
                    final String noStyleColorAttributeExistsMessage= request.getParameter(ContentScreenConstants.NO_STYLE_COLOR_ATTRIBUTE_DATA_EXISTS_KEY);
                    contentDisplayForm.setNoStyleColorAttributeExistsMessage(noStyleColorAttributeExistsMessage);

                }

            }
        }
        LOGGER.info("----------End of displayStyleColorAttributeData----------");

    }




    /**
     * Display style information.
     *
     * @param orinNumber the orin number
     * @param contentDisplayForm the content display form
     * @param request the request
     * @param response the response
     */
    private void displayStyleInformation(String orinNumber, ContentForm contentDisplayForm, RenderRequest request, RenderResponse response) {
        try {
            final StyleInformationVO  styleInformation= contentDelegate.getStyleInformation(orinNumber);


            if(styleInformation!=null)
            {
                contentDisplayForm.setStyleInformationVO(styleInformation);
                LOGGER.info("-----getStyleId------"+ contentDisplayForm.getStyleInformationVO().getStyleId());

                modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

            }
        }
        catch (final PEPDelegateException e) {
            LOGGER.severe("Exception occurred"+e.getMessage());
            contentDisplayForm.setFetchStyleInfoErrorMessage(ContentScreenConstants.FETCH_STYLE_INFO_ERROR_MESSAGE);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

        }
    }




    /**
     * Display the message to submit style level data before submitting style color data.
     *
     * @param request the request
     * @param contentDisplayForm the content display form
     * @param styleDataSubmissionFlag the style data submission flag
     */
    private void displayTheMessageToSubmitStyleLevelDataBeforeSubmittingStyleColorData(
        RenderRequest request, ContentForm contentDisplayForm, boolean styleDataSubmissionFlag) {
        if(!styleDataSubmissionFlag)
        {
            final String  styeLevelDataToBeSubmittedFirstMessage= request.getParameter(ContentScreenConstants.SUBMIT_STYLE_LEVEL_DATA_FIRST);

            if(StringUtils.isNotBlank(styeLevelDataToBeSubmittedFirstMessage))
            {
                contentDisplayForm.setStyleDataSubmissionDataMessage(styeLevelDataToBeSubmittedFirstMessage);
                modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
            }


        }

    }




    /**
     * Display update style color status webservice message.
     *
     * @param request the request
     * @param contentDisplayForm the content display form
     */
    private void displayUpdateStyleColorStatusWebserviceMessage(RenderRequest request, ContentForm contentDisplayForm) {
        final String  updateContentStatusForStyleColorMessage= request.getParameter(ContentScreenConstants.UPDATE_STYLE_COLOR_PET_CONTENT_STATUS_WEBSERVICE_REPONSE_KEY);
        LOGGER.info("updateContentStatusForStyleColorMessage = "+ updateContentStatusForStyleColorMessage);
        if(StringUtils.isNotBlank(updateContentStatusForStyleColorMessage))
        {
            LOGGER.info("updateContentStatusForStyleColorMessage = "+ updateContentStatusForStyleColorMessage);
            contentDisplayForm.setUpdateContentStatusMessage(updateContentStatusForStyleColorMessage);
            LOGGER.info(" contentDisplayForm.setUpdateContentStatusMessage(= "+ contentDisplayForm.getUpdateContentStatusMessage());
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

        }

    }




    /**
     * Display update style status webservice message.
     *
     * @param request the request
     * @param contentDisplayForm the content display form
     */
    private void displayUpdateStyleStatusWebserviceMessage(RenderRequest request, ContentForm contentDisplayForm) {
        final String  updateContentStatusForStyleMessage= request.getParameter(ContentScreenConstants.UPDATE_STYLE_PET_CONTENT_STATUS_WEBSERVICE_REPONSE_KEY);
        LOGGER.info("updateContentStatusForStyleMessage = "+ updateContentStatusForStyleMessage);
        if(StringUtils.isNotBlank(updateContentStatusForStyleMessage))
        {
            LOGGER.info("updateContentStatusForStyleMessage = "+ updateContentStatusForStyleMessage);
            contentDisplayForm.setUpdateContentStatusMessage(updateContentStatusForStyleMessage);
            LOGGER.info(" contentDisplayForm.setUpdateContentStatusMessage(= "+ contentDisplayForm.getUpdateContentStatusMessage());
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

        }

    }




    /*    @RequestMapping(method = RequestMethod.POST)
    private void foo() {
        LOGGER.info("foo from drop dwon = ");

    }
     */



    @ResourceMapping("fetchIPHCategory") ModelAndView fetchIPHCategory(ResourceRequest request, ResourceResponse response){
        LOGGER.info("fetchIPHCategory ************..");
        final ModelAndView modelAndView = null;
        final String categoryId = request.getParameter("categoryId");
        LOGGER.info("fetchIPHCategory **categoryId**********.."+categoryId);
        if(StringUtils.isBlank(categoryId)){
            setCategoryIdFromDropDown(categoryId);
        }
        return modelAndView;

    }



    /**
     * Gets the category id from drop down.
     *
     * @return the categoryIdFromDropDown
     */
    public String getCategoryIdFromDropDown() {
        return categoryIdFromDropDown;
    }



    /**
     * Gets the content delegate.
     *
     * @return the content delegate
     */
    public ContentDelegate getContentDelegate() {
        return contentDelegate;
    }







    /**
     * Gets the content display form.
     *
     * @return the contentDisplayForm
     */
    public ContentForm getContentDisplayForm() {
        return contentDisplayForm;
    }



    /**
     * Gets the content pet details from inter portlet communication.
     *
     * @param request the request
     * @return the content pet details from inter portlet communication
     */
    private ContentPetDetails getContentPetDetailsFromIPC(RenderRequest request) {
        LOGGER.info("-----------------Start of getContentPetDetailsFromIPC-----------------");
        LOGGER.info("request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS).."+request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS));
        if(request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS)!=null)
        {
            LOGGER.info("request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS).not null check."+request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS));
            final ContentPetDetails contentPetDetails= (ContentPetDetails) request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS);
            LOGGER.info("-----------------End of getContentPetDetailsFromIPC-----------------");
            return contentPetDetails;
        }
        return null;


    }




    @ActionMapping(params = "action=getIphAttributeAction")
    public void getIphAttributeAction(ActionRequest request,
        ActionResponse resourceResponse,
        final BindingResult result,
        final Model model) {

        LOGGER.info("end of ActionMapping....getIPHAttributesFromDB.");

    }

    /**
     * Gets the IPH category.
     *
     * @param iphCategoryList the iph category list
     * @return the IPH category
     * @throws PEPDelegateException
     */
    private Map<String, String> getIPHCategory(List<ItemPrimaryHierarchyVO> iphCategoryList,String orinNumber) throws PEPDelegateException {
        LOGGER.info("--------------start of getIPHCategory----------------");

        final Map<String,String> categoryMap = new LinkedHashMap<String,String>();
        for(final ItemPrimaryHierarchyVO iphCategory:iphCategoryList)
        {
            final String categoryId=  iphCategory.getPetCategoryId();
            final String categoryName= iphCategory.getPetCategoryName();
            LOGGER.info("--------------categoryId----------------"+categoryId);
            LOGGER.info("------------categoryName---------------"+categoryName);
            final boolean categoryIdFlag=StringUtils.isNotBlank(categoryId) && !categoryId.equalsIgnoreCase("No Data");
            final boolean categoryNameFlag=StringUtils.isNotBlank(categoryId) && !categoryId.equalsIgnoreCase("No Data");
            LOGGER.info("--------------categoryIdFlag----------------"+categoryIdFlag);
            LOGGER.info("------------categoryNameFlag---------------"+categoryNameFlag);
            if(categoryIdFlag && categoryNameFlag)
            {
                LOGGER.info("------------Inside the categoryIdFlag ,categoryNameFlag if loop---------------");
                categoryMap.put(categoryId, categoryName);

            }
            else
            {
                //Get the category id and category name from the ADSE_MERCHANDISE_HIERARCHY TABLE when the  category id and category name is not present  in the ADSE_PET_CATALOG TABLE

                try {
                    final List<ItemPrimaryHierarchyVO>  iphCategoryFromAdseMerchHierarchyList= contentDelegate.getIPHCategoriesFromAdseMerchandiseHierarchy(orinNumber);
                    for(final ItemPrimaryHierarchyVO iphCategoryMerchandiseObject:iphCategoryFromAdseMerchHierarchyList)
                    {

                        final String categoryIdFromMerchandise=  iphCategoryMerchandiseObject.getMerchandiseCategoryId();
                        final String categoryNameFromMerchandise= iphCategoryMerchandiseObject.getMerchandiseCategoryName();
                        LOGGER.info("--------------categoryIdFromMerchandise----------------"+categoryIdFromMerchandise);
                        LOGGER.info("------------categoryNameFromMerchandise---------------"+categoryNameFromMerchandise);
                        final boolean categoryIdMerchFlag=StringUtils.isNotBlank(categoryIdFromMerchandise) && !categoryId.equalsIgnoreCase("No Data");
                        final boolean categoryNameMerchFlag=StringUtils.isNotBlank(categoryNameFromMerchandise) && !categoryId.equalsIgnoreCase("No Data");
                        LOGGER.info("--------------categoryIdMerchFlag----------------"+categoryIdMerchFlag);
                        LOGGER.info("------------categoryNameMerchFlag---------------"+categoryNameMerchFlag);
                        if(categoryIdMerchFlag && categoryNameMerchFlag)
                        {
                            LOGGER.info("------------Inside the categoryIdMerchFlag ,categoryNameMerchFlag if loop---------------");

                            categoryMap.put(categoryIdFromMerchandise, categoryNameFromMerchandise);

                        }
                    }

                }
                catch (final Exception e) {

                    e.printStackTrace();
                }


            }
            LOGGER.info("--------------end of getIPHCategory----------------");


        }

        return categoryMap;

    }





    /**
     * Gets the model and view.
     *
     * @return the modelAndView
     */
    public ModelAndView getModelAndView() {
        return modelAndView;
    }



    /**
     * Gets the pet list.
     *
     * @param orinNumber the orin number
     * @param contentDisplayForm2 the content display form2
     * @param request the request
     * @param response the response
     * @return the pet list
     */
    private void getPetList(String orinNumber, ContentForm contentDisplayForm,
        RenderRequest request, RenderResponse response) {
        List<PetsFound> petList;
        try {
            petList = contentDelegate.getPetList(orinNumber);
            if((petList!=null) && (petList.size()>0))
            {
                LOGGER.info("StylePetsFound---Data");
                modelAndView.addObject("StylePetsFound","Data exists");
                final StyleAndItsChildDisplay styleAndItsChildDisplay= displayPetsAsParentChild(petList);
                contentDisplayForm.setStyleAndItsChildDisplay(styleAndItsChildDisplay);
                modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
            }
            else
            {
                LOGGER.info("noStylePetsFound---No Data exist");
                System.out.println("Style Attributes is Null for Global Styles");
                contentDisplayForm.setNoPetsFoundInADSEDatabase("true");
                modelAndView.addObject("noStylePetsFound","No Data exists");
                modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
            }
        }
        catch (final PEPDelegateException e) {
            LOGGER.info("PEPDelegateException......message........"+e.getMessage());
            LOGGER.info("PEPDelegateException......stacktrace........"+e.getStackTrace());
            LOGGER.info("PEPDelegateException......cause........"+e.getCause());
            e.printStackTrace();
        }


    }

    /**
     * Gets the response message web service.
     *
     * @return the responseMessageWebService
     */
    public String getResponseMessageWebService() {
        return responseMessageWebService;
    }

    /**
     * Gets the SKU attribute details.
     *
     * @param request the request
     * @param response the response
     * @return the SKU attribute details
     */
    @ResourceMapping("getSKUAttributeDetails")
    public ModelAndView getSKUAttributeDetails(ResourceRequest request, ResourceResponse response){
        LOGGER.info("getSKUAttributeDetails ************..");
        final ModelAndView modelAndView = null;

        final String skuOrinNumber = request.getParameter("skuOrinNumber");
        if (StringUtils.isNotBlank(skuOrinNumber)) {
            LOGGER.info("@ResourceMapping-skuOrinNumber--- " + skuOrinNumber);
            SkuAttributesVO skuAttributes;
            try {
                skuAttributes = contentDelegate.getSkuAttributes(skuOrinNumber);

                if (skuAttributes != null) {
                    skuAttributes.setSkuOrinNumber(skuOrinNumber);
                    final Gson gson = new Gson();
                    final JsonObject jsonObject = new JsonObject();
                    final JsonElement skuAttributeObject = gson.toJsonTree(skuAttributes);
                    jsonObject.add("skuObjectInfo",skuAttributeObject);
                    System.out.println("myObj sku attributes from json--->" + jsonObject.toString());
                    try {
                        response.getWriter().write(jsonObject.toString());

                    }
                    catch (final IOException e) {

                        e.printStackTrace();
                    }
                    LOGGER.info("inside get SKUAttributeDetails");

                    return null;
                }
            }

            catch (final PEPDelegateException e1) {

                e1.printStackTrace();
            }

            LOGGER.info("Exiting getSKUAttributeDetails method");

        }
        return modelAndView;

    }


    /**
     * Gets the style attribute details.
     *
     * @param request the request
     * @param response the response
     * @return the style attribute details
     */
    @ResourceMapping("getStyleAttributeDetails")
    public ModelAndView getStyleAttributeDetails(ResourceRequest request, ResourceResponse response){
        LOGGER.info("getStyleAttributeDetails ************..");
        final ModelAndView modelAndView = null;

        final String styleOrinNumber = request.getParameter("styleOrinNumber");
        if (StringUtils.isNotBlank(styleOrinNumber)) {
            LOGGER.info("@ResourceMapping-styleOrinNumber--- " + styleOrinNumber);
            GlobalAttributesVO styleAttributes;
            try {
                styleAttributes = contentDelegate.getStyleAttributesADSE(styleOrinNumber);

                if (styleAttributes != null) {
                    styleAttributes.setStyleOrinNumber(styleOrinNumber);
                    final Gson gson = new Gson();
                    final JsonObject jsonObject = new JsonObject();
                    final JsonElement styleAttributeObject = gson.toJsonTree(styleAttributes);
                    jsonObject.add("styleAttributeObject",styleAttributeObject);
                    System.out.println("styleAttributeObject json--->" + jsonObject.toString());
                    try {
                        response.getWriter().write(jsonObject.toString());

                    }
                    catch (final IOException e) {

                        e.printStackTrace();
                    }
                    LOGGER.info("inside get getStyleAttributeDetails");

                    return null;
                }
            }

            catch (final PEPDelegateException e1) {

                e1.printStackTrace();
            }

            LOGGER.info("Exiting getStyleAttributeDetails method");

        }
        return modelAndView;

    }










    /**
     * Gets the style attributes.
     *
     * @return the styleAttributes
     */
    public GlobalAttributesVO getStyleAttributes() {
        return styleAttributes;
    }

    /**
     * Gets the style color attribute.
     *
     * @return the styleColorAttribute
     */
    public ColorAttributesVO getStyleColorAttribute() {
        return styleColorAttribute;
    }

    /**
     * Gets the style color attribute details.
     *
     * @param request the request
     * @param response the response
     * @return the style color attribute details
     */
    @ResourceMapping("getStyleColorAttributeDetails")
    public ModelAndView getStyleColorAttributeDetails(ResourceRequest request, ResourceResponse response){
        LOGGER.info("getStyleColorAttributes ************..");
        final ModelAndView modelAndView = null;

        final String styleColorOrinNumber = request.getParameter("styleColorOrinNumber");
        if (StringUtils.isNotBlank(styleColorOrinNumber)) {
            LOGGER.info("@ResourceMapping-styleColorOrinNumber--- " + styleColorOrinNumber);
            ColorAttributesVO styleColorAttributes;
            try {
                styleColorAttributes = contentDelegate.getStyleColorAttributes(styleColorOrinNumber);

                if (styleColorAttributes != null) {
                    styleColorAttributes.setStyleColorOrinNumber(styleColorOrinNumber);
                    final Gson gson = new Gson();
                    final JsonObject jsonObject = new JsonObject();
                    final JsonElement styleColorAttributeObject = gson.toJsonTree(styleColorAttributes);
                    jsonObject.add("styleColorObjectInfo",styleColorAttributeObject);
                    System.out.println("styleColorObjectInfofrom json--->" + jsonObject.toString());
                    try {
                        response.getWriter().write(jsonObject.toString());

                    }
                    catch (final IOException e) {

                        e.printStackTrace();
                    }
                    LOGGER.info("inside get getStyleColorAttributes");

                    return null;
                }
                else
                {
                    ColorAttributesVO styleColorAttributeObject = new ColorAttributesVO();
                    styleColorAttributeObject.setMessage("No Style Color Attribute Data Exists....");
                    final Gson gson = new Gson();
                    final JsonObject jsonObject = new JsonObject();
                    final JsonElement noStyleColorAttributesData = gson.toJsonTree(styleColorAttributeObject);
                    jsonObject.add("styleColorObjectInfo",noStyleColorAttributesData);

                }
            }

            catch (final PEPDelegateException e1) {

                e1.printStackTrace();
            }

            LOGGER.info("Exiting getStyleColorAttributes method");

        }
        return modelAndView;

    }

    /*    public void handleActionRequest(ActionRequest request,
        ActionResponse response)  {
        String categoryId=request.getParameter("dynamicCategoryId");

        LOGGER.info("-----------------categoryId ------handleActionRequest----------"+categoryId);

    }
     */
    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.EventAwareController#handleEventRequest(javax.portlet.EventRequest, javax.portlet.EventResponse)
     */
    @Override
    @EventMapping
    public void handleEventRequest(EventRequest request, EventResponse response)
            throws Exception {
        LOGGER.info("-----------------Start of handle handleEventRequest-----------------");
        final Event event = request.getEvent();
        LOGGER.info("event... " + event);
        if (event.getName() != null) {
            if (event.getName().equals(
                ContentScreenConstants.CONTENT_PET_DETAILS)) {

                final ContentPetDetails contentPetDetails =(ContentPetDetails) event.getValue();
                LOGGER.info("event.getName() 1 " + event.getName());
                LOGGER.info("event.value() 1 " + event.getValue());
                LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getContentStatus"+contentPetDetails.getContentStatus());
                LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getContentStatus"+contentPetDetails.getContentStatus());
                LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getOrinNumber"+contentPetDetails.getOrinNumber());
                LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getVpUser"+contentPetDetails.getVpUser());
                LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getUserData"+contentPetDetails.getUserData());
                LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getBelkUser"+contentPetDetails.getBelkUser());
                request.getPortletSession().setAttribute(ContentScreenConstants.CONTENT_PET_DETAILS,contentPetDetails);
            }

        }

        LOGGER.info("-----------------End of handle handleEventRequest-----------------");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.portlet.mvc.Controller#handleRenderRequest(javax
     * .portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @RenderMapping
    public ModelAndView handleRenderRequest(RenderRequest request,
        RenderResponse response) throws Exception
    {
        LOGGER.info("-----------------Start of handleRenderRequest-----------------");

        String roleNameFromIPC=null;
        String orinNumber=null;
        String orinNumberFromIPC=null;
        String pepUserID = null;
        String lanIdFromIPC=null;
        String pepUserIdFromIPC=null;

        final ContentPetDetails contentPetDetailsFromIPC= getContentPetDetailsFromIPC(request);
        LOGGER.info("contentPetDetailsFromIPC----"+contentPetDetailsFromIPC);
        if(contentPetDetailsFromIPC!=null)
        {
            orinNumberFromIPC=contentPetDetailsFromIPC.getOrinNumber();
            LOGGER.info("orinNumberFromIPC----"+orinNumberFromIPC);
            final String contentStatusFromIPC=contentPetDetailsFromIPC.getContentStatus();
            LOGGER.info("contentStatusFromIPC----"+contentStatusFromIPC);
            roleNameFromIPC= contentPetDetailsFromIPC.getUserData().getRoleName();
            LOGGER.info("roleNameFromIPC----"+roleNameFromIPC);
            final Common_Vpuser externalVendorFromIPC= contentPetDetailsFromIPC.getVpUser();
            LOGGER.info("externalVendorFromIPC----"+externalVendorFromIPC);
            final Common_BelkUser belkUserFromIPC=contentPetDetailsFromIPC.getBelkUser();

            LOGGER.info("belkUserFromIPC----"+belkUserFromIPC);

            if(externalVendorFromIPC!=null)
            {
                pepUserIdFromIPC= externalVendorFromIPC.getPepUserID();
                final String emailIdFromIPC= externalVendorFromIPC.getUserEmailAddress();

                LOGGER.info("pepUserIdFromIPC----"+pepUserIdFromIPC);
                LOGGER.info("emailIdFromIPC----"+emailIdFromIPC);

            }

            if(belkUserFromIPC!=null)
            {
                lanIdFromIPC= belkUserFromIPC.getLanId();

                LOGGER.info("lanIdFromIPC----"+lanIdFromIPC);


            }

            if(contentPetDetailsFromIPC.getUserData().isInternal()){
                pepUserID = lanIdFromIPC;
                if ((pepUserID == null) || pepUserID.equalsIgnoreCase("") ){
                    pepUserID = contentPetDetailsFromIPC.getUserData().getBelkUser().getLanId();
                }
            }if(contentPetDetailsFromIPC.getUserData().isExternal()){
                pepUserID = pepUserIdFromIPC;
                if ((pepUserID == null) || pepUserID.equalsIgnoreCase("") ){
                    pepUserID= contentPetDetailsFromIPC.getUserData().getVpUser().getPepUserID();
                }
            }

            LOGGER.info("SRI----"+pepUserID);
        }

        // String orinNumber = "'249041001'";
        if(StringUtils.isNotBlank(orinNumberFromIPC)){
            orinNumber="'"+orinNumberFromIPC+"'";
        }

        contentDisplayForm =new ContentForm();

        //get the logged in user  role ,the logged in user role  can be  dca role ,vendor role  or read only role

        if(StringUtils.isNotBlank(roleNameFromIPC))
        {
            //Set the role Name of the logged in User  from the IPC
            contentDisplayForm.setRoleName(roleNameFromIPC);
        }

        //Set PEP User Id
        contentDisplayForm.setPepUserId(pepUserID);

        modelAndView= new ModelAndView(ContentScreenConstants.PAGE);
        //Logic for getting the Style Information and displaying the Style Information  starts here
        displayStyleInformation(orinNumber,contentDisplayForm,request,response);

        //Logic for getting the Product Information and displaying the Product Information  starts here
        displayProductInformation(orinNumber,contentDisplayForm,request,response);

        //Logic for getting the Pet List and Displaying the Pets starts here
        getPetList(orinNumber,contentDisplayForm,request,response);

        //Logic for displaying  the Style Color Attribute data on click of the Style Color Orin Number starts here

        displayStyleAttributeData(contentDisplayForm,request,response);

        //Logic for checking  the webservice response starts here
        checkWebserviceResponse();

        //Logic for getting the content management data and displaying the content management data  starts here
        displayContentManagement(orinNumber,contentDisplayForm,request,response);

        //Logic for getting the  SKU data and displaying the SKU data   starts here

        displaySKUData(orinNumber,contentDisplayForm,request,response);

        //Logic for getting the  Content History data and displaying the Content History   starts here

        displayContentHistoryData(orinNumber,contentDisplayForm,request,response);


        //Logic for getting the  Copy Attribute data data and displaying the  Copy Attribute data  starts here
        displayCopyAttributeData(orinNumber,contentDisplayForm,request,response);

        //Get the IPH Categories to display on the  screen;
        displayIPHCategories(orinNumber,contentDisplayForm,request,response);

        //Display the message for the Save Style Attributes webservice  on  click of the Save Button
        displaySaveStyleAttributeWebserviceMessage(request,contentDisplayForm);

        //Display the message for the Update Content Status webservice  on  click of the Style Data Submit Button
        displayUpdateStyleStatusWebserviceMessage(request,contentDisplayForm);


        //Display the message for the Update Content Status webservice  on  click of the Style Color Data Submit Button
        displayUpdateStyleColorStatusWebserviceMessage(request,contentDisplayForm);


        //Display message to the User for submitting  the Style Level Data followed by submitting the Style Color Data"
        displayTheMessageToSubmitStyleLevelDataBeforeSubmittingStyleColorData(request,contentDisplayForm,styleDataSubmissionFlag);

        //DisableSaveButton
        disableSaveButton(request,contentDisplayForm,disableSaveButtonFlag);

        //Set the Ready for Review Status for Style
        setReadyForReviewStatusForStyle(request,contentDisplayForm);


        //Set the Ready for Review Status for Style Color
        setReadyForReviewStatusForStyleColor(request,contentDisplayForm);

        //Logic for displaying  the Style Color Attribute data on click of the Style Color Orin Number starts here

        displayStyleColorAttributeData(contentDisplayForm,request,response);


        //Logic for displaying the Category Specific Attributes

        displayCategorySpecificAttributeData(contentDisplayForm,request,response, orinNumber);


        modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

        LOGGER.info("-----------------End of handleRenderRequest-----------------");
        return modelAndView;
    }
    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.ResourceAwareController#handleResourceRequest(javax.portlet.ResourceRequest, javax.portlet.ResourceResponse)
     */
    @SuppressWarnings("null")
    @Override
    public ModelAndView handleResourceRequest(ResourceRequest request,ResourceResponse response) throws Exception {

        String webServiceMessage = null;
        modelAndView = new ModelAndView(ContentScreenConstants.PAGE);
        contentDisplayForm = new ContentForm();
        final String categoryKey = request.getParameter("categoryKey");
        LOGGER.info("categoryKey-------" + categoryKey);
        if (StringUtils.isNotBlank(categoryKey)) {
            LOGGER.info("@ResourceMapping-categoryKey--- " + categoryKey);
            LOGGER.info("categoryKey---- " + categoryKey);
            final String petId = request.getParameter("petIdForWebservice");
            LOGGER.info("petId--------" + petId);
            final DataObject dataObject = new DataObject(petId, categoryKey);
            final Gson gson = new Gson();
            // convert java object to JSON format,
            // and returned as JSON formatted string
            final String json = gson.toJson(dataObject);
            webServiceMessage = contentDelegate.callIPHMappingWebService(json);
            final PrintWriter writer = response.getWriter();
            // to send the response to ajax call from jsp
            writer.write(webServiceMessage);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM,contentDisplayForm);

        }


        return modelAndView;
    }

    @InitBinder
    public final void initBinder(final PortletRequest request,
        final PortletRequestDataBinder binder) throws Exception {
    }




    /**
     * Checks if is disable save button flag.
     *
     * @return the disableSaveButtonFlag
     */
    public boolean isDisableSaveButtonFlag() {
        return disableSaveButtonFlag;
    }


    /**
     * Checks if is style data submission flag.
     *
     * @return the styleDataSubmissionFlag
     */
    public boolean isStyleDataSubmissionFlag() {
        return styleDataSubmissionFlag;
    }




    /**
     * Save content pet attributes.
     *
     * @param request the request
     * @param response the response
     */
    @ResourceMapping("saveContentPetAttributes")
    private void saveContentPetAttributes(ResourceRequest request,ResourceResponse response) {
        LOGGER.info("start of saveContentPetAttributes...");

        final String stylePetId = request.getParameter("stylePetOrinNumber");
        List<AttributesBean> beanList = null;
        String dropValue = request.getParameter("dropdownhidden_id1");
        final ItemIdBean listOfDropAttributes = new ItemIdBean();
        if((dropValue != null) && StringUtils.isNotEmpty(dropValue) ){
            AttributesBean attributesBeanProductName = null;
            beanList = new ArrayList<AttributesBean>();
            String dropDwonAttName[] = dropValue.split("~");

            if(dropDwonAttName != null){
                for(int i=0; i<dropDwonAttName.length; i++){

                    String innerValue = dropDwonAttName[i];
                    if(innerValue != null){
                        String values[] = innerValue.split("#");
                        // row[3]!=null?row[3].toString():null
                        if((values!=null) && (values.length>0))
                        {


                            String attributeName=values[0]!=null?values[0]:null;
                            String attributeXPath=values[1]!=null?values[1]:null;

                            System.out.println("Attribute Name "+attributeName);
                            System.out.println("Attribute Path "+attributeXPath);
                            String userSelected=values[2]!=null?values[2]:null;
                            System.out.println("Usere Selected Values "+userSelected);
                            attributesBeanProductName = new AttributesBean(attributeXPath,userSelected);
                            beanList.add(attributesBeanProductName);
                            /*  if(userSelected != null){
                            String selected[] = userSelected.split(",");
                            if(selected != null){
                                for(int j=0; j<selected.length; j++){
                                    System.out.println("VAlues "+selected[j]);
                                }
                            }
                        }*/
                        }

                    }
                }

            }
        }

        listOfDropAttributes.setItemId(stylePetId);
        listOfDropAttributes.setList(beanList);

        if((listOfDropAttributes.getList()!=null) && (listOfDropAttributes.getList().size()>0))
        {
            LOGGER.info("listOfDropAttributes json Request to createContentWebService = ");
            final Gson gson = new Gson();
            //convert from JSON to String
            final String createContentWebServiceReq = gson.toJson(listOfDropAttributes);

            //request to web service
            LOGGER.info("listOfDropAttributes Request to createContentWebService = "+createContentWebServiceReq);
            //call web service and read response
            final String webserviceResponseMessage=contentDelegate.createContentWebService(createContentWebServiceReq);
            final WebserviceResponse webserviceResponse = new WebserviceResponse();
            webserviceResponse.setMessage(webserviceResponseMessage);
            final JsonObject jsonObject = new JsonObject();
            final JsonElement webserviceResponseObject = gson.toJsonTree(webserviceResponse);
            jsonObject.add("responseObject",webserviceResponseObject);
            LOGGER.info("webserviceResponseObject..... = "+webserviceResponseObject);
            try {
                response.getWriter().write(jsonObject.toString());
            }
            catch (final IOException e) {

                e.printStackTrace();
            }

        }


        final List<AttributesBean> attributesBeansList = new ArrayList<AttributesBean>();

        final String productName = request.getParameter("productNameStyleAttribute");
        final String productDescription = request.getParameter("productDescriptionStyleAttribute");

        final String productNameXpath=ContentScreenConstants.PRODUCT_NAME_XPATH;
        final String productDescriptionXpath=ContentScreenConstants.PRODUCT_DESCRIPTION_XPATH;

        final String brand = request.getParameter("brand");
        //String omniChannelBrandXpath = request.getParameter("omniChannelBrandXpath");
        final String omniChannelBrandXpath = ContentScreenConstants.OMNICHANNEL_BRAND_XPATH;
        final String belkExclusive = request.getParameter("belkExclusive");
        //String belkExclusiveXpath = request.getParameter("belkExclusiveXpath");
        final String belkExclusiveXpath =  ContentScreenConstants.BELK_EXCLUSIVE_XPATH;
        LOGGER.info("user inputs for content update -> productName = "+productName);
        LOGGER.info("user inputs for content update -> productDescription = "+productDescription);

        LOGGER.info("user inputs for content update -> stylePetId = "+stylePetId+" , brand = "+brand+", omniChannelBrandXpath = "+omniChannelBrandXpath+", belkExclusive = "+belkExclusive+", belkExclusiveXpath = "+belkExclusiveXpath);

        //form JSON request to web service
        final ItemIdBean listOfAttributes = new ItemIdBean();



        final AttributesBean attributesBeanProductName = new AttributesBean(productNameXpath,productName);
        final AttributesBean attributesBeanProductDescription = new AttributesBean(productDescriptionXpath,productDescription);

        attributesBeansList.add(attributesBeanProductName);
        attributesBeansList.add(attributesBeanProductDescription);
        if(StringUtils.isNotBlank(brand))
        {
            final AttributesBean attributesBeanBrand = new AttributesBean(omniChannelBrandXpath,brand);
            attributesBeansList.add(attributesBeanBrand);
        }

        if(StringUtils.isNotBlank(belkExclusive))
        {
            final AttributesBean attributesBeanBelkExclusive = new AttributesBean(belkExclusiveXpath,belkExclusive);
            attributesBeansList.add(attributesBeanBelkExclusive);
        }




        listOfAttributes.setList(attributesBeansList);//this will complete JSON request data
        if((listOfAttributes.getList()!=null) && (listOfAttributes.getList().size()>0))
        {
            listOfAttributes.setItemId(stylePetId);
            final Gson gson = new Gson();
            //convert from JSON to String
            final String createContentWebServiceReq = gson.toJson(listOfAttributes);

            //request to web service
            LOGGER.info("Request to createContentWebService = "+createContentWebServiceReq);
            //call web service and read response
            final String webserviceResponseMessage=contentDelegate.createContentWebService(createContentWebServiceReq);
            final WebserviceResponse webserviceResponse = new WebserviceResponse();
            webserviceResponse.setMessage(webserviceResponseMessage);
            final JsonObject jsonObject = new JsonObject();
            final JsonElement webserviceResponseObject = gson.toJsonTree(webserviceResponse);
            jsonObject.add("responseObject",webserviceResponseObject);
            LOGGER.info("webserviceResponseObject..... = "+webserviceResponseObject);
            /* try {
                response.getWriter().write(jsonObject.toString());
            }
            catch (final IOException e) {

                e.printStackTrace();
            }*/

        }













    }


    /**
     * Sets the category id from drop down.
     *
     * @param categoryIdFromDropDown the categoryIdFromDropDown to set
     */
    public void setCategoryIdFromDropDown(String categoryIdFromDropDown) {
        this.categoryIdFromDropDown = categoryIdFromDropDown;
    }

    /**
     * Sets the content delegate.
     *
     * @param contentDelegate the new content delegate
     */
    public void setContentDelegate(ContentDelegate contentDelegate) {
        this.contentDelegate = contentDelegate;
    }

    /**
     * Sets the content display form.
     *
     * @param contentDisplayForm the contentDisplayForm to set
     */
    public void setContentDisplayForm(ContentForm contentDisplayForm) {
        this.contentDisplayForm = contentDisplayForm;
    }

    /**
     * Sets the disable save button flag.
     *
     * @param disableSaveButtonFlag the disableSaveButtonFlag to set
     */
    public void setDisableSaveButtonFlag(boolean disableSaveButtonFlag) {
        this.disableSaveButtonFlag = disableSaveButtonFlag;
    }

    /**
     * Sets the model and view.
     *
     * @param modelAndView the modelAndView to set
     */
    public void setModelAndView(ModelAndView modelAndView) {
        if(modelAndView==null)
        {
            modelAndView = new ModelAndView(ContentScreenConstants.PAGE);
        }
        this.modelAndView = modelAndView;
    }

    /**
     * Sets the ready for review status for style.
     *
     * @param request the request
     * @param contentDisplayForm the content display form
     */
    private void setReadyForReviewStatusForStyle(RenderRequest request,
        ContentForm contentDisplayForm) {
        final String readyForReviewStatus= request.getParameter(ContentScreenConstants.READY_FOR_REVIEW_MESSAGE_KEY);
        if(StringUtils.isNotBlank(readyForReviewStatus))
        {
            contentDisplayForm.setReadyForReviewMessage(readyForReviewStatus);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }

    }


    /**
     * Sets the ready for review status for style color.
     *
     * @param request the request
     * @param contentDisplayForm the content display form
     */
    private void setReadyForReviewStatusForStyleColor(RenderRequest request,ContentForm contentDisplayForm) {
        final String readyForReviewStatusForStyleColor= request.getParameter(ContentScreenConstants.READY_FOR_REVIEW_STYLE_COLOR_MESSAGE_KEY);
        if(StringUtils.isNotBlank(readyForReviewStatusForStyleColor))
        {
            contentDisplayForm.setReadyForReviewMessageStyleColor(readyForReviewStatusForStyleColor);
            modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
        }

    }


    /**
     * Sets the response message web service.
     *
     * @param responseMessageWebService the responseMessageWebService to set
     */
    public void setResponseMessageWebService(String responseMessageWebService) {
        this.responseMessageWebService = responseMessageWebService;
    }

    /**
     * Sets the style attributes.
     *
     * @param styleAttributes the styleAttributes to set
     */
    public void setStyleAttributes(GlobalAttributesVO styleAttributes) {
        this.styleAttributes = styleAttributes;
    }

    /**
     * Sets the style color attribute.
     *
     * @param styleColorAttribute the styleColorAttribute to set
     */
    public void setStyleColorAttribute(ColorAttributesVO styleColorAttribute) {
        this.styleColorAttribute = styleColorAttribute;
    }

    /**
     * Sets the style data submission flag.
     *
     * @param styleDataSubmissionFlag the styleDataSubmissionFlag to set
     */
    public void setStyleDataSubmissionFlag(boolean styleDataSubmissionFlag) {
        this.styleDataSubmissionFlag = styleDataSubmissionFlag;
    }

    /**
     * Split iph category by category id.
     *
     * @param iphCategoryList the iph category list
     * @return the map
     */
    private Map<String, String> splitIPHCategoryByCategoryId(List<ItemPrimaryHierarchyVO> iphCategoryList) {

        final Map<String,String> categoryMap = new LinkedHashMap<String,String>();
        for(final ItemPrimaryHierarchyVO iphCategory:iphCategoryList)
        {
            final String fullCategoryName=  iphCategory.getCategoryName();

            final int index=fullCategoryName.indexOf('-');//get the first occurence of -
            //LOGGER.info("index.."+index);
            final String categoryId=fullCategoryName.substring(0, index);
            //LOGGER.info("categoryId...."+categoryId);
            final String categoryName= fullCategoryName.substring(index+1);
            //LOGGER.info("categoryName...."+categoryName);
            categoryMap.put(categoryId, categoryName);
        }

        return categoryMap;

    }

    /**
     * Update content pet style color data status.
     *
     * @param request the request
     * @param response the response
     */
    @ResourceMapping("updateContentPetStyleColorDataStatus")
    private void updateContentPetStyleColorDataStatus(ResourceRequest request,ResourceResponse response) {
        LOGGER.info("start of ActionMapping....updateContentPetStyleColorDataStatus...");

        final String  selectedStyleColorOrinNumber= request.getParameter("selectedStyleColorOrinNumber");
        LOGGER.info("selectedStyleColorOrinNumber..."+selectedStyleColorOrinNumber);
        final String  styleColorPetContentStatus=  request.getParameter("styleColorPetContentStatus");
        LOGGER.info("styleColorPetContentStatus..."+styleColorPetContentStatus);
        final String  loggedInUser=  request.getParameter("loggedInUser");
        LOGGER.info("loggedInUser..."+loggedInUser);

        if(StringUtils.isNotBlank(selectedStyleColorOrinNumber) && StringUtils.isNotBlank(styleColorPetContentStatus) && StringUtils.isNotBlank(loggedInUser))
        {
            final UpdateContentStatusDataObject dataObject = new UpdateContentStatusDataObject(selectedStyleColorOrinNumber, styleColorPetContentStatus,loggedInUser);
            final Gson gson = new Gson();
            // convert java object to JSON format,
            // and returned as JSON formatted string
            final String json = gson.toJson(dataObject);
            final String webserviceResponseMessage=contentDelegate.updateContentStatusWebService(json);
            LOGGER.info("webserviceResponseMessage..."+webserviceResponseMessage);
            //Create a new webserviceResponse object
            final WebserviceResponse webserviceResponse = new WebserviceResponse();
            //Set the webserviceResponseMessage in the webserviceResponse object
            webserviceResponse.setMessage(webserviceResponseMessage);
            //Create a new JsonObject
            final JsonObject jsonObject = new JsonObject();
            //Convert the  webserviceResponse object Json Element
            final JsonElement webserviceResponseObject = gson.toJsonTree(webserviceResponse);
            jsonObject.add("responseObject",webserviceResponseObject);
            LOGGER.info("webserviceResponseObject..... = "+webserviceResponseObject);
            if(webserviceResponseMessage.contains("successfully"))
            {
                disableSaveButtonFlag=true;
                try {
                    response.getWriter().write(jsonObject.toString());
                }
                catch (final IOException e) {

                    e.printStackTrace();
                }



                LOGGER.info("webserviceResponseMessag from  updateContentPetStyleColorDataStatus = "+ getResponseMessageWebService());



            }

        }



    }

    /**
     * Update content pet style data status.
     *
     * @param request the request
     * @param response the response
     */
    @ResourceMapping("updateContentPetStyleDataStatus")
    private void updateContentPetStyleDataStatus(ResourceRequest request,ResourceResponse response) {
        LOGGER.info("start of ActionMapping....updateContentPetStyleDataStatus...");

        final String  styleOrinNumber= request.getParameter("selectedOrinNumber");
        LOGGER.info("styleOrinNumber..."+styleOrinNumber);
        final String  styleContentStatus=  request.getParameter("styleContentStatus");
        LOGGER.info("styleContentStatus..."+styleContentStatus);
        final String  loggedInUser=  request.getParameter("loggedInUser");
        LOGGER.info("loggedInUser..."+loggedInUser);

        if(StringUtils.isNotBlank(styleOrinNumber) && StringUtils.isNotBlank(styleContentStatus) && StringUtils.isNotBlank(loggedInUser))
        {
            final UpdateContentStatusDataObject dataObject = new UpdateContentStatusDataObject(styleOrinNumber, styleContentStatus,loggedInUser);
            final Gson gson = new Gson();
            // convert java object to JSON format,
            // and returned as JSON formatted string
            final String json = gson.toJson(dataObject);
            final String webserviceResponseMessage=contentDelegate.updateContentStatusWebService(json);
            LOGGER.info("webserviceResponseMessage..."+webserviceResponseMessage);
            //Create a new webserviceResponse object
            final WebserviceResponse webserviceResponse = new WebserviceResponse();
            //Set the webserviceResponseMessage in the webserviceResponse object
            webserviceResponse.setMessage(webserviceResponseMessage);
            //Create a new JsonObject
            final JsonObject jsonObject = new JsonObject();
            //Convert the  webserviceResponse object Json Element
            final JsonElement webserviceResponseObject = gson.toJsonTree(webserviceResponse);
            jsonObject.add("responseObject",webserviceResponseObject);
            LOGGER.info("webserviceResponseObject..... = "+webserviceResponseObject);
            if(webserviceResponseMessage.contains("successfully"))
            {
                styleDataSubmissionFlag=true;
                disableSaveButtonFlag=true;
                try {
                    response.getWriter().write(jsonObject.toString());
                }
                catch (final IOException e) {

                    e.printStackTrace();
                }

            }


            LOGGER.info("end of ActionMapping....updateContentPetStyleDataStatus.");



        }





    }

}