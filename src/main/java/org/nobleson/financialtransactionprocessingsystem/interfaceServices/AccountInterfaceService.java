package org.nobleson.financialtransactionprocessingsystem.interfaceServices;

import org.nobleson.financialtransactionprocessingsystem.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountInterfaceService {
    Account getAccount(Long accountId);
    List<Account> getAccounts();
    Account createAccount(Long branchId, Long customerId, Account account );
    List<Account> createAccounts(List<Account> accounts);
    Account updateAccount(Long accountId,Long branchId, Long customerId, Account account);
    List<Account> updateAccounts(List<Account> accounts);
    void deleteAccount(Long accountId);
    void deleteAccount(Account account);
    void deleteAccounts(List<Account> accounts);
    void deleteAllAccounts();
    Page<Account> getAccounts(int page, int pageSize);
    Page<Account> getAccounts(Pageable pageable);

}
