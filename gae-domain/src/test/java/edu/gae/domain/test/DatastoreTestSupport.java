package edu.gae.domain.test;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;

import edu.gae.domain.entity.User;
import edu.gae.domain.entity.base.SearcheableString;
import edu.gae.domain.entity.ref.UserProfile;
import edu.gae.domain.entity.ref.UserStatus;
import edu.gae.domain.repository.datastore.DatastoreUserDao;

public abstract class DatastoreTestSupport {
	
	private static final LocalServiceTestHelper datastoreHelper =
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());	
	
	public static void contextInitialized() {
		OfyInitializer.contextInitialized();
	    ObjectifyService.begin();	   
	}

	@Before
	public  void setUp() {
		contextInitialized();
		datastoreHelper.setUp();		
	}
	
	@After
	public  void tearDown() {
		datastoreHelper.tearDown();	
	}
	
	protected User user1(String email){
		User user = new User(new SearcheableString("Username"), email, "pass", UserProfile.ADMIN, UserStatus.ACTIVE, true);
		DatastoreUserDao.getInstance().addOrUpdate(user);
		return user;
	}
	
	protected User user1(){
		return user1("teste@email.com");
	}
}
