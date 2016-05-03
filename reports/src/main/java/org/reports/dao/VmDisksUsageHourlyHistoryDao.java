package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.reports.Backend;

//查询一天内某几个小时虚拟机各个磁盘的使用率
public class VmDisksUsageHourlyHistoryDao extends BaseDao{
	private static VmDisksUsageHourlyHistoryDao instance;
	/*
	public VmDisksUsageHourlyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}*/
	
	public List<Map<String, Double>> queryDisksByHours(String startHour, String endHour, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:00'), disks_usage"
				+ " from vm_disks_usage_hourly_history"
				+ " where vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:00') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:00') <= '" + endHour
				+ "' order by history_datetime asc;");
		List<Map<String, Double>> lmsd = new ArrayList<Map<String, Double>>();
		Map<String, Double> disks_usage_map = null;
		String history_datetime = null;
		while (rs.next()) {
			disks_usage_map = new LinkedHashMap<String, Double>();
			history_datetime = rs.getString("to_char");
			disks_usage_map.put(history_datetime, 0.0);
			// 从 disks_usage 字符串中算出某虚拟机(1 ~ n)个磁盘的使用率
			disks_usage_map = countDiskUsage(disks_usage_map, rs.getString("disks_usage"));
			lmsd.add(disks_usage_map);
		}
		return lmsd;
	}
	
	public static VmDisksUsageHourlyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDisksUsageHourlyHistoryDao();
            return instance;
        }
        return instance;
    }
}
