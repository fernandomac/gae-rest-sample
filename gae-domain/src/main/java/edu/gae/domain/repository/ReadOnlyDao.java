package edu.gae.domain.repository;

import java.util.List;
import java.util.Map;

public interface ReadOnlyDao<T> {
	
	T get(Long id);
	
	T find(Object ancestor, String key);	
	
	List<T> list(Object ancestor, Map<String, Object> filters);
	
	List<T> list(Object ancestor, Map<String, Object> filters, Integer limit, Integer offset, Object order);
	
	Integer count(Object ancestor, Map<String, Object> filters);
	
}
