package edu.gae.web.rest.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import edu.gae.domain.constants.ReturnMessage;
import edu.gae.domain.entity.User;
import edu.gae.domain.entity.ref.UserProfile;
import edu.gae.model.MessageDto;
import edu.gae.web.rest.config.UserFactory;
import edu.gae.web.rest.filter.annotation.Admin;

@Provider
@Priority(value = 1)
@Admin
public class AdminFilter implements ContainerRequestFilter {
	
	private static final Logger log = Logger.getLogger(AdminFilter.class.getName());
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.log(Level.INFO, "ADMIN FILTER");		
		User user = (User) requestContext.getProperty(UserFactory.USER_PROPERTY);
		
		if (user == null || !UserProfile.ADMIN.equals(user.getProfile())){
			log.info("User is not Admin: " + user);
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(new MessageDto(ReturnMessage.LOGIN_UNAUTHORIZED_ERROR)).build());
		} 
		log.log(Level.INFO, user.toString());				
	}
}
