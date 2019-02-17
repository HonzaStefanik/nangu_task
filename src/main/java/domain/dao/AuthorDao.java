package domain.dao;

import domain.entities.Author;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDao extends AbstractDao {

  Author findById(int id) {
    return getCurrentSession().get(Author.class, id);
  }

  void persist(Author entity) {
    getCurrentSession().save(entity);
  }

  void update(Author entity) {
    getCurrentSession().update(entity);
  }

  void delete(Author entity) {
    getCurrentSession().delete(entity);
  }

  @SuppressWarnings("unchecked")
  Optional<Author> getByName(String name) {
    Query query = getCurrentSession().createQuery("from Author where name=:name");
    query.setParameter("name", name);
    return  (Optional<Author>) query.getResultList().stream().findFirst();
  }

  List<Author> getAllAuthors() {
    List<Author> authors = new ArrayList<>();
    try {
     authors = getCurrentSession().createQuery("from Author").list();
    } catch (Exception e) {/* no result exception can be ignored, return an empty list instead*/}
      return authors;
    }
}
