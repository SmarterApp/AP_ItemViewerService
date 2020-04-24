package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/*import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
*/

/**
 * The type Base diagnostic.
 */
class BaseDiagnostic {

  /**
   * Error messages generated when running diagnostics.
   */
  @XmlElement(name = "error")
  List<String> errors = null;

  /**
   * The Status rating.
   */
  Integer statusRating = null;

  /**
   * The Status text.
   */
  String statusText = null;

  /**
   * Instantiates a new Base diagnostic.
   */
  BaseDiagnostic() {
    this.errors = new ArrayList<>();
  }

  /**
   * Add error.
   *
   * @param errorMessage the error message
   */
  void addError(String errorMessage) {
    this.errors.add(errorMessage);
  }

  /**
   * Generate and set status rating and text based off of errors.
   */
  void generateStatus() {
    if ((this.errors == null) || (this.errors.size() == 0)) {
      this.errors = null;
      this.statusRating = 4;
      this.statusText = convertToStatusText(this.statusRating);
    } else {
      this.statusRating = 0;
      this.statusText = convertToStatusText(this.statusRating);
    }
  }

  /**
   * Lookup the status text that corresponds with the given status rating.
   *
   * @param statusRating the status rating
   * @return the string
   */
  public static String convertToStatusText(Integer statusRating) {
    String statusText;
    switch (statusRating) {
      case 0:
        statusText = "failed";
        break;
      case 1:
        statusText = "degraded";
        break;
      case 2:
        statusText = "warning";
        break;
      case 3:
        statusText = "recovering";
        break;
      case 4:
        statusText = "ideal";
        break;
      default:
        statusText = "unknown";
        break;
    }
    return statusText;
  }

  /**
   * Gets status rating.
   *
   * @return the status rating
   */
  @XmlAttribute(name = "statusRating")
  public Integer getStatusRating() {
    return this.statusRating;
  }

  /**
   * Sets status rating.
   *
   * @param statusRating the status rating
   */
  public void setStatusRating(Integer statusRating) {
    this.statusRating = statusRating;
  }

  /**
   * Gets status text.
   *
   * @return the status text
   */
  @XmlAttribute(name = "statusText")
  public String getStatusText() {
    return statusText;
  }

  /**
   * Sets status text.
   *
   * @param statusText the status text
   */
  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }

  /**
   * Gets errors.
   *
   * @return the errors
   */
  public List<String> getErrors() {
    return this.errors;
  }

}
