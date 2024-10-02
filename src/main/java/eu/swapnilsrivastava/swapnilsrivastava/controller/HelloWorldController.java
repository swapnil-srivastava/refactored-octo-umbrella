package eu.swapnilsrivastava.swapnilsrivastava.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {


    @GetMapping("/hello-gcp")
    public String HelloWorldSprings() {
       return "hello world google cloud platform";
    }

    @GetMapping("/hello-world")
    public String HelloWorld() {
        return "Hello world";
    }

    @GetMapping("/hello")
    public String Hello() {
        return "Hello Hello";
    }

    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMessage() {
        return "Hello from the microservice!";
    }
    
}
