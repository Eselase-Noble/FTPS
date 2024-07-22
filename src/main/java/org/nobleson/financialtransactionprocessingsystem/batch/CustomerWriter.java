package org.nobleson.financialtransactionprocessingsystem.batch;

import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.models.Customer;
import org.nobleson.financialtransactionprocessingsystem.repositories.CustomerRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerWriter implements ItemWriter<Customer> {

    @Autowired
    private final CustomerRepository customerRepository;

    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        System.out.println("Thread Name: " + Thread.currentThread().getName());
        customerRepository.saveAll(chunk);
    }
}
