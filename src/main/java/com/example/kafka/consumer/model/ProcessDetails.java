package com.example.kafka.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ProcessDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String processName;
    Integer processId;

}
