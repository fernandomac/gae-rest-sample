package edu.gae.web.rest.endpoint;


import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.gae.model.LoginDto;
import edu.gae.service.impl.LoginServiceImpl;
import edu.gae.web.rest.handler.ErrorHandler;

@Path("/login")
@Singleton
public class LoginResource {
   
	private static final Logger log = Logger.getLogger(LoginResource.class.getName());
	

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public LoginDto login(
			@HeaderParam("user-email") String email,
			@HeaderParam("user-pass") String pass) {		
		try {
			log.info(String.format("Login - Header: %s - %s", email, pass));
			
			LoginDto login = LoginServiceImpl.getInstace().login(email, pass);
			
			log.info(login.toString());
			
			return login;
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "login");
			return null;
		}
	}
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	public void logout(@HeaderParam("user-token") String token){
		try {
			log.info(String.format("Logout - Header: %s ", token));
			LoginServiceImpl.getInstace().logout(token);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "login");
		}
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	public void changePassword(
			@HeaderParam("user-email") String email,
			@HeaderParam("user-pass") String pass,
			@HeaderParam("user-new") String newPass) {		
		try {
			log.info(String.format("ChangePassword - Header: %s - %s - %s", email, pass, newPass));
			LoginServiceImpl.getInstace().changePassword(email, pass, newPass);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "login");			
		}
	}

	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public LoginDto getLogin(@HeaderParam("user-token") String token) {		
		try {
			log.info(String.format("Get - Header: %s ", token));
			return LoginServiceImpl.getInstace().get(token);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "login");
			return null;
		}
	}	
}
