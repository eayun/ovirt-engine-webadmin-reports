package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.collections.ListUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.reports.Backend;

// 取出来的 disks_usage 是 String 类型，在控制端将 String 类型中的每个虚拟机的每个磁盘的磁盘占用空间的数据计算出来（用到 JSONArray 类）
public class VmDisksUsageSamplesHistoryDao extends BaseDao{
	private static VmDisksUsageSamplesHistoryDao instance;
	
	/*ublic VmDisksUsageSamplesHistoryDao(Connection conn) throws SQLException {
		super(conn);
	}
	*/
	public List<Map<String, Object>> queryDisksByMinutes(String startMinute, String endMinute, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI'), disks_usage"
				+ " from (select *, row_number() over(partition by history_datetime order by history_datetime) as row_number from vm_disks_usage_samples_history where vm_id = '"
				+ "'vm_id) as rows"
			    + " where row_number = 1"
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "';");
		List<Map<String, Object>> lmsd = new ArrayList<Map<String, Object>>();
		Map<String, Object> disks_usage_map = null;
		String history_datetime = null;
		String disks_usage = null;
		Set<String> pathSet = new TreeSet<String>();
		List<String> disks_usage_list = new ArrayList<String>();
		List<String> history_datetime_list = new ArrayList<String>();
		String pathName = null;
		JSONArray disks_usage_json = null;
		while (rs.next()) {
			history_datetime = rs.getString("to_char");
			disks_usage = rs.getString("disks_usage");
			disks_usage_list.add(disks_usage);
			history_datetime_list.add(history_datetime);
			// 获取所选时间段内的所有 path，并排序(eg:/, /boot, /etc, /usr)
			if (disks_usage != null){
				disks_usage_json = new JSONArray(disks_usage);
				for (int i = 0; i < disks_usage_json.length(); i ++){
					pathName = (String) disks_usage_json.getJSONObject(i).get("path");
					pathSet.add(pathName);
				}
			}
		}
		List<String> pathList = new ArrayList<String>(pathSet);
		List<String> pathListOfOneData = null;
		JSONArray disks_usage_ofOneData = null;
		for (int i = 0; i < history_datetime_list.size(); i ++){
			disks_usage_map = new LinkedHashMap<String, Object>();
			// 对 usage_disks 进行处理，获取每条数据的 path
			if (disks_usage_list.get(i) != null){
				disks_usage_ofOneData = new JSONArray(disks_usage_list.get(i));
				pathListOfOneData = new ArrayList<String>();
				for (int j = 0; j < disks_usage_ofOneData.length(); j ++){
					pathListOfOneData.add((String)disks_usage_ofOneData.getJSONObject(j).get("path"));
				}
				List<String> list = ListUtils.subtract(pathList, pathListOfOneData);
				if (list != null){
				    // 往 json 数据中添加没有的 path
					for (int k = 0; k < list.size(); k ++){
						JSONObject newJsonData = new JSONObject();  
						// 注意是字符串 null，因为写成 null 会包错:The method put(String, Collection<?>) is ambiguous for the type JSONObject
						newJsonData.put("path", list.get(k));
						newJsonData.put("total", "null");
						newJsonData.put("used", "null");
						newJsonData.put("fs", "null");
						disks_usage_ofOneData.put(newJsonData);
					}
				}
			}
			else {
				disks_usage_ofOneData = new JSONArray();
				for (int k = 0; k < pathList.size(); k ++){
					JSONObject newJsonData = new JSONObject();
					newJsonData.put("path", pathList.get(k));
					newJsonData.put("total", "null");
					newJsonData.put("used", "null");
					newJsonData.put("fs", "null");
					disks_usage_ofOneData.put(newJsonData);
				}
			}
			// 从 disks_usage 字符串中算出某虚拟机(1 ~ n)个磁盘的使用率
			// {日期:0.0} 前端只取出来日期即可
			disks_usage_map.put(history_datetime_list.get(i), null);
			disks_usage_map = countDiskUsage(disks_usage_map, disks_usage_ofOneData, pathList);
			lmsd.add(disks_usage_map);
		}
		return lmsd;
	}
	
	public static VmDisksUsageSamplesHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDisksUsageSamplesHistoryDao();
            return instance;
        }
        return instance;
    }
}
