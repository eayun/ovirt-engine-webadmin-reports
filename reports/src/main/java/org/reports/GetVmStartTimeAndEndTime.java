package org.reports;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.VmDailyHistoryDao;
import org.reports.dao.VmHourlyHistoryDao;
import org.reports.dao.VmSampleHistoryDao;

@Path("/GETVMSTARTTIMEANDENDTIME")
public class GetVmStartTimeAndEndTime {
	@GET
	@Path("{vm_id}") // vm_id, host_id, storage domain id
	public List<String> getCreatedDate(@PathParam("vm_id") UUID vmId, @QueryParam("period") String period) throws SQLException{
		if (period.equals("MINUTE")){
			return VmSampleHistoryDao.getInstance().queryVmStartTimeAndEndTimeByMinutes(vmId);
		}
		else if (period.equals("HOUR")){
			return VmHourlyHistoryDao.getInstance().queryVmStartTimeAndEndTimeByHours(vmId);
		}
		else if (period.equals("DAY")){
			return VmDailyHistoryDao.getInstance().queryVmStartTimeAndEndTimeByDays(vmId);
		}
		else {
			return null;
		}
	}		
}
