package edu.gae.web.rest.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.gae.domain.constants.ReturnMessage;
import edu.gae.domain.exception.BusinessException;
import edu.gae.domain.exception.InfrastructureException;
import edu.gae.domain.exception.InvalidArgumentException;
import edu.gae.domain.exception.LoginException;
import edu.gae.domain.exception.NotFoundException;
import edu.gae.model.MessageDto;

public class ErrorHandler {
	
		
	private static ErrorHandler errorHandler;
	
	public static ErrorHandler getInstance(){
		if (errorHandler == null){
			errorHandler = new ErrorHandler();
		}
		return errorHandler;
	}
	
	private ErrorHandler(){}
	
	public void execute(Exception exception, Logger logger, String method){
		logger.log(Level.INFO, "EXCEPTION HANDLER - METHOD: " + method + " - CAUSE: {0}", exception.getMessage());
		try{
			throw exception; 
		} catch (BusinessException | InvalidArgumentException e){				
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(new MessageDto(e.getMessage())).build());
		} catch (IllegalArgumentException e){
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(new MessageDto(String.format(ReturnMessage.ILLEGAL_ARGUMENT_ERROR, e.getMessage()))).build());			
		} catch (LoginException e){
			handleLoginException(e);			
		} catch(NotFoundException e){
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(new MessageDto(e.getMessage())).build());
		} catch (InfrastructureException e){
			logger.log(Level.INFO, e.getClass().getSimpleName() + " ERROR", e);
			throw new WebApplicationException(Response.status(Status.SERVICE_UNAVAILABLE).entity(new MessageDto(e.getMessage())).build());
		} catch(Exception e){
			logger.log(Level.INFO, "SERVER ERROR", e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(new MessageDto(e.getMessage())).build());
		}	
	}
	
	private void handleLoginException(LoginException e) {
		switch (e.getError()) {
			case NEW:
				throw new WebApplicationException(Response.status(Status.FOUND).entity(new MessageDto(e.getMessage())).build());
			default:
				throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(new MessageDto(e.getMessage())).build());			
		}		
	}	
	
}
