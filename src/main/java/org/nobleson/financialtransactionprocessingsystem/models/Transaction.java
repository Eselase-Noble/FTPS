package org.nobleson.financialtransactionprocessingsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nobleson.financialtransactionprocessingsystem.enums.TransactionStatus;
import org.nobleson.financialtransactionprocessingsystem.enums.TransactionType;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @InsertOnlyProperty
    private Date transactionDate;
    private BigDecimal transactionAmount;
   // private String transactionDescription;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
