package de.java.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.java.domain.Drug;

@Remote
public interface DrugService {

  Collection<Drug> getAllDrugs();

}
