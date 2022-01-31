package it.webred.mui.hibernate;

import java.net.URL;

import it.webred.mui.AppProperties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	public static final SessionFactory sessionFactory;
	static {
		SessionFactory sessionFactoryTmp = null;
		Configuration conf = null;
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			conf = new Configuration().configure("/hibernate.cfg.xml");			
		} catch (Throwable ex) {
			try {
				// Create the SessionFactory from hibernate.cfg.xml
				URL url = Thread.currentThread().getContextClassLoader().getResource("hibernate.cfg.xml"); 
				conf = new Configuration().configure(url);
			} catch (Throwable ex1) {
				try {
					// Create the SessionFactory from hibernate.batch-cfg.xml
					conf = new Configuration().configure("/hibernate.batch-cfg.xml");
				} catch (Throwable ex2) {
					try {
						// Create the SessionFactory from hibernate.batch-cfg.xml
						URL url = Thread.currentThread().getContextClassLoader().getResource("hibernate.batch-cfg.xml"); 
						conf = new Configuration().configure(url);
					} catch (Throwable ex3) {
						throw new ExceptionInInitializerError(ex3);
					}
				}
			}
		}
		if (conf != null) {
			conf.setProperty("hibernate.connection.url", AppProperties.getProperties().get(AppProperties.CONN_STRING));
			conf.setProperty("hibernate.connection.username", AppProperties.getProperties().get(AppProperties.USER_NAME));
			conf.setProperty("hibernate.connection.password", AppProperties.getProperties().get(AppProperties.PASSWORD));
			sessionFactoryTmp = conf.buildSessionFactory();			
		}
		sessionFactory = sessionFactoryTmp;
	}

	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		// Open a new Session, if this thread has none yet
		if (s == null) {
			s = sessionFactory.withOptions().interceptor(new MuiInterceptor()).openSession();
			// Store it in the ThreadLocal variable
			session.set(s);
		}
		return s;
	}

	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		if (s != null)
			s.close();
		session.set(null);
	}
}
