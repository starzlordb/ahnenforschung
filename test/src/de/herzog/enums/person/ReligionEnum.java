package de.herzog.enums.person;

public enum ReligionEnum {

	EV("evangelisch"), 
	KATH("katholisch"), 
	RK("römisch-katholisch"), 
	REF("reformiert"),
	LUTH("lutherisch");
	
	private String label;
	
	ReligionEnum(String label) {
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public static ReligionEnum fromLabel(String label) {
		if (label == null) {
			return null;
		}
		
		for (ReligionEnum religion : ReligionEnum.values()) {
			if (religion.getLabel().equals(label)) {
				return religion;
			}
		}
		
		return null;
	}
}

	