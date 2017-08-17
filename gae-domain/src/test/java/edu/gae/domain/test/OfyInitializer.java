package edu.gae.domain.test;

import java.util.Set;

import org.reflections.Reflections;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.impl.translate.opt.BigDecimalLongTranslatorFactory;


public class OfyInitializer {
	
	public static void contextInitialized() {
		ObjectifyService.factory().getTranslators().add(new BigDecimalLongTranslatorFactory());	

		Reflections reflections = new Reflections("edu.gae.domain.entity");
    
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);
	    	
	    for (Class<?> class1 : annotated) {
	    	@SuppressWarnings("rawtypes")
	    	final Class clz = class1;
	     	ObjectifyService.register(clz);
	    }	    
	    
	}
}