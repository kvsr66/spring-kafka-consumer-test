package com.example.kafka;

import com.example.kafka.dao.EventsProcessDao;
import com.example.kafka.model.EventDetails;
import com.example.kafka.model.ProcessDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProcessService {

    @Autowired
    EventsProcessDao dao;

    public EventDetails saveEvent(EventDetails eventDetails){

        return dao.saveEvent(eventDetails);
    }

    public ProcessDetails saveProcess(ProcessDetails processDetails){

        return dao.saveProcessDetails(processDetails);
    }
}
