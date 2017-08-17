package edu.domain.helper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class Convert {
	
	public static NumberFormat NUMBER_FORMAT;
	
	static {
		NUMBER_FORMAT = NumberFormat.getInstance(new Locale("pt", "BR"));
		NUMBER_FORMAT.setMaximumFractionDigits(2);
		NUMBER_FORMAT.setMinimumFractionDigits(2);
		NUMBER_FORMAT.setRoundingMode(RoundingMode.HALF_UP);		
	}
	
	public static long stringToIntDef(String valor, long def){
	     try{
	         return Long.parseLong(valor.trim());
	     }catch (Exception e){
	    	 return def;
	     }	 
	     
	} 
	
	public static double stringToDoubleDef(String valor, double def){
	     try{
	         return Double.parseDouble(valor.trim());
	     }catch (Exception e){
	    	 return def;
	     }	 
	     
	} 
	
	public static ArrayList<Long> stringToIntList(String source, char delimiter){
		ArrayList<Long> aux = new ArrayList<Long>();
		if (!source.isEmpty()){
			String [] tokens = source.split(String.valueOf(delimiter));
			for (String token : tokens){
				try{
					aux.add(Long.parseLong(token.trim()));
				}catch(NumberFormatException e){
					System.err.println("Error at "+Convert.class + ": "+e.getMessage());
				}
			}
		}
		return aux;
	}
	
	public static BigDecimal StringToBigDecimal(String value){
		if (StringUtils.isNotBlank(value)){
			try {
				return new BigDecimal(NUMBER_FORMAT.parse(value).toString()).setScale(2, RoundingMode.HALF_UP);
			} catch (ParseException e) {
				System.err.println("Error at "+Convert.class + ": "+e.getMessage());
				return null;
			}
		} else {
			return null;
		}	
	};
	
	public static String BigDecimalToString(BigDecimal value){
		return value != null ? NUMBER_FORMAT.format(value.setScale(2, RoundingMode.HALF_UP)) : null;
	}
	
	public static String ObjectToStringFields(Object object){
		if (object == null ) return "null";
		
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		
		result.append( object.getClass().getName() );
		result.append( " Object {" );
		result.append(newLine);
		
		Field[] fields = object.getClass().getDeclaredFields();

		for ( Field field : fields  ) {
			field.setAccessible(true);
			result.append("  ");
			try {
				result.append( field.getName() );
				result.append(": ");
				result.append( field.get(object) );
			} catch ( IllegalAccessException ex ) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");		
		return result.toString();
	}
	
	public static int objectToHashcodeFields(Object object){
		if (object == null ) return 0;		
		int result = 0;		
		Field[] fields = object.getClass().getDeclaredFields();

		for ( Field field : fields  ) {
			field.setAccessible(true);			
			try {				
				Object value = field.get(object);
				if (value instanceof String){
					result += StringUtils.isBlank((String) value) ? 0 : 1;
				} else {
					result += value == null ? 0 : 1;
				}
				
			} catch ( IllegalAccessException ex ) {
				System.err.println(ex);
			}			
		}		
		return result;
	}
}	     


