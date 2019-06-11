package com.kevinguanchedarias.kevinsuite.commons.exception;

/**
 * Usually thrown when value doesn't exists in
 * 
 * @author Kevin Guanche Darias
 */
public class InvalidCollectionValueException extends CommonException {
	private static final long serialVersionUID = 2414532286926276775L;

	public InvalidCollectionValueException(String message) {
		super(message);
	}
}
