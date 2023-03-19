package com.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaConsumerSampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerSampleApplication.class, args);
	}

}
