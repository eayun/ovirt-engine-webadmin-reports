package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.reports.Backend;

public class StorageDomainSamplesHistoryDao extends BaseDao {
	private static StorageDomainSamplesHistoryDao instance;
	
//    public StorageDomainSamplesHistoryDao(Connection conn) throws SQLException {
//		super(conn);
//		// TODO Auto-generated constructor stub
//	}
    
	public List<Map<String, Double>> queryStorageDomainByMinutes(String startMinute, String endMinute, UUID storage_domain_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI'), available_disk_size_gb, used_disk_size_gb from storage_domain_samples_history"
				+ " where storage_domain_id = '" + storage_domain_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "' order by history_datetime asc;");
		
		List<Map<String, Double>> lmsd = new ArrayList<Map<String, Double>>();
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		String history_datetime = null;
		Double available_disk_size_gb = 0.0;
		Double used_disk_size_gb = 0.0;
		Double usage = 0.0;
		while (rs.next()) {
			history_datetime = rs.getString("to_char");
			available_disk_size_gb = rs.getInt("available_disk_size_gb") + 0.0;
			used_disk_size_gb = rs.getInt("used_disk_size_gb") + 0.0;
			if (available_disk_size_gb + used_disk_size_gb == 0.0) {
				usage = 0.0;
			} else {
				usage = used_disk_size_gb / (used_disk_size_gb + available_disk_size_gb);
			}
			map.put(history_datetime, usage * 100);
		}
		lmsd.add(map);
		return lmsd;
	}
	
	public List<String> queryStorageDomainStartTimeAndEndTimeByMinutes(UUID storage_domain_id) throws SQLException{
		Statement stmt0 = Backend.conn.createStatement();
		Statement stmt1 = Backend.conn.createStatement();
		ResultSet startTime = stmt0.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI') from storage_domain_samples_history where storage_domain_id = '" + storage_domain_id
				+ "' order by history_datetime asc limit 1;");
		ResultSet endTime = stmt1.executeQuery("select to_char(history_datetime, 'YYYY-MM-DD HH24:MI') from storage_domain_samples_history where storage_domain_id = '" + storage_domain_id
				+ "' order by history_datetime desc limit 1;");
		List<String> Time = new ArrayList<String>();
		String start_time = null;
		String end_time = null;
		while(startTime.next()){
			start_time = startTime.getString("to_char");
			Time.add(start_time);
		}
		while(endTime.next()){
			end_time = endTime.getString("to_char");
			Time.add(end_time);
		}
		return Time;
	}
	
	public static StorageDomainSamplesHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new StorageDomainSamplesHistoryDao();
            return instance;
        }
        return instance;
    }
	
}
