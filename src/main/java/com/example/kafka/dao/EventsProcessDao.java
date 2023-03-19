package com.example.kafka.dao;

import com.example.kafka.consumer.model.EventDetails;
import com.example.kafka.consumer.model.ProcessDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventsProcessDao {

    @Autowired
    EventDetailsRepo eventDetailsRepo;
    @Autowired
    ProcessDetailsRepo processDetailsRepo;

    public EventDetails saveEvent(EventDetails eventDetails){

        return eventDetailsRepo.save(eventDetails);
    }

    public ProcessDetails saveProcessDetails(ProcessDetails processDetails){
        return processDetailsRepo.save(processDetails);
    }

}
