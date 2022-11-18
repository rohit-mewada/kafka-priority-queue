package com.mewada.rohit.kafkapriorityqueue.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter
@Entity
@Table(name = "QUEUEDATA")
public class QueueData {
    @Id
    @GeneratedValue
    private Integer ID;
    @Column(name = "RECORD",length = 100000)
    private String record;
    @Column(name = "POLLREADYTIME")
    private Timestamp pollReadyTime;
}
