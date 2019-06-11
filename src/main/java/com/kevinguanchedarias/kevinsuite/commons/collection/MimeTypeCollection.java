package com.kevinguanchedarias.kevinsuite.commons.collection;

import java.util.HashMap;

import com.kevinguanchedarias.kevinsuite.commons.exception.InvalidCollectionValueException;
import com.kevinguanchedarias.kevinsuite.commons.pojo.MimeType;

public class MimeTypeCollection extends HashMap<String, MimeType> {
	private static final long serialVersionUID = -3367517154645798178L;

	public MimeTypeCollection() {
		super();
	}

	public MimeTypeCollection(MimeType[] mimeTypes) {
		addAll(mimeTypes);
	}

	public MimeType add(MimeType mimeType) {
		return super.put(mimeType.getType(), mimeType);
	}

	public void addAll(MimeType[] mimeTypes) {
		for (MimeType currentType : mimeTypes) {
			add(currentType);
		}
	}

	public MimeType get(MimeType mimeType) {
		return super.get(mimeType.getType());
	}

	/**
	 * 
	 * @param type
	 * @return the MimeType object representing the specified type
	 * @throws InvalidCollectionValueException
	 *             When there is not such MimeType
	 * @author Kevin Guanche Darias
	 */
	public MimeType findIfExists(String type) {
		MimeType retVal = get(type);
		if (retVal == null) {
			throw new InvalidCollectionValueException("Invalid file type: " + type);
		}

		return retVal;
	}

	/**
	 * Notice: This collection doesn't care about the key param, will use
	 * MimeType.type as key
	 * 
	 * @param key
	 *            Ignored field!
	 * @param value
	 * @author Kevin Guanche Darias
	 */
	@Override
	public MimeType put(String key, MimeType value) {
		return add(value);
	}
}
