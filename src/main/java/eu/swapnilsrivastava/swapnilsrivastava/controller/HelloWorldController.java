package eu.swapnilsrivastava.swapnilsrivastava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class HelloWorldController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    @GetMapping("/hello-gcp")
    public String HelloWorldSprings() {
       return "hello world google cloud platform :: Working";
    }

    @GetMapping("/hello-world")
    public String HelloWorld() {
        return "Hello world :: Working :: Working on different sha";
    }

    @GetMapping("/hello")
    public String Hello() {
        return "Hello Hello :: Working";
    }

    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMessage() {
        return "Hello from the microservice! --> Working";
    }

    @GetMapping("/call-heroku-service")
    public Mono<ResponseEntity<String>> callGcpService() {
        return webClient.get()
            .uri("/call-heroku-service")
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, 
                response -> Mono.error(new RuntimeException("4xx error")))
            .onStatus(HttpStatusCode::is5xxServerError, 
                response -> Mono.error(new RuntimeException("5xx error")))
            .toEntity(String.class)
            .timeout(Duration.ofSeconds(10));
    }

    @GetMapping("/call-guess-number-service")
    public ResponseEntity<String> callGuessService() {
        String guessNumberService2Url = "https://guess-a-number.azurewebsites.net/hack/0";
        
        ResponseEntity<String> response = restTemplate.getForEntity(guessNumberService2Url, String.class);
        String message = response.getBody();
        
        return ResponseEntity.ok("Message from Guess Number Service: " + message);
    }
    
}
