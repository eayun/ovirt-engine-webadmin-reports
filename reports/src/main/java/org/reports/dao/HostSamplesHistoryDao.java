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
	public List<HostSamplesHistory> queryCpuByMinutes(String startMinute, String endMinute, UUID host_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI'), cpu_usage_percent"
				+ " from host_samples_history where host_id = '" + host_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "' order by history_datetime asc;");
		List<HostSamplesHistory> lhsh = new ArrayList<HostSamplesHistory>();
		HostSamplesHistory hsh = null;
		while (rs.next()) {
			hsh = new HostSamplesHistory();
			hsh.setCpu_usage_percent(rs.getInt("cpu_usage_percent"));
			lhsh.add(hsh);
		}
		return lhsh;
	}
	
	public List<HostSamplesHistory> queryMemoryByMinutes(String startMinute, String endMinute, UUID host_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI'), memory_usage_percent"
				+ " from host_samples_history where host_id = '" + host_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "' order by history_datetime asc;");
		List<HostSamplesHistory> lhsh = new ArrayList<HostSamplesHistory>();
		HostSamplesHistory hsh = null;
		while (rs.next()) {
			hsh = new HostSamplesHistory();
			hsh.setHistory_datetime(rs.getString("to_char"));
			hsh.setMemory_usage_percent(rs.getInt("memory_usage_percent"));
			lhsh.add(hsh);
		}
		return lhsh;
	}
	
	public List<String> queryHostStartTimeAndEndTimeByMinutes(UUID host_id) throws SQLException{
		Statement stmt0 = Backend.conn.createStatement();
		Statement stmt1 = Backend.conn.createStatement();
		ResultSet startTime = stmt0.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI') from host_samples_history where host_id = '" + host_id
				+ "' order by history_datetime asc limit 1;");
		ResultSet endTime = stmt1.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI') from host_samples_history where host_id = '" + host_id
				+ "' order by history_datetime desc limit 1;");
		List<String> Time = new ArrayList<String>();
		String start_time = null;
		String end_time = null;
		while(startTime.next()){
			start_time = startTime.getString("to_char");
			Time.add(start_time);
		}
		while(endTime.next()){
			end_time = endTime.getString("to_char");
			Time.add(end_time);
		}
		return Time;
	}
	
	public static HostSamplesHistoryDao getInstance() throws SQLException{
		if (instance == null) {
            instance = new HostSamplesHistoryDao();
            return instance;
        }
        return instance;
	}
}
