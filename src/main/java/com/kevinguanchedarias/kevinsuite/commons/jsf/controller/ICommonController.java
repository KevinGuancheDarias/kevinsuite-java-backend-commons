package com.kevinguanchedarias.kevinsuite.commons.jsf.controller;

import java.util.Map;

import javax.faces.context.ExternalContext;

public interface ICommonController {
	
	public ExternalContext getExternalContext();
	public Map<String, Object> getSession();
}
