package com.belk.pep.util;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

import com.belk.pep.model.PetsFound;
import com.belk.pep.model.StyleColor;

//import java.util.logging.Logger;
import org.apache.log4j.Logger;


public class CompletionDateComparator implements Comparator<StyleColor> {
    
    private final static Logger LOGGER = Logger.getLogger(CompletionDateComparator.class
        .getName());

    @Override
    public int compare(StyleColor s1 , StyleColor s2){
        Date id1 = null;
        Date id2 = null;
        try {
            //id1 = DateUtil.stringToDate(s1.getCompletionDate());
           // id2 = DateUtil.stringToDate(s2.getCompletionDate());
            
            if(s1.getCompletionDate() != null){
                id1 = DateUtil.stringToDate(s1.getCompletionDate());
            }else{
               id1 = new Date(); 
            }
            
            if(s2.getCompletionDate() != null){
                id2 = DateUtil.stringToDate(s2.getCompletionDate());
            }else {
                id2 = new Date();
            }
            
            //LOGGER.info("id's......" + id1+"----"+ id2);
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      
        
        return id1.compareTo(id2);
    }

}
