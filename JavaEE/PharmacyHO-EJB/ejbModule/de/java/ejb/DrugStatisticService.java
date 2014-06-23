package de.java.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.java.ejb.statistic.AggregatedDrugStatistic;

@Remote
public interface DrugStatisticService {

  Collection<AggregatedDrugStatistic> getAllStatistics();

  AggregatedDrugStatistic getStatisticFor(int pzn);

}
