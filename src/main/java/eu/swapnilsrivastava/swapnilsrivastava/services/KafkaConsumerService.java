package eu.swapnilsrivastava.swapnilsrivastava.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import eu.swapnilsrivastava.swapnilsrivastava.models.HelloWorldModel;
import eu.swapnilsrivastava.swapnilsrivastava.repositories.HelloWorldRepository;
import jakarta.transaction.Transactional;

@Service
public class KafkaConsumerService {

    @Autowired
    private HelloWorldRepository helloWorldRepository;

    // @KafkaListener(topics = "hello_world_topic", groupId = "${spring.kafka.consumer.group-id}")
    // public void consume(String message) {
    //     // Save the message to the database
    //     HelloWorldModel helloWorldEntity = new HelloWorldModel();
    //     helloWorldEntity.setName(message);
    //     System.out.println("HelloWorldModel" + helloWorldEntity);
    //     helloWorldRepository.save(helloWorldEntity);
    //     System.out.println("helloWorldRepository save called");
    // }

     private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(id = "myConsumer", topics = "hello_world_topic", groupId = "${spring.kafka.consumer.group-id}", autoStartup = "true")
    public void listen(String value,
        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
        @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        logger.info(String.format("\n\n Consumed event from topic %s: key = %-10s value = %s \n\n", topic, key, value));
        
         // Save the message to the database
         HelloWorldModel helloWorldEntity = new HelloWorldModel();
         helloWorldEntity.setName(value);
         System.out.println("HelloWorldModel" + helloWorldEntity);
         helloWorldRepository.save(helloWorldEntity);
         System.out.println("helloWorldRepository save called");
  }
    
}
