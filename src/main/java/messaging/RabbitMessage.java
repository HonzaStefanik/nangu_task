package messaging;

import controller.MessageWrapper;
import domain.entities.Author;
import domain.entities.Message;

import java.io.Serializable;
import java.util.List;

public class RabbitMessage implements Serializable {

  private List<Author> authorList;
  private Author author;
  private List<Message> messageList;
  private MessageWrapper messageWrapper;
  private MessageType messageType;
  private int id;
  private String text;

  public RabbitMessage(MessageType messageType) {
    this.messageType = messageType;
  }

  public RabbitMessage(List<Author> authorList, MessageType messageType) {
    this.authorList = authorList;
    this.messageType = messageType;
  }

  public RabbitMessage(Author author, MessageType messageType) {
    this.author = author;
    this.messageType = messageType;
  }

  public RabbitMessage(MessageType messageType, List<Message> messageList) {
    this.messageType = messageType;
    this.messageList = messageList;
  }

  public RabbitMessage(MessageType messageType, Author author) {
    this.messageType = messageType;
    this.author = author;
  }

  public RabbitMessage(MessageType messageType, int id) {
    this.messageType = messageType;
    this.id = id;
  }

  public RabbitMessage(MessageType messageType, int id, Author owner) {
    this.messageType = messageType;
    this.id = id;
    this.author = owner;
  }


  public List<Author> getAuthorList() {
    return authorList;
  }

  public void setAuthorList(List<Author> authorList) {
    this.authorList = authorList;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public List<Message> getMessageList() {
    return messageList;
  }

  public void setMessageList(List<Message> messageList) {
    this.messageList = messageList;
  }

  public MessageWrapper getMessageWrapper() {
    return messageWrapper;
  }

  public void setMessageWrapper(MessageWrapper messageWrapper) {
    this.messageWrapper = messageWrapper;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
