package org.reports.model;

public class HostInterfaceSamplesHistory {
	private String history_datetime;
	private String host_interface_name;
	private int receive_rate_percent;
	private int transmit_rate_percent;
	
	public String getHistory_datetime() {
		return history_datetime;
	}

	public void setHistory_datetime(String history_datetime) {
		this.history_datetime = history_datetime;
	}
	
	public String getHost_interface_name() {
		return host_interface_name;
	}
	
	public void setHost_interface_name(String host_interface_name) {
		this.host_interface_name = host_interface_name;
	}

	public int getReceive_rate_percent() {
		return receive_rate_percent;
	}

	public void setReceive_rate_percent(int receive_rate_percent) {
		this.receive_rate_percent = receive_rate_percent;
	}

	public int getTransmit_rate_percent() {
		return transmit_rate_percent;
	}

	public void setTransmit_rate_percent(int transmit_rate_percent) {
		this.transmit_rate_percent = transmit_rate_percent;
	}

}
