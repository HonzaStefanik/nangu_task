package domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Message implements Serializable {

  private int id;
  private String text;

  public Message() { /*ORM constructor*/  }

  public Message(String text) {
    this.text = text;
  }

  @Id
  @GenericGenerator(name="id" , strategy="increment")
  @GeneratedValue(generator="id")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(nullable = false)
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


}
