package edu.gae.service.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.dozer.CustomConverter;

import edu.gae.domain.entity.base.Selectable;
import edu.gae.model.ref.SelectableDto;

public class DataSelectableConverter implements CustomConverter {		

	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		
		if (SelectableDto.class.isAssignableFrom(destinationClass) &&
			Selectable.class.isAssignableFrom(sourceClass)){
			
			if (sourceFieldValue != null){
				Selectable source = (Selectable) sourceFieldValue;
				return getModelInstance(sourceClass, source);			
			} else{
				return getModelInstance(sourceClass, null);	
			} 					
		}
		
		if (Selectable.class.isAssignableFrom(destinationClass) &&
				SelectableDto.class.isAssignableFrom(sourceClass) && 
				sourceFieldValue != null){
			
			SelectableDto source = (SelectableDto) sourceFieldValue;
			
			if (isValidKey(source.getKey())){
				
				if (Enum.class.isAssignableFrom(destinationClass)){				
					
					return getEnum(destinationClass, source.getKey());
					
				} else{	
							
					return source.getKey();
				}				
			}
			
		}
		return null;			
	}
		
	private boolean isValidKey(String key) {
		return StringUtils.isNotBlank(key) && !"undefined".equals(key) && !"null".equals(key);		
	}

	private SelectableDto getModelInstance(Class<?> sourceClass, Selectable source) {
		SelectableDto result = null;		
		if (result == null){
			if(source != null){
				result = new SelectableDto(source.getKey(), source.getValue());
			} else {
				result = null;
			}
		}
		return result;
	}	

	private Object getEnum(Class<?> destinationClass, String key){
	    Object enumeration = null;

	    Method [] ms = destinationClass.getMethods();
	    for(Method m : ms){
	        if(m.getName().equalsIgnoreCase("valueOf")){
	            try {
	                enumeration = m.invoke( destinationClass.getClass(), key);
	            }
	            catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            }
	            catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }
	            catch (InvocationTargetException e) {
	                e.printStackTrace();
	            }
	            return enumeration;
	        }
	    }
	    return null;
	}

}
