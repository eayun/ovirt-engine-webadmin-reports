package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.reports.Backend;

public class BaseDao {
//	protected static Connection conn;
	private Statement stmt;
	private ResultSet rs;
/*
 * 0. 首先遍历在某时间段内出现的所有磁盘使用目录（即 "path"），暂称 allOfPath
 * 1. 对每一条数据进行遍历，如果与 allOfPath 不匹配（即缺少了某几个 path）的话：
 *   1.0 就将前台的查看方式中的曲线图灰掉，这是因为会出现断点，曲线图是连续的不能画，柱状图和雷达图均可有断点的存在。
 *   1.1 对每条数据缺少的 path，进行强制补充（给出了键），值就设置为 null 即可。
 */
    
	// disks_usage ：[{"path":"/","total":"51605344256","used":"3711524864","fs	":"ext4"}]
	// String data = "[{'path':'/','total':'100','used':'20','fs':'xfs'},{'path':'/boot','total':'100','used':'50','fs':'xfs'}]";
	// 计算虚拟机磁盘使用率
	public Map<String, Object> countDiskUsage(Map<String, Object> disks_usage_map, JSONArray disks_usage_from_db, List<String> pathList){
		List<String> pathName = new ArrayList<String>();
		List<Object> total = new ArrayList<Object>();
		List<Object> used = new ArrayList<Object>();
		Double usage_percent = 0.0;
		for (int i = 0; i < disks_usage_from_db.length(); i ++){
			String pathName_ = (String) disks_usage_from_db.getJSONObject(i).get("path");
			pathName.add(pathName_);
		    String total_ = (String) disks_usage_from_db.getJSONObject(i).get("total");
		    total.add(total_);
		    String used_ = (String) disks_usage_from_db.getJSONObject(i).get("used");
		    used.add(used_);
		}
		for (int j = 0; j < pathList.size(); j ++){
			for (int i = 0; i < pathName.size(); i ++){
				if (pathList.get(j).equals(pathName.get(i))){
					if (!total.get(i).equals("null")){
						Double total_num = Double.parseDouble((String)(total.get(i)));
						Double used_num = Double.parseDouble((String)(used.get(i)));
						if (total_num == 0 || used_num == 0){
							disks_usage_map.put(pathList.get(j), 0);
						}
						else {
							usage_percent = (used_num / total_num) * 100;
							disks_usage_map.put(pathList.get(j), usage_percent);
						}
					}
					else {
						disks_usage_map.put(pathList.get(j), null);
					}
				}
			}
		}
		return disks_usage_map;
	}
	
	// 通过虚拟机的 id 获取虚拟机的 name
	public String getVmInterfaceNameByVmInterfaceId(UUID vm_interface_id) throws SQLException{
		stmt = Backend.conn.createStatement();
		String name = null;
		rs = stmt.executeQuery("select vm_interface_name from vm_interface_configuration"
				+ " where vm_interface_id = '" + vm_interface_id + "';");
		while(rs.next()){
			name = rs.getString("vm_interface_name");
		}
		return name;
	}
	
	// 通过主机的 id 获取主机的 name
	public String getHostInterfaceNameByHostInterfaceId(UUID host_interface_id) throws SQLException{
		stmt = Backend.conn.createStatement();
		String name = null;
		rs = stmt.executeQuery("select host_interface_name from host_interface_configuration"
				+ " where host_interface_id = '" + host_interface_id + "';");
		while (rs.next()) {
			name = rs.getString("host_interface_name");
		}
		return name;
	}
}