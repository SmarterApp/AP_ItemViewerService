package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The Diagnostic Api class.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "status")
public class DiagnosticApi extends BaseDiagnostic {
  private static final Logger logger = LoggerFactory.getLogger(DiagnosticApi.class);

  @XmlAttribute(name = "unit")
  private static final String unit = "webserver";

  @XmlAttribute(name = "level")
  private Integer diagnosticLevel;

  @XmlAttribute(name = "time")
  private String time;

  @XmlElement(name = "system")
  private SystemDiagnostic systemDiagnostic = null;

  @XmlElement(name = "configuration")
  private ConfigurationDiagnostic configurationDiagnostic = null;

  @XmlElement(name = "database")
  private DatabaseDiagnostic databaseDiagnostic = null;

  @XmlElement(name = "providers")
  private ProvidersDiagnostic providersDiagnostic = null;

  @XmlTransient
  private String baseUrl = null;

  /**
   * Instantiates a new Diagnostic api.
   *
   * @param level   the level
   * @param baseUrl the base url for the application
   */
  public DiagnosticApi(Integer level, String baseUrl) {
    this.diagnosticLevel = level;
    this.baseUrl = baseUrl;
    if (level > 5) {
      this.diagnosticLevel = 5;
    }
    this.time = generateTimestamp();
  }

  /**
   * Instantiates a new Diagnostic api.
   *
   * @throws UnsupportedOperationException The empty constructor only exists for the XML serializer.
   */
  //This empty constructor is required for the Xml serializer
  public DiagnosticApi() {
  }

  /**
   * Creates a UTC timestamp with RFC 3339 format for the current time.
   * @return Current UTC time in RFC 3339 format.
   */
  public static String generateTimestamp() {
    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    time.setTimeZone(TimeZone.getTimeZone("UTC"));
    return time.format(new Date());
  }

  /**
   * Run the diagnostics for the class's diagnostics level and all levels lower.
   */
  public void runDiagnostics() {
    //Intentional fall through the switch statement
    switch (this.diagnosticLevel) {
      case 5:
        this.providersDiagnostic = new ProvidersDiagnostic(this.baseUrl);
        this.providersDiagnostic.runDiagnostics();
        this.databaseDiagnostic = new DatabaseDiagnostic();
        this.databaseDiagnostic.dbReadDiagnostics();
        this.configurationDiagnostic = new ConfigurationDiagnostic();
        this.configurationDiagnostic.runDiagnostics();
        this.systemDiagnostic = new SystemDiagnostic();
        this.systemDiagnostic.runDiagnostics();
        break;
      case 4:
        this.databaseDiagnostic = new DatabaseDiagnostic();
        this.databaseDiagnostic.dbReadDiagnostics();
        this.configurationDiagnostic = new ConfigurationDiagnostic();
        this.configurationDiagnostic.runDiagnostics();
        this.systemDiagnostic = new SystemDiagnostic();
        this.systemDiagnostic.runDiagnostics();
        break;
      case 3:
        this.databaseDiagnostic = new DatabaseDiagnostic();
        this.databaseDiagnostic.dbReadDiagnostics();
        this.configurationDiagnostic = new ConfigurationDiagnostic();
        this.configurationDiagnostic.runDiagnostics();
        this.systemDiagnostic = new SystemDiagnostic();
        this.systemDiagnostic.runDiagnostics();
        break;
      case 2:
        this.configurationDiagnostic = new ConfigurationDiagnostic();
        this.configurationDiagnostic.runDiagnostics();
        this.systemDiagnostic = new SystemDiagnostic();
        this.systemDiagnostic.runDiagnostics();
        break;
      case 1:
        this.systemDiagnostic = new SystemDiagnostic();
        this.systemDiagnostic.runDiagnostics();
        break;
      case 0:
        break;
      default:
        break;
    }
    generateStatus();
  }

