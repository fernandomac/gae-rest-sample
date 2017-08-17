package edu.gae.service.support;

import edu.gae.domain.entity.User;
import edu.gae.domain.entity.base.SearcheableString;
import edu.gae.domain.entity.ref.UserProfile;
import edu.gae.domain.entity.ref.UserStatus;
import edu.gae.domain.test.DatastoreTestSupport;

public abstract class AbstractJUnitTestSupport extends DatastoreTestSupport {
	
	private User user1;
	
	@Override
	public User user1(){
		if (user1 == null){
			user1 = new User(new SearcheableString("User 1"), "user1@email.com", "pass", UserProfile.ADMIN, UserStatus.ACTIVE, true);
			user1.setAncestor(33l);
		}
		return user1;
	}

}
