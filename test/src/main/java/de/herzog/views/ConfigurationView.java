package de.herzog.views;

import de.herzog.util.Logger;

public class ConfigurationView {

	private Logger log = new Logger(ConfigurationView.class);
	
	private long primaryPersonId;

	public long getPrimaryPersonId() {
		return primaryPersonId;
	}

	public void setPrimaryPersonId(String primaryPersonId) {
		try {
			this.primaryPersonId = Long.parseLong(primaryPersonId);
		} catch (Exception e) {
			log.warn(e);
			
			this.primaryPersonId = 0l;
		}
	}

}
