package eu.swapnilsrivastava.swapnilsrivastava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloWorldController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello-gcp")
    public String HelloWorldSprings() {
       return "hello world google cloud platform :: Working";
    }

    @GetMapping("/hello-world")
    public String HelloWorld() {
        return "Hello world --> Working";
    }

    @GetMapping("/hello")
    public String Hello() {
        return "Hello Hello :: Working";
    }

    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMessage() {
        return "Hello from the microservice! :: Working";
    }

    @GetMapping("/call-heroku-service")
    public ResponseEntity<String> callHerokuService() {
        String herokuService2Url = "https://spring-hackfestival2024-df62fb596841.herokuapp.com/call-heroku-service";
        
        ResponseEntity<String> response = restTemplate.getForEntity(herokuService2Url, String.class);
        String message = response.getBody();
        
        return ResponseEntity.ok("Message from Heroku Service: " + message);
    }

    @GetMapping("/call-guess-number-service")
    public ResponseEntity<String> callGuessService() {
        String guessNumberService2Url = "https://guess-a-number.azurewebsites.net/hack/0";
        
        ResponseEntity<String> response = restTemplate.getForEntity(guessNumberService2Url, String.class);
        String message = response.getBody();
        
        return ResponseEntity.ok("Message from Guess Number Service: " + message);
    }
    
}
