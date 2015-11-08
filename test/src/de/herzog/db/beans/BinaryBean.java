package de.herzog.db.beans;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.envers.Audited;

import de.herzog.enums.BinaryDataTypeEnum;
import de.herzog.enums.EventTypeEnum;

@Entity
@Audited
public class BinaryBean extends AbstractDomainBean {

	private static final long serialVersionUID = 4658239989860342822L;

	private byte[] data;
	private String filename;
	
	private BinaryDataTypeEnum type = BinaryDataTypeEnum.JPG;
	private EventTypeEnum eventType = null;
	
	private Long personId;
	private Long hochzeitId;
	
	public static BinaryBean newBean(EventTypeEnum eventType, long referenceId) {
		BinaryBean bean = new BinaryBean();
		bean.setEventType(eventType);
		
		if (eventType != null) {
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
				bean.setPersonId(referenceId);
				break;
			
			}
		} else {
			bean.setPersonId(referenceId);
		}
		
		return bean;		
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Enumerated(EnumType.STRING)
	public BinaryDataTypeEnum getType() {
		return type;
	}

	public void setType(BinaryDataTypeEnum type) {
		this.type = type;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Enumerated(EnumType.STRING)
	public EventTypeEnum getEventType() {
		return eventType;
	}

	public void setEventType(EventTypeEnum eventType) {
		this.eventType = eventType;
	}
}
