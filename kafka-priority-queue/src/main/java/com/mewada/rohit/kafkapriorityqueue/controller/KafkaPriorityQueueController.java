package com.mewada.rohit.kafkapriorityqueue.controller;

import com.mewada.rohit.kafkapriorityqueue.model.KafkaWaitRecord;
import com.mewada.rohit.kafkapriorityqueue.model.QueueData;
import com.mewada.rohit.kafkapriorityqueue.service.KafkaPriorityQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KafkaPriorityQueueController {

    @Autowired
    private KafkaPriorityQueueService service;

    @GetMapping("/getRecord")
    public List<KafkaWaitRecord> getRecords(){
        return service.getRecords();
    }

    @PostMapping("/postRecord")
    public QueueData postRecord(@RequestBody KafkaWaitRecord kafkaWaitRecord){
        return service.postRecord(kafkaWaitRecord);
    }

}
