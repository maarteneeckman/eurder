package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.domain.exceptions.CustomerNotUniqueException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class CustomerRepositoryNoDB {

    private Map<UUID, Customer> customers = new HashMap<>();

    public void addCustomer(Customer customer) {
        if(customers.values().stream()
                .anyMatch(cust -> cust.equals(customer))){
            throw new CustomerNotUniqueException("Customer already exists!");
        }
        customers.put(customer.getId(), customer);
    }

    public Customer getCustomer(UUID customerId) {
        if(customers.get(customerId) == null){
            throw new CustomerNotFoundException("Customer does not exist!");
        }
        return customers.get(customerId);
    }

    public Collection<Customer> getCustomers() {
        return customers.values();
    }
}
