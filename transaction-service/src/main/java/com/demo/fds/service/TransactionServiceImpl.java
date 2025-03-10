package com.demo.fds.service;

import com.demo.fds.dto.TransactionFilterDto;
import com.demo.fds.entity.Transaction;
import com.demo.fds.exception.custom.ResourceNotFoundException;
import com.demo.fds.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "transactions", allEntries = true) // to avoid messing up with the filtering logic
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    @CacheEvict(value = "transactions", key = "#transactionId")
    public Optional<Transaction> updateTransaction(Long transactionId, Transaction transaction) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(transactionId);
        if (existingTransaction.isPresent()) {
            Transaction currentTransaction = existingTransaction.get();
            if (!currentTransaction.getVersion().equals(transaction.getVersion())) {
                throw new OptimisticLockingFailureException("The transaction has been modified by another user.");
            }
            updateTransactionFields(currentTransaction, transaction);
            return Optional.of(transactionRepository.save(currentTransaction));
        }
        log.error("Could not find transaction with id {}", transactionId);
        throw new ResourceNotFoundException("Transaction with ID " + transactionId + " not found");
    }

    @Override
    @Transactional
    @CacheEvict(value = "transactions", allEntries = true)
    public boolean deleteTransaction(Long id) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(id);
        if (existingTransaction.isPresent()) {
            transactionRepository.delete(existingTransaction.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "transactions",
            key = "#transaction.hashCode() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<Transaction> getFilteredTransactions(TransactionFilterDto transaction, Pageable pageable) {
        return transactionRepository.findFilteredTransactions(transaction, pageable);
    }

    private void updateTransactionFields(Transaction currentTransaction, Transaction transaction) {
        currentTransaction.setCustomerId(transaction.getCustomerId());
        currentTransaction.setMerchantId(transaction.getMerchantId());
        currentTransaction.setAmount(transaction.getAmount());
        currentTransaction.setTimestamp(transaction.getTimestamp());
        currentTransaction.setCurrency(transaction.getCurrency());
        currentTransaction.setLocation(transaction.getLocation());
        currentTransaction.setRisk(transaction.getRisk());
        currentTransaction.setStatus(transaction.getStatus());
        currentTransaction.setVersion(transaction.getVersion() + 1);
    }
}