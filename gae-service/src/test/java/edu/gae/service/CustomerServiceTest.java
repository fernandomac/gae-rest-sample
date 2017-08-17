package edu.gae.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.gae.domain.exception.BusinessException;
import edu.gae.model.AddressDto;
import edu.gae.model.ContactDto;
import edu.gae.model.CustomerDto;
import edu.gae.model.Page;
import edu.gae.model.result.CustomerResultDto;
import edu.gae.service.impl.CustomerServiceImpl;
import edu.gae.service.support.AbstractJUnitTestSupport;

public class CustomerServiceTest extends AbstractJUnitTestSupport { 
	
	@Test
	public void shouldCreate(){
		CustomerDto dto = new CustomerDto("Machado é@#@", "938.374.862-14", 
				new ContactDto("email@cliente.com", "9999-8888"), 
				new AddressDto("line1", "line2", "City", "State", "zipcode"));				
	
		
		dto = CustomerServiceImpl.getInstace().create(dto, user1());
		
		System.out.println(dto);		
	}	
	
	@Test(expected=BusinessException.class)
	public void shouldNotCreateNullMandatoryField(){
		CustomerDto dto = new CustomerDto(null, "48.371.151/0001-82", 
				new ContactDto("email@cliente.com", "9999-8888"), 
				new AddressDto("Rua", "Numero", "Comp", "Bairro", "23456789")); 
				
		dto = CustomerServiceImpl.getInstace().create(dto, user1());	
	}
	
	@Test
	public void shouldUpdate(){
		CustomerDto dto = new CustomerDto("Machado é@#@", "938.374.862-14", 
				new ContactDto("email@cliente.com", "9999-8888"), 
				new AddressDto("line1", "line2", "City", "State", "zipcode"));		
		dto = CustomerServiceImpl.getInstace().create(dto, user1());
		
		Long id = dto.getId();
		
		dto.setId(333l);
		dto.setActive(Boolean.FALSE);
		dto.setCreateDate(null);
		
		dto.setName("New updated name");
		dto.getContact().setEmail("new@email.com");
		dto.setDocument("44444-4444");
		
		dto = CustomerServiceImpl.getInstace().amend(id, dto, user1());
		
		assertEquals("New updated name", dto.getName());
		assertEquals("new@email.com", dto.getContact().getEmail());
		assertEquals("44444-4444", dto.getDocument());
		assertEquals(id, dto.getId());
		assertTrue(dto.getActive());
		assertNotNull(dto.getCreateDate());
		
	}
	
	@Test
	public void shouldList(){
		CustomerDto dto = new CustomerDto("Machado","938.374.862-14", new ContactDto("", "9999-8888"), null);	
		
		dto = CustomerServiceImpl.getInstace().create(dto, user1());		
		
		Map<String, Object> searchCriteria = new HashMap<String, Object>();
		Page page = new Page(0, 0);
		
		List<String> order = new ArrayList<>();
		order.add("name.cleanesed");
		
		List<CustomerResultDto> result = CustomerServiceImpl.getInstace().listByCriteria(user1(), searchCriteria, page, order);
		
		assertEquals(1, result.size());
		
	}	
}
