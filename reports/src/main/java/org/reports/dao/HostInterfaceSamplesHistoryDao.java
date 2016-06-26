package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.HostInterfaceSamplesHistory;

public class HostInterfaceSamplesHistoryDao extends BaseDao{
	private static HostInterfaceSamplesHistoryDao instance;
	
//	public HostInterfaceSamplesHistoryDao(Connection conn) throws SQLException {
//		super(conn);
//	}

	// 获取一个主机在某个小时内的所有网络接口 ID
	public List<UUID> queryHostInterfaceIdsByHostIdAndPeriod(String startMinute, String endMinute, UUID host_id) throws SQLException{
		List<UUID> hostInterfaceIdsOfOneHost = new ArrayList<UUID>();
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select hish.host_interface_id from host_interface_configuration hic, host_interface_samples_history hish"
				+ " where hic.host_interface_id = hish.host_interface_id"
				+ " and hic.host_id = '" + host_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "' group by hish.host_interface_id;");
		while (rs.next()) {
			hostInterfaceIdsOfOneHost.add(UUID.fromString(rs.getString("host_interface_id")));
		}
		return hostInterfaceIdsOfOneHost;
	}
	
	// 主机在某一个小时内的网络接口的传入/传出的速率
	public List<HostInterfaceSamplesHistory> queryNetworkRateByMinutes(String startMinute, String endMinute, UUID host_interface_id)
			throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI'), receive_rate_percent, transmit_rate_percent"
						+ " from (select *, row_number() over(partition by history_datetime order by history_datetime) as row_number from host_interface_samples_history where host_interface_id = '"
						+ "' host_interface_id) as rows"
					    + " where row_number = 1"
						+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
						+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
						+ "';");
		
		List<HostInterfaceSamplesHistory> lhish = new ArrayList<HostInterfaceSamplesHistory>();
		HostInterfaceSamplesHistory hish = null;
		while (rs.next()) {
			hish = new HostInterfaceSamplesHistory();
			hish.setHistory_datetime(rs.getString("to_char"));
			hish.setReceive_rate_percent(rs.getInt("receive_rate_percent"));
			hish.setTransmit_rate_percent(rs.getInt("transmit_rate_percent"));
			hish.setHost_interface_name(getHostInterfaceNameByHostInterfaceId(host_interface_id));
			lhish.add(hish);
		}
		return lhish;
	}

	public static HostInterfaceSamplesHistoryDao getInstance() throws SQLException {
		if (instance == null) {
			instance = new HostInterfaceSamplesHistoryDao();
			return instance;
		}
		return instance;
	}
}
