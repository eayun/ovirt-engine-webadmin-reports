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

// 取出来的 disks_usage 是 String 类型，在控制端将 String 类型中的每个虚拟机的每个磁盘的磁盘占用空间的数据计算出来（用到 JSONArray 类）
public class VmDisksUsageSamplesHistoryDao extends BaseDao{
	private static VmDisksUsageSamplesHistoryDao instance;
	
	/*ublic VmDisksUsageSamplesHistoryDao(Connection conn) throws SQLException {
		super(conn);
	}
	*/
	public List<Map<String, Double>> queryDisksByMinutes(String startMinute, String endMinute, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI'), disks_usage"
				+ " from vm_disks_usage_samples_history where vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "' order by history_datetime asc;");
		List<Map<String, Double>> lmsd = new ArrayList<Map<String, Double>>();
		Map<String, Double> disks_usage_map = new LinkedHashMap<String, Double>();
		String history_datetime = null;
		String disks_usage = null;
		while (rs.next()) {
			history_datetime = rs.getString("to_char");
			disks_usage = rs.getString("disks_usage");
			disks_usage_map.put(history_datetime, 0.0);
			// 从 disks_usage 字符串中算出某虚拟机(1 ~ n)个磁盘的使用率
			disks_usage_map = countDiskUsage(disks_usage_map, disks_usage);
			lmsd.add(disks_usage_map);
		}
		return lmsd;
	}
	
	public static VmDisksUsageSamplesHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDisksUsageSamplesHistoryDao();
            return instance;
        }
        return instance;
    }
}
