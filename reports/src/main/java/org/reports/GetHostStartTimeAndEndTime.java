package org.reports;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.HostDailyHistoryDao;
import org.reports.dao.HostHourlyHistoryDao;
import org.reports.dao.HostSamplesHistoryDao;

@Path("/GETHOSTSTARTTIMEANDENDTIME")
public class GetHostStartTimeAndEndTime {
	@GET
	@Path("{host_id}") // vm_id, host_id, storage domain id
	public List<String> getCreatedDate(@PathParam("host_id") UUID hostId, @QueryParam("period") String period) throws SQLException{
		if (period.equals("MINUTE")){
			return HostSamplesHistoryDao.getInstance().queryHostStartTimeAndEndTimeByMinutes(hostId);
		}
		else if (period.equals("HOUR")){
			return HostHourlyHistoryDao.getInstance().queryHostStartTimeAndEndTimeByHours(hostId);
		}
		else if (period.equals("DAY")){
			return HostDailyHistoryDao.getInstance().queryHostStartTimeAndEndTimeByDays(hostId);
		}
		else {
			return null;
		}
	}	
}
