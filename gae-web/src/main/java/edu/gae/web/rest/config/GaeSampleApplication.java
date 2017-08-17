package edu.gae.web.rest.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

import edu.gae.domain.entity.User;

public class GaeSampleApplication extends ResourceConfig {
	
    public GaeSampleApplication() {

        register(new AbstractBinder(){
            @Override
            protected void configure() {
                System.out.println("-------  GAE Sample Application - ---------------");
            	
            	bindFactory(UserFactory.class)
                        .to(User.class)
                        .in(RequestScoped.class);
            } 
        });
    }
}
