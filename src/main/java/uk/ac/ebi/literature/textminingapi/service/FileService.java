package uk.ac.ebi.literature.textminingapi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "storage.enable", havingValue = "true")
public class FileService {
	
	@Value("${storage.path}")
	private String storagePath;
	
	private static final Logger log = LoggerFactory.getLogger(FileService.class);

	/**
	 * Method to write a file in storage.path reading the content from a String
	 * @param content Content of the file to write
	 * @param fileName name of the file
	 * @throws IOException
	 */
	public String write(String content, String fileName) throws IOException {

		Path path = getPath(fileName);

		path = Files.write(path, content.getBytes(), StandardOpenOption.CREATE);

		log.info("File Written successfully to path {" + path + "}");

		return path.toString();
	}

	private Path getPath(String fileName) throws IOException {
		log.info("Writing file "+ fileName +" at path {" + storagePath + "}");

		Path path = Path.of(storagePath + File.separator + fileName);

		boolean isDeleted = Files.deleteIfExists(path);

		log.info("Old file deleted? {" + isDeleted + "}");

		File dir = new File(storagePath);

		if (!dir.exists()) {
			log.info("Creating directory {" + storagePath + "}");
			dir.mkdir();
		}
		return path;
	}

	/**
	 * Method to read all content of a file in storage.path
	 * @param fileName name of the files
	 * @return entire content of files in form of bytes array
	 * @throws IOException
	 */
	public byte[] read(String fileName) throws IOException {
		Path path = Path.of(storagePath + File.separator + fileName);
		return Files.readAllBytes(path);
	}
	
	/**
	 * Method to delete a specific file from the storage.path
	 * @param fileName name of the file to be deleted
	 * @return true if the file was thare and it was deleted successfully, otherwise false
	 */
	public boolean delete(String fileName) {
		boolean deleted = false;
		Path path = Path.of(storagePath + File.separator + fileName);
		try {
			deleted = Files.deleteIfExists(path);
		}catch(Exception e) {
			log.error("Problem while deleting file "+path.toString());
			deleted=false;
		}
		
		return deleted;
	}
	
	/**
	 * Method to write a file in storage.path reading content from an InputStream
	 * @param content inputStream containing the content
	 * @param fileName name of the file to which to write the content
	 * @throws IOException
	 */
	public void write(InputStream content, String fileName) throws IOException {
		Path path = getPath(fileName);
		log.info("File Written successfully to path {" + path + "}");
		Files.write(path, content.readAllBytes(), StandardOpenOption.CREATE);
	}
	
	/**
	 * Method to get a FileInputStream from a file contained into storage.path
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public FileInputStream getInputStream(String fileName) throws FileNotFoundException {
		return new FileInputStream(storagePath + File.separator + fileName);
	}

}
