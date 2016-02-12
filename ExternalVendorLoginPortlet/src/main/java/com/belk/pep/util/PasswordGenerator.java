package com.belk.pep.util;

import java.util.Random;
/**
 * This class will generate random password.
 * @author AFUBXJ1
 *
 */
public class PasswordGenerator {

    /** The Constant symbols. */
    private static final char[] symbols;

    static {
      StringBuilder tmp = new StringBuilder();
      for (char ch = '0'; ch <= '9'; ++ch)
        tmp.append(ch);
      for (char ch = 'a'; ch <= 'z'; ++ch)
        tmp.append(ch);
      for (char ch = 'A'; ch <= 'Z'; ++ch)
          tmp.append(ch);
      symbols = tmp.toString().toCharArray();
    }   

    /** The random. */
    private static  Random random = new Random();

    /** The buf. */
    public  static char[] buf;

    /**
     * Instantiates a new password generator.
     *
     * @param length the length
     */
    public PasswordGenerator(int length) {
      if (length < 1)
        throw new IllegalArgumentException("length < 1: " + length);
      buf = new char[length];
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String nextString() {
      for (int idx = 0; idx < buf.length; ++idx) 
        buf[idx] = symbols[random.nextInt(symbols.length)];
      return new String(buf);
    }
    
 /*   public static void main(String args[]){
        System.out.println("This is from main");
        PasswordGenerator pasg1= new PasswordGenerator(8);
        System.out.println("Next String"+pasg1.nextString());
    }*/
  }

