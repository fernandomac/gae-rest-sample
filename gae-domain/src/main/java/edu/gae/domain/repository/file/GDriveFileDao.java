package edu.gae.domain.repository.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import edu.gae.domain.repository.FileDao;

public class GDriveFileDao implements FileDao {
	
	private final String folder;
	
	private static FileDao dao;
	
	private GDriveFileDao(String gdriveFolder){
		this.folder = gdriveFolder;		
	}

	@Override
	public String save(byte[] bytes, String fileName, FileType fileType) throws IOException {
		
		InputStream in = new ByteArrayInputStream(bytes);
		
		Drive driveService = GDriveFactory.getDriveService();
		
		File fileMetadata = new File();
		fileMetadata.setName(fileName);
		fileMetadata.setParents(Collections.singletonList(folder));
		
		File file = driveService.files().create(fileMetadata, new InputStreamContent(fileType.getMimeType(), in)).setFields("id, parents").execute();
		return file.getId();
	}

	@Override
	public boolean delete(String fileId) throws IOException {
		Drive driveService = GDriveFactory.getDriveService();
		driveService.files().delete(fileId).execute();
		return true;
	}
	
	public static FileDao getInstance(String gdriveFolder){
		if (dao == null){
			dao = new GDriveFileDao(gdriveFolder);
		}
		return dao;
	}	
}
