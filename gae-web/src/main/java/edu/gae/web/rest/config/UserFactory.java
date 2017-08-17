package edu.gae.web.rest.config;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.hk2.api.Factory;

import edu.gae.domain.entity.User;

public class UserFactory implements Factory<User> {

	public static final String USER_PROPERTY = "usuario";
	
    private final ContainerRequestContext context;

    @Inject
    public UserFactory(ContainerRequestContext context) {
        this.context = context;
    }
    
	@Override
	public void dispose(User user) {}
	
	@Override
	public User provide() {		
		return (User) context.getProperty(USER_PROPERTY);
	}  
}
	

