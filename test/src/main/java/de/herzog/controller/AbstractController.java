package de.herzog.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import de.herzog.db.repositories.BinaryRepository;
import de.herzog.db.repositories.EventRepository;
import de.herzog.db.repositories.HochzeitRepository;
import de.herzog.db.repositories.PersonRepository;
import de.herzog.util.Logger;
import de.herzog.views.ConfigurationView;

public abstract class AbstractController implements Serializable {

	private static Logger log = new Logger(AbstractController.class);
	
	private static final long serialVersionUID = 6711578651743526445L;
	private static final String PROPERTIES_FILE = "app.properties";

	protected PersonRepository personRepository;
	protected EventRepository eventRepository;
	protected HochzeitRepository hochzeitRepository;
	protected BinaryRepository binaryRepository;
	
	private static ConfigurationView configuration;
	
	static {
		Properties properties = new Properties();
		InputStream baseIn = AbstractController.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
		
		if (baseIn == null) {
			log.warn("Konnte Properties-Datei nicht öffnen: " + PROPERTIES_FILE);
		} else {
			try {
				properties.load(baseIn);
			} catch (Exception e) {
				log.warn("Konnte Properties-Datei nicht lesen: " + PROPERTIES_FILE);
			}
		}
		
		ConfigurationView configuration = new ConfigurationView();
		
		if (properties != null) {
			for (Entry<Object, Object> entry : properties.entrySet()) {
				if (entry.getKey() == null || !(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) {
					continue;
				}
				
				try {
					String key = (String) entry.getKey();
					key = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
					String value = (String) entry.getValue();
					
					configuration.getClass().getMethod(key, String.class).invoke(configuration, value);
				} catch (Exception e) {
					log.warn("error parsing configuration key: " + entry.getKey() + ", " + e.getMessage());
				}
			}
		}
		
		setConfiguration(configuration);
	}
	
	@PostConstruct
	protected void postConstruct() {		
		personRepository = new PersonRepository();
		eventRepository = new EventRepository();
		hochzeitRepository = new HochzeitRepository();
		binaryRepository = new BinaryRepository();
		
		initProperties();
		
		init();
	}
	
	private void initProperties() {
		
	}
	
	protected void init() {
		
	}
	
	public void refresh() {
		init();
	}
	
	public String getRequestParameter(String key) {
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if (map != null && map.containsKey(key)) {
			return map.get(key);
		}
		
		return null;
	}
	
	public Long getRequestParameterAsLong(String key) {
		String result = getRequestParameter(key);
		
		try {
			return Long.parseLong(result);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Boolean getRequestParameterAsBoolean(String key) {
		String result = getRequestParameter(key);
		
		try {
			return Boolean.parseBoolean(result);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static ConfigurationView getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(ConfigurationView configuration) {
		AbstractController.configuration = configuration;
	}
}
