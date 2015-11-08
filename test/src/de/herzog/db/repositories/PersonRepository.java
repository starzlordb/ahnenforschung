package de.herzog.db.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.herzog.db.beans.PersonBean;
import de.herzog.util.HibernateUtil;
import de.herzog.util.Logger;

public class PersonRepository extends AbstractDomainRepository<PersonBean> {

	private static final long serialVersionUID = 5410145443289846671L;
	
	private Logger log = new Logger(PersonRepository.class);
	
	public PersonRepository() {
		super(PersonBean.class);
	}

	@SuppressWarnings("unchecked")
	public List<PersonBean> getByVaterId(long personId) {
		List<PersonBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE vaterId = " + personId + " AND deleted = FALSE ORDER BY id ASC").list();
        } catch (RuntimeException e) {
            log.warn(e);
        } finally {
            session.flush();
            session.close();
        }
        
        return result;
	}

	@SuppressWarnings("unchecked")
	public List<PersonBean> getByMutterId(long personId) {
		List<PersonBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE mutterId = " + personId + " AND deleted = FALSE ORDER BY id ASC").list();
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
	public List<PersonBean> getChildrenByPerson(Long personId) {
		List<PersonBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND (vaterId = " + personId + " OR mutterId = " + personId + ") ORDER BY id ASC").list();
        } catch (RuntimeException e) {
            log.warn(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            session.flush();
            session.close();
        }
        
        return result;
	}
}
