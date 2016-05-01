package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;
import org.reports.model.VmInterfaceDailyHistory;

public class VmInterfaceDailyHistoryDao extends BaseDao {
	private static VmInterfaceDailyHistoryDao instance;
/*	
	public VmInterfaceDailyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}*/
	
	// 获取一个虚拟机在某几天内的所有网络接口 ID
	public List<UUID> queryVmInterfaceIdsByVmIdAndPeriod(String startDate, String endDate, UUID vm_id) throws SQLException{
		List<UUID> vmInterfaceIdsOfOneVm = new ArrayList<UUID>();
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select vidh.vm_interface_id from vm_interface_configuration vic, vm_interface_daily_history vidh"
				+ " where vic.vm_interface_id = vidh.vm_interface_id and"
				+ " vic.vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate
				+ "' group by vidh.vm_interface_id;");
		while (rs.next()) {
			vmInterfaceIdsOfOneVm.add(UUID.fromString(rs.getString("vm_interface_id")));
		}
		return vmInterfaceIdsOfOneVm;
	}
	
	// 查询虚拟机在一周，一月，一季度，一年的网络传入/传出数据
	public List<VmInterfaceDailyHistory> queryNetworkRateByDays(String startDate, String endDate, UUID vm_interface_id)
			throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select receive_rate_percent, max_receive_rate_percent, transmit_rate_percent, max_transmit_rate_percent"
						+ " from vm_interface_daily_history"
						+ " where vm_interface_id = '" + vm_interface_id
						+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate
						+ "' and to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
						+ "' order by history_datetime asc;");

		List<VmInterfaceDailyHistory> lvidh = new ArrayList<VmInterfaceDailyHistory>();

		VmInterfaceDailyHistory vidh = null;

		while (rs.next()) {
			vidh = new VmInterfaceDailyHistory();
			vidh.setReceive_rate_percent(rs.getInt("receive_rate_percent"));
			vidh.setMax_receive_rate_percent(rs.getInt("max_receive_rate_percent"));
			vidh.setTransmit_rate_percent(rs.getInt("transmit_rate_percent"));
			vidh.setMax_transmit_rate_percent(rs.getInt("max_transmit_rate_percent"));
			vidh.setVm_interface_name(getVmInterfaceNameByVmInterfaceId(vm_interface_id));
			lvidh.add(vidh);
			System.out.println("-------------------- queryNetworkRateByDays 方法");
		}
		return lvidh;
	}
	
	public static VmInterfaceDailyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmInterfaceDailyHistoryDao();
            return instance;
        }
        return instance;
    }
}
