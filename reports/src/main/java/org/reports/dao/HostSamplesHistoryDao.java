package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.HostSamplesHistory;

public class HostSamplesHistoryDao extends BaseDao {
	private static HostSamplesHistoryDao instance;
	private static Connection conn;
	
	public HostSamplesHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	// 主机在某个小时内的 60 条 cpu / memory 使用率的数据
	public List<HostSamplesHistory> queryCpuByDays(String hourOfDay, UUID host_id) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select cpu_usage_percent"
				+ " from host_samples_history where host_id = '" + host_id
				+ "' and position('" + hourOfDay + "' in to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS')) > 0"
				+ " order by history_datetime asc;");
		List<HostSamplesHistory> lhsh = new ArrayList<HostSamplesHistory>();
		HostSamplesHistory hsh = null;
		while (rs.next()) {
			hsh = new HostSamplesHistory();
			hsh.setCpu_usage_percent(rs.getInt("cpu_usage_percent"));
			lhsh.add(hsh);
		}
		return lhsh;
	}
	
	public List<HostSamplesHistory> queryMemoryByTime(String hourOfDay, UUID host_id) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select memory_usage_percent"
				+ " from host_samples_history where host_id = '" + host_id
				+ "' and position('" + hourOfDay + "' in to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS')) > 0"
				+ " order by history_datetime asc;");
		List<HostSamplesHistory> lhsh = new ArrayList<HostSamplesHistory>();
		HostSamplesHistory hsh = null;
		while (rs.next()) {
			hsh = new HostSamplesHistory();
			hsh.setMemory_usage_percent(rs.getInt("memory_usage_percent"));
			lhsh.add(hsh);
		}
		return lhsh;
	}
	
	public static HostSamplesHistoryDao getInstance() throws SQLException{
		if (instance == null) {
            instance = new HostSamplesHistoryDao(conn);
            return instance;
        }
        return instance;
	}
}
