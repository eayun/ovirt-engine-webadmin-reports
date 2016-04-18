package org.reports.model;

public class VmInterfaceSamplesHistory {
	private String vm_interface_name;
	private int receive_rate_percent;
	private int transmit_rate_percent;

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

	public int getTransmit_rate_percent() {
		return transmit_rate_percent;
	}

	public void setTransmit_rate_percent(int transmit_rate_percent) {
		this.transmit_rate_percent = transmit_rate_percent;
	}

}