package com.demo.fds.event;

import com.demo.fds.dto.TransactionDto;
import lombok.Getter;

@Getter
public class TransactionUpdatedEvent extends TransactionEvent {

    private final TransactionDto oldTransaction;
    private final TransactionDto newTransaction;

    public TransactionUpdatedEvent(TransactionDto oldTransaction, TransactionDto newTransaction) {
        super(newTransaction.getTransactionId());
        this.oldTransaction = oldTransaction;
        this.newTransaction = newTransaction;
    }
}