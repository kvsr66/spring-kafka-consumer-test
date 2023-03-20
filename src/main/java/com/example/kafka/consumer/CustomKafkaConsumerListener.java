package com.example.kafka.consumer;

import com.example.kafka.EventProcessService;
import com.example.kafka.model.EventDetails;
import com.example.kafka.model.ProcessDetails;
import com.example.kafka.dao.EventsProcessDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomKafkaConsumerListener {


    @Autowired
    EventProcessService service;

    @KafkaListener(topics = {"test"}, groupId = "group1", containerFactory =  "listenerContainerFactory")
    public void processMessage(String message){



        System.out.println("message: "+ message);
        ObjectMapper mapper = new ObjectMapper();

        try {
            EventDetails event = mapper.readValue(message, EventDetails.class);
            System.out.println(event);

            if(event!=null){
                service.saveEvent(event);

                System.out.println("Event details saved");
            }

            ProcessDetails process = mapper.readValue(message, ProcessDetails.class);

            System.out.println(process);

            if(process!=null){
                service.saveProcess(process);

                System.out.println("Process details saved");
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
