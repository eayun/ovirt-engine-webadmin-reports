package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.Backend;

public class StorageDomainHourlyHistoryDao extends BaseDao {
	private static StorageDomainHourlyHistoryDao instance;
	
//	public StorageDomainHourlyHistoryDao(Connection conn) throws SQLException {
//		super(conn);
//	}
	
	public List<Double> queryStorageDomainByHours(String startHour, String endHour, UUID storage_domain_id) throws Exception {
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select available_disk_size_gb, used_disk_size_gb from storage_domain_hourly_history"
				+ " where storage_domain_id = '" + storage_domain_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') < '" + endHour
				+ "' order by history_datetime asc;");
		
		List<Double> lsdhh = new ArrayList<Double>();
		int available_disk_size_gb = 0;
		int used_disk_size_gb = 0;
		double usage = 0.0;
		while (rs.next()) {
			available_disk_size_gb = rs.getInt("available_disk_size_gb");
			used_disk_size_gb = rs.getInt("used_disk_size_gb");
			usage = 0.0;
			if (available_disk_size_gb + used_disk_size_gb == 0) {
				usage = 0.0;
			}else {
				usage = ( used_disk_size_gb + 0.0 ) / ( used_disk_size_gb + available_disk_size_gb + 0.0);
			}
			lsdhh.add(usage);
		}
		return lsdhh;
	}
	
	public static StorageDomainHourlyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new StorageDomainHourlyHistoryDao();
            return instance;
        }
        return instance;
    }
}
