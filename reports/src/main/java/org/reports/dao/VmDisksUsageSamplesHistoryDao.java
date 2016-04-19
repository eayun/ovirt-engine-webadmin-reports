package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	public List<Map<String, Double>> queryDisksByMinutes(String hourOfDay, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select disks_usage"
				+ " from vm_disks_usage_samples_history where vm_id = '" + vm_id + "' and position('" + hourOfDay
				+ "' in to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS')) > 0" + " order by history_datetime asc;");
		List<Map<String, Double>> lvdush = new ArrayList<Map<String, Double>>();
		while (rs.next()) {
			// 从 disks_usage 字符串中算出某虚拟机(1 ~ n)个磁盘的使用率
			Map<String, Double> disks_usage = countDiskUsage(rs.getString("disks_usage"));
			lvdush.add(disks_usage);
		}
		return lvdush;
	}
	
	public static VmDisksUsageSamplesHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDisksUsageSamplesHistoryDao();
            return instance;
        }
        return instance;
    }
}
