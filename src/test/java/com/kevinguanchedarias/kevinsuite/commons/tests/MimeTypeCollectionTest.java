package com.kevinguanchedarias.kevinsuite.commons.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.kevinguanchedarias.kevinsuite.commons.collection.MimeTypeCollection;
import com.kevinguanchedarias.kevinsuite.commons.exception.InvalidCollectionValueException;
import com.kevinguanchedarias.kevinsuite.commons.pojo.MimeType;

public class MimeTypeCollectionTest {

	MimeTypeCollection collection;

	@Before
	public void init() {
		collection = new MimeTypeCollection();
	}

	@Test
	public void shouldIgnoreKeyWhenPut() {
		String falseKey = "proGuy";
		collection.put(falseKey, MimeType.MIME_IMAGE_PNG);
		assertNull(collection.get(falseKey));
		assertNotNull(collection.get(MimeType.MIME_IMAGE_PNG));
	}

	@Test
	public void shouldAddOne() {
		collection.add(MimeType.MIME_IMAGE_PNG);
		assertEquals(1, collection.size());
		assertNotNull(collection.get(MimeType.MIME_IMAGE_PNG));
	}

	@Test
	public void shouldAddAllArray() {
		collection.addAll(MimeType.newCollectionPngJpgGif());
		assertEquals(MimeType.newCollectionPngJpgGif().length, collection.size());
	}

	@Test
	public void shouldReturnValidWhenExists() {
		collection.add(MimeType.MIME_IMAGE_JPG);
		MimeType mimeType = collection.findIfExists(MimeType.MIME_IMAGE_JPG.getType());
		assertNotNull(mimeType);
		assertEquals(MimeType.MIME_IMAGE_JPG, mimeType);
	}

	@Test(expected = InvalidCollectionValueException.class)
	public void shouldThrowWhenNotExists() {
		collection.addAll(MimeType.newCollectionPngJpgGif());
		collection.findIfExists("lie/text");
	}
}
