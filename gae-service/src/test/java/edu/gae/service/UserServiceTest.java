package edu.gae.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.gae.model.Page;
import edu.gae.model.UserDto;
import edu.gae.model.ref.SelectableDto;
import edu.gae.model.result.UserResultDto;
import edu.gae.service.impl.UserServiceImpl;
import edu.gae.service.support.AbstractJUnitTestSupport;

public class UserServiceTest extends AbstractJUnitTestSupport { 
	
	@Test
	public void shouldCreate(){
		UserDto dto = new UserDto("Teste nome é &- ç Ç", "teste@email", "pass12312", new SelectableDto("ADMIN"));
		
		dto = UserServiceImpl.getInstace().create(dto, user1());	
		
		assertEquals("Teste nome é &- ç Ç", dto.getName());
		assertEquals("teste@email",dto.getEmail());
		assertEquals("Administrador",dto.getProfile().getValue());
		assertNotNull(dto.getId());
		assertNotNull(dto.getCreateDate());
		assertTrue(dto.getActive());
		
	}
	
	@Test
	public void shouldUpdate(){
		UserDto dto = new UserDto("Default Teste", "default-email@teste.com", "pass12312", new SelectableDto("ADMIN"));	
		dto = UserServiceImpl.getInstace().create(dto, user1());
		
		Long id = dto.getId();
		
		dto.setId(null);
		dto.setCreateDate(null);
		dto.setLastAccessDate(new Date());
		dto.setProfile(new SelectableDto("CUSTOMER"));
		dto.setName("Update Name");
		dto.setEmail("update@email.com.br");
		
		UserServiceImpl.getInstace().amend(id, dto, user1());
		
		UserDto actual = UserServiceImpl.getInstace().findUnique("update@email.com.br", user1());
		
		assertEquals("Update Name", actual.getName());
		assertEquals("update@email.com.br", actual.getEmail());
		assertEquals("CUSTOMER", actual.getProfile().getKey());
		assertEquals("Customer", actual.getProfile().getValue());		
		assertEquals(id, actual.getId());
		assertNotNull(actual.getId());
	
	}
	
	@Test
	public void shouldFindByEmail(){
		UserDto dto = new UserDto("Default Teste", "default-email@teste.com", "pass12312", new SelectableDto("ADMIN"));	
		dto = UserServiceImpl.getInstace().create(dto, user1());
		
		UserDto actual = UserServiceImpl.getInstace().findUnique("default-email@teste.com", user1());
		
		assertEquals("Default Teste", actual.getName());
		assertEquals("default-email@teste.com", actual.getEmail());
		assertEquals("ADMIN", actual.getProfile().getKey());
		assertEquals("Administrador", actual.getProfile().getValue());		
		assertEquals(dto.getId(), actual.getId());
		assertNotNull(dto.getId());	
	}
	
	@Test
	public void shouldListByCleanesedName(){
		UserDto dto = new UserDto("Test name é &- ç Ç", "default-email@teste.com","pass12312", new SelectableDto("ADMIN"));	
		dto = UserServiceImpl.getInstace().create(dto, user1());
		
		Map<String, Object> searchCriteria = new HashMap<String, Object>();
		searchCriteria.put("name.cleanesed", "testnamee&cc");
		Page page = new Page(0, 0);		
		List<String> order = new ArrayList<>();
		order.add("name.cleanesed");
		
		UserResultDto actual = UserServiceImpl.getInstace().listByCriteria(user1(), searchCriteria, page, order).get(0);
		
		assertEquals("Test name é &- ç Ç", actual.getName());
		assertEquals("default-email@teste.com", actual.getEmail());
		assertEquals("ADMIN", actual.getProfile().getKey());
		assertEquals("Administrador", actual.getProfile().getValue());		
		assertEquals(dto.getId(), actual.getId());
		assertNotNull(dto.getId());
		
	}
	
	@Test
	public void shouldListByStartWithName(){
		UserDto dto = new UserDto("Teste nome é &- ç Ç", "default-email@teste.com", "pass", new SelectableDto("ADMIN"));	
		dto = UserServiceImpl.getInstace().create(dto, user1());
		
		Map<String, Object> searchCriteria = new HashMap<String, Object>();
		searchCriteria.put("name.startWith", "te");
		Page page = new Page(0, 0);		
		List<String> order = new ArrayList<>();
		order.add("name.cleanesed");
		
		UserResultDto actual = UserServiceImpl.getInstace().listByCriteria(user1(), searchCriteria, page, order).get(0);
		
		assertEquals("Teste nome é &- ç Ç", actual.getName());
		assertEquals("default-email@teste.com", actual.getEmail());
		assertEquals("ADMIN", actual.getProfile().getKey());
		assertEquals("Administrador", actual.getProfile().getValue());		
		assertEquals(dto.getId(), actual.getId());
		assertNotNull(dto.getId());
		
	}
}
