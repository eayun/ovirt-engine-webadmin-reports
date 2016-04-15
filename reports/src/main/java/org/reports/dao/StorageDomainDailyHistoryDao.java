package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.StorageDomainDailyHistory;

public class StorageDomainDailyHistoryDao extends BaseDao {
	private static StorageDomainDailyHistoryDao instance;
	private static Connection conn;
	
	public StorageDomainDailyHistoryDao(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}


	public List<StorageDomainDailyHistory> queryCpuByDays(String startDate, String endDate, UUID storage_domain_id)
			throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("select available_disk_size_gb, used_disk_size_gb from storage_domain_daily_history"
						+ " where storage_domain_id = '" + storage_domain_id
						+ "' and to_char(history_datetime, 'YYYY-MM-DD') >= '" + startDate
						+ "' and to_char(history_datetime, 'YYYY-MM-DD') <= '" + endDate
						+ "' order by history_datetime asc;");
		List<StorageDomainDailyHistory> lsddh = new ArrayList<StorageDomainDailyHistory>();
		StorageDomainDailyHistory sddh = null;
		while (rs.next()) {
			sddh = new StorageDomainDailyHistory();
			sddh.setAvailable_disk_size_gb(rs.getInt("available_disk_size_gb"));
			sddh.setUsed_disk_size_gb(rs.getInt("used_disk_size_gb"));
			lsddh.add(sddh);
		}
		return lsddh;
	}

	public static StorageDomainDailyHistoryDao getInstance() {
		if (instance == null) {
			instance = new StorageDomainDailyHistoryDao(conn);
			return instance;
		}
		return instance;
	}
}
