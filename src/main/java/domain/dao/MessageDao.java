package domain.dao;

import domain.entities.Message;

import java.util.List;

public class MessageDao extends AbstractDao {

  Message findById(int id) {
    return getCurrentSession().get(Message.class, id);
  }

  void persist(Message entity) {
    getCurrentSession().save(entity);
  }

  void update(Message entity) {
    getCurrentSession().update(entity);
  }

  void delete(Message entity) {
    getCurrentSession().delete(entity);
  }

  List<Message> getAllMessages() {
    return (List<Message>) getCurrentSession().createQuery("from Message").list();
  }

}