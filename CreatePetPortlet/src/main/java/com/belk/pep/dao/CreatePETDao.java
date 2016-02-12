package com.belk.pep.dao;

import java.util.ArrayList;

import com.belk.pep.model.WorkFlow;

/**
 * The Interface CreatePETDao.
 * 
 * @author
 */
public interface CreatePETDao {

    /**
     * fetch PET Details.
     * 
     * @param String
     *            orinNo
     * @return WorkFlow
     * @throws Exception
     *             the exception
     */
    public ArrayList<WorkFlow> fetchPETDetails(String orinNo) throws Exception;

    public String validateOrin(String orinNo) throws Exception;

}
