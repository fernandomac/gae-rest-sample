package edu.gae.domain.repository;

import edu.gae.domain.entity.Login;

public interface LoginDao extends PersistableDao<Login> {
	
	Login findByEmail(String email);
	
}
