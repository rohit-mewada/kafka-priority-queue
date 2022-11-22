package com.mewada.rohit.kafkapriorityqueue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mewada.rohit.kafkapriorityqueue.model.QueueData;
import com.mewada.rohit.kafkapriorityqueue.model.Records;
import com.mewada.rohit.kafkapriorityqueue.repository.KafkaPriorityQueueRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.model.KafkaWaitRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
public class KafkaPriorityQueueService {

    @Autowired
    private KafkaPriorityQueueRepository repository;

    @SneakyThrows
    public Records getRecords() {

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Records records = new Records();

        ObjectMapper mapper = new ObjectMapper();

        for(QueueData data : repository.getRecords(currentTime)){

            KafkaWaitRecord record = mapper.readValue(data.getRecord(),KafkaWaitRecord.class);
            records.getRecordList().add(record);

            //if data is processed deleting from db
            repository.delete(data);

        }

//        log.info("Get Records = {}",records.getRecordList());
        return records;
    }

    @SneakyThrows
    public QueueData postRecord(KafkaWaitRecord kafkaWaitRecord) {

        QueueData queueData = new QueueData();

//        handling the waiting time
        log.info("current time {}",new Timestamp(System.currentTimeMillis()));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()+kafkaWaitRecord.getDelayMs());
        queueData.setPollReadyTime(timestamp);
        log.info("time stamp {}",timestamp);


//        mapping object to string
        ObjectMapper mapper = new ObjectMapper();
        String record = mapper.writeValueAsString(kafkaWaitRecord);

        queueData.setRecord(record);

        log.info("Post Record = {}",queueData);
        return repository.save(queueData);
    }
}
