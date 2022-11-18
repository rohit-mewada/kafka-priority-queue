package com.mewada.rohit.kafkapriorityqueue.repository;

import com.mewada.rohit.kafkapriorityqueue.model.QueueData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface KafkaPriorityQueueRepository extends JpaRepository<QueueData, Integer> {
    @Query(value = "SELECT * FROM QUEUEDATA q WHERE q.pollReadyTime <= :currentTime", nativeQuery=true)
    List<QueueData> getRecords(@Param("currentTime") Timestamp currentTime);
}
