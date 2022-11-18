package com.mewada.rohit.kafkapriorityqueue.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KafkaWaitRecord {

    private String recordNumber;
    private Integer delayMs;
    private String payload;

}
