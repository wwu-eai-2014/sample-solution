package de.java.ejb.statistic;

import java.util.Collection;

import javax.ejb.Remote;

@Remote
public interface DrugStatisticService {

  Collection<AggregatedDrugStatistic> getAllStatistics();

  AggregatedDrugStatistic getStatisticFor(int pzn);

}
