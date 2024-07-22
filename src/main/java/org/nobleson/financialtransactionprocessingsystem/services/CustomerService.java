package org.nobleson.financialtransactionprocessingsystem.services;

import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.interfaceServices.CustomerInterfaceService;
import org.nobleson.financialtransactionprocessingsystem.models.Customer;
import org.nobleson.financialtransactionprocessingsystem.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This class handles the business logic and services of the customer entity
 * It is part of the Financial Project
 * @author Nobleson
 * @version 1.0.0
 * @date: 03/07/2024
 */


@Transactional
@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerInterfaceService {
    private final CustomerRepository customerRepository;
    private final Map<Long, Customer> customerMap = new ConcurrentHashMap<>();
    private final Map<Long, Customer> customerHashMap = new HashMap<>();
    private final Map<Long, Customer> customerLinkedHashMap = new LinkedHashMap<>();
    private final Map<Long, Customer> customerHashtable = new Hashtable<>();


    /**
     * This method is used to add a new customer
     * @param customer
     * @return
     */

    @Transactional
    @Override
    public Customer addCustomer(Customer customer) {


        if (customerRepository.existsByEmail(customer.getEmail())){
            throw new RuntimeException("\"Customer with email, \" +customer.getEmail() + \" already exists\"");
        }

        long endTime  = System.nanoTime();


        return customerRepository.save(customer);

    }

    /**
     * This method is used to add more than one customer
     * @param customers
     * @return
     */
    @Transactional
    @Override
    public List<Customer> addCustomers(List<Customer> customers) {
        if (customers == null || customers.isEmpty() ){
            throw new IllegalArgumentException("Customers list cannot be empty");
        }

        for (Customer customer : customers) {
            if (customerRepository.existsByEmail(customer.getEmail())){
                throw new RuntimeException("Customer with email, " +customer.getEmail() + " already exists");
            }

        }

        return customerRepository.saveAll(customers);
    }


    /**
     * This method is responsible for querying all the customers in the database
     * SELECT * FROM CUSTOMER;
     * @return
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * This method is used to get a particular customer based on the customer id
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Customer getCustomer(Long id) {
        Customer customer = customerRepository.findCustomerByCustomerId(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer;
    }

    /**
     * This method is used to handle the update for a single customer
     * @param customerId
     * @return
     */
    @Override
    public Customer updateCustomer(Long customerId) {
        Customer updatedCustomer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerRepository.save(updatedCustomer);
    }

    /**
     * This method is used to handle one or more customers
     * @param customers
     * @return
     */

    @Override
    public List<Customer> updateCustomers(List<Customer> customers) {
        //Check whether the list is empty or null
        if (customers == null || customers.isEmpty() ){
            throw new IllegalArgumentException("Customers list cannot be empty");
        }

        // Check if a customer already exist by looping through the list
        for (Customer customer : customers) {
            if (!customerRepository.existsById(customer.getCustomerId())) {
                throw new RuntimeException("Customer does not exists");
            }

            //Save the new updated user
            customerRepository.save(customer);

            //add the new customer to the list
            customers.add(customer);
        }


        return customers;
    }

    /**
     * This method is used to delete a customer based on the customer id
     * @param id
     */
    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        //Check if the customer exist
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer does not exists");
        }
        //delete the customer
        customerRepository.deleteById(id);

    }

    @Transactional
    @Override
    public void deleteCustomer(Customer customer) {

        if (!customerRepository.existsById(customer.getCustomerId())) {
            throw new RuntimeException("Customer does not exists");
        }
        customerRepository.delete(customer);
    }

    @Override
    public void deleteAllCustomers() {

        customerRepository.deleteAll();
    }


    //SEARCH CUSTOMER
    //PERFORMING SEARCH USING DIFFERENT SEARCH ALGORITHMS
    public Collection<Customer> searchCustomers(String keyword) {

        List<Customer> customers =  getAllCustomers();

        for (Customer customer : customers) {
            customerMap.put(customer.getCustomerId(), customer);
            System.out.println(customer.getCustomerId());
        }

        return customerMap.values().stream()
                .filter(customer->customer.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getSurname().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getOtherName().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getAddress().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getCustomerId().toString().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Collection<Customer> searchCustomer(String keyword){

        List<Customer> customers =  getAllCustomers();

        for (Customer customer : customers) {
            customerHashMap.put(customer.getCustomerId(), customer);
            System.out.println(customer.getCustomerId());
        }


        return customerHashMap.values()
                .stream()
                .filter(customer->customer.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getSurname().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getOtherName().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getAddress().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Collection<Customer> searchCustomerHash(String keyword){


        List<Customer> customers =  getAllCustomers();

        for (Customer customer : customers) {
            customerHashtable.put(customer.getCustomerId(), customer);
            System.out.println(customer.getCustomerId());
        }


        return customerHashtable.values()
                .stream()
                .filter(customer->customer.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getSurname().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getOtherName().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getAddress().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Collection<Customer> searchCustomerLinked(String keyword){

        List<Customer> customers =  getAllCustomers();

        for (Customer customer : customers) {
            customerLinkedHashMap.put(customer.getCustomerId(), customer);
            System.out.println(customer.getCustomerId());
        }

        return customerLinkedHashMap.values()
                .stream()
                .filter(customer->customer.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getSurname().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getOtherName().toLowerCase().contains(keyword.toLowerCase()) ||
                        customer.getAddress().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
