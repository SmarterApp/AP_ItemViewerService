package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The Providers diagnostic.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "providers")
class ProvidersDiagnostic extends BaseDiagnostic {

  private static final Logger logger = LoggerFactory.getLogger(ProvidersDiagnostic.class);

  @XmlElement(name = "Itemviewerservice-API-HTTP-status")
  private Integer irisStatus = null;

  @XmlElement(name = "Itemviewerservice-blackbox-HTTP-status")
  private Integer blackBoxStatus = null;

  @XmlElement(name = "Itemviewerservice-wordlisthandler-HTTP-status")
  private Integer wordListHandlerStatus = null;

  @XmlTransient
  private String baseUrl = null;

  /**
   * Instantiates a new Providers diagnostic.
   *
   * @param baseUrl the base application url
   */
  ProvidersDiagnostic(String baseUrl) {
    this.errors = new ArrayList<>();
    this.baseUrl = baseUrl;
  }

  /**
   * Instantiates a new Providers diagnostic.
   */
  /* The empty constructor is here for the XML serializer.
  Your IDE may claim it is not used. It is.
  DO NOT REMOVE! */
  ProvidersDiagnostic() {
  }

  /**
   * Run diagnostics.
   * <p>
   * Validates the Amazon S3 connection, iris is running, the blackbox is running,
   * and the word list handler is running.
   * </p>
   */
  void runDiagnostics() {
    String baseUrl = this.baseUrl;
    validateIris(baseUrl);
    validateBlackbox(baseUrl);
    validateWordListHandler(baseUrl);
    generateStatus();
  }

  private Integer getHttpStatus(URL url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    Integer responseCode = connection.getResponseCode();
    connection.disconnect();
    return responseCode;
  }

  private void validateIris(String baseUrl) {
    try {
      URL url = new URL(baseUrl + "/item/0-0");
      Integer status = getHttpStatus(url);
      if (status == 200 || status == 404) {
        this.irisStatus = 200;
      } else {
        this.irisStatus = status;
        addError("Item viewer service API ");
        logger.error("Item viewer service API returned a failing HTTP status code. HTTP code: "
                + status.toString());
      }
    } catch (IOException e) {
      addError("An I/O error occurred when trying to connect to the item viewer service API. "
              + "Please review the system logs.");
      logger.error("Unable to connect to item viewer service API. Exception: " + e.getMessage());
    }
  }

  private void validateBlackbox(String baseUrl) {
    try {
      URL url = new URL(baseUrl + "/");
      Integer status = getHttpStatus(url);
      if (status != 200) {
        this.blackBoxStatus = status;
        addError("Item viewer service BlackBox dependency returned a non 200 HTTP status code."
                + " HTTP code: " + status.toString());
        logger.error("Item viewer service BlackBox dependency returned a non 200 HTTP status code."
                + " HTTP code: " + status.toString());
      } else {
        this.blackBoxStatus = status;
      }
    } catch (IOException e) {
      addError("An internal I/O error occurred when trying to connect to the BlackBox API "
              + "the item viewer service depends on. Please review the system logs.");
      logger.error("Unable to connect to the blackbox API. Exception: " + e.getMessage());
    }
  }

  private void validateWordListHandler(String baseUrl) {
    try {
      String urlParams = "?bankKey=0&itemKey=0&index=1&TDS_ACCS=TDS_WL_Glossary";
      URL url = new URL(baseUrl + "/Pages/API/WordList.axd/resolve" + urlParams);
      Integer status = getHttpStatus(url);
      if (status == 200 || status == 500) {
        this.wordListHandlerStatus = 200;
      } else {

        this.wordListHandlerStatus = status;
        addError("Item viewer service word list handler dependency returned"
                + " a failing http status code. HTTP code: " + status.toString());
        logger.error("Item viewer service word list handler dependency returned"
                + " a failing http status code. HTTP code: " + status.toString());
      }
    } catch (IOException e) {
      addError("An I/O error occurred when trying to connect to the word list handler API "
              + "the item viewer service depends on. Please review the system logs.");
      logger.error("Unable to connect to the word list handler API. Exception: " + e.getMessage());
    }
  }

  /**
   * Gets iris status.
   *
   * @return the iris status
   */
  public Integer getIrisStatus() {
    return irisStatus;
  }

  /**
   * Sets iris status.
   *
   * @param irisStatus the iris status
   */
  public void setIrisStatus(Integer irisStatus) {
    this.irisStatus = irisStatus;
  }

  /**
   * Gets blackbox status.
   *
   * @return the blackbox status
   */
  public Integer getBlackBoxStatus() {
    return blackBoxStatus;
  }

  /**
   * Sets blackbox status.
   *
   * @param blackBoxStatus the blackbox status
   */
  public void setBlackBoxStatus(Integer blackBoxStatus) {
    this.blackBoxStatus = blackBoxStatus;
  }

  /**
   * Gets word list handler status.
   *
   * @return the word list handler status
   */
  public Integer getWordListHandlerStatus() {
    return wordListHandlerStatus;
  }

  /**
   * Sets word list handler status.
   *
   * @param wordListHandlerStatus the word list handler status
   */
  public void setWordListHandlerStatus(Integer wordListHandlerStatus) {
    this.wordListHandlerStatus = wordListHandlerStatus;
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
