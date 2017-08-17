package edu.gae.domain.repository.datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import edu.gae.domain.entity.User;
import edu.gae.domain.repository.UserDao;

public class DatastoreUserDao extends DatastoreAbstractDAO<User> implements UserDao{

	private static DatastoreUserDao dao;
	
	private DatastoreUserDao() {
		super(User.class);
		
	}
	
	public static UserDao getInstance(){
		if (dao == null){
			dao = new DatastoreUserDao();
		}
		return dao;
	}

	@Override
	public Long insert(User entity) {
		addOrUpdate(entity);
		return entity.getId();
	}

	@Override
	public User find(Object ancestor, String email) {		
		return ofy().load().type(User.class).filter(new FilterPredicate("email", Query.FilterOperator.EQUAL, email)).first().now();		
	}
	
}
