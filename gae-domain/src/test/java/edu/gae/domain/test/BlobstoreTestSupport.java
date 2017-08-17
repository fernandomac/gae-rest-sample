package edu.gae.domain.test;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class BlobstoreTestSupport {
	
	private static final LocalServiceTestHelper blobstoreHelper =
			new LocalServiceTestHelper(new LocalBlobstoreServiceTestConfig());
	
	@Before
	public  void setUp() {
		blobstoreHelper.setUp();
	}
	
	@After
	public  void tearDown() {	
		blobstoreHelper.tearDown();
	}	
	
}
