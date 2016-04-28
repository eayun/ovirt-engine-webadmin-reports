package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.reports.Backend;

public class VHSConfigurationDao {
	private static VHSConfigurationDao instance;
	
	public String queryVmOrHostOrStorageDomainCreatedDate(UUID id, String Entity, String period) throws SQLException{
		// id: vm_id host_id storage_domain_id
		// Entity: vm host storage_domain
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select create_date from " + Entity + "_configuration where " + Entity + "_id = '"
				+ id + "'group by create_date;");
		String created_date = null;
		while(rs.next()) {
			System.out.println(period);
			if (period.equals("MINUTE")) {
			   created_date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs.getDate("create_date"));
			}
			else if (period.equals("HOUR")) {
			   created_date = new SimpleDateFormat("yyyy-MM-dd HH").format(rs.getDate("create_date"));
			}
			else {
			   created_date = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("create_date"));
			}
		}
		
		return created_date;
	}
	
	public static VHSConfigurationDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new VHSConfigurationDao();
            return instance;
        }
        return instance;
    }
}
