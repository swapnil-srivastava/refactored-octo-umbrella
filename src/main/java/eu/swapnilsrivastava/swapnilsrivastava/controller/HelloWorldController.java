package eu.swapnilsrivastava.swapnilsrivastava.controller;

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
        return "Hello world GCP";
    }
    
}
