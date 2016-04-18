package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.VmDisksUsageDailyHistory;

public class VmDisksUsageDailyHistoryDao extends BaseDao {
	private static VmDisksUsageDailyHistoryDao instance;
	private static Connection conn;
	
	public VmDisksUsageDailyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	// 获取虚拟机一周，一月，一个季度，一年的磁盘使用率的数据
	public List<VmDisksUsageDailyHistory> queryDisksByDays(String startDate, String endDate, UUID vm_id) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select disks_usage"
				+ " from vm_disks_usage_daily_history"
				+ " where vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate
				+ "' order by history_datetime asc;");
		List<VmDisksUsageDailyHistory> lvdudh = new ArrayList<VmDisksUsageDailyHistory>();
		VmDisksUsageDailyHistory vdudh = null;
		while (rs.next()) {
			vdudh = new VmDisksUsageDailyHistory();
			vdudh.setDisks_usage(rs.getString("disks_usage"));
			lvdudh.add(vdudh);
		}
		return lvdudh;
	}
	
	public static VmDisksUsageDailyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDisksUsageDailyHistoryDao(conn);
            return instance;
        }
        return instance;
    }
}
