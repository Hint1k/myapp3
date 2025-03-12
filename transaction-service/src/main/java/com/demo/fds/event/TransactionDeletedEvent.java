package com.demo.fds.event;

import com.demo.fds.entity.Transaction;

public class TransactionDeletedEvent extends TransactionEvent {

    public TransactionDeletedEvent(Long transactionId) {
        super(transactionId);
    }
}