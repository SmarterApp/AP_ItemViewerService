package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;


import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClient;
import com.amazonaws.services.ecs.model.ContainerInstance;
import com.amazonaws.services.ecs.model.DescribeContainerInstancesRequest;
import com.amazonaws.services.ecs.model.DescribeContainerInstancesResult;
import com.amazonaws.services.ecs.model.ListContainerInstancesRequest;
import com.amazonaws.services.ecs.model.ListContainerInstancesResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DiagnosticManager {
  private static final Logger logger = LoggerFactory.getLogger(DiagnosticManager.class);

  public static String localDiagnosticStatus(Integer level, String baseUrl) throws JAXBException{
    String response;
    DiagnosticApi diagnosticApi = new DiagnosticApi(level, baseUrl);
    diagnosticApi.runDiagnostics();
    DiagnosticXmlWriter xmlWriter = new DiagnosticXmlWriter();
    try {
      response = xmlWriter.generateDiagnosticXml(diagnosticApi);
    } catch (JAXBException e) {
      logger.error("Failed to generate diagnostic XML response: " + e.getMessage());
      throw e;
    }
    return response;
  }

  /**
   * Look up the instances in an ECR cluster
   *
   * @param awsCluster
   * @return EC2 instance Ids.
   */
  private static List<String> getEc2InstanceIds(String awsCluster, String awsRegion) {
    AmazonECSClient ecsClient = new AmazonECSClient();
    ecsClient.setRegion(RegionUtils.getRegion(awsRegion));
    List<String> instanceIds = new LinkedList<>();
    try {
      ListContainerInstancesRequest req = new ListContainerInstancesRequest();
      req.setCluster(awsCluster);
      List<String> arns = ecsClient.listContainerInstances(req).getContainerInstanceArns();
      DescribeContainerInstancesRequest request = new DescribeContainerInstancesRequest();
      request.setCluster(awsCluster);
      request.setContainerInstances(arns);
      DescribeContainerInstancesResult result = ecsClient.describeContainerInstances(request);
      for (ContainerInstance instance: result.getContainerInstances()) {
        if(instance.getRunningTasksCount() > 0) {
          instanceIds.add(instance.getEc2InstanceId());
        }
      }
    } catch (Exception e){
      logger.error("Unable to list Amazon Container ARNs: " + e.getMessage());
    }

    return instanceIds;
  }

  /**
   * List the public IPS for ec2 instances
   * @param ec2InstanceIds
   * @return
   */
  private static List<String> getEc2InstanceIPs(List<String> ec2InstanceIds, String awsRegion) {
    List<String> instanceIPs = new LinkedList<>();
    AmazonEC2Client ec2Client = new AmazonEC2Client();
    ec2Client.setRegion(RegionUtils.getRegion(awsRegion));
    DescribeInstancesRequest ec2Request = new DescribeInstancesRequest();
    ec2Request.setInstanceIds(ec2InstanceIds);
    DescribeInstancesResult result = ec2Client.describeInstances(ec2Request);
    for(Reservation reservation: result.getReservations()) {
      for(Instance instance: reservation.getInstances()) {
        String ip = instance.getPublicIpAddress();
        if( (ip != null) && !ip.isEmpty()) {
          instanceIPs.add(instance.getPublicIpAddress());
        }
      }
    }
    return instanceIPs;
  }

  public static List<Document> getStatuses(List<String> instanceIPs, Integer level) throws IOException,
          ParserConfigurationException {
    List<Document> statuses = new LinkedList<>();
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    for (String ip: instanceIPs) {
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpGet get = new HttpGet("http://" + ip + "/statusLocal?level=" + level.toString());
      CloseableHttpResponse response = httpclient.execute(get);
      try {
        Integer status = response.getStatusLine().getStatusCode();
        if (status == 200) {
          HttpEntity entity = response.getEntity();
          InputStream contentStream = entity.getContent();
          Document xml = documentBuilder.parse(contentStream);
          contentStream.close();
          statuses.add(xml);
          EntityUtils.consume(entity);
        } else {
          //The item viewer service is not running properly on this instance.
          logger.info("Diagnostics report the instance with IP: " + ip
                  + " is returning a non 200 status code. Code: " + status.toString());

          //TODO: add a failure on the final status
        }
      } catch (SAXException e) {
        logger.warn("Unable to deserialize XML from item viewer service diagnostics");
      }
      finally {
        response.close();
      }
    }
    return statuses;
  }

  private static List<DiagnosticApi> deserializeDiagnosticXml(List<Document> xmlDocs) throws Exception {
    List<DiagnosticApi> diagnosticResults = new LinkedList<>();
    JAXBContext jc = JAXBContext.newInstance(DiagnosticApi.class);
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    for (Document doc : xmlDocs) {
      DiagnosticApi api = (DiagnosticApi) unmarshaller.unmarshal(doc);
      diagnosticResults.add(api);
    }

    return diagnosticResults;
  }


  public static String diagnosticStatuses(Integer level) throws Exception {
    if(level > 5) {
      level = 5;
    }
    URL resource = DiagnosticManager.class.getResource("/settings-mysql.xml");
    String awsRegion;
    String awsCluster;
    String response = null;
    //Get the Aws region and cluster from the settings.
    try {
      InputStream in = new FileInputStream(resource.getPath());
      Properties props = new Properties();
      props.loadFromXML(in);
      in.close();
      awsRegion = props.getProperty("AwsRegion");
      awsCluster = props.getProperty("AwsClusterName");
    } catch (Exception e) {
      logger.error("Unable to read Aws information from settings. " + e.getMessage());
      throw e;
    }

    List<String> instanceIds = getEc2InstanceIds(awsCluster, awsRegion);
    List<String> instanceIPs = getEc2InstanceIPs(instanceIds, awsRegion);

    List<Document> docs = getStatuses(instanceIPs, level);
    Integer statusLevel = 0;
    for(Document doc : docs) {
      //Root element should have the status rating on it.
      Element el = doc.getDocumentElement();
      Integer elementStatus = Integer.parseInt(el.getAttribute("statusRating"));
      if(elementStatus < 5) {
        statusLevel = (statusLevel < elementStatus) ? statusLevel : elementStatus;
      }
    }

    List<DiagnosticApi> statuses = deserializeDiagnosticXml(docs);

    ClusterStatuses clusterStatuses = new ClusterStatuses(statusLevel, statuses);

    try {
      StringWriter writer = new StringWriter();
      JAXBContext jaxbContext = JAXBContext.newInstance(ClusterStatuses.class);
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshaller.marshal(clusterStatuses, writer);
      response = writer.toString();
    } catch (JAXBException e) {
      logger.warn("error writing diagnostic API XML: " + e.getMessage());
      throw e;
    }

    return response;

  }
}
