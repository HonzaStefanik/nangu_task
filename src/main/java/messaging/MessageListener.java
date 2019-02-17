package messaging;

import controller.MessageWrapper;
import domain.entities.Author;
import domain.entities.Message;
import exception.AuthorExistsException;
import exception.AuthorNotFoundException;
import exception.MessageNotFoundException;
import main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.AuthorService;
import service.MessageService;

import java.util.List;

import static messaging.MessageType.*;


@Component
public class MessageListener {

  private AuthorService authorService;
  private MessageService messageService;

  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String EXCEPTION = "exception";


  public MessageListener() {
  }

  @Autowired
  public MessageListener(AuthorService authorService, MessageService messageService) {
    this.authorService = authorService;
    this.messageService = messageService;
  }

  public void receiveMessage(RabbitMessage rabbitMessage) {
    log.info("Received <{}> message", rabbitMessage.getMessageType());
    processMessage(rabbitMessage);
    log.info("Message processed");
  }


  private void processMessage(RabbitMessage rabbitMessage) {
    switch (rabbitMessage.getMessageType()) {
      case CREATE_AUTHOR:
        createAuthor(rabbitMessage.getAuthor());
        break;
      case DELETE_AUTHOR:
        deleteAuthor(rabbitMessage.getAuthor().getId());
        break;
      case GET_ALL_AUTHORS:
        getAllAuthors();
        break;
      case GET_MESSAGES_BY_AUTHOR:
        getAuthorsMessages(rabbitMessage.getAuthor().getName());
        break;
      case GET_ALL_MESSAGES:
        getAllMessages();
        break;
      case CREATE_MESSAGE:
        createMessage(rabbitMessage.getMessageWrapper());
        break;
      case UPDATE_MESSAGE:
        updateMessage(rabbitMessage.getId(), rabbitMessage.getText(), rabbitMessage.getAuthor());
        break;
      case DELETE_MESSAGE:
        deleteMessage(rabbitMessage.getId(), rabbitMessage.getAuthor());
        break;
    }
  }

  private void createAuthor(Author author) {
    try {
      authorService.persistAuthor(author);
      authorService.sendAuthorMessage(AUTHOR_CREATED, Main.NANGU_TASK_QUEUE_OUT);
    } catch (AuthorExistsException e) {
      log.error(EXCEPTION, e);
    }
  }

  private void deleteAuthor(int authorId) {
    try {
      authorService.deleteAuthorById(authorId);
      authorService.sendAuthorMessage(AUTHOR_DELETED, Main.NANGU_TASK_QUEUE_OUT);
    } catch (AuthorNotFoundException e) {
      log.error(EXCEPTION, e);
    }
  }

  private void getAllAuthors() {
    List<Author> authors = authorService.getAllAuthors();
    authorService.sendAuthorMessage(ALL_AUTHORS, authors, Main.NANGU_TASK_QUEUE_OUT);
  }


  private void getAuthorsMessages(String name) {
    try {
      Author author = authorService.getAuthorByName(name).get();
      authorService.sendAuthorMessage(author.getMessages(), MESSAGES_BY_AUTHOR, Main.NANGU_TASK_QUEUE_OUT);
    } catch (AuthorNotFoundException e) {
      log.error(EXCEPTION, e);
    }
  }

  private void getAllMessages() {
    List<Message> messageList = messageService.getAllMessages();
    messageService.sendMessage(messageList, ALL_MESSAGES, Main.NANGU_TASK_QUEUE_OUT);
  }

  private void createMessage(MessageWrapper messageWrapper) {
    try {
      messageService.persistMessage(messageWrapper);
      messageService.sendMessage(MESSAGE_CREATED, Main.NANGU_TASK_QUEUE_OUT);
    } catch (AuthorNotFoundException e) {
      log.error(EXCEPTION, e);
    }
  }

  private void updateMessage(int messageId, String newText, Author author) {
    try {
      Message message = messageService.getMessageById(messageId);
      if (author.getMessages().contains(message)) {
        messageService.updateMessageById(messageId, newText);
        messageService.sendMessage(MESSAGE_UPDATED, Main.NANGU_TASK_QUEUE_OUT);
      }

    } catch (MessageNotFoundException e) {
      log.error(EXCEPTION, e);
    }
  }

  private void deleteMessage(int messageId, Author author) {
    try {
      Message message = messageService.getMessageById(messageId);
      if (author.getMessages().contains(message)) {
        messageService.deleteMessageById(messageId);
        messageService.sendMessage(MESSAGE_DELETED, Main.NANGU_TASK_QUEUE_OUT);
      }
    } catch (MessageNotFoundException e) {
      log.error(EXCEPTION, e);
    }
  }

}