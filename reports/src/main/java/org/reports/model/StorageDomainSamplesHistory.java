package org.reports.model;

public class StorageDomainSamplesHistory {
	private String history_datetime;
	private int available_disk_size_gb;
	private int used_disk_size_gb;

	public String getHistory_datetime() {
		return history_datetime;
	}

	public void setHistory_datetime(String history_datetime) {
		this.history_datetime = history_datetime;
	}
	
	public int getAvailable_disk_size_gb() {
		return available_disk_size_gb;
	}

	public void setAvailable_disk_size_gb(int available_disk_size_gb) {
		this.available_disk_size_gb = available_disk_size_gb;
	}

	public int getUsed_disk_size_gb() {
		return used_disk_size_gb;
	}

	public void setUsed_disk_size_gb(int used_disk_size_gb) {
		this.used_disk_size_gb = used_disk_size_gb;
	}

}
