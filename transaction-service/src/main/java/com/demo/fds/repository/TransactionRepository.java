package com.demo.fds.repository;

import com.demo.fds.dto.TransactionFilterDto;
import com.demo.fds.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Long id);

    void delete(Transaction transaction);

    Page<Transaction> findFilteredTransactions(TransactionFilterDto filterDto, Pageable pageable);
}