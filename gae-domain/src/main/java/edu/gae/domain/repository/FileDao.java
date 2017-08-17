package edu.gae.domain.repository;

import java.io.IOException;

import edu.gae.domain.repository.file.FileType;

public interface FileDao {
	
	String save(byte [] bytes, String fileName, FileType fileType) throws IOException;
	
	boolean delete(String fileId) throws IOException;
	
}
