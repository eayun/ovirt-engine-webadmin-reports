package org.reports.common;

import java.util.UUID;

public class VmReport {
	private UUID vmId; // vm 的 id
	private String contentViewed;
	private String period; // 查看周期
	private String startingTime; // 起始时间
	private String terminalTime; // 终止时间
	
	public VmReport(UUID vmId, String period, String startingTime, String terminalTime) {
		super();
		this.vmId = vmId;
		this.period = period;
		this.startingTime = startingTime;
		this.terminalTime = terminalTime;
	}

	public UUID getVmId() {
		return vmId;
	}

	public void setVmId(UUID vmId) {
		this.vmId = vmId;
	}

	public String getContentViewed() {
		return contentViewed;
	}

	public void setContentViewed(String contentViewed) {
		this.contentViewed = contentViewed;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(String startingTime) {
		this.startingTime = startingTime;
	}

	public String getTerminalTime() {
		return terminalTime;
	}

	public void setTerminalTime(String terminalTime) {
		this.terminalTime = terminalTime;
	}
}