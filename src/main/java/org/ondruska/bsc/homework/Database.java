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
   * @param postCode destination postal code
   * @param weight package weight
   */
  public void store(String postCode, BigDecimal weight) {
    database.compute(postCode, (k, v) -> v == null ? weight : v.add(weight));
  }

  /**
   * Output as per spec. Additionally post codes with same weight are natural
   * ordered as in {@link PostCodeWeight#compareTo(org.ondruska.bsc.homework.PostCodeWeight)
   * }.
   *
   * @return output data
   */
  public String list() {
    
    List<PostCodeWeight> result = new ArrayList<>(database.size());
    
    database.forEach((k, v) -> {
      result.add(new PostCodeWeight(k, v));
    });
    
    result.sort(null);
    
    StringBuffer sb = new StringBuffer();
    result.forEach(sb::append);
    
    return sb.toString();
  }

}
