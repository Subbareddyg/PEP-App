/**
 * 
 */
package com.belk.pep.service;

import java.util.ArrayList;

import org.json.JSONArray;

import com.belk.pep.model.WorkFlow;

/**
 * The Interface CreatePETService.
 * 
 * @author
 */
public interface CreatePETService {

    /**
     * Create PET webservice.
     * 
     * @param StyleInfo
     * @return String
     * @throws Exception
     *             the exception
     */
    public String createPetWebService(JSONArray styleInfo) throws Exception;

    /**
     * fetch PET Details
     * 
     * @param String
     *            orinNo
     * @return UserObject
     * @throws Exception
     *             the exception
     */
    public ArrayList<WorkFlow> fetchPETDetails(String orinNo) throws Exception;

    /**
     * Validate Orin.
     * 
     * @param String
     *            orinNumber
     * @return String
     * @throws Exception
     *             the exception
     */

    public String validateOrin(String orinNumber) throws Exception;

}
