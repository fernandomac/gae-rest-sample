package edu.gae.service;

import edu.gae.domain.entity.User;
import edu.gae.model.UserDto;
import edu.gae.model.result.UserResultDto;

public interface UserService extends EntityLifeCycle<UserDto>, EntitySearch<UserResultDto> {
	
	UserDto changeImage(Long id, byte[] bytes, User user);
	
	UserDto createMaster(String email, String masterPass);
	
}
