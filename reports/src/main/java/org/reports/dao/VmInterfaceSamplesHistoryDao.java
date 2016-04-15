package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.VmInterfaceSamplesHistory;

public class VmInterfaceSamplesHistoryDao extends BaseDao{
	private static VmInterfaceSamplesHistoryDao instance;
	private static Connection conn;
	
	public VmInterfaceSamplesHistoryDao(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	// 获取一个虚拟机在某个小时内的所有网络接口 ID
	public List<UUID> queryVmInterfaceIdsByVmIdAndPeriod(String hourOfDay, UUID vm_id) throws SQLException{
		// 现在要找历史虚拟机网卡信息，就不能用 sdk 了，应利用数据库来获取。
		List<UUID> vmInterfaceIdsOfOneVm = new ArrayList<UUID>();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select vish.vm_interface_id from vm_interface_configuration vic, vm_interface_samples_history vish“"
				+ " where vic.vm_interface_id = vish.vm_interface_id"
				+ " and vic.vm_id = '" + vm_id
				+ "' and position('" + hourOfDay + "' in to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS')) > 0"
				+ " group by vish.vm_interface_id;");
		while (rs.next()) {
			vmInterfaceIdsOfOneVm.add(UUID.fromString(rs.getString("vm_interface_id")));
		}
		return vmInterfaceIdsOfOneVm;
	}
	
	// 获取某个小时内的网络传入/传出数据 (By 后面就是横坐标变化的内容)
	// hourOfDay 格式是这样的 '2016-04-09 17:'，有年月日，具体的小时数，字符串。
	public List<VmInterfaceSamplesHistory> queryNetworkRateByMinutes(String hourOfDay, UUID vm_interface_id)
			throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select receive_rate_percent, transmit_rate_percent"
				+ " from vm_interface_samples_history"
				+ " where vm_interface_id = '" + vm_interface_id
				+ "' and position('" + hourOfDay + "' in to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS')) > 0"
				+ " order by history_datetime asc;");
		List<VmInterfaceSamplesHistory> lvish = new ArrayList<VmInterfaceSamplesHistory>();
		VmInterfaceSamplesHistory vish = null;
		while (rs.next()) {
			vish = new VmInterfaceSamplesHistory();
			vish.setReceive_rate_percent(rs.getInt("receive_rate_percent"));
			vish.setTransmit_rate_percent(rs.getInt("transmit_rate_percent"));
			vish.setVm_interface_name(getVmInterfaceNameByVmInterfaceId(vm_interface_id)); //把接口的名字也传给该对象
			lvish.add(vish);
		}
		return lvish;
	}
	
	public static VmInterfaceSamplesHistoryDao getInstance() {
        if (instance == null) {
            instance = new VmInterfaceSamplesHistoryDao(conn);
            return instance;
        }
        return instance;
    }
}
