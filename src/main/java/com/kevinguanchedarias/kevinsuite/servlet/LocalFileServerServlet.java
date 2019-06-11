package com.kevinguanchedarias.kevinsuite.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kevinguanchedarias.kevinsuite.commons.exception.InternalOperationException;
import com.kevinguanchedarias.kevinsuite.commons.exception.InvalidFileException;

public class LocalFileServerServlet extends HttpServlet {
	private static final long serialVersionUID = -1312673845760875525L;

	private static final Logger LOGGER = Logger.getLogger(LocalFileServerServlet.class);
	private static final String DIRECTORY_PATH_CONTEXT_PARAM = "FileSystemDirectory";
	private static final Integer READ_BUFFER_SIZE = 8192;
	private static final Long CACHE_AGE_IN_SECONDS = 86400L;

	/**
	 * Will find the directory path where files are stored from context
	 * configuration
	 * 
	 * @throws InternalOperationException
	 *             Path not set or not found in fs
	 * @return absolute path to directory
	 * @author Kevin Guanche Darias
	 */
	public String findDirectoryPath() {
		String directory = getServletContext().getInitParameter(DIRECTORY_PATH_CONTEXT_PARAM);
		if (directory == null) {
			throw new InternalOperationException("Path value not set");
		}
		File directoryFile = new File(directory);
		if (!directoryFile.exists()) {
			throw new InternalOperationException("File " + directory + " doesn't exists");
		}
		if (!directoryFile.isDirectory()) {
			throw new InternalOperationException("File " + directory + " is not a directory");
		}
		return directory;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getPathInfo().substring(1);
		checkValidFile(fileName);
		File targetFile = new File(findDirectoryPath(), fileName);
		Path targetFilePath = targetFile.toPath();
		String mimeType = getServletContext().getMimeType(targetFilePath.toAbsolutePath().toString());
		long expires = new Date().getTime() + (CACHE_AGE_IN_SECONDS * 1000);

		response.setHeader("Content-Type", mimeType);
		response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
		response.setDateHeader("Expires", expires);
		response.setHeader("Cache-Control", "max-age=" + CACHE_AGE_IN_SECONDS);

		try (InputStream input = new FileInputStream(targetFilePath.toFile());
				OutputStream output = response.getOutputStream()) {
			response.setContentLength((int) targetFile.length());
			writeBinaryToOutput(input, output);
		} catch (FileNotFoundException e) {
			response.setStatus(404);
			LOGGER.debug(e);
		} catch (IOException e) {
			response.setStatus(500);
			LOGGER.error(e);
		}
	}

	/**
	 * Will check that filename is valid
	 * 
	 * @param fileName
	 * @throws InvalidFileException
	 *             File is not valid
	 * @author Kevin Guanche Darias
	 */
	private void checkValidFile(String fileName) {
		if (fileName.indexOf("..") != -1) {
			throw new InvalidFileException("File can't have ..");
		}
	}

	private void writeBinaryToOutput(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[READ_BUFFER_SIZE];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}
	}
}
