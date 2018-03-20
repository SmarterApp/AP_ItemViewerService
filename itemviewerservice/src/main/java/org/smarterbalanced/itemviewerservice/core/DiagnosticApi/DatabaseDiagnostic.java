package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarterbalanced.itemviewerservice.core.SettingsReader;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The Class use for database diagnostics.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "database")
class DatabaseDiagnostic extends BaseDiagnostic {

  private static final Logger logger = LoggerFactory.getLogger(DatabaseDiagnostic.class);

  @XmlElement(name = "db-exists")
  private Boolean contentExists;

  @XmlElement(name = "db-accessible")
  private Boolean contentReadable;

  @XmlTransient
  private String contentPath;

  /**
   * Instantiates a new Database diagnostic object.
   */
  DatabaseDiagnostic() {
    this.errors = new ArrayList<>();
    try {
      this.contentPath = SettingsReader.readIrisContentPath();
    } catch (Exception e) {
      addError("Unable to load configuration file required to connect to content database.");
      addError("No further diagnostics can be run on the content database until the configuration"
              + " is corrected.");
      logger.error("Unable to load configuration file required to connect to content database.");
    }
  }

  /**
   * Constructs a new database diagnostic to use the provided content path instead of the content
   * path specified in the settings.
   * @param contentPath Local content path to use
   */
  DatabaseDiagnostic(String contentPath) {
    this.contentPath = contentPath;
  }

  /**
   * Validate that the specified content directory exists, is readable,
   * and contains content.
   */
  void dbReadDiagnostics() {
    if (this.contentPath == null) {
      return;
    }

    try {
      File dir = new File(this.contentPath);

      if (!dir.exists()) {
        addError("No content");
        logger.error("The content directory specified in settings-mysql.xml does not exist.");
        return;
      }

      if (!dir.isDirectory()) {
        this.contentExists = false;
        addError("No content");
        logger.error("The content directory specified in settings-mysql.xml "
                + "is not a directory.");
      } else {
        this.contentExists = true;
      }

      if (!dir.canRead()) {
        this.contentReadable = false;
        addError("Unable to read content");
        logger.error("The content directory specified in settings-mysql.xml is not readable");
        return;
      } else {
        this.contentReadable = true;
      }

      File[] dirContents = dir.listFiles();
      if (dirContents != null && dirContents.length <= 0) {
        addError("No content");
        logger.error("The content directory specified in settings-mysql.xml is empty");
      }
    } catch (NullPointerException e) {
      addError("No content");
      logger.error("Unable to open content directory specified in settings-mysql.xml");
    } catch (Exception e) {
      addError("No content");
      logger.error("The content path specified in settings-mysql.xml is invalid.");
    }
    generateStatus();
  }

  /**
   * Gets db exists.
   *
   * @return the db exists
   */
  public boolean getContentExists() {
    return contentExists;
  }

  /**
   * Gets if the content is readable.
   *
   * @return is the content readable
   */
  public Boolean getContentReadable() {
    return contentReadable;
  }
}
