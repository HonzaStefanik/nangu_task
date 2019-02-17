package messaging;

import java.io.Serializable;

public enum MessageType implements Serializable {
  // Command messages
  CREATE_AUTHOR,
  DELETE_AUTHOR,
  GET_ALL_AUTHORS,
  GET_ALL_MESSAGES,
  CREATE_MESSAGE,
  GET_MESSAGES_BY_AUTHOR,
  DELETE_MESSAGE,
  UPDATE_MESSAGE,
  // Event messages
  ALL_AUTHORS,
  AUTHOR_CREATED,
  AUTHOR_DELETED,
  MESSAGE_CREATED,
  MESSAGE_DELETED,
  MESSAGE_UPDATED,
  MESSAGES_BY_AUTHOR,
  ALL_MESSAGES
}
