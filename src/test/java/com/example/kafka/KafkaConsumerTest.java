package com.example.kafka;

import com.example.kafka.consumer.CustomKafkaConsumerListener;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(topics = "test", partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
//@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaConsumerTest {

    String TOPIC_NAME = "test";
    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaTemplate<String, String> kafkaTemplate;
    private Producer<String, String> producer;

    @Captor
    ArgumentCaptor<String> messageCaptor;
    @SpyBean
    private CustomKafkaConsumerListener consumer;
    @BeforeAll
    void setUp(){
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();
  }

   @Test
   void testKafkaConsumerListener(){

        String message = "{\n" +
                "  \"name\": \"event1\",\n" +
                "  \"id\": 1,\n" +
                "  \"processName\": \"process1\",\n" +
                "  \"processId\": 12\n" +
                "}";

        producer.send(new ProducerRecord<>(TOPIC_NAME,message));

     //  verify(consumer, timeout(5000).times(1)).processMessage(messageCaptor.capture());

       consumer.processMessage(messageCaptor.capture());



   }

}
