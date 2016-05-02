package org.reports.model;

import java.util.Date;
import java.util.UUID;

public class HostInterfaceConfiguration {
	private UUID vm_interface_id;
	private UUID vm_id;
	private Date create_date;
	private Date update_date;
	private Date delete_date;

	public UUID getVm_interface_id() {
		return vm_interface_id;
	}

	public void setVm_interface_id(UUID vm_interface_id) {
		this.vm_interface_id = vm_interface_id;
	}

	public UUID getVm_id() {
		return vm_id;
	}

	public void setVm_id(UUID vm_id) {
		this.vm_id = vm_id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Date getDelete_date() {
		return delete_date;
	}

	public void setDelete_date(Date delete_date) {
		this.delete_date = delete_date;
	}

}
