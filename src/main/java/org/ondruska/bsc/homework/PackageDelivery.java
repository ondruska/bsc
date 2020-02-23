package org.ondruska.bsc.homework;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import com.google.common.io.Files;

/**
 * The package delivery implementation with simple in-memory database.
 */
public class PackageDelivery {

  private static final Pattern POSTCODE_PATTERN = Pattern.compile("\\d{5}");

  private final Database db = new Database();

  private final Timer timer = new Timer();

  /**
   * Load initial data from file as if they were entered directly.
   *
   * @param fileName file name.
   */
  public void load(String fileName) {

    File file = new File(fileName);

    try {
      Files.asCharSource(file, StandardCharsets.ISO_8859_1).forEachLine((line) -> {
        process(line);
      });
    } catch (IOException e) {
      System.err.println("File error");
    }

  }

  /**
   * Process input command. See spec (not copy-pasting).
   *
   * @param command
   */
  public void process(String command) {

    try (Scanner scanner = new Scanner(command)) {

      try {

        BigDecimal weight = scanner.nextBigDecimal();
        String postCode = scanner.next();

        if (BigDecimal.ZERO.compareTo(weight) >= 0) {
          System.err.println("weight must be a positive number");
        } else if (weight.scale() > 3) {
          System.err.println("weight can have up to 3 decimal places");
        } else if (!POSTCODE_PATTERN.matcher(postCode).matches()) {
          System.err.println("post code must be exactly 5 digits");
        } else {
          db.store(postCode, weight);
        }

      } catch (Exception e) {
        System.err.println(
            "Invalid input, expecting positive number with up to 3 decimal places and 5 digits separated by space or quit to exit");
      }

    }

  }

  /**
   * Start reporting output of {@link Database#list} every minute.
   */
  public void startReporting() {

    startReporting(60);

  }

  /**
   * Start reporting defined output of {@link Database#list}.
   *
   * @param seconds report interval
   */
  public void startReporting(int seconds) {

    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        System.out.print(db.list());
      }
    }, 0, seconds * 1000);

  }

  /**
   * Stop reporting.
   */
  public void stopReporting() {

    timer.cancel();

  }

}
