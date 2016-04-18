package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.VmDisksUsageSamplesHistory;

// 取出来的 disks_usage 是 String 类型，在控制端将 String 类型中的每个虚拟机的每个磁盘的磁盘占用空间的数据计算出来（用到 JSONArray 类）
public class VmDisksUsageSamplesHistoryDao extends BaseDao{
	private static VmDisksUsageSamplesHistoryDao instance;
	private static Connection conn;
	
	public VmDisksUsageSamplesHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public List<VmDisksUsageSamplesHistory> queryDisksByMinutes(String hourOfDay, UUID vm_id) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select disks_usage"
				+ " from vm_disks_usage_samples_history where vm_id = '" + vm_id + "' and position('" + hourOfDay
				+ "' in to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS')) > 0" + " order by history_datetime asc;");
		List<VmDisksUsageSamplesHistory> lvdush = new ArrayList<VmDisksUsageSamplesHistory>();
		VmDisksUsageSamplesHistory vdush = null;
		while (rs.next()) {
			vdush = new VmDisksUsageSamplesHistory();
			vdush.setDisks_usage(rs.getString("disks_usage"));
			lvdush.add(vdush);
		}
		return lvdush;
	}
	
	public static VmDisksUsageSamplesHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDisksUsageSamplesHistoryDao(conn);
            return instance;
        }
        return instance;
    }
}
