package org.reports;

import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.reports.dao.IfDeviceExistDao;

@Path("IFVMINTERFACEANDDISKEXIST")
public class IfVmInterfaceAndDiskExist {
	@GET
	@Path("{vm_id}")
	// 返回值如果是 0 : 没有任何设备； 1 : 没有网卡； 2: 没有磁盘； 3: 都有
	public int IfDeviceExist(@PathParam("vm_id") UUID vmId) throws SQLException{
		int ifExistOfInterface = IfDeviceExistDao.getInstance().queryIfInterfaceExist(vmId);
		int ifExistOfDisk = IfDeviceExistDao.getInstance().queryIfDiskExit(vmId);
		if (ifExistOfInterface + ifExistOfDisk == 0){
			return 0;
		}
		else if (ifExistOfInterface + ifExistOfDisk == 1){
			if (ifExistOfInterface == 0){
				return 1;
			}
			else {
				return 2;
			}
		}
		else{
			return 3;
		}
	}
}
