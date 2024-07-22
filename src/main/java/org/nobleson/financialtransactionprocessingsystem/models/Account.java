package org.nobleson.financialtransactionprocessingsystem.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nobleson.financialtransactionprocessingsystem.enums.AccountStatus;
import org.nobleson.financialtransactionprocessingsystem.enums.AccountType;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Column(unique = true,nullable = false)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private volatile BigDecimal mainBalance;
    private volatile BigDecimal availableBalance;
    @InsertOnlyProperty
    private Date createDate;
    private Date updateDate;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transaction;


    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    private Branch branch;
}
