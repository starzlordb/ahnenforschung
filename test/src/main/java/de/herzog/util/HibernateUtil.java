package de.herzog.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateUtil {  
	  
    private static final SessionFactory sessionFactory;  
  
    static {  
        try {  
            // Create the SessionFactory from hibernate.cfg.xml  
        	Configuration configuration = new Configuration();
        	  configuration.configure();
        	StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
        	 applySettings(configuration.getProperties()).build();
        	  sessionFactory = configuration.configure().      buildSessionFactory(serviceRegistry);
        	  //return sessionFactory;
        	
        	//sessionFactory = new Configuration().configure()..buildSessionFactory();  
        } catch (Throwable ex) {  
            // Make sure you log the exception, as it might be swallowed  
            System.err.println("Initial SessionFactory creation failed." + ex);  
            throw new ExceptionInInitializerError(ex);  
        }  
    }  
  
    public static SessionFactory getSessionFactory() {  
        return sessionFactory;  
    }  
  
}  