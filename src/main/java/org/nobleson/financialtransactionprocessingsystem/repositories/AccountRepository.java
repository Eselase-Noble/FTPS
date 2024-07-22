package org.nobleson.financialtransactionprocessingsystem.repositories;

import org.nobleson.financialtransactionprocessingsystem.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByAccountId(Long accountId);
    boolean existsByAccountNumber(String accountNumber);
}
