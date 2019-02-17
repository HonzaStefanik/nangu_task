package main;


import messaging.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import static db.HibernateUtil.getSessionFactory;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ImportResource("springcontext.xml")
public class Main {

  public static final String NANGU_TASK_QUEUE_IN = "nangu.task.queue-IN";
  public static final String NANGU_TASK_QUEUE_OUT = "nangu.task.queue-OUT";


  @Bean
  Queue queueIn() {
    return new Queue(NANGU_TASK_QUEUE_IN, false);
  }

  @Bean
  Queue queueOut() {
    return new Queue(NANGU_TASK_QUEUE_OUT, false);
  }

  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                           MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(NANGU_TASK_QUEUE_IN, NANGU_TASK_QUEUE_OUT);
    container.setMessageListener(listenerAdapter);
    return container;
  }

  @Bean
  MessageListenerAdapter listenerAdapter(MessageListener messageListener) {
    return new MessageListenerAdapter(messageListener, "receiveMessage");
  }

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
    getSessionFactory().openSession();

    /* Author a = new Author();
    a.setName(name);
    authorService.sendAuthorMessage(MessageType.GET_MESSAGES_BY_AUTHOR, a, Main.NANGU_TASK_QUEUE_IN);*/
  }
}


