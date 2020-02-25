package org.ondruska.bsc.homework;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;

/**
 * Post code and weight encapsulation. Immutable.
 */
public class PostCodeWeight implements Comparable<PostCodeWeight> {

  private final String postCode;
  private final BigDecimal weight;

  public PostCodeWeight(String postCode, BigDecimal weight) {
    this.postCode = postCode;
    this.weight = weight;
  }

  public String getPostCode() {
    return postCode;
  }

  public BigDecimal getWeight() {
    return weight;
  }

  private static final NumberFormat DF = new DecimalFormat("0.000");

  @Override
  public String toString() {
    return String.format("%s %s\n", postCode, DF.format(weight));
  }

  @Override
  public int compareTo(PostCodeWeight another) {
    return Comparator.comparing((PostCodeWeight pcw) -> pcw.weight).reversed().thenComparing(pcw -> pcw.postCode).compare(this, another);
  }

}
