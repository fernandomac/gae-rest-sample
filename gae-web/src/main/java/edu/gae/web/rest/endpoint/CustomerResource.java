package edu.gae.web.rest.endpoint;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import edu.gae.model.CustomerDto;
import edu.gae.model.Page;
import edu.gae.model.result.CustomerResultDto;
import edu.gae.service.impl.CustomerServiceImpl;
import edu.gae.web.rest.filter.annotation.Authenticated;
import edu.gae.web.rest.handler.ErrorHandler;

@Path("/customer")
@Singleton
@Authenticated
public class CustomerResource extends AbstractResource {
   
	private static final Logger log = Logger.getLogger(CustomerResource.class.getName());
	
	public CustomerResource(){}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON})	
	@Consumes(MediaType.APPLICATION_JSON)	
	public CustomerDto create(CustomerDto dto, @Context User user) {
		try {
			return CustomerServiceImpl.getInstace().create(dto, user);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "create cliente");
			return null;
		}
	}
	
	@PUT
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})	
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerDto amend(@PathParam("id") Long id, CustomerDto dto, @Context User user) {
		try {
			return CustomerServiceImpl.getInstace().amend(id, dto, user);		
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "amend cliente");
			return null;
		}
	}
	
	@POST
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public CustomerDto reactivate(@PathParam("id") Long id, @Context User user) {
		try {
			return CustomerServiceImpl.getInstace().reactivate(id, user);	
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "reactivate cliente");
			return null;
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})	
	public CustomerDto deactivate(@PathParam("id") Long id, @Context User user) {
		try {
			return CustomerServiceImpl.getInstace().deactivate(id, user);	
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "delete cliente");
			return null;
		}
	}	
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public CustomerDto findById(@PathParam("id") Long id, @Context User user) {
		try {
			return CustomerServiceImpl.getInstace().findById(id, user);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "findById cliente");
			return null;
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<CustomerResultDto> list(@Context UriInfo uriInfo, @Context User user) {		
		try {			
			HashMap<String, Object> searchCriteria = buildCriteria(uriInfo);			
			Page page = buildPage(uriInfo);				
			List<String> order = buildOrder(uriInfo);
			
			log.info(String.format("Search - Criteria: %s, Page: %s, Order: %s", searchCriteria.toString(), page.toString(), order));
		
			return CustomerServiceImpl.getInstace().listByCriteria(user, searchCriteria, page, order);
		} catch (Exception e) {
			ErrorHandler.getInstance().execute(e, log, "list cliente");
			return null;
		}
	}	

	private HashMap<String, Object> buildCriteria(UriInfo uriInfo) {
		String name = uriInfo.getQueryParameters().getFirst("name");
		
		HashMap<String, Object> searchCriteria = new HashMap<>();			
		searchCriteria.put("active", BooleanUtils.toBooleanObject(uriInfo.getQueryParameters().getFirst("active")));
		searchCriteria.put("name", name);
				
		return searchCriteria;
	}
	
}
