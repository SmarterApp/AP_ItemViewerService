package org.smarterbalanced.itemviewerservice.core.DiagnosticApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarterbalanced.itemviewerservice.core.DiagnosticApi.SystemInfo.FileSystemInfo;
import org.smarterbalanced.itemviewerservice.core.DiagnosticApi.SystemInfo.SystemFsStats;
import org.smarterbalanced.itemviewerservice.core.DiagnosticApi.SystemInfo.SystemMemoryStats;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * Class used to run system diagnostics.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "system")
class SystemDiagnostic extends BaseDiagnostic {

  @XmlTransient
  private static final Logger logger = LoggerFactory.getLogger(SystemDiagnostic.class);

  @XmlElement(name = "memory")
  private SystemMemoryStats memory;

  @XmlElement(name = "fileSystems")
  private SystemFsStats systemFsStats;

  /**
   * Instantiates a new System diagnostic.
   */
  SystemDiagnostic() {
    this.errors = new ArrayList<>();
    this.memory = new SystemMemoryStats();
    this.systemFsStats = new SystemFsStats();
  }

  /**
   * Run the diagnostics.
   */
  void runDiagnostics() {
    this.statusRating = 4;
    Float freeSpaceWarnAmount = 15f;
    this.memory.generateStats();
    this.systemFsStats.generateStats();
    for (FileSystemInfo info : systemFsStats.getFsInfo()) {
      if (info.getPercentFreeSpace() < freeSpaceWarnAmount) {
        this.statusRating = 2;
        addError("File system mounted at " + info.getMountPoint()
                + " has " + info.getPercentFreeSpace().toString()
                + " percent of its space remaining");

        logger.warn("File system mounted at " + info.getMountPoint()
                + " has " + info.getPercentFreeSpace().toString()
                + " percent of its space remaining");
      }
    }
    generateStatus();
  }

  @Override
  void generateStatus() {
    if ((this.errors == null) || (this.errors.size() == 0)) {
      this.errors = null;
      this.statusRating = 4;
      this.statusText = convertToStatusText(this.statusRating);
    } else {
      this.statusRating = 2;
      this.statusText = convertToStatusText(this.statusRating);
    }
  }

}
