package de.herzog.db.beans;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;

import de.herzog.controller.person.PersonController;
import de.herzog.util.Logger;
import de.herzog.views.AbstractView;

/**
 * Oberklasse für alle zukünftigen, neuen PNK-Beans, die vernünftigt modelliert
 * wurden,
 * und nicht eine direkte "Übersetzung" von vorhandenen,
 * unstrukturierten Datenbanktabellen sind.
 * 
 * Neue Objekte sollen automatisch eine UUID erhalten- diese soll schon vor
 * dem Speichern über getUuid() zurückgegeben und verwendet werden können.
 * Die Erzeugung von UUIDs ist leider relativ zeitaufwändig und die
 * meisten Objekte werden aus der Datenbank geladen. Daher sollten für diese
 * Objekte
 * keine UUIDs erzeugt werden.
 * 
 * @author willkomm
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
@Audited
public abstract class AbstractDomainBean implements Serializable, Cloneable {
	
	@SuppressWarnings("unused")
	private Logger log = new Logger(AbstractDomainBean.class);
	
    public static final String ID = "id";
    public static final String UUID = "uuid";
    public static final String CREATED_DATE = "created_date";
    public static final String CHANGED_DATE = "changed_date";
    public static final String CREATED_UID = "created_uid";
    public static final String CHANGED_UID = "changed_uid";
    public static final String DELETED = "deleted";

    private Long id;

    private String uuid;

    private Date createdDate;

    private Date changedDate;

    private Boolean deleted;

    private Long createdUid;

    private Long changedUid;

    public AbstractDomainBean() {

        createdDate = new Date();
        deleted = Boolean.FALSE;
    }

	public static <X extends AbstractView, Y extends AbstractDomainBean> X fromBean(Class<X> resultingClass, Y personBean, PersonController personController) {
		if (personBean == null) {
			return null;
		}
		
		X view = personBean.convertToClass(resultingClass);
		view.setController(personController);
		
		return view;
	}

	public static <X extends AbstractDomainBean, Y extends AbstractDomainBean> X toBean(Class<X> resultingClass, Y personBean) {
		if (personBean == null) {
			return null;
		}
		
		X view = personBean.convertToClass(resultingClass);
		
		return view;
	}

    @Override
    public AbstractDomainBean clone() {

        try {
            return (AbstractDomainBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @Basic(optional = false)
    @Column(nullable = false, unique = true, length = 36)
    public String getUuid() {

        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString();
        }
        return uuid;
    }

    public void setUuid(String uuid) {

        this.uuid = uuid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = CREATED_DATE)
    public Date getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {

        this.createdDate = createdDate;
    }

    @Audited
    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = CHANGED_DATE)
    public Date getChangedDate() {

        return changedDate;
    }

    public void setChangedDate(Date changedDate) {

        this.changedDate = changedDate;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (Hibernate.getClass(this) != (Hibernate.getClass(o))) {
            return false;
        }

        // Dieser Abschnitt geht, weil wir global unique IDs haben
        AbstractDomainBean adb = (AbstractDomainBean) o;
        return getUuid().equals(adb.getUuid());
    }

    @Override
    public int hashCode() {

        return getUuid().hashCode();
    }

    @Column(nullable = false)
    public Boolean getDeleted() {

        return deleted;
    }

    public void setDeleted(Boolean deleted) {

        this.deleted = deleted;
    }

    @Column(name = CREATED_UID)
    public Long getCreatedUid() {

        return createdUid;
    }

    public void setCreatedUid(Long createdUid) {

        this.createdUid = createdUid;
    }

    @Column(name = CHANGED_UID)
    public Long getChangedUid() {

        return changedUid;
    }

    public void setChangedUid(Long changedUid) {

        this.changedUid = changedUid;
    }

    /**
     * Setzt die Ids zurück- wird im Falle von .clone benötigt, damit die Bean neu gespeichert werden kann, ohne die 
     * Ursprungsbean zu überschreiben. NUR AUFRUFEN, WENN WIRKLICH GEWOLLT.
     * 
     */
	public void resetIdentifiersAfterClone(Long uId) {
		setUuid(null);
		setId(null);
		setCreatedDate(new Date());
		setChangedUid(uId);
	}
	
    public HashMap<String, Object> toHashMap() {

        HashMap<String, Object> result = new HashMap<String, Object>();

        try {
            Field[] fields = this.getClass().getDeclaredFields();

            for (Field field : fields) {
                result.put(field.getName(), field.get(this));
            }
        } catch (Exception e) {
        }

        return result;
    }
    
    public <T> T convertToClass(Class<T> clazz) {
    	try {
			T result = clazz.newInstance();
			
			Class<? extends Object> obj = this.getClass();
			
			while (obj != null) {
				for (Field field : obj.getDeclaredFields()) {
					//log.info("copy " + field.getName() + " from " + obj.getName());
					try {
						String getter = (field.getType().equals(boolean.class) ? "is" : "get") + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
						String setter = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
						
						Method method = result.getClass().getMethod(setter, field.getType());
						method.invoke(result, this.getClass().getMethod(getter).invoke(this));
					} catch (Exception e) {
						//log.info("cannot set " + field.getName() + " in object of type " + clazz.getName() + ": " + e);
					}
				}
				
				obj = obj.getSuperclass();
			}
			
			return result;
		} catch (Exception e) {
			//log.info("cannot convert to an object of type " + clazz.getName() + ": " + e);
		}
    	
    	return null;
    }
}
