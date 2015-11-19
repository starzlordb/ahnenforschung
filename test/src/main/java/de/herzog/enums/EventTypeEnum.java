package de.herzog.enums;

public enum EventTypeEnum {
	BIRTH("Geburt"), BAPTISM("Taufe"), DEATH("Tod"), BURIAL("Beerdigung"), MARRIAGE("Hochzeit"), DIVORCE("Scheidung"), PERSON("Allgemein");
	
	private String label;
	
	EventTypeEnum(String label) {
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
