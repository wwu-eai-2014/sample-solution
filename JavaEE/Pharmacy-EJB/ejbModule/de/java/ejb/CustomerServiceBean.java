package de.java.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.customer.Customer;

@Stateless
public class CustomerServiceBean implements CustomerService {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Collection<Customer> getAllCustomers() {
    return em.createQuery("From Customer", Customer.class).getResultList();
  }

}
