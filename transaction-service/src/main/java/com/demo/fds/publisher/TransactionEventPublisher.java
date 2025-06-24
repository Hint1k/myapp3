package com.demo.fds.publisher;

import com.demo.fds.dto.TransactionDto;
import com.demo.fds.event.TransactionReceivedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishTransactionReceivedEvent(TransactionDto transaction) {
        TransactionReceivedEvent event = new TransactionReceivedEvent(transaction);
        kafkaTemplate.send("transaction-received", event);
        Long transactionId = event.getTransaction().getTransactionId();
        log.info("Published transaction-received event for transaction id: {}", transactionId);
    }
}