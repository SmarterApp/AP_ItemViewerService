package org.smarterbalanced.itemviewerservice.core.DiagnosticApi.SystemInfo;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The type System memory stats.
 */
@XmlRootElement(name = "memory")
public class SystemMemoryStats {

  @XmlAttribute(name = "totalMemory")
  private String totalMemory;

  @XmlAttribute(name = "availableMemory")
  private String availableMemory;

  @XmlAttribute(name = "swapTotal")
  private String swapTotal;

  @XmlAttribute(name = "swapUsed")
  private String swapUsed;

  @XmlTransient
  private HardwareAbstractionLayer hal;

  /**
   * Instantiates a new System memory stats.
   */
  public SystemMemoryStats() {
    SystemInfo systemInfo = new SystemInfo();
    this.hal = systemInfo.getHardware();
  }

  /**
   * Read the system memory stats and store them in the associated class variables.
   */
  public void generateStats() {
    GlobalMemory memory = hal.getMemory();
    this.availableMemory = FormatUtil.formatBytes(memory.getAvailable());
    this.totalMemory = FormatUtil.formatBytes(memory.getTotal());
    this.swapUsed = FormatUtil.formatBytes(memory.getSwapUsed());
    this.swapTotal = FormatUtil.formatBytes(memory.getSwapTotal());
  }

  /**
   * Gets available memory.
   *
   * @return the available memory
   */
  public String getAvailableMemory() {
    GlobalMemory memory = hal.getMemory();
    this.availableMemory = FormatUtil.formatBytes(memory.getAvailable());
    return this.availableMemory;
  }

  /**
   * Gets total memory.
   *
   * @return the total memory
   */
  public String getTotalMemory() {
    GlobalMemory memory = hal.getMemory();
    this.totalMemory = FormatUtil.formatBytes(memory.getTotal());
    return this.totalMemory;
  }

  /**
   * Gets swap used.
   *
   * @return the swap used
   */
  public String getSwapUsed() {
    GlobalMemory memory = hal.getMemory();
    this.swapUsed = FormatUtil.formatBytes(memory.getSwapUsed());
    return this.swapUsed;
  }

  /**
   * Gets swap total.
   *
   * @return the swap total
   */
  public String getSwapTotal() {
    GlobalMemory memory = hal.getMemory();
    this.swapTotal = FormatUtil.formatBytes(memory.getSwapTotal());
    return this.swapTotal;
  }

}
