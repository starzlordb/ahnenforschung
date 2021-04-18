package de.herzog.controller.person;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import de.herzog.controller.AbstractController;
import de.herzog.controller.navigation.Navigation;
import de.herzog.db.beans.AbstractDomainBean;
import de.herzog.db.beans.BinaryBean;
import de.herzog.db.beans.EventBean;
import de.herzog.db.beans.HochzeitBean;
import de.herzog.db.beans.PersonBean;
import de.herzog.enums.BinaryDataTypeEnum;
import de.herzog.enums.EventTypeEnum;
import de.herzog.enums.person.EventDateModifierEnum;
import de.herzog.util.Helper;
import de.herzog.util.Logger;
import de.herzog.views.BinaryView;
import de.herzog.views.EventView;
import de.herzog.views.hochzeit.HochzeitView;
import de.herzog.views.person.KekuleView;
import de.herzog.views.person.PersonView;
import de.herzog.views.person.SimplePersonView;

@ManagedBean(name = PersonController.MANAGED_BEAN_NAME)
@SessionScoped
public class PersonController extends AbstractController {
	
	public static final String MANAGED_BEAN_NAME = "Person";
	
	private static final long serialVersionUID = 3245970480382285266L;

	private Logger log = new Logger(PersonController.class);
	
	private PersonView person;
	private boolean editable = true;
	
	// search
	private List<PersonView> searchedPersons;
	private String search;
	
	// Hochzeit Upload
	private String selectedHochzeitUuid;
	private Part uploadHochzeit;
	
	private List<SimplePersonView> allPersons;
	
	@Override
	protected void init() {
		super.init();
		
		setPerson(initPerson(Helper.getManagedBean(Navigation.class).getPersonId()));
		setSearch(null);
		setSearchedPersons(null);
		
		initAllPersons();
		setEditable(false);
	}
	
	private void initAllPersons() {
		if (getAllPersons() == null || getAllPersons().size() == 0) {
			List<SimplePersonView> searchPersons = new ArrayList<SimplePersonView>();
			
			List<PersonBean> allPersons = personRepository.getAll();
			List<EventBean> allEvents = eventRepository.getAll();
			
			BeansById<EventBean> eventByPersonId = null;
			try {
				eventByPersonId = new BeansById<EventBean>(allEvents, EventBean.class.getMethod("getPersonId"));
			} catch (Exception e) {
				log.error(e);
			}
			
			for (PersonBean person : allPersons) {
				EventBean geburt = null;
				EventBean taufe = null;
				EventBean tod = null;
				
				if (eventByPersonId != null) {
					List<EventBean> events = eventByPersonId.getAll(person.getId());
					
					if (events != null) {
						for (EventBean event : events) {
							if (event.getEventType().equals(EventTypeEnum.BIRTH)) {
								geburt = event;
							} else if (event.getEventType().equals(EventTypeEnum.BAPTISM)) {
								taufe = event;
							} else if (event.getEventType().equals(EventTypeEnum.DEATH)) {
								tod = event;
							}
						}
					}
				}
				
				searchPersons.add(new SimplePersonView(person, geburt, taufe, tod));
			}

			setAllPersons(searchPersons);
		}
	}

	public void switchEditable(ActionEvent event) {
		setEditable(!isEditable());
	}
	
