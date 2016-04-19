package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.HostInterfaceHourlyHistory;

public class HostInterfaceHourlyHistoryDao extends BaseDao{
	private static HostInterfaceHourlyHistoryDao instance;
//	
//	public HostInterfaceHourlyHistoryDao(Connection conn) throws SQLException {
//		super(conn);
//	}

	// 获取一个主机在某个小时内的所有网络接口 ID
	public List<UUID> queryHostInterfaceIdsByHostIdAndPeriod(String startHour, String endHour, UUID host_id) throws SQLException{
		List<UUID> hostInterfaceIdsOfOneHost = new ArrayList<UUID>();
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select hihh.host_interface_id from host_interface_configuration hic, host_interface_hourly_history hihh"
				+ " where hic.host_interface_id = hihh.host_interface_id"
				+ " and hic.host_id = '" + host_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') < '" + endHour
				+ "' group by hihh.host_interface_id;");
		while (rs.next()) {
			hostInterfaceIdsOfOneHost.add(UUID.fromString(rs.getString("host_interface_id")));
		}
		return hostInterfaceIdsOfOneHost;
	}
	
	// 主机某一天内某几个小时的网络接口传入/传出的速率
	public List<HostInterfaceHourlyHistory> queryNetworkRateByHours(String startHour, String endHour,
			UUID host_interface_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select receive_rate_percent, max_receive_rate_percent, transmit_rate_percent, max_transmit_rate_percent"
						+ " from host_interface_hourly_history where host_interface_id = '" + host_interface_id
						+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') >= '" + startHour
						+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') <= '" + endHour
						+ "' order by history_datetime asc;");
		List<HostInterfaceHourlyHistory> lhihh = new ArrayList<HostInterfaceHourlyHistory>();
		HostInterfaceHourlyHistory hihh = null;
		while (rs.next()) {
			hihh = new HostInterfaceHourlyHistory();
			hihh.setReceive_rate_percent(rs.getInt("receive_rate_percent"));
			hihh.setMax_receive_rate_percent(rs.getInt("max_receive_rate_percent"));
			hihh.setTransmit_rate_percent(rs.getInt("transmit_rate_percent"));
			hihh.setMax_transmit_rate_percent(rs.getInt("max_transmit_rate_percent"));
			hihh.setHost_interface_name(getHostInterfaceNameByHostInterfaceId(host_interface_id));
			lhihh.add(hihh);
		}
		return lhihh;
	}

	public static HostInterfaceHourlyHistoryDao getInstance() throws SQLException {
		if (instance == null) {
			instance = new HostInterfaceHourlyHistoryDao();
			return instance;
		}
		return instance;
	}
}
