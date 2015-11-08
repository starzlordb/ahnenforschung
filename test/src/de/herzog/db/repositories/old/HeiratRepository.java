package de.herzog.db.repositories.old;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.herzog.db.beans.old.Heirat;
import de.herzog.util.HibernateUtil;
import de.herzog.util.Logger;

public class HeiratRepository implements Serializable {

	private Logger log = new Logger(HeiratRepository.class);
	
	private static final long serialVersionUID = 6209753920634387074L;

	@SuppressWarnings("unchecked")
	public List<Heirat> getAll() {
		List<Heirat> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM heirat_old ORDER BY id ASC").list();
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
