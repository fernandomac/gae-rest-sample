package edu.gae.domain.repository.file;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import edu.gae.domain.repository.FileDao;
import edu.gae.domain.test.DatastoreTestSupport;

public class GCloudStorageFileDaoTest extends DatastoreTestSupport{
	
	private static final String TEST_FOLDER = "app-id.appspot.com";
	
	private FileDao fileDao = GCloudStorageFileDao.getInstance(TEST_FOLDER);
	
	@Test
	public void shouldSaveGCloudStorageFile() throws IOException{
		byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/logo-fv.png"));
				
		String path = fileDao.save(bytes, "test-should-save", FileType.PNG);
		
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		ServingUrlOptions options = ServingUrlOptions.Builder.withGoogleStorageFileName(path);			
		String servingUrl = imagesService.getServingUrl(options);
		
		assertNotNull(servingUrl);
	}
	
	@Test
	public void shouldDeleteGCloudStorageFile() throws IOException{
		byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/logo-fv.png"));
		
		String path = fileDao.save(bytes, "test-should-save", FileType.PNG);
		
		assertNotNull(path);
		
		boolean actual = fileDao.delete(path);
		
		assertTrue(actual);
		
	}	
}
