package de.herzog.db.repositories;

import de.herzog.db.beans.TestBean;

public class TestRepository extends AbstractDomainRepository<TestBean> {

	private static final long serialVersionUID = -7100680093894279463L;

	public TestRepository() {
		super(TestBean.class);
	}
	
	public TestRepository(Class<TestBean> className) {
		super(TestBean.class);
	}

}
