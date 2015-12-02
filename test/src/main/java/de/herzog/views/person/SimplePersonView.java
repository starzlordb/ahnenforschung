package de.herzog.views.person;

import de.herzog.db.beans.EventBean;
import de.herzog.db.beans.PersonBean;

public class SimplePersonView {
	private Long id;
	private String nachname;
	private String vornamen;
	private boolean mann;
	private Integer geburtJahr;
	private Integer taufeJahr;
	private Integer todJahr;
	private String geburtOrt;
	private String taufeOrt;
	private String todOrt;
	private String wohnort;
	
	public SimplePersonView(PersonBean person, EventBean geburt, EventBean taufe, EventBean tod) {
		setId(person.getId());
		setNachname(person.getNachname());
		setVornamen(person.getVornamen());
		setMann(person.isMann());
		
		if (geburt != null) {
			setGeburtJahr(geburt.getJahr());
			setGeburtOrt(geburt.getLocation());
		}
		
		if (taufe != null) {
			setTaufeJahr(taufe.getJahr());
			setTaufeOrt(taufe.getLocation());
		}
		
		if (tod != null) {
			setTodJahr(tod.getJahr());
			setTodOrt(tod.getLocation());
		}
		
		setWohnort(person.getLocation());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getVornamen() {
		return vornamen;
	}
	public void setVornamen(String vornamen) {
		this.vornamen = vornamen;
	}
	public boolean isMann() {
		return mann;
	}
	public void setMann(boolean mann) {
		this.mann = mann;
	}
	public Integer getGeburtJahr() {
		return geburtJahr;
	}
	public void setGeburtJahr(Integer geburtJahr) {
		this.geburtJahr = geburtJahr;
	}
	public Integer getTaufeJahr() {
		return taufeJahr;
	}
	public void setTaufeJahr(Integer taufeJahr) {
		this.taufeJahr = taufeJahr;
	}
	public Integer getTodJahr() {
		return todJahr;
	}
	public void setTodJahr(Integer todJahr) {
		this.todJahr = todJahr;
	}
	public String getGeburtOrt() {
		return geburtOrt;
	}
	public void setGeburtOrt(String geburtOrt) {
		this.geburtOrt = geburtOrt;
	}
	public String getTaufeOrt() {
		return taufeOrt;
	}
	public void setTaufeOrt(String taufeOrt) {
		this.taufeOrt = taufeOrt;
	}
	public String getTodOrt() {
		return todOrt;
	}
	public void setTodOrt(String todOrt) {
		this.todOrt = todOrt;
	}
	public String getWohnort() {
		return wohnort;
	}
	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}
}
