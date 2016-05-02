package org.reports.model;

public class VmInterfaceHourlyHistory {
	private String history_datetime;
	private String vm_interface_name;
	private int receive_rate_percent;
	private int max_receive_rate_percent;
	private int transmit_rate_percent;
	private int max_transmit_rate_percent;

	public String getHistory_datetime() {
		return history_datetime;
	}

	public void setHistory_datetime(String history_datetime) {
		this.history_datetime = history_datetime;
	}
	
	public String getVm_interface_name() {
		return vm_interface_name;
	}
	
	public void setVm_interface_name(String vm_interface_name) {
		this.vm_interface_name = vm_interface_name;
	}
	
	public int getReceive_rate_percent() {
		return receive_rate_percent;
	}

	public void setReceive_rate_percent(int receive_rate_percent) {
		this.receive_rate_percent = receive_rate_percent;
	}

	public int getMax_receive_rate_percent() {
		return max_receive_rate_percent;
	}

	public void setMax_receive_rate_percent(int max_receive_rate_percent) {
		this.max_receive_rate_percent = max_receive_rate_percent;
	}

	public int getTransmit_rate_percent() {
		return transmit_rate_percent;
	}

	public void setTransmit_rate_percent(int transmit_rate_percent) {
		this.transmit_rate_percent = transmit_rate_percent;
	}

	public int getMax_transmit_rate_percent() {
		return max_transmit_rate_percent;
	}

	public void setMax_transmit_rate_percent(int max_transmit_rate_percent) {
		this.max_transmit_rate_percent = max_transmit_rate_percent;
	}

}
