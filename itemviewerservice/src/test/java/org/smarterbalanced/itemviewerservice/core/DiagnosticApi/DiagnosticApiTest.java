package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DiagnosticApiTest {
  DiagnosticApi diagnosticApi;

  @Before
  public void setup() {
    Integer diagnosticLevel = 5;
    diagnosticApi = new DiagnosticApi(diagnosticLevel, "testURL");
    SystemDiagnostic systemDiagnostic = new SystemDiagnostic();
    systemDiagnostic.setStatusRating(4);
    systemDiagnostic.setStatusText("ideal");

    ConfigurationDiagnostic configurationDiagnostic = new ConfigurationDiagnostic();
    configurationDiagnostic.setStatusRating(4);
    configurationDiagnostic.setStatusText("ideal");

    DatabaseDiagnostic databaseDiagnostic = new DatabaseDiagnostic("/testContent");
    databaseDiagnostic.setStatusRating(4);
    databaseDiagnostic.setStatusText("ideal");

    List<String> testPackages = new ArrayList<>();
    testPackages.add("package1.zip");
    testPackages.add("package2.zip");

    ProvidersDiagnostic providersDiagnostic = new ProvidersDiagnostic("/testContent");
    providersDiagnostic.setStatusRating(4);
    providersDiagnostic.setStatusText(BaseDiagnostic.convertToStatusText(4));
    providersDiagnostic.setBlackBoxStatus(200);
    providersDiagnostic.setIrisStatus(200);
    providersDiagnostic.setWordListHandlerStatus(200);

    diagnosticApi.setSystemDiagnostic(systemDiagnostic);
    diagnosticApi.setConfigurationDiagnostic(configurationDiagnostic);
    diagnosticApi.setDatabaseDiagnostic(databaseDiagnostic);
    diagnosticApi.setProvidersDiagnostic(providersDiagnostic);
  }

  @Test
  public void testIdealResults() {
    diagnosticApi.generateStatus();
    assertEquals(diagnosticApi.getStatusRating(), (Integer)4);
    assertEquals(diagnosticApi.getStatusText(), BaseDiagnostic.convertToStatusText(4));

  }

  @Test
  public void testFailedResults() {
    diagnosticApi.getProvidersDiagnostic().setStatusRating(0);
    diagnosticApi.getProvidersDiagnostic().setStatusText("failed");
    diagnosticApi.generateStatus();
    assertEquals(diagnosticApi.getStatusRating(), (Integer)0);
    assertEquals(diagnosticApi.getStatusText(), BaseDiagnostic.convertToStatusText(0));
  }

}
