package com.belk.pep.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Comparator;
import java.util.regex.Pattern;

public class SortComparator implements Comparator<String>{

    @Override
    public int compare(String obj1, String obj2) {


        String o1 = obj1;
        String o2 = obj2;
        
        String firstString = removePadding(o1);
        String secondString = removePadding(o2);
        
        //Pattern p= Pattern.compile("[^a-zA-z0-9]");
        Pattern p= Pattern.compile("[|]{2}|&");
        boolean isFirstString= p.matcher(firstString).find();
        boolean isSecondString= p.matcher(secondString).find();
        
        if(isFirstString || isSecondString){
            return firstString.compareToIgnoreCase(secondString);
        }
        
        if (secondString == null || firstString == null) {
            return 0;
        }
 
        int lengthFirstStr = firstString.length();
        int lengthSecondStr = secondString.length();
 
        int index1 = 0;
        int index2 = 0; 
 
        while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
            char ch1 = firstString.charAt(index1);
            char ch2 = secondString.charAt(index2);
 
            char[] space1 = new char[lengthFirstStr];
            char[] space2 = new char[lengthSecondStr];
 
            int loc1 = 0;
            int loc2 = 0;
 
            do {
                space1[loc1++] = ch1;
                index1++;
 
                if (index1 < lengthFirstStr) {
                    ch1 = firstString.charAt(index1);
                } else {
                    break;
                }
            } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));
 
            do {
                space2[loc2++] = ch2;
                index2++;
 
                if (index2 < lengthSecondStr) {
                    ch2 = secondString.charAt(index2);
                } else {
                    break;
                }
            } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));
 
            String str1 = new String(space1);
            String str2 = new String(space2);
 
            int result;
 
            if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
                Integer firstNumberToCompare = new Integer(
                        Integer.parseInt(str1.trim()));
                Integer secondNumberToCompare = new Integer(
                        Integer.parseInt(str2.trim()));
                result = firstNumberToCompare.compareTo(secondNumberToCompare);
            } else {
                result = str1.compareTo(str2);
            }
 
            if (result != 0) {
                return result;
            }
        }
        return lengthFirstStr - lengthSecondStr;
        
    }
    public static String leftPad(String stringToPad, String padder, Integer size) {

        final StringBuilder strb = new StringBuilder(size.intValue());
        final StringCharacterIterator sci = new StringCharacterIterator(padder);

        while (strb.length() < (size.intValue() - stringToPad.length())) {
            for (char ch = sci.first(); ch != CharacterIterator.DONE; ch = sci.next()) {
                if (strb.length() < (size.intValue() - stringToPad.length())) {
                    strb.insert(strb.length(), String.valueOf(ch));
                }
            }
        }

        return strb.append(stringToPad).toString();
    }
    private String removePadding(String string) {
        String result="";
        try{
            result+= Integer.parseInt(string.trim());
        } catch (Exception e) {
            result= string;
        }
        return result;
    }

}
