package org.reports.model;

import java.util.Date;
import java.util.UUID;

public class VmDisksUsageHourlyHistory {
	private Date history_datetime;
	private UUID vm_id;
	private Double disks_usage;

	public Date getHistory_datetime() {
		return history_datetime;
	}

	public void setHistory_datetime(Date history_datetime) {
		this.history_datetime = history_datetime;
	}

	public UUID getVm_id() {
		return vm_id;
	}

	public void setVm_id(UUID vm_id) {
		this.vm_id = vm_id;
	}

	public Double getDisks_usage() {
		return disks_usage;
	}

	public void setDisks_usage(Double disks_usage) {
		this.disks_usage = disks_usage;
	}

}
