package org.ondruska.bsc.homework;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

/**
 * Various tests. Time based are shortened to make test execution faster.
 */
public class MainTest {

  PackageDelivery pd;

  @Before
  public void setUp() {
    pd = new PackageDelivery();
  }

  @Rule
  public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Test
  public void zeroWeight() {
    pd.process("0 00000");
    assertEquals("weight must be a positive number\n", systemErrRule.getLog());
  }

  @Test
  public void negativeWeight() {
    pd.process("-1.0 00000");
    assertEquals("weight must be a positive number\n", systemErrRule.getLog());
  }

  @Test
  public void tooManyDecimals() {
    pd.process("0.1111 00000");
    assertEquals("weight can have up to 3 decimal places\n", systemErrRule.getLog());
  }

  @Test
  public void longPostCode() {
    pd.process("1 123456");
    assertEquals("post code must be exactly 5 digits\n", systemErrRule.getLog());
  }

  @Test
  public void shortPostCode() {
    pd.process("1 1234");
    assertEquals("post code must be exactly 5 digits\n", systemErrRule.getLog());
  }

  @Test
  public void nonDigitPostCode() {
    pd.process("1 ABCDE");
    assertEquals("post code must be exactly 5 digits\n", systemErrRule.getLog());
  }

  @Test
  public void invalidCommand() {
    pd.process("a");
    assertEquals("Invalid input, expecting positive number with up to 3 decimal places and 5 digits separated by space or quit to exit\n", systemErrRule.getLog());
  }

  private static final int TEST_INVERVAL = 5;

  @Test
  public void add() {
    pd.startReporting(TEST_INVERVAL);
    pd.process("1 00000");
    pd.process("2.456 00000");
    try {
      Thread.sleep((TEST_INVERVAL + 1) * 1000);
    } catch (InterruptedException e) {
      System.err.println("interrupted");
    }
    assertEquals("00000 3.456\n", systemOutRule.getLog());
    pd.stopReporting();
  }

  @Test
  public void order() {
    pd.startReporting(TEST_INVERVAL);
    pd.process("0.001 00000");
    pd.process("1 11111");
    pd.process("9 99999");
    pd.process("2 44444");
    pd.process("3 33333");
    pd.process("2 44444");
    try {
      Thread.sleep((TEST_INVERVAL + 1) * 1000);
    } catch (InterruptedException e) {
    }
    assertEquals("99999 9.000\n44444 4.000\n33333 3.000\n11111 1.000\n00000 0.001\n", systemOutRule.getLog());
    pd.stopReporting();
  }

  @Test
  public void load() {
    pd.startReporting(5);
    pd.load("data.txt");
    try {
      Thread.sleep(6 * 1000);
    } catch (InterruptedException e) {
    }
    assertEquals("99999 100099.000\n12345 4.321\n54321 4.321\n00000 3.000\n33333 3.000\n", systemOutRule.getLog());
    pd.stopReporting();
  }

}
