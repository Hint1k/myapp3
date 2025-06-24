package com.demo.fds.repository;

import com.demo.fds.dto.TransactionFilterDto;
import com.demo.fds.entity.Transaction;
import com.demo.fds.exception.custom.DatabaseException;
import com.demo.fds.utils.TransactionCurrency;
import com.demo.fds.utils.TransactionRisk;
import com.demo.fds.utils.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.StringJoiner;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transactions (account_source_number, destination_account_number, amount, timestamp, "
                + "currency, location, risk, status, version) "
                + "VALUES (:sourceAccountNumber, :destinationAccountNumber, :amount, :timestamp, :currency, "
                + ":location, :risk, :status, :version) "
                + "ON CONFLICT (transaction_id) "
                + "DO UPDATE SET source_account_number = EXCLUDED.source_account_number, destination_account_number = "
                + "EXCLUDED.destination_account_number, amount = EXCLUDED.amount, timestamp = EXCLUDED.timestamp, "
                + "currency = EXCLUDED.currency, location = EXCLUDED.location, risk = EXCLUDED.risk, status = "
                + "EXCLUDED.status, version = EXCLUDED.version";

        try {
            namedParameterJdbcTemplate.update(sql, createTransactionParams(transaction));
        } catch (Exception e) {
            throw new DatabaseException("Error saving transaction", e);
        }

        return transaction;
    }

    public void delete(Transaction transaction) {
        String sql = "DELETE FROM transactions WHERE transaction_id = :transactionId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("transactionId", transaction.getTransactionId());
        try {
            namedParameterJdbcTemplate.update(sql, params);
        } catch (Exception e) {
            throw new DatabaseException("Error deleting transaction", e);
        }
    }

    public Optional<Transaction> findById(Long id) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = :transactionId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("transactionId", id);
        try {
            List<Transaction> transactions = namedParameterJdbcTemplate.query(sql, params, TRANSACTION_ROW_MAPPER);
            return transactions.isEmpty() ? Optional.empty() : Optional.of(transactions.get(0));
        } catch (Exception e) {
            throw new DatabaseException("Error fetching transaction by id", e);
        }
    }

    public Page<Transaction> findFilteredTransactions(TransactionFilterDto filterDto, Pageable pageable) {
        String baseQuery = "SELECT * FROM transactions";
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringJoiner conditions = new StringJoiner(" AND ");
        try {
            applyFilters(filterDto, conditions, params);

            String finalQuery = baseQuery;
            if (conditions.length() > 0) {
                finalQuery += " WHERE " + conditions;
            }

            finalQuery += " LIMIT :limit OFFSET :offset";
            params.addValue("limit", pageable.getPageSize());
            params.addValue("offset", pageable.getOffset());

            List<Transaction> transactions =
                    namedParameterJdbcTemplate.query(finalQuery, params, TRANSACTION_ROW_MAPPER);
            return new PageImpl<>(transactions, pageable, transactions.size());
        } catch (Exception e) {
            throw new DatabaseException("Error fetching filtered transactions", e);
        }
    }

    private void addNamedCondition(Object filterValue, String columnName, StringJoiner conditions,
                                   MapSqlParameterSource params) {
        if (filterValue != null) {
            conditions.add(columnName + " = :" + columnName);
            params.addValue(columnName, filterValue);
        }
    }

    private <T> void addRangeCondition(T minValue, T maxValue, String columnName, StringJoiner conditions,
                                       MapSqlParameterSource params) {
        if (minValue != null) {
            conditions.add(columnName + " >= :" + columnName + "Min");
            params.addValue(columnName + "Min", minValue);
        }
        if (maxValue != null) {
            conditions.add(columnName + " <= :" + columnName + "Max");
            params.addValue(columnName + "Max", maxValue);
        }
    }

    private static final RowMapper<Transaction> TRANSACTION_ROW_MAPPER = (rs, rowNum) -> {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getLong("transaction_id"));
        transaction.setSourceAccountNumber(rs.getLong("source_account_number"));
        transaction.setDestinationAccountNumber(rs.getLong("destination_account_number"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        transaction.setCurrency(TransactionCurrency.valueOf(rs.getString("currency")));
        transaction.setLocation(rs.getString("location"));
        transaction.setRisk(TransactionRisk.valueOf(rs.getString("risk")));
        transaction.setStatus(TransactionStatus.valueOf(rs.getString("status")));
        transaction.setVersion(rs.getLong("version"));
        return transaction;
    };

    private MapSqlParameterSource createTransactionParams(Transaction transaction) {
        return new MapSqlParameterSource()
                .addValue("source_account_number", transaction.getSourceAccountNumber())
                .addValue("destination_account_number", transaction.getDestinationAccountNumber())
                .addValue("amount", transaction.getAmount())
                .addValue("timestamp", transaction.getTimestamp())
                .addValue("currency", transaction.getCurrency())
                .addValue("location", transaction.getLocation())
                .addValue("risk", transaction.getRisk())
                .addValue("status", transaction.getStatus())
                .addValue("version", transaction.getVersion());
    }

    private void applyFilters(TransactionFilterDto filterDto, StringJoiner conditions, MapSqlParameterSource params) {
        addNamedCondition(filterDto.getTransactionId(), "transaction_id", conditions, params);
        addNamedCondition(filterDto.getSourceAccountNumber(), "source_account_number", conditions, params);
        addNamedCondition(filterDto.getDestinationAccountNumber(),
                "destination_account_number", conditions, params);
        addNamedCondition(filterDto.getStatus(), "status", conditions, params);
        addNamedCondition(filterDto.getRisk(), "risk", conditions, params);
        addNamedCondition(filterDto.getCurrency(), "currency", conditions, params);
        addRangeCondition(filterDto.getMinAmount(), filterDto.getMaxAmount(), "amount", conditions, params);
        addRangeCondition(filterDto.getStartDate(), filterDto.getEndDate(),
                "timestamp", conditions, params);
    }
}