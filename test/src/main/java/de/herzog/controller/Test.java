package de.herzog.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.herzog.db.beans.TestBean;
import de.herzog.db.repositories.TestRepository;
import de.herzog.util.Logger;

@ManagedBean(name = "Test")
@RequestScoped
public class Test extends AbstractController {
    private static final long serialVersionUID = 4629817047379532658L;
    
    private Logger log = new Logger(Test.class);
    
    private TestRepository testRepository;
    
    @PostConstruct
    protected void postConstruct() {
    	super.postConstruct();
    	
    	testRepository = new TestRepository();
    	
    	// Super hotifx
    }
	
	private String test = "blabla";
	
	public void method() {
		TestBean test = new TestBean();
		test.setValue(getTest());
		
		System.out.println(test.getValue() + " (" + test.getId() + ")");
		
		testRepository.save(test);
		
		System.out.println(test.getValue() + " (" + test.getId() + ")");
		
		try {
			for (TestBean bean : testRepository.getAll()) {
				System.out.println("  " + bean.getId() + ": " + bean.getValue());
			}
		} catch (Exception e) {
			log.warn(e);
		}
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}
