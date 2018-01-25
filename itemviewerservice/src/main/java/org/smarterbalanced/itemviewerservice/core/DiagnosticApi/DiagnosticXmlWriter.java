package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


/**
 * The type Diagnostic xml writer.
 */
public class DiagnosticXmlWriter {

  private static final Logger logger = LoggerFactory.getLogger(DiagnosticXmlWriter.class);

  /**
   * Generate diagnostic xml string.
   *
   * @param diagnostics Diagnostics API object to serialize into an XML string.
   * @return the XML document as a string.
   * @throws JAXBException If there is an error serializing the diagnostic object into XML.
   */
  public String generateDiagnosticXml(DiagnosticApi diagnostics) throws JAXBException {
    String response;
    try {
      StringWriter writer = new StringWriter();
      JAXBContext jaxbContext = JAXBContext.newInstance(DiagnosticApi.class);
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshaller.marshal(diagnostics, writer);
      response = writer.toString();
    } catch (JAXBException e) {
      logger.warn("error writing diagnostic API XML: " + e.getMessage());
      throw e;
    }
    return response;
  }
}













