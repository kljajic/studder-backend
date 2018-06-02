package com.studder.service;

import java.io.IOException;

public interface FileService {

	void saveFile(String directory, String filename, byte[] bytes) throws IOException;
	
	byte[] readFile(String directory, String filename) throws IOException;
	
	void deleteFile(String directory, String filename);
	
}
