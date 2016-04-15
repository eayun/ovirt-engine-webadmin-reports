package org.reports.model;

import java.util.Date;
import java.util.UUID;

public class HostInterfaceConfiguration {
	private UUID host_interface_id;
	private UUID host_id;
	private Date create_date;
	private Date update_date;
	private Date delete_date;

	public UUID getHost_interface_id() {
		return host_interface_id;
	}

	public void setHost_interface_id(UUID host_interface_id) {
		this.host_interface_id = host_interface_id;
	}

	public UUID getHost_id() {
		return host_id;
	}

	public void setHost_id(UUID host_id) {
		this.host_id = host_id;
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
