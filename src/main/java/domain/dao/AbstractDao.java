package domain.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import static db.HibernateUtil.getSessionFactory;

public abstract class AbstractDao {

  private Session currentSession;
  private Transaction currentTransaction;

  Session openCurrentSession() {
    currentSession = getSessionFactory().openSession();
    return currentSession;
  }

  Session openCurrentSessionWithTransaction() {
    currentSession = getSessionFactory().openSession();
    currentTransaction = currentSession.beginTransaction();
    return currentSession;
  }

  void closeCurrentSession() {
    currentSession.close();
  }

  void closeCurrentSessionwithTransaction() {
    currentTransaction.commit();
    currentSession.close();
  }

  Session getCurrentSession() {
    return currentSession;
  }

  void setCurrentSession(Session currentSession) {
    this.currentSession = currentSession;
  }

  Transaction getCurrentTransaction() {
    return currentTransaction;
  }

  void setCurrentTransaction(Transaction currentTransaction) {
    this.currentTransaction = currentTransaction;
  }
}
