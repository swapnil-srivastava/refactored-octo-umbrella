package eu.swapnilsrivastava.swapnilsrivastava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import eu.swapnilsrivastava.swapnilsrivastava.models.HelloWorldModel;
import eu.swapnilsrivastava.swapnilsrivastava.repositories.HelloWorldRepository;
import jakarta.transaction.Transactional;

@Service
public class KafkaConsumerService {

    @Autowired
    private HelloWorldRepository helloWorldRepository;

    @KafkaListener(topics = "hello_world_topic", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        System.out.println("Received message: " + message);
        
        // Save the message to the database
        HelloWorldModel helloWorldEntity = new HelloWorldModel();
        helloWorldEntity.setName(message);
        System.out.println("HelloWorldModel" + helloWorldEntity);
        helloWorldRepository.save(helloWorldEntity);
        System.out.println("helloWorldRepository save called");
    }
    
}
