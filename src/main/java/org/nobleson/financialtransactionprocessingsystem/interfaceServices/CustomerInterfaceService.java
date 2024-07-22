package org.nobleson.financialtransactionprocessingsystem.interfaceServices;

import org.nobleson.financialtransactionprocessingsystem.models.Customer;

import java.util.List;


public interface CustomerInterfaceService {

    Customer addCustomer(Customer customer);
    List<Customer> addCustomers(List<Customer> customers);
    List<Customer> getAllCustomers();
    Customer getCustomer(Long id);
    Customer updateCustomer(Long customerId);
    List<Customer> updateCustomers(List<Customer> customers);
    void deleteCustomer(Long id);
    void deleteCustomer(Customer customer);
    void deleteAllCustomers();
}
