package controller;

import domain.entities.Message;

import java.io.Serializable;

public class MessageWrapper implements Serializable {

  private Message message;
  private int authorId;

  public MessageWrapper(Message message, int authorId) {
    this.message = message;
    this.authorId = authorId;
  }

  public MessageWrapper() {
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public int getAuthorId() {
    return authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }
}
