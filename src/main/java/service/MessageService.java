package service;

import controller.MessageWrapper;
import domain.entities.Author;
import domain.entities.Message;
import exception.AuthorNotFoundException;
import exception.MessageNotFoundException;
import messaging.MessageType;

import java.util.List;

public interface MessageService {

  void persistMessage(MessageWrapper messageWrapper)
          throws AuthorNotFoundException;

  Message getMessageById(int id)
          throws MessageNotFoundException;

  void updateMessageById(int messageId, String newText)
          throws MessageNotFoundException;

  void deleteMessageById(int messageId)
          throws MessageNotFoundException;

  List<Message> getAllMessages();

  void sendMessage(MessageType messageType, String queueName);

  void sendMessage(List<Message> messageList, MessageType messageType, String queueName);

  void sendMessage(MessageType messageType, int id, Author owner, String queueName);

}
