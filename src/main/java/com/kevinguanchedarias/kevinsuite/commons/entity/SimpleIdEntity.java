package com.kevinguanchedarias.kevinsuite.commons.entity;

import java.io.Serializable;

/**
 * This interface means that the enity have single id numeric field Useful for
 * those entities that need the
 * {@link com.kevinguanchedarias.kevinsuite.commons.jsf.converter.EntityConverter}
 * 
 * @author Kevin Guanche Darias
 *
 */
@FunctionalInterface
public interface SimpleIdEntity extends Serializable {
	public Number getId();
}
