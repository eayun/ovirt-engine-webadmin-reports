package org.reports.model;

public class VmHourlyHistory {
	private String history_datetime;
	private int cpu_usage_percent;
	private int max_cpu_usage;
	private int memory_usage_percent;
	private int max_memory_usage;

	public String getHistory_datetime() {
		return history_datetime;
	}

	public void setHistory_datetime(String history_datetime) {
		this.history_datetime = history_datetime;
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

}
