package edu.gae.service;

import java.util.List;
import java.util.Map;

import edu.gae.domain.entity.User;
import edu.gae.model.Page;


public interface EntitySearch <T> {
	
	List<T> listByCriteria(User user, Map<String, Object> searchCriteria, Page page, List<String> order);
	
}
