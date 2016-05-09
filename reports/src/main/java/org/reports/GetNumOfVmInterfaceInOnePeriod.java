package org.reports;

import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.VmInterfaceDailyHistoryDao;
import org.reports.dao.VmInterfaceHourlyHistoryDao;
import org.reports.dao.VmInterfaceSamplesHistoryDao;

@Path("/GETNUMOFVMINTERFACEINONEPERIOD")
public class GetNumOfVmInterfaceInOnePeriod {
	@GET
	@Path("{vm_id}") // vm_id, host_id, storage domain id
	public int getCreatedDate(@PathParam("vm_id") UUID vmId,
				@QueryParam("period") String period,
				@QueryParam("startingTime") String startingTime,
				@QueryParam("terminalTime") String terminalTime) throws SQLException {
		if (period.equals("MINUTE")){
			return VmInterfaceSamplesHistoryDao.getInstance().queryVmInterfaceIdsByVmIdAndPeriod(startingTime, terminalTime, vmId).size();
		}
		else if (period.equals("HOUR")){
			return VmInterfaceHourlyHistoryDao.getInstance().queryVmInterfaceIdsByVmIdAndPeriod(startingTime, terminalTime, vmId).size();
		}
		else if (period.equals("DAY")){
			return VmInterfaceDailyHistoryDao.getInstance().queryVmInterfaceIdsByVmIdAndPeriod(startingTime, terminalTime, vmId).size();
		}
		else {
			return 0;
		}
	}
}
