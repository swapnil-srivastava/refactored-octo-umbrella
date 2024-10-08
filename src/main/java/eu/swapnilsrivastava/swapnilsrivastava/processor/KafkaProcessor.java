package eu.swapnilsrivastava.swapnilsrivastava.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class KafkaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProcessor.class);
    
    @Autowired
    public void process(StreamsBuilder builder) {
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        KStream<String, String> textLines = builder.stream("shakespeare-topic", Consumed.with(stringSerde, stringSerde));

        KTable<String, Long> wordCounts = textLines
            .peek((key, value) -> logger.info("Received message - Key: {}, Value: {}", key, value))
            .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
            .groupBy((key, word) -> word)
            .count();

        wordCounts.toStream().to("streams-wordcount-output", Produced.with(stringSerde, longSerde));
    }
}

