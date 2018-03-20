package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigurationDiagnosticNoPropertiesTest {

  @Test
  public void noConfig() {
    ConfigurationDiagnostic configurationDiagnostic = new ConfigurationDiagnostic();
    configurationDiagnostic.setContentPath("");
    configurationDiagnostic.runDiagnostics();
    assertTrue(configurationDiagnostic.getErrors().size() > 0);
    assertEquals((Integer)0, configurationDiagnostic.getStatusRating());
    assertEquals("failed", configurationDiagnostic.getStatusText());
  }

}
