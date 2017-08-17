package edu.gae.web.rest.endpoint;


import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.BooleanUtils;

import edu.gae.domain.entity.User;
import edu.gae.model.Page;
import edu.gae.model.UserDto;
import edu.gae.model.result.UserResultDto;
import edu.gae.service.impl.UserServiceImpl;
import edu.gae.web.rest.filter.annotation.Authenticated;
import edu.gae.web.rest.handler.ErrorHandler;

@Path("/user")
@Singleton
@Authenticated
public class UserResource extends AbstractResource {
   
	private static final Logger log = Logger.getLogger(UserResource.class.getName());	
	
	
	@POST
	@Produces({MediaType.APPLICATION_JSON})	
	@Consumes(MediaType.APPLICATION_JSON)	
	public UserDto create(UserDto dto, @Context User user) {
		try {
			log.info(dto.toString());
			return UserServiceImpl.getInstace().create(dto, user);		
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "create user");
			return null;
		}
	}
	
	@POST
	@Path("/master")
	@Produces({MediaType.APPLICATION_JSON})
	public UserDto createMaster(
			@HeaderParam("user-email") String email,
			@HeaderParam("user-pass") String pass) {
		try {
			return UserServiceImpl.getInstace().createMaster(email, pass);	
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "reactivate user");
			return null;
		}
	}
	
	@PUT
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})	
	@Consumes(MediaType.APPLICATION_JSON)
	public UserDto amend(@PathParam("id") Long id, UserDto dto, @Context User user) {
		try {
			return UserServiceImpl.getInstace().amend(id, dto, user);		
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "amend user");
			return null;
		}
	}
	
	@PUT
	@Path("/image/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes("*/*") 
	public UserDto changeImage(@PathParam("id") Long id, byte[] file, @Context User user) {
		try {			
			log.info("Saving Image: " + file.length);
			return UserServiceImpl.getInstace().changeImage(id, file, user);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "changeImage user");
			return null;
		}
	}
	
	@POST
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public UserDto reactivate(@PathParam("id") Long id, @Context User user) {
		try {
			return UserServiceImpl.getInstace().reactivate(id, user);	
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "reactivate user");
			return null;
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})	
	public UserDto deactivate(@PathParam("id") Long id, @Context User user) {
		try {
			return UserServiceImpl.getInstace().deactivate(id, user);	
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "delete user");
			return null;
		}
	}
	
	@GET
	@Path("/email/{email}")
	@Produces({MediaType.APPLICATION_JSON})
	public UserDto findByEmail(@PathParam("email") String email, @Context User user) {
		try {
			return UserServiceImpl.getInstace().findUnique(email, user);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "get EMAIL user");
			return null;
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public UserDto findById(@PathParam("id") Long id, @Context User user) {
		try {
			return UserServiceImpl.getInstace().findById(id, user);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "findById user");
			return null;
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<UserResultDto> list(@Context UriInfo uriInfo, @Context User user) {		
		try {
			HashMap<String, Object> searchCriteria = buildCriteria(uriInfo);			
			Page page = buildPage(uriInfo);				
			List<String> order = buildOrderCleanesedField("name", uriInfo);			
			
			log.info(String.format("Search - Criteria: %s, Page: %s, Order: %s", searchCriteria.toString(), page.toString(), order));
		
			return UserServiceImpl.getInstace().listByCriteria(user, searchCriteria, page, order);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "list user");
			return null;
		}
	}	

	private HashMap<String, Object> buildCriteria(UriInfo uriInfo) {
		HashMap<String, Object> searchCriteria = new HashMap<>();			
		searchCriteria.put("active", BooleanUtils.toBooleanObject(uriInfo.getQueryParameters().getFirst("active")));		
		addCleanesedStringFilter("name", uriInfo, searchCriteria);
		addStartWithStringFilter("name", uriInfo, searchCriteria);
		addValidStringFilter("email", uriInfo, searchCriteria);						
		return searchCriteria;
	}
	
}
