package de.herzog.enums.person;

public enum EventDateModifierEnum {
	NONE("n/a"), EXACT("exakt"), AROUND("um"), BEFORE("vor"), AFTER("nach");
	
	EventDateModifierEnum(String label) {
		setLabel(label);
	}
	
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
