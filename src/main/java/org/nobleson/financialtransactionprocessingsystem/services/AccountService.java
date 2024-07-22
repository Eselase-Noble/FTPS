package org.nobleson.financialtransactionprocessingsystem.services;

import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.interfaceServices.AccountInterfaceService;
import org.nobleson.financialtransactionprocessingsystem.models.Account;
import org.nobleson.financialtransactionprocessingsystem.models.Branch;
import org.nobleson.financialtransactionprocessingsystem.models.Customer;
import org.nobleson.financialtransactionprocessingsystem.repositories.AccountRepository;
import org.nobleson.financialtransactionprocessingsystem.repositories.BranchRepository;
import org.nobleson.financialtransactionprocessingsystem.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor

public class AccountService implements AccountInterfaceService {

    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Account getAccount(Long accountId) {
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Account> getAccounts(int page, int pageSize) {
        return accountRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Account> getAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account createAccount(Long branchId, Long customerId, Account account ) {
        if (accountRepository.findByAccountId(account.getAccountId()).isPresent() || accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new RuntimeException("Account already exists");
        }

        Branch branch = branchRepository.findByBranchId(branchId).orElseThrow(() -> new RuntimeException("Branch not found"));

        Customer customer = customerRepository.findCustomerByCustomerId(customerId).orElseThrow(()-> new RuntimeException("Customer not found"));

        account.setCustomer(customer);

        account.setBranch(branch);
        account.setCreateDate(Date.from(Instant.now()));
        account.setUpdateDate(Date.from(Instant.now()));

        return accountRepository.save(account);
    }

    @Override
    public List<Account> createAccounts(List<Account> accounts) {
        if (accounts.isEmpty()) {
            throw new RuntimeException("Account list cannot be empty");
        }
        for (Account account : accounts) {
            if (accountRepository.existsById(account.getAccountId())) {
                throw new RuntimeException("Account already exists");
            }

        }
        return accountRepository.saveAll(accounts);
    }

    @Override
    public Account updateAccount(Long accountNumber, Long branchId, Long customerId,Account account) {
        if (!accountRepository.existsByAccountNumber(account.getAccountNumber())) {
           throw new RuntimeException("Account not exists");
        }

        Branch branch = branchRepository.findByBranchId(branchId).orElseThrow(() -> new RuntimeException("Branch not found"));
        Customer customer = customerRepository.findCustomerByCustomerId(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        Account updatedAccount= accountRepository.findByAccountNumber(account.getAccountNumber()).orElseThrow(() -> new RuntimeException("Account not found"));


        updatedAccount.setBranch(branch);
        updatedAccount.setCustomer(customer);
        updatedAccount.setAccountNumber(account.getAccountNumber());
        updatedAccount.setAccountType(account.getAccountType());
        updatedAccount.setMainBalance(account.getMainBalance());
        updatedAccount.setAccountStatus(account.getAccountStatus());
        updatedAccount.setAccountType(account.getAccountType());
        updatedAccount.setAvailableBalance(account.getAvailableBalance());
        updatedAccount.setCreateDate(updatedAccount.getCreateDate());
        updatedAccount.setUpdateDate(Date.from(Instant.now()));


        return accountRepository.save(updatedAccount);
    }

    @Override
    public List<Account> updateAccounts(List<Account> accounts) {

        if (accounts.isEmpty()) {
            throw new RuntimeException("Account list cannot be empty");
        }

        for (Account account : accounts) {
            if (!accountRepository.existsById(account.getAccountId())) {
                throw new RuntimeException("Account not found");
            }
            accountRepository.save(account);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.delete(account);
    }

    @Override
    public void deleteAccount(Account account) {
    Account account1 = accountRepository.findByAccountId(account.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found"));
    accountRepository.delete(account1);
    }

    @Override
    public void deleteAccounts(List<Account> accounts) {
        if (accounts.isEmpty()) {
            throw new RuntimeException("Account list cannot be empty");
        }

        for (Account account : accounts) {
            if (!accountRepository.existsById(account.getAccountId())) {
                throw new RuntimeException("Account not found");
            }
            accountRepository.delete(account);
            accounts.remove(account);
        }
    }

    @Override
    public void deleteAllAccounts() {
            accountRepository.deleteAll();
    }


}
