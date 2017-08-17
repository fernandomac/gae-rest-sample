package edu.gae.domain.repository;

import edu.gae.domain.entity.Customer;

public interface CustomerDao extends PersistableDao<Customer>, SelectableDao<Customer> {
	
}
