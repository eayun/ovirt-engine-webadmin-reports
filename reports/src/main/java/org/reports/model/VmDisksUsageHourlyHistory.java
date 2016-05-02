package org.reports.model;

public class VmDisksUsageHourlyHistory {
	private String history_datetime;
	private Double disks_usage;

	public String getHistory_datetime() {
		return history_datetime;
	}

	public void setHistory_datetime(String history_datetime) {
		this.history_datetime = history_datetime;
	}
	
	public Double getDisks_usage() {
		return disks_usage;
	}

	public void setDisks_usage(Double disks_usage) {
		this.disks_usage = disks_usage;
	}

}
