package de.herzog.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import de.herzog.controller.person.PersonController;
import de.herzog.db.beans.BinaryBean;
import de.herzog.db.beans.EventBean;
import de.herzog.db.beans.HochzeitBean;
import de.herzog.db.beans.PersonBean;
import de.herzog.db.beans.old.Heirat;
import de.herzog.db.beans.old.Person;
import de.herzog.db.repositories.old.HeiratRepository;
import de.herzog.db.repositories.old.PersonRepository;
import de.herzog.enums.BinaryDataTypeEnum;
import de.herzog.enums.EventTypeEnum;
import de.herzog.enums.person.EventDateModifierEnum;
import de.herzog.util.Helper;
import de.herzog.util.Logger;
import de.herzog.views.person.PersonView;

@ManagedBean(name = MigrationController.MANAGED_BEAN_NAME)
@SessionScoped
public class MigrationController extends AbstractController {

	public static final String MANAGED_BEAN_NAME = "Migration";
	
	private Logger log = new Logger(MigrationController.class);
	
	public class Event {
		public int jahr;
		public int monat;
		public int tag;
		public EventDateModifierEnum modifier = EventDateModifierEnum.EXACT;
	}

	private static final long serialVersionUID = 2800663051672923136L;

	private PersonRepository personRepository;
	private HeiratRepository heiratRepository;
	
	private String documentsPath;
	private EventTypeEnum documentType = EventTypeEnum.BIRTH;
	
	protected void init() {
		personRepository = new PersonRepository();
		heiratRepository = new HeiratRepository();
		
		setDocumentsPath(System.getProperty("user.dir"));
	}
	
