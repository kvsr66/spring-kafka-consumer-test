package com.example.kafka.dao;

import com.example.kafka.consumer.model.ProcessDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessDetailsRepo extends JpaRepository<ProcessDetails, Integer> {

}
