package de.herzog.enums;

public enum BinaryDataTypeEnum {
	JPG("image/jpg"), PNG("image/png"), GIF("image/gif"), PDF("application/pdf"), DOC("application/msword");

	private String mimeType;
	
	BinaryDataTypeEnum(String mimeType) {
		setMimeType(mimeType);
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
}
