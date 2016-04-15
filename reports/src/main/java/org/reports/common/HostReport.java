package org.reports.common;

import java.util.UUID;

public class HostReport {
	private UUID hostId;
	private String contentViewed;
	private String startingTime;
	private String terminalTime;
	
	public HostReport() {
		super();
	}
	
	public HostReport(UUID hostId, String contentViewed, String startingTime, String terminalTime) {
		super();
		this.hostId = hostId;
		this.contentViewed = contentViewed;
		this.startingTime = startingTime;
		this.terminalTime = terminalTime;
	}

	public UUID getHostId() {
		return hostId;
	}

	public void setHostId(UUID hostId) {
		this.hostId = hostId;
	}

	public String getContentViewed() {
		return contentViewed;
	}

	public void setContentViewed(String contentViewed) {
		this.contentViewed = contentViewed;
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
