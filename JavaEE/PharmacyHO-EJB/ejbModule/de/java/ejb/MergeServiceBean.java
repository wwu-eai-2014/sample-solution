package de.java.ejb;

import static de.java.ejb.Subsidiary.C_SHARPE;
import static de.java.ejb.Subsidiary.JAVA;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import de.java.domain.Drug;
import de.java.web.drug.data.DrugDto;
import de.java.web.drug.data.DrugResource;

@Stateless
public class MergeServiceBean implements MergeService {

  @EJB
  DrugService drugService;

  DrugResource javaResource;
  DrugResource csharpeResource;

  @PostConstruct
  public void registerRestEasy() {
    RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
    javaResource = new ResteasyClientBuilder().build()
        .target(JAVA.getBaseUri()).proxy(DrugResource.class);
    csharpeResource = new ResteasyClientBuilder().build()
        .target(C_SHARPE.getBaseUri()).proxy(DrugResource.class);
  }

  @Override
  public boolean isInNeedOfMerge() {
    return drugService.getAllDrugs().isEmpty();
  }

  @Override
  public void merge() {
    if (!isInNeedOfMerge()) 
      return;
    
    Map<Integer, DrugDto> drugsAtJava = retrieveDrugsFrom(javaResource);
    Map<Integer, DrugDto> drugsAtCsharpe = retrieveDrugsFrom(csharpeResource);
    
    Set<Integer> pznAtJava = drugsAtJava.keySet();
    Set<Integer> pznAtCsharpe = drugsAtCsharpe.keySet();
    Set<Integer> atJavaOnly = disjoint(pznAtJava, pznAtCsharpe);
    Set<Integer> atCsharpeOnly = disjoint(pznAtCsharpe, pznAtJava);
    Set<Integer> common = intersect(pznAtJava, pznAtCsharpe);
    
    // create at csharpe
    for (Integer pzn : atJavaOnly) {
      DrugDto drugFromJava = drugsAtJava.get(pzn);
      csharpeResource.createDrug(drugFromJava);
      drugService.createDrugLocally(transform(drugFromJava));
    }
    
    // update at csharpe (java takes precendence)
    for (Integer pzn : common) {
      DrugDto drugFromJava = drugsAtJava.get(pzn);
      csharpeResource.updateDrug(pzn, drugFromJava);
      drugService.createDrugLocally(transform(drugFromJava));
    }
    
    // create at java
    for (Integer pzn : atCsharpeOnly) {
      DrugDto drugFromCsharpe = drugsAtCsharpe.get(pzn);
      javaResource.createDrug(drugFromCsharpe);
      drugService.createDrugLocally(transform(drugFromCsharpe));
    }
  }


  private Map<Integer, DrugDto> retrieveDrugsFrom(DrugResource resource) {
    Collection<DrugDto> allDrugs = resource.allDrugs();
    Map<Integer, DrugDto> resultMap = new HashMap<>();
    for (DrugDto d : allDrugs) {
      resultMap.put(d.getPzn(), d);
    }
    return resultMap;
  }

  private Set<Integer> intersect(Set<Integer> first, Set<Integer> second) {
    Set<Integer> result = new HashSet<>();
    for (Integer i : first) {
      if (second.contains(i)) {
        result.add(i);
      }
    }
    return result;
  }

  private Set<Integer> disjoint(Set<Integer> first, Set<Integer> excluded) {
    Set<Integer> result = new HashSet<>(first);
    result.removeAll(excluded);
    return result;
  }

  private Drug transform(DrugDto drugDto) {
    Drug result = new Drug();
    result.setPzn(drugDto.getPzn());
    result.setName(drugDto.getName());
    result.setDescription(drugDto.getDescription());
    return result;
  }

}
