package de.herzog.db.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.herzog.db.beans.EventBean;
import de.herzog.enums.EventTypeEnum;
import de.herzog.util.HibernateUtil;
import de.herzog.util.Logger;

public class EventRepository extends AbstractDomainRepository<EventBean> {

	private static final long serialVersionUID = -333228541318046277L;
	
	private Logger log = new Logger(EventRepository.class);
	
	public EventRepository() {
		super(EventBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public EventBean getEventByPersonIdAndType(long personId, EventTypeEnum eventType) {
		List<EventBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND eventType = '" + eventType + "' AND personId = " + personId).list();
        } catch (RuntimeException e) {
            log.warn(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            session.flush();
            session.close();
        }
        
        if (result != null && result.size() > 0) {
        	return result.get(0);
        } else {
        	return EventBean.newBean(eventType, personId);
        }
	}
	
	@SuppressWarnings("unchecked")
	public EventBean getMarriageById(long hochzeitId) {
		List<EventBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND eventType = '" + EventTypeEnum.MARRIAGE + "' AND hochzeitId = " + hochzeitId).list();
        } catch (RuntimeException e) {
            log.warn(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            session.flush();
            session.close();
        }
        
        if (result != null && result.size() > 0) {
        	return result.get(0);
        } else {
        	return EventBean.newBean(EventTypeEnum.MARRIAGE, hochzeitId);
        }
	}
	
	@SuppressWarnings("unchecked")
	public EventBean getDivorceById(long hochzeitId) {
		List<EventBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND eventType = '" + EventTypeEnum.DIVORCE + "' AND hochzeitId = " + hochzeitId).list();
        } catch (RuntimeException e) {
            log.warn(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            session.flush();
            session.close();
        }
        
        if (result != null && result.size() > 0) {
        	return result.get(0);
        } else {
        	return EventBean.newBean(EventTypeEnum.DIVORCE, hochzeitId);
        }
	}

}
