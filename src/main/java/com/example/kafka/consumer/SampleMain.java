package com.example.kafka.consumer;

import com.example.kafka.model.EventDetails;
import com.example.kafka.model.ProcessDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleMain {

    public static void main(String[] args) throws Exception{


    String message = "{\n" +
            "  \"name\": \"event1\",\n" +
            "  \"id\": 1,\n" +
            "  \"processName\": \"process1\",\n" +
            "  \"processId\": 12\n" +
            "}";


    ObjectMapper objectMapper = new ObjectMapper();

     EventDetails event = objectMapper.readValue(message, EventDetails.class);

        ProcessDetails processDetails = objectMapper.readValue(message, ProcessDetails.class);

        System.out.println(event);

        System.out.println(processDetails);

    }
}
