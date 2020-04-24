package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseDiagnosticTest {

  private DatabaseDiagnostic databaseDiagnostic;
  private String readOnlyDirectoryPath;

  @Before
  public void setup() {
    String testContentPath = DatabaseDiagnosticTest.class.getResource("/testContent").getFile();
    databaseDiagnostic = new DatabaseDiagnostic(testContentPath);
    readOnlyDirectoryPath = testContentPath + "/readOnly";
  }

  @Test
  public void testDbRead() {
    databaseDiagnostic.dbReadDiagnostics();
    assertEquals((Integer)4, databaseDiagnostic.getStatusRating());
    assertEquals(BaseDiagnostic.convertToStatusText(4), databaseDiagnostic.getStatusText());
    assertTrue(databaseDiagnostic.getContentReadable());
    assertTrue(databaseDiagnostic.getContentExists());
  }

  @Test
  public void testNoContentDirectory() {
    DatabaseDiagnostic invalidDatabaseDiagnostic = new DatabaseDiagnostic(readOnlyDirectoryPath + "/someInvalidPathThatDoesNotExist");
    invalidDatabaseDiagnostic.dbReadDiagnostics();
    invalidDatabaseDiagnostic.generateStatus();
    assertEquals((Integer)0, invalidDatabaseDiagnostic.getStatusRating());
    assertEquals(BaseDiagnostic.convertToStatusText(0), invalidDatabaseDiagnostic.getStatusText());
  }

}
