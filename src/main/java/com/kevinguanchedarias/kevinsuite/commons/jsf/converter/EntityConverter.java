package com.kevinguanchedarias.kevinsuite.commons.jsf.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kevinguanchedarias.kevinsuite.commons.entity.SimpleIdEntity;

/**
 * Generic converter for all entities
 * 
 * @see http://stackoverflow.com/a/5790463
 */
@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

	private static final String KEY = "com.kevinguanchedarias.kevinsuite.commons.jsf.converter.EntityConverter";
	private static final String EMPTY = "";

	private Map<String, Object> getViewMap(FacesContext context) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> idMap = (Map) viewMap.get(KEY);
		if (idMap == null) {
			idMap = new HashMap<>();
			viewMap.put(KEY, idMap);
		}
		return idMap;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String value) {
		if (value.isEmpty()) {
			return null;
		}
		return getViewMap(context).get(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object value) {
		if (value == null || EMPTY.equals(value)) {
			return EMPTY;
		}
		String id = ((SimpleIdEntity) value).getId().toString();
		getViewMap(context).put(id, value);
		return id;
	}

}
