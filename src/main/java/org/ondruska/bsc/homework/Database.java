package org.ondruska.bsc.homework;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Very simple in-memory database implementation.
 */
public class Database {

  private final ConcurrentMap<String, BigDecimal> database = new ConcurrentHashMap<>();

  /**
   * Store package information.
   * 
   * @param pcw post code and weight
   */
  public void store(PostCodeWeight pcw) {
    database.compute(pcw.getPostCode(), (k, v) -> v == null ? pcw.getWeight() : v.add(pcw.getWeight()));
  }

  /**
   * Output as per spec. Additionally post codes with same weight are natural
   * ordered as in {@link PostCodeWeight#compareTo(org.ondruska.bsc.homework.PostCodeWeight)
   * }.
   *
   * @return output data
   */
  public List<PostCodeWeight> list() {
    
    List<PostCodeWeight> result = new ArrayList<>(database.size());
    
    database.forEach((k, v) -> {
      result.add(new PostCodeWeight(k, v));
    });
    
    result.sort(null);
    
    return result;

  }

}
