package com.mewada.rohit.kafkapriorityqueue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mewada.rohit.kafkapriorityqueue.model.KafkaWaitRecord;
import com.mewada.rohit.kafkapriorityqueue.model.QueueData;
import com.mewada.rohit.kafkapriorityqueue.repository.KafkaPriorityQueueRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KafkaPriorityQueueService {

    @Autowired
    private KafkaPriorityQueueRepository repository;

    @SneakyThrows
    public List<KafkaWaitRecord> getRecords() {

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        List<KafkaWaitRecord> records = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        for(QueueData data : repository.getRecords(currentTime)){

            KafkaWaitRecord record = mapper.readValue(data.getRecord(),KafkaWaitRecord.class);
            records.add(record);

            //if data is processed deleting from db
            repository.delete(data);

        }

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

        return repository.save(queueData);
    }
}
