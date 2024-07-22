package org.nobleson.financialtransactionprocessingsystem.batch;

import org.nobleson.financialtransactionprocessingsystem.models.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) throws Exception {

        //Add other logics here


        return item;
    }
}
