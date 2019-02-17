package domain.dao;

import domain.entities.Message;

import java.util.List;

public class MessageDaoService {

  private MessageDao dao;

  public void persist(Message entity) {
    dao.openCurrentSessionWithTransaction();
    dao.persist(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public void update(Message entity) {
    dao.openCurrentSessionWithTransaction();
    dao.update(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public Message findById(int id) {
    dao.openCurrentSession();
    Message messages = dao.findById(id);
    dao.closeCurrentSession();
    return messages;
  }


  public void deleteById(int id) {
    dao.openCurrentSessionWithTransaction();
    Message message = dao.findById(id);
    dao.delete(message);
    dao.closeCurrentSessionwithTransaction();
  }

  public List<Message> getAllMessages() {
    dao.openCurrentSession();
    List<Message> messages = dao.getAllMessages();
    dao.closeCurrentSession();
    return messages;
  }

  public void setDao(MessageDao dao) {
    this.dao = dao;
  }

  public MessageDao getDao() {
    return dao;
  }

}
