package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaseDiagnosticTest {



  //base diagnostic
  @Test
  public void testStatusTextLookup() {
    assertEquals(BaseDiagnostic.convertToStatusText(4), "ideal");
    assertEquals(BaseDiagnostic.convertToStatusText(3), "recovering");
    assertEquals(BaseDiagnostic.convertToStatusText(2), "warning");
    assertEquals(BaseDiagnostic.convertToStatusText(1), "degraded");
    assertEquals(BaseDiagnostic.convertToStatusText(0), "failed");
    assertEquals(BaseDiagnostic.convertToStatusText(10), "unknown");
  }

  @Test
  public void testBaseDiagnostic() {
    BaseDiagnostic baseDiagnostic = new BaseDiagnostic();
    baseDiagnostic.addError("Testing adding errors");
    assertTrue(baseDiagnostic.getErrors().size() == 1);
  }

  @Test
  public void testIdealStatusGeneration() {
    BaseDiagnostic baseDiagnostic = new BaseDiagnostic();
    baseDiagnostic.generateStatus();
    assertEquals(baseDiagnostic.getStatusRating(), (Integer) 4);
    assertEquals(baseDiagnostic.getStatusText(), "ideal");
  }

  @Test
  public void testFailStatusGeneration() {
    BaseDiagnostic baseDiagnostic = new BaseDiagnostic();
    baseDiagnostic.addError("Testing error");
    baseDiagnostic.generateStatus();
    assertTrue(baseDiagnostic.getStatusRating() == 0);
    assertEquals(baseDiagnostic.getStatusText(), "failed");
  }

}
