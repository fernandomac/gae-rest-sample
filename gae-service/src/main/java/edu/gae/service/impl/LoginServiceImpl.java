package edu.gae.service.impl;

import java.util.Date;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import edu.domain.helper.Password;
import edu.gae.domain.constants.ReturnMessage;
import edu.gae.domain.entity.Login;
import edu.gae.domain.entity.User;
import edu.gae.domain.entity.ref.UserStatus;
import edu.gae.domain.exception.LoginError;
import edu.gae.domain.exception.LoginException;
import edu.gae.domain.exception.NotFoundException;
import edu.gae.domain.repository.LoginDao;
import edu.gae.domain.repository.UserDao;
import edu.gae.domain.repository.datastore.DatastoreLoginDao;
import edu.gae.domain.repository.datastore.DatastoreUserDao;
import edu.gae.model.LoginDto;
import edu.gae.service.LoginService;

public class LoginServiceImpl implements LoginService {
	
	private static LoginService service;

	private LoginServiceImpl() {}
	
	public static LoginService getInstace(){
		if (service == null){
			service = new LoginServiceImpl();
		}
		return service;
	}
	
	@Override
	public LoginDto login(String email, String pass){
		User user = createLogin(email, pass);		
		String token = persistLogin(user);		
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		LoginDto result = mapper.map(user, LoginDto.class);
		result.setToken(token);
		return result;
	}
	
	@Override
	public void logout(String token) {
		LoginDao dao = DatastoreLoginDao.getInstance();
		Login login = dao.find(null, token);
		if (login != null){
			dao.delete(login);
		}		
	}
	
	@Override
	public User login(String token) {
		Login login = DatastoreLoginDao.getInstance().find(null, token);		
		return login == null ? null : login.getUser();	
	}
	
	@Override
	public void changePassword(String email, String pass, String newPass) {
		User domain = getUserFromCredentials(email, pass);
		validateLogin(pass, domain, true, newPass);		
		domain.setPassword(Password.getMD5(newPass));
		domain.setStatus(UserStatus.ACTIVE);
		DatastoreUserDao.getInstance().addOrUpdate(domain);		
	}
	
	@Override
	public LoginDto get(String token) {
		Login login = DatastoreLoginDao.getInstance().find(null, token);
		
		if (login == null){
			throw new NotFoundException(token, Login.class.getSimpleName());
		}	
			
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		LoginDto result = mapper.map(login.getUser(), LoginDto.class);
		result.setToken(token);
		return result;
	}


	private User createLogin(String email, String pass) {
		User user = getUserFromCredentials(email, pass);
		validateLogin(pass, user, false, null);
		DatastoreUserDao.getInstance().addOrUpdate(user);	
		return user;
	}

	private User getUserFromCredentials(String email, String pass) {
		UserDao dao = DatastoreUserDao.getInstance();		
		User domain = dao.find(null, email);			
		if (domain != null){
			domain.setLastAccessDate(new Date());
		}		
		return domain;
	}

	private void validateLogin(String pass, User usuario, boolean isChangePassword, String newPass) {		
		
		if (usuario == null || !Password.getMD5(pass).equals(usuario.getPassword())){
			throw new LoginException(LoginError.AUTHENTICATION, ReturnMessage.LOGIN_CREDENTIALS_ERROR);
		}
		
		if (usuario.getStatus().equals(UserStatus.BLOCKED)){
			throw new LoginException(LoginError.BLOCKED, ReturnMessage.LOGIN_BLOCK_ACCOUNT_ERROR);
		}
		
		if (!isChangePassword && usuario.getStatus().equals(UserStatus.NEW)){			
			throw new LoginException(LoginError.NEW, ReturnMessage.LOGIN_NEW_ERROR);
		}
		
		if (isChangePassword && !Password.validatePassword(newPass, Password.PASSWORD_PATTERN_MEDIUM)){
			throw new LoginException(LoginError.WEAK_PASSWORD, Password.PASSWORD_MESSAGE_MEDIUM);
		}	
		
	}	
	
	private String persistLogin(User user) {
		String token = Password.getMD5(user.getEmail() + new Date().getTime());			
		Login login = DatastoreLoginDao.getInstance().findByEmail(user.getEmail());		
		
		if (login == null){
			login = new Login(token, user);
		} else {
			login.setUser(user);
			login.setToken(token);			
			login.setLoginDate(new Date());
		}		
		
		DatastoreLoginDao.getInstance().addOrUpdate(login);
		return token;
	}
}
