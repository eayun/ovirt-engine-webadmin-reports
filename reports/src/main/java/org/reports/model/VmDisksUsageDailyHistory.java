package org.reports.model;

public class VmDisksUsageDailyHistory {
	private String history_datetime;
	private String disks_usage;

	public String getHistory_datetime() {
		return history_datetime;
	}

	public void setHistory_datetime(String history_datetime) {
		this.history_datetime = history_datetime;
	}
	
	public String getDisks_usage() {
		return disks_usage;
	}

	public void setDisks_usage(String disks_usage) {
		this.disks_usage = disks_usage;
	}
}
