package org.nobleson.financialtransactionprocessingsystem.repositories;

import org.nobleson.financialtransactionprocessingsystem.models.Customer;
import org.nobleson.financialtransactionprocessingsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer getCustomerByEmail(String username);
    Optional<Customer> findCustomerByCustomerId(Long customerID);
    boolean existsByEmail(String email);
//    Map<Long, Customer> findAll();
}
