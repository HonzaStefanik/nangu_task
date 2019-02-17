package db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

  private static SessionFactory sessionFactory;

  private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);


  private HibernateUtil() {
  } // util class

  static {
    try {
      sessionFactory = new Configuration().configure().buildSessionFactory();
    } catch (Exception e) {
     log.error("Error building Sessionfactory " + e);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static void shutDown() {
    getSessionFactory().close();
  }

}
