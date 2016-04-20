package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.HostSamplesHistory;

public class HostSamplesHistoryDao extends BaseDao {
	private static HostSamplesHistoryDao instance;
//	
//	public HostSamplesHistoryDao(Connection conn) throws SQLException {
//		super(conn);
//	}

	// 主机在某个小时内的 60 条 cpu / memory 使用率的数据
	public List<HostSamplesHistory> queryCpuByMinutes(String hourOfDay, UUID host_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
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
	
	public List<HostSamplesHistory> queryMemoryByMinutes(String hourOfDay, UUID host_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
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
            instance = new HostSamplesHistoryDao();
            return instance;
        }
        return instance;
	}
}
