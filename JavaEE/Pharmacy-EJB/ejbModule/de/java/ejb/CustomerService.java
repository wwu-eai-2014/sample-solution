package de.java.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.java.domain.customer.Customer;

@Remote
public interface CustomerService {
  
  Customer getCustomer(long id);

  Collection<Customer> getAllCustomers();

  Customer createCustomer(Customer newCustomer);

  Customer update(long id, String telephoneNumber, String address);

}
