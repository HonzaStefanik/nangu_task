package controller;

import domain.entities.Author;
import domain.entities.Message;
import exception.AuthorNotFoundException;
import exception.MessageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AuthorService;
import service.MessageService;
import util.Base64Util;

import java.util.List;

@RestController
@RequestMapping(value = "message")
public class MessageController {

  private MessageService messageService;
  private AuthorService authorService;
  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String EXCEPTION = "exception";
  private static final String MESSAGE_CREATED = "Message created successfully";
  private static final String WRONG_CREDENTIALS = "Wrong credentials";
  private static final String MESSAGE_NOT_FOUND = "Message not found";
  private static final String MESSAGE_UPDATED = "Message updated successfully";
  private static final String MESSAGE_DELETED = "Message deleted successfully";


  @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
  ResponseEntity createMessage(@RequestBody MessageWrapper messageWrapper) {
    try {
      Author author = authorService.getAuthorById(messageWrapper.getAuthorId()).get();
      author.getMessages().add(messageWrapper.getMessage());
      authorService.updateAuthor(author);
      return ResponseEntity.ok().body(MESSAGE_CREATED);
    } catch (AuthorNotFoundException e) {
      log.error(EXCEPTION, e);
      return ResponseEntity.badRequest().body(WRONG_CREDENTIALS);
    }
  }

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  ResponseEntity getAllMessages() {
    List<Message> list = messageService.getAllMessages();
    return ResponseEntity.ok().body(list);
  }

  @RequestMapping(value = "/delete/{messageId}", method = RequestMethod.DELETE)
  ResponseEntity deleteMessageById(@PathVariable int messageId, @RequestHeader("authentication") String auth) {
    String headerUsername = Base64Util.decodeCredentials(auth).getName();
    String headerPw = Base64Util.decodeCredentials(auth).getPassword();
    try {
      Author authenticated = authorService.getAuthorByName(headerUsername).get();
      if (authenticated.getPassword().equals(headerPw) && authenticated.findMessage(messageId)) {
        messageService.deleteMessageById(messageId);
        return ResponseEntity.ok().body(MESSAGE_DELETED);
      } else {
        return ResponseEntity.badRequest().body("Only the owner can delete the message");
      }
    } catch (MessageNotFoundException e) {
      log.error(EXCEPTION, e);
      return ResponseEntity.badRequest().body(MESSAGE_NOT_FOUND);
    } catch (AuthorNotFoundException e) {
      log.error(EXCEPTION, e);
      return ResponseEntity.badRequest().body(WRONG_CREDENTIALS);
    }
  }

  @RequestMapping(value = "/update/{messageId}", method = RequestMethod.PUT)
  ResponseEntity updateMessageById(@PathVariable("messageId") int messageId, @RequestBody NewTextWrapper newText, @RequestHeader("authentication") String auth) {
    String headerUsername = Base64Util.decodeCredentials(auth).getName();
    String headerPw = Base64Util.decodeCredentials(auth).getPassword();
    try {
      Author authenticated = authorService.getAuthorByName(headerUsername).get();
      if (authenticated.getPassword().equals(headerPw) && authenticated.findMessage(messageId)) {
        messageService.updateMessageById(messageId, newText.getText());
        return ResponseEntity.badRequest().body(MESSAGE_UPDATED);
      } else {
        return ResponseEntity.badRequest().body("Only the owner can update the message");
      }
    } catch (AuthorNotFoundException e) {
      log.error(EXCEPTION, e);
      return ResponseEntity.badRequest().body(MESSAGE_NOT_FOUND);
    } catch (MessageNotFoundException e) {
      return ResponseEntity.badRequest().body(WRONG_CREDENTIALS);
    }
  }

  public void setMessageService(MessageService messageService) {
    this.messageService = messageService;
  }

  public void setAuthorService(AuthorService authorService) {
    this.authorService = authorService;
  }
}

