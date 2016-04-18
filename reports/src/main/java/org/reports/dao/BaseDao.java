package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.reports.Backend;

public class BaseDao {
	protected static Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	public BaseDao(Connection conn) throws SQLException {
		super();
		BaseDao.conn = Backend.getDs().getConnection();
		System.out.println("===============" + conn);
	}
    
	// disks_usage ：[{"path":"/","total":"51605344256","used":"3711524864","fs	":"ext4"}]
	// 计算虚拟机磁盘使用率
	public Map<String, Double> countDiskUsage(String disks_usage_from_db){
		Map<String, Double> disks_usage = new HashMap<String, Double>();
		List<String> pathName = new ArrayList<String>();
		List<Double> total = new ArrayList<Double>();
		List<Double> used = new ArrayList<Double>();
 		//String data = "[{'path':'/','total':'100','used':'20','fs':'xfs'},{'path':'/boot','total':'100','used':'50','fs':'xfs'}]";
		JSONArray arr = new JSONArray(disks_usage_from_db);
		for (int i = 0; i < arr.length(); i ++){
			String pathName_ = (String) arr.getJSONObject(i).get("path");
			pathName.add(pathName_);
		    String total_ = (String) arr.getJSONObject(i).get("total");
		    Double total_num = Double.parseDouble(total_);
		    total.add(total_num);
		    String used_ = (String) arr.getJSONObject(i).get("used");
		    Double used_num = Double.parseDouble(used_);
		    used.add(used_num);
		}
		for (int i = 0; i < pathName.size(); i ++){
			Double usage_percent = (used.get(i) / total.get(i)) * 100;
			disks_usage.put(pathName.get(i), usage_percent);
		}
		return disks_usage;
	}
	
	// 通过虚拟机的 id 获取虚拟机的 name
	public String getVmInterfaceNameByVmInterfaceId(UUID vm_interface_id) throws SQLException{
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select vm_interface_name from vm_interface_configuration"
				+ " where vm_interface_id = '" + vm_interface_id
				+ " group by vm_interface_name;");
		return rs.getString("vm_interface_name");
	}
	
	// 通过主机的 id 获取主机的 name
	public String getHostInterfaceNameByHostInterfaceId(UUID host_interface_id) throws SQLException{
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select host_interface_name from host_interface_configuration"
				+ " where host_interface_id = '" + host_interface_id
				+ "' group by host_interface_name;");
		return rs.getString("host_interface_name");
	}
	
}