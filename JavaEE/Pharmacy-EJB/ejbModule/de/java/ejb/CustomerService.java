package de.java.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.java.domain.customer.Customer;

@Remote
public interface CustomerService {

  Collection<Customer> getAllCustomers();

}
