package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.VmDailyHistory;

public class VmDailyHistoryDao extends BaseDao {
	private static VmDailyHistoryDao instance;
	private static Connection conn;
	
	public VmDailyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	// 获取虚拟机一周，一月，一个季度，一年的 CPU 使用率的数据
	public List<VmDailyHistory> queryCpuByDays(String startDate, String endDate, UUID vm_id) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select cpu_usage_percent, max_cpu_usage from vm_hourly_history"
				+ " where to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate + "' and vm_id = '" + vm_id
				+ "' order by history_datetime asc;");
		List<VmDailyHistory> lvdh = new ArrayList<VmDailyHistory>();
		VmDailyHistory vdh = null;
		while (rs.next()) {
			vdh = new VmDailyHistory();
			vdh.setCpu_usage_percent(rs.getInt("cpu_usage_percent"));
			vdh.setMax_cpu_usage(rs.getInt("max_cpu_usage"));
			lvdh.add(vdh);
		}
		return lvdh;
	}
	
	// 获取虚拟机一周，一月，一个季度，一年的 Memory 使用率的数据
	public List<VmDailyHistory> queryMemoryByDays(String startDate, String endDate, UUID vm_id) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select memory_usage_percent, max_memory_usage from vm_hourly_history"
				+ " where to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate + "' and vm_id = '" + vm_id
				+ "' order by history_datetime asc;");
		List<VmDailyHistory> lvdh = new ArrayList<VmDailyHistory>();
		VmDailyHistory vdh = null;
		while (rs.next()) {
			vdh = new VmDailyHistory();
			vdh.setMemory_usage_percent(rs.getInt("memory_usage_percent"));
			vdh.setMax_cpu_usage(rs.getInt("max_memory_usage"));
			lvdh.add(vdh);
		}
		return lvdh;
	}
	
	public static VmDailyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDailyHistoryDao(conn);
            return instance;
        }
        return instance;
    }
}
