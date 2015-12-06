package de.herzog.views.person;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;

import de.herzog.controller.person.PersonController;
import de.herzog.db.beans.BinaryBean;
import de.herzog.db.beans.EventBean;
import de.herzog.db.beans.HochzeitBean;
import de.herzog.db.beans.PersonBean;
import de.herzog.enums.EventTypeEnum;
import de.herzog.enums.person.EventDateModifierEnum;
import de.herzog.util.Logger;
import de.herzog.views.AbstractView;
import de.herzog.views.BinaryView;
import de.herzog.views.EventView;
import de.herzog.views.hochzeit.HochzeitView;

public class PersonView extends PersonBean implements AbstractView {
	
	private Logger log = new Logger(PersonView.class);
	
	private static final long serialVersionUID = 3134697897828650612L;

	private PersonView vater;
	private PersonView mutter;
	
	private List<PersonView> elternteil;
	private String searchbox;
		
	private EventView geburt;
	private EventView taufe;
	private EventView tod;
	
	private List<HochzeitView> hochzeiten;
	
	private PersonController controller;
	
	private List<PersonView> kinder;
	
	private List<KekuleView> kekule;
	
	private Part upload;
	private EventTypeEnum uploadType;
	
	private List<BinaryView> documents;
	
	public void closeVater(ActionEvent event) {
		setVater(null);
		setVaterId(-1l);
	}
	
	public void removeVater(ActionEvent event) {
		setVater(null);
		setVaterId(null);
	}

	public void closeMutter(ActionEvent event) {
		setMutter(null);
		setMutterId(-1l);
	}
	
	public void removeMutter(ActionEvent event) {
		setMutter(null);
		setMutterId(null);
	}
	
	public void addHochzeit(ActionEvent event) {
		List<HochzeitView> hochzeiten = getHochzeiten();
		
		if (hochzeiten == null) {
			hochzeiten = new ArrayList<HochzeitView>();
		}
		
		HochzeitBean hochzeit = new HochzeitBean();
		if (isMann()) {
			hochzeit.setMannId(getId());
		} else { 
			hochzeit.setFrauId(getId());
		}
		
		EventBean eventBean = new EventBean();
		eventBean.setEventType(EventTypeEnum.MARRIAGE);
		
		hochzeit.setMarriage(true);
		
		HochzeitView view = HochzeitView.fromBean(HochzeitView.class, hochzeit, controller);
		view.setHochzeit(EventView.fromBean(EventView.class, eventBean, controller));
		
		hochzeiten.add(view);
		setHochzeiten(hochzeiten);
	}
	
