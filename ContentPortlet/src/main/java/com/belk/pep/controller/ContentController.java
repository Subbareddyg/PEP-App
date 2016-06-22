package com.belk.pep.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.belk.pep.common.userdata.UserData;
import com.belk.pep.constants.ContentScreenConstants;
import com.belk.pep.delegate.ContentDelegate;
import com.belk.pep.exception.checked.PEPDelegateException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.form.ContentForm;
import com.belk.pep.jsonconverter.DataObject;
import com.belk.pep.jsonconverter.GroupDataObject;
import com.belk.pep.jsonconverter.UpdateContentStatusDataObject;
import com.belk.pep.model.GroupsFound;
import com.belk.pep.model.PetsFound;
import com.belk.pep.model.WebserviceResponse;
import com.belk.pep.util.ExtractColorCode;
import com.belk.pep.util.SortComparator;
import com.belk.pep.vo.BlueMartiniAttributesVO;
import com.belk.pep.vo.CarBrandVO;
import com.belk.pep.vo.ChildSkuVO;
import com.belk.pep.vo.ColorAttributesVO;
import com.belk.pep.vo.ContentHistoryVO;
import com.belk.pep.vo.ContentManagementVO;
import com.belk.pep.vo.CopyAttributeVO;
import com.belk.pep.vo.CopyAttributesVO;
import com.belk.pep.vo.GlobalAttributesVO;
import com.belk.pep.vo.GroupingVO;
import com.belk.pep.vo.IPHMappingVO;
import com.belk.pep.vo.ItemPrimaryHierarchyVO;
import com.belk.pep.vo.LegacyAttributesVO;
import com.belk.pep.vo.OmniChannelBrandVO;
import com.belk.pep.vo.PetAttributeVO;
import com.belk.pep.vo.ProductAttributesVO;
import com.belk.pep.vo.ProductDetailsVO;
import com.belk.pep.vo.SkuAttributesVO;
import com.belk.pep.vo.SkuVO;
import com.belk.pep.vo.StyleAndItsChildDisplay;
import com.belk.pep.vo.StyleColorFamilyVO;
import com.belk.pep.vo.StyleColorVO;
import com.belk.pep.vo.StyleInformationVO;
import com.belk.pep.vo.StyleVO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/** The Class ContentController. */
@Controller
@RequestMapping("VIEW")
public class ContentController implements ResourceAwareController, EventAwareController, PortletSession {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(ContentController.class.getName());

	/** The content delegate. */
	private ContentDelegate contentDelegate;
	/** The content form. */
	// private ContentForm contentDisplayForm;

	/** The style attributes. */
	private GlobalAttributesVO styleAttributes;
	/** The style color attribute. */
	private ColorAttributesVO styleColorAttribute;

	/** The response message web service. */
	private String responseMessageWebService = "";
	/** The model and view. */
	private ModelAndView modelAndView = null;

	/** The style data submission flag. */
	private boolean styleDataSubmissionFlag;

	/** The disable save button flag. */
	private boolean disableSaveButtonFlag;

	/** The category id from drop down. */
	private String categoryIdFromDropDown;

	/** Check webservice response. */
	private void checkWebserviceResponse() {
		// Check the response from the webservice
		if (getResponseMessageWebService().contains("successfully")) {
			LOGGER.info("getResponseMessageWebService---successfully");

			modelAndView.addObject("responseSuccessMessageWebService", getResponseMessageWebService());
		} else if (getResponseMessageWebService().contains("updation failed")) {
			LOGGER.info("getResponseMessageWebService---updation failed");
			modelAndView.addObject("responseSuccessMessageWebService", getResponseMessageWebService());
		} else if (getResponseMessageWebService().contains("HTTP Error")) {
			LOGGER.info("getResponseMessageWebService---HTTP Error");
			modelAndView.addObject("responseFailMessageWebService", getResponseMessageWebService());
		}

	}

	/** Display attributes on change.
	 * 
	 * @param request the request
	 * @param response the response
	 * @param contentDisplayForm2 the content display form2
	 * @param orinNumber the orin number */
	private void displayAttributesOnChange(RenderRequest request, RenderResponse response, ContentForm contentDisplayForm2,
			String orinNumber, String dynamicCategoryId) {

		LOGGER.info("called displayAttributesOnChange method");
		// Logic for displaying the Category Specific Attributes
		// String dynamicCategoryId = null;
		// if(request.getAttribute("dynamicCategoryId")!=null){
		// dynamicCategoryId =(String)
		// request.getAttribute("dynamicCategoryId");

		// Start of Logic for IPH Mapping
		LOGGER.info("dynamicCategoryId latest--------------- " + dynamicCategoryId);
		String iphMappingFlag = iphMappingResponse(orinNumber, dynamicCategoryId, "false");
		LOGGER.info("Latest ....--------------- " + iphMappingFlag);

		// LOGGER.info("iphMappingFlag --------------- "+iphMappingFlag);

		if (iphMappingFlag.trim().equalsIgnoreCase("true")) {
			// Logic for getting the grand parent category id,parent category
			// id,child category id from ADSE_ITEM_PRIMARY_HIERARCHY Table

			List<ItemPrimaryHierarchyVO> familyCategoryList = getFamilyTreeCategory(dynamicCategoryId);

			LOGGER.info("familyCategoryList --------------- " + familyCategoryList.size());
			String finalCatIds = "";
			if ((familyCategoryList != null) && (familyCategoryList.size() > 0)) {
				int i = 1;
				for (ItemPrimaryHierarchyVO iph : familyCategoryList) {

					String categoryId = iph.getCategoryId();
					if (i == 1) {
						finalCatIds = categoryId;
					} else {
						finalCatIds = finalCatIds + "," + categoryId;
					}
					i++;
				}
			}

			// Pass the multiple category Ids obtained from
			// ADSE_ITEM_PRIMARY_HIERARCHY Table to display all the grand parent
			// attributes,parent attributes and child attributes
			LOGGER.info("dynamicCategoryId OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO " + finalCatIds);

			// LOGGER.info("dynamicCategoryId OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO "+dynamicCategoryId
			// );
			displayCategorySpecificAttributeData(finalCatIds, contentDisplayForm2, request, response, orinNumber);

			displayBlueMartiniSpecificAttributeData(finalCatIds, contentDisplayForm2, request, response, orinNumber);
			request.setAttribute("selectedCategory", dynamicCategoryId);

			callAsyncIphMappingResponse(orinNumber, dynamicCategoryId, "true");

		} else {
			request.setAttribute("selectedCategory", "select");
			contentDisplayForm2
					.setIphMappingMessage("IPH selection was not saved successfully. Please try again, if the problem still persists, please contact Belk helpdesk.");
		}

		LOGGER.info("end displayAttributesOnChange method");

	}

	/** Display attributes on load.
	 * 
	 * @param request the request
	 * @param response the response
	 * @param contentDisplayForm2 the content display form2
	 * @param orinNumber the orin number */
	private void displayAttributesOnLoad(RenderRequest request, RenderResponse response, ContentForm contentDisplayForm2,
			String orinNumber, String mappedCategoryId) {

		// Logic for getting the grand parent category id,parent category
		// id,child category id from ADSE_ITEM_PRIMARY_HIERARCHY Table
		LOGGER.info("petCategoryId --------------- " + mappedCategoryId);
		List<ItemPrimaryHierarchyVO> familyCategoryList = getFamilyTreeCategory(mappedCategoryId);

		String finalCatIds = "";
		if ((familyCategoryList != null) && (familyCategoryList.size() > 0)) {
			LOGGER.info("familyCategoryList --------------- " + familyCategoryList.size());
			int i = 1;
			for (ItemPrimaryHierarchyVO iph : familyCategoryList) {

				String categoryId = iph.getCategoryId();
				if (i == 1) {
					finalCatIds = categoryId;
				} else {
					finalCatIds = finalCatIds + "," + categoryId;
				}
				i++;
			}
			// LOGGER.info("finalCatIds OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO------ "+finalCatIds
			// );
		}

		// Pass the multiple category Ids obtained from
		// ADSE_ITEM_PRIMARY_HIERARCHY Table to display all the grand parent
		// attributes,parent attributes and child attributes
		LOGGER.info("petCategoryId finalCatIdsOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO " + finalCatIds);

		// LOGGER.info("petCategoryId OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO "+mappedCategoryId
		// );
		displayCategorySpecificAttributeData(finalCatIds, contentDisplayForm2, request, response, orinNumber);

		displayBlueMartiniSpecificAttributeData(finalCatIds, contentDisplayForm2, request, response, orinNumber);

		LOGGER.info("end of  displayAttributesOnLoad--------------- ");

	}

