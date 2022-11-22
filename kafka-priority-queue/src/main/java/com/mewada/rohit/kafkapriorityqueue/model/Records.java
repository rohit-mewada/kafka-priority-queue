package com.mewada.rohit.kafkapriorityqueue.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.KafkaWaitRecord;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class Records implements Serializable {
    List<KafkaWaitRecord> recordList = new LinkedList<>();
}
