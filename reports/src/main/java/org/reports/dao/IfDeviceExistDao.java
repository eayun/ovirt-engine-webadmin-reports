package org.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.reports.Backend;

public class IfDeviceExistDao {
	private static IfDeviceExistDao instance;
	
	public int queryIfInterfaceExist(UUID vm_id) throws SQLException{
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select count(distinct type) from vm_device_history where vm_id = '" + vm_id
				+ "' and type = 'interface';");
		int iFexist = 0;
		while(rs.next()){
			iFexist = rs.getInt("count");
		}
		return iFexist;
	}
	
	public int queryIfDiskExit(UUID vm_id) throws SQLException{
		Statement stmt = Backend.conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select count(distinct type) from vm_device_history where vm_id = '" + vm_id
				+ "' and type = 'disk';");
		int iFexist = 0;
		while(rs.next()){
			iFexist = rs.getInt("count");
		}
		return iFexist;
	}
	
	public static IfDeviceExistDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new IfDeviceExistDao();
            return instance;
        }
        return instance;
    }
}