	/** Display blue martini specific attribute data.
	 * 
	 * @param contentDisplayForm2 the content display form2
	 * @param request the request
	 * @param response the response
	 * @param orinNumber the orin number */
	private void displayBlueMartiniSpecificAttributeData(String dynamicCategoryId, ContentForm contentDisplayForm2,
			RenderRequest request, RenderResponse response, String orinNumber) {
		LOGGER.info("start of BlueMartiniSpecificAttributeData......");
		List<BlueMartiniAttributesVO> blueMartiniSpecificAttributesObjectList = null;
		List<String> dropDownValueList = null;

		List<String> radioButtonValueList = null;

		List<String> savedDropDownValueList = null;

		String categoryId = null;

		LegacyAttributesVO legacyAttributesDisplay = new LegacyAttributesVO();

		final List<BlueMartiniAttributesVO> blueMartiniSpecificAttributesObjectDropDownList = new ArrayList<BlueMartiniAttributesVO>();

		final List<BlueMartiniAttributesVO> dropDownList = new ArrayList<BlueMartiniAttributesVO>();

		final List<BlueMartiniAttributesVO> radioButtonList = new ArrayList<BlueMartiniAttributesVO>();

		final List<BlueMartiniAttributesVO> blueMartiniSpecificAttributesObjectTextFieldList = new ArrayList<BlueMartiniAttributesVO>();

		final List<BlueMartiniAttributesVO> blueMartiniSpecificAttributesObjectRadioButtonList = new ArrayList<BlueMartiniAttributesVO>();

		final List<BlueMartiniAttributesVO> blueMartiniSpecificAttributesObjectCheckBoxList = new ArrayList<BlueMartiniAttributesVO>();
		// Code for alphabetical order sorting added by AFUDXK7 -START
		final List<BlueMartiniAttributesVO> blueMartiniSpecificAttributesObjectAllList = new ArrayList<BlueMartiniAttributesVO>();
		// Code for alphabetical order sorting added by AFUDXK7 -END
		try {
			// String categoryId=request.getParameter("dynamicCategoryId");
			Map<String, String> categoryReferenceData = contentDisplayForm2.getCategoryReferenceData();

			for (Map.Entry<String, String> entry : categoryReferenceData.entrySet()) {
				LOGGER.info("Key : " + entry.getKey() + " Value : " + entry.getValue());
				categoryId = entry.getKey();
			}

			// String dummyCategoryId="4664";

			// String dummyOrinNumber="100354629";
			LOGGER.info("categoryId : " + categoryId);
			LOGGER.info("Style Orin Number : " + orinNumber);
			blueMartiniSpecificAttributesObjectList = contentDelegate
					.getPetBlueMartiniAttributesDetails(dynamicCategoryId, orinNumber);
			LOGGER.info("categorySpecificAttributesObjectList. size...." + blueMartiniSpecificAttributesObjectList.size());
		} catch (final PEPDelegateException e) {

			e.printStackTrace();
		}

		if (blueMartiniSpecificAttributesObjectList != null) {

			for (final BlueMartiniAttributesVO blueMartiniSpecificAttributeObject : blueMartiniSpecificAttributesObjectList) {

				final String attributeFieldType = blueMartiniSpecificAttributeObject.getAttributeFieldType();
				final String attributeName = blueMartiniSpecificAttributeObject.getAttributeName();
				final String attributeFieldValue = blueMartiniSpecificAttributeObject.getAttributeFieldValue();
				String attributeType = blueMartiniSpecificAttributeObject.getAttributeType();
				final String secondarySpecValue = blueMartiniSpecificAttributeObject.getZbmSecondarySpecValue();

				// Need to move to another method
				switch (attributeFieldType) {
				case "Drop Down":
					// Code for Alphabetical order sorting START
					blueMartiniSpecificAttributeObject.setAttributeFieldType(ContentScreenConstants.ATTR_FIELD_DROP_DOWN);
					// Code for Alphabetical order sorting added by -END
					blueMartiniSpecificAttributesObjectDropDownList.add(blueMartiniSpecificAttributeObject);
					break;
				case "Radio Button":
					// Code for Alphabetical order sorting -START
					blueMartiniSpecificAttributeObject.setAttributeFieldType(ContentScreenConstants.ATTR_FIELD_RADIO_BUTTON);
					// Code for Alphabetical order sorting -END
					// blueMartiniSpecificAttributeObject.setAttributeFieldValue(secondarySpecValue);
					blueMartiniSpecificAttributesObjectRadioButtonList.add(blueMartiniSpecificAttributeObject);
					break;
				case "Check Boxes":
					// Code for Alphabetical order sorting -START
					blueMartiniSpecificAttributeObject.setAttributeFieldType(ContentScreenConstants.ATTR_FIELD_CHECK_BOXES);
					// Code for Alphabetical order sorting -END
					blueMartiniSpecificAttributesObjectCheckBoxList.add(blueMartiniSpecificAttributeObject);
					break;
				case "Text Field":
					// Code for Alphabetical order sorting -START
					blueMartiniSpecificAttributeObject.setAttributeFieldType(ContentScreenConstants.ATTR_FIELD_TEXT_FIELD);
					// Code for Alphabetical order sorting -END
					// set the attribute value in text field from pet
					// catalog,secondarySpecValue is the saved value from the
					// pet catalog
					LOGGER.info("BM Text Field Saved Value --------- " + secondarySpecValue);
					if (secondarySpecValue != null) {
						blueMartiniSpecificAttributeObject.setAttributeFieldValue(secondarySpecValue.trim());
					} else {
						blueMartiniSpecificAttributeObject.setAttributeFieldValue(secondarySpecValue);
					}

					blueMartiniSpecificAttributesObjectTextFieldList.add(blueMartiniSpecificAttributeObject);
					break;
				default:
					break;
				}
			}

			// Need to move to another method
			// Logic for collecting all the drop downs to display on the Portal
			// Interface
			if ((blueMartiniSpecificAttributesObjectDropDownList != null)
					&& (blueMartiniSpecificAttributesObjectDropDownList.size() > 0)) {
				for (BlueMartiniAttributesVO petBlueMartiniAttributeDropDownObject : blueMartiniSpecificAttributesObjectDropDownList) {
					String htmlDisplayName = petBlueMartiniAttributeDropDownObject.getDisplayName();
					String secondarySpecificationValue = petBlueMartiniAttributeDropDownObject.getZbmSecondarySpecValue();
					String htmlFieldValue = petBlueMartiniAttributeDropDownObject.getAttributeFieldValue();
					LOGGER.info("BM Drop Down Saved Value --------- " + secondarySpecificationValue);
					// Convert the field values separated by pipe to the hash
					// map of values for displaying in the drop down from the
					// Pet Attribute Table
					Map<String, String> dropdownValueMap = new TreeMap<String, String>(new SortComparator());

					// Code for Alphabetical order sorting added by -START
					List<String> sortedList = new ArrayList<String>();
					// Code for Alphabetical order sorting added by -START
					if (StringUtils.isNotBlank(htmlFieldValue)) {
						htmlFieldValue = htmlFieldValue.replace("|", "~");
						dropDownValueList = Arrays.asList(htmlFieldValue.split("~"));

						// Iterate over the dropDownValueList to put the key
						// value in the Hash Map
						// Code for Alphabetical order sorting added by -START
						for (String dropDownValue : dropDownValueList) {
							// LOGGER.info("dropDownValueList :"+dropDownValue);
							dropDownValue = StringUtils.reverse(dropDownValue).trim();
							dropDownValue = StringUtils.reverse(dropDownValue).trim();
							// LOGGER.info("dropDownValueList :"+dropDownValue);
							sortedList.add(dropDownValue);
						}
						Collections.sort(sortedList);
						// Code for Alphabetical order sorting added by -end
						for (String dropDownValue : sortedList) {
							if (("N/A").equals(dropDownValue.trim())) {
								dropdownValueMap.put("0", dropDownValue);
							} else {
								dropdownValueMap.put(dropDownValue, dropDownValue);
							}
						}
						petBlueMartiniAttributeDropDownObject.setDropDownValuesMap(dropdownValueMap);// uncommented
																										// for
																										// now
																										// to
																										// display
																										// the
																										// value
																										// of
																										// data
																										// saved
																										// value

					}

					// Convert the saved field values separated by pipe to the
					// hash map of values for displaying in the drop down from
					// the Pet Catalog Table
					Map<String, String> savedDropDownValueMap = new ConcurrentHashMap<String, String>();
					if (StringUtils.isNotBlank(secondarySpecificationValue)) {
						secondarySpecificationValue = secondarySpecificationValue.replace("|", ",");
						savedDropDownValueList = Arrays.asList(secondarySpecificationValue.split(","));
						// Iterate over the savedDropDownValueList to put the
						// key value in the Hash Map
						for (String savedDropDownValue : savedDropDownValueList) {
							if (savedDropDownValue.indexOf('"') > 0) {
								savedDropDownValue = savedDropDownValue.replace("\"", "&quot;");
							}
							if (savedDropDownValue.trim().equals("N/A")) {
								savedDropDownValueMap.put("0", savedDropDownValue.trim());
							} else {
								savedDropDownValueMap.put(savedDropDownValue.trim(), savedDropDownValue.trim());
							}
						}
						// LOGGER.info("dropdownValueMap---size-------"
						// +savedDropDownValueMap.size());
						petBlueMartiniAttributeDropDownObject.setSavedDropDownValuesMap(savedDropDownValueMap);
					}
					// Me writing the logic
					// LOGGER.info("dropdownValueMap---size-------"
					// +dropdownValueMap.size());
					// LOGGER.info("petAttributeDropDownObject.getDropDownValuesMap()----------"
					// +petBlueMartiniAttributeDropDownObject.getDropDownValuesMap());
					dropDownList.add(petBlueMartiniAttributeDropDownObject);

				}
				legacyAttributesDisplay.setDropDownList(dropDownList);
				// Code for Alphabetical order sorting added by -START
				blueMartiniSpecificAttributesObjectAllList.addAll(dropDownList);
				// Code for Alphabetical order sorting added by -START

			}

			if ((blueMartiniSpecificAttributesObjectCheckBoxList != null)
					&& (blueMartiniSpecificAttributesObjectCheckBoxList.size() > 0)) {

				legacyAttributesDisplay.setCheckboxList(blueMartiniSpecificAttributesObjectCheckBoxList);
				// Code for Alphabetical order sorting -START
				blueMartiniSpecificAttributesObjectAllList.addAll(blueMartiniSpecificAttributesObjectCheckBoxList);
				// Code for Alphabetical order sorting -END
			}

			// Radio Button
			if ((blueMartiniSpecificAttributesObjectRadioButtonList != null)
					&& (blueMartiniSpecificAttributesObjectRadioButtonList.size() > 0)) {
				for (BlueMartiniAttributesVO petAttributeDropDownObject : blueMartiniSpecificAttributesObjectRadioButtonList) {
					String htmlDisplayName = petAttributeDropDownObject.getDisplayName();
					String secondarySpecificationValue = petAttributeDropDownObject.getZbmSecondarySpecValue();
					String htmlFieldValue = petAttributeDropDownObject.getAttributeFieldValue();
					LOGGER.info("BM Radio Saved Value --------- " + secondarySpecificationValue);
					// Convert the field values separated by pipe to the hash
					// map of values for displaying in the form of radio button
					// from the Pet Attribute Table
					Map<String, String> radioButtonValueMap = new ConcurrentHashMap<String, String>();

					if (StringUtils.isNotBlank(htmlFieldValue)) {
						htmlFieldValue = htmlFieldValue.replace("|", "~");
						radioButtonValueList = Arrays.asList(htmlFieldValue.split("~"));

						// Iterate over the dropDownValueList to put the key
						// value in the Hash Map
						for (String radioButtonValue : radioButtonValueList) {
							if (radioButtonValue != null && !radioButtonValue.trim().equalsIgnoreCase("")) {
								radioButtonValueMap.put(radioButtonValue.trim(), radioButtonValue.trim());
							}
						}
						petAttributeDropDownObject.setRadioButtonValuesMap(radioButtonValueMap);
					}

					// Convert the saved field values separated by pipe to the
					// hash map of values for displaying in the radio button
					// value from the Pet Catalog Table
					Map<String, String> savedRadioButtonValueMap = new ConcurrentHashMap<String, String>();

					if (StringUtils.isNotBlank(secondarySpecificationValue)) {
						savedRadioButtonValueMap.put(secondarySpecificationValue.trim(), secondarySpecificationValue.trim());
						petAttributeDropDownObject.setSavedRadioButtonValuesMap(savedRadioButtonValueMap);
					}

					// Me writing the logic
					if (radioButtonValueMap != null) {
						// LOGGER.info("bm radioButtonValueMap---size-------"
						// +radioButtonValueMap.size());
						// LOGGER.info("petAttributeDropDownObject.radioButtonValueMap()----------"
						// +petAttributeDropDownObject.getRadioButtonValuesMap());
					}
					radioButtonList.add(petAttributeDropDownObject);
				}

				legacyAttributesDisplay.setRadiobuttonList(radioButtonList);
				// Code for Alphabetical order sorting -START
				blueMartiniSpecificAttributesObjectAllList.addAll(radioButtonList);
				// Code for Alphabetical order sorting -END
			}

			if ((blueMartiniSpecificAttributesObjectTextFieldList != null)
					&& (blueMartiniSpecificAttributesObjectTextFieldList.size() > 0)) {
				legacyAttributesDisplay.setTextFieldList(blueMartiniSpecificAttributesObjectTextFieldList);
				blueMartiniSpecificAttributesObjectAllList.addAll(blueMartiniSpecificAttributesObjectTextFieldList);
			}

			// Code for Alphabetical order sorting -START
			Collections.sort(blueMartiniSpecificAttributesObjectAllList);
			legacyAttributesDisplay.setDisplayList(blueMartiniSpecificAttributesObjectAllList);
			// Code for Alphabetical order sorting -END
			contentDisplayForm2.setLegacyAttributesDisplay(legacyAttributesDisplay);

			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm2);

		}
		LOGGER.info("End of start of BlueMartiniSpecificAttributeData...... ");
	}

	/** Display category specific attribute data.
	 * 
	 * @param contentDisplayForm2 the content display form2
	 * @param request the request
	 * @param response the response */
	private void displayCategorySpecificAttributeData(String dynamicCategoryId, ContentForm contentDisplayForm2,
			RenderRequest request, RenderResponse response, String orinNumber) {
		LOGGER.info("start of displayCategorySpecificAttributeData......");
		List<PetAttributeVO> categorySpecificAttributesObjectList = null;
		List<String> dropDownValueList = null;

		List<String> radioButtonValueList = null;
		List<String> savedDropDownValueList = null;
		List<String> savedRadioButtonValueList = null;

		String categoryId = null;

		ProductAttributesVO productAttributesDisplay = new ProductAttributesVO();

		final List<PetAttributeVO> categorySpecificAttributesObjectDropDownList = new ArrayList<PetAttributeVO>();

		final List<PetAttributeVO> dropDownList = new ArrayList<PetAttributeVO>();

		final List<PetAttributeVO> radioButtonList = new ArrayList<PetAttributeVO>();

		final List<PetAttributeVO> categorySpecificAttributesObjectTextFieldList = new ArrayList<PetAttributeVO>();

		final List<PetAttributeVO> categorySpecificAttributesObjectRadioButtonList = new ArrayList<PetAttributeVO>();

		final List<PetAttributeVO> categorySpecificAttributesObjectCheckBoxList = new ArrayList<PetAttributeVO>();
		// Code for Alphabetical order sorting -START
		final List<PetAttributeVO> categorySpecificAttributesObjectAllList = new ArrayList<PetAttributeVO>();
		// Code for Alphabetical order sorting -END
		try {
			// String categoryId=request.getParameter("dynamicCategoryId");
			Map<String, String> categoryReferenceData = contentDisplayForm2.getCategoryReferenceData();

			for (Map.Entry<String, String> entry : categoryReferenceData.entrySet()) {
				LOGGER.info("Key : " + entry.getKey() + " Value : " + entry.getValue());

				categoryId = entry.getKey();

			}
			LOGGER.info("categoryId : " + categoryId);
			LOGGER.info("Style Orin Number : " + orinNumber);
			// String dummyCategoryId="4664";

			// String dummyOrinNumber="100354629";
			LOGGER.info("categoryId : " + categoryId);
			LOGGER.info("Style Orin Number : " + orinNumber);
			categorySpecificAttributesObjectList = contentDelegate.getPetAttributeDetails(dynamicCategoryId, orinNumber);
			// categorySpecificAttributesObjectList =
			// contentDelegate.getPetAttributeDetails(dummyCategoryId,dummyOrinNumber);
			// LOGGER.info("categorySpecificAttributesObjectList. size...."+categorySpecificAttributesObjectList.size());
		} catch (final PEPDelegateException e) {

			e.printStackTrace();
		}

		if (categorySpecificAttributesObjectList != null) {

			for (final PetAttributeVO categorySpecificAttributeObject : categorySpecificAttributesObjectList) {

				final String attributeFieldType = categorySpecificAttributeObject.getAttributeFieldType();
				// LOGGER.info("attributeFieldType..."+attributeFieldType);
				final String attributeName = categorySpecificAttributeObject.getAttributeName();
				// LOGGER.info("attributeName..."+attributeName);
				final String attributeFieldValue = categorySpecificAttributeObject.getAttributeFieldValue();
				// LOGGER.info("attributeFieldValue..."+attributeFieldValue);
				String attributeType = categorySpecificAttributeObject.getAttributeType();
				// LOGGER.info("attributeType..."+attributeType);
				final String secondarySpecValue = categorySpecificAttributeObject.getSecondarySpecValue();
				// LOGGER.info("Saved Value ..."+secondarySpecValue);

				/* final Map<String, List<?>> mapOfDisplayFields
				 * =categorySpecificAttributeObject.getMapOfDisplayFields();
				 * //loop a Map for (final Map.Entry<String, List<?>> entry :
				 * mapOfDisplayFields.entrySet()) { LOGGER.info("Key : " +
				 * entry.getKey() + " Value : " + entry.getValue()); } */

				// Need to move to another method
				if (attributeType.equals("PIM")) {
					switch (attributeFieldType) {
					case "Drop Down":
						// Code for Alphabetical order sorting -START
						categorySpecificAttributeObject.setAttributeFieldType(ContentScreenConstants.ATTR_FIELD_DROP_DOWN);
						// Code for Alphabetical order sorting -END
						categorySpecificAttributesObjectDropDownList.add(categorySpecificAttributeObject);
						break;
					case "Radio Button":
						// Code for Alphabetical order sorting -START
						categorySpecificAttributeObject.setAttributeFieldType(ContentScreenConstants.ATTR_FIELD_RADIO_BUTTON);
						// Code for Alphabetical order sorting -END
						categorySpecificAttributesObjectRadioButtonList.add(categorySpecificAttributeObject);
						break;
					case "Check Boxes":
						// Code for Alphabetical order sorting -START
						categorySpecificAttributeObject.setAttributeFieldType(ContentScreenConstants.ATTR_FIELD_CHECK_BOXES);
						// Code for Alphabetical order sorting -END
						categorySpecificAttributesObjectCheckBoxList.add(categorySpecificAttributeObject);
						break;
					case "Text Field":
						// Code for Alphabetical order sorting -START
						categorySpecificAttributeObject.setAttributeFieldType(ContentScreenConstants.ATTR_FIELD_TEXT_FIELD);
						// Code for Alphabetical order sorting -END
						if (secondarySpecValue != null) {
							categorySpecificAttributeObject.setAttributeFieldValue(secondarySpecValue.trim());
						} else {
							categorySpecificAttributeObject.setAttributeFieldValue(secondarySpecValue);
						}
						LOGGER.info("Saved Text Field Value ------" + categorySpecificAttributeObject.getSecondarySpecValue());
						categorySpecificAttributesObjectTextFieldList.add(categorySpecificAttributeObject);

						break;

					default:

						break;
					}
				}
			}

			// Need to move to another method
			// Logic for collecting all the drop downs to display on the Portal
			// Interface
			if ((categorySpecificAttributesObjectDropDownList != null) && (categorySpecificAttributesObjectDropDownList.size() > 0)) {
				for (PetAttributeVO petAttributeDropDownObject : categorySpecificAttributesObjectDropDownList) {
					String htmlDisplayName = petAttributeDropDownObject.getDisplayName();
					String secondarySpecificationValue = petAttributeDropDownObject.getSecondarySpecValue();
					String htmlFieldValue = petAttributeDropDownObject.getAttributeFieldValue();
					LOGGER.info("Saved Drop Down Value --------- " + secondarySpecificationValue);
					// Convert the field values separated by pipe to the hash
					// map of values for displaying in the drop down from the
					// Pet Attribute Table
					Map<String, String> dropdownValueMap = new TreeMap<String, String>(new SortComparator());
					// LOGGER.info("New Code Deployed --------- ");
					// Code for Alphabetical order sorting -START
					List<String> sortedList = new ArrayList<String>();
					// Code for Alphabetical order sorting -END
					if (StringUtils.isNotBlank(htmlFieldValue)) {
						htmlFieldValue = htmlFieldValue.replace("|", "~");
						dropDownValueList = Arrays.asList(htmlFieldValue.split("~"));
						Collections.sort(dropDownValueList);
						// Iterate over the dropDownValueList to put the key
						// value in the Hash Map
						// Code for Alphabetical order sorting -START
						for (String dropDownValue : dropDownValueList) {
							// LOGGER.info("dropDownValueList :"+dropDownValue);
							dropDownValue = StringUtils.reverse(dropDownValue).trim();
							dropDownValue = StringUtils.reverse(dropDownValue).trim();
							// LOGGER.info("dropDownValueList :"+dropDownValue);
							sortedList.add(dropDownValue);
						}
						Collections.sort(sortedList);
						// Code for Alphabetical order sorting -START
						// Iterate over the dropDownValueList to put the key
						// value in the Hash Map
						for (String dropDownValue : sortedList) {
							if (("N/A").equals(dropDownValue.trim())) {
								// dropdownValueMap.remove(dropDownValue);
								dropdownValueMap.put("0", dropDownValue);
							} else {
								dropdownValueMap.put(dropDownValue.trim(), dropDownValue.trim());
							}
						}
						petAttributeDropDownObject.setDropDownValuesMap(dropdownValueMap);// uncommented
																							// for
																							// now
																							// to
																							// display
																							// the
																							// value
																							// of
																							// data
																							// saved
																							// value

					}

					// Convert the saved field values separated by pipe to the
					// hash map of values for displaying in the drop down from
					// the Pet Catalog Table
					Map<String, String> savedDropDownValueMap = new ConcurrentHashMap<String, String>();
					if (StringUtils.isNotBlank(secondarySpecificationValue)) {
						secondarySpecificationValue = secondarySpecificationValue.replace("|", ",");
						savedDropDownValueList = Arrays.asList(secondarySpecificationValue.split(","));

						// Iterate over the savedDropDownValueList to put the
						// key value in the Hash Map
						for (String savedDropDownValue : savedDropDownValueList) {
							if (savedDropDownValue.indexOf('"') > 0) {
								savedDropDownValue = savedDropDownValue.replace("\"", "&quot;");
							}
							if (savedDropDownValue.trim().equals("N/A")) {
								savedDropDownValueMap.put("0", savedDropDownValue.trim());
							} else {
								savedDropDownValueMap.put(savedDropDownValue.trim(), savedDropDownValue.trim());
							}
						}
						petAttributeDropDownObject.setSavedDropDownValuesMap(savedDropDownValueMap);
					}

					// Me writing the logic
					// LOGGER.info("dropdownValueMap---size-------"
					// +dropdownValueMap.size());
					// LOGGER.info("petAttributeDropDownObject.getDropDownValuesMap()----------"
					// +petAttributeDropDownObject.getDropDownValuesMap());
					dropDownList.add(petAttributeDropDownObject);

				}
				productAttributesDisplay.setDropDownList(dropDownList);

				// Code for Alphabetical order sorting -START
				categorySpecificAttributesObjectAllList.addAll(dropDownList);
				// Code for Alphabetical order sorting -END
			}

			if ((categorySpecificAttributesObjectCheckBoxList != null) && (categorySpecificAttributesObjectCheckBoxList.size() > 0)) {

				productAttributesDisplay.setCheckboxList(categorySpecificAttributesObjectCheckBoxList);
				// Code for Alphabetical order sorting -START
				categorySpecificAttributesObjectAllList.addAll(categorySpecificAttributesObjectCheckBoxList);
				// Code for Alphabetical order sorting -END
			}

			if ((categorySpecificAttributesObjectRadioButtonList != null)
					&& (categorySpecificAttributesObjectRadioButtonList.size() > 0)) {
				// Logic for dynamic radio button

				for (PetAttributeVO petAttributeDropDownObject : categorySpecificAttributesObjectRadioButtonList) {
					String htmlDisplayName = petAttributeDropDownObject.getDisplayName();
					String secondarySpecificationValue = petAttributeDropDownObject.getSecondarySpecValue();
					String htmlFieldValue = petAttributeDropDownObject.getAttributeFieldValue();
					LOGGER.info("Saved Radio Value --------- " + secondarySpecificationValue);
					// Convert the field values separated by pipe to the hash
					// map of values for displaying in the form of radio button
					// from the Pet Attribute Table
					Map<String, String> radioButtonValueMap = new ConcurrentHashMap<String, String>();

					if (StringUtils.isNotBlank(htmlFieldValue)) {
						htmlFieldValue = htmlFieldValue.replace("|", "~");
						radioButtonValueList = Arrays.asList(htmlFieldValue.split("~"));

						// Iterate over the dropDownValueList to put the key
						// value in the Hash Map
						for (String radioButtonValue : radioButtonValueList) {
							if (radioButtonValue != null && !radioButtonValue.trim().equalsIgnoreCase("")) {
								radioButtonValueMap.put(radioButtonValue.trim(), radioButtonValue.trim());
							}
						}
						petAttributeDropDownObject.setRadioButtonValuesMap(radioButtonValueMap);
					}

					// Convert the saved field values separated by pipe to the
					// hash map of values for displaying in the radio button
					// value from the Pet Catalog Table
					Map<String, String> savedRadioButtonValueMap = new ConcurrentHashMap<String, String>();

					// System.out.println("secondarySpecificationValue    "+secondarySpecificationValue
					// );
					if (StringUtils.isNotBlank(secondarySpecificationValue)) {
						savedRadioButtonValueMap.put(secondarySpecificationValue.trim(), secondarySpecificationValue.trim());
						petAttributeDropDownObject.setSavedRadioButtonValuesMap(savedRadioButtonValueMap);
					}

					// Me writing the logic
					if (radioButtonValueMap != null) {
						// LOGGER.info("radioButtonValueMap---size-------"
						// +radioButtonValueMap.size());
						// LOGGER.info("petAttributeDropDownObject.radioButtonValueMap()----------"
						// +petAttributeDropDownObject.getRadioButtonValuesMap());
					}
					radioButtonList.add(petAttributeDropDownObject);
				}

				productAttributesDisplay.setRadiobuttonList(radioButtonList);
				// Code for Alphabetical order sorting -START
				categorySpecificAttributesObjectAllList.addAll(radioButtonList);
				// Code for Alphabetical order sorting -END
			}

			if ((categorySpecificAttributesObjectTextFieldList != null) && (categorySpecificAttributesObjectTextFieldList.size() > 0)) {
				productAttributesDisplay.setTextFieldList(categorySpecificAttributesObjectTextFieldList);
				// Code for Alphabetical order sorting -START
				categorySpecificAttributesObjectAllList.addAll(categorySpecificAttributesObjectTextFieldList);
				// Code for Alphabetical order sorting -END
			}

			// Code for Alphabetical order sorting -START
			Collections.sort(categorySpecificAttributesObjectAllList);
			productAttributesDisplay.setDisplayList(categorySpecificAttributesObjectAllList);
			// Code for Alphabetical order sorting -END
			contentDisplayForm2.setProductAttributesDisplay(productAttributesDisplay);

			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm2);

		}
		LOGGER.info("End of displayCategorySpecificAttributeData....  ");
	}

	/** Display color data set.
	 * 
	 * @param contentDisplayForm2 the content display form2
	 * @param request the request
	 * @param response the response */
	private void displayColorDataSet(ContentForm contentDisplayForm2, RenderRequest request, RenderResponse response) {
		List<StyleColorFamilyVO> styleColorList = null;

		try {
			styleColorList = contentDelegate.getColorFamilyDataSet();

		} catch (PEPDelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((styleColorList != null) && (styleColorList.size() > 0)) {
			final Map<String, String> styleColorMap = new LinkedHashMap<String, String>();

			for (final StyleColorFamilyVO styleColor : styleColorList) {
				final String colorCode = styleColor.getSuperColorCode();
				final String code = styleColor.getCode();
				final String colorDescription = styleColor.getSuperColorDesc();
				final boolean colorCodeFlag = StringUtils.isNotBlank(colorCode);
				final boolean codeFlag = StringUtils.isNotBlank(code);
				final boolean colorDescriptionFlag = StringUtils.isNotBlank(colorDescription);
				// if(codeFlag && colorDescriptionFlag)
				{
					styleColorMap.put(code, colorDescription);

				}
				contentDisplayForm2.setColorDataSet(styleColorMap);
			}

			if (request.getAttribute("omniColorFamily") != null) {
				// String omniColorFamily =
				// request.getAttribute("omniColorFamily");
				request.setAttribute("selectedOmniColorFamily", request.getAttribute("omniColorFamily"));

			}

			if (request.getAttribute("secondaryColor1") != null) {
				// String secondaryColor1 =
				// request.getAttribute("secondaryColor1");
				request.setAttribute("selectedSecondaryColor1", request.getAttribute("secondaryColor1"));

			}

			if (request.getAttribute("secondaryColor2") != null) {
				// String secondaryColor2 =
				// request.getParameter("secondaryColor2");
				request.setAttribute("selectedSecondaryColor2", request.getAttribute("secondaryColor2"));

			}

			if (request.getAttribute("secondaryColor3") != null) {
				// String secondaryColor3 =
				// request.getParameter("secondaryColor3");
				request.setAttribute("selectedSecondaryColor3", request.getAttribute("secondaryColor3"));

			}

			if (request.getAttribute("secondaryColor4") != null) {
				// String secondaryColor4 =
				// request.getParameter("secondaryColor4");
				request.setAttribute("selectedSecondaryColor4", request.getAttribute("secondaryColor4"));

			}

			if (request.getAttribute("omniChannelColorDescription") != null) {
				// String omniChannelColorDescription =
				// request.getParameter("omniChannelColorDescription");
				request.setAttribute("selectedOmniChannelColorDescription", request.getAttribute("omniChannelColorDescription"));

			}

			if (request.getAttribute("vendorColor") != null) {
				// String vendorColor = request.getParameter("vendorColor");
				request.setAttribute("selectedVendorColor", request.getAttribute("vendorColor"));

			}

			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm2);

		}

	}

	/** Display content history data.
	 * 
	 * @param orinNumber the orin number
	 * @param contentDisplayForm the content display form
	 * @param request the request
	 * @param response the response */
	private void displayContentHistoryData(String orinNumber, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {

		// Get the Content History from the ADSE tables and prepare the data for
		// displaying on the Content History Section
		List<ContentHistoryVO> contentHistoryLsit = contentDelegate.getContentHistory(orinNumber);
		if (contentHistoryLsit != null) {
			contentDisplayForm.setContentHistoryList(contentHistoryLsit);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} else {
			// set the message to display on the screen No Content History Data
			// Exists
			contentDisplayForm.setContentHistoryData("No Content History data exists");

		}

	}

	/** Display content management.
	 * 
	 * @param orinNumber the orin number
	 * @param contentDisplayForm2 the content display form2
	 * @param request the request
	 * @param response the response */
	private void displayContentManagement(String orinNumber, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		// Get the content management data from the ADSE_PET_CATALOG table and
		// prep the data for displaying on the Pet Content Management Section
		final ContentManagementVO contentManagementVO = contentDelegate.getContentManagmentInfo(orinNumber);
		if (contentManagementVO != null) {
			contentDisplayForm.setContentManagementDisplay(contentManagementVO);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}

	}

	/** Display copy attribute data.
	 * 
	 * @param orinNumber the orin number
	 * @param contentDisplayForm the content display form
	 * @param request the request
	 * @param response the response */
	private void displayCopyAttributeData(String orinNumber, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		// Get the Copy Attribute data from the ADSE tables and prepare the data
		// for displaying on the Content Copy Attribute Section
		final List<CopyAttributesVO> copyAttributeList = contentDelegate.getCopyAttributes(orinNumber);
		if ((copyAttributeList != null) && (copyAttributeList.size() > 0)) {
			contentDisplayForm.setCopyAttributeDisplayList(copyAttributeList);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} else {
			// set the message to display on the screen no copy attribute data
			// exists
			contentDisplayForm.setCopyAttribueData("No Copy Attrbute Data exists");
			;
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}

	}

	/** Display iph categories.
	 * 
	 * @param orinNumber the orin number
	 * @param contentDisplayForm the content display form
	 * @param request the request
	 * @param response the response */
	private String displayIPHCategories(String orinNumber, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		List<ItemPrimaryHierarchyVO> iphCategoryList = null;
		LOGGER.info("start of displayIPHCategories......");
		Map<String, String> categoryReferenceData = null;
		
		String categoryId = null;
		String categoryName = null;
		try {
			iphCategoryList = contentDelegate.getIPHCategoriesFromADSEPetCatalog(orinNumber);
			

			if (iphCategoryList != null) {

				if (iphCategoryList.size() > 0) {
					ItemPrimaryHierarchyVO iphCategory = iphCategoryList.get(0);
					categoryId = iphCategory.getPetCategoryId();
					categoryName = iphCategory.getPetCategoryName();
					// categoryMap.put(categoryId, categoryName);
					// contentDisplayForm.setCategoryReferenceData(categoryMap);
				}
			}

			categoryReferenceData = getIPHCategory(iphCategoryList, orinNumber);

			if (iphCategoryList != null) {
				categoryReferenceData.put(categoryId, categoryName);
				request.setAttribute("selectedCategory", categoryId);
			}
			contentDisplayForm.setCategoryReferenceData(categoryReferenceData);

			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
			// if(iphCategoryList == null)
			// {

			LOGGER.info("end of displayIPHCategories......");
			// } else {

			//
			// }
			LOGGER.info("IPH Count on load " + categoryId);

			// request.setAttribute("dynamicCategoryId",
			// request.getParameter("dynamicCategoryId"));
			// String dynamicCategoryId=(String)
			// request.getAttribute("dynamicCategoryId");
			// LOGGER.info("dynamicCategoryId.in display iph....."+dynamicCategoryId);

			// String selectedCategory=(String)
			// request.getAttribute("selectedCategory");
			// LOGGER.info("selectedCategory...."+selectedCategory);
		} catch (final PEPDelegateException e) {

			e.printStackTrace();
		}

		return categoryId;

	}

	/** Display pets as parent child.
	 * 
	 * @param petList the pet list
	 * @return the style and its child display */
	private StyleAndItsChildDisplay displayPetsAsParentChild(List<PetsFound> petList, ContentForm contentDisplayForm) {
		LOGGER.info("Portal StyleAndItsChildDisplay------");
		LOGGER.info("Portal StyleAndItsChildDisplay--petList----" + petList.size());
		String childsParentOrinNumber = null;
		String parentOrinNumber = null;
		String earliestCompletionDate = "";
		String styleColorOrinNumber1 = "";
		Map<String, String> completionDateMap = new HashMap<String, String>();
		final List<SkuVO> skuList = new ArrayList<SkuVO>();
		final List<StyleColorVO> styleColorList = new ArrayList<StyleColorVO>();
		final List<StyleVO> styleList = new ArrayList<StyleVO>();
		final List<StyleVO> styleListForContentDisplay = new ArrayList<StyleVO>();
		final StyleAndItsChildDisplay styleAndItsChildDisplay = new StyleAndItsChildDisplay();
		List<String> styleColorCompletionDateList = null;
		for (final PetsFound pet : petList) {
			final String entryType = pet.getEntryType();
			// LOGGER.info("entryType---"+entryType);
			// Logic for collecting all the Pets of Entity Type as Complex Pack
			if (entryType.equalsIgnoreCase("Complex Pack")) {
				// LOGGER.info("Portal StyleAndItsChildDisplay--petList----Complex Pack entry type is "+entryType);

				final String orinNumber = pet.getOrinNumber();
				pet.getVendorStyle();
				parentOrinNumber = pet.getParentStyleOrin();
				final String color = pet.getColor();
				final String vendorSize = pet.getVendorSize();
				final String omniSizeDescription = pet.getOmniSizeDescription();
				final String contentState = pet.getContentState();
				final String completionDate = pet.getCompletionDate();
				final StyleVO style = new StyleVO();
				style.setEntryType(entryType);
				style.setColor(color);
				style.setOrinNumber(orinNumber);
				style.setParentOrinNumber(orinNumber);// changed the Parent
														// MDMID of the Style is
														// null
				style.setVendorSize(vendorSize);
				// Set the earliest completion date from Style Colors to Style
				// for display on the portal
				style.setCompletionDate(earliestCompletionDate);
				// LOGGER.info("Portal earliestCompletionDate for Style----"+earliestCompletionDate);
				// LOGGER.info("Portal earliestCompletionDate for Style--12--"+style.getCompletionDate());
				style.setContentStatus(contentState);
				String contentStatusCode = setContentStatusCode(contentState);
				style.setContentStatusCode(contentStatusCode);
				style.setOmniSizeDescription(omniSizeDescription);
				styleList.add(style);// Add all the Style to the Style list
				styleAndItsChildDisplay.setComplexPackEntry("Complex Pack");

			}
			// Logic for collecting all the Pets of Entity Type as Pack Color

			if (entryType.equalsIgnoreCase("PackColor")) {
				// LOGGER.info("Portal StyleAndItsChildDisplay--petList----entry type is PackColor");
				childsParentOrinNumber = pet.getParentStyleOrin();
				final String orinNumber = pet.getOrinNumber();
				final String color = pet.getColor();
				final String vendorSize = pet.getVendorSize();
				final String omniSizeDescription = pet.getOmniSizeDescription();
				final String contentState = pet.getContentState();
				final String completionDate = pet.getCompletionDate();
				final StyleColorVO styleColor = new StyleColorVO();
				styleColor.setEntryType(entryType);
				styleColor.setParentStyleOrinNumber(childsParentOrinNumber);
				styleColor.setOrinNumber(orinNumber);
				styleColor.setColor(color);
				styleColor.setVendorSize(vendorSize);
				styleColor.setOmniSizeDescription(omniSizeDescription);
				styleColor.setCompletionDate(completionDate);
				styleColor.setContentStatus(contentState);
				String contentStatusCode = setContentStatusCode(contentState);
				styleColor.setContentStatusCode(contentStatusCode);
				styleColorList.add(styleColor);// Add all the StyleColor to the
												// Style Color list
				styleAndItsChildDisplay.setPackColorEntry("PackColor");
				// LOGGER.info("styleColorList size.."+styleColorList.size());

			}

			if (entryType.equalsIgnoreCase("SKU")) {
				// LOGGER.info("Portal StyleAndItsChildDisplay--petList----entry type is SKU");
				childsParentOrinNumber = pet.getParentStyleOrin();
				final String orinNumber = pet.getOrinNumber();
				final String color = pet.getColor();
				final String vendorSize = pet.getVendorSize();
				final String omniChannelSizeDescription = pet.getOmniSizeDescription();
				final String contentState = pet.getContentState();
				final String colorCode = pet.getColorCode();
				final String completionDate = pet.getCompletionDate();
				final SkuVO sku = new SkuVO();
				sku.setEntryType(entryType);
				sku.setStyleId(childsParentOrinNumber);
				sku.setOrin(orinNumber);
				sku.setColor(color);
				sku.setColorCode(colorCode);
				sku.setVendorSize(vendorSize);
				sku.setOmniChannelSizeDescription(omniChannelSizeDescription);
				sku.setCompletionDate(completionDate);
				String contentStatusCode = setContentStatusCode(contentState);
				sku.setContentStatus(contentState);
				sku.setContentStatusCode(contentStatusCode);
				skuList.add(sku);// Add all the SKU to the SKU list

			}

			if (entryType.equalsIgnoreCase("StyleColor")) {
				// LOGGER.info("Portal StyleAndItsChildDisplay--petList----entry type is StyleColor");
				childsParentOrinNumber = pet.getParentStyleOrin();
				final String orinNumber = pet.getOrinNumber();
				final String color = pet.getColor();
				final String vendorSize = pet.getVendorSize();
				final String omniSizeDescription = pet.getOmniSizeDescription();
				final String contentState = pet.getContentState();
				final String completionDate = pet.getCompletionDate();
				final StyleColorVO styleColor = new StyleColorVO();
				styleColor.setEntryType(entryType);
				styleColor.setParentStyleOrinNumber(childsParentOrinNumber);
				styleColor.setOrinNumber(orinNumber);
				styleColor.setColor(color);
				styleColor.setVendorSize(vendorSize);
				styleColor.setOmniSizeDescription(omniSizeDescription);
				styleColor.setCompletionDate(completionDate);
				styleColor.setContentStatus(contentState);
				String contentStatusCode = setContentStatusCode(contentState);
				styleColor.setContentStatusCode(contentStatusCode);
				styleColorList.add(styleColor);// Add all the StyleColor to the
												// Style Color list
				// LOGGER.info("styleColorList size.."+styleColorList.size());
				styleAndItsChildDisplay.setStyleColorEntry("StyleColor");

			}

			/* //Logic for displaying the earliest completion date at Style
			 * level by calculating the minimum completion date of style colors
			 * if((styleColorList!=null) && (styleColorList.size()>0)) { for
			 * (StyleColorVO styleColor:styleColorList) { String
			 * completionDate=styleColor.getCompletionDate(); String
			 * styleColorOrinNumber=styleColor.getOrinNumber();
			 * completionDateMap.put(styleColorOrinNumber, completionDate);
			 * 
			 * } if((completionDateMap!=null) && (completionDateMap.size()>0) )
			 * { styleColorCompletionDateList = new
			 * ArrayList<String>(completionDateMap.values());
			 * 
			 * Collections.sort(styleColorCompletionDateList);//Sort completion
			 * date in ascending order-the earliest completion date would be at
			 * the top of the list
			 * 
			 * earliestCompletionDate= styleColorCompletionDateList.get(0); }
			 * 
			 * LOGGER.info(
			 * "earliestCompletionDate among Style Colors---parent child loop"
			 * +earliestCompletionDate);
			 * 
			 * } */
			if (entryType.equalsIgnoreCase("Style")) {
				// LOGGER.info("Portal StyleAndItsChildDisplay--petList----entry type is Style");
				final String orinNumber = pet.getOrinNumber();
				pet.getVendorStyle();
				parentOrinNumber = pet.getParentStyleOrin();
				final String color = pet.getColor();
				final String vendorSize = pet.getVendorSize();
				final String omniSizeDescription = pet.getOmniSizeDescription();
				final String contentState = pet.getContentState();
				final String completionDate = pet.getCompletionDate();
				final StyleVO style = new StyleVO();
				style.setEntryType(entryType);
				style.setColor(color);
				style.setOrinNumber(orinNumber);
				style.setParentOrinNumber(orinNumber);// changed the Parent
														// MDMID of the Style is
														// null
				style.setVendorSize(vendorSize);
				// Set the earliest completion date from Style Colors to Style
				// for display on the portal
				style.setCompletionDate(earliestCompletionDate);
				// LOGGER.info("Portal earliestCompletionDate for Style----"+earliestCompletionDate);
				// LOGGER.info("Portal earliestCompletionDate for Style--12--"+style.getCompletionDate());
				style.setContentStatus(contentState);
				String contentStatusCode = setContentStatusCode(contentState);
				style.setContentStatusCode(contentStatusCode);
				style.setOmniSizeDescription(omniSizeDescription);
				styleList.add(style);// Add all the Style to the Style list
				styleAndItsChildDisplay.setStyleEntry("Style");

			}

		}
		LOGGER.info("styleList length=" + styleList.size());
		LOGGER.info("styleColorList length=" + styleColorList.size());
		// Check for the Parent Child association
		for (final StyleVO style : styleList) {
			parentOrinNumber = style.getParentOrinNumber();
			LOGGER.info("parentOrinNumber.." + parentOrinNumber);
			final List<StyleColorVO> subStyleColorList = new ArrayList<StyleColorVO>();
			List<SkuVO> childSkuList = null;

			for (final StyleColorVO styleColor : styleColorList) {
				// Get the Orin Number of the Style Color,Style Color is the
				// child of the Parent Style
				childsParentOrinNumber = styleColor.getParentStyleOrinNumber();
				styleColor.getOrinNumber();
				childSkuList = new ArrayList<SkuVO>();
				childsParentOrinNumber = styleColor.getParentStyleOrinNumber();
				styleColorOrinNumber1 = styleColor.getOrinNumber();
				earliestCompletionDate = styleColor.getCompletionDate();
				final String colorCodeFromStyleColorOrinNumber = ExtractColorCode.getLastThreeDigitNRFColorCode(styleColorOrinNumber1);
				// System.out.println("styleColorOrinNumber1.."+styleColorOrinNumber1);
				// Check if the Style Parent Orin Number is same as the Style
				// Color Child Orin Number
				if (parentOrinNumber.equalsIgnoreCase(childsParentOrinNumber)) {
					// Start of Logic for calculating earliest completion date
					completionDateMap.put(styleColorOrinNumber1, earliestCompletionDate);
					if ((completionDateMap != null) && (completionDateMap.size() > 0)) {

						styleColorCompletionDateList = new ArrayList<String>(completionDateMap.values());

						Collections.sort(styleColorCompletionDateList);// Sort
																		// completion
																		// date
																		// in
																		// ascending
																		// order-the
																		// earliest
																		// completion
																		// date
																		// would
																		// be at
																		// the
																		// top
																		// of
																		// the
																		// list

						earliestCompletionDate = styleColorCompletionDateList.get(0);

						// LOGGER.info("earliestCompletionDate among Style Colors---parent child loop"+earliestCompletionDate);
						earliestCompletionDate = styleColorCompletionDateList.get(0);
					}

					// End of Logic for calculating earliest completion date
					for (final SkuVO sku : skuList) {
						final String skuColorCode = sku.getColorCode();
						// Create the Style Color parent and SKU Child
						// relationship
						if (colorCodeFromStyleColorOrinNumber.equalsIgnoreCase(skuColorCode)) {
							// Add all the sku with the matching color code with
							// that of the Parent Style Color Number in a list
							childSkuList.add(sku);
						}

					}

					// Add all the list of child SKUs to its respective parent
					// Style Color
					styleColor.setSkuList(childSkuList);
					subStyleColorList.add(styleColor);
					// Set the earliest completion date in Style Entity
					style.setCompletionDate(earliestCompletionDate);
					contentDisplayForm.getStyleInformationVO().setCompletionDateOfStyle(earliestCompletionDate);
					// Add all the list of child Style Colors to its respective
					// parent Style

					// LOGGER.info("earliestCompletionDate for Style after Parent Child relationship---for style----------------"+earliestCompletionDate);
					// LOGGER.info("earliestCompletionDate for Style after Parent Child relationship-------------------"+style.getCompletionDate());
					style.setStyleColorList(subStyleColorList);
					System.out.println("get the SKUList size..." + styleColor.getSkuList().size());

				}

			}
			if (subStyleColorList.size() > 0) {
				LOGGER.info("Size of the Color List .." + subStyleColorList.size());
				style.setStyleColorList(subStyleColorList);// Add all the child
															// Style Colors to
															// the Parent Style
			} else {
				LOGGER.info("This is from Else part just return the style list with out any child style color");

			}
			styleListForContentDisplay.add(style);// Add all the Styles with
													// children Style Color to
													// the declared style list
													// for content display

		}

		LOGGER.info("styleColorList.." + styleListForContentDisplay.size());
		styleAndItsChildDisplay.setStyleList(styleListForContentDisplay);// Add
																			// all
																			// the
																			// Styles
																			// to
																			// the
																			// content
																			// list
																			// display
																			// form

		LOGGER.info("styAndItsChildDisplay size.." + styleAndItsChildDisplay.getStyleList().size());
		LOGGER.info("End of Portal StyleAndItsChildDisplay------");
		return styleAndItsChildDisplay;

	}

	/** Display product information.
	 * 
	 * @param orinNumber the orin number
	 * @param contentDisplayForm the content display form
	 * @param request the request
	 * @param response the response */
	private void displayProductInformation(String orinNumber, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		LOGGER.info("Start of displayProductInformation.... ");

		ProductDetailsVO productInformation = contentDelegate.getProductInformation(orinNumber);

		if (productInformation == null) {
			productInformation = new ProductDetailsVO();
		}
		if (request.getAttribute("productName") != null) {
			request.setAttribute("selectedProductName", request.getAttribute("productName"));
			productInformation.setProductName((String) request.getAttribute("productName"));
		}

		if (request.getAttribute("productDescription") != null) {
			request.setAttribute("selectedProductDescription", request.getAttribute("productDescription"));
			productInformation.setProductDescription((String) request.getAttribute("productDescription"));
		}
		if (productInformation != null) {
			contentDisplayForm.setProductDetailsVO(productInformation);
		}

		modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		LOGGER.info("End of displayProductInformation.... ");
	}

	/** Display save style attribute webservice message.
	 * 
	 * @param request the request
	 * @param contentDisplayForm the content display form */
	private void displaySaveStyleAttributeWebserviceMessage(RenderRequest request, ContentForm contentDisplayForm) {
		final String saveStyleAttributeMessage = request.getParameter(ContentScreenConstants.SAVE_STYLE_ATTRIBUTES_KEY);
		LOGGER.info("displaySaveStyleAttributeWebserviceMessage = " + saveStyleAttributeMessage);
		if (StringUtils.isNotBlank(saveStyleAttributeMessage)) {
			LOGGER.info("displaySaveStyleAttributeWebserviceMessage = " + saveStyleAttributeMessage);
			contentDisplayForm.setSaveStyleAttributeMessage(saveStyleAttributeMessage);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

		}

	}

	/** Display sku data.
	 * 
	 * @param orinNumber the orin number
	 * @param contentDisplayForm the content display form
	 * @param request the request
	 * @param response the response */
	private void displaySKUData(String orinNumber, ContentForm contentDisplayForm, RenderRequest request, RenderResponse response) {
		// Get the SKU data from the ADSE tables and prep the data for
		// displaying on the Child SKU Section
		final List<ChildSkuVO> skuList = contentDelegate.getChildSkuInfo(orinNumber);
		if ((skuList != null) && (skuList.size() > 0)) {
			contentDisplayForm.setChildSkuDisplayList(skuList);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} else {
			// set the message to display on the screen No Child SKU data exists
			contentDisplayForm.setNoChildSkuDataPresent("No Child SKU data exists");
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}

	}

	/** Display style attribute data.
	 * 
	 * @param contentDisplayForm the content display form
	 * @param request the request
	 * @param response the response */
	private void displayStyleAttributeData(ContentForm contentDisplayForm, RenderRequest request, RenderResponse response) {
		LOGGER.info("----------Start of displayStyleAttributeData----------");
		final String displayStyleAttributeSectionFlag = request.getParameter(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_KEY);
		if (StringUtils.isNotBlank(displayStyleAttributeSectionFlag)) {
			LOGGER.info("----------displayStyleAttributeSectionFlag---------" + displayStyleAttributeSectionFlag);
			if (displayStyleAttributeSectionFlag.equals(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_FLAG_YES)) {
				contentDisplayForm.setDisplayStyleAttributeSection(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_FLAG_YES);
			} else if (displayStyleAttributeSectionFlag.equals(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_FLAG_NO)) {

				contentDisplayForm.setDisplayStyleAttributeSection(ContentScreenConstants.HIDE_STYLE_ATTRIBUTE_SECTION_FLAG_NO);
				final GlobalAttributesVO styleAttributes = getStyleAttributes();
				LOGGER.info("----------styleAttributes---------" + styleAttributes);
				if (styleAttributes != null) {
					contentDisplayForm.setGlobalAttributesDisplay(getStyleAttributes());
					LOGGER.info("----------contentDisplayForm.getGlobalAttributesDisplay()---------"
							+ contentDisplayForm.getGlobalAttributesDisplay());
				} else {
					final String noStyleAttributeExistsMessage = request
							.getParameter(ContentScreenConstants.NO_STYLE_ATTRIBUTE_EXISTS_KEY);
					contentDisplayForm.setNoStyleAttributeExistsMessage(noStyleAttributeExistsMessage);

				}

			}
		}
		LOGGER.info("----------End of displayStyleAttributeData----------");

	}

	/** Display style information.
	 * 
	 * @param orinNumber the orin number
	 * @param contentDisplayForm the content display form
	 * @param request the request
	 * @param response the response */
	private void displayStyleInformation(String orinNumber, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		try {
			final StyleInformationVO styleInformation = contentDelegate.getStyleInformation(orinNumber);

			if (styleInformation != null) {
				contentDisplayForm.setStyleInformationVO(styleInformation);
				LOGGER.info("-----getStyleId------" + contentDisplayForm.getStyleInformationVO().getStyleId());
			}

			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

		} catch (final PEPDelegateException e) {
			LOGGER.info("Exception occurred" + e.getMessage());
			contentDisplayForm.setFetchStyleInfoErrorMessage(ContentScreenConstants.FETCH_STYLE_INFO_ERROR_MESSAGE);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

		}
	}

	/** Display the message to submit style level data before submitting style
	 * color data.
	 * 
	 * @param request the request
	 * @param contentDisplayForm the content display form
	 * @param styleDataSubmissionFlag the style data submission flag */
	private void displayTheMessageToSubmitStyleLevelDataBeforeSubmittingStyleColorData(RenderRequest request,
			ContentForm contentDisplayForm, boolean styleDataSubmissionFlag) {
		if (!styleDataSubmissionFlag) {
			final String styeLevelDataToBeSubmittedFirstMessage = request
					.getParameter(ContentScreenConstants.SUBMIT_STYLE_LEVEL_DATA_FIRST);

			if (StringUtils.isNotBlank(styeLevelDataToBeSubmittedFirstMessage)) {
				contentDisplayForm.setStyleDataSubmissionDataMessage(styeLevelDataToBeSubmittedFirstMessage);
				modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
			}

		}

	}

	/** Display update style color status webservice message.
	 * 
	 * @param request the request
	 * @param contentDisplayForm the content display form */
	private void displayUpdateStyleColorStatusWebserviceMessage(RenderRequest request, ContentForm contentDisplayForm) {
		final String updateContentStatusForStyleColorMessage = request
				.getParameter(ContentScreenConstants.UPDATE_STYLE_COLOR_PET_CONTENT_STATUS_WEBSERVICE_REPONSE_KEY);
		LOGGER.info("updateContentStatusForStyleColorMessage = " + updateContentStatusForStyleColorMessage);
		if (StringUtils.isNotBlank(updateContentStatusForStyleColorMessage)) {
			LOGGER.info("updateContentStatusForStyleColorMessage = " + updateContentStatusForStyleColorMessage);
			contentDisplayForm.setUpdateContentStatusMessage(updateContentStatusForStyleColorMessage);
			LOGGER.info(" contentDisplayForm.setUpdateContentStatusMessage(= " + contentDisplayForm.getUpdateContentStatusMessage());
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

		}

	}

	/** Display update style status webservice message.
	 * 
	 * @param request the request
	 * @param contentDisplayForm the content display form */
	private void displayUpdateStyleStatusWebserviceMessage(RenderRequest request, ContentForm contentDisplayForm) {
		final String updateContentStatusForStyleMessage = request
				.getParameter(ContentScreenConstants.UPDATE_STYLE_PET_CONTENT_STATUS_WEBSERVICE_REPONSE_KEY);
		LOGGER.info("updateContentStatusForStyleMessage = " + updateContentStatusForStyleMessage);
		if (StringUtils.isNotBlank(updateContentStatusForStyleMessage)) {
			LOGGER.info("updateContentStatusForStyleMessage = " + updateContentStatusForStyleMessage);
			contentDisplayForm.setUpdateContentStatusMessage(updateContentStatusForStyleMessage);
			LOGGER.info(" contentDisplayForm.setUpdateContentStatusMessage(= " + contentDisplayForm.getUpdateContentStatusMessage());
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}
	}

	@ResourceMapping("fetchIPHCategory")
	ModelAndView fetchIPHCategory(ResourceRequest request, ResourceResponse response) {
		LOGGER.info("fetchIPHCategory ************..");
		final ModelAndView modelAndView = null;
		final String categoryId = request.getParameter("categoryId");
		LOGGER.info("fetchIPHCategory **categoryId**********.." + categoryId);
		if (StringUtils.isBlank(categoryId)) {
			setCategoryIdFromDropDown(categoryId);
		}
		return modelAndView;

	}

	/** Form html dynamic display. */
	private void formHtmlDynamicDisplay() {
	}

	@Override
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAttributeMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAttributeMap(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/** Gets the omni channel brand.
	 * 
	 * @param orinNumber the orin number
	 * @param supplierId the supplier id
	 * @return the omni channel brand
	 * @throws PEPDelegateException the PEP delegate exception */
	private List<CarBrandVO> getCarsBrandList(String orinNumber, String supplierId) throws PEPDelegateException {
		LOGGER.info("Start of getCarsBrandList ......");
		List<CarBrandVO> lstCarBrandVO = null;
		CarBrandVO carBrandVO = null;

		lstCarBrandVO = contentDelegate.getCardsBrandList(orinNumber, supplierId);

		LOGGER.info("Start of getCarsBrandList ......");
		return lstCarBrandVO;
	}

	/** Gets the category id from drop down.
	 * 
	 * @return the categoryIdFromDropDown */
	public String getCategoryIdFromDropDown() {
		return categoryIdFromDropDown;
	}

	/** Gets the changed iph category data.
	 * 
	 * @param fdForm the fd form
	 * @param result the result
	 * @param request the request
	 * @param response the response
	 * @return the changed iph category data
	 * @throws IOException Signals that an I/O exception has occurred. */
	@ActionMapping(params = { "action=getChangedIPHCategoryData" })
	public void getChangedIPHCategoryData(@ModelAttribute("fdForm") ContentForm fdForm, BindingResult result, ActionRequest request,
			ActionResponse response) throws IOException {
		LOGGER.info("----------------getChangedIPHCategoryData- on change of  drop down---------------");

		String savedCategoryId = request.getParameter("savedCategoryId");

		LOGGER.info("-----------------savedCategoryId--------------" + savedCategoryId);

		String iphCategoryDropDownId = request.getParameter("iphCategoryDropDownId");
		LOGGER.info("-----------------Start of iphCategoryDropDownId--iphCategoryDropDownId---------------" + iphCategoryDropDownId);

		if (request.getParameter("dynamicCategoryId") != null) {
			String dynamicCategoryId = request.getParameter("dynamicCategoryId");
			request.setAttribute("dynamicCategoryId", request.getParameter("dynamicCategoryId"));
		}

		if (request.getParameter("omnichannelbrand") != null) {
			String omnichannelbrand = request.getParameter("omnichannelbrand");
			request.setAttribute("omnichannelbrand", request.getParameter("omnichannelbrand"));
		}

		if (request.getParameter("carbrand") != null) {
			String carbrand = request.getParameter("carbrand");
			request.setAttribute("carbrand", request.getParameter("carbrand"));
		}

		if (request.getParameter("productName") != null) {
			String productName = request.getParameter("productName");
			request.setAttribute("productName", request.getParameter("productName"));
		}

		if (request.getParameter("productDescription") != null) {
			String productDescription = request.getParameter("productDescription");
			request.setAttribute("productDescription", request.getParameter("productDescription"));
		}

		if (request.getParameter("omniColorFamily") != null) {
			String omniColorFamily = request.getParameter("omniColorFamily");
			request.setAttribute("omniColorFamily", request.getParameter("omniColorFamily"));
		}

		if (request.getParameter("secondaryColor1") != null) {
			String secondaryColor1 = request.getParameter("secondaryColor1");
			request.setAttribute("secondaryColor1", request.getParameter("secondaryColor1"));
		}

		if (request.getParameter("secondaryColor2") != null) {
			String secondaryColor2 = request.getParameter("secondaryColor2");
			request.setAttribute("secondaryColor2", request.getParameter("secondaryColor2"));
		}

		if (request.getParameter("secondaryColor3") != null) {
			String secondaryColor3 = request.getParameter("secondaryColor3");
			request.setAttribute("secondaryColor3", request.getParameter("secondaryColor3"));
		}

		if (request.getParameter("secondaryColor4") != null) {
			String secondaryColor4 = request.getParameter("secondaryColor4");
			request.setAttribute("secondaryColor4", request.getParameter("secondaryColor4"));
		}

		if (request.getParameter("omniChannelColorDescription") != null) {
			String omniChannelColorDescription = request.getParameter("omniChannelColorDescription");
			request.setAttribute("omniChannelColorDescription", request.getParameter("omniChannelColorDescription"));
		}

		if (request.getParameter("vendorColor") != null) {
			String vendorColor = request.getParameter("vendorColor");
			request.setAttribute("vendorColor", request.getParameter("vendorColor"));
		}
		String channelkExcValue = request.getParameter("channelExclId");
		if (channelkExcValue != null) {
			request.setAttribute("selectedChannelExclusive", channelkExcValue);
		}
		String belkExcValue = request.getParameter("belkExclId");
		if (belkExcValue != null) {
			request.setAttribute("selectedBelkExclusive", belkExcValue);
		}
		String selectedGWP = request.getParameter("globalGWPId");
		if (selectedGWP != null) {
			request.setAttribute("selectedGWP", selectedGWP);
		}
		String selectedPWP = request.getParameter("globalPWPId");
		if (selectedPWP != null) {
			request.setAttribute("selectedPWP", selectedPWP);
		}
		String selectedPYG = request.getParameter("globalPYGId");
		if (selectedPYG != null) {
			request.setAttribute("selectedPYG", selectedPYG);
		}
		String selectedBopis = request.getParameter("bopislId");
		if (selectedBopis != null) {
			request.setAttribute("selectedBopis", selectedBopis);
		}

	}

	/** Gets the content delegate.
	 * 
	 * @return the content delegate */
	public ContentDelegate getContentDelegate() {
		return contentDelegate;
	}

	/** Gets the content display form.
	 * 
	 * @return the contentDisplayForm */
	/* public ContentForm getContentDisplayForm() { return contentDisplayForm; } */

	/** Gets the content pet details from inter portlet communication.
	 * 
	 * @param request the request
	 * @return the content pet details from inter portlet communication */
	private ContentPetDetails getContentPetDetailsFromIPC(RenderRequest request) {
		LOGGER.info("-----------------Start of getContentPetDetailsFromIPC-----------------");
		LOGGER.info("request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS).."
				+ request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS));
		if (request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS) != null) {
			LOGGER.info("request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS).not null check."
					+ request.getPortletSession().getAttribute(ContentScreenConstants.CONTENT_PET_DETAILS));
			final ContentPetDetails contentPetDetails = (ContentPetDetails) request.getPortletSession().getAttribute(
					ContentScreenConstants.CONTENT_PET_DETAILS);
			LOGGER.info("-----------------End of getContentPetDetailsFromIPC-----------------");
			return contentPetDetails;
		}
		return null;

	}

	@Override
	public long getCreationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** Gets the family tree category.
	 * 
	 * @param dynamicCategoryId the dynamic category id
	 * @return the family tree category */
	private List<ItemPrimaryHierarchyVO> getFamilyTreeCategory(String dynamicCategoryId) {

		List<ItemPrimaryHierarchyVO> iphFamilyList = null;
		try {
			iphFamilyList = contentDelegate.getFamilyCategoriesFromIPH(dynamicCategoryId);
			LOGGER.info("iphFamilyList--------" + iphFamilyList.size());
		} catch (PEPDelegateException e) {

			e.printStackTrace();
		}

		return iphFamilyList;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@ActionMapping(params = "action=getIphAttributeAction")
	public void getIphAttributeAction(ActionRequest request, ActionResponse resourceResponse, final BindingResult result,
			final Model model) {

		LOGGER.info("end of ActionMapping....getIPHAttributesFromDB.");

	}

	/** Gets the IPH category.
	 * 
	 * @param iphCategoryList the iph category list
	 * @return the IPH category
	 * @throws PEPDelegateException */
	private Map<String, String> getIPHCategory(List<ItemPrimaryHierarchyVO> iphCategoryList, String orinNumber)
			throws PEPDelegateException {
		LOGGER.info("--------------start of getIPHCategory----------------");

		final Map<String, String> categoryMap = new LinkedHashMap<String, String>();

		try {
			final List<ItemPrimaryHierarchyVO> iphCategoryFromAdseMerchHierarchyList = contentDelegate
					.getIPHCategoriesFromAdseMerchandiseHierarchy(orinNumber);
			for (final ItemPrimaryHierarchyVO iphCategoryMerchandiseObject : iphCategoryFromAdseMerchHierarchyList) {

				final String categoryIdFromMerchandise = iphCategoryMerchandiseObject.getMerchandiseCategoryId();
				final String categoryNameFromMerchandise = iphCategoryMerchandiseObject.getMerchandiseCategoryName();
				final String categoryFullPath = iphCategoryMerchandiseObject.getCategoryFullPath();

				LOGGER.info("--------------categoryIdFromMerchandise----------------" + categoryIdFromMerchandise);
				LOGGER.info("------------categoryNameFromMerchandise---------------" + categoryFullPath);

				categoryMap.put(categoryIdFromMerchandise, categoryFullPath);

			}
		} catch (final Exception e) {

			e.printStackTrace();
		}

		return categoryMap;

	}

	@Override
	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** Gets the model and view.
	 * 
	 * @return the modelAndView */
	public ModelAndView getModelAndView() {
		return modelAndView;
	}

	/** Gets the cars brand.
	 * 
	 * @param orinNumber the orin number
	 * @param supplierId the supplier id
	 * @return the cars brand
	 * @throws PEPDelegateException the PEP delegate exception */
	private List<OmniChannelBrandVO> getOmniChannelBrandList(String orinNumber, String supplierId) throws PEPDelegateException {
		LOGGER.info("Start of getOmniChannelBrandList ......");
		List<OmniChannelBrandVO> lstOmniChannelBrandVO = null;
		OmniChannelBrandVO omniChannelBrandVO = null;

		lstOmniChannelBrandVO = contentDelegate.getOmniChannelBrandList(orinNumber, supplierId);

		LOGGER.info("Start of getCarsBrandList ......");
		return lstOmniChannelBrandVO;
	}

	/** Gets the pet list.
	 * 
	 * @param orinNumber the orin number
	 * @param contentDisplayForm2 the content display form2
	 * @param request the request
	 * @param response the response
	 * @return the pet list */
	private void getPetList(String roleNameFromIPC, String orinNumber, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		LOGGER.info("Start of getPetList  to display on the portal..................");
		List<PetsFound> petList;
		try {
			petList = contentDelegate.getPetList(roleNameFromIPC, orinNumber);

			LOGGER.info("portal pet list size.................." + petList.size());

			if ((petList != null) && (petList.size() > 0)) {
				LOGGER.info("StylePetsFound---Data");
				modelAndView.addObject("StylePetsFound", "Data exists");
				final StyleAndItsChildDisplay styleAndItsChildDisplay = displayPetsAsParentChild(petList, contentDisplayForm);
				contentDisplayForm.setStyleAndItsChildDisplay(styleAndItsChildDisplay);
				// modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM,
				// contentDisplayForm);
			} else {
				LOGGER.info("noStylePetsFound---No Data exist");
				System.out.println("Style Attributes is Null for Global Styles");
				contentDisplayForm.setNoPetsFoundInADSEDatabase("true");
				modelAndView.addObject("noStylePetsFound", "No Data exists");
				// modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM,
				// contentDisplayForm);
			}
		} catch (final PEPDelegateException e) {
			LOGGER.info("PEPDelegateException......message........" + e.getMessage());
			LOGGER.info("PEPDelegateException......stacktrace........" + e.getStackTrace());
			LOGGER.info("PEPDelegateException......cause........" + e.getCause());
			e.printStackTrace();
		}
		LOGGER.info("End of getPetList  to display on the portal...................");

	}

	@Override
	public PortletContext getPortletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	/** Gets the response message web service.
	 * 
	 * @return the responseMessageWebService */
	public String getResponseMessageWebService() {
		return responseMessageWebService;
	}

	/** Gets the SKU attribute details.
	 * 
	 * @param request the request
	 * @param response the response
	 * @return the SKU attribute details */
	@ResourceMapping("getSKUAttributeDetails")
	public ModelAndView getSKUAttributeDetails(ResourceRequest request, ResourceResponse response) {
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
					jsonObject.add("skuObjectInfo", skuAttributeObject);
					System.out.println("myObj sku attributes from json--->" + jsonObject.toString());
					try {
						response.getWriter().write(jsonObject.toString());

					} catch (final IOException e) {

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

	/** Gets the style attribute details.
	 * 
	 * @param request the request
	 * @param response the response
	 * @return the style attribute details */
	@ResourceMapping("getStyleAttributeDetails")
	public ModelAndView getStyleAttributeDetails(ResourceRequest request, ResourceResponse response) {
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
					styleAttributes.setGwp("");
					styleAttributes.setPyg("");
					styleAttributes.setProductDimensionUom("");
					styleAttributes.setLength("");
					styleAttributes.setWidth("");
					styleAttributes.setHeight("");
					styleAttributes.setWeightUOM("");
					styleAttributes.setProductWeight("");
					final Gson gson = new Gson();
					final JsonObject jsonObject = new JsonObject();
					final JsonElement styleAttributeObject = gson.toJsonTree(styleAttributes);
					jsonObject.add("styleAttributeObject", styleAttributeObject);
					System.out.println("styleAttributeObject json--->" + jsonObject.toString());
					try {
						response.getWriter().write(jsonObject.toString());

					} catch (final IOException e) {

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

	/** Gets the style attributes.
	 * 
	 * @return the styleAttributes */
	public GlobalAttributesVO getStyleAttributes() {
		return styleAttributes;
	}

	/** Gets the style color attribute.
	 * 
	 * @return the styleColorAttribute */
	public ColorAttributesVO getStyleColorAttribute() {
		return styleColorAttribute;
	}

	/** Gets the style color attribute details.
	 * 
	 * @param request the request
	 * @param response the response
	 * @return the style color attribute details */
	@ResourceMapping("getStyleColorAttributeDetails")
	public ModelAndView getStyleColorAttributeDetails(ResourceRequest request, ResourceResponse response) {
		LOGGER.info("getStyleColorAttributes ************..");
		final ModelAndView modelAndView = null;

		final String styleColorOrinNumber = request.getParameter("styleColorOrinNumber");
		if (StringUtils.isNotBlank(styleColorOrinNumber)) {
			LOGGER.info("@ResourceMapping-styleColorOrinNumber--- " + styleColorOrinNumber);
			ColorAttributesVO styleColorAttributes;
			try {
				styleColorAttributes = contentDelegate.getStyleColorAttributes(styleColorOrinNumber);
				LOGGER.info("styleColorAttributes object-- " + styleColorAttributes);

				if (styleColorAttributes != null) {
					styleColorAttributes.setStyleColorOrinNumber(styleColorOrinNumber);
					final Gson gson = new Gson();
					final JsonObject jsonObject = new JsonObject();
					final JsonElement styleColorAttributeObject = gson.toJsonTree(styleColorAttributes);
					jsonObject.add("styleColorObjectInfo", styleColorAttributeObject);
					System.out.println("styleColorObjectInfofrom json--->" + jsonObject.toString());
					try {
						response.getWriter().write(jsonObject.toString());

					} catch (final IOException e) {

						e.printStackTrace();
					}
					LOGGER.info("inside get getStyleColorAttributes");

					return null;
				} else {
					ColorAttributesVO styleColorAttributes1 = new ColorAttributesVO();
					styleColorAttributes1.setMessage("No Style Color Attribute Data Exists....");
					final Gson gson = new Gson();
					final JsonObject jsonObject = new JsonObject();
					final JsonElement styleColorAttributeObject = gson.toJsonTree(styleColorAttributes1);
					jsonObject.add("styleColorObjectInfo ", styleColorAttributeObject);
					System.out.println("noStyleColorObjectInfo json--->" + jsonObject.toString());
					try {
						response.getWriter().write(jsonObject.toString());

					} catch (final IOException e) {

						e.printStackTrace();
					}

				}
			}

			catch (final PEPDelegateException e1) {

				e1.printStackTrace();
			}

			LOGGER.info("Exiting getStyleColorAttributes method");

		}
		return modelAndView;

	}

	/* (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.portlet.mvc.EventAwareController#handleEventRequest
	 * (javax.portlet.EventRequest, javax.portlet.EventResponse) */
	@Override
	@EventMapping
	public void handleEventRequest(EventRequest request, EventResponse response) throws Exception {
		LOGGER.info("-----------------Start of handle handleEventRequest-----------------");
		final Event event = request.getEvent();
		LOGGER.info("event... " + event);
		if (event.getName() != null) {
			if (event.getName().equals(ContentScreenConstants.CONTENT_PET_DETAILS)) {

				final ContentPetDetails contentPetDetails = (ContentPetDetails) event.getValue();
				LOGGER.info("event.getName() 1 " + event.getName());
				LOGGER.info("event.value() 1 " + event.getValue());
				LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getContentStatus"
						+ contentPetDetails.getContentStatus());
				LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getContentStatus"
						+ contentPetDetails.getContentStatus());
				LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getOrinNumber" + contentPetDetails.getOrinNumber());
				LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getVpUser" + contentPetDetails.getVpUser());
				LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getUserData" + contentPetDetails.getUserData());
				LOGGER.info("Inside handle handleEventRequest...contentPetDetails...getBelkUser" + contentPetDetails.getBelkUser());
				request.getPortletSession().setAttribute(ContentScreenConstants.CONTENT_PET_DETAILS, contentPetDetails);
			}

		}

		LOGGER.info("-----------------End of handle handleEventRequest-----------------");
	}

	/** Handle render request.
	 * 
	 * @param request the request
	 * @param response the response
	 * @return the model and view
	 * @throws Exception the exception */
	/* (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.portlet.mvc.Controller#handleRenderRequest(javax
	 * .portlet.RenderRequest, javax.portlet.RenderResponse) */
	@RenderMapping
	public ModelAndView handleRenderRequest(RenderRequest request, RenderResponse response) throws Exception {
		System.out.println("Grouping content orinOrGrouping -->");
		LOGGER.info(" ------------------------------------------------------- Start of  handleRenderRequest -----------------------------------"
				+ new Date());

		String roleNameFromIPC = null;
		String orinNumber = null;
		String orinNumberFromIPC = null;
		String pepUserID = null;
		String lanIdFromIPC = null;
		String pepUserIdFromIPC = null;

		ContentPetDetails contentPetDetailsFromIPC = getContentPetDetailsFromIPC(request);

		System.out.println("contentPetDetailsFromIPC -->" + contentPetDetailsFromIPC);
		LOGGER.info("contentPetDetailsFromIPC----" + contentPetDetailsFromIPC);
		if (contentPetDetailsFromIPC != null) {
			orinNumberFromIPC = contentPetDetailsFromIPC.getOrinNumber();
			LOGGER.info("orinNumberFromIPC----" + orinNumberFromIPC);
			final String contentStatusFromIPC = contentPetDetailsFromIPC.getContentStatus();
			LOGGER.info("contentStatusFromIPC----" + contentStatusFromIPC);
			roleNameFromIPC = contentPetDetailsFromIPC.getUserData().getRoleName();
			LOGGER.info("roleNameFromIPC----" + roleNameFromIPC);
			final Common_Vpuser externalVendorFromIPC = contentPetDetailsFromIPC.getVpUser();
			LOGGER.info("externalVendorFromIPC----" + externalVendorFromIPC);
			final Common_BelkUser belkUserFromIPC = contentPetDetailsFromIPC.getBelkUser();

			LOGGER.info("belkUserFromIPC----" + belkUserFromIPC);

			if (externalVendorFromIPC != null) {
				pepUserIdFromIPC = externalVendorFromIPC.getPepUserID();
				final String emailIdFromIPC = externalVendorFromIPC.getUserEmailAddress();

				LOGGER.info("pepUserIdFromIPC----" + pepUserIdFromIPC);
				LOGGER.info("emailIdFromIPC----" + emailIdFromIPC);

			}

			if (belkUserFromIPC != null) {
				lanIdFromIPC = belkUserFromIPC.getLanId();

				LOGGER.info("lanIdFromIPC----" + lanIdFromIPC);

			}

			if (contentPetDetailsFromIPC.getUserData().isInternal()) {
				pepUserID = lanIdFromIPC;
				if ((pepUserID == null) || pepUserID.equalsIgnoreCase("")) {
					pepUserID = contentPetDetailsFromIPC.getUserData().getBelkUser().getLanId();
				}
			}
			if (contentPetDetailsFromIPC.getUserData().isExternal()) {
				pepUserID = pepUserIdFromIPC;
				if ((pepUserID == null) || pepUserID.equalsIgnoreCase("")) {
					pepUserID = contentPetDetailsFromIPC.getUserData().getVpUser().getPepUserID();
				}
			}

			// String orinNumber = "'249041001'";
			if (StringUtils.isNotBlank(orinNumberFromIPC)) {
				orinNumber = orinNumberFromIPC;
			}

			// request.setAttribute("orinNumber",
			// request.getParameter("dynamicCategoryId"));
			ContentForm contentDisplayForm = new ContentForm();

			LOGGER.info("PepUserID----" + pepUserID);
			String formSessionKey = request.getPortletSession().getId() + pepUserID;
			request.getPortletSession().setAttribute("formSessionKey", formSessionKey);
			request.getPortletSession().setAttribute(formSessionKey, contentDisplayForm); // Added
																							// by
																							// Sriharsha

			// get the logged in user role ,the logged in user role can be dca
			// role ,vendor role or read only role

			if (StringUtils.isNotBlank(roleNameFromIPC)) {
				// Set the role Name of the logged in User from the IPC
				contentDisplayForm.setRoleName(roleNameFromIPC); // TODO need to
																	// remove
																	// before
																	// deploy
				// contentDisplayForm.setRoleName("vendor"); //TODO for testing
			}

			// Set PEP User Id
			contentDisplayForm.setPepUserId(pepUserID);
			contentDisplayForm.setUserName(pepUserID);

			/** The changes for displaying and populating grouping content data
			 * Updated By: AFUSKJ2 6172016 */

			// System.out.println("contentPetDetailsFromIPC -->"+contentPetDetailsFromIPC);
			String orinOrGrouping = contentPetDetailsFromIPC.getOrinNumber();
			LOGGER.debug("orinOrGrouping -->" + orinOrGrouping);
			if (null != orinOrGrouping && (orinOrGrouping.trim()).length() == 7) {
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("Inside Grouping for populateGroupingContent");
				}
				populateGroupingContent(request, response, contentPetDetailsFromIPC);							
				
				/** Modification End. */
			} else {
				modelAndView = new ModelAndView(ContentScreenConstants.PAGE);
				// Logic for displaying the color family for Omnichannel color
				// Family,Secondary Color 1,Secondary Color 2,Secondary Color
				// 3,Secondary Color 4
				displayColorDataSet(contentDisplayForm, request, response);
				// Logic for getting the Style Information and displaying the
				// Style Information starts here
				//String complexpackOrinNumber = "232360371";
				displayStyleInformation(orinNumber, contentDisplayForm, request, response);

				// Logic for getting the Product Information and displaying the
				// Product Information starts here
				displayProductInformation(orinNumber, contentDisplayForm, request, response);

				// Logic for getting the Pet List and Displaying the Pets starts
				// here
				getPetList(roleNameFromIPC, orinNumber, contentDisplayForm, request, response);

				// Logic for displaying the Style Color Attribute data on click
				// of the Style Color Orin Number starts here

				displayStyleAttributeData(contentDisplayForm, request, response);

				// Logic for checking the webservice response starts here
				// checkWebserviceResponse();

				// Logic for getting the content management data and displaying
				// the content management data starts here
				displayContentManagement(orinNumber, contentDisplayForm, request, response);

				// Logic for getting the SKU data and displaying the SKU data
				// starts here

				displaySKUData(orinNumber, contentDisplayForm, request, response);

				// Logic for getting the Content History data and displaying the
				// Content History starts here

				displayContentHistoryData(orinNumber, contentDisplayForm, request, response);

				GlobalAttributesVO styleAttributes = contentDelegate.getStyleAttributesADSE(orinNumber);
				contentDisplayForm.setGlobalAttributesDisplay(styleAttributes);

				// Load Cars Brand List
				List<CarBrandVO> lstCarBrands = getCarsBrandList(orinNumberFromIPC, contentDisplayForm.getStyleInformationVO()
						.getSupplierSiteId());
				contentDisplayForm.setLstCarBrandVO(lstCarBrands);

				List<OmniChannelBrandVO> lstOmniBrands = getOmniChannelBrandList(orinNumberFromIPC, contentDisplayForm
						.getStyleInformationVO().getSupplierSiteId());
				contentDisplayForm.setLstOmniChannelBrandVO(lstOmniBrands); // TODO
																			// Need
																			// to
																			// pass
																			// supplier
																			// id

				// Get the IPH Categories to display on the screen;
				String mappedCategoryId = displayIPHCategories(orinNumber, contentDisplayForm, request, response);

				if (mappedCategoryId != null && request.getAttribute("dynamicCategoryId") == null) {
					LOGGER.info("Inside mapped IPH Category..............................  ");
					displayAttributesOnLoad(request, response, contentDisplayForm, orinNumber, mappedCategoryId);
					if ((lstCarBrands != null) && (lstCarBrands.size() > 0)) {
						String selectedBrand = lstCarBrands.get(0).getSelectedBrand();
						LOGGER.info("selectedBrand:" + selectedBrand); // JIRA
																		// VP9
						if (selectedBrand != null && !selectedBrand.trim().equalsIgnoreCase("")) {
							request.setAttribute("selectedCarbrand", selectedBrand);
						}
					} else {
						request.setAttribute("selectedCarbrand", "-1");
					}

					if ((lstOmniBrands != null) && (lstOmniBrands.size() > 0)) {
						String selectedOmni = lstOmniBrands.get(0).getSelectedBrand();
						LOGGER.info("selectedOmni:" + selectedOmni); // JIRA VP9
						if (selectedOmni != null && !selectedOmni.trim().equalsIgnoreCase("")) {
							// request.setAttribute("selectedOmnichannelbrand",
							// (lstOmniBrands.get(0).getSelectedBrand().split("-"))[0]);
							request.setAttribute("selectedOmnichannelbrand", (lstOmniBrands.get(0).getSelectedBrand()));
						}
					} else {
						request.setAttribute("selectedOmnichannelbrand", "-1");
					}

					if (styleAttributes != null) {
						if (styleAttributes.getBelkExclusive() != null
								&& !styleAttributes.getBelkExclusive().trim().equalsIgnoreCase("")) {
							request.setAttribute("selectedBelkExclusive", styleAttributes.getBelkExclusive());
						} else {
							request.setAttribute("selectedBelkExclusive", "-1");
						}
						if (styleAttributes.getChannelExclusive() != null
								&& !styleAttributes.getChannelExclusive().trim().equalsIgnoreCase("")) {
							request.setAttribute("selectedChannelExclusive", styleAttributes.getChannelExclusive());
						} else {
							request.setAttribute("selectedChannelExclusive", "-1");
						}
						if (styleAttributes.getBopis() != null && !styleAttributes.getBopis().trim().equalsIgnoreCase("")) {
							request.setAttribute("selectedBopis", styleAttributes.getBopis());
						} else {
							request.setAttribute("selectedBopis", "-1");
						}
						if (styleAttributes.getGwp() != null && !styleAttributes.getGwp().trim().equalsIgnoreCase("")) {
							request.setAttribute("selectedGWP", styleAttributes.getGwp());
						} else {
							request.setAttribute("selectedGWP", "No");
						}
						if (styleAttributes.getPwp() != null && !styleAttributes.getPwp().trim().equalsIgnoreCase("")) {
							request.setAttribute("selectedPWP", styleAttributes.getPwp());
						} else {
							request.setAttribute("selectedPWP", "No");
						}
						if (styleAttributes.getPyg() != null && !styleAttributes.getPyg().trim().equalsIgnoreCase("")) {
							request.setAttribute("selectedPYG", styleAttributes.getPyg());
						} else {
							request.setAttribute("selectedPYG", "No");
						}

					}
				}// else{
					// request.setAttribute("selectedGWP", "No");
					// request.setAttribute("selectedPWP", "No");
					// request.setAttribute("selectedPYG", "No");
					// }

				// Logic for displaying the Category Specific Attributes
				String dynamicCategoryId = null;
				if (request.getAttribute("dynamicCategoryId") != null) {
					LOGGER.info("Inside IPH Changed.......................................");

					dynamicCategoryId = (String) request.getAttribute("dynamicCategoryId");
					displayAttributesOnChange(request, response, contentDisplayForm, orinNumber, dynamicCategoryId);

					// Logic for highlighting the selected omnichannelbrand in
					// its drop down on change of IPH Drop Down
					if (request.getAttribute("omnichannelbrand") != null) {
						// LOGGER.info("omnichannelbrand .... "+request.getAttribute("omnichannelbrand"));
						request.setAttribute("selectedOmnichannelbrand", request.getAttribute("omnichannelbrand"));
					}

					// Logic for highlighting the selected carbrand in its drop
					// down on change of IPH Drop Down
					if (request.getAttribute("carbrand") != null) {
						// LOGGER.info("carbrand .... "+request.getAttribute("carbrand"));
						request.setAttribute("selectedCarbrand", request.getAttribute("carbrand"));
					}

					String channelExcValue = (String) request.getAttribute("selectedChannelExclusive");
					if (channelExcValue != null) {
						// LOGGER.info("channelExcValue .... "+channelExcValue);
						request.setAttribute("selectedChannelExclusive", channelExcValue);
					}

					String belkExcValue = (String) request.getAttribute("selectedBelkExclusive");
					if (belkExcValue != null) {
						// LOGGER.info("belkExcValue .... "+belkExcValue);
						request.setAttribute("selectedBelkExclusive", belkExcValue);
					}

					String selectedGWP = (String) request.getAttribute("selectedGWP");
					// LOGGER.info("selectedGWP.... "+selectedGWP);
					if (selectedGWP != null) {
						request.setAttribute("selectedGWP", selectedGWP);
					}
					String selectedPWP = (String) request.getAttribute("selectedPWP");
					// LOGGER.info("selectedPWP.... "+selectedPWP);
					if (selectedPWP != null) {
						request.setAttribute("selectedPWP", selectedPWP);
					}
					String selectedPYG = (String) request.getAttribute("selectedPYG");
					// LOGGER.info("selectedPYG.... "+selectedPYG);
					if (selectedPYG != null) {
						request.setAttribute("selectedPYG", selectedPYG);
					}
					String selectedBopis = (String) request.getAttribute("selectedBopis");
					// LOGGER.info("selectedBopis.... "+selectedBopis);
					if (selectedBopis != null) {
						request.setAttribute("selectedBopis", selectedBopis);
					}

				}

				/** Method modified to show the Copy attribute details in screen.
				 * 
				 * Modified For PIM Phase 2 - Regular Item Copy Attribute Date:
				 * 05/16/2016 Modified By: Cognizant */
				CopyAttributeVO copyAttributeVO = contentDelegate.getCopyAttributeDetails(orinNumber);
				contentDisplayForm.setCopyAttributeVO(copyAttributeVO);
				

			} // IF not null
		} else {
			modelAndView = new ModelAndView(null);
		}
		
		String copyContentMessage = (String) request.getPortletSession()
											.getAttribute(ContentScreenConstants.CONTENT_COPY_STATUS_MESSAGE);

		LOGGER.info("copyContentMessage" + copyContentMessage);
		if (copyContentMessage != null) {
			modelAndView.addObject(
					ContentScreenConstants.CONTENT_COPY_STATUS_MESSAGE,
					copyContentMessage);
			request.getPortletSession().setAttribute(
					ContentScreenConstants.CONTENT_COPY_STATUS_MESSAGE, null);

		}


		
		

		LOGGER.info(" --------------------------------------------------------- End of handleRenderRequest ---------------------------------------------------"
				+ new Date());
		return modelAndView;

	}

	/** This controller method is used to retrieve Grouping Content Attributes
	 * 
	 * @param request
	 * @param response
	 * @return */
	private void populateGroupingContent(RenderRequest request, RenderResponse response, ContentPetDetails contentPetDetailsFromIPC) {
		modelAndView = new ModelAndView(ContentScreenConstants.GROUPING_CONTENT_PAGE);

		LOGGER.info("Entering populateGroupingContent......");
		String pepUserID = null;
		String lanIdFromIPC = null;
		String pepUserIdFromIPC = null;
		String groupId = null;
		String roleNameFromIPC = null;

		groupId = contentPetDetailsFromIPC.getOrinNumber();
		LOGGER.info("orinNumberFromIPC----" + groupId);
		final String contentStatusFromIPC = contentPetDetailsFromIPC.getContentStatus();
		LOGGER.info("contentStatusFromIPC----" + contentStatusFromIPC);
		roleNameFromIPC = contentPetDetailsFromIPC.getUserData().getRoleName();


		if (contentPetDetailsFromIPC.getUserData().isInternal()) {
			pepUserID = lanIdFromIPC;
			if ((pepUserID == null) || pepUserID.equalsIgnoreCase("")) {
				pepUserID = contentPetDetailsFromIPC.getUserData().getBelkUser().getLanId();
			}
		}
		if (contentPetDetailsFromIPC.getUserData().isExternal()) {
			pepUserID = pepUserIdFromIPC;
			if ((pepUserID == null) || pepUserID.equalsIgnoreCase("")) {
				pepUserID = contentPetDetailsFromIPC.getUserData().getVpUser().getPepUserID();
			}
		}
		LOGGER.info("lanIdFromIPC----" + pepUserID);

		ContentForm contentDisplayForm = new ContentForm();

		String formSessionKey = request.getPortletSession().getId() + pepUserID;
		request.getPortletSession().setAttribute("formSessionKey", formSessionKey);
		request.getPortletSession().setAttribute(formSessionKey, contentDisplayForm);

		if (null != roleNameFromIPC) {
			contentDisplayForm.setRoleName(roleNameFromIPC);
		}

		contentDisplayForm.setPepUserId(pepUserID);
		contentDisplayForm.setUserName(pepUserID);

		// Populate Group information
		displayGroupInformation(groupId, contentDisplayForm, request, response);

		// Populate Omni Channel Brand List for Group
		List<OmniChannelBrandVO> omniChannelBrandList = populateGroupOmniChannelBrandList(groupId);
		contentDisplayForm.setLstOmniChannelBrandVO(omniChannelBrandList);
		if(!omniChannelBrandList.isEmpty())
			contentDisplayForm.setOmniBrandSelected(omniChannelBrandList.get(0).getSelectedBrand());
		// Populate CAR brand list for group
		List<CarBrandVO> carBrandList = populateGroupCarBrandList(groupId);
		contentDisplayForm.setLstCarBrandVO(carBrandList);
		if(!carBrandList.isEmpty())
			contentDisplayForm.setCarBrandSelected(carBrandList.get(0).getSelectedBrand());

		// Populate Group Details
		displayGroupingDetails(groupId, contentDisplayForm, request, response);

		// Populate Grouping Copy Attributes
		displayGroupCopyAttributes(groupId, contentDisplayForm, request, response);

		// Populate Grouping Soecific Data
		displayGroupingSpcificData(groupId, contentDisplayForm, request, response);

		// Populate Grouping Components
		displayGroupingComponent(groupId, contentDisplayForm, request, response);

		// Populate IPH Category Drop down value
		String mappedCategoryId = populateIPHCategorydropdown(groupId, contentDisplayForm, request, response);

		// Populate Content history data
		populatGroupContentHistry(groupId, contentDisplayForm, request, response);
		
		

		
		String dynamicCategoryId = (String) request.getAttribute("dynamicCategoryId");
		if (dynamicCategoryId != null) {
			LOGGER.info("Inside IPH Changed.......................................");
			
			LOGGER.info("dynamicCategoryId -->"+dynamicCategoryId);
			try {
				displayGroupIPHAttributesOnChange(request, response, contentDisplayForm, groupId, dynamicCategoryId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (mappedCategoryId!=null || dynamicCategoryId!=null){
			List<ItemPrimaryHierarchyVO> familyCategoryList = getFamilyTreeCategory(mappedCategoryId);
			LOGGER.info("familyCategoryList --------------- " + familyCategoryList.size());
			
			String finalCatIds = "";
			if ((familyCategoryList != null) && (familyCategoryList.size() > 0)) {
				int i = 1;
				for (ItemPrimaryHierarchyVO iph : familyCategoryList) {

					String categoryId = iph.getCategoryId();
					if (i == 1) {
						finalCatIds = categoryId;
					} else {
						finalCatIds = finalCatIds + "," + categoryId;
					}
					i++;
				}
			}
			
			displayCategorySpecificAttributeData(finalCatIds, contentDisplayForm, request, response, groupId);

			displayBlueMartiniSpecificAttributeData(finalCatIds, contentDisplayForm, request, response, groupId);
			if(mappedCategoryId!=null){
				request.setAttribute("selectedCategory", mappedCategoryId);
				request.setAttribute("selectedCarbrand", contentDisplayForm.getCarBrandSelected());
				request.setAttribute("selectedOmniBrand", contentDisplayForm.getOmniBrandSelected());
			}else{
				request.setAttribute("selectedCategory", dynamicCategoryId);
				// Logic for highlighting the selected omnichannelbrand in its drop
				// down on change of IPH Drop Down
				if (request.getAttribute("omnichannelbrand") != null) {
					// LOGGER.info("omnichannelbrand .... "+request.getAttribute("omnichannelbrand"));
					request.setAttribute("selectedOmnichannelbrand", request.getAttribute("omnichannelbrand"));
				}

				// Logic for highlighting the selected carbrand in its drop down on
				// change of IPH Drop Down
				if (request.getAttribute("carbrand") != null) {
					// LOGGER.info("carbrand .... "+request.getAttribute("carbrand"));
					request.setAttribute("selectedCarbrand", request.getAttribute("carbrand"));
				}

			}
		}

			
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		
		
		
		

		modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		LOGGER.info("Exiting populateGroupingContent......");
	}

	/** This method will populate group information
	 * 
	 * @param groupId
	 * @param contentDisplayForm
	 * @param request
	 * @param response */
	private void displayGroupInformation(String groupId, ContentForm contentDisplayForm, RenderRequest request, RenderResponse response) {
		LOGGER.error("Entering displayGroupInformation ..");
		try {
			StyleInformationVO styleInformationVO = contentDelegate.getGroupingInformation(groupId);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("styleInformationVO.getDepartmentId in controller:::" + styleInformationVO.getDepartmentId().trim());
			}

			// Populate Group department Details if department id not null
			if (!"".equals(styleInformationVO.getDepartmentId().trim())) {
				styleInformationVO = contentDelegate.getGroupingDepartmentDetails(styleInformationVO);
			}

			if (null != styleInformationVO) {
				contentDisplayForm.setStyleInformationVO(styleInformationVO);
			}
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} catch (PEPDelegateException e) {
			LOGGER.error("Error in Controller:---- " + e.getMessage());
			contentDisplayForm.setFetchStyleInfoErrorMessage(ContentScreenConstants.FETCH_STYLE_INFO_ERROR_MESSAGE);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}
	}

	/** This method populates omnichannel brand list for group
	 * 
	 * @param groupId
	 * @param contentDisplayForm
	 * @param request
	 * @param response */
	private List<OmniChannelBrandVO> populateGroupOmniChannelBrandList(String groupId) {
		List<OmniChannelBrandVO> groupOmniChannelBrandList = null;
		LOGGER.info("Entering populateGroupOmniChannelBrandList....");
		try {
			groupOmniChannelBrandList = contentDelegate.getGroupingOmniChannelBrand(groupId);
		} catch (PEPDelegateException e) {

			LOGGER.error("populateGroupOmniChannelBrandList :PEPDelegateException--> "+e);
		}
		LOGGER.info("Exiting populateGroupOmniChannelBrandList....");
		return groupOmniChannelBrandList;
	}

	/** This method populates car brand list for group
	 * 
	 * @param groupId
	 * @return */
	private List<CarBrandVO> populateGroupCarBrandList(String groupId) {
		List<CarBrandVO> groupCarBrandList = null;
		LOGGER.info("Entering populateGroupCarBrandList....");
		try {
			groupCarBrandList = contentDelegate.populateGroupCarBrandList(groupId);

		} catch (PEPDelegateException e) {
			LOGGER.error("populateGroupCarBrandList :PEPDelegateException--> "+e);
		}
		LOGGER.info("Exiting populateGroupCarBrandList....");
		return groupCarBrandList;
	}

	/** This method retrieves grouping details
	 * 
	 * @param groupId
	 * @param contentDisplayForm
	 * @param request
	 * @param response */
	private void displayGroupingDetails(String groupId, ContentForm contentDisplayForm, RenderRequest request, RenderResponse response) {
		try {
			ProductDetailsVO productDetailsVO = contentDelegate.getGroupingDetails(groupId);

			if (null != productDetailsVO) {
				contentDisplayForm.setProductDetailsVO(productDetailsVO);
			}

			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} catch (PEPDelegateException e) {
			LOGGER.error("Error in Controller:::: " + e.getMessage());
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}
	}

	/** This method retrieves Grouping Copy Attributes
	 * 
	 * @param groupId
	 * @param contentDisplayForm
	 * @param request
	 * @param response */
	private void displayGroupCopyAttributes(String groupId, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		try {
			CopyAttributeVO copyAttributeVO = contentDelegate.getGroupingCopyAttributes(groupId);

			if (null != copyAttributeVO) {
				contentDisplayForm.setCopyAttributeVO(copyAttributeVO);
			}
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} catch (PEPDelegateException e) {
			LOGGER.error("Error in Controller:::: " + e.getMessage());
			contentDisplayForm.setCopyAttributeData("No Copy Attribute Data Available");
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}
	}

	/** This method populates grouping specfic data
	 * 
	 * @param groupId
	 * @param contentDisplayForm
	 * @param request
	 * @param response
	 * @author AFUSKJ2 06172016 */
	public void displayGroupingSpcificData(String groupId, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		try {
			GroupingVO groupVo = contentDelegate.getGroupingSpecificAttributes(groupId);
			if (null != groupVo) {
				contentDisplayForm.setGrouping(groupVo);
			}
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} catch (PEPDelegateException e) {
			LOGGER.error("Error in delegate:::: " + e.getMessage());
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}
	}

	/** This method populates Grouping Component data
	 * 
	 * @param groupId
	 * @param contentDisplayForm
	 * @param request
	 * @param response
	 * @author AFUSKJ2 6/17/2016 */
	private void displayGroupingComponent(String groupId, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		List<GroupsFound> groupsList = new ArrayList<GroupsFound>();

		try {
			groupsList = contentDelegate.getGroupingComponents(groupId);
			StyleAndItsChildDisplay styleAndItsChildDisplay = displayGroupsAsParentChild(groupsList, contentDisplayForm, groupId);
			contentDisplayForm.setStyleAndItsChildDisplay(styleAndItsChildDisplay);
			contentDisplayForm.setGroupingNumber(groupId);
			contentDisplayForm.setGroupingType(contentDisplayForm.getStyleInformationVO().getGroupingType());
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} catch (PEPDelegateException e) {
			LOGGER.error("Error in Controller displayGroupingComponent:::: " + e.getMessage());
		}

	}

	/** This method populates IPH category drop downd list
	 * 
	 * @param groupId
	 * @param contentDisplayForm
	 * @param request
	 * @param response
	 * @author AFUSKJ2 6/17/2016 */
	private String populateIPHCategorydropdown(String groupId, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		List<ItemPrimaryHierarchyVO> iphCategoryList = null;
		List<ItemPrimaryHierarchyVO> iphList = null;
		String categoryId = null;
		String categoryName = null;
		try {

			Map<String, String> categoryReferenceData = new HashMap<String, String>();


				iphList = contentDelegate.selectedIPHCategorydropdown(groupId);
				// categoryMap = new LinkedHashMap<String,String>();

				if (iphList != null) {

					if (iphList.size() > 0) {
						ItemPrimaryHierarchyVO iphCategory = iphList.get(0);
						categoryId = iphCategory.getPetCategoryId();
						categoryName = iphCategory.getPetCategoryName();
					}
				}
			iphCategoryList = contentDelegate.populateIPHCategorydropdown(groupId,contentDisplayForm.getGroupingType());
			if(iphCategoryList!=null){
				for (ItemPrimaryHierarchyVO iphVo : iphCategoryList) {
					categoryReferenceData.put(iphVo.getMerchandiseCategoryId(), iphVo.getCategoryFullPath());
				}
			}
			
			if (iphList != null) {
				categoryReferenceData.put(categoryId, categoryName);
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("categoryId is getting selected -->"+categoryId);
				}
				request.setAttribute("selectedCategory", categoryId);
			}

			contentDisplayForm.setCategoryReferenceData(categoryReferenceData);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

		} catch (PEPDelegateException e) {
			LOGGER.error("Error in Controller::: " + e.getMessage());
		}
		return categoryId; 
	}

	/** This method populates grouping content history section data+
	 * 
	 * @param groupId
	 * @param contentDisplayForm
	 * @param request
	 * @param response
	 * @author AFUSKJ2 6/17/2016 */
	private void populatGroupContentHistry(String groupId, ContentForm contentDisplayForm, RenderRequest request,
			RenderResponse response) {
		List<ContentHistoryVO> contentHistoryList = new ArrayList<ContentHistoryVO>();

		try {
			contentHistoryList = contentDelegate.getGroupContentHistory(groupId);
			contentDisplayForm.setContentHistoryList(contentHistoryList);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		} catch (PEPDelegateException e) {
			LOGGER.error("Error in controller populateGroupContentHistory::: " + e.getMessage());
		}
	}

	/** This method populates IPH related data on change of IPH category drop
	 * down
	 * 
	 * @param request
	 * @param response
	 * @param contentDisplayForm
	 * @param groupId
	 * @param dynamicCategoryId
	 * @author AFUSKJ2 6/17/2016 
	 * @throws IOException */
	private void displayGroupIPHAttributesOnChange(RenderRequest request, RenderResponse response, ContentForm contentDisplayForm,
			String groupId, String dynamicCategoryId) throws IOException {
		LOGGER.info("Entering displayGroupIPHAttributesOnChange....................");
		
		String webServiceMessage =null;
		if (StringUtils.isNotBlank(dynamicCategoryId)) {

			 LOGGER.info("groupId--------" + groupId);
			final GroupDataObject dataObject = new GroupDataObject(groupId, dynamicCategoryId,contentDisplayForm.getUserName());
			final Gson gson = new Gson();
			// convert java object to JSON format,
			// and returned as JSON formatted string
			final String json = gson.toJson(dataObject);
			if(LOGGER.isDebugEnabled())
				LOGGER.debug("JSON in String ....."+json);
			webServiceMessage = contentDelegate.callIPHMappingWebService(json,true);

			request.setAttribute("selectedCategory", dynamicCategoryId);
		//final PrintWriter writer = response.getWriter();
		// to send the response to ajax call from jsp
		//writer.write(webServiceMessage);
			contentDisplayForm.setIphMappingMessage(webServiceMessage);
			
		}

	
	}

	/* (non-Javadoc)
	 * 
	 * @see org.springframework.web.portlet.mvc.ResourceAwareController#
	 * handleResourceRequest(javax.portlet.ResourceRequest,
	 * javax.portlet.ResourceResponse) */
	@SuppressWarnings("null")
	@Override
	public ModelAndView handleResourceRequest(ResourceRequest request, ResourceResponse response) throws Exception {

		String webServiceMessage = null;
		modelAndView = new ModelAndView(ContentScreenConstants.PAGE);
		ContentForm contentDisplayForm = new ContentForm();
		final String categoryKey = request.getParameter("categoryKey");
		LOGGER.info("Warning-------------------------------------------------" + categoryKey);
		if (StringUtils.isNotBlank(categoryKey)) {
			// LOGGER.info("@ResourceMapping-categoryKey--- " + categoryKey);
			// LOGGER.info("categoryKey---- " + categoryKey);
			final String petId = request.getParameter("petIdForWebservice");
			// LOGGER.info("petId--------" + petId);
			final DataObject dataObject = new DataObject(petId, categoryKey);
			final Gson gson = new Gson();
			// convert java object to JSON format,
			// and returned as JSON formatted string
			final String json = gson.toJson(dataObject);
			webServiceMessage = contentDelegate.callIPHMappingWebService(json,false);
			final PrintWriter writer = response.getWriter();
			// to send the response to ajax call from jsp
			writer.write(webServiceMessage);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);

		}

		return modelAndView;
	}

	/** Inits the binder.
	 * 
	 * @param request the request
	 * @param binder the binder
	 * @throws Exception the exception */
	@InitBinder
	public final void initBinder(final PortletRequest request, final PortletRequestDataBinder binder) throws Exception {
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub

	}

	/** Iph mapping response.
	 * 
	 * @param orinNumber the orin number
	 * @param dynamicCategoryId the dynamic category id
	 * @return the string */
	private String iphMappingResponse(String orinNumber, String dynamicCategoryId, String skuIndicator) {

		LOGGER.info("orinNumber----" + orinNumber);

		LOGGER.info("dynamicCategoryId----" + dynamicCategoryId);
		String iphMappingFlag = "";

		IPHMappingVO iphMapping = new IPHMappingVO();
		if (StringUtils.isNotBlank(dynamicCategoryId)) {
			iphMapping.setCategoryId(dynamicCategoryId);
		}

		if (StringUtils.isNotBlank(orinNumber)) {
			iphMapping.setPetId(orinNumber);
		}

		iphMapping.setSKUindicator(skuIndicator);

		final Gson gson = new Gson();
		// convert from JSON to String
		final String iphMappingRequest = gson.toJson(iphMapping);

		LOGGER.info("iphMappingRequest----" + iphMappingRequest);
		final String webserviceResponseMessage = contentDelegate.callIPHMappingWebService(iphMappingRequest,false);
		LOGGER.info("IPH Mapping webserviceResponseMessage----" + webserviceResponseMessage);

		if (StringUtils.isNotBlank(webserviceResponseMessage) && (webserviceResponseMessage.contains("successfully"))) {
			iphMappingFlag = "true";
		} else {
			iphMappingFlag = "false";
		}

		// End of Logic for IPH Mapping
		return iphMappingFlag;
	}

	/** Iph mapping response.
	 * 
	 * @param orinNumber the orin number
	 * @param dynamicCategoryId the dynamic category id
	 * @return the string */
	private void callAsyncIphMappingResponse(String orinNumber, String dynamicCategoryId, String skuIndicator) {

		LOGGER.info("callAsyncIphMappingResponse----");

		String iphMappingFlag = "";

		IPHMappingVO iphMapping = new IPHMappingVO();
		if (StringUtils.isNotBlank(dynamicCategoryId)) {
			iphMapping.setCategoryId(dynamicCategoryId);
		}

		if (StringUtils.isNotBlank(orinNumber)) {
			iphMapping.setPetId(orinNumber);
		}

		iphMapping.setSKUindicator(skuIndicator);

		final Gson gson = new Gson();
		// convert from JSON to String
		final String iphMappingRequest = gson.toJson(iphMapping);
		LOGGER.info("callAsyncIphMappingResponse----" + iphMappingRequest);
		contentDelegate.callasyncIPHMappingWebService(iphMappingRequest);
		LOGGER.info("IPH Mapping callAsyncIphMappingResponse----");

	}

	/** Checks if is disable save button flag.
	 * 
	 * @return the disableSaveButtonFlag */
	public boolean isDisableSaveButtonFlag() {
		return disableSaveButtonFlag;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	/** Checks if is style data submission flag.
	 * 
	 * @return the styleDataSubmissionFlag */
	public boolean isStyleDataSubmissionFlag() {
		return styleDataSubmissionFlag;
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/** Save content pet attributes.
	 * 
	 * @param request the request
	 * @param response the response */
	@ResourceMapping("saveContentPetAttributes")
	private void saveContentPetAttributes(ResourceRequest request, ResourceResponse response) {
		LOGGER.info("start of saveContentPetAttributes...");
		boolean finalStatus = false;

		String iphCategoryDropDownId = request.getParameter("iphCategoryDropDownId");
		final String orinNumber = request.getParameter("stylePetOrinNumber");
		String submitClicked = request.getParameter("submitClicked");
		String updateStatus = ContentScreenConstants.EMPTY;

		ContentPetDetails contentPetDetails = (ContentPetDetails) request.getPortletSession().getAttribute(
				ContentScreenConstants.CONTENT_PET_DETAILS);
		LOGGER.info("Inside handle saveContentPetAttributes...contentPetDetails...getContentStatus"
				+ contentPetDetails.getContentStatus());
		LOGGER.info("Inside handle saveContentPetAttributes...contentPetDetails...getContentStatus"
				+ contentPetDetails.getContentStatus());
		LOGGER.info("Inside handle saveContentPetAttributes...contentPetDetails...getOrinNumber" + contentPetDetails.getOrinNumber());
		LOGGER.info("Inside handle saveContentPetAttributes...contentPetDetails...getVpUser" + contentPetDetails.getVpUser());
		LOGGER.info("Inside handle saveContentPetAttributes...contentPetDetails...getUserData" + contentPetDetails.getUserData());
		LOGGER.info("Inside handle saveContentPetAttributes...contentPetDetails...getBelkUser" + contentPetDetails.getBelkUser());

		UserData custuser = contentPetDetails.getUserData();
		String pepUserId = null;
		if (custuser != null) {
			LOGGER.info("custuser content  update  ****************" + custuser.isExternal());
			if (custuser.isExternal()) {

				pepUserId = custuser.getVpUser().getUserEmailAddress();
			} else {
				pepUserId = custuser.getBelkUser().getLanId();
			}
		}
		LOGGER.info("custuser content  pepUserId  ****************" + pepUserId);

		LOGGER.info("iphCategoryDropDownId Save---------------" + iphCategoryDropDownId);
		LOGGER.info("orinNumber Save---------------" + orinNumber);

		JSONObject jsonObj = null;
		JSONArray jsonArrayPetDtls = new JSONArray();
		jsonObj = new JSONObject();

		try {
			finalStatus = saveContentStyleLevelAttributes(request, response, pepUserId);
			if (!StringUtils.isEmpty(submitClicked) && finalStatus) {

				LOGGER.info("Grouping ID:------- "
						+ request.getParameter("groupId"));
				LOGGER.info("Group Type:------- "
						+ request.getParameter("groupType"));
				LOGGER.info("Overall Status:------- "
						+ request.getParameter("groupOverallStatus"));
				LOGGER.info("User ID:------- "
						+ pepUserId);
				String groupID = (String) request
						.getParameter(ContentScreenConstants.GROUP_ID);
				String groupType = (String) request
						.getParameter(ContentScreenConstants.GROUP_TYPE);
				String overallStatus = (String) request
						.getParameter(ContentScreenConstants.OVERALL_STATUS_PARAM);
				updateStatus = contentDelegate.callUpdateContentStatusService(
						groupID, groupType, overallStatus, pepUserId);
			}

			if (finalStatus) {
				jsonObj.put("UpdateStatus", "Success");
				if (!StringUtils.isEmpty(updateStatus)) {
					jsonObj.put(ContentScreenConstants.CONTENT_STATUS_UPDATE,
							updateStatus);
				}
			} else {
				jsonObj.put("UpdateStatus", "Failed");
				jsonObj.put(ContentScreenConstants.CONTENT_STATUS_UPDATE, 
						updateStatus);
			}

			jsonArrayPetDtls.put(jsonObj);
			LOGGER.info("Locked Status end  " + jsonArrayPetDtls.toString());
			response.getWriter().write(jsonArrayPetDtls.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(PEPDelegateException e)
		{
			LOGGER.error("Exception in saveContentPetAttributes. " + e);
		}
	}

	/** Save content style level attributes.
	 * 
	 * @param request the request
	 * @param response the response
	 * @return true, if successful */
	private boolean saveContentStyleLevelAttributes(ResourceRequest request, ResourceResponse response, String pepUserId) {
		JSONObject jsonObj = null;
		String responseMsgCode = "";
		JSONObject jsonObjectRes = null;
		String webserviceResponseMessage = "";
		boolean updateStatus = false;
		AttributesBean attributesBeanProductName = null;

		List<AttributesBean> beanList = null;
		final ItemIdBean finalPIMAndBMAttributesBean = new ItemIdBean();
		final String stylePetId = request.getParameter("stylePetOrinNumber");
		beanList = new ArrayList<AttributesBean>();// Final Bean List

		String dropValue = request.getParameter("dropdownhidden_id1");
		try {
			if ((dropValue != null) && StringUtils.isNotEmpty(dropValue)) {
				String dropDwonAttName[] = dropValue.split("~");
				if (dropDwonAttName != null) {
					for (int i = 0; i < dropDwonAttName.length; i++) {
						String innerValue = dropDwonAttName[i];
						if (innerValue != null) {
							String values[] = innerValue.split("#");
							// row[3]!=null?row[3].toString():null
							if ((values != null) && (values.length > 0)) {
								String attributeName = values[0] != null ? values[0] : null;
								String attributeXPath = values[1] != null ? values[1] : null;
								String userSelected = values[2] != null ? values[2] : null;
								if (userSelected != null && !userSelected.equalsIgnoreCase("-1")) {
									userSelected = StringEscapeUtils.escapeHtml4(userSelected);
									if (userSelected.equals("0"))
										userSelected = "N/A";
									attributesBeanProductName = new AttributesBean(attributeXPath, userSelected);
									beanList.add(attributesBeanProductName);
								}
							}
						}
					}

				}
			}// End Main PIM MultiSelect

			String productAttributeRadionValues = request.getParameter("pimradioValues");
			if ((productAttributeRadionValues != null) && StringUtils.isNotEmpty(productAttributeRadionValues)) {
				String radionAttributeName[] = productAttributeRadionValues.split("~");
				if (radionAttributeName != null) {
					for (int i = 0; i < radionAttributeName.length; i++) {
						String innerValue = radionAttributeName[i];
						if (innerValue != null) {
							String values[] = innerValue.split("#");
							if ((values != null) && (values.length > 0)) {
								String attributeName = values[0] != null ? values[0] : null;
								String attributeXPath = values[1] != null ? values[1] : null;
								String enteredValues = "";
								if (values.length == 3)// The user has entered
														// the values in the
														// text fields
								{
									String userSelected = values[2] != null ? values[2] : null;
									if (StringUtils.isNotBlank(userSelected)) {
										enteredValues = userSelected;
									} else {
										enteredValues = "";
									}
								} else {// The user did not enter the values in
										// the text fields,update the pim
										// database text field to blank
									enteredValues = "";
								}
								attributesBeanProductName = new AttributesBean(attributeXPath, enteredValues);
								beanList.add(attributesBeanProductName);
							}
						}
					}
				}
			} // End of PIM Radio buttons

			String productAttributeTextFieldValues = request.getParameter("textFieldsValues");
			if ((productAttributeTextFieldValues != null) && StringUtils.isNotEmpty(productAttributeTextFieldValues)) {
				String textFieldAttributeName[] = productAttributeTextFieldValues.split("~");
				if (textFieldAttributeName != null) {
					for (int i = 0; i < textFieldAttributeName.length; i++) {
						String innerValue = textFieldAttributeName[i];
						if (innerValue != null) {
							String values[] = innerValue.split("#");
							if ((values != null) && (values.length > 0)) {
								String attributeName = values[0] != null ? values[0] : null;
								String attributeXPath = values[1] != null ? values[1] : null;
								String enteredValues = "";
								if (values.length == 3)// The user has entered
														// the values in the
														// text fields
								{
									String userSelected = values[2] != null ? values[2] : null;
									if (StringUtils.isNotBlank(userSelected)) {
										enteredValues = userSelected;
									} else {
										enteredValues = "";
									}
								} else {// The user did not enter the values in
										// the text fields,update the pim
										// database text field to blank
									enteredValues = "";
								}
								attributesBeanProductName = new AttributesBean(attributeXPath, enteredValues);
								beanList.add(attributesBeanProductName);
							}
						}
					}
				}
			} // End of PIM Text Fields

			String bmDropValue = request.getParameter("blueMartiniDropdownhidden_id1");
			if ((bmDropValue != null) && StringUtils.isNotEmpty(bmDropValue)) {
				String dropDwonAttName[] = bmDropValue.split("~");
				if (dropDwonAttName != null) {
					for (int i = 0; i < dropDwonAttName.length; i++) {
						String innerValue = dropDwonAttName[i];
						if (innerValue != null) {
							String values[] = innerValue.split("#");
							// row[3]!=null?row[3].toString():null
							if ((values != null) && (values.length > 0)) {
								String attributeName = values[0] != null ? values[0] : null;
								String attributeXPath = values[1] != null ? values[1] : null;
								String userSelected = values[2] != null ? values[2] : null;
								if (userSelected != null && !userSelected.equalsIgnoreCase("-1")) {
									userSelected = StringEscapeUtils.escapeHtml4(userSelected);
									if (userSelected.equals("0"))
										userSelected = "N/A";
									attributesBeanProductName = new AttributesBean(attributeXPath, userSelected);
									beanList.add(attributesBeanProductName);
								}
							}
						}
					}
				}
			} // End of BM MultiSelect attributes

			String bmProductAttributeRadionValues = request.getParameter("bmradioValues");
			if ((bmProductAttributeRadionValues != null) && StringUtils.isNotEmpty(bmProductAttributeRadionValues)) {
				String radionAttributeName[] = bmProductAttributeRadionValues.split("~");
				if (radionAttributeName != null) {
					for (int i = 0; i < radionAttributeName.length; i++) {
						String innerValue = radionAttributeName[i];
						if (innerValue != null) {
							String values[] = innerValue.split("#");
							if ((values != null) && (values.length > 0)) {
								String attributeName = values[0] != null ? values[0] : null;
								String attributeXPath = values[1] != null ? values[1] : null;
								String enteredValues = "";
								if (values.length == 3)// The user has entered
														// the values in the
														// text fields
								{
									String userSelected = values[2] != null ? values[2] : null;
									if (StringUtils.isNotBlank(userSelected)) {
										enteredValues = userSelected;
									} else {
										enteredValues = "";
									}
								} else {// The user did not enter the values in
										// the text fields,update the pim
										// database text field to blank
									enteredValues = "";
								}
								attributesBeanProductName = new AttributesBean(attributeXPath, enteredValues);
								beanList.add(attributesBeanProductName);

							}
						}
					}

				}
			} // End of BM Radio attributes

			String bmAttributeTextFieldValues = request.getParameter("bmTextFieldsValues");
			if ((bmAttributeTextFieldValues != null) && StringUtils.isNotEmpty(bmAttributeTextFieldValues)) {
				String textFieldAttributeName[] = bmAttributeTextFieldValues.split("~");
				if (textFieldAttributeName != null) {
					for (int i = 0; i < textFieldAttributeName.length; i++) {
						String innerValue = textFieldAttributeName[i];
						if (innerValue != null) {
							String values[] = innerValue.split("#");
							if ((values != null) && (values.length > 0)) {
								String attributeName = values[0] != null ? values[0] : null;
								String attributeXPath = values[1] != null ? values[1] : null;
								String enteredValues = "";
								if (values.length == 3)// The user has entered
														// the values in the
														// text fields
								{
									String userSelected = values[2] != null ? values[2] : null;
									if (StringUtils.isNotBlank(userSelected)) {
										enteredValues = userSelected;
									} else {
										enteredValues = "";
									}
								} else {// The user did not enter the values in
										// the text fields,update the pim
										// database text field to blank
									enteredValues = "";
								}
								attributesBeanProductName = new AttributesBean(attributeXPath, enteredValues);
								beanList.add(attributesBeanProductName);
							}
						}
					}
				}
			} // End of BM Text Field attributes

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("dropValue-->" + dropValue + "|productAttributeRadionValues-->" + productAttributeRadionValues
						+ " |productAttributeTextFieldValues-->" + productAttributeTextFieldValues + " |bmDropValue-->" + bmDropValue
						+ " |bmProductAttributeRadionValues-->" + bmProductAttributeRadionValues + " |bmAttributeTextFieldValues-->"
						+ bmAttributeTextFieldValues);
			}
			// Style/Global Attributes....
			String styleReq = request.getParameter("styleReq");
			String complexPackReq = request.getParameter("complexPackReq");
			// final String stylePetId =
			// request.getParameter("stylePetOrinNumber");

			final String productName = request.getParameter("productName");
			final String productDescription = request.getParameter("productDescription");
			final String omniBrandCode = request.getParameter("selectedOmniBrand");
			final String carsBrandCode = request.getParameter("selectedCarsBrand");
			final String belkExclusive = request.getParameter("belkExclusive");
			final String gWPValue = request.getParameter("GWPValue");
			final String pWPValue = request.getParameter("PWPValue");
			final String pYGValue = request.getParameter("PYGValue");
			final String channelExclusive = request.getParameter("channelExclusive");
			final String bopisValue = request.getParameter("bopisSelectedValue");

			LOGGER.info("omniBrandCode:" + omniBrandCode); // JIRA VP9
			LOGGER.info("carsBrandCode:" + carsBrandCode); // JIRA VP9
			String carsBrandCodeEncoded = StringEscapeUtils.escapeHtml4(carsBrandCode); // JIRA
																						// VP9
			LOGGER.info("carsBrandCodeEncoded:" + carsBrandCodeEncoded); // JIRA
																			// VP9

			if (StringUtils.isNotBlank(styleReq) && styleReq.equalsIgnoreCase("Style")) {

				final String productNameXpath = ContentScreenConstants.PRODUCT_NAME_XPATH;
				final String productDescriptionXpath = ContentScreenConstants.PRODUCT_DESCRIPTION_XPATH;
				final String omniChannelBrandXpath = ContentScreenConstants.OMNICHANNEL_BRAND_XPATH;
				final String carsBrandXpath = ContentScreenConstants.CARS_BRAND_XPATH;
				final String belkExclusiveXpath = ContentScreenConstants.BELK_EXCLUSIVE_XPATH;
				final String gWPValueXpath = ContentScreenConstants.GWP_XPATH;
				final String pWPValueXpath = ContentScreenConstants.PWP_XPATH;
				final String pYGValueXpath = ContentScreenConstants.PYG_XPATH;
				final String channelExclusiveXpath = ContentScreenConstants.CHANNEL_EXCLUSIVE_XPATH;
				final String bopisXpath = ContentScreenConstants.BOPIS_XPATH;

				final AttributesBean attributesBeanProductNameStyle = new AttributesBean(productNameXpath, productName);
				final AttributesBean attributesBeanProductDescriptionStyle = new AttributesBean(productDescriptionXpath,
						productDescription);
				beanList.add(attributesBeanProductNameStyle);
				beanList.add(attributesBeanProductDescriptionStyle);

				if (StringUtils.isNotBlank(belkExclusive)) {
					final AttributesBean attributesBeanBelkExclusive = new AttributesBean(belkExclusiveXpath, belkExclusive);
					beanList.add(attributesBeanBelkExclusive);
				}
				if (StringUtils.isNotBlank(channelExclusive) && !channelExclusive.equalsIgnoreCase("-1")) {
					final AttributesBean attributesBeanChannelExclusive = new AttributesBean(channelExclusiveXpath, channelExclusive);
					beanList.add(attributesBeanChannelExclusive);
				}
				if (StringUtils.isNotBlank(bopisValue) && !bopisValue.equalsIgnoreCase("-1")) {
					final AttributesBean attributesBopisExclusive = new AttributesBean(bopisXpath, bopisValue);
					beanList.add(attributesBopisExclusive);
				}
				if (StringUtils.isNotBlank(gWPValue)) {
					final AttributesBean attributesBeangWP = new AttributesBean(gWPValueXpath, gWPValue);
					beanList.add(attributesBeangWP);
				}
				if (StringUtils.isNotBlank(pWPValue)) {
					final AttributesBean attributesBeanpWPValue = new AttributesBean(pWPValueXpath, pWPValue);
					beanList.add(attributesBeanpWPValue);
				}
				if (StringUtils.isNotBlank(pYGValue)) {
					final AttributesBean attributesBeanpYGValue = new AttributesBean(pYGValueXpath, pYGValue);
					beanList.add(attributesBeanpYGValue);
				}
				if ((omniBrandCode != null) && StringUtils.isNotBlank(omniBrandCode) && !omniBrandCode.equalsIgnoreCase("-1")) {
					final AttributesBean attributesOmniBrand = new AttributesBean(omniChannelBrandXpath, (omniBrandCode));
					beanList.add(attributesOmniBrand);
				}
				if (( carsBrandCodeEncoded!= null) && StringUtils.isNotBlank(carsBrandCodeEncoded)
						&& !carsBrandCodeEncoded.equalsIgnoreCase("-1")) { // JIRA
																			// VP9
					final AttributesBean attributeCarsBrandCode = new AttributesBean(carsBrandXpath, (carsBrandCodeEncoded)); // JIRA
																																// VP9
					beanList.add(attributeCarsBrandCode);
				}

			} else if (StringUtils.isNotBlank(complexPackReq) && complexPackReq.equalsIgnoreCase("Complex Pack")) {
				final String productNameXpath = ContentScreenConstants.PRODUCT_NAME_COMPLEX_PACK_XPATH;
				final String productDescriptionXpath = ContentScreenConstants.PRODUCT_DESCRIPTION_COMPLEX_PACK_XPATH;
				final String omniChannelBrandXpath = ContentScreenConstants.OMNICHANNEL_BRAND_COMPLEX_PACK_XPATH;
				final String carsBrandXpath = ContentScreenConstants.CARS_BRAND_COMPLEX_PACK_XPATH;
				final String belkExclusiveXpath = ContentScreenConstants.BELK_EXCLUSIVE_PACK_XPATH;
				final String gWPValueXpath = ContentScreenConstants.GWP_PACK_XPATH;
				final String pWPValueXpath = ContentScreenConstants.PWP_PACK_XPATH;
				final String pYGValueXpath = ContentScreenConstants.PYG_PACK_XPATH;
				final String channelExclusiveXpath = ContentScreenConstants.CHANNEL_EXCLUSIVE_PACK_XPATH;
				final String bopisXpath = ContentScreenConstants.BOPIS_PACK_XPATH;

				// form JSON request to web service

				final AttributesBean attributesBeanProductNamePack = new AttributesBean(productNameXpath, productName);
				final AttributesBean attributesBeanProductDescriptionPack = new AttributesBean(productDescriptionXpath,
						productDescription);

				beanList.add(attributesBeanProductNamePack);
				beanList.add(attributesBeanProductDescriptionPack);

				if (StringUtils.isNotBlank(belkExclusive)) {
					final AttributesBean attributesBeanBelkExclusive = new AttributesBean(belkExclusiveXpath, belkExclusive);
					beanList.add(attributesBeanBelkExclusive);
				}
				if (StringUtils.isNotBlank(channelExclusive) && !channelExclusive.equalsIgnoreCase("-1")) {
					final AttributesBean attributesBeanChannelExclusive = new AttributesBean(channelExclusiveXpath, channelExclusive);
					beanList.add(attributesBeanChannelExclusive);
				}
				if (StringUtils.isNotBlank(bopisValue) && !bopisValue.equalsIgnoreCase("-1")) {
					final AttributesBean attributesBopisExclusive = new AttributesBean(bopisXpath, bopisValue);
					beanList.add(attributesBopisExclusive);
				}
				if (StringUtils.isNotBlank(gWPValue)) {
					final AttributesBean attributesBeangWP = new AttributesBean(gWPValueXpath, gWPValue);
					beanList.add(attributesBeangWP);
				}
				if (StringUtils.isNotBlank(pWPValue)) {
					final AttributesBean attributesBeanpWPValue = new AttributesBean(pWPValueXpath, pWPValue);
					beanList.add(attributesBeanpWPValue);
				}
				if (StringUtils.isNotBlank(pYGValue)) {
					final AttributesBean attributesBeanpYGValue = new AttributesBean(pYGValueXpath, pYGValue);
					beanList.add(attributesBeanpYGValue);
				}
				// Added by Sriharsha
				if ((omniBrandCode != null) && StringUtils.isNotBlank(omniBrandCode) && !omniBrandCode.equalsIgnoreCase("-1")) {
					final AttributesBean attributesOmniBrand = new AttributesBean(omniChannelBrandXpath, (omniBrandCode));
					beanList.add(attributesOmniBrand);
				}
				if ((carsBrandCodeEncoded != null) && StringUtils.isNotBlank(carsBrandCodeEncoded)
						&& !carsBrandCodeEncoded.equalsIgnoreCase("-1")) { // JIRA
																			// VP9
					final AttributesBean attributeCarsBrandCode = new AttributesBean(carsBrandXpath, (carsBrandCodeEncoded)); // JIRA
																																// VP9
					beanList.add(attributeCarsBrandCode);
				}
			}
			// Added for Grouping Save 
			else{
				//Setting the message as blank
				String formSessionKey = (String) request.getPortletSession().getAttribute("formSessionKey");
				ContentForm contentDisplayForm = (ContentForm) request.getPortletSession().getAttribute(formSessionKey);
				if(contentDisplayForm!=null){
					contentDisplayForm.setIphMappingMessage("");
					contentDisplayForm.setCopyContentMessage("");
				}
				request.getPortletSession().setAttribute(formSessionKey, contentDisplayForm);
				

				final String omniChannelBrandXpath = ContentScreenConstants.OMNICHANNEL_BRAND_XPATH;
				final String carsBrandXpath = ContentScreenConstants.CARS_BRAND_XPATH;
				final String omniGroupChannelBrandXpath = ContentScreenConstants.OMNICHANNEL_BRAND_XPATH_GROUP;
				final String carsGroupBrandXpath = ContentScreenConstants.CARS_BRAND_XPATH_GROUP;

				
					final AttributesBean attributesBeanProductNameStyle = new AttributesBean(ContentScreenConstants.GROUP_NAME_XPATH, productName);
					final AttributesBean attributesBeanProductDescriptionStyle = new AttributesBean(ContentScreenConstants.GROUP_DESC_XPATH,
							productDescription);
					beanList.add(attributesBeanProductNameStyle);
					beanList.add(attributesBeanProductDescriptionStyle);
				
					AttributesBean attributesOmniBrand =null;
					AttributesBean attributeCarsBrandCode =null;
				if ((omniBrandCode != null) && StringUtils.isNotBlank(omniBrandCode) && !omniBrandCode.equalsIgnoreCase("-1")) {
					if(contentDisplayForm!=null && !ContentScreenConstants.CONSOLIDATED_PRODUCT_GROUP_TYPE.equals(contentDisplayForm.getGroupingType())){
						attributesOmniBrand = new AttributesBean(omniGroupChannelBrandXpath, (omniBrandCode));
					}else{
						attributesOmniBrand = new AttributesBean(omniChannelBrandXpath, (omniBrandCode));
					}
					beanList.add(attributesOmniBrand);
				}
				if ((carsBrandCodeEncoded != null) && StringUtils.isNotBlank(carsBrandCodeEncoded)
						&& !carsBrandCodeEncoded.equalsIgnoreCase("-1")) { 
					if(contentDisplayForm!=null && !ContentScreenConstants.CONSOLIDATED_PRODUCT_GROUP_TYPE.equals(contentDisplayForm.getGroupingType())){
						
						attributeCarsBrandCode = new AttributesBean(carsGroupBrandXpath, (carsBrandCodeEncoded));
					}else{
						attributeCarsBrandCode = new AttributesBean(carsBrandXpath, (carsBrandCodeEncoded));
					}
					
					beanList.add(attributeCarsBrandCode);
				}
			}

			if ("groupingSave".equalsIgnoreCase(request.getParameter("groupingSave"))) {

				jsonObj = populateUpdateContentJson(stylePetId, "", pepUserId, beanList);

			} else {
				// Final adding...
				finalPIMAndBMAttributesBean.setItemId(stylePetId);
				finalPIMAndBMAttributesBean.setList(beanList);
			}

			// Call Web service...
			final Gson gson = new Gson();
			// convert from JSON to String
			final String createContentWebServiceReq = gson.toJson(finalPIMAndBMAttributesBean);

			// request to web service
			LOGGER.info("Final Save Request object :" + createContentWebServiceReq);
			// call web service and read response
			LOGGER.info("Before Calling PIM ::  " + new Date());
			System.out.println("createContentWebServiceReq::: " + createContentWebServiceReq);
			LOGGER.debug("grouoping save type::::: " + request.getParameter("groupingSave"));
			if ("groupingSave".equalsIgnoreCase(request.getParameter("groupingSave"))) {
				
				LOGGER.debug("in groupingService Save.");
				webserviceResponseMessage = contentDelegate.createGroupContentWebService(jsonObj);
				LOGGER.debug("in groupingService Save." + webserviceResponseMessage);
				/** Extract Service message **/
				if (null != webserviceResponseMessage && !("").equals(webserviceResponseMessage)) {
					jsonObjectRes = new JSONObject(webserviceResponseMessage);
				}
				if (null != jsonObjectRes) {
					responseMsgCode = jsonObjectRes.getString(ContentScreenConstants.MSG_CODE);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("groupingService.webserviceResponseMessage.responseMsgCode-->" + responseMsgCode);
				}
				if (responseMsgCode.equals(ContentScreenConstants.SUCCESS_CODE)) {
					updateStatus = true;
				} else {
					updateStatus = false;
				}
				// final String resMsgSplitColor =
			} else {
				webserviceResponseMessage = contentDelegate.createContentWebService(createContentWebServiceReq);
				if (webserviceResponseMessage != null && webserviceResponseMessage.trim().equalsIgnoreCase("SUCCESS")) {
					updateStatus = true;
				} else {
					updateStatus = false;
				}
			}
			LOGGER.info("After Calling PIM ::  " + new Date());
			LOGGER.info("Final Save Status :" + updateStatus);
		} catch (Exception ex) {
			LOGGER.error("inside saveContentStyleLevelAttributes Exception-->" + ex);
		}
		return updateStatus;
	}

	/** Method to pass JSON Array to call the update Group Content.
	 * 
	 * @param groupIdRes
	 * @param groupType
	 * @param updatedBy
	 * @param selectedSplitAttributeList
	 * @return jsonObj
	 * @author Cognizant */
	public final JSONObject populateUpdateContentJson(final String groupId, final String groupType, final String modifiedBy,
			final List<AttributesBean> attributeList) {
		LOGGER.info("Entering populateUpdateContentJson-->.");

		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObjComponent = null;
		AttributesBean attributesBean = null;
		JSONArray jsonArray = new JSONArray();
		try {

			for (int i = 0; i < attributeList.size(); i++) {
				attributesBean = attributeList.get(i);
				String attrName = attributesBean.getAttributeName();
				String attrVal = attributesBean.getAttributeValue();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Group Content-attrName-->" + attrName + " <--attrVal-->" + attrVal);
				}
				jsonObjComponent = new JSONObject();
				jsonObjComponent.put(ContentScreenConstants.SERVICE_ATTRIBUTE_NAME, attrName);
				jsonObjComponent.put(ContentScreenConstants.SERVICE_ATTRIBUTE_VALUE, attrVal);
				jsonArray.put(jsonObjComponent);
			}

			jsonObj.put(ContentScreenConstants.SERVICE_ATTRIBUTE_GROUP_ID, groupId);
			jsonObj.put(ContentScreenConstants.SERVICE_ATTRIBUTE_GROUP_TYPE, groupType);
			jsonObj.put(ContentScreenConstants.SERVICE_ATTRIBUTE_MODIFIED_BY, modifiedBy);
			jsonObj.put(ContentScreenConstants.SERVICE_ATTRIBUTE_LIST, jsonArray);
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		LOGGER.info("Exiting populateUpdateContentJson-->" + jsonObj);
		return jsonObj;
	}

	@ResourceMapping("saveContentColorAttributes")
	private void saveContentPetColortAttributes(ResourceRequest request, ResourceResponse response) {
		LOGGER.info("start of saveContentPetColortAttributes...");
		boolean updateSaveStatus = false;

		String formSessionKey = (String) request.getPortletSession().getAttribute("formSessionKey");
		ContentForm contentDisplayForm = (ContentForm) request.getPortletSession().getAttribute(formSessionKey);

		String dynamicCategoryId = (String) request.getAttribute("dynamicCategoryId");
		String iphCategoryDropDownId = request.getParameter("iphCategoryDropDownId");
		final String orinNumber = request.getParameter("stylePetOrinNumber");

		LOGGER.info("iphCategoryDropDownId Save---------------" + iphCategoryDropDownId);
		LOGGER.info("orinNumber Save---------------" + orinNumber);

		JSONObject jsonObj = null;
		JSONArray jsonArrayPetDtls = new JSONArray();
		jsonObj = new JSONObject();
		try {
			updateSaveStatus = saveStyleColorAttributes(request, response);
			LOGGER.info("1" + updateSaveStatus);

			if (updateSaveStatus) {
				jsonObj.put("UpdateStatus", "Success");
			} else {
				jsonObj.put("UpdateStatus", "Failed");
			}

			jsonArrayPetDtls.put(jsonObj);
			LOGGER.info("Locked Status end  " + jsonArrayPetDtls.toString());
			response.getWriter().write(jsonArrayPetDtls.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Save style color attributes.
	 * 
	 * @param request the request
	 * @param response the response */
	private boolean saveStyleColorAttributes(ResourceRequest request, ResourceResponse response) {
		// final String styleColorPetId = request.getParameter("201469930301");
		boolean updateStatus = false;
		LOGGER.info("Start  of saving saveStyleColorAttributes..............................");
		final String styleColorReq = request.getParameter("styleColorReq");
		LOGGER.info("styleColorReq.." + styleColorReq);
		final String packColorReq = request.getParameter("packColorReq");
		LOGGER.info("packColorReq.." + packColorReq);

		if (StringUtils.isNotBlank(styleColorReq) && styleColorReq.equalsIgnoreCase("StyleColor")) {
			final String styleColorPetId = request.getParameter("styleColorPetOrinNumber");
			String omniColorCode = null;
			String secondaryColorCode = null;
			String secondary2ColorCode = null;
			String secondary3ColorCode = null;
			String secondary4ColorCode = null;

			// Logic for save Style Color Attributes starts here

			List<AttributesBean> styleColorAttributeBeanList = new ArrayList<AttributesBean>();
			// form JSON request to web service
			final ItemIdBean styleColorAttributes = new ItemIdBean();
			String omniChannelColorFamily = request.getParameter("omniColor");
			String[] colorOmniArray = omniChannelColorFamily.split("-");
			if (colorOmniArray != null) {
				omniColorCode = colorOmniArray[0];

			}
			String secondaryColorStyleColor = request.getParameter("secondaryColorStyleColorAttribute");
			if (StringUtils.isNotBlank(secondaryColorStyleColor)) {
				String[] secondaryColorStyleColorArray = secondaryColorStyleColor.split("-");
				if (secondaryColorStyleColorArray != null) {
					secondaryColorCode = secondaryColorStyleColorArray[0];
				}
			}

			String secondaryColorId2StyleColor = request.getParameter("secondaryColorId2StyleColorAttribute");
			if (StringUtils.isNotBlank(secondaryColorId2StyleColor)) {
				String[] secondaryColorId2StyleArray = secondaryColorId2StyleColor.split("-");
				if (secondaryColorId2StyleArray != null) {
					secondary2ColorCode = secondaryColorId2StyleArray[0];
				}
			}

			String secondaryColorId3StyleColor = request.getParameter("secondaryColorId3StyleColorAttribute");
			if (StringUtils.isNotBlank(secondaryColorId3StyleColor)) {
				String[] secondaryColorId3StyleColorArray = secondaryColorId3StyleColor.split("-");
				if (secondaryColorId3StyleColorArray != null) {
					secondary3ColorCode = secondaryColorId3StyleColorArray[0];
				}
			}

			String secondaryColorId4StyleColor = request.getParameter("secondaryColorId4StyleColorAttribute");
			if (StringUtils.isNotBlank(secondaryColorId4StyleColor)) {
				String[] secondaryColorId4StyleColorArray = secondaryColorId4StyleColor.split("-");
				if (secondaryColorId4StyleColorArray != null) {
					secondary4ColorCode = secondaryColorId4StyleColorArray[0];
				}
			}

			String nrfColorCodeStyleColor = request.getParameter("nrfColorCodeStyleColorAttribute");
			String omniChannelColorDescriptionStyleColor = request.getParameter("omniChannelColorDescriptionStyleColorAttribute");
			String vendorColorIdStyleColor = request.getParameter("vendorColorIdStyleColorAttribute");

			String omniChannelColorFamily_Xpath = "Ecomm_StyleColor_Spec/Omnichannel_Color_Family";
			String secondary_Color_1_Xpath = "Ecomm_StyleColor_Spec/Secondary_Color_1";
			String secondary_Color_2_Xpath = "Ecomm_StyleColor_Spec/Secondary_Color_2";
			String secondary_Color_3_Xpath = "Ecomm_StyleColor_Spec/Secondary_Color_3";
			String secondary_Color_4_Xpath = "Ecomm_StyleColor_Spec/Secondary_Color_4";
			String nRF_Color_Code_Xpath = "Ecomm_StyleColor_Spec/NRF_Color_Code";
			String omni_Channel_Color_Description_Xpath = "Ecomm_StyleColor_Spec/Omni_Channel_Color_Description";

			// No XPath for vendorColorIdStyleColor?

			// styleColorAttributes.setItemId(styleColorPetId);
			if (StringUtils.isNotBlank(omniColorCode)) {
				final AttributesBean attributesBeanOmniChannelColorFamily = new AttributesBean(omniChannelColorFamily_Xpath,
						omniColorCode);
				styleColorAttributeBeanList.add(attributesBeanOmniChannelColorFamily);
			}
			if (StringUtils.isNotBlank(secondaryColorCode)) {

				final AttributesBean attributesBeanSecondaryColorStyleColor = new AttributesBean(secondary_Color_1_Xpath,
						secondaryColorCode);
				styleColorAttributeBeanList.add(attributesBeanSecondaryColorStyleColor);
			}
			if (StringUtils.isNotBlank(secondary2ColorCode)) {
				final AttributesBean attributesBeanSecondaryColorId2StyleColor = new AttributesBean(secondary_Color_2_Xpath,
						secondary2ColorCode);
				styleColorAttributeBeanList.add(attributesBeanSecondaryColorId2StyleColor);
			}
			if (StringUtils.isNotBlank(secondary3ColorCode)) {
				final AttributesBean attributesBeanSecondaryColorId3StyleColor = new AttributesBean(secondary_Color_3_Xpath,
						secondary3ColorCode);
				styleColorAttributeBeanList.add(attributesBeanSecondaryColorId3StyleColor);
			}

			if (StringUtils.isNotBlank(secondary4ColorCode)) {
				final AttributesBean attributesBeanSecondaryColorId4StyleColor = new AttributesBean(secondary_Color_4_Xpath,
						secondary4ColorCode);
				styleColorAttributeBeanList.add(attributesBeanSecondaryColorId4StyleColor);
			}

			if (StringUtils.isNotBlank(nrfColorCodeStyleColor)) {
				final AttributesBean attributesBeanNrfColorCodeStyleColor = new AttributesBean(nRF_Color_Code_Xpath,
						nrfColorCodeStyleColor);
				styleColorAttributeBeanList.add(attributesBeanNrfColorCodeStyleColor);
			}
			if (StringUtils.isNotBlank(omniChannelColorDescriptionStyleColor)) {
				final AttributesBean attributesBeannOmniChannelColorDescriptionStyleColor = new AttributesBean(
						omni_Channel_Color_Description_Xpath, omniChannelColorDescriptionStyleColor);
				styleColorAttributeBeanList.add(attributesBeannOmniChannelColorDescriptionStyleColor);
			}

			// final AttributesBean attributesBeanVendorColorIdStyleColor = new
			// AttributesBean(productNameXpath,vendorColorIdStyleColor);
			if (StringUtils.isNotBlank(styleColorPetId)) {
				styleColorAttributes.setItemId(styleColorPetId);
			}
			if ((styleColorAttributeBeanList != null) && (styleColorAttributeBeanList.size() > 0)) {
				styleColorAttributes.setList(styleColorAttributeBeanList);
			}

			if ((styleColorAttributes.getList() != null) && (styleColorAttributes.getList().size() > 0)) {

				// styleColorAttributes.setItemId(styleColorPetId);
				final Gson gson = new Gson();
				// convert from JSON to String
				final String createContentWebServiceReq = gson.toJson(styleColorAttributes);

				// request to web service
				LOGGER.info("Style Color Attributes Webservice Request Object ------------------------------------------> "
						+ createContentWebServiceReq);
				// call web service and read response
				final String webserviceResponseMessage = contentDelegate.createContentWebService(createContentWebServiceReq);
				if (webserviceResponseMessage != null && webserviceResponseMessage.trim().equalsIgnoreCase("SUCCESS")) {
					updateStatus = true;
				} else {
					updateStatus = false;
				}
			} else {
				updateStatus = true;
			}
			LOGGER.info("Style Color Attributes Webservice Response Object -------------------------------------------> "
					+ updateStatus);
			// Logic for save Style Color Attributes ends here
		}

		else if (StringUtils.isNotBlank(packColorReq) && packColorReq.equalsIgnoreCase("PackColor")) {
			LOGGER.info("packColorReq. in if loop." + packColorReq);

			final String styleColorPetId = request.getParameter("styleColorPetOrinNumber");
			String omniColorCode = null;
			String secondaryColorCode = null;
			String secondary2ColorCode = null;
			String secondary3ColorCode = null;
			String secondary4ColorCode = null;

			// Logic for save Style Color Attributes starts here

			List<AttributesBean> styleColorAttributeBeanList = new ArrayList<AttributesBean>();
			// form JSON request to web service
			final ItemIdBean styleColorAttributes = new ItemIdBean();
			String omniChannelColorFamily = request.getParameter("omniColor");
			String[] colorOmniArray = omniChannelColorFamily.split("-");
			if (colorOmniArray != null) {
				omniColorCode = colorOmniArray[0];

			}
			String secondaryColorStyleColor = request.getParameter("secondaryColorStyleColorAttribute");
			if (StringUtils.isNotBlank(secondaryColorStyleColor)) {
				String[] secondaryColorStyleColorArray = secondaryColorStyleColor.split("-");
				if (secondaryColorStyleColorArray != null) {
					secondaryColorCode = secondaryColorStyleColorArray[0];

				}
			}
			String secondaryColorId2StyleColor = request.getParameter("secondaryColorId2StyleColorAttribute");
			if (StringUtils.isNotBlank(secondaryColorId2StyleColor)) {
				String[] secondaryColorId2StyleArray = secondaryColorId2StyleColor.split("-");
				if (secondaryColorId2StyleArray != null) {
					secondary2ColorCode = secondaryColorId2StyleArray[0];
				}
			}

			String secondaryColorId3StyleColor = request.getParameter("secondaryColorId3StyleColorAttribute");
			if (StringUtils.isNotBlank(secondaryColorId3StyleColor)) {
				String[] secondaryColorId3StyleColorArray = secondaryColorId3StyleColor.split("-");
				if (secondaryColorId3StyleColorArray != null) {
					secondary3ColorCode = secondaryColorId3StyleColorArray[0];
				}
			}

			String secondaryColorId4StyleColor = request.getParameter("secondaryColorId4StyleColorAttribute");
			if (StringUtils.isNotBlank(secondaryColorId4StyleColor)) {
				String[] secondaryColorId4StyleColorArray = secondaryColorId4StyleColor.split("-");
				if (secondaryColorId4StyleColorArray != null) {
					secondary4ColorCode = secondaryColorId4StyleColorArray[0];
				}
			}

			String nrfColorCodeStyleColor = request.getParameter("nrfColorCodeStyleColorAttribute");
			String omniChannelColorDescriptionStyleColor = request.getParameter("omniChannelColorDescriptionStyleColorAttribute");
			String vendorColorIdStyleColor = request.getParameter("vendorColorIdStyleColorAttribute");

			String omniChannelColorFamily_Xpath = "Ecomm_PackColor_Spec/Omnichannel_Color_Family";
			String secondary_Color_1_Xpath = "Ecomm_PackColor_Spec/Secondary_Color_1";
			String secondary_Color_2_Xpath = "Ecomm_PackColor_Spec/Secondary_Color_2";
			String secondary_Color_3_Xpath = "Ecomm_PackColor_Spec/Secondary_Color_3";
			String secondary_Color_4_Xpath = "Ecomm_PackColor_Spec/Secondary_Color_4";
			String nRF_Color_Code_Xpath = "Ecomm_PackColor_Spec/NRF_Color_Code";
			String omni_Channel_Color_Description_Xpath = "Ecomm_PackColor_Spec/Omni_Channel_Color_Description";

			// No XPath for vendorColorIdStyleColor?

			// styleColorAttributes.setItemId(styleColorPetId);
			if (StringUtils.isNotBlank(omniColorCode)) {
				final AttributesBean attributesBeanOmniChannelColorFamily = new AttributesBean(omniChannelColorFamily_Xpath,
						omniColorCode);
				styleColorAttributeBeanList.add(attributesBeanOmniChannelColorFamily);
			}
			if (StringUtils.isNotBlank(secondaryColorCode)) {

				final AttributesBean attributesBeanSecondaryColorStyleColor = new AttributesBean(secondary_Color_1_Xpath,
						secondaryColorCode);
				styleColorAttributeBeanList.add(attributesBeanSecondaryColorStyleColor);
			}
			if (StringUtils.isNotBlank(secondary2ColorCode)) {
				final AttributesBean attributesBeanSecondaryColorId2StyleColor = new AttributesBean(secondary_Color_2_Xpath,
						secondary2ColorCode);
				styleColorAttributeBeanList.add(attributesBeanSecondaryColorId2StyleColor);
			}
			if (StringUtils.isNotBlank(secondary3ColorCode)) {
				final AttributesBean attributesBeanSecondaryColorId3StyleColor = new AttributesBean(secondary_Color_3_Xpath,
						secondary3ColorCode);
				styleColorAttributeBeanList.add(attributesBeanSecondaryColorId3StyleColor);

			}

			if (StringUtils.isNotBlank(secondary4ColorCode)) {
				final AttributesBean attributesBeanSecondaryColorId4StyleColor = new AttributesBean(secondary_Color_4_Xpath,
						secondary4ColorCode);
				styleColorAttributeBeanList.add(attributesBeanSecondaryColorId4StyleColor);
			}

			if (StringUtils.isNotBlank(nrfColorCodeStyleColor)) {
				final AttributesBean attributesBeanNrfColorCodeStyleColor = new AttributesBean(nRF_Color_Code_Xpath,
						nrfColorCodeStyleColor);
				styleColorAttributeBeanList.add(attributesBeanNrfColorCodeStyleColor);
			}
			if (StringUtils.isNotBlank(omniChannelColorDescriptionStyleColor)) {
				final AttributesBean attributesBeannOmniChannelColorDescriptionStyleColor = new AttributesBean(
						omni_Channel_Color_Description_Xpath, omniChannelColorDescriptionStyleColor);
				styleColorAttributeBeanList.add(attributesBeannOmniChannelColorDescriptionStyleColor);
			}

			// final AttributesBean attributesBeanVendorColorIdStyleColor = new
			// AttributesBean(productNameXpath,vendorColorIdStyleColor);
			if (StringUtils.isNotBlank(styleColorPetId)) {
				styleColorAttributes.setItemId(styleColorPetId);
			}
			if ((styleColorAttributeBeanList != null) && (styleColorAttributeBeanList.size() > 0)) {
				styleColorAttributes.setList(styleColorAttributeBeanList);
			}

			if ((styleColorAttributes.getList() != null) && (styleColorAttributes.getList().size() > 0)) {

				// styleColorAttributes.setItemId(styleColorPetId);
				final Gson gson = new Gson();
				// convert from JSON to String
				final String createContentWebServiceReq = gson.toJson(styleColorAttributes);

				// request to web service
				LOGGER.info("Style Pack Attributes Webservice Request Object ------------------------------------------> "
						+ createContentWebServiceReq);

				// call web service and read response
				final String webserviceResponseMessage = contentDelegate.createContentWebService(createContentWebServiceReq);
				if (StringUtils.isNotBlank(webserviceResponseMessage) && webserviceResponseMessage.trim().equalsIgnoreCase("SUCCESS")) {
					updateStatus = true;
				} else {
					updateStatus = false;
				}

				LOGGER.info("Style Pack Attributes Webservice Response Object ------------------------------------------> ");

			} else {
				updateStatus = true;
			}
		}

		LOGGER.info("End  of saving saveStyleColorAttributes.............................." + updateStatus);
		return updateStatus;
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(String arg0, Object arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/** Sets the category id from drop down.
	 * 
	 * @param categoryIdFromDropDown the categoryIdFromDropDown to set */
	public void setCategoryIdFromDropDown(String categoryIdFromDropDown) {
		this.categoryIdFromDropDown = categoryIdFromDropDown;
	}

	/** Sets the content delegate.
	 * 
	 * @param contentDelegate the new content delegate */
	public void setContentDelegate(ContentDelegate contentDelegate) {
		this.contentDelegate = contentDelegate;
	}

	/** Sets the content display form.
	 * 
	 * @param contentDisplayForm the contentDisplayForm to set */
	/* public void setContentDisplayForm(ContentForm contentDisplayForm) {
	 * this.contentDisplayForm = contentDisplayForm; } */

	/** Sets the content status code.
	 * 
	 * @param contentState the content state
	 * @return the string */
	private String setContentStatusCode(String contentState) {
		String contentStatusCode = "";

		if (contentState.equalsIgnoreCase("Ready_For_Review")) {
			contentStatusCode = "08";
		} else if (contentState.equalsIgnoreCase("Waiting_To_Be_Closed")) {
			contentStatusCode = "07";
		} else if (contentState.equalsIgnoreCase("Closed")) {
			contentStatusCode = "06";
		} else if (contentState.equalsIgnoreCase("Deactivated")) {
			contentStatusCode = "05";
		} else if (contentState.equalsIgnoreCase("Rejected")) {
			contentStatusCode = "04";
		} else if (contentState.equalsIgnoreCase("Approved")) {
			contentStatusCode = "03";
		} else if (contentState.equalsIgnoreCase("Completed")) {
			contentStatusCode = "02";
		}

		else if (contentState.equalsIgnoreCase("Initiated")) {
			contentStatusCode = "01";
		}

		return contentStatusCode;
	}

	/** Sets the disable save button flag.
	 * 
	 * @param disableSaveButtonFlag the disableSaveButtonFlag to set */
	public void setDisableSaveButtonFlag(boolean disableSaveButtonFlag) {
		this.disableSaveButtonFlag = disableSaveButtonFlag;
	}

	@Override
	public void setMaxInactiveInterval(int arg0) {

	}

	/** Sets the model and view.
	 * 
	 * @param modelAndView the modelAndView to set */
	public void setModelAndView(ModelAndView modelAndView) {
		if (modelAndView == null) {
			modelAndView = new ModelAndView(ContentScreenConstants.PAGE);
		}
		this.modelAndView = modelAndView;
	}

	/** Sets the ready for review status for style.
	 * 
	 * @param request the request
	 * @param contentDisplayForm the content display form */
	private void setReadyForReviewStatusForStyle(RenderRequest request, ContentForm contentDisplayForm) {
		final String readyForReviewStatus = request.getParameter(ContentScreenConstants.READY_FOR_REVIEW_MESSAGE_KEY);
		if (StringUtils.isNotBlank(readyForReviewStatus)) {
			contentDisplayForm.setReadyForReviewMessage(readyForReviewStatus);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}
	}

	/** Sets the ready for review status for style color.
	 * 
	 * @param request the request
	 * @param contentDisplayForm the content display form */
	private void setReadyForReviewStatusForStyleColor(RenderRequest request, ContentForm contentDisplayForm) {
		final String readyForReviewStatusForStyleColor = request
				.getParameter(ContentScreenConstants.READY_FOR_REVIEW_STYLE_COLOR_MESSAGE_KEY);
		if (StringUtils.isNotBlank(readyForReviewStatusForStyleColor)) {
			contentDisplayForm.setReadyForReviewMessageStyleColor(readyForReviewStatusForStyleColor);
			modelAndView.addObject(ContentScreenConstants.CONTENT_DISPLAY_FORM, contentDisplayForm);
		}
	}

	/** Sets the response message web service.
	 * 
	 * @param responseMessageWebService the responseMessageWebService to set */
	public void setResponseMessageWebService(String responseMessageWebService) {
		this.responseMessageWebService = responseMessageWebService;
	}

	/** Sets the style attributes.
	 * 
	 * @param styleAttributes the styleAttributes to set */
	public void setStyleAttributes(GlobalAttributesVO styleAttributes) {
		this.styleAttributes = styleAttributes;
	}

	/** Sets the style color attribute.
	 * 
	 * @param styleColorAttribute the styleColorAttribute to set */
	public void setStyleColorAttribute(ColorAttributesVO styleColorAttribute) {
		this.styleColorAttribute = styleColorAttribute;
	}

	/** Sets the style data submission flag.
	 * 
	 * @param styleDataSubmissionFlag the styleDataSubmissionFlag to set */
	public void setStyleDataSubmissionFlag(boolean styleDataSubmissionFlag) {
		this.styleDataSubmissionFlag = styleDataSubmissionFlag;
	}

	/** Split iph category by category id.
	 * 
	 * @param iphCategoryList the iph category list
	 * @return the map */
	private Map<String, String> splitIPHCategoryByCategoryId(List<ItemPrimaryHierarchyVO> iphCategoryList) {

		final Map<String, String> categoryMap = new LinkedHashMap<String, String>();
		for (final ItemPrimaryHierarchyVO iphCategory : iphCategoryList) {
			final String fullCategoryName = iphCategory.getCategoryName();

			final int index = fullCategoryName.indexOf('-');// get the first
															// occurence of -
			// LOGGER.info("index.."+index);
			final String categoryId = fullCategoryName.substring(0, index);
			// LOGGER.info("categoryId...."+categoryId);
			final String categoryName = fullCategoryName.substring(index + 1);
			// LOGGER.info("categoryName...."+categoryName);
			categoryMap.put(categoryId, categoryName);
		}

		return categoryMap;
	}

	/** Update content pet style color data status.
	 * 
	 * @param request the request
	 * @param response the response */
	@ResourceMapping("updateContentPetStyleColorDataStatus")
	private void updateContentPetStyleColorDataStatus(ResourceRequest request, ResourceResponse response) {
		LOGGER.info("start of ActionMapping....updateContentPetStyleColorDataStatus...");

		final String selectedStyleColorOrinNumber = request.getParameter("selectedStyleColorOrinNumber");
		LOGGER.info("selectedStyleColorOrinNumber..." + selectedStyleColorOrinNumber);
		final String styleColorPetContentStatus = request.getParameter("styleColorPetContentStatus");
		LOGGER.info("styleColorPetContentStatus..." + styleColorPetContentStatus);
		final String loggedInUser = request.getParameter("loggedInUser");
		LOGGER.info("loggedInUser..." + loggedInUser);

		if (StringUtils.isNotBlank(selectedStyleColorOrinNumber) && StringUtils.isNotBlank(styleColorPetContentStatus)
				&& StringUtils.isNotBlank(loggedInUser)) {
			final UpdateContentStatusDataObject dataObject = new UpdateContentStatusDataObject(selectedStyleColorOrinNumber,
					styleColorPetContentStatus, loggedInUser);
			final Gson gson = new Gson();
			// convert java object to JSON format,
			// and returned as JSON formatted string
			final String json = gson.toJson(dataObject);
			final String webserviceResponseMessage = contentDelegate.updateContentStatusWebService(json);
			LOGGER.info("webserviceResponseMessage..." + webserviceResponseMessage);
			// Create a new webserviceResponse object
			final WebserviceResponse webserviceResponse = new WebserviceResponse();
			// Set the webserviceResponseMessage in the webserviceResponse
			// object
			webserviceResponse.setMessage(webserviceResponseMessage);
			// Create a new JsonObject
			final JsonObject jsonObject = new JsonObject();
			// Convert the webserviceResponse object Json Element
			final JsonElement webserviceResponseObject = gson.toJsonTree(webserviceResponse);
			jsonObject.add("responseObject", webserviceResponseObject);
			LOGGER.info("webserviceResponseObject..... = " + webserviceResponseObject);
			if (webserviceResponseMessage.contains("successfully")) {
				disableSaveButtonFlag = true;
				try {
					response.getWriter().write(jsonObject.toString());
				} catch (final IOException e) {

					e.printStackTrace();
				}

				LOGGER.info("webserviceResponseMessag from  updateContentPetStyleColorDataStatus = " + getResponseMessageWebService());

			}
		}

	}

	@ResourceMapping("releseLockedPet")
	public void releseLockedPet(ResourceRequest request, ResourceResponse response) {
		LOGGER.info("releseLockedPet ************..");
		String loggedInUser = request.getParameter("loggedInUser");
		String lockedPet = request.getParameter("lockedPet");
		String pepFunction = request.getParameter("pepFunction");
		LOGGER.info("releseLockedPet lockedPet CONTNET REQUEST CONTROLLER ************.." + lockedPet);
		try {
			contentDelegate.releseLockedPet(lockedPet, loggedInUser, pepFunction);
		} catch (PEPPersistencyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		LOGGER.info("Exiting releseLockedPet method");
	}

	/** Update content pet style data status.
	 * 
	 * @param request the request
	 * @param response the response */
	@ResourceMapping("updateContentPetStyleDataStatus")
	private void updateContentPetStyleDataStatus(ResourceRequest request, ResourceResponse response) {
		LOGGER.info("start of ActionMapping....updateContentPetStyleDataStatus...");

		final String styleOrinNumber = request.getParameter("selectedOrinNumber");
		LOGGER.info("styleOrinNumber..." + styleOrinNumber);
		final String styleContentStatus = request.getParameter("styleContentStatus");
		LOGGER.info("styleContentStatus..." + styleContentStatus);
		final String loggedInUser = request.getParameter("loggedInUser");
		LOGGER.info("loggedInUser..." + loggedInUser);

		if (StringUtils.isNotBlank(styleOrinNumber) && StringUtils.isNotBlank(styleContentStatus)
				&& StringUtils.isNotBlank(loggedInUser)) {
			final UpdateContentStatusDataObject dataObject = new UpdateContentStatusDataObject(styleOrinNumber, styleContentStatus,
					loggedInUser);
			final Gson gson = new Gson();
			// convert java object to JSON format,
			// and returned as JSON formatted string
			final String json = gson.toJson(dataObject);
			final String webserviceResponseMessage = contentDelegate.updateContentStatusWebService(json);
			LOGGER.info("webserviceResponseMessage..." + webserviceResponseMessage);
			// Create a new webserviceResponse object
			final WebserviceResponse webserviceResponse = new WebserviceResponse();
			// Set the webserviceResponseMessage in the webserviceResponse
			// object
			webserviceResponse.setMessage(webserviceResponseMessage);
			// Create a new JsonObject
			final JsonObject jsonObject = new JsonObject();
			// Convert the webserviceResponse object Json Element
			final JsonElement webserviceResponseObject = gson.toJsonTree(webserviceResponse);
			jsonObject.add("responseObject", webserviceResponseObject);
			LOGGER.info("webserviceResponseObject..... = " + webserviceResponseObject);
			if (webserviceResponseMessage.contains("successfully")) {
				styleDataSubmissionFlag = true;
				disableSaveButtonFlag = true;
				try {
					response.getWriter().write(jsonObject.toString());
				} catch (final IOException e) {

					e.printStackTrace();
				}
			}

			LOGGER.info("end of ActionMapping....updateContentPetStyleDataStatus.");

		}
	}

	/** This method populates Grouping component structure in list
	 * 
	 * @param groupsFoundList
	 * @param contentDisplayForm
	 * @param groupId
	 * @return StyleAndItsChildDisplay
	 * @author AFUSKJ2 6/17/2016 */
	private StyleAndItsChildDisplay displayGroupsAsParentChild(List<GroupsFound> groupsFoundList, ContentForm contentDisplayForm,
			String groupId) {
		StyleAndItsChildDisplay styleAndItsChildDisplay = new StyleAndItsChildDisplay();

		String childsParentOrinNumber = null;
		String parentOrinNumber = null;
		String earliestCompletionDate = "";
		String styleColorOriNumber = "";
		String styleColorOrinNumber1 = "";
		Map<String, String> completionDateMap = new HashMap<String, String>();
		final List<SkuVO> skuList = new ArrayList<SkuVO>();
		final List<StyleColorVO> styleColorList = new ArrayList<StyleColorVO>();
		final List<StyleVO> styleList = new ArrayList<StyleVO>();
		List<String> styleColorCompletionDateList = null;
		List<GroupingVO> childGroupList = new ArrayList<GroupingVO>();
		List<GroupingVO> parentGroupList = new ArrayList<GroupingVO>();
		final List<StyleVO> styleListForContentDisplay = new ArrayList<StyleVO>();

		// Populating parent group details

		for (final GroupsFound found : groupsFoundList) {
			// Populate Groups List
			if (found.getComponentType().equalsIgnoreCase("Group")) {
				GroupingVO groupingVo = new GroupingVO();
				groupingVo.setGroupingNumber(found.getGroupId());
				groupingVo.setStyleNumber(found.getStyleId());
				groupingVo.setOrin(found.getComponentId());
				groupingVo.setCompletionDate(found.getCompletionDate());
				groupingVo.setContentStatus(found.getContentStatus());
				groupingVo.setColor(found.getColorDesc());
				groupingVo.setVendorSizeCodeDesc(found.getVendorSizeodeDesc());
				childGroupList.add(groupingVo);
			}

			// Populate Group Style List
			if (found.getEntryType().equalsIgnoreCase("Style")) {
				StyleVO styleVO = new StyleVO();
				styleVO.setGroupingNumber(found.getGroupId());
				styleVO.setStyleNumber(found.getStyleId());
				styleVO.setOrinNumber(found.getComponentId());
				styleVO.setCompletionDate(found.getCompletionDate());
				styleVO.setContentStatus(found.getContentStatus());
				styleVO.setColor(found.getColorDesc());
				styleVO.setVendorSize(found.getVendorSizeodeDesc());
				styleList.add(styleVO);
			}

			// Populate Group Style Color list
			if (found.getEntryType().equalsIgnoreCase("StyleColor")) {
				StyleColorVO styleColorVO = new StyleColorVO();
				styleColorVO.setStyleNumber(found.getStyleId());
				styleColorVO.setOrinNumber(found.getComponentId());
				styleColorVO.setCompletionDate(found.getCompletionDate());
				styleColorVO.setContentStatus(found.getContentStatus());
				styleColorVO.setColor(found.getColorDesc());
				styleColorVO.setVendorSize(found.getVendorSizeodeDesc());
				styleColorList.add(styleColorVO);
			}

			// Populate Group SKU List
			if (found.getEntryType().equalsIgnoreCase("SKU")) {
				SkuVO skuVO = new SkuVO();
				skuVO.setStyleId(found.getStyleId());
				skuVO.setOrin(found.getComponentId());
				skuVO.setCompletionDate(found.getCompletionDate());
				skuVO.setContentStatus(found.getContentStatus());
				skuVO.setColorCode(found.getColorCode());
				skuVO.setColor(found.getColorDesc());
				skuVO.setVendorSize(found.getVendorSizeodeDesc());
				skuList.add(skuVO);
			}

		}

		// Popualate Parent-Child Association

		List<StyleVO> subStyleList = new ArrayList<StyleVO>();
		List<StyleColorVO> subStyleColorList = new ArrayList<StyleColorVO>();
		List<SkuVO> childSkuList = new ArrayList<SkuVO>();

		for (StyleVO style : styleList) {

			for (StyleColorVO styleColor : styleColorList) {
				if (style.getOrinNumber().equalsIgnoreCase(styleColor.getStyleNumber())) {

					for (SkuVO sku : skuList) {

						String skuId = sku.getStyleId() + sku.getColorCode();
						if (skuId.equalsIgnoreCase(styleColor.getOrinNumber())) {
							childSkuList.add(sku);
							styleColor.setSkuList(childSkuList);

						}
					}
					subStyleColorList.add(styleColor);
					style.setStyleColorList(subStyleColorList);

				}
			}

			subStyleList.add(style);
		}

		styleAndItsChildDisplay.setStyleList(subStyleList);
		styleAndItsChildDisplay.setGroupList(childGroupList);

		return styleAndItsChildDisplay;
	}
	
	/**
	 * This method will handle the Action from the portlet.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws Exception
	 *             the exception
	 */
    @ActionMapping(params={"action=copyOrinContent"})
	public void getContentCopy(ActionRequest request,
	        ActionResponse response) throws Exception {
		LOGGER.info("ContentPortlet:handleActionRequest:Enter");

		String sessionDataKey = (String) request.getPortletSession()
				.getAttribute(ContentScreenConstants.SESSIONDATAKEY);
		String user = ContentScreenConstants.EMPTY;
		if(sessionDataKey != null 
				&& sessionDataKey.length() > 0)
		{
			if(sessionDataKey.length() >= 7)
			{
				user = sessionDataKey.substring(sessionDataKey.length() - 7, sessionDataKey.length());
			}
			else
			{
				user = sessionDataKey;
			}
		}

		String finalStatus = ContentScreenConstants.EMPTY;
		LOGGER.info("Grouping ID:------- " + request.getParameter("groupId"));
		LOGGER.info("Style ID:------- " + request.getParameter("styleId"));
		LOGGER.info("User ID:------- " + user);
		String groupID = (String) request
				.getParameter(ContentScreenConstants.GROUP_ID);
		String styleId = (String) request
				.getParameter(ContentScreenConstants.STYLE_ID);

		if (StringUtils.isNotBlank(groupID) && StringUtils.isNotBlank(styleId)) {

			// Get Group number confirmation
			String message = contentDelegate.getGroupCopyValidation(groupID,
					styleId);
			if (message.equals(ContentScreenConstants.SUCCESS)) {
				// call web service
				finalStatus = contentDelegate.callCopyContentService(groupID,
						styleId, user);
			} else {
				// send error message
				finalStatus = ContentScreenConstants.COPY_CONTENT_DB_FAILURE_MESSAGE
						+ groupID;
			}
		} else {
			finalStatus = ContentScreenConstants.COPY_CONTENT_NO_VALUE_FAILURE_MESSAGE;
		}
		request.getPortletSession()
				.setAttribute(
						ContentScreenConstants.CONTENT_COPY_STATUS_MESSAGE,
						finalStatus);

		LOGGER.info("ContentPortlet:handleActionRequest:Exit");
	}
}
