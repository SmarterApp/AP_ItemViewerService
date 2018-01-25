package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarterbalanced.itemviewerservice.core.SettingsReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Diagnostics for the system configuration.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "configuration")
class ConfigurationDiagnostic extends BaseDiagnostic {

  private static final Logger logger = LoggerFactory.getLogger(ConfigurationDiagnostic.class);

  @XmlElement(name = "content-path")
  private String contentPath = null;

  /**
   * Instantiates a new Configuration diagnostic.
   */
  ConfigurationDiagnostic() {
    this.errors = new ArrayList<>();
  }

  /**
   * Run diagnostics.
   * Validate the iris content path and application properties.
   */
  void runDiagnostics() {
    if (this.errors == null) {
      this.errors = new ArrayList<>();
    }
    validateContentPath();
    generateStatus();
  }

  private void validateContentPath() {
    try {
      String path;
      //Allows manual setting of content path
      if (this.contentPath == null) {
        path = SettingsReader.readIrisContentPath();
      } else {
        path = this.contentPath;
      }

      if (path == null || path.isEmpty()) {
        addError("Settings are not valid.");
      }
    } catch (IOException e) {
      addError("Unable to load settings file to validate settings");
      logger.error("Unable to load settings-mysql.xml to validate settings. Reason: "
              + e.getMessage());
    } catch (URISyntaxException e) {
      addError("Unable to load settings file to validate settings");
      logger.error("Unable to generate URI required to load the settings-mysql.xml. Reason: "
              + e.getMessage());
    } catch (Exception e) {
      addError("Unable to validate configuration.");
      logger.error(e.getMessage());
    }

  }

  public void setContentPath(String path) {
    this.contentPath = path;
  }

}
