package org.smarterbalanced.itemviewerservice.core.DiagnosticApi.SystemInfo;


import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Class used for fetching file system info and storing it in a format that is serializable to XML.
 */
public class FileSystemInfo {

  private String type;
  private String mountPoint;
  private String freeSpace;
  private String totalSpace;
  private Float percentFreeSpace;

  /**
   * The empty constructor only exsists for the java XML serializer.
   */
  public FileSystemInfo() {
    //This space intentionally left blank
  }

  /**
   * Instantiates a new File system info object.
   *
   * @param fileSystem An OSHI file system object
   */
  public FileSystemInfo(OSFileStore fileSystem) {
    Long free = fileSystem.getUsableSpace();
    Long total = fileSystem.getTotalSpace();
    this.percentFreeSpace = (free.floatValue() / total.floatValue()) * 100f;
    this.type = fileSystem.getType();
    this.mountPoint = fileSystem.getMount();
    this.freeSpace = FormatUtil.formatBytes(free);
    this.totalSpace = FormatUtil.formatBytes(total);
  }

  /**
   * Gets the file system type.
   *
   * @return the file system type
   */
  @XmlAttribute(name = "fsType")
  public String getType() {
    return type;
  }

  /**
   * Sets the file system type.
   *
   * @param type the file system type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets mount point.
   *
   * @return the mount point
   */
  @XmlAttribute(name = "mountPoint")
  public String getMountPoint() {
    return mountPoint;
  }

  /**
   * Sets mount point.
   *
   * @param mountPoint the mount point
   */
  public void setMountPoint(String mountPoint) {
    this.mountPoint = mountPoint;
  }

  /**
   * Gets free space.
   *
   * @return the free space
   */
  @XmlAttribute(name = "freeSpace")
  public String getFreeSpace() {
    return freeSpace;
  }

  /**
   * Sets free space.
   *
   * @param freeSpace the free space
   */
  public void setFreeSpace(String freeSpace) {
    this.freeSpace = freeSpace;
  }

  /**
   * Gets total space.
   *
   * @return the total space
   */
  @XmlAttribute(name = "totalSpace")
  public String getTotalSpace() {
    return totalSpace;
  }

  /**
   * Sets total space.
   *
   * @param totalSpace the total space
   */
  public void setTotalSpace(String totalSpace) {
    this.totalSpace = totalSpace;
  }

  /**
   * Gets percentage of free space.
   *
   * @return the percentage of free space
   */
  @XmlAttribute(name = "percentFreeSpace")
  public Float getPercentFreeSpace() {
    return percentFreeSpace;
  }

  /**
   * Sets percent free space.
   *
   * @param percentFreeSpace the percent free space
   */
  public void setPercentFreeSpace(Float percentFreeSpace) {
    this.percentFreeSpace = percentFreeSpace;
  }
}
