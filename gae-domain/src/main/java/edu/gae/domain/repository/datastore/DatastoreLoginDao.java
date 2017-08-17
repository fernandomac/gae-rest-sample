package edu.gae.domain.repository.datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import edu.gae.domain.entity.Login;
import edu.gae.domain.repository.LoginDao;

public class DatastoreLoginDao extends DatastoreAbstractDAO<Login> implements LoginDao{

	private static DatastoreLoginDao dao;
	
	private DatastoreLoginDao() {
		super(Login.class);
		
	}
	
	public static LoginDao getInstance(){
		if (dao == null){
			dao = new DatastoreLoginDao();
		}
		return dao;
	}

	@Override
	public Long insert(Login entity) {
		addOrUpdate(entity);
		return entity.getId();
	}

	@Override
	public Login find(Object ancestor, String token) {		
		return ofy().load().type(Login.class).filter(new FilterPredicate("token", Query.FilterOperator.EQUAL, token)).first().now();		
	}
	
	@Override
	public Login findByEmail(String email) {
		return ofy().load().type(Login.class).filter(new FilterPredicate("user.email", Query.FilterOperator.EQUAL, email)).first().now();
	}
	
}
