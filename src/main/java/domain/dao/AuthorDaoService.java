package domain.dao;

import domain.entities.Author;

import java.util.List;
import java.util.Optional;

public class AuthorDaoService {

  private AuthorDao dao;

  public void persist(Author entity) {
    dao.openCurrentSessionWithTransaction();
    dao.persist(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public void update(Author entity) {
    dao.openCurrentSessionWithTransaction();
    dao.update(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public Author findById(int id) {
    dao.openCurrentSession();
    Author authors = dao.findById(id);
    dao.closeCurrentSession();
    return authors;
  }

  public void deleteById(int id) {
    dao.openCurrentSessionWithTransaction();
    Author authors = dao.findById(id);
    dao.delete(authors);
    dao.closeCurrentSessionwithTransaction();
  }

  public List<Author> getAllAuthors() {
    dao.openCurrentSession();
    List<Author> authors = dao.getAllAuthors();
    dao.closeCurrentSession();
    return authors;
  }

  public Optional<Author> findByName(String name){
    dao.openCurrentSession();
    Optional<Author> author = dao.getByName(name);
    dao.closeCurrentSession();
    return author;
  }

  public void setDao(AuthorDao dao) {
    this.dao = dao;
  }

  public AuthorDao getDao() {
    return dao;
  }

}
