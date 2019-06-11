package com.kevinguanchedarias.kevinsuite.commons.pojo;

public final class MimeType {
	public static final MimeType MIME_IMAGE_PNG = new MimeType("image/png", "png");
	public static final MimeType MIME_IMAGE_JPG = new MimeType("image/jpeg", "jpg");
	public static final MimeType MIME_IMAGE_GIF = new MimeType("image/gif", "gif");

	private String type;
	private String extension;

	public MimeType(String type, String extension) {
		this.type = type;
		this.extension = extension;
	}

	public static MimeType[] newCollectionPngJpgGif() {
		return new MimeType[] { MIME_IMAGE_PNG, MIME_IMAGE_JPG, MIME_IMAGE_GIF };
	}

	public String getType() {
		return type;
	}

	public String getExtension() {
		return extension;
	}

	public String getDottedExtension() {
		return "." + extension;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MimeType other = (MimeType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
