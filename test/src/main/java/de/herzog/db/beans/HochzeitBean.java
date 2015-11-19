package de.herzog.db.beans;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

@Entity
@Audited
public class HochzeitBean extends AbstractDomainBean {

	private static final long serialVersionUID = 4763441787769761002L;

	private Long mannId;
	private Long frauId;

	private boolean marriage;
	private boolean divorce;	

	public Long getMannId() {
		return mannId;
	}

	public void setMannId(Long mannId) {
		this.mannId = mannId;
	}

	public Long getFrauId() {
		return frauId;
	}

	public void setFrauId(Long frauId) {
		this.frauId = frauId;
	}

	public boolean isMarriage() {
		return marriage;
	}

	public void setMarriage(boolean marriage) {
		this.marriage = marriage;
	}

	public boolean isDivorce() {
		return divorce;
	}

	public void setDivorce(boolean divorce) {
		this.divorce = divorce;
	}

}
