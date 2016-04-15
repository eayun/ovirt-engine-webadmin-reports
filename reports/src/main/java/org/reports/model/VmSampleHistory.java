package org.reports.model;

import java.util.Date;
import java.util.UUID;

public class VmSampleHistory {

	private Date history_datetime;
	private UUID vm_id;
	private int cpu_usage_percent;
	private int memory_usage_percent;

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

	public int getCpu_usage_percent() {
		return cpu_usage_percent;
	}

	public void setCpu_usage_percent(int cpu_usage_percent) {
		this.cpu_usage_percent = cpu_usage_percent;
	}

	public int getMemory_usage_percent() {
		return memory_usage_percent;
	}

	public void setMemory_usage_percent(int memory_usage_percent) {
		this.memory_usage_percent = memory_usage_percent;
	}

}
