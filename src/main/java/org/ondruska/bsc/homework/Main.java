package org.ondruska.bsc.homework;

import java.util.Scanner;

public class Main {

  /**
   * Expect optional single argument with file name to process.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    PackageDelivery pd = new PackageDelivery();

    if (args.length > 0) {
      pd.load(args[0]);
    }

    pd.startReporting();

    try (Scanner scanner = new Scanner(System.in)) {
      String input;
      while (!(input = scanner.nextLine()).equalsIgnoreCase("quit")) {
        pd.process(input);
      }
    }

    pd.stopReporting();

  }

}
