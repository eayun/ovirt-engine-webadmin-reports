package org.reports.model;

public class HostDailyHistory {
	private String history_datetime;	
	private int memory_usage_percent;
	private int max_memory_usage;
	private int cpu_usage_percent;
	private int max_cpu_usage;
	private int ksm_cpu_percent;
	private int max_ksm_cpu_percent;

	public String getHistory_datetime() {
		return history_datetime;
	}

	public void setHistory_datetime(String history_datetime) {
		this.history_datetime = history_datetime;
	}

	public int getMemory_usage_percent() {
		return memory_usage_percent;
	}

	public void setMemory_usage_percent(int memory_usage_percent) {
		this.memory_usage_percent = memory_usage_percent;
	}

	public int getMax_memory_usage() {
		return max_memory_usage;
	}

	public void setMax_memory_usage(int max_memory_usage) {
		this.max_memory_usage = max_memory_usage;
	}

	public int getCpu_usage_percent() {
		return cpu_usage_percent;
	}

	public void setCpu_usage_percent(int cpu_usage_percent) {
		this.cpu_usage_percent = cpu_usage_percent;
	}

	public int getMax_cpu_usage() {
		return max_cpu_usage;
	}

	public void setMax_cpu_usage(int max_cpu_usage) {
		this.max_cpu_usage = max_cpu_usage;
	}

	public int getKsm_cpu_percent() {
		return ksm_cpu_percent;
	}

	public void setKsm_cpu_percent(int ksm_cpu_percent) {
		this.ksm_cpu_percent = ksm_cpu_percent;
	}

	public int getMax_ksm_cpu_percent() {
		return max_ksm_cpu_percent;
	}

	public void setMax_ksm_cpu_percent(int max_ksm_cpu_percent) {
		this.max_ksm_cpu_percent = max_ksm_cpu_percent;
	}

}
