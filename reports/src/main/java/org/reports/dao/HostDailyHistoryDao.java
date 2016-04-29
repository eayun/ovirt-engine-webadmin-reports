package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.HostDailyHistory;

public class HostDailyHistoryDao extends BaseDao {
	private static HostDailyHistoryDao instance;
	
//	public HostDailyHistoryDao(Connection conn) throws SQLException {
//		super(conn);
//	}
	
	public List<HostDailyHistory> queryCpuByDays(String startDate, String endDate, UUID host_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select cpu_usage_percent, max_cpu_usage"
				+ " from host_daily_history where host_id = '" + host_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate
				+ "' order by history_datetime asc;");
		List<HostDailyHistory> lhdh = new ArrayList<HostDailyHistory>();
		HostDailyHistory hdh = null;
		while (rs.next()) {
			hdh = new HostDailyHistory();
			hdh.setCpu_usage_percent(rs.getInt("cpu_usage_percent"));
			hdh.setMax_cpu_usage(rs.getInt("max_cpu_usage"));
			lhdh.add(hdh);
		}
		return lhdh;
	}
	
	// 获取虚拟机一周，一月，一个季度，一年的 Memory 使用率的数据
	public List<HostDailyHistory> queryMemoryByDays(String startDate, String endDate, UUID host_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select memory_usage_percent, max_memory_usage"
				+ " from host_daily_history where host_id = '" + host_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate
				+ "' order by history_datetime asc;");
		List<HostDailyHistory> lhdh = new ArrayList<HostDailyHistory>();
		HostDailyHistory hdh = null;
		while (rs.next()) {
			hdh = new HostDailyHistory();
			hdh.setMemory_usage_percent(rs.getInt("memory_usage_percent"));
			hdh.setMax_memory_usage(rs.getInt("max_memory_usage"));
			lhdh.add(hdh);
		}
		return lhdh;
	}
	
	public List<String> queryHostStartTimeAndEndTimeByDays(UUID host_id) throws SQLException{
		Statement stmt0 = Backend.conn.createStatement();
		Statement stmt1 = Backend.conn.createStatement();
		ResultSet startTime = stmt0.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD') from vm_daily_history where host_id = '" + host_id
				+ "' order by history_datetime asc limit 1;");
		ResultSet endTime = stmt1.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD') from vm_daily_history where host_id = '" + host_id
				+ "' order by history_datetime desc limit 1;");
		List<String> Time = new ArrayList<String>();
		Date start_time = null;
		Date end_time = null;
		while(startTime.next()){
			start_time = startTime.getDate("history_datetime");
			Time.add((new SimpleDateFormat("yyyy-MM-dd")).format(start_time));
		}
		while(endTime.next()){
			end_time = endTime.getDate("history_datetime");
			Time.add((new SimpleDateFormat("yyyy-MM-dd")).format(end_time));
		}
		return Time;
	}
	
	public static HostDailyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new HostDailyHistoryDao();
            return instance;
        }
        return instance;
    }
}
