package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.VmInterfaceHourlyHistory;

public class VmInterfaceHourlyHistoryDao extends BaseDao{
	private static VmInterfaceHourlyHistoryDao instance;
	
/*	public VmInterfaceHourlyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}
*/
	// 获取一个虚拟机在某天的某几个小时内的所有网络接口 ID
	public List<UUID> queryVmInterfaceIdsByVmIdAndPeriod(String startHour, String endHour, UUID vm_id) throws SQLException{
		List<UUID> vmInterfaceIdsOfOneVm = new ArrayList<UUID>();
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select vihh.vm_interface_id from vm_interface_configuration vic, vm_interface_hourly_history vihh"
				+ " where vic.vm_interface_id = vihh.vm_interface_id and vic.vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:00') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:00') < '" + endHour
				+ "' group by vihh.vm_interface_id;");
		while (rs.next()) {
			vmInterfaceIdsOfOneVm.add(UUID.fromString(rs.getString("vm_interface_id")));
		}
		return vmInterfaceIdsOfOneVm;
	}
	
	// 查询一天内某几个小时虚拟机网络传入/传出速率
	public List<VmInterfaceHourlyHistory> queryNetworkRateByHours(String startHour, String endHour,
			UUID vm_interface_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select to_char(history_datetime, 'YYYY-MM-DD HH24:00'), receive_rate_percent, max_receive_rate_percent, transmit_rate_percent, max_transmit_rate_percent"
						+ " from vm_interface_hourly_history"
						+ " where vm_interface_id = '" + vm_interface_id
						+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:00') >= '" + startHour
						+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:00') <= '" + endHour
						+ "' order by history_datetime asc;");
		List<VmInterfaceHourlyHistory> lvihh = new ArrayList<VmInterfaceHourlyHistory>();
		VmInterfaceHourlyHistory vihh = null;
		while (rs.next()) {
			vihh = new VmInterfaceHourlyHistory();
			vihh.setHistory_datetime(rs.getString("to_char"));
			vihh.setReceive_rate_percent(rs.getInt("receive_rate_percent"));
			vihh.setMax_receive_rate_percent(rs.getInt("max_receive_rate_percent"));
			vihh.setTransmit_rate_percent(rs.getInt("transmit_rate_percent"));
			vihh.setMax_transmit_rate_percent(rs.getInt("max_transmit_rate_percent"));
			vihh.setVm_interface_name(getVmInterfaceNameByVmInterfaceId(vm_interface_id));
			lvihh.add(vihh);
		}
		return lvihh;
	}
	
	public static VmInterfaceHourlyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmInterfaceHourlyHistoryDao();
            return instance;
        }
        return instance;
    }
}
