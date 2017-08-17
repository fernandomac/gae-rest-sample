package edu.gae.domain.repository.file;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;

import edu.gae.domain.repository.FileDao;

public class GCloudStorageFileDao implements FileDao {

	private final String folder;
	
	private static FileDao dao;
	
	private GCloudStorageFileDao(String gdriveFolder){
		this.folder = gdriveFolder;		
	}
	
	@Override
	public String save(byte[] bytes, String fileName, FileType fileType) throws IOException {
		GcsService gcsService = GcsServiceFactory.createGcsService();
		gcsService.createOrReplace(
			    new GcsFilename(folder, fileName),
			    new GcsFileOptions.Builder().mimeType(fileType.getMimeType()).build(),
			    ByteBuffer.wrap(bytes));
		return String.format("/gs/%s/%s", folder, fileName);
	}

	@Override
	public boolean delete(String fileId) throws IOException {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static FileDao getInstance(String gdriveFolder){
		if (dao == null){
			dao = new GCloudStorageFileDao(gdriveFolder);
		}
		return dao;
	}	
	
}
