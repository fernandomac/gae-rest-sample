package edu.gae.domain.repository.file;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Test;

import edu.gae.domain.repository.FileDao;

public class LocalFileDaoTest {
	
	private static final String TEST_FOLDER = "src/test/output";
	
	private FileDao fileDao = LocalFileDao.getInstance(TEST_FOLDER);
	
	@Test
	public void shouldSaveLocalFile() throws IOException{
		byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/logo-fv.png"));
				
		String path = fileDao.save(bytes, "test-should-save", FileType.PNG);
		
		assertTrue(path.contains("gae-domain\\src\\test\\output\\test-should-save.png"));
	}
	
	@Test
	public void shouldDeleteLocalFile() throws IOException{
		byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/logo-fv.png"));
		
		String path = fileDao.save(bytes, "test-should-save", FileType.PNG);
		
		assertTrue(path.contains("gae-domain\\src\\test\\output\\test-should-save.png"));
		
		boolean actual = fileDao.delete(path);
		
		assertTrue(actual);
		
	}
	
	@After
	public void cleanUp(){
		File dir = new File(TEST_FOLDER);
		for (File file: dir.listFiles()) {
			 if (!file.isDirectory() && !file.getName().equals("readme.txt")) {
				 file.delete();
			 }
		 }	 
	}
	
}
