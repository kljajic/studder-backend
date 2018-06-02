package com.studder.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.studder.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Value("${file.upload-dir}")
	private String baseDirectory;
	
	@Override
	public void saveFile(String directory, String filename, byte[] bytes) throws IOException {
		Files.createDirectories(Paths.get(baseDirectory, directory));
		Files.write(Paths.get(baseDirectory, directory, filename), bytes);
	}

	@Override
	public byte[] readFile(String directory, String filename) throws IOException {
		return Files.readAllBytes(Paths.get(baseDirectory, directory, filename));
	}

	@Override
	public void deleteFile(String directory, String filename) {
		try {
			Files.delete(Paths.get(baseDirectory, directory, filename));
		}catch (Exception e) {
		}
	}

}
