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

    @KafkaListener(topics = {"hello_world_topic"}, groupId = "gcp-group-id")
    public void consume(String message) {
        System.out.println("Receiving message from topic" + message);
        HelloWorldModel helloWorld = new HelloWorldModel();
        helloWorld.setName(message);
        System.out.println("HelloWorldModel" + helloWorld.toString());

        helloWorldRepository.save(helloWorld);
        System.out.println("helloWorldRepository save called");
    }
    
}
