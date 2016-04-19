package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.HostHourlyHistory;

public class HostHourlyHistoryDao extends BaseDao {
	private static HostHourlyHistoryDao instance;
	
//	public HostHourlyHistoryDao(Connection conn) throws SQLException {
//		super(conn);
//	}
	// 主机在一天的某几个小时内的 cpu & memory 的数据。
	public List<HostHourlyHistory> queryCpuByHours(String startHour, String endHour, UUID host_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select cpu_usage_percent, max_cpu_usage from host_hourly_history"
				+ " where host_id = '" + host_id + "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') >= '"
				+ startHour + "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') <= '" + endHour
				+ "' order by history_datetime asc;");
		List<HostHourlyHistory> lhhh = new ArrayList<HostHourlyHistory>();
		HostHourlyHistory hhh = null;
		while (rs.next()) {
			hhh = new HostHourlyHistory();
			hhh.setCpu_usage_percent(rs.getInt("cpu_usage_percent"));
			hhh.setMax_cpu_usage(rs.getInt("max_cpu_usage"));
			lhhh.add(hhh);
		}
		return lhhh;
	}

	public List<HostHourlyHistory> queryMemoryByHours(String startHour, String endHour, UUID host_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select memory_usage_percent, max_memory_usage" + " from host_hourly_history where host_id = '" + host_id
						+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') >= '" + startHour
						+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') <= '" + endHour
						+ "' order by history_datetime asc;");
		List<HostHourlyHistory> lhhh = new ArrayList<HostHourlyHistory>();
		HostHourlyHistory hhh = null;
		while (rs.next()) {
			hhh = new HostHourlyHistory();
			hhh.setMemory_usage_percent(rs.getInt("memory_usage_percent"));
			hhh.setMax_memory_usage(rs.getInt("max_memory_usage"));
			lhhh.add(hhh);
		}
		return lhhh;
	}

	public static HostHourlyHistoryDao getInstance() throws SQLException {
		if (instance == null) {
			instance = new HostHourlyHistoryDao();
			return instance;
		}
		return instance;
	}
}
