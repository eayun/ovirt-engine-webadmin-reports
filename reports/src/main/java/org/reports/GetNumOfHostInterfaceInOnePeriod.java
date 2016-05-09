package org.reports;

import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.HostInterfaceDailyHistoryDao;
import org.reports.dao.HostInterfaceHourlyHistoryDao;
import org.reports.dao.HostInterfaceSamplesHistoryDao;

@Path("/GETNUMOFHOSTINTERFACEINONEPERIOD")
public class GetNumOfHostInterfaceInOnePeriod {
	@GET
	@Path("{vm_id}") // vm_id, host_id, storage domain id
	public int getCreatedDate(@PathParam("vm_id") UUID vmId,
				@QueryParam("period") String period,
				@QueryParam("startingTime") String startingTime,
				@QueryParam("terminalTime") String terminalTime) throws SQLException {
		if (period.equals("MINUTE")){
			return HostInterfaceSamplesHistoryDao.getInstance().queryHostInterfaceIdsByHostIdAndPeriod(startingTime, terminalTime, vmId).size();
		}
		else if (period.equals("HOUR")){
			return HostInterfaceHourlyHistoryDao.getInstance().queryHostInterfaceIdsByHostIdAndPeriod(startingTime, terminalTime, vmId).size();
		}
		else if (period.equals("DAY")){
			return HostInterfaceDailyHistoryDao.getInstance().queryHostInterfaceIdsByHostIdAndPeriod(startingTime, terminalTime, vmId).size();
		}
		else {
			return 0;
		}
	}
}
