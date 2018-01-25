package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ConfigurationDiagnosticValidTest {

  @Before
  public void generateTestProperties() throws IOException {
    String propertiesFile = BaseDiagnosticTest.class.getResource("/itemviewerservice.properties").getFile();
    Properties properties = new Properties();
    OutputStream outputStream = new FileOutputStream(propertiesFile);
    properties.setProperty("S3region", "us-west-2");
    properties.setProperty("S3bucket", "cass-test");
    properties.store(outputStream, null);
    outputStream.close();
  }

  @Test
  public void testGoodConfigurationDiagnostic() {
    ConfigurationDiagnostic configurationDiagnostic = new ConfigurationDiagnostic();
    configurationDiagnostic.runDiagnostics();
    assertEquals((Integer)4, configurationDiagnostic.getStatusRating());
    assertEquals(configurationDiagnostic.getStatusText(), "ideal");
    assertEquals(configurationDiagnostic.getErrors(), null);
  }

  @After
  public void cleanupTestConfig() throws IOException {
    String propertiesFile = BaseDiagnosticTest.class.getResource("/itemviewerservice.properties").getFile();
    Properties properties = new Properties();
    OutputStream outputStream = new FileOutputStream(propertiesFile);
    properties.remove("S3region");
    properties.remove("S3bucket");
    properties.store(outputStream, null);
    outputStream.close();
  }
}
