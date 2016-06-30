package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.VmHourlyHistory;

public class VmHourlyHistoryDao extends BaseDao {
	private static VmHourlyHistoryDao instance;
	/*
	public VmHourlyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}*/

	// 一天内某几个小时的 CPU 使用率（当然 24 小时就是一天啦），从截面获取 startHour, endHour；
	// 如果是获取一天的数据，startHour = "2016-04-09 00:00:00+08" ，endHour = "2016-04-09
	// 24:00:00+08"
	public List<VmHourlyHistory> queryCpuByHours(String startHour, String endHour, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:00'), cpu_usage_percent, max_cpu_usage"
				+ " from (select *, row_number() over(partition by history_datetime order by history_datetime) as row_number from vm_hourly_history where vm_id = '"
				+ vm_id + "') as rows"
			   + " where row_number = 1"
				+ " and to_char(history_datetime, 'YYYY-MM-DD HH24:00') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:00') <= '" + endHour
				+ "';");
		List<VmHourlyHistory> lvhh = new ArrayList<VmHourlyHistory>();
		VmHourlyHistory vhh = null;
		while (rs.next()) {
			vhh = new VmHourlyHistory();
			vhh.setHistory_datetime(rs.getString("to_char"));
			vhh.setCpu_usage_percent(rs.getInt("cpu_usage_percent"));
			vhh.setMax_cpu_usage(rs.getInt("max_cpu_usage"));
			lvhh.add(vhh);
		}
		return lvhh;
	}
	
	public List<VmHourlyHistory> queryMemoryByHours(String startHour, String endHour, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:00'), memory_usage_percent, max_memory_usage"
				+ " from (select *, row_number() over(partition by history_datetime order by history_datetime) as row_number from vm_hourly_history where vm_id = '"
				+ vm_id + "') as rows"
			   + " where row_number = 1"
				+ " and to_char(history_datetime, 'YYYY-MM-DD HH24:00') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:00') <= '" + endHour
				+ "';");
		List<VmHourlyHistory> lvhh = new ArrayList<VmHourlyHistory>();
		VmHourlyHistory vhh = null;
		while (rs.next()) {
			vhh = new VmHourlyHistory();
			vhh.setHistory_datetime(rs.getString("to_char"));
			vhh.setMemory_usage_percent(rs.getInt("memory_usage_percent"));
			vhh.setMax_memory_usage(rs.getInt("max_memory_usage"));
			lvhh.add(vhh);
		}
		return lvhh;
	}
	
	public List<String> queryVmStartTimeAndEndTimeByHours(UUID vm_id) throws SQLException{
		Statement stmt0 = Backend.conn.createStatement();
		Statement stmt1 = Backend.conn.createStatement();
		ResultSet startTime = stmt0.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:00') from vm_hourly_history where vm_id = '" + vm_id
				+ "' order by history_datetime asc limit 1;");
		ResultSet endTime = stmt1.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:00') from vm_hourly_history where vm_id = '" + vm_id
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
	
	public static VmHourlyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmHourlyHistoryDao();
            return instance;
        }
        return instance;
    }
}
