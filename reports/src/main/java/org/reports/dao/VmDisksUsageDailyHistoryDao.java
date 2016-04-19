package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.reports.Backend;

public class VmDisksUsageDailyHistoryDao extends BaseDao {
	private static VmDisksUsageDailyHistoryDao instance;
	
/*	public VmDisksUsageDailyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}
*/
	// 获取虚拟机一周，一月，一个季度，一年的磁盘使用率的数据
	public List<Map<String, Double>> queryDisksByDays(String startDate, String endDate, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select disks_usage"
				+ " from vm_disks_usage_daily_history"
				+ " where vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate
				+ "' order by history_datetime asc;");
		List<Map<String, Double>> lvdudh = new ArrayList<Map<String, Double>>();
		while (rs.next()) {
			// 从 disks_usage 字符串中算出某虚拟机(1 ~ n)个磁盘的使用率
			Map<String, Double> disks_usage = countDiskUsage(rs.getString("disks_usage"));
			lvdudh.add(disks_usage);
		}
		return lvdudh;
	}
	
	public static VmDisksUsageDailyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDisksUsageDailyHistoryDao();
            return instance;
        }
        return instance;
    }
}
