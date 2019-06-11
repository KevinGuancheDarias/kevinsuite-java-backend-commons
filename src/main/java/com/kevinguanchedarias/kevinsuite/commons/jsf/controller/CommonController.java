package com.kevinguanchedarias.kevinsuite.commons.jsf.controller;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class CommonController implements ICommonController {
	public static final String INTERNAL_ERROR_TITLE = "Error interno";
	public static final String INVALID_INPUT = "Error de entrada no válida";

	/**
	 * Will add a message to Facelets
	 * 
	 * @param title
	 * @param detail
	 * @author Kevin Guanche Darias
	 */
	public void addMessage(String title, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, detail);
		getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Will add an error message to Facelets
	 * 
	 * @param title
	 * @param detail
	 * @author Kevin Guanche Darias
	 */
	public void addErrorMessage(String title, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, detail);
		getCurrentInstance().addMessage(null, message);
	}

	public FacesContext getCurrentInstance() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Useful to have access to the request object
	 * 
	 * @author Kevin Guanche Darias
	 * @return
	 */
	@Override
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Returns the JSF session
	 * 
	 * @author Kevin Guanche Darias
	 */
	@Override
	public Map<String, Object> getSession() {
		return getExternalContext().getSessionMap();
	}

	/**
	 * 
	 * @return Application url context path
	 * @author Kevin Guanche Darias
	 */
	protected String findContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}
}
