package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.HostInterfaceSamplesHistory;

public class HostInterfaceSamplesHistoryDao extends BaseDao{
	private static HostInterfaceSamplesHistoryDao instance;
	private static Connection conn;
	
	public HostInterfaceSamplesHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	// 获取一个主机在某个小时内的所有网络接口 ID
	public List<UUID> queryHostInterfaceIdsByHostIdAndPeriod(String hourOfDay, UUID host_id) throws SQLException{
		List<UUID> hostInterfaceIdsOfOneHost = new ArrayList<UUID>();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select hish.host_interface_id from host_interface_configuration hic, host_interface_samples_history hish"
				+ " where hic.host_interface_id = hish.host_interface_id"
				+ " and hic.host_id = '" + host_id
				+ "' and position('" + hourOfDay + "' in to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS')) > 0"
				+ " group by hish.host_interface_id;");
		while (rs.next()) {
			hostInterfaceIdsOfOneHost.add(UUID.fromString(rs.getString("vm_interface_id")));
		}
		return hostInterfaceIdsOfOneHost;
	}
	
	// 主机在某一个小时内的网络接口的传入/传出的速率
	public List<HostInterfaceSamplesHistory> queryNetworkRateByMinutes(String hourOfDay, UUID host_interface_id)
			throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("select receive_rate_percent, transmit_rate_percent from host_interface_samples_history"
						+ " where host_interface_id = '" + host_interface_id + "' and position('" + hourOfDay
						+ "' in to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS')) > 0"
						+ " order by history_datetime asc;");
		List<HostInterfaceSamplesHistory> lhish = new ArrayList<HostInterfaceSamplesHistory>();
		HostInterfaceSamplesHistory hish = null;
		while (rs.next()) {
			hish = new HostInterfaceSamplesHistory();
			hish.setReceive_rate_percent(rs.getInt("receive_rate_percent"));
			hish.setTransmit_rate_percent(rs.getInt("transmit_rate_percent"));
			hish.setHost_interface_name(getHostInterfaceNameByHostInterfaceId(host_interface_id));
			lhish.add(hish);
		}
		return lhish;
	}

	public static HostInterfaceSamplesHistoryDao getInstance() throws SQLException {
		if (instance == null) {
			instance = new HostInterfaceSamplesHistoryDao(conn);
			return instance;
		}
		return instance;
	}
}
