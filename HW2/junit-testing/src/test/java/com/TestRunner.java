package com;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;

import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;
import java.util.List;

public class TestRunner {

   public static void test1(){
      final LauncherDiscoveryRequest request =
              LauncherDiscoveryRequestBuilder.request()
                      .selectors(selectClass(TestJUnit.class))
                      .build();

      final Launcher launcher = LauncherFactory.create();
      final SummaryGeneratingListener listener = new SummaryGeneratingListener();

      launcher.registerTestExecutionListeners(listener);
      launcher.execute(request);

      TestExecutionSummary summary = listener.getSummary();
      long testFoundCount = summary.getTestsFoundCount();
      List<Failure> failures = summary.getFailures();
      System.out.println("getTestsSucceededCount() - " + summary.getTestsSucceededCount());
      failures.forEach(failure -> System.out.println("failure - " + failure.getTestIdentifier().getLegacyReportingName()));

   }
   public static void test2(){
      final LauncherDiscoveryRequest request =
              LauncherDiscoveryRequestBuilder.request()
                      .selectors(selectClass(TestForCorrected.class))
                      .build();

      final Launcher launcher = LauncherFactory.create();
      final SummaryGeneratingListener listener = new SummaryGeneratingListener();

      launcher.registerTestExecutionListeners(listener);
      launcher.execute(request);

      TestExecutionSummary summary = listener.getSummary();
      long testFoundCount = summary.getTestsFoundCount();
      List<Failure> failures = summary.getFailures();
      System.out.println("getTestsSucceededCount() - " + summary.getTestsSucceededCount());
      failures.forEach(failure -> System.out.println("failure - " + failure.getTestIdentifier().getLegacyReportingName()));

   }
   public static void main(String[] args) {
      System.out.println("Test For Default:");
      test1();
      System.out.println("Test For Corrected:");
      test2();

    //   System.out.println(result.wasSuccessful());
   }
} 