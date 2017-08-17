package edu.gae.domain.repository.datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import edu.gae.domain.entity.Customer;
import edu.gae.domain.repository.CustomerDao;

public class DatastoreCustomerDao extends DatastoreAbstractDAO<Customer> implements CustomerDao{

	private static DatastoreCustomerDao dao;
	
	private DatastoreCustomerDao() {
		super(Customer.class);
		
	}
	
	public static CustomerDao getInstance(){
		if (dao == null){
			dao = new DatastoreCustomerDao();
		}
		return dao;
	}

	@Override
	public Long insert(Customer entity) {
		addOrUpdate(entity);
		return entity.getId();
	}

	@Override
	public Customer find(Object ancestor, String email) {		
		return ofy().load().type(Customer.class).filter(new FilterPredicate("document", Query.FilterOperator.EQUAL, email)).first().now();		
	}
	
}
