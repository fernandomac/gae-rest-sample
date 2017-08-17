package edu.gae.domain.repository.file;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Test;

import edu.gae.domain.repository.FileDao;

@Ignore
public class GDriveFileDaoTest {
	
	private static final String TEST_FOLDER = "yourGoogleDriveFolderId";
	
	private FileDao fileDao = GDriveFileDao.getInstance(TEST_FOLDER);
	
	@Test
	public void shouldSaveGDriveFile() throws IOException{
		byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/logo-fv.png"));
				
		String path = fileDao.save(bytes, "test-should-save", FileType.PNG);
		
		assertNotNull(path);
	}
	
	@Test
	public void shouldDeleteGDriveFile() throws IOException{
		byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/logo-fv.png"));
		
		String path = fileDao.save(bytes, "test-should-save", FileType.PNG);
		
		assertNotNull(path);
		
		boolean actual = fileDao.delete(path);
		
		assertTrue(actual);
		
	}	
}
