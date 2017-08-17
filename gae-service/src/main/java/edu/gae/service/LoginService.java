package edu.gae.service;



import edu.gae.domain.entity.User;
import edu.gae.model.LoginDto;

public interface LoginService {
	
	LoginDto login(String email, String pass);
	
	User login(String token);
	
	void logout(String token);
	
	LoginDto get(String token);
	
	void changePassword(String email, String pass, String newPass);
	
}
