package com.kevinguanchedarias.kevinsuite.commons.jsf.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Properties;

import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;

import com.kevinguanchedarias.kevinsuite.commons.collection.MimeTypeCollection;
import com.kevinguanchedarias.kevinsuite.commons.exception.CommonException;
import com.kevinguanchedarias.kevinsuite.commons.exception.FileNotFoundException;
import com.kevinguanchedarias.kevinsuite.commons.exception.InternalOperationException;
import com.kevinguanchedarias.kevinsuite.commons.pojo.MimeType;

public class FileUploadController extends CommonController {
	private static final Logger LOGGER = Logger.getLogger(FileUploadController.class);
	private static final String TARGET_UPLOAD_DIR_PROP_NAME = "fileuploadcontroller.save_path";

	private InputStream propertiesFile;
	private Properties properties = new Properties();
	private MimeTypeCollection validMimes = new MimeTypeCollection();
	private Part uploadedFile;
	private String fileName;

	/**
	 * Uploads and save the file to hard disk
	 * 
	 * @return file name and extension without path information or <b>null</b>
	 *         if error
	 * @author Kevin Guanche Darias
	 */
	public String handleFileUpload() {
		String retVal = null;
		try {
			loadProperties();
			fileName = attachExtension(computeMd5FromFile(uploadedFile), findContentType(uploadedFile));
			Path fileAbsolutePath = new File(properties.getProperty(TARGET_UPLOAD_DIR_PROP_NAME), fileName).toPath();

			if (!Files.exists(fileAbsolutePath, LinkOption.values())) {
				Files.copy(uploadedFile.getInputStream(), fileAbsolutePath);
			}
			retVal = fileName;
		} catch (CommonException e) {
			addErrorMessage(INTERNAL_ERROR_TITLE, e.getMessage());
			LOGGER.error(e);
		} catch (IOException e) {
			addErrorMessage(INTERNAL_ERROR_TITLE, "No se pudo copiar el fichero");
			LOGGER.error(e);
		}
		return retVal;
	}

	public MimeTypeCollection getValidMimes() {
		return validMimes;
	}

	public void setValidMimes(MimeTypeCollection validMimes) {
		this.validMimes = validMimes;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getMd5() {
		return fileName;
	}

	public void setMd5(String md5) {
		this.fileName = md5;
	}

	public InputStream getPropertiesFile() {
		return propertiesFile;
	}

	/**
	 * InputStream representing target properties file<br />
	 * 
	 * @param propertiesFile
	 *            Example value:
	 *            getClass().getClassLoader().getResourceAsStream("some.properties")
	 * @author Kevin Guanche Darias
	 */
	public void setPropertiesFile(InputStream propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	/**
	 * Will throw exception if mime type is invalid
	 * 
	 * @param file
	 * @author Kevin Guanche Darias
	 */
	private MimeType findContentType(Part file) {
		return validMimes.findIfExists(file.getContentType());
	}

	private String computeMd5FromFile(Part file) {
		try {
			return DigestUtils.md5DigestAsHex(file.getInputStream());
		} catch (IOException e) {
			LOGGER.error(e);
			throw new InternalOperationException("Ocurrió un error al calcular la suma MD5 del fichero");
		}
	}

	private String attachExtension(String fileName, MimeType mimeType) {
		return fileName + mimeType.getDottedExtension();
	}

	private void loadProperties() {
		if (propertiesFile != null) {
			try {
				properties.load(propertiesFile);
			} catch (IOException e) {
				LOGGER.error(e);
				throw new InternalOperationException(e.getMessage());
			}
		} else {
			throw new FileNotFoundException("Properties file: " + propertiesFile + " doesn't exists");
		}
	}
}
