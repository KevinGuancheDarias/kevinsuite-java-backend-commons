package com.kevinguanchedarias.kevinsuite.commons.exception;

public class CommonException extends RuntimeException {
	private static final long serialVersionUID = -6919939924863035766L;

	public CommonException(String message) {
		super(message);
	}

	public CommonException(String message, Exception e) {
		super(message, e);
	}
}
