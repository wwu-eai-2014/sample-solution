package de.java.ejb;

import javax.ejb.Remote;

@Remote
public interface MergeService {

  boolean isInNeedOfMerge();

  void merge();

}
