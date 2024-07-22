package org.nobleson.financialtransactionprocessingsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("/FTPS/customer/account/transaction")
@Controller
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;


    @PostMapping("/withdraw-money")
    public ResponseEntity<String> withdrawMoney(@RequestParam String accountNumber,@RequestParam BigDecimal amount){

        transactionService.withdraw(accountNumber, amount);
        return new ResponseEntity<>("Amount of GHC" + amount + " successfully withdrawn", HttpStatus.OK);
    }

    @PostMapping("/transfer-money")
    public ResponseEntity<String> transferMoney(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber ,@RequestParam BigDecimal amount){

        transactionService.transfer(fromAccountNumber, toAccountNumber, amount);
        return new ResponseEntity<>("Amount of GHC" + amount + " successfully transferred from " + fromAccountNumber + " to " + toAccountNumber + ".", HttpStatus.OK);
    }

    @PostMapping("/deposit-money")
    public ResponseEntity<String> depositMoney(@RequestParam String accountNumber, @RequestParam BigDecimal amount){
        transactionService.deposit(accountNumber, amount);
        return new ResponseEntity<>("An Amount of GHC" + amount + " successfully deposited.", HttpStatus.OK);
    }

    @PostMapping("/get-balance")
    public ResponseEntity<String> getBalance(@RequestParam String accountNumber){
        BigDecimal availableBalance = transactionService.getAvailableBalance(accountNumber);
        BigDecimal mainBalance = transactionService.getMainBalance(accountNumber);

        return new ResponseEntity<>("Available Balance: GHC" + availableBalance + ". Main Balance: GHC" + mainBalance, HttpStatus.OK);
    }





}