	public void importDocuments(ActionEvent event) {
		HashMap<Long, List<File>> fileCacheByKekule = createFileCacheByKekule();
		
		List<PersonBean> personBeans = super.personRepository.getAll();
		List<HochzeitBean> hochzeitBeans = super.hochzeitRepository.getAll();
		
		HashMap<Long, Long> personIdByKekule = new HashMap<Long, Long>();
		for (PersonBean personBean : personBeans) {
			PersonView person = PersonView.fromBean(PersonView.class, personBean, Helper.getManagedBean(PersonController.class));
			
			for (Long kekule : person.getKekule()) {
				personIdByKekule.put(kekule, person.getId());
			}
		}
		
		HashMap<String, Long> hochzeitIdByKekuleMix = new HashMap<String, Long>();
		for (HochzeitBean hochzeitBean : hochzeitBeans) {
			hochzeitIdByKekuleMix.put(String.valueOf(hochzeitBean.getMannId()) + "-" + String.valueOf(hochzeitBean.getFrauId()), hochzeitBean.getId()); 
		}
		
		List<BinaryBean> beans = new ArrayList<BinaryBean>();
		
		for (Iterator<Long> iterator = fileCacheByKekule.keySet().iterator(); iterator.hasNext();) {
			Long kekule = iterator.next();
			List<File> files = fileCacheByKekule.get(kekule);
			
			if (getDocumentType().equals(EventTypeEnum.MARRIAGE) || getDocumentType().equals(EventTypeEnum.DIVORCE)) {
				// TODO
			} else {
				for (File file : files) {
					try {
						BinaryBean newBean = BinaryBean.newBean(getDocumentType(), personIdByKekule.get(kekule));
						newBean.setChangedUid(0l);
						newBean.setCreatedUid(0l);
						newBean.setData(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
						newBean.setEventType(getDocumentType());
						newBean.setFilename(file.getName());
						newBean.setPersonId(personIdByKekule.get(kekule));
						newBean.setType(BinaryDataTypeEnum.DOC);
						beans.add(newBean);
					} catch (Exception e) {
						log.error(e);
					}
				}
			}
		}
		
		log.info("created " + beans.size() + " new binary beans");
	}

	private HashMap<Long, List<File>> createFileCacheByKekule() {
		HashMap<Long, List<File>> fileCacheByKekule = new HashMap<Long, List<File>>();
		
		File folder = new File(getDocumentsPath());
		File[] files = folder.listFiles();
		
		int countFiles = 0;
		int countCorrectFiles = 0;
		int countFolders = 0;
		
		for (File file : files) {
			if (file.isFile()) {
				countFiles++;
				try {
					String[] filename = file.getName().split("([ ]*)-([ ]*)");
					long kekule = Long.parseLong(filename[0]);
					String[] name = filename[1].split("\\.(?=[^\\.]+$)");
					try {
						String[] nameParts = name[0].split(",([ ]*)");
						String nachname = nameParts[0];
						String vornamen = nameParts[1];
						log.info("Kekule: " + kekule + "\tNachname: " + nachname + "\tVorname: " + vornamen);
					} catch (Exception e) {
						log.info("Kekule: " + kekule + "\tName: " + name[0]);
					}
					
					if (!fileCacheByKekule.containsKey(kekule)) {
						fileCacheByKekule.put(kekule, new ArrayList<File>());
					}
					
					fileCacheByKekule.get(kekule).add(file);
					countCorrectFiles++;
				} catch (Exception e) {
					log.error("unknown format: " + file.getName());
				}
			} else {
				countFolders++;
			}
		}
		
		log.info("Ordner: " + countFolders + "\tDateien: " + countFiles + "\tErkannt: " + countCorrectFiles);
		
		return fileCacheByKekule;
	}
	
	public void migrateHeirat(ActionEvent event) {
		List<Heirat> oldHeiraten = heiratRepository.getAll();
		
		long id = 100000l;
		
		if (oldHeiraten != null) {
			for (Heirat oldHeirat : oldHeiraten) {
				log.info("Hochzeit von " + oldHeirat.getMannId() + " und " + oldHeirat.getFrauId() + " (id = " + oldHeirat.getId() + ")");
				HochzeitBean newHeirat = new HochzeitBean();
				newHeirat.setChangedDate(new Date(1000l * (long) oldHeirat.getChangedDate()));
				newHeirat.setChangedUid(1l);
				newHeirat.setCreatedDate(new Date(1000l * (long) oldHeirat.getChangedDate()));
				newHeirat.setCreatedUid(1l);
				newHeirat.setDeleted(oldHeirat.getDeleted() == 1);
				newHeirat.setDivorce(false);
				newHeirat.setFrauId((long) oldHeirat.getFrauId());
				newHeirat.setId((long) oldHeirat.getId());
				newHeirat.setMannId((long) oldHeirat.getMannId());
				newHeirat.setMarriage(true);
				newHeirat.setUuid("uuid-heirat-" + oldHeirat.getId());
				newHeirat = super.hochzeitRepository.save(newHeirat);
				log.info("   ... hochzeit (id = " + newHeirat.getId() + ")");
				
				EventBean hochzeit = new EventBean();
				hochzeit.setId(id++);
				hochzeit.setChangedDate(new Date(1000l * (long) oldHeirat.getChangedDate()));
				hochzeit.setChangedUid(1l);
				hochzeit.setCreatedDate(new Date(1000l * (long) oldHeirat.getChangedDate()));
				hochzeit.setCreatedUid(1l);
				hochzeit.setDeleted(oldHeirat.getDeleted() == 1);
				hochzeit.setEventType(EventTypeEnum.MARRIAGE);
				hochzeit.setFamilySearch(oldHeirat.getDatumFs() == 1);
				hochzeit.setJahr(convertDate(oldHeirat.getDatum()).jahr);
				hochzeit.setLocation(oldHeirat.getOrt());
				hochzeit.setMonat(convertDate(oldHeirat.getDatum()).monat);
				hochzeit.setNachweis(oldHeirat.getDokDa() == 1 || oldHeirat.getDokUs() == 1);
				hochzeit.setNotice(oldHeirat.getNotiz());
				hochzeit.setHochzeitId((long) oldHeirat.getId());
				hochzeit.setSource(oldHeirat.getQuelle());
				hochzeit.setTag(convertDate(oldHeirat.getDatum()).tag);
				hochzeit.setTyp(convertDate(oldHeirat.getDatum()).modifier);
				hochzeit.setUuid("uuid-marriage-" + oldHeirat.getId());
				eventRepository.save(hochzeit);
				log.info("   ... marriage");
				
			}
		}
	}
	
	public void migratePerson(ActionEvent event) {
		List<Person> oldPersons = personRepository.getAll();
		
		long id = 1;
		
		if (oldPersons != null) {
			for (Person oldPerson : oldPersons) {
				log.info("migrating " + oldPerson.getNachname() + ", " + oldPerson.getVornamen() + " (id = " + oldPerson.getId() + ")");
				PersonBean newPerson = new PersonBean();
				newPerson.setBeruf(oldPerson.getBeruf());
				newPerson.setBerufFamilySearch(oldPerson.getBerufFs() == 1);
				newPerson.setChangedDate(new Date(1000l * (long) oldPerson.getChangedDate()));
				newPerson.setChangedUid(1l);
				newPerson.setCreatedDate(new Date(1000l * (long) oldPerson.getChangedDate()));
				newPerson.setCreatedUid(1l);
				newPerson.setDeleted(oldPerson.getDeleted() == 1);
				newPerson.setId((long) oldPerson.getId());
				newPerson.setKonfession(oldPerson.getKonfession());
				newPerson.setKonfessionFamilySearch(oldPerson.getKonfessionFs() == 1);
				newPerson.setLocation(oldPerson.getWohnort());
				newPerson.setLocationFamilySearch(oldPerson.getWohnortFs() == 1);
				newPerson.setMann(oldPerson.getGeschlecht().equalsIgnoreCase("m"));
				newPerson.setMutterFamilySearch(oldPerson.getMutterIdFs() == 1);
				newPerson.setMutterId((long) oldPerson.getMutterId());
				newPerson.setNachname(oldPerson.getNachname());
				newPerson.setNachnameFamilySearch(oldPerson.getNachnameFs() == 1);
				newPerson.setNotice(oldPerson.getNotiz());
				newPerson.setUuid("uuid-person-" + oldPerson.getId());
				newPerson.setVaterFamilySearch(oldPerson.getVaterIdFs() == 1);
				newPerson.setVaterId((long) oldPerson.getVaterId());
				newPerson.setVornamen(oldPerson.getVornamen());
				newPerson.setVornamenFamilySearch(oldPerson.getVornamenFs() == 1);
				newPerson = super.personRepository.save(newPerson);
				log.info("   ... new person (id = " + newPerson.getId() + ")");
				
				EventBean geburt = new EventBean();
				geburt.setId(id++);
				geburt.setChangedDate(new Date(1000l * (long) oldPerson.getChangedDate()));
				geburt.setChangedUid(1l);
				geburt.setCreatedDate(new Date(1000l * (long) oldPerson.getChangedDate()));
				geburt.setCreatedUid(1l);
				geburt.setDeleted(oldPerson.getDeleted() == 1);
				geburt.setEventType(EventTypeEnum.BIRTH);
				geburt.setFamilySearch(oldPerson.getGeburtsdatumFs() == 1);
				geburt.setJahr(convertDate(oldPerson.getGeburtsdatum()).jahr);
				geburt.setLocation(oldPerson.getGeburtsort());
				geburt.setMonat(convertDate(oldPerson.getGeburtsdatum()).monat);
				geburt.setNachweis(oldPerson.getGeburtDokDa() == 1 || oldPerson.getGeburtDokUs() == 1);
				geburt.setNotice(oldPerson.getGeburtsnotiz());
				geburt.setPersonId((long) oldPerson.getId());
				geburt.setSource(oldPerson.getGeburtsquelle());
				geburt.setTag(convertDate(oldPerson.getGeburtsdatum()).tag);
				geburt.setTyp(convertDate(oldPerson.getGeburtsdatum()).modifier);
				geburt.setUuid("uuid-birth-" + oldPerson.getId());
				eventRepository.save(geburt);
				log.info("   ... birth");
				
				EventBean taufe = new EventBean();
				taufe.setId(id++);
				taufe.setChangedDate(new Date(1000l * (long) oldPerson.getChangedDate()));
				taufe.setChangedUid(1l);
				taufe.setCreatedDate(new Date(1000l * (long) oldPerson.getChangedDate()));
				taufe.setCreatedUid(1l);
				taufe.setDeleted(oldPerson.getDeleted() == 1);
				taufe.setEventType(EventTypeEnum.BAPTISM);
				taufe.setFamilySearch(oldPerson.getTaufdatumFs() == 1);
				taufe.setJahr(convertDate(oldPerson.getTaufdatum()).jahr);
				taufe.setLocation(oldPerson.getTaufort());
				taufe.setMonat(convertDate(oldPerson.getTaufdatum()).monat);
				taufe.setNachweis(oldPerson.getTaufDokDa() == 1 || oldPerson.getTaufDokUs() == 1);
				taufe.setNotice(oldPerson.getTaufnotiz());
				taufe.setPersonId((long) oldPerson.getId());
				taufe.setSource(oldPerson.getTaufquelle());
				taufe.setTag(convertDate(oldPerson.getTaufdatum()).tag);
				taufe.setTyp(convertDate(oldPerson.getTaufdatum()).modifier);
				taufe.setUuid("uuid-baptism-" + oldPerson.getId());
				eventRepository.save(taufe);
				log.info("   ... baptism");
				
				EventBean tod = new EventBean();
				tod.setId(id++);
				tod.setChangedDate(new Date(1000l * (long) oldPerson.getChangedDate()));
				tod.setChangedUid(1l);
				tod.setCreatedDate(new Date(1000l * (long) oldPerson.getChangedDate()));
				tod.setCreatedUid(1l);
				tod.setDeleted(oldPerson.getDeleted() == 1);
				tod.setEventType(EventTypeEnum.DEATH);
				tod.setFamilySearch(oldPerson.getSterbedatumFs() == 1);
				tod.setJahr(convertDate(oldPerson.getSterbedatum()).jahr);
				tod.setLocation(oldPerson.getSterbeort());
				tod.setMonat(convertDate(oldPerson.getSterbedatum()).monat);
				tod.setNachweis(oldPerson.getSterbeDokDa() == 1 || oldPerson.getSterbeDokUs() == 1);
				tod.setNotice(oldPerson.getSterbenotiz());
				tod.setPersonId((long) oldPerson.getId());
				tod.setSource(oldPerson.getSterbequelle());
				tod.setTag(convertDate(oldPerson.getSterbedatum()).tag);
				tod.setTyp(convertDate(oldPerson.getSterbedatum()).modifier);
				tod.setUuid("uuid-death-" + oldPerson.getId());
				eventRepository.save(tod);
				log.info("   ... death");
				
			}
		}
		
		
	}
	
	private HashMap<String, Event> eventCache;
	
	private Event convertDate(String date) {
		if (eventCache == null) {
			eventCache = new HashMap<String, MigrationController.Event>();
		}
		
		if (date == null) {
			Event event = new Event();
			event.modifier = EventDateModifierEnum.NONE;
			
			return event;
		}
		
		if (!eventCache.keySet().contains(date)) {
			String[] entries = date.split("-");
			
			if (entries.length == 1) {
				Event event = new Event();
				
				if (date.startsWith("um ") || date.startsWith("ca ")) {
					event.modifier = EventDateModifierEnum.AROUND;
					event.jahr = Integer.parseInt(date.substring(3));
				} else if (date.startsWith("vor ")) {
					event.modifier = EventDateModifierEnum.BEFORE;
					event.jahr = Integer.parseInt(date.substring(4));
				} else if (date.startsWith("nach ")) {
					event.modifier = EventDateModifierEnum.AFTER;
					event.jahr = Integer.parseInt(date.substring(5));
				} else if (date.isEmpty()) {
					event.modifier = EventDateModifierEnum.NONE;
				} else {
					event.jahr = Integer.parseInt(date);
				}
				
				eventCache.put(date, event);
				
				return event;
			} else if (entries.length == 3) {
				Event event = new Event();
				event.jahr = Integer.parseInt(entries[0]);
				event.monat = Integer.parseInt(entries[1]);
				
				if (entries[2].startsWith("um ") || entries[2].startsWith("ca ")) {
					event.modifier = EventDateModifierEnum.AROUND;
					event.tag = Integer.parseInt(entries[2].substring(3));
				} else if (entries[2].startsWith("vor ")) {
					event.modifier = EventDateModifierEnum.BEFORE;
					event.tag = Integer.parseInt(entries[2].substring(4));
				} else if (entries[2].startsWith("nach ")) {
					event.modifier = EventDateModifierEnum.AFTER;
					event.tag = Integer.parseInt(entries[2].substring(5));
				} else if (entries[2].isEmpty()) {
					event.modifier = EventDateModifierEnum.NONE;
				} else {
					event.tag = Integer.parseInt(entries[2]);
				}
				
				eventCache.put(date, event);
				
				return event;
			} else {
				log.error("unbekanntes Format: " + date);
				return null;
			}
		} else {
			return eventCache.get(date);
		}
	}

	public String getDocumentsPath() {
		return documentsPath;
	}

	public void setDocumentsPath(String documentsPath) {
		this.documentsPath = documentsPath;
	}

	public EventTypeEnum getDocumentType() {
		return documentType;
	}

	public void setDocumentType(EventTypeEnum documentType) {
		this.documentType = documentType;
	}
	
}
