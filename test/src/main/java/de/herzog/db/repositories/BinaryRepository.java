package de.herzog.db.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.herzog.db.beans.BinaryBean;
import de.herzog.enums.EventTypeEnum;
import de.herzog.util.HibernateUtil;
import de.herzog.util.Logger;

public class BinaryRepository extends AbstractDomainRepository<BinaryBean> {

	private static final long serialVersionUID = -333528541318046277L;
	
	private Logger log = new Logger(BinaryRepository.class);
	
	public BinaryRepository() {
		super(BinaryBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<BinaryBean> getBinaryByPersonIdAndType(long personId, EventTypeEnum type) {
		List<BinaryBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND eventType = '" + type + "' AND personId = " + personId).list();
        } catch (RuntimeException e) {
            log.error(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            session.flush();
            session.close();
        }
        
        return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<BinaryBean> getBinaryByHochzeitIdAndType(long hochzeitId, EventTypeEnum type) {
		List<BinaryBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND eventType = '" + type + "' AND hochzeitId = " + hochzeitId).list();
        } catch (RuntimeException e) {
            log.error(e);
        } finally {
        	try { tx.rollback(); } catch (Exception e) {}
            session.flush();
            session.close();
        }
        
        return result;
	}

}
