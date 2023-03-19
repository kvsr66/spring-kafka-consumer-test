package com.example.kafka.consumer;

import com.example.kafka.KafkaTestConsumerListenerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Import(KafkaTestConsumerListenerConfig.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(topics = "test", partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaConsumerTest {

    String TOPIC_NAME = "test";
    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    private Producer<String, String> producer;
    @SpyBean
    private CustomKafkaConsumerListener consumer;

    String message;
    @BeforeAll
    void setUp(){
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();
        try {

            File file = new File("../src/test/resources/events.json");
            message = new String(Files.readAllBytes(file.toPath()));

            System.out.println("Message Read from json file: "+ message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    void testKafkaConsumerListener(){

//        String message = "{\n" +
//                "  \"name\": \"event1\",\n" +
//                "  \"id\": 1,\n" +
//                "  \"processName\": \"process1\",\n" +
//                "  \"processId\": 12\n" +
//                "}";

        producer.send(new ProducerRecord<>(TOPIC_NAME, 0, null, message));
        producer.flush();

        //Verify that the listener method should be called only one time during this test run
        Mockito.verify(consumer, Mockito.timeout(5000).times(1)).processMessage(message);


    }


    @AfterAll
    void shutdown() {
        producer.close();
    }
}
