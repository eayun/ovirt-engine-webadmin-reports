package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.StorageDomainHourlyHistory;

public class StorageDomainHourlyHistoryDao extends BaseDao {
	private static StorageDomainHourlyHistoryDao instance;
	private static Connection conn;
	
	public StorageDomainHourlyHistoryDao(Connection conn) throws SQLException {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	
	public List<StorageDomainHourlyHistory> queryMemoryByHours(String startHour, String endHour, UUID storage_domain_id) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select available_disk_size_gb, used_disk_size_gb from storage_domain_hourly_history"
				+ " where storage_domain_id = '" + storage_domain_id
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') >= '" + startHour
				+ "' and to_char(history_datetime, 'YYYY-MM-DD HH24:MI:SS') <= '" + endHour
				+ "' order by history_datetime asc;");
		List<StorageDomainHourlyHistory> lsdhh = new ArrayList<StorageDomainHourlyHistory>();
		StorageDomainHourlyHistory sdhh = null;
		while (rs.next()) {
			sdhh = new StorageDomainHourlyHistory();
			sdhh.setAvailable_disk_size_gb(rs.getInt("available_disk_size_gb"));
			sdhh.setUsed_disk_size_gb(rs.getInt("used_disk_size_gb"));
			lsdhh.add(sdhh);
		}
		return lsdhh;
	}
	
	public static StorageDomainHourlyHistoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new StorageDomainHourlyHistoryDao(conn);
            return instance;
        }
        return instance;
    }
}
