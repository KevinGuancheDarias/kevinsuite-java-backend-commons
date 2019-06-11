package com.kevinguanchedarias.kevinsuite.commons.convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

/**
 * Used to convert from entity to dto and viceversa
 * 
 * @author Kevin Guanche Darias
 *
 */
public final class EntityPojoConverterUtil {

	private static final Logger LOGGER = Logger.getLogger(EntityPojoConverterUtil.class);

	private EntityPojoConverterUtil() {
		throw new AssertionError("Can't init this class");
	}

	/**
	 * Copies object from one class to other.<br />
	 * <b>NOTICE:</b> Only name marching properties are copied
	 * 
	 * @param targetClass
	 *            - Class of the result conversion
	 * @param source
	 * @return Result object of the copy with the specified class
	 * @author Kevin Guanche Darias
	 */
	public static <E> E convertFromTo(Class<E> targetClass, Object source) {
		E targetEntity = null;
		try {
			targetEntity = targetClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error(e);
		}
		BeanUtils.copyProperties(source, targetEntity);
		return targetEntity;
	}

	/**
	 * Copies object from one class to one existing instance
	 * 
	 * @param targetEntity
	 *            - Instance of target entity
	 * @param source
	 * @return
	 * @author Kevin Guanche Darias
	 */
	public static <E> E convertFromTo(E targetEntity, Object source) {
		BeanUtils.copyProperties(source, targetEntity);
		return targetEntity;
	}

	/**
	 * Copies object from one class to other.<br />
	 * <b>NOTICE:</b> Only name marching properties are copied
	 * 
	 * @param targetClass
	 *            - Class of the result conversion
	 * @param sourceList
	 * @return Result list of the copy with the specified class
	 * @author Kevin Guanche Darias
	 */
	public static <E> List<E> convertFromTo(Class<E> targetClass, List<?> sourceList) {
		List<E> retVal = null;
		try {
			retVal = new ArrayList<>();
			for (Object current : sourceList) {
				E targetObject = targetClass.newInstance();
				BeanUtils.copyProperties(current, retVal);
				retVal.add(targetObject);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error(e);
		}
		return retVal;
	}
}
