package de.herzog.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.herzog.enums.person.EventDateModifierEnum;
import de.herzog.views.EventView;

@FacesConverter(value = "event")
public class EventViewConverter implements Converter {
	
	public static final String AROUND = "um ";
	public static final String AFTER = "nach ";
	public static final String BEFORE = "vor ";	

	public static final String ERROR = "n/a";
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		
		String[] parts = string.split(" ");
		
		EventView event = new EventView();
		event.setTyp(EventDateModifierEnum.EXACT);
		
		if (parts[0].equals(BEFORE)) {
			event.setTyp(EventDateModifierEnum.BEFORE);
		} else if (parts[0].equals(AFTER)) {
			event.setTyp(EventDateModifierEnum.AFTER);
		} else if (parts[0].equals(AROUND)) {
			event.setTyp(EventDateModifierEnum.AROUND);
		}
		
		String[] date = parts[1].split("\\.");
		
		if (date.length == 3) {
			event.setTag(Integer.parseInt(date[0]));
		}
		
		if (date.length >= 2) {
			event.setMonat(Integer.parseInt(date[date.length == 2 ? 0 : 1]));
		}
		
		if (date.length >= 1) {
			event.setJahr(Integer.parseInt(date[date.length == 3 ? 2 : (date.length == 2 ? 1 : 0)]));
		} 
				
		return event;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if (object == null || !(object instanceof EventView)) {
			return ERROR;
		}
		
		EventView event = (EventView) object;
		
		StringBuilder result = new StringBuilder();
		if (event.getTyp() != null) {
			switch (event.getTyp()) {
			case AFTER:
				result.append(AFTER);
				break;
			case AROUND:
				result.append(AROUND);
				break;
			case BEFORE:
				result.append(BEFORE);
				break;
			case EXACT:
				break;
			default:
				break;
			}
		}
		
		if (event.getTag() != null && event.getTag() > 0) {
			result.append((event.getTag() < 10 ? "0" : "") + event.getTag());
		}
		
		if (event.getMonat() != null && event.getMonat() > 0) {
			result.append("." + (event.getMonat() < 10 ? "0" : "") + event.getMonat());
		}
		
		if (event.getJahr() != null && event.getJahr() > 0) {
			result.append("." + event.getJahr());
		}
		
		return result.toString();
	}

}
