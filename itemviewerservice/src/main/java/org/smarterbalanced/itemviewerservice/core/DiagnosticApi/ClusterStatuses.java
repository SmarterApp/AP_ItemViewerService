package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the diagnostic status of all instances of the Item Viewer Service in an AWS ECS
 * cluster.
 */
@XmlRootElement(name = "status")
public class ClusterStatuses {

  @XmlAttribute
  private Integer statusRating;

  @XmlAttribute
  private String statusText;

  @XmlAttribute(name = "time")
  private String time;

  @XmlElement(name = "status")
  private List<DiagnosticApi> clusterStatuses;

  /* DO NOT REMOVE
     This empty constructor is used by the xml serializer.
   */
  ClusterStatuses() {
    //This space intentionally left blank
  }

  ClusterStatuses(Integer rating, List<DiagnosticApi> statuses) {
    this.statusRating = rating;
    this.statusText = BaseDiagnostic.convertToStatusText(rating);
    this.clusterStatuses = statuses;
    this.time = DiagnosticApi.generateTimestamp();
  }
}
