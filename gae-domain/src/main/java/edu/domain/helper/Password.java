package edu.domain.helper;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
 
public class Password {
    
     private static final short MIN_LENGTH = 6;
     private static final short MAX_LENGTH = 20;
     public static final String PASSWORD_PATTERN_MEDIUM = 
    		 String.format("(^(?=.*[a-zA-Z])(?=.*\\d)[\\S]{%d,%d}$)", MIN_LENGTH, MAX_LENGTH);   
     public static final String PASSWORD_MESSAGE_MEDIUM = 
    		 String.format("Password must have letters and numbers, between %d and %d characters.", MIN_LENGTH, MAX_LENGTH);
                   
      public static String getMD5(String s){
    	   if (s != null){
	    	   String sen = ""; 
	    	   MessageDigest md = null; 
	    	   try { 
	    		   md = MessageDigest.getInstance("MD5"); 
	    	   } catch (NoSuchAlgorithmException e) { 
	    		   e.printStackTrace(); 
	    	   } 
	    	   BigInteger hash = new BigInteger(1, md.digest(s.getBytes())); 
	    	   sen = hash.toString(16);          
	    	   return sen;
    	   }else {
    		   return null;
    	   }
       } 
      
      public static char[] generateRandom(int lentgh){
    	  SecureRandom random = new SecureRandom();
    	  String pass = new BigInteger(130, random).toString(32);
    	  if (lentgh > pass.length()){ 
    		  lentgh = pass.length();
    	  }
      	  return pass.substring(0, lentgh).toCharArray();
      }
      
      public static boolean validatePassword(String password, String regex){
    	  if(StringUtils.isEmpty(password)){
    		  return false;
    	  }
    	  Pattern pattern = Pattern.compile(regex);
    	  Matcher matcher = pattern.matcher(password);
  		  return matcher.matches();
      }	  
}