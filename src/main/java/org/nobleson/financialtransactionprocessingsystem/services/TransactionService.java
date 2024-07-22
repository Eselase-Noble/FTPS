package org.nobleson.financialtransactionprocessingsystem.services;


import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.enums.TransactionStatus;
import org.nobleson.financialtransactionprocessingsystem.enums.TransactionType;
import org.nobleson.financialtransactionprocessingsystem.interfaceServices.TransactionInterfaceService;
import org.nobleson.financialtransactionprocessingsystem.models.Account;
import org.nobleson.financialtransactionprocessingsystem.models.Transaction;
import org.nobleson.financialtransactionprocessingsystem.repositories.AccountRepository;
import org.nobleson.financialtransactionprocessingsystem.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;


@RequiredArgsConstructor
@Service
public class TransactionService implements TransactionInterfaceService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;



    @Transactional
    @Override
    public void createTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId).orElseThrow(() -> new RuntimeException("Transaction Not Found"));

    }

    @Transactional
    @Override
    public synchronized void withdraw(String accountNumber, BigDecimal amount) {
     Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Transaction Not Found"));
    Transaction transaction = new Transaction();

     if (account.getMainBalance().compareTo(amount) < 0) {
         throw new RuntimeException("Transaction Amount is less than Account Amount " + account.getMainBalance());
     }

        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setTransactionStatus(TransactionStatus.PROCESSING);
        System.out.println("PROCESSING TRANSACTION");
        account.setAvailableBalance(account.getAvailableBalance().subtract(amount));
        System.out.println("PROCESSING TRANSACTION");
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        account.setMainBalance(account.getMainBalance().subtract(amount));
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDate(Date.from(Instant.now()));
        transaction.setAccount(account);
        System.out.println("TRANSACTION COMPLETED");

        accountRepository.save(account);
        transactionRepository.save(transaction);

    }

    @Transactional
    @Override
    public synchronized void deposit(String accountNumber , BigDecimal amount) {
        Transaction transaction = new Transaction();
        Account account = accountRepository.findByAccountNumber(accountNumber).
                orElseThrow(() -> new RuntimeException("Account Number Not Found"));

        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionStatus(TransactionStatus.PROCESSING);
        System.out.println("PROCESSING TRANSACTION");
        account.setAvailableBalance(account.getAvailableBalance().add(amount));
        System.out.println("PROCESSING TRANSACTION");
        transaction.setTransactionAmount(amount);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setTransactionAmount(amount);
        account.setMainBalance(account.getMainBalance().add(amount));
        System.out.println("TRANSACTION COMPLETED");
        transaction.setAccount(account);
        transaction.setTransactionDate(Date.from(Instant.now()));
        transactionRepository.save(transaction);
        accountRepository.save(account);

        System.out.println(TransactionType.TRANFER);
    }

    @Transactional
    @Override
    public synchronized void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {

        Transaction transaction = new Transaction();
        Transaction transaction1 = new Transaction();
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber).orElseThrow(() -> new RuntimeException("Account Number Not Found"));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(() -> new RuntimeException("Account Number  Not Found"));

        if (fromAccount.getMainBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Transaction Amount is less than Account Amount " + fromAccount.getMainBalance());
        }

        transaction.setTransactionType(TransactionType.TRANFER);
        System.out.println("PROCESSING TRANSACTION");
        //Processing the transaction
        transaction.setTransactionStatus(TransactionStatus.PROCESSING);
        fromAccount.setAvailableBalance(fromAccount.getAvailableBalance().subtract(amount));
        toAccount.setAvailableBalance(toAccount.getAvailableBalance().add(amount));

        System.out.println("PROCESSING TRANSACTION");
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        fromAccount.setMainBalance(fromAccount.getMainBalance().subtract(amount));
        toAccount.setMainBalance(toAccount.getMainBalance().add(amount));
        transaction.setTransactionDate(Date.from(Instant.now()));
        System.out.println("TRANSACTION COMPLETED");

        transaction1.setTransactionType(TransactionType.DEPOSIT);
        transaction1.setTransactionDate(Date.from(Instant.now()));
        transaction1.setTransactionAmount(amount);
        transaction1.setTransactionStatus(TransactionStatus.COMPLETED);

        transaction1.setAccount(toAccount);

        transaction.setAccount(toAccount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        transactionRepository.save(transaction);
        transactionRepository.save(transaction1);


    }

    @Override
    public BigDecimal getMainBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account Not Found"));


        return account.getMainBalance();
    }

    @Override
    public BigDecimal  getAvailableBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account Not Found"));


        return account.getAvailableBalance();
    }
}
