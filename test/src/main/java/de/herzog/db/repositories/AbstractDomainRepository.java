package de.herzog.db.repositories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.herzog.db.beans.AbstractDomainBean;
import de.herzog.util.HibernateUtil;
import de.herzog.util.Logger;

public class AbstractDomainRepository<ADB extends AbstractDomainBean> implements Serializable {
	
	private static final long serialVersionUID = -4213378412870212567L;

	private Logger log = new Logger(AbstractDomainRepository.class);
	
	protected Class<ADB> className;
	
	public AbstractDomainRepository(Class<ADB> className) {
		this.className = className;
	}
	
	@SuppressWarnings("unchecked")
	public List<ADB> getAll() {
		List<ADB> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE ORDER BY id ASC").list();
        } catch (RuntimeException e) {
            log.warn(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            session.flush();
            session.close();
        }
        
        return result;
	}
	
	@SuppressWarnings("unchecked")
	public ADB getById(Long id) {
		if (id == null) {
			return null;
		}
		
		ADB result = null;		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();//.openSession();
		Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<ADB> list = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND id = " + id).list();
            if (list != null && list.size() > 0) {
            	result = (ADB) list.get(0);
            }
        } catch (Exception e) {
            log.warn(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            //session.flush();
            //session.close();
        }
        
        return result;
	}
	
	@SuppressWarnings("unchecked")
	public ADB getByUuid(String uuid) {
		if (uuid == null) {
			return null;
		}
		
		ADB result = null;		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = (ADB) session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND uuid = '" + uuid + "'").list().get(0);
        } catch (RuntimeException e) {
            log.warn(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            session.flush();
            session.close();
        }
        
        return result;
	}
	
	public boolean delete(ADB element) {
		if (element == null) {
			return false;
		}
		
		element.setDeleted(true);
		element = save(element);
		
		if (element == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public ADB save(ADB element) {
		ADB result = null;
		Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.saveOrUpdate(element);
            session.getTransaction().commit();
            result = element;
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        
        return result;
	}

}
