package com.belk.pep.util;

import java.util.Comparator;
import com.belk.pep.model.PetsFound;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;


public class ComplexPackComparator implements Comparator<PetsFound> {
    
    private final static Logger LOGGER = Logger.getLogger(ComplexPackComparator.class
        .getName());

    @Override
    public int compare(PetsFound p1 , PetsFound p2){
        String id1 = p1.getOrinNumber();
        String id2 = p2.getOrinNumber();
        //LOGGER.info("id's......" + id1+"----"+ id2);
        
        return id1.compareTo(id2);
    }

}
