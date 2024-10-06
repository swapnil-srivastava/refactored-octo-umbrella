package eu.swapnilsrivastava.swapnilsrivastava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SwapnilsrivastavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwapnilsrivastavaApplication.class, args);
	}

}
