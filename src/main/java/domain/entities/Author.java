package domain.entities;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author implements Serializable{

  private int id;
  private String name;
  private String password;
  private List<Message> messages;

  public Author() { /* ORM constructor*/
    messages = new ArrayList<>();
  }

  public Author(String name, String password) {
    this.name = name;
    this.password = password;
    messages = new ArrayList<>();
  }

  @Id
  @GenericGenerator(name = "id", strategy = "increment")
  @GeneratedValue(generator = "id")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(nullable = false, unique = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @OneToMany(targetEntity = Message.class,
          orphanRemoval = true,
          cascade = CascadeType.REMOVE,
          fetch = FetchType.EAGER)
  @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  @Transient
  public boolean findMessage(int id){
    for(Message m : messages){
      if(m.getId() == id){
        return true;
      }
    }
    return false;
  }

  @Transient

  @Override
  public String toString() {
    return "Author{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}
