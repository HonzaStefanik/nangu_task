package controller;

import domain.entities.Author;
import exception.AuthorExistsException;
import exception.AuthorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AuthorService;
import service.AuthorServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "author")
public class AuthorController {

  private AuthorService authorService;
  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String EXCEPTION = "exception";
  private static final String AUTHOR_CREATED = "Author created successfully";
  private static final String AUTHOR_EXISTS = "Author already exists";
  private static final String AUTHOR_NOT_FOUND = "Author not found";

  @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
  ResponseEntity createAuthor(@RequestBody Author author) {
    try {
      authorService.persistAuthor(author);
      return ResponseEntity.ok().body(AUTHOR_CREATED);
    } catch (AuthorExistsException e) {
      log.error(EXCEPTION, e);
      return ResponseEntity.badRequest().body(AUTHOR_EXISTS);
    }
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
  ResponseEntity deleteById(@PathVariable("id") int id) {
    try {
      authorService.deleteAuthorById(id);
      return ResponseEntity.ok().body("Author deleted successfully");
    } catch (AuthorNotFoundException e) {
      log.error(EXCEPTION, e);
      return ResponseEntity.badRequest().body(AUTHOR_NOT_FOUND);
    }
  }

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  ResponseEntity getAllAuthors() {
    List<Author> authors = authorService.getAllAuthors();
    return ResponseEntity.ok().body(authors);
  }

  @RequestMapping(value = "/messages/{name}", method = RequestMethod.GET)
  ResponseEntity getAuthorsMessages(@PathVariable("name") String name) {
    try {
      Author author = authorService.getAuthorByName(name).get();
      return ResponseEntity.ok().body(author.getMessages());
    } catch (AuthorNotFoundException e) {
      log.error(EXCEPTION, e);
      return ResponseEntity.badRequest().body(AUTHOR_NOT_FOUND);
    }
  }


  public void setAuthorService(AuthorServiceImpl authorService) {
    this.authorService = authorService;
  }
}
