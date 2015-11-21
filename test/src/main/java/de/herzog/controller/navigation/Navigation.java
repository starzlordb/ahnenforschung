package de.herzog.controller.navigation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import de.herzog.controller.AbstractController;
import de.herzog.controller.person.PersonController;
import de.herzog.controller.person.StammbaumController;
import de.herzog.enums.navigation.PageEnum;
import de.herzog.util.Helper;
import de.herzog.util.Logger;

@ManagedBean(name = Navigation.MANAGED_BEAN_NAME)
@SessionScoped
public class Navigation extends AbstractController {
	private static final long serialVersionUID = -3671934685588184543L;
	
	public static final String MANAGED_BEAN_NAME = "Navigation";
	
	private PageEnum page = PageEnum.PERSON;
	private long personId;
	
	private Logger log = new Logger(Navigation.class);

	protected void init() {
		super.init();
		
		setPersonId(getConfiguration().getPrimaryPersonId());
	}
	
	public void navigate() {
		String page = getRequestParameter("action");
		
		try {
			setPage(PageEnum.valueOf(page));
		} catch (Exception e) {
			log.warn(e);
			
			setPage(PageEnum.PERSON);
		}
	}
	
	public void navigate(ActionEvent event) {
		navigate();
	}

	public void open(ActionEvent event) {
		long id = getRequestParameterAsLong("personId");
		open(id);
	}
	
	private void open(Long id) {
		setPersonId(id);
		
		log.debug("opening person " + id);
		
		PersonController personController = Helper.getManagedBean(PersonController.class); 
		personController.refresh();
		
		StammbaumController stammbaumController = Helper.getManagedBean(StammbaumController.class);
		stammbaumController.refresh();
	}
	
	public PageEnum getPage() {
		Long id = getRequestParameterAsLong("id");
		if (id != null && !id.equals(Helper.getManagedBean(PersonController.class).getPerson().getId())) {
			open(id);
		}
		
		return page;
	}

	public void setPage(PageEnum page) {
		this.page = page;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}
}
