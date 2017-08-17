package edu.gae.service;

import edu.gae.domain.entity.User;

public interface EntityLifeCycle<T> {
	
	T create(T dto, User user);
	
	T amend(Long id, T dto, User user);
	
	T deactivate(Long id, User user);
	
	T reactivate(Long id, User user);
	
	T findById(Long id, User user);
	
	T findUnique(String uniqueId, User user);
}
