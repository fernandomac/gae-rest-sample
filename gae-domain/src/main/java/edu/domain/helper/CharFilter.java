package edu.domain.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
  
public class CharFilter {  
  
	private static final String[] REPLACES = { "a", "e", "i", "o", "u", "c", "A", "E", "I", "O", "U", "C" };  
	  
    private static Pattern[] PATTERNS = null;
    
    private static final String MASK_CHARS = "/\\-.,:$() ";
  
    private static void compilePatterns() {  
    	 PATTERNS = new Pattern[REPLACES.length];  
         PATTERNS[0]  = Pattern.compile("[âăáŕä]");  
         PATTERNS[1]  = Pattern.compile("[éčęë]");  
         PATTERNS[2]  = Pattern.compile("[íěîď]");  
         PATTERNS[3]  = Pattern.compile("[óňôőö]");  
         PATTERNS[4]  = Pattern.compile("[úůűü]");  
         PATTERNS[5]  = Pattern.compile("[ç]");  
         PATTERNS[6]  = Pattern.compile("[ÂĂÁŔÄ]");  
         PATTERNS[7]  = Pattern.compile("[ÉČĘË]");  
         PATTERNS[8]  = Pattern.compile("[ÍĚÎĎ]");  
         PATTERNS[9]  = Pattern.compile("[ÓŇÔŐÖ]");  
         PATTERNS[10] = Pattern.compile("[ÚŮŰÜ]");  
         PATTERNS[11] = Pattern.compile("[Ç]");    
    }  
  
 
    public static String replaceSpecial(String text) {  
        if (PATTERNS == null) {  
            compilePatterns();  
        }  
  
        String result = text;  
        for (int i = 0; i < PATTERNS.length; i++) {  
            Matcher matcher = PATTERNS[i].matcher(result);  
            result = matcher.replaceAll(REPLACES[i]);  
        }  
        return result;  
    }  
    
    public static String removeChars(String word, String listCharsToRemove){
    	if (word != null && listCharsToRemove != null){
    		for (int i = 0; i < listCharsToRemove.length(); i++){
    			word = word.replace(String.valueOf(listCharsToRemove.charAt(i)), "");
    		}
    		
    	}
    	return StringUtils.isEmpty(word) ? null : word;
    } 
    
    public static String removeMaskChars(String word){
    	return removeChars(word, MASK_CHARS);
    }

    
    public static void replaceAll(StringBuilder sb, String old, String replacement) {
    	if (sb != null && old != null && replacement != null){    	
			Pattern p = Pattern.compile(old);
			Matcher m = p.matcher(sb);
			int start = 0;
			while (m.find(start)) {
				sb.replace(m.start(), m.end(), replacement);
				start = m.start() + replacement.length();
			}
		}
	}
}  