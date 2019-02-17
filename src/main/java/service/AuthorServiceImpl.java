package service;

import domain.dao.AuthorDaoService;
import domain.entities.Author;
import domain.entities.Message;
import exception.AuthorExistsException;
import exception.AuthorNotFoundException;
import messaging.MessageType;
import messaging.RabbitMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.PasswordUtil;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

  private RabbitTemplate rabbitTemplate;
  private AuthorDaoService authorDaoService;

  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String MESSAGE = "Sending <{}> message";

  @Autowired
  public AuthorServiceImpl(RabbitTemplate rabbitTemplate, AuthorDaoService authorDaoService) {
    this.rabbitTemplate = rabbitTemplate;
    this.authorDaoService = authorDaoService;
  }

  @Override
  public void persistAuthor(Author author) throws AuthorExistsException {
    if (!authorExists(author.getName())) {
      author.setPassword(PasswordUtil.hashPassword(author.getPassword()));
      authorDaoService.persist(author);
      log.info("Author created successfully.");
    } else {
      log.error("Author with name <{}> already exists", author.getName());
      throw new AuthorExistsException();
    }
  }

  @Override
  public void updateAuthor(Author author) throws AuthorNotFoundException {
    authorDaoService.update(author);
    log.info("Updated successfully");
  }

  @Override
  public void deleteAuthorById(int id) throws AuthorNotFoundException {
    Author author = authorDaoService.findById(id);
    if (author != null) {
      authorDaoService.deleteById(id);
      log.info("Author deleted successfully");
    } else {
      log.error("Author with id <{}> does not exist", id);
      throw new AuthorNotFoundException();
    }
  }

  @Override
  public List<Author> getAllAuthors() {
    return authorDaoService.getAllAuthors();
  }

  @Override
  public Optional<Author> getAuthorByName(String name) throws AuthorNotFoundException {
    Optional<Author> author = authorDaoService.findByName(name);
    if (author.isPresent()) {
      return author;
    } else {
      log.error("Author with name <{}> does not exist", name);
      throw new AuthorNotFoundException();
    }

  }

  @Override
  public Optional<Author> getAuthorById(int id) throws AuthorNotFoundException {
    Author author = authorDaoService.findById(id);
    if (author != null) {
      return Optional.of(author);
    } else {
      log.error("Author with id <{}> does not exist", id);
      throw new AuthorNotFoundException();
    }
  }

  @Override
  public List<Message> getMessagesByAuthor(String name) throws AuthorNotFoundException {
    Optional<Author> author = getAuthorByName(name);
    if (author.isPresent()) {
      log.info("Returned messages of author <{}>", name);
      return author.get().getMessages();
    } else {
      log.error("Author with name <{}> does not exist", name);
      throw new AuthorNotFoundException();
    }
  }

  @Override
  public void sendAuthorMessage(MessageType messageType, String queueName) {
    RabbitMessage rabbitMessage = new RabbitMessage(messageType);
    log.info(MESSAGE, messageType);
    rabbitTemplate.convertAndSend(queueName, rabbitMessage);
  }


  @Override
  public void sendAuthorMessage(MessageType messageType, Author author, String queueName) {
    RabbitMessage rabbitMessage = new RabbitMessage(messageType, author);
    log.info(MESSAGE, messageType);
    rabbitTemplate.convertAndSend(queueName, rabbitMessage);
  }

  @Override
  public void sendAuthorMessage(MessageType messageType, List<Author> authors, String queueName) {
    RabbitMessage rabbitMessage = new RabbitMessage(authors, messageType);
    log.info(MESSAGE, messageType);
    rabbitTemplate.convertAndSend(queueName, rabbitMessage);
  }

  @Override
  public void sendAuthorMessage(List<Message> messages, MessageType messageType, String queueName) {
    RabbitMessage rabbitMessage = new RabbitMessage(messageType, messages);
    log.info(MESSAGE, messageType);
    rabbitTemplate.convertAndSend(queueName, rabbitMessage);
  }

  @Override
  public void sendAuthorMessage( MessageType messageType,int id, String queueName) {
    RabbitMessage rabbitMessage = new RabbitMessage(messageType, id);
    log.info(MESSAGE, messageType);
    rabbitTemplate.convertAndSend(queueName, rabbitMessage);
  }

  private boolean authorExists(String name) {
    return authorDaoService.findByName(name).isPresent();
  }

  private boolean authorExists(int id) {
    return authorDaoService.findById(id) != null;
  }

}
