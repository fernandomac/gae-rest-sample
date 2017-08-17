package edu.gae.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.domain.helper.Password;
import edu.gae.domain.entity.User;
import edu.gae.domain.entity.base.SearcheableString;
import edu.gae.domain.entity.ref.UserProfile;
import edu.gae.domain.entity.ref.UserStatus;
import edu.gae.domain.exception.LoginError;
import edu.gae.domain.exception.LoginException;
import edu.gae.domain.repository.datastore.DatastoreUserDao;
import edu.gae.model.LoginDto;
import edu.gae.service.impl.LoginServiceImpl;
import edu.gae.service.support.AbstractJUnitTestSupport;

public class LoginServiceTest extends AbstractJUnitTestSupport {	
	
	
	@Test
	public void shouldCreateLogin(){
		String email = "email.123@test.com";
		String pass = "45646$%$#%$";		
		createUsuario(email, pass);
		
		LoginDto login = LoginServiceImpl.getInstace().login(email, pass);
		
		assertEquals(email, login.getEmail());
		assertEquals("User Name", login.getName());		
		assertEquals("MASTER", login.getProfile());
		assertNotNull(login.getToken());
		
		User userAuth = LoginServiceImpl.getInstace().login(login.getToken());
		
		assertNotNull(userAuth);
		assertEquals("User Name", userAuth.getName().getValue());
		
	}
	
	@Test
	public void shouldNotChangePassNewWeakPassword(){		
		String email = "login-email@teste.com";
		String pass = "45646$%$#%$";		
		createUsuario(email, pass);
		
		LoginServiceImpl.getInstace().login(email, pass);
		
		try{
			LoginServiceImpl.getInstace().changePassword(email, pass, "00a23");
			fail();
		}catch (LoginException e){
			assertEquals("Password must have letters and numbers, between 6 and 20 characters.", e.getMessage());
			assertEquals(LoginError.WEAK_PASSWORD, e.getError());
		}		
	}
	
	private void createUsuario(String email, String password){
		User domain = new User(new SearcheableString("User Name"), email, password, UserProfile.MASTER, 
				UserStatus.ACTIVE, true);
		domain.setPassword(Password.getMD5(password));		
		
		DatastoreUserDao.getInstance().addOrUpdate(domain);
	}	
	
}
