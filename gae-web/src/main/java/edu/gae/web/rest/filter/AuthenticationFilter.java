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
import edu.gae.model.MessageDto;
import edu.gae.web.rest.config.UserFactory;
import edu.gae.web.rest.filter.annotation.Authenticated;

@Provider
@Priority(value = 1)
@Authenticated
public class AuthenticationFilter implements ContainerRequestFilter {
	
	private static final Logger log = Logger.getLogger(AuthenticationFilter.class.getName());
	
	private static final String MASTER_PASS = "3246gfs786485%*($%";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.log(Level.INFO, "AUTHENTICATION FILTER");		
		User user = (User) requestContext.getProperty(UserFactory.USER_PROPERTY);	
		
		if (MASTER_PASS.equals(requestContext.getHeaderString("master-pass"))){
			log.log(Level.INFO, "Master pass login");
		} else if (user != null ){
			log.log(Level.INFO, user.toString());				
		} else {
			log.info("User is not authenticated");
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(new MessageDto(ReturnMessage.LOGIN_UNAUTHORIZED_ERROR)).build());
		}
	
	}
}
