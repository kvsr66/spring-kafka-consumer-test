package com.example.kafka.dao;

import com.example.kafka.consumer.model.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDetailsRepo extends JpaRepository<EventDetails, Integer> {
}
