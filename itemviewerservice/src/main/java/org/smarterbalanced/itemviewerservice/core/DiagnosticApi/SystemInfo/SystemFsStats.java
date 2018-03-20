package org.smarterbalanced.itemviewerservice.core.DiagnosticApi.SystemInfo;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Class used to fetch and store the stats for multiple file systems in an object
 * that can be serialized to XML.
 */
@XmlRootElement(name = "fileSystems")
public class SystemFsStats {


  private List<FileSystemInfo> fsInfo;

  /**
   * Instantiates a new SystemFsStats object.
   */
  public SystemFsStats() {
    SystemInfo systemInfo = new SystemInfo();
    this.fsInfo = new ArrayList<>();
  }

  /**
   * Generate usage statistics for the filesystems connected to the system.
   */
  public void generateStats() {
    SystemInfo systemInfo = new SystemInfo();
    HardwareAbstractionLayer hal = systemInfo.getHardware();
    FileSystem fileSystem = hal.getFileSystem();
    OSFileStore[] fsArray = fileSystem.getFileStores();
    for (OSFileStore fs: fsArray) {
      if (!fs.getType().isEmpty() && fs.getTotalSpace() != 0) {
        fsInfo.add(new FileSystemInfo(fs));
      }
    }
  }

  /**
   * Gets list of FileSystemInfo objects with an entry for each filesystem detected on the computer.
   *
   * @return the list of FileSystemInfo objects
   */
  @XmlElement(name = "filesystem")
  public List<FileSystemInfo> getFsInfo() {
    return fsInfo;
  }

  /**
   * Sets the list of FileSystemInfo objects
   *
   * @param fsInfo List of FileSystemInfo objects to use for this object.
   */
  public void setFsInfo(List<FileSystemInfo> fsInfo) {
    this.fsInfo = fsInfo;
  }
}
