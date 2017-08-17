package edu.gae.domain.repository.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.gae.domain.repository.FileDao;

public class LocalFileDao implements FileDao{
	
	private final String folder;

	private static FileDao dao;
	
	private LocalFileDao(String localFolder){
		folder = localFolder;
	}
	
	@Override
	public String save(byte[] bytes, String fileName, FileType fileType) throws IOException {
		File file = getCanonicalFilePath(fileName, fileType.getExtension());	
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bytes);
		fos.close();
		return file.getAbsolutePath();
	}

	@Override
	public boolean delete(String fileId) throws IOException {
		File file = new File(fileId);
		return file.delete();
	}
	
	private File getCanonicalFilePath(String fileName, String mimeType){
		String pathToSave = String.format("%s/%s.%s", folder, fileName, mimeType);	
		return new File(pathToSave);
	}
	
	public static FileDao getInstance(String localFolder){
		if (dao == null){
			dao = new LocalFileDao(localFolder);
		}
		return dao;
	}	
	
}
