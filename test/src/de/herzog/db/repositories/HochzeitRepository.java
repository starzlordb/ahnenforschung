package de.herzog.db.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.herzog.db.beans.HochzeitBean;
import de.herzog.util.HibernateUtil;
import de.herzog.util.Logger;

public class HochzeitRepository extends AbstractDomainRepository<HochzeitBean> {

	private static final long serialVersionUID = -9177303865189556509L;
	
	private Logger log = new Logger(HochzeitRepository.class);
	
	public HochzeitRepository() {
		super(HochzeitBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<HochzeitBean> getByPersonId(long personId) {
		List<HochzeitBean> result = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM " + className.getName() + " WHERE deleted = FALSE AND (mannId = " + personId + " OR frauId = " + personId + ")").list();
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
