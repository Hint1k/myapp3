package com.demo.fds.entity;

import com.demo.fds.utils.TransactionCurrency;
import com.demo.fds.utils.TransactionRisk;
import com.demo.fds.utils.TransactionStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column("transaction_id")
    private Long transactionId;

    @Column("customer_id")
    private Long customerId;

    @Column("merchant_id")
    private Long merchantId;

    @Column("amount")
    private BigDecimal amount;

    @Column("timestamp")
    private LocalDateTime timestamp;

    @Column("currency")
    private TransactionCurrency currency;

    @Column("location")
    private String location;

    @Column("risk")
    private TransactionRisk risk;

    @Column("status")
    private TransactionStatus status;

    @Version
    @Column("version")
    private Long version;

    // no transaction id
    public Transaction(Long customerId, Long merchantId, BigDecimal amount, LocalDateTime timestamp,
                       TransactionCurrency currency, String location, TransactionRisk risk, TransactionStatus status,
                       Long version) {
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.currency = currency;
        this.location = location;
        this.risk = risk;
        this.status = status;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(customerId, that.customerId)
                && Objects.equals(merchantId, that.merchantId) && Objects.equals(amount, that.amount)
                && Objects.equals(timestamp, that.timestamp) && currency == that.currency
                && Objects.equals(location, that.location) && risk == that.risk && status == that.status
                && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                transactionId, customerId, merchantId, amount, timestamp, currency, location, risk, status, version
        );
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", customerId=" + customerId +
                ", merchantId=" + merchantId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", currency=" + currency +
                ", location='" + location + '\'' +
                ", risk=" + risk +
                ", status=" + status +
                ", version=" + version +
                '}';
    }
}