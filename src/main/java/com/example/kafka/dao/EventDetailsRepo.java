package com.example.kafka.dao;

import com.example.kafka.model.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDetailsRepo extends JpaRepository<EventDetails, Integer> {
}
