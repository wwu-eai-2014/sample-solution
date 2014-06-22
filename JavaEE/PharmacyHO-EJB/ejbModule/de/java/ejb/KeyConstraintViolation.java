package de.java.ejb;

import javax.ejb.EJBException;

public class KeyConstraintViolation extends EJBException {

  private static final long serialVersionUID = 5100335252431433726L;

  public KeyConstraintViolation(String message) {
		super(message);
	}
}
