package edu.gae.web.rest.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import edu.gae.domain.entity.User;
import edu.gae.service.impl.LoginServiceImpl;
import edu.gae.web.rest.config.UserFactory;

@Provider
@PreMatching
public class PreAuthenticatedFilter implements ContainerRequestFilter{
	
	private static final Logger log = Logger.getLogger(PreAuthenticatedFilter.class.getName());
	public static final String AUTHENTICATION_HEADER = "user-token";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.log(Level.INFO, "PRE AUTHENTICATION FILTER");
		
		String token = requestContext.getHeaderString(AUTHENTICATION_HEADER);
		
		if (StringUtils.isNotBlank(token)){
			processAuthentication(requestContext, token);
		} else {
			log.log(Level.INFO, "Token not provided");
		}
	}

	private void processAuthentication(ContainerRequestContext requestContext, String token) {
		User user = LoginServiceImpl.getInstace().login(token);
		if (user != null){
			requestContext.setProperty(UserFactory.USER_PROPERTY, user);	
			log.log(Level.INFO, "Is Authenticated");
		} else {
			log.log(Level.INFO, "Is NOT Authenticated " + token);
		}
	}

}
