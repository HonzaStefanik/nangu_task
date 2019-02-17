package service;

import controller.MessageWrapper;
import domain.dao.MessageDaoService;
import domain.entities.Author;
import domain.entities.Message;
import exception.AuthorNotFoundException;
import exception.MessageNotFoundException;
import javafx.util.Pair;
import main.Main;
import messaging.MessageType;
import messaging.RabbitMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

  private RabbitTemplate rabbitTemplate;
  private MessageDaoService messageDaoService;
  private AuthorService authorService;

  private final Logger log = LoggerFactory.getLogger(getClass());
  private static final String MESSAGE = "Sending <{}> message";

  @Autowired
  public MessageServiceImpl(RabbitTemplate rabbitTemplate, MessageDaoService messageDaoService, AuthorService authorService) {
    this.rabbitTemplate = rabbitTemplate;
    this.messageDaoService = messageDaoService;
    this.authorService = authorService;
  }


  public void sendMsgMessage(Message message, MessageType messageType) {
    Pair<MessageType, Message> pair = new Pair<>(messageType, message);
    log.info("Sending {} message", messageType);
    rabbitTemplate.convertAndSend(Main.NANGU_TASK_QUEUE_IN, pair);
  }

  @Override
  public void persistMessage(MessageWrapper messageWrapper) throws AuthorNotFoundException {
    Optional<Author> author = authorService.getAuthorById(messageWrapper.getAuthorId());
    author.get().getMessages().add((messageWrapper.getMessage()));
    authorService.updateAuthor(author.get());
  }

  @Override
  public Message getMessageById(int messageId) throws MessageNotFoundException {
    Message message = messageDaoService.findById(messageId);
    if (message != null) {
      return message;
    } else {
      log.error("Message with id <{}> does not exist", messageId);
      throw new MessageNotFoundException();
    }
  }

  @Override
  public void updateMessageById(int messageId, String newText) throws MessageNotFoundException {
    Message message = getMessageById(messageId);
    message.setText(newText);
    messageDaoService.update(message);
  }

  @Override
  public void deleteMessageById(int messageId) throws MessageNotFoundException {
    getMessageById(messageId);
    messageDaoService.deleteById(messageId);
  }

  @Override
  public List<Message> getAllMessages() {
    return messageDaoService.getAllMessages();
  }


  @Override
  public void sendMessage(MessageType messageType, String queueName) {
    RabbitMessage rabbitMessage = new RabbitMessage(messageType);
    log.info(MESSAGE, messageType);
    rabbitTemplate.convertAndSend(queueName, rabbitMessage);

  }

  @Override
  public void sendMessage(List<Message> messageList, MessageType messageType, String queueName) {
    RabbitMessage rabbitMessage = new RabbitMessage(messageType);
    log.info(MESSAGE, messageType);
    rabbitTemplate.convertAndSend(queueName, rabbitMessage);
  }

  @Override
  public void sendMessage(MessageType messageType, int id, Author owner, String queueName) {
    RabbitMessage rabbitMessage = new RabbitMessage(messageType, id, owner);
    log.info(MESSAGE, messageType);
    rabbitTemplate.convertAndSend(queueName, rabbitMessage);
  }


}
