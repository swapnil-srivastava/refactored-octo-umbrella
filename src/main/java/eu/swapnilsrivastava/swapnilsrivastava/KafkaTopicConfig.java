package eu.swapnilsrivastava.swapnilsrivastava;

import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean 
    public KafkaAdmin admin() {
      return new KafkaAdmin(Map.of(
        AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, "30000",
        AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, "60000"
      ));
    }

    @Bean
    public NewTopic supermanTopic() {
      return TopicBuilder.name("superman-topic")
          .partitions(6)
          .replicas(3)
          .compact()
          .build();
    }
}

