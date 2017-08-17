package edu.gae.web.rest.config;

import java.util.Set;

import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.reflections.Reflections;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.impl.translate.opt.BigDecimalLongTranslatorFactory;

@Singleton
public class OfyContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
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
