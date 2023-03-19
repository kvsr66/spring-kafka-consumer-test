package com.example.kafka.consumer;

import com.example.kafka.consumer.model.EventDetails;
import com.example.kafka.consumer.model.ProcessDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class CustomKafkaConsumerListener {

//
//    private final CountDownLatch latch;
//
//    @Autowired
//    public CustomKafkaConsumerListener(CountDownLatch latch) {
//        this.latch = latch;
//    }


    @KafkaListener(topics = {"test"}, groupId = "group1", containerFactory =  "listenerContainerFactory")
    public void processMessage(String message){



        System.out.println("message: "+ message);
        ObjectMapper mapper = new ObjectMapper();

        try {
            EventDetails event = mapper.readValue(message, EventDetails.class);
            System.out.println(event);

            ProcessDetails process = mapper.readValue(message, ProcessDetails.class);

            System.out.println(process);

//            latch.countDown();

        }catch (Exception e){
            e.printStackTrace();
        }
        //Do Save the data to database

    }

}
