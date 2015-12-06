package de.herzog.controller.person;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import de.herzog.controller.AbstractController;
import de.herzog.util.Helper;
import de.herzog.util.Logger;
import de.herzog.views.person.PersonView;

@ManagedBean(name = StammbaumController.MANAGED_BEAN_NAME)
@SessionScoped
public class StammbaumController extends AbstractController {
	
	public static final String MANAGED_BEAN_NAME = "Stammbaum";

	private static final long serialVersionUID = -4652693923435721495L;

	private Logger log = new Logger(StammbaumController.class);
	
	private List<List<PersonView>> stammbaum;
	
	private int generation = 1;
	
	@Override
	protected void init() {
		super.init();
		
		initStammbaum();
	}
	
	public void initStammbaum() {
		try {
			PersonController personController = Helper.getManagedBean(PersonController.class);
			PersonView source = personController.initPerson(personController.getPerson().getId());
		
			List<List<PersonView>> stammbaum = getStammbaum(source, getGeneration());
		
			setStammbaum(stammbaum);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public void openNewGeneration(ActionEvent event) {
		setGeneration(getGeneration() + 1);
		
		List<List<PersonView>> stammbaum = getStammbaum();
		
		List<PersonView> newGeneration = new ArrayList<PersonView>();
		for (PersonView person : stammbaum.get(stammbaum.size() - 1)) {
			if (person == null) {
				newGeneration.add(null);
				newGeneration.add(null);
			} else {
				newGeneration.add(person.getVater());
				newGeneration.add(person.getMutter());
			}
		}
		stammbaum.add(newGeneration);
		setStammbaum(stammbaum);
	}
	
	public void closeGeneration(ActionEvent event) {
		if (getGeneration() > 0) {
			setGeneration(getGeneration() - 1);
			
			List<List<PersonView>> stammbaum = getStammbaum();
			stammbaum.remove(stammbaum.size() - 1);
			setStammbaum(stammbaum);
		}
	}
	
	private List<List<PersonView>> getStammbaum(PersonView source, int generations) {
		
		List<List<PersonView>> result = new ArrayList<List<PersonView>>();
		if (generations > 0) {
			List<PersonView> generation = new ArrayList<PersonView>();
			generation.add(source);
			
			result.add(generation);
			
			List<List<PersonView>> parentGeneration = new ArrayList<List<PersonView>>();
			List<List<PersonView>> fatherGeneration = null;
			if (source != null && source.getVater() != null) {
				fatherGeneration = getStammbaum(source.getVater(), generations - 1);
			} else {
				fatherGeneration = getStammbaum(null, generations - 1);
			}
			if (fatherGeneration != null) {
				parentGeneration.addAll(fatherGeneration);
			}
		
			List<List<PersonView>> motherGeneration = null;
			if (source != null && source.getMutter() != null) {
				motherGeneration = getStammbaum(source.getMutter(), generations - 1);
			} else {
				motherGeneration = getStammbaum(null, generations - 1);
			}
			if (motherGeneration != null) {
				for (int i = 0; i < Math.min(parentGeneration.size(), motherGeneration.size()); i++) {
					parentGeneration.get(i).addAll(motherGeneration.get(i));
				}
				
				if (parentGeneration.size() < motherGeneration.size()) {
					for (int i = parentGeneration.size(); i < motherGeneration.size(); i++) {
						parentGeneration.add(motherGeneration.get(i));
					}
				}
			}
			
			result.addAll(parentGeneration);
		}
		
		return result;
	}

	public List<List<PersonView>> getStammbaum() {
		return stammbaum;
	}

	public void setStammbaum(List<List<PersonView>> stammbaum) {
		this.stammbaum = stammbaum;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}
}
