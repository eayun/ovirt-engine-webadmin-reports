package org.reports.model;

public class StorageDomainDailyHistory {
	private int storage_domain_id;
	private int available_disk_size_gb;
	private int used_disk_size_gb;

	public int getStorage_domain_id() {
		return storage_domain_id;
	}

	public void setStorage_domain_id(int storage_domain_id) {
		this.storage_domain_id = storage_domain_id;
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
