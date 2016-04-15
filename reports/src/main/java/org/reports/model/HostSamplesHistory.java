package org.reports.model;

import java.util.UUID;

public class HostSamplesHistory {
	private UUID host_id;
	private int memory_usage_percent;
	private int cpu_usage_percent;
	private int ksm_cpu_percent;

	public UUID getHost_id() {
		return host_id;
	}

	public void setHost_id(UUID host_id) {
		this.host_id = host_id;
	}

	public int getMemory_usage_percent() {
		return memory_usage_percent;
	}

	public void setMemory_usage_percent(int memory_usage_percent) {
		this.memory_usage_percent = memory_usage_percent;
	}

	public int getCpu_usage_percent() {
		return cpu_usage_percent;
	}

	public void setCpu_usage_percent(int cpu_usage_percent) {
		this.cpu_usage_percent = cpu_usage_percent;
	}

	public int getKsm_cpu_percent() {
		return ksm_cpu_percent;
	}

	public void setKsm_cpu_percent(int ksm_cpu_percent) {
		this.ksm_cpu_percent = ksm_cpu_percent;
	}

}
