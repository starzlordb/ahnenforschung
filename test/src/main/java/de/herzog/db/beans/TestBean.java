package de.herzog.db.beans;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

@Entity
@Audited
public class TestBean extends AbstractDomainBean {
	private static final long serialVersionUID = -1633956733014354158L;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
