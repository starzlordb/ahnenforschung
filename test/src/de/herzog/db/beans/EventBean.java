package de.herzog.db.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.envers.Audited;

import de.herzog.enums.EventTypeEnum;
import de.herzog.enums.person.EventDateModifierEnum;

@Entity
@Audited
public class EventBean extends AbstractDomainBean {

	private static final long serialVersionUID = 4658239589860342822L;

	private Integer tag;
	private Integer monat;
	private Integer jahr;
	
	private EventDateModifierEnum typ;
	private EventTypeEnum eventType;
	
	private String location;
	private String source;
	private String notice;
	
	private Long personId;
	private Long hochzeitId;
	
	private boolean familySearch = false;
	private boolean nachweis = false;
	
	public static EventBean newBean(EventTypeEnum eventType, long referenceId) {
		EventBean bean = new EventBean();
		
		bean.setEventType(eventType);
		
		switch (eventType) {
		case BAPTISM:
		case BIRTH:
		case BURIAL:
		case DEATH:
			bean.setPersonId(referenceId);
			break;
		case DIVORCE:
		case MARRIAGE:
			bean.setHochzeitId(referenceId);
			break;
		default:
			break;
		
		}
		
		return bean;		
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public Integer getMonat() {
		return monat;
	}

	public void setMonat(Integer monat) {
		this.monat = monat;
	}

	public Integer getJahr() {
		return jahr;
	}

	public void setJahr(Integer jahr) {
		this.jahr = jahr;
	}

	@Enumerated(EnumType.STRING)
	public EventDateModifierEnum getTyp() {
		return typ;
	}

	public void setTyp(EventDateModifierEnum typ) {
		this.typ = typ;
	}

	@Column(columnDefinition = "text")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Enumerated(EnumType.STRING)
	public EventTypeEnum getEventType() {
		return eventType;
	}

	public void setEventType(EventTypeEnum eventType) {
		this.eventType = eventType;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getHochzeitId() {
		return hochzeitId;
	}

	public void setHochzeitId(Long hochzeitId) {
		this.hochzeitId = hochzeitId;
	}

	@Column(columnDefinition = "text")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(columnDefinition = "text")
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public boolean isFamilySearch() {
		return familySearch;
	}

	public void setFamilySearch(boolean familySearch) {
		this.familySearch = familySearch;
	}

	public boolean isNachweis() {
		return nachweis;
	}

	public void setNachweis(boolean nachweis) {
		this.nachweis = nachweis;
	}
}
