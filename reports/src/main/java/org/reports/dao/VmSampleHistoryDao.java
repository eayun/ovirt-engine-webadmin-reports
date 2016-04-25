package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.VmSampleHistory;

public class VmSampleHistoryDao extends BaseDao {
	private static VmSampleHistoryDao instance;
	/*
	public VmSampleHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}*/

	// vm_samples_history 表存的是1天零8小时的每分钟的数据，查询 hourOfDay 至 hourOfDay + 1 这一个小时内的 CPU
	// memory 的数据。
	// hourOfDay 格式是这样的 '2016-04-09 17:'，有年月日，具体的小时数，字符串。
	public List<VmSampleHistory> queryCpuByMinutes(String startMinute, String endMinute, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select cpu_usage_percent from vm_samples_history where vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "' order by history_datetime asc;");
		List<VmSampleHistory> lv = new ArrayList<VmSampleHistory>();
		VmSampleHistory vsh = null;
		while (rs.next()) {
			vsh = new VmSampleHistory();
			vsh.setCpu_usage_percent(rs.getInt("cpu_usage_percent"));
			lv.add(vsh);
		}
		return lv;
	}

	public List<VmSampleHistory> queryMemoryByMinutes(String startMinute, String endMinute, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select memory_usage_percent from vm_samples_history where vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "' order by history_datetime asc;");
		List<VmSampleHistory> lv = new ArrayList<VmSampleHistory>();
		VmSampleHistory vsh = null;
		while (rs.next()) {
			vsh = new VmSampleHistory();
			vsh.setCpu_usage_percent(rs.getInt("memory_usage_percent"));
			lv.add(vsh);
		}
		return lv;
	}
	
	public static VmSampleHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmSampleHistoryDao();
            return instance;
        }
        return instance;
    }
}
