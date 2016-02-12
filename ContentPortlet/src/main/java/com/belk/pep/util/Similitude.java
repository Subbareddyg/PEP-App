/**
 *
 */
package com.belk.pep.util;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Class RemoveDuplicates.
 *
 * @author AFUSOS3
 */
public class Similitude {

    /**
     * Removes the duplicates.
     *
     * @param list the list
     * @return the array list
     */
    static ArrayList<String> removeDuplicates(ArrayList<String> list) {

        // Store unique items in result.
        ArrayList<String> result = new ArrayList<>();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<>();

        // Loop over argument list.
        for (String item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }

    /**
     * Instantiates a new removes the duplicates.
     */
    public Similitude() {

    }



}
