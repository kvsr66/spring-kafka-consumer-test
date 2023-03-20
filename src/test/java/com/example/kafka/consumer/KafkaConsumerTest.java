package com.example.kafka.consumer;

import com.example.kafka.EventProcessService;
import com.example.kafka.KafkaTestConsumerListenerConfig;
import com.example.kafka.dao.EventsProcessDao;
import com.example.kafka.model.EventDetails;
import com.example.kafka.model.ProcessDetails;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

import static org.mockito.Mockito.*;


@Import(KafkaTestConsumerListenerConfig.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(topics = "test", partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaConsumerTest {

    @InjectMocks
    EventProcessService service;
    @Mock
    EventsProcessDao dao;
    String TOPIC_NAME = "test";
    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    private Producer<String, String> producer;
    @SpyBean
    private CustomKafkaConsumerListener consumer;

    String message;
    @BeforeAll
    void setUp(){
        MockitoAnnotations.openMocks(this);
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();

        try {

            String path = "D:\\subbu\\workspaces\\intellij-workspace\\KafkaConsumerSample\\src\\test\\resources\\events.json";
            String path1 = "../src/test/resources/events.json";
            File file = new File(path);
            message = new String(Files.readAllBytes(file.toPath()));

            System.out.println("Message Read from json file: "+ message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    void testKafkaConsumerListener(){


        when(dao.saveEvent(any(EventDetails.class))).thenReturn(new EventDetails());
        when(dao.saveProcessDetails(any(ProcessDetails.class))).thenReturn(new ProcessDetails());

        producer.send(new ProducerRecord<>(TOPIC_NAME, 0, null, message));
        producer.flush();

        //Verify that the listener method should be called only one time during this test run
        verify(consumer, timeout(5000).times(1)).processMessage(message);

    }

    @Test
    public void testEventService(){

        when(dao.saveEvent(any(EventDetails.class))).thenReturn(new EventDetails());
        when(dao.saveProcessDetails(any(ProcessDetails.class))).thenReturn(new ProcessDetails());

        EventDetails eventDetails = service.saveEvent(new EventDetails());
        ProcessDetails processDetails = service.saveProcess(new ProcessDetails());

        Assertions.assertNotNull(eventDetails);
        Assertions.assertNotNull(processDetails);


    }
    @AfterAll
    void shutdown() {
        producer.close();
    }
}