	public void upload(ActionEvent event) {
		try {
			BinaryBean bean = controller.uploadFile(getUpload());
			
			EventTypeEnum type = getUploadType();
			bean.setEventType(type);
			
			List<BinaryView> documents = null;
			
			switch (type) {
			case BAPTISM:
				documents = getTaufe().getDocuments();
				documents.add(BinaryView.fromBean(BinaryView.class, bean, controller));
				getTaufe().setDocuments(documents);
				break;
			case BIRTH:
				documents = getGeburt().getDocuments();
				documents.add(BinaryView.fromBean(BinaryView.class, bean, controller));
				getGeburt().setDocuments(documents);
				break;
			case DEATH:
				documents = getTod().getDocuments();
				documents.add(BinaryView.fromBean(BinaryView.class, bean, controller));
				getTod().setDocuments(documents);
				break;
			case PERSON:
				documents = getDocuments();
				documents.add(BinaryView.fromBean(BinaryView.class, bean, controller));
				setDocuments(documents);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public void searchElternteil(ActionEvent event) {
		search(event, null);
	}
	
	private void search(ActionEvent event, Boolean male) {
		List<PersonView> persons = controller.searchPersons(getSearchbox(), male);
		
		setElternteil(persons);
	}
	
	public void useElternteil(ActionEvent event) {
		Long personId = controller.getRequestParameterAsLong("personId");
		
		PersonView person = controller.initPerson(personId);
		
		changeElternteil(person);
	}
	
	private void changeElternteil(PersonView person) {
		if (person.isMann()) {
			setVater(person);
			setVaterId(person.getId());
		} else {
			setMutter(person);
			setMutterId(person.getId());
		}
		
		setSearchbox(null);
		setElternteil(null);
	}
	
	public void addMutter(ActionEvent event) {
		addElternteil(false);
	}
	
	public void addVater(ActionEvent event) {
		addElternteil(true);
	}
	
	private void addElternteil(boolean male) {
		String[] name = getSearchbox().split(",");
		
		PersonView person = new PersonView();
		person.setController(controller);
		person.setMann(male);
		person.setNachname(name[0].trim());
		person.setVornamen(name.length > 1 ? name[1].trim() : "?");
		
		changeElternteil(person);
	}
	
	public EventView getGeburt() {
		return geburt;
	}

	public void setGeburt(EventView geburt) {
		this.geburt = geburt;
	}

	public EventView getTaufe() {
		return taufe;
	}

	public void setTaufe(EventView taufe) {
		this.taufe = taufe;
	}

	public EventView getTod() {
		return tod;
	}

	public void setTod(EventView tod) {
		this.tod = tod;
	}

	public List<HochzeitView> getHochzeiten() {
		return hochzeiten;
	}

	public void setHochzeiten(List<HochzeitView> hochzeiten) {
		this.hochzeiten = hochzeiten;
	}

	public List<PersonView> getKinder() {
		return kinder;
	}

	public void setKinder(List<PersonView> kinder) {
		this.kinder = kinder;
	}

	public PersonView getVater() {
		if (vater == null) {
			vater = controller.initPerson(getVaterId());
		}
		
		return vater;
	}

	public void setVater(PersonView vater) {
		this.vater = vater;
	}

	public PersonView getMutter() {
		if (mutter == null) {
			mutter = controller.initPerson(getMutterId());
		}
		
		return mutter;
	}

	public void setMutter(PersonView mutter) {
		this.mutter = mutter;
	}

	public PersonController getController() {
		return controller;
	}

	public void setController(PersonController controller) {
		this.controller = controller;
	}

	public List<KekuleView> getKekule() {
		if (kekule == null) {
			kekule = controller.calculateKekule(getId());
		}
		return kekule;
	}

	public List<PersonView> getElternteil() {
		return elternteil;
	}

	public void setElternteil(List<PersonView> elternteil) {
		this.elternteil = elternteil;
	}

	public String getSearchbox() {
		return searchbox;
	}

	public void setSearchbox(String searchbox) {
		this.searchbox = searchbox;
	}

	public Part getUpload() {
		return upload;
	}

	public void setUpload(Part upload) {
		this.upload = upload;
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

	public EventTypeEnum getUploadType() {
		return uploadType;
	}

	public void setUploadType(EventTypeEnum uploadType) {
		this.uploadType = uploadType;
	}

	public String getPeriod() {
		try {
			EventView geburt = getGeburt() != null && !getGeburt().getTyp().equals(EventDateModifierEnum.NONE) ? getGeburt() : getTaufe();
			EventView tod = getTod();
			
			DateTime start = null;
			if (geburt != null && geburt.getJahr() != null && geburt.getJahr() > 0) {
				start = new DateTime(geburt.getJahr(), geburt.getMonat() == null || geburt.getMonat() == 0 ? 6 : geburt.getMonat(), geburt.getTag() == 0 ? 15 : geburt.getTag(), 0, 0);
			}
			
			DateTime end = null;
			if (tod != null && tod.getJahr() != null && tod.getJahr() > 0) {
				end = new DateTime(tod.getJahr(), tod.getMonat() == null || tod.getMonat() == 0 ? 6 : tod.getMonat(), tod.getTag() == 0 ? 15 : tod.getTag(), 0, 0);
			} else {
				end = new DateTime();
			}
			
			if (start == null) {
				return "n/a";
			} else {
				boolean exact = false;
				Period period = new Period(start, end);
				PeriodFormatterBuilder formatBuilder = new PeriodFormatterBuilder().appendYears().appendSuffix(" Jahr", " Jahre");
				
				if (geburt.getMonat() != null && geburt.getMonat() > 0 && tod.getMonat() != null && tod.getMonat() > 0) {
					formatBuilder.appendSeparator(", ").appendMonths().appendSuffix(" Monat", " Monate");
				}
				if (geburt.getTag() != null && geburt.getTag() > 0 && tod.getTag() != null && tod.getTag() > 0) {
					exact = true;
					formatBuilder.appendSeparator(", ").appendDays().appendSuffix(" Tag", " Tage");
				}
	
				return (exact ? "" : "etwa ") + formatBuilder.toFormatter().print(period);
			}
		} catch (Exception e) {
			log.error(e.getMessage() + " @" + e.getStackTrace()[0].getLineNumber());
		}
		
		return "";
	}
}
