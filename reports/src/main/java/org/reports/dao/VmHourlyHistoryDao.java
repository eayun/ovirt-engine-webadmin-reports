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
		ResultSet rs = stmt.executeQuery("select cpu_usage_percent, max_cpu_usage from vm_hourly_history"
				+ " where to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') <= '" + endHour + "' and vm_id = '" + vm_id
				+ "' order by history_datetime asc;");
		List<VmHourlyHistory> lvhh = new ArrayList<VmHourlyHistory>();
		VmHourlyHistory vhh = null;
		while (rs.next()) {
			vhh = new VmHourlyHistory();
			vhh.setCpu_usage_percent(rs.getInt("cpu_usage_percent"));
			vhh.setMax_cpu_usage(rs.getInt("max_cpu_usage"));
			lvhh.add(vhh);
		}
		return lvhh;
	}
	
	public List<VmHourlyHistory> queryMemoryByHours(String startHour, String endHour, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select memory_usage_percent, max_memory_usage from vm_hourly_history"
				+ " where to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') <= '" + endHour + "' and vm_id = '" + vm_id
				+ "' order by history_datetime asc;");
		List<VmHourlyHistory> lvhh = new ArrayList<VmHourlyHistory>();
		VmHourlyHistory vhh = null;
		while (rs.next()) {
			vhh = new VmHourlyHistory();
			vhh.setMemory_usage_percent(rs.getInt("memory_usage_percent"));
			vhh.setMax_memory_usage(rs.getInt("max_memory_usage"));
			lvhh.add(vhh);
		}
		return lvhh;
	}
	
	public static VmHourlyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmHourlyHistoryDao();
            return instance;
        }
        return instance;
    }
}
