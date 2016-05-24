
package com.belk.pep.dao;

import java.util.List;

import com.belk.pep.dto.CreateGroupDTO;
import com.belk.pep.form.GroupAttributeForm;

/**
 * This class responsible for handling all the DAO call to the VP Database
 * @author AFUPYB3
 *
 */

public  interface GroupingDAO {

    public CreateGroupDTO getGroupHeaderDetails(String groupId);
    
    public List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin);
    public List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin);

}