	public void uploadHochzeit(ActionEvent event) {
		try {
			BinaryBean bean = uploadFile(getUploadHochzeit());
			
			bean.setEventType(EventTypeEnum.MARRIAGE);
			
			for (HochzeitView hochzeit : getPerson().getHochzeiten()) {
				if (hochzeit.getUuid().equals(getSelectedHochzeitUuid())) {
					List<BinaryView> documents = hochzeit.getHochzeit().getDocuments();
					documents.add(BinaryView.fromBean(BinaryView.class, bean, this));
					hochzeit.getHochzeit().setDocuments(documents);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	
	}
	
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}
	
	public Long persist(BinaryView view) {
		if (view == null || view.getId() == null && view.getData() == null) {
			return null;
		}
		
		BinaryBean bean = BinaryBean.toBean(BinaryBean.class, view);
		
		bean = binaryRepository.save(bean);
		
		return bean.getId();
	}
	
	public Long persist(EventView view) {
		if (view == null) {
			return null;
		}
		
		EventBean bean = EventBean.toBean(EventBean.class, view);
		
		bean = eventRepository.save(bean);
		
		return bean.getId();
	}
	
	public Long persist(HochzeitView view) {
		if (view == null) {
			return null;
		}
		
		HochzeitBean bean = HochzeitBean.toBean(HochzeitBean.class, view);
		
		bean = hochzeitRepository.save(bean);
		
		return bean.getId();
	}
	
	public Long persist(PersonView view) {
		if (view == null) {
			return null;
		}
		
		PersonBean bean = PersonBean.toBean(PersonBean.class, view);
		
		bean = personRepository.save(bean);
		
		return bean.getId();
	}
	
	public void persist(ActionEvent event) {
		PersonView person = getPerson();

		// Vater
		PersonView vater = person.getVater();
		if (vater != null) {
			Long vaterId = persist(vater);
			person.setVaterId(vaterId);
		}
		
		// Mutter
		PersonView mutter = person.getMutter();
		if (mutter != null) {
			Long mutterId = persist(mutter);
			person.setMutterId(mutterId);
		}
		
		// Stammdaten
		Long personId = persist(person);
		
		// Dokumente
		for (BinaryView document : person.getDocuments()) {
			document.setPersonId(personId);
			persist(document);
		}
		
		// Events
		EventView geburt = person.getGeburt();
		if (geburt != null) {
			geburt.setPersonId(personId);
			persist(geburt);
			
			for (BinaryView document : geburt.getDocuments()) {
				document.setPersonId(personId);
				persist(document);
			}
		}
		
		EventView taufe = person.getTaufe();
		if (taufe != null) {
			taufe.setPersonId(personId);
			persist(taufe);
			
			for (BinaryView document : taufe.getDocuments()) {
				document.setPersonId(personId);
				persist(document);
			}
		}
		
		EventView tod = person.getTod();
		if (tod != null) {
			tod.setPersonId(personId);
			persist(tod);
			
			for (BinaryView document : tod.getDocuments()) {
				document.setPersonId(personId);
				persist(document);
			}
		}
		
		// Hochzeiten
		for (HochzeitView hochzeit : person.getHochzeiten()) {
			PersonView partner = null;
			
			if (person.isMann()) {
				partner = hochzeit.getFrau();
			} else {
				partner = hochzeit.getMann();
			}
			
			Long partnerId = null;
			if (partner != null) {
				partnerId = persist(partner);
			}
			
			if (person.isMann()) {
				hochzeit.setMannId(personId);
				hochzeit.setFrauId(partnerId);
			} else {
				hochzeit.setFrauId(personId);
				hochzeit.setMannId(partnerId);
			}
			
			Long hochzeitId = persist(hochzeit);
			
			EventView marriage = hochzeit.getHochzeit();
			if (marriage != null) {
				marriage.setHochzeitId(hochzeitId);
				persist(marriage);
				
				for (BinaryView document : marriage.getDocuments()) {
					document.setHochzeitId(hochzeitId);
					persist(document);
				}
			}
		}
		
		setPerson(initPerson(personId));
	}
	
	public List<SelectItem> getHochzeitUuidSelect() {
		List<SelectItem> select = new ArrayList<SelectItem>();
		
		int i = 1;
		
		if (getPerson() != null && getPerson().getHochzeiten() != null) {
			for (HochzeitView hochzeit : getPerson().getHochzeiten()) {
				PersonView partner = getPerson().isMann() ? hochzeit.getFrau() : hochzeit.getMann();
				String name = partner == null ? "Hochzeit #" + i : partner.getNachname() + ", " + partner.getVornamen();
				
				select.add(new SelectItem(hochzeit.getUuid(), name));
				i++;
			}
		}
		
		return select;
	}

	public BinaryBean uploadFile(Part upload) throws Exception {
		InputStream inputStream = upload.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		byte[] buffer = new byte[4096];
		int bytesRead = 0;
		while (true) {
			bytesRead = inputStream.read(buffer);
			if (bytesRead > 0) {
				outputStream.write(buffer, 0, bytesRead);
			} else {
				break;
			}
		}
		inputStream.close();
		
		String filename = getFilename(upload);
		String[] splitted = filename.split("\\.");
		String extension = splitted[splitted.length - 1].toUpperCase();
		
		BinaryBean bean = new BinaryBean();
		bean.setType(BinaryDataTypeEnum.valueOf(extension));
		bean.setData(outputStream.toByteArray());
		bean.setFilename(getFilename(upload));
		
		return bean;
	}
	
	public List<SelectItem> getEventTypeSelect() {
		List<SelectItem> select = new ArrayList<SelectItem>();
		
		for (EventTypeEnum value : EventTypeEnum.values()) {
			select.add(new SelectItem(value, value.getLabel()));
		}
		
		return select;
	}
	
	public List<SelectItem> getEventDateModifier() {
		List<SelectItem> select = new ArrayList<SelectItem>();
		
		for (EventDateModifierEnum value : EventDateModifierEnum.values()) {
			select.add(new SelectItem(value, value.getLabel()));
		}
		
		return select;
	}

	public PersonView initPerson(Long personId) {
		PersonBean personBean = null;
		
		if (personId == null || personId.equals(0l) || personId.equals(-1l)) {
			return null;
		}
		
		personBean = personRepository.getById(personId);
		
		PersonView person = initStammdaten(personBean);
		
		if (person != null) {
			List<BinaryView> documents = new ArrayList<BinaryView>();
			
			List<BinaryBean> beans = binaryRepository.getBinaryByPersonIdAndType(personId, EventTypeEnum.PERSON);
			if (beans != null) {
				for (BinaryBean binaryBean : beans) {
					documents.add(BinaryView.fromBean(BinaryView.class, binaryBean, this));
				}
			}
			person.setDocuments(documents);
			
			List<HochzeitBean> hochzeiten = hochzeitRepository.getByPersonId(personId);
			
			List<HochzeitView> hochzeitViews = new ArrayList<HochzeitView>();
			List<PersonView> alleKinder = new ArrayList<PersonView>();
			
			List<PersonBean> kinderBeans = null;
			
			if (personBean.isMann()) {
				kinderBeans = personRepository.getByVaterId(personId);
			} else {
				kinderBeans = personRepository.getByMutterId(personId);
			}
			
			List<PersonView> kinder = new ArrayList<PersonView>();
			
			if (kinderBeans != null) {
				for (PersonBean kind : kinderBeans) {
					kinder.add(initStammdaten(kind));
				}
			}
			
			alleKinder.addAll(kinder);
			
			if (hochzeiten != null) {
				for (HochzeitBean hochzeit : hochzeiten) {
					HochzeitView view = initHochzeit(person, hochzeit, alleKinder);
					hochzeitViews.add(view);
				}
			}
			
			person.setKinder(alleKinder);
			person.setHochzeiten(hochzeitViews);
		}
		
		return person;
	}
	
	private HochzeitView initHochzeit(PersonView person, HochzeitBean hochzeit, List<PersonView> kinder) {
		HochzeitView view = HochzeitView.fromBean(HochzeitView.class, hochzeit, this);
		
		view.setHochzeit(EventView.fromBean(EventView.class, eventRepository.getMarriageById(hochzeit.getId()), this));
		view.setScheidung(EventView.fromBean(EventView.class, eventRepository.getDivorceById(hochzeit.getId()), this));
		
		view.setFrau(!person.isMann() ? person : initStammdaten(personRepository.getById(hochzeit.getFrauId())));
		view.setMann(person.isMann() ? person : initStammdaten(personRepository.getById(hochzeit.getMannId())));
		
		List<BinaryView> hVDocuments = new ArrayList<BinaryView>();
		List<BinaryBean> hDocuments = binaryRepository.getBinaryByHochzeitIdAndType(hochzeit.getId(), EventTypeEnum.MARRIAGE);
		if (hDocuments != null) {
			for (BinaryBean binaryBean : hDocuments) {
				hVDocuments.add(BinaryView.fromBean(BinaryView.class, binaryBean, this));
			}
		}
		view.getHochzeit().setDocuments(hVDocuments);
		
		List<PersonView> myKinder = new ArrayList<PersonView>();
		
		if (kinder != null) {
			for (PersonView kind : kinder) {
				if (kind.getVaterId() != null && kind.getVaterId().equals(view.getMannId()) && kind.getMutterId() != null && kind.getMutterId().equals(view.getFrauId())) {
					myKinder.add(kind);
				}
			}
		}
		
		view.setKinder(myKinder);
		
		return view;
	}
	
	private PersonView initStammdaten(PersonBean bean) {
		if (bean == null) {
			return null;
		}
		
		PersonView person = PersonView.fromBean(PersonView.class, bean, this);
		
		long id = bean.getId() == null ? 0l : bean.getId();
		
		person.setGeburt(EventView.fromBean(EventView.class, eventRepository.getEventByPersonIdAndType(id, EventTypeEnum.BIRTH), this));
		
		if (person.getGeburt() != null) {
			List<BinaryView> documents = new ArrayList<BinaryView>();
			
			List<BinaryBean> beans = binaryRepository.getBinaryByPersonIdAndType(id, EventTypeEnum.BIRTH);
			if (beans != null) {
				for (BinaryBean binaryBean : beans) {
					documents.add(BinaryView.fromBean(BinaryView.class, binaryBean, this));
				}
			}
			person.getGeburt().setDocuments(documents);
		}
		
		person.setTaufe(EventView.fromBean(EventView.class, eventRepository.getEventByPersonIdAndType(id, EventTypeEnum.BAPTISM), this));

		if (person.getTaufe() != null) {
			List<BinaryView> documents = new ArrayList<BinaryView>();
			
			List<BinaryBean> beans = binaryRepository.getBinaryByPersonIdAndType(id, EventTypeEnum.BAPTISM);
			if (beans != null) {
				for (BinaryBean binaryBean : beans) {
					documents.add(BinaryView.fromBean(BinaryView.class, binaryBean, this));
				}
			}
			person.getTaufe().setDocuments(documents);
		}
		
		person.setTod(EventView.fromBean(EventView.class, eventRepository.getEventByPersonIdAndType(id, EventTypeEnum.DEATH), this));

		if (person.getTod() != null) {
			List<BinaryView> documents = new ArrayList<BinaryView>();
			
			List<BinaryBean> beans = binaryRepository.getBinaryByPersonIdAndType(id, EventTypeEnum.DEATH);
			if (beans != null) {
				for (BinaryBean binaryBean : beans) {
					documents.add(BinaryView.fromBean(BinaryView.class, binaryBean, this));
				}
			}
			person.getTod().setDocuments(documents);
		}
		
		return person;		
	}

	public PersonView getPerson() {
		return person;
	}

	public void setPerson(PersonView person) {
		this.person = person;
	}

	public List<KekuleView> calculateKekule(Long personId) {
		if (personId == null) {
			return null;
		}
		
		if (personId.equals(getConfiguration().getPrimaryPersonId())) {
			List<KekuleView> kekule = new ArrayList<KekuleView>();
			kekule.add(new KekuleView(1l, 1l));
			return kekule;
		}
		
		List<KekuleView> kekule = new ArrayList<KekuleView>();
		
		PersonBean person = personRepository.getById(personId);
		
		List<PersonBean> children = personRepository.getChildrenByPerson(personId);
		
		if (person != null && children != null) {
			for (PersonBean child : children) {
				List<KekuleView> subList = calculateKekule(child.getId());
				
				for (KekuleView entry : subList) {
					kekule.add(new KekuleView(entry.getKekule() * 2l + (person.isMann() ? 0l : 1l), entry.getGeneration() + 1l));
				}
			}
		}
		
		return kekule;
	}
	
	private class BeansById<X extends AbstractDomainBean> {
		private HashMap<Long, List<X>> cache;
		
		public BeansById(List<X> data) {
			cache = new HashMap<Long, List<X>>();
			
			if (data != null) {
				for (X entry : data) {
					if (entry != null && entry.getId() != null) {
						if (cache.containsKey(entry.getId())) {
							List<X> values = cache.get(entry.getId());
							values.add(entry);
							cache.put(entry.getId(), values);
						} else {
							List<X> values = new ArrayList<X>();
							values.add(entry);
							cache.put(entry.getId(), values);
						}
					}
				}
			}
		}
		
		public BeansById(List<X> data, Method method) {
			cache = new HashMap<Long, List<X>>();
			
			if (data != null && method != null) {
				for (X entry : data) {
					try {
						Long id = (Long) method.invoke(entry);
					
						if (cache.containsKey(id)) {
							List<X> values = cache.get(id);
							values.add(entry);
							cache.put(id, values);
						} else {
							List<X> values = new ArrayList<X>();
							values.add(entry);
							cache.put(id, values);
						}
					} catch (Exception e) {
						log.error(e);
					}
				}
			}
		}
		
		public X get(Long id) {
			if (cache.containsKey(id) && cache.get(id) != null && cache.get(id).size() == 1) {
				return cache.get(id).get(0);
			}
			
			return null;
		}
		
		public List<X> getAll(Long id) {
			if (cache.containsKey(id)) {
				return cache.get(id);
			}
			
			return null;
		}
	}
	
	public void searchPersons(ValueChangeEvent event) {
		setSearchedPersons(searchPersons(event.getNewValue().toString(), null));
	}
	
	public List<PersonView> searchPersons(String search, Boolean male) {
		if (search == null || search.trim().isEmpty()) {
			return null;
		}
		
		search = search.replaceAll("[^A-Za-z0-9]", "");
		
		List<PersonBean> allPersons = personRepository.getAll();
		List<HochzeitBean> allHochzeiten = hochzeitRepository.getAll();
		List<EventBean> allEvents = eventRepository.getAll();
		
		BeansById<EventBean> eventByPersonId = null;
		BeansById<EventBean> eventByHochzeitId = null;
		try {
			eventByPersonId = new BeansById<EventBean>(allEvents, EventBean.class.getMethod("getPersonId"));
			eventByHochzeitId = new BeansById<EventBean>(allEvents, EventBean.class.getMethod("getHochzeitId"));
		} catch (Exception e) {
			log.error(e);
		}
		
		BeansById<PersonBean> personById = new BeansById<PersonBean>(allPersons);
		BeansById<HochzeitBean> hochzeitById = new BeansById<HochzeitBean>(allHochzeiten);
		BeansById<HochzeitBean> hochzeitByMannId = null;
		try {
			hochzeitByMannId = new BeansById<HochzeitBean>(allHochzeiten, HochzeitBean.class.getMethod("getMannId"));
		} catch (Exception e) {
			log.error(e);
		}
		
		BeansById<HochzeitBean> hochzeitByFrauId = null;
		try {
			hochzeitByFrauId = new BeansById<HochzeitBean>(allHochzeiten, HochzeitBean.class.getMethod("getFrauId"));
		} catch (Exception e) {
			log.error(e);
		}
		
		HashSet<Long> personIds = new HashSet<Long>();
		
		if (allPersons != null) {
			for (PersonBean person : allPersons) {
				if (male == null || (person.isMann() && male) || (!person.isMann() && !male)) {
					if (personIds.contains(person.getId()) ||
							contains(person.getNachname() + " " + person.getVornamen(), search) ||
							contains(person.getBeruf(), search) ||
							contains(person.getKonfession(), search) ||
							contains(person.getLocation(), search) ||
							contains(person.getNotice(), search)) {
						personIds.add(person.getId());
					}
				}
			}
		}
		
		if (allEvents != null) {
			for (EventBean event : allEvents) {
				switch (event.getEventType()) {
				case BAPTISM:
				case BIRTH:
				case BURIAL:
				case DEATH:
					if (personIds.contains(event.getPersonId()) ||
							contains(event.getLocation(), search) ||
							contains(event.getNotice(), search) ||
							contains(event.getSource(), search)) {
						personIds.add(event.getPersonId());
					}
					break;
				case DIVORCE:
				case MARRIAGE:
					HochzeitBean hochzeit = hochzeitById.get(event.getHochzeitId());
					boolean searched = contains(event.getLocation(), search) ||
							contains(event.getNotice(), search) ||
							contains(event.getSource(), search);
					
					if (hochzeit != null && hochzeit.getMannId() != null && !personIds.contains(hochzeit.getMannId()) && searched) {
						personIds.add(hochzeit.getMannId());
					}
					if (hochzeit != null && hochzeit.getFrauId() != null && !personIds.contains(hochzeit.getFrauId()) && searched) {
						personIds.add(hochzeit.getFrauId());
					}
					break;
				default:
					break;
				
				}
			}
		}
		
		List<PersonView> persons = new ArrayList<PersonView>();
		for (Long personId : personIds) {
			PersonBean person = personById.get(personId);
			
			if (person != null) {
				List<EventBean> events = eventByPersonId.getAll(personId);
				
				PersonView pView = PersonView.fromBean(PersonView.class, person, this);
				
				if (events != null) {
					for (EventBean event : events) {
						switch (event.getEventType()) {
						case BAPTISM:
							pView.setTaufe(EventView.fromBean(EventView.class, event, this));
							break;
						case BIRTH:
							pView.setGeburt(EventView.fromBean(EventView.class, event, this));
							break;
						case DEATH:
							pView.setTod(EventView.fromBean(EventView.class, event, this));
							break;
						default:
							break;
						}
					}
				}
				
				List<HochzeitBean> hochzeiten = null;
				
				if (person.isMann()) {
					hochzeiten = hochzeitByMannId.getAll(personId);
				} else {
					hochzeiten = hochzeitByFrauId.getAll(personId);
				}
				
				if (hochzeiten != null) {
					List<HochzeitView> hViews = new ArrayList<HochzeitView>();
					
					for (HochzeitBean hochzeit : hochzeiten) {
						HochzeitView hView = HochzeitView.fromBean(HochzeitView.class, hochzeit, this);
						List<EventBean> hochzeitEvents = eventByHochzeitId.getAll(hochzeit.getId());
						
						if (hochzeitEvents != null) {
							for (EventBean event : hochzeitEvents) {
								switch (event.getEventType()) {
								case DIVORCE:
									hView.setScheidung(EventView.fromBean(EventView.class, event, this));
									break;
								case MARRIAGE:
									hView.setHochzeit(EventView.fromBean(EventView.class, event, this));
									break;
								default:
									break;
								}
							}
						}
						
						hViews.add(hView);
					}
					pView.setHochzeiten(hViews);
				}
				
				persons.add(pView);
			}
		}
		
		Collections.sort(persons, new Comparator<PersonView>() {

			public int compare(PersonView o1, PersonView o2) {
				String name1 = o1.getNachname() + ", " + o1.getVornamen();
				String name2 = o2.getNachname() + ", " + o2.getVornamen();

				return name1.compareTo(name2);
			}
		});
		
		if (persons == null || persons.size() == 0) {
			persons = null;
		}
		
		return persons;
	}
	
	private boolean contains(String haystack, String search) {
		if (haystack == null || haystack.isEmpty()) {
			return false;
		}
		
		if (search == null || search.isEmpty()) {
			return true;
		}
		
		String[] aSearch = search.trim().toLowerCase().split(" ");
		haystack = haystack.toLowerCase();
		boolean contains = true;
		
		for (String a : aSearch) {
			contains &= haystack.toLowerCase().contains(a.toLowerCase());
		}
		
		return contains;
	}

	public List<PersonView> getSearchedPersons() {
		return searchedPersons;
	}

	public void setSearchedPersons(List<PersonView> searchedPersons) {
		this.searchedPersons = searchedPersons;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getSelectedHochzeitUuid() {
		return selectedHochzeitUuid;
	}

	public void setSelectedHochzeitUuid(String selectedHochzeitUuid) {
		this.selectedHochzeitUuid = selectedHochzeitUuid;
	}

	public Part getUploadHochzeit() {
		return uploadHochzeit;
	}

	public void setUploadHochzeit(Part uploadHochzeit) {
		this.uploadHochzeit = uploadHochzeit;
	}

	public List<SimplePersonView> getAllPersons() {
		return allPersons;
	}

	public void setAllPersons(List<SimplePersonView> allPersons) {
		this.allPersons = allPersons;
	}

}
