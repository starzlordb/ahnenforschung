package de.herzog.views;

import java.io.Serializable;

import de.herzog.controller.person.PersonController;

public interface AbstractView extends Serializable {
	public PersonController getController();
	public void setController(PersonController controller);
}
