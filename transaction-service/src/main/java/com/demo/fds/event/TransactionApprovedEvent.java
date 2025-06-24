package com.demo.fds.event;

import com.demo.fds.dto.TransactionDto;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TransactionApprovedEvent extends TransactionEvent {

    private final String approvedReason;

    protected TransactionApprovedEvent(TransactionDto transaction, String approvedReason) {
        super(transaction);
        this.approvedReason = approvedReason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransactionApprovedEvent that = (TransactionApprovedEvent) o;
        return Objects.equals(approvedReason, that.approvedReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), approvedReason);
    }

    @Override
    public String toString() {
        return "TransactionApprovedEvent{" +
                "approvedReason='" + approvedReason + '\'' +
                '}';
    }
}