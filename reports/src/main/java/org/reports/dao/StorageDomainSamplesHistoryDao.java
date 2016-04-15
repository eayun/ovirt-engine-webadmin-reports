package org.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.reports.model.StorageDomainSamplesHistory;

public class StorageDomainSamplesHistoryDao extends BaseDao {
	private static StorageDomainSamplesHistoryDao instance;
	private static Connection conn;
	
    public StorageDomainSamplesHistoryDao(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
    
	public List<StorageDomainSamplesHistory> queryStorageDomainByDays(String hourOfDay, UUID storage_domain_id) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select available_disk_size_gb, used_disk_size_gb from storage_domain_samples_history"
				+ " where storage_domain_id = '" + storage_domain_id
				+ "' and position('" + storage_domain_id + "' in to_char(history_datetime, 'YYYY-MM-DD HH24:')) > 0"
				+ " order by history_datetime asc;");
		List<StorageDomainSamplesHistory> lsdsh = new ArrayList<StorageDomainSamplesHistory>();
		StorageDomainSamplesHistory sdsh = null;
		while (rs.next()) {
			sdsh = new StorageDomainSamplesHistory();
			sdsh.setAvailable_disk_size_gb(rs.getInt("available_disk_size_gb"));
			sdsh.setUsed_disk_size_gb(rs.getInt("used_disk_size_gb"));
			lsdsh.add(sdsh);
		}
		return lsdsh;
	}
	
	public static StorageDomainSamplesHistoryDao getInstance() {
        if (instance == null) {
            instance = new StorageDomainSamplesHistoryDao(conn);
            return instance;
        }
        return instance;
    }
	
}
