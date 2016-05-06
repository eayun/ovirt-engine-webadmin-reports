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

public class VmDisksUsageDailyHistoryDao extends BaseDao {
	private static VmDisksUsageDailyHistoryDao instance;
	
/*	public VmDisksUsageDailyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}
*/
	// 获取虚拟机一周，一月，一个季度，一年的磁盘使用率的数据
	public List<Map<String, Object>> queryDisksByDays(String startDate, String endDate, UUID vm_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD'), disks_usage"
				+ " from vm_disks_usage_daily_history"
				+ " where vm_id = '" + vm_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
				+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate
				+ "' order by history_datetime asc;");
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
/*  
 [{"path":"/","total":"18503614464","used":"4809031680","fs":"ext4"},{"path":"/boot","total":"507744256","used":"67568640","fs":"ext4"},{"path":"/media/CentOS_6.5_Final","total":"446780
2112","used":"4467802112","fs":"iso9660"}]
 [{"path":"/","total":"18503614464","used":"5283454976","fs":"ext4"},{"path":"/boot","total":"507744256","used":"67568640","fs":"ext4"}]
 [{"path":"/","total":"18503614464","used":"6049079296","fs":"ext4"},{"path":"/boot","total":"507744256","used":"67568640","fs":"ext4"}]
 
 
 [{"path":"/","total":"18369396736","used":"6006054912","fs":"ext4"},{"path":"/boot","total":"499355648","used":"64350208","fs":"ext4"}]
*/
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
	
	public static VmDisksUsageDailyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VmDisksUsageDailyHistoryDao();
            return instance;
        }
        return instance;
    }
}
