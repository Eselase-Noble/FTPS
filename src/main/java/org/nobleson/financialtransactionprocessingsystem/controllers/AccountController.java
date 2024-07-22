package org.nobleson.financialtransactionprocessingsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.models.Account;
import org.nobleson.financialtransactionprocessingsystem.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/FTPS/customer/account")
@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/add-account")
    public ResponseEntity<String> addAccount( @RequestParam Long branchId, @RequestParam Long customerId, @RequestBody  Account account) {

        accountService.createAccount(branchId, customerId, account );
        logger.info("Account created");
        return new ResponseEntity<>("Account successfully created", HttpStatus.CREATED);
    }

    @PostMapping("/add-more-accounts")
    public ResponseEntity<String> addAccounts(@RequestBody final List<Account> accounts) {
        accountService.createAccounts(accounts);
        return new ResponseEntity<>("Accounts successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/all-accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        final List<Account> accounts = accountService.getAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/accounts-page-size")
    public ResponseEntity<Page<Account>> getAccounts(@RequestParam Integer page, @RequestParam Integer size){
        final Page<Account> accounts = accountService.getAccounts(page, size);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/pageable-accounts")
    public ResponseEntity<Page<Account>> getAccounts(@RequestBody final Pageable pageable) {

        final Page<Account> accounts = accountService.getAccounts(pageable);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/find-account/{accountID}")
    public ResponseEntity<Account> getAccountById(@PathVariable("accountID") final Long accountID) {

        return new ResponseEntity<>(accountService.getAccount(accountID), HttpStatus.OK);
    }
    @PutMapping("/update-account")
    public ResponseEntity<String> updateAccount(@RequestParam Long accountNumber ,@RequestParam Long branchId, @RequestParam Long customerId,@RequestBody final Account account) {

        accountService.updateAccount(accountNumber,branchId, customerId, account);
        return new ResponseEntity<>("Account updated successfully", HttpStatus.OK);

    }

    @PutMapping("/update-accounts")
    public ResponseEntity<List<Account>> updateAccounts(@PathVariable("accountID") List<Account> accountList) {

        return new ResponseEntity<>(accountService.updateAccounts(accountList), HttpStatus.OK);
    }


    @DeleteMapping("/delete-account/{accountID}")
    public ResponseEntity<String> deleteAccountById(@PathVariable("accountID") final Long accountID) {
        accountService.deleteAccount(accountID);
        return new ResponseEntity<>("Account successfully deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all-accounts")
    public ResponseEntity<String> deleteAllAccounts() {
        accountService.deleteAllAccounts();
        return new ResponseEntity<>("All Account have been successfully deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody final Account account) {
        accountService.deleteAccount(account);
        return new ResponseEntity<>("Account successfully deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete-many-accounts")
    public ResponseEntity<String> deleteAccounts(@RequestBody final List<Account> accounts) {
        accountService.deleteAccounts(accounts);
        return new ResponseEntity<>("All input accounts have been successfully deleted", HttpStatus.OK);
    }

}
