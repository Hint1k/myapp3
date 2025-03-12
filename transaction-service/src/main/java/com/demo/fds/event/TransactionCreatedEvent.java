package com.demo.fds.event;

import com.demo.fds.dto.TransactionDto;
import lombok.Getter;

@Getter
public class TransactionCreatedEvent extends TransactionEvent {

    private final TransactionDto transaction;

    public TransactionCreatedEvent(TransactionDto transaction) {
        super(transaction.getTransactionId());
        this.transaction = transaction;
    }
}