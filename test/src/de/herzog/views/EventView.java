package de.herzog.views;

import java.util.ArrayList;
import java.util.List;

import de.herzog.controller.person.PersonController;
import de.herzog.db.beans.EventBean;

public class EventView extends EventBean implements AbstractView {
	
	private static final long serialVersionUID = -7584559368095315557L;
	
	private PersonController controller;
	
	private List<BinaryView> documents;

	public PersonController getController() {
		return controller;
	}

	public void setController(PersonController controller) {
		this.controller = controller;
	}

	public List<BinaryView> getDocuments() {
		if (documents == null) {
			documents = new ArrayList<BinaryView>();
		}
		
		return documents;
	}

	public void setDocuments(List<BinaryView> documents) {
		this.documents = documents;
	}
}
