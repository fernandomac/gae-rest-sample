package edu.gae.domain.repository;

public interface PersistableDao <T> extends ReadOnlyDao<T>{

	T addOrUpdate(T entity);
	
	Long insert(T entity);	
	
    void delete(T entity); 
   
	
}
