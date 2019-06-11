package com.kevinguanchedarias.kevinsuite.commons.jsf.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import com.kevinguanchedarias.kevinsuite.commons.exception.InternalOperationException;

@ManagedBean
@RequestScoped
public class SpringLoginController extends CommonController implements PhaseListener {

	private static final long serialVersionUID = -8116406342493524335L;

	private static Logger logger = Logger.getLogger("CommonController");
	private static final String LOGIN_PATH = "/j_spring_security_check";
	public String doLogin(){

		try{
			RequestDispatcher dispatcher = ((ServletRequest) getExternalContext().getRequest())
					.getRequestDispatcher(LOGIN_PATH);

			dispatcher.forward((ServletRequest)getExternalContext().getRequest(), (ServletResponse)getExternalContext().getResponse());
			
			FacesContext.getCurrentInstance().responseComplete();
			
		}catch(IOException|ServletException e){
			logger.log(Level.ERROR,e);
			throw new InternalOperationException("Can't doLogin due to servlet issue");
		}
		return null;
	}

	/**
	 * This method will be run before rendering the view, so it will check if
	 * there is a login exception
	 * @author Kevin Guanche Darias
	 */
	@Override
	public void beforePhase(PhaseEvent arg0) {
		Exception e = (Exception) getSession().get(WebAttributes.AUTHENTICATION_EXCEPTION);

		// Login error
		if (e instanceof BadCredentialsException) {
			getSession().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);

			addMessage("Credenciales no válidos", "El usuario o la ontraseña no son correctos");

		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// Not used, but must implement it because it's mandatory
	}
}
