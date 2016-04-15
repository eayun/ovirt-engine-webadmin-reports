package org.reports.model;

import java.util.UUID;

public class HostInterfaceSamplesHistory {
	private UUID host_interface_id;
	private String host_interface_name;

	private int receive_rate_percent;
	private int transmit_rate_percent;

	public UUID getHost_interface_id() {
		return host_interface_id;
	}

	public void setHost_interface_id(UUID host_interface_id) {
		this.host_interface_id = host_interface_id;
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
