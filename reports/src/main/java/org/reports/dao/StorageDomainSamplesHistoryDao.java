package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;

public class StorageDomainSamplesHistoryDao extends BaseDao {
	private static StorageDomainSamplesHistoryDao instance;
	
//    public StorageDomainSamplesHistoryDao(Connection conn) throws SQLException {
//		super(conn);
//		// TODO Auto-generated constructor stub
//	}
    
	public List<Double> queryStorageDomainByMinutes(String startMinute, String endMinute, UUID storage_domain_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select available_disk_size_gb, used_disk_size_gb from storage_domain_samples_history"
				+ " where storage_domain_id = '" + storage_domain_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') >= '" + startMinute
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI') <= '" + endMinute
				+ "' order by history_datetime asc;");
		List<Double> lsdsh = new ArrayList<Double>();
		Double available_disk_size_gb = 0.0;
		Double used_disk_size_gb = 0.0;
		Double usage = 0.0;
		while (rs.next()) {
			available_disk_size_gb = rs.getInt("available_disk_size_gb") + 0.0;
			used_disk_size_gb = rs.getInt("used_disk_size_gb") + 0.0;
			if (available_disk_size_gb + used_disk_size_gb == 0.0) {
				usage = 0.0;
			} else {
				usage = used_disk_size_gb / (used_disk_size_gb + available_disk_size_gb);
			}
			lsdsh.add(usage);
		}
		return lsdsh;
	}
	
	public static StorageDomainSamplesHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new StorageDomainSamplesHistoryDao();
            return instance;
        }
        return instance;
    }
	
}
