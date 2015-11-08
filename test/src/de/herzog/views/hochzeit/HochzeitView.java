package de.herzog.views.hochzeit;

import java.util.List;

import javax.faces.event.ActionEvent;

import de.herzog.controller.person.PersonController;
import de.herzog.db.beans.HochzeitBean;
import de.herzog.util.Logger;
import de.herzog.views.AbstractView;
import de.herzog.views.EventView;
import de.herzog.views.person.PersonView;

public class HochzeitView extends HochzeitBean implements AbstractView {
	private static final long serialVersionUID = 2651620415249909823L;
	
	@SuppressWarnings("unused")
	private Logger log = new Logger(HochzeitView.class);

	private EventView hochzeit;
	
	private PersonView mann;
	private PersonView frau;
	
	private List<PersonView> partner;
	
	private EventView scheidung;
	
	private List<PersonView> kinder;
	
	private PersonController controller;
	
	private String searchbox;
	
	public void searchFrau(ActionEvent event) {
		search(event, false);
	}
	
	public void searchMann(ActionEvent event) {
		search(event, true);
	}
	
	private void search(ActionEvent event, boolean male) {
		List<PersonView> persons = controller.searchPersons(getSearchbox(), male);
		
		setPartner(persons);
	}
	
	public void changePartner(ActionEvent event) {
		Long personId = controller.getRequestParameterAsLong("personId");
		
		PersonView person = controller.initPerson(personId);
		
		changePartner(person);
	}
	
	private void changePartner(PersonView person) {
		if (person.isMann()) {
			setMann(person);
			setMannId(person.getId());
		} else {
			setFrau(person);
			setFrauId(person.getId());
		}
		
		setSearchbox(null);
		setPartner(null);
	}
	
	public void addNeuMann(ActionEvent event) {
		addPartner(true);
	}
	
	private void addPartner(boolean male) {
		String[] name = getSearchbox().split(",");
		
		PersonView person = new PersonView();
		person.setController(controller);
		person.setMann(male);
		person.setNachname(name[0].trim());
		person.setVornamen(name.length > 1 ? name[1].trim() : "?");
		
		changePartner(person);
	}
	
	public void remove(ActionEvent event) {
		setDeleted(true);
	}
	
	public void addNeuFrau(ActionEvent event) {
		addPartner(false);
	}
	
	public EventView getHochzeit() {
		return hochzeit;
	}

	public void setHochzeit(EventView hochzeit) {
		this.hochzeit = hochzeit;
	}

	public PersonView getMann() {
		return mann;
	}

	public void setMann(PersonView mann) {
		this.mann = mann;
	}

	public PersonView getFrau() {
		return frau;
	}

	public void setFrau(PersonView frau) {
		this.frau = frau;
	}

	public EventView getScheidung() {
		return scheidung;
	}

	public void setScheidung(EventView scheidung) {
		this.scheidung = scheidung;
	}

	public List<PersonView> getKinder() {
		return kinder;
	}

	public void setKinder(List<PersonView> kinder) {
		this.kinder = kinder;
	}

	public PersonController getController() {
		return controller;
	}

	public void setController(PersonController controller) {
		this.controller = controller;
	}

	public List<PersonView> getPartner() {
		return partner;
	}

	public void setPartner(List<PersonView> partner) {
		this.partner = partner;
	}

	public String getSearchbox() {
		return searchbox;
	}

	public void setSearchbox(String searchbox) {
		this.searchbox = searchbox;
	}

}
