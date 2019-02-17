package service;

import domain.entities.Author;
import domain.entities.Message;
import exception.AuthorExistsException;
import exception.AuthorNotFoundException;
import messaging.MessageType;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

  void persistAuthor(Author author)
          throws AuthorExistsException;

  void updateAuthor(Author author)
          throws AuthorNotFoundException;

  void deleteAuthorById(int id)
          throws AuthorNotFoundException;

  Optional<Author> getAuthorByName(String name)
          throws AuthorNotFoundException;

  Optional<Author> getAuthorById(int id)
          throws AuthorNotFoundException;

  List<Message> getMessagesByAuthor(String name)
          throws AuthorNotFoundException;

  List<Author> getAllAuthors();


  void sendAuthorMessage(List<Message> messages, MessageType messageType, String queueName);

  void sendAuthorMessage(MessageType messageType, Author author, String queueName);

  void sendAuthorMessage(MessageType messageType, String queueName);

  void sendAuthorMessage(MessageType messageType, List<Author> authors, String queueName);

  void sendAuthorMessage(MessageType messageType, int id, String queueName);

}