  @Override
  void generateStatus() {
    //
    this.statusRating = 4;
    switch (this.diagnosticLevel) {
      case 5:
        if (this.providersDiagnostic.getStatusRating() == 0) {
          this.statusRating = 0;
          break;
        }
        if (this.databaseDiagnostic.getStatusRating() == 0) {
          this.statusRating = 0;
          break;
        }
        if (this.configurationDiagnostic.getStatusRating() == 0) {
          this.statusRating = 0;
          break;
        }
        if ((this.systemDiagnostic.getStatusRating() != 4)) {
          this.statusRating = this.systemDiagnostic.getStatusRating();
        }
        break;
      case 4:
      case 3:
        if (this.databaseDiagnostic.getStatusRating() == 0) {
          this.statusRating = 0;
          break;
        }
        if (this.configurationDiagnostic.getStatusRating() == 0) {
          this.statusRating = 0;
          break;
        }
        if ((this.systemDiagnostic.getStatusRating() != 4)) {
          this.statusRating = this.systemDiagnostic.getStatusRating();
        }
        break;
      case 2:
        if (this.configurationDiagnostic.getStatusRating() == 0) {
          this.statusRating = 0;
          break;
        }
        if ((this.systemDiagnostic.getStatusRating() != 4)) {
          this.statusRating = this.systemDiagnostic.getStatusRating();
        }
        break;
      case 1:
        if ((this.systemDiagnostic.getStatusRating() != 4)) {
          this.statusRating = this.systemDiagnostic.getStatusRating();
        }
        break;
      //Level 0 diagnostics just test to see if the system is up. If it makes it this far it is.
      case 0:
        this.statusRating = 4;
        break;
      default:
        break;
    }
    this.statusText = convertToStatusText(this.statusRating);
  }

  /**
   * Gets unit.
   *
   * @return the unit
   */
  public static String getUnit() {
    return unit;
  }

  /**
   * Gets diagnostic level.
   *
   * @return the diagnostic level
   */
  public Integer getDiagnosticLevel() {
    return diagnosticLevel;
  }

  /**
   * Sets diagnostic level.
   *
   * @param diagnosticLevel the diagnostic level
   */
  public void setDiagnosticLevel(Integer diagnosticLevel) {
    this.diagnosticLevel = diagnosticLevel;
  }

  /**
   * Gets timestamp for the diagnostics.
   *
   * @return the diagnostics timestamp
   */
  public String getTime() {
    return time;
  }

  /**
   * Sets time.
   *
   * @param time the timestamp for the diagnostics
   */
  public void setTime(String time) {
    this.time = time;
  }

  /**
   * Gets system diagnostic.
   *
   * @return the system diagnostic
   */
  public SystemDiagnostic getSystemDiagnostic() {
    return systemDiagnostic;
  }

  /**
   * Sets system diagnostic.
   *
   * @param systemDiagnostic the system diagnostic
   */
  public void setSystemDiagnostic(SystemDiagnostic systemDiagnostic) {
    this.systemDiagnostic = systemDiagnostic;
  }

  /**
   * Gets configuration diagnostic.
   *
   * @return the configuration diagnostic
   */
  public ConfigurationDiagnostic getConfigurationDiagnostic() {
    return configurationDiagnostic;
  }

  /**
   * Sets configuration diagnostic.
   *
   * @param configurationDiagnostic the configuration diagnostic
   */
  public void setConfigurationDiagnostic(ConfigurationDiagnostic configurationDiagnostic) {
    this.configurationDiagnostic = configurationDiagnostic;
  }

  /**
   * Gets database diagnostic object.
   *
   * @return the database diagnostic object
   */
  public DatabaseDiagnostic getDatabaseDiagnostic() {
    return databaseDiagnostic;
  }

  /**
   * Sets database diagnostic object.
   *
   * @param databaseDiagnostic the database diagnostic
   */
  public void setDatabaseDiagnostic(DatabaseDiagnostic databaseDiagnostic) {
    this.databaseDiagnostic = databaseDiagnostic;
  }

  /**
   * Gets providers diagnostic object.
   *
   * @return the providers diagnostic object
   */
  public ProvidersDiagnostic getProvidersDiagnostic() {
    return providersDiagnostic;
  }

  /**
   * Sets providers diagnostic object.
   *
   * @param providersDiagnostic the providers diagnostic object
   */
  public void setProvidersDiagnostic(ProvidersDiagnostic providersDiagnostic) {
    this.providersDiagnostic = providersDiagnostic;
  }

  /**
   * Gets base application url.
   *
   * @return the base application url
   */
  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * Sets base application url.
   *
   * @param baseUrl the base application url
   */
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }
}
