package org.nobleson.financialtransactionprocessingsystem.controllers;

/**
 * This class is used to handle the customer apis
 * Various end-points are implement
 * This the controller class for the customer
 * @author Nobleson
 * @version 1.0.0
 * @date 03/07/2024
 *
 */


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.models.Customer;
import org.nobleson.financialtransactionprocessingsystem.services.CustomerService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * This class is used to handle the controller rendered by the Customer entity leveraging on the service
 * It handles the APIs generated request needed in the application
 * @author Nobleson
 * @version 1.0.0
 * @date 12/07/2024
 * @date: 12/07/2024
 *
 */


@Controller
@RestController
@RequestMapping("/FTPS/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    private final JobLauncher jobLauncher;

    @Autowired
    private final Job job;

    @PostMapping("/add-customer")
    public ResponseEntity<String> addCustomer(@RequestBody final Customer customer) {

        customerService.addCustomer(customer);

        return new ResponseEntity<>("Customer with ID: " + customer.getCustomerId() + " successfully created", HttpStatus.CREATED);
    }


    @PostMapping("/add-more-customers")
    public ResponseEntity<String> addCustomers(@RequestBody final List<Customer> customers) {

        customerService.addCustomers(customers);
        return new ResponseEntity<>("Customers successfully created", HttpStatus.CREATED);
    }


    @PostMapping("/job/importCustomers")
    public ResponseEntity<String> importCustomersCsvToDB(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("StartAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                 JobInstanceAlreadyCompleteException | JobRestartException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("Customers successfully created", HttpStatus.CREATED);
    }



    @GetMapping("/get-all-customers")
    public ResponseEntity<List<Customer>> getCustomers() {

        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/find-customer/{customerId}")

    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") final Long customerId) {

        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @PutMapping("/update-customer/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable("customerId") final Long customerId) {
        customerService.updateCustomer(customerId);
        return new ResponseEntity<>("Customer with id, "  + customerId + " has been successfully updated", HttpStatus.OK);
    }


    @PutMapping("/update-customer")
    public ResponseEntity<String> updateCustomers(@RequestBody final List<Customer> customers) {
        customerService.updateCustomers(customers);

        return new ResponseEntity<>("Customers successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/delete-customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerIdbhy") final Long customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>("Customer with id " + customerId + " has been deleted", HttpStatus.OK);
    }

    @DeleteMapping("delete-customer")
    public ResponseEntity<String> deleteCustomer(@RequestBody final Customer customer) {
        customerService.deleteCustomer(customer);
        return new ResponseEntity<>("Customer " + customer.getCustomerId() + " has been deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all-customers")
    public ResponseEntity<String> deleteCustomers() {
        customerService.deleteAllCustomers();
        return new ResponseEntity<>("All Customers successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<Customer>> searchCustomers(@RequestParam final String search) {
        return new ResponseEntity<>(customerService.searchCustomers(search), HttpStatus.OK);
    }


}
