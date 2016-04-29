package org.reports;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.StorageDomainDailyHistoryDao;
import org.reports.dao.StorageDomainHourlyHistoryDao;
import org.reports.dao.StorageDomainSamplesHistoryDao;

@Path("/GETSTORAGEDOMAINSTARTTIMEANDENDTIME")
public class GetStorageDomainStartTimeAndEndTime {
	@GET
	@Path("{storage_domain_id}")
	public List<String> getCreatedDate(@PathParam("storage_domain_id") UUID storageDomainId, @QueryParam("period") String period) throws SQLException{
		if (period.equals("MINUTE")){
			return StorageDomainSamplesHistoryDao.getInstance().queryStorageDomainStartTimeAndEndTimeByMinutes(storageDomainId);
		}
		else if (period.equals("HOUR")){
			return StorageDomainHourlyHistoryDao.getInstance().queryStorageDomainStartTimeAndEndTimeByHours(storageDomainId);
		}
		else if (period.equals("DAY")){
			return StorageDomainDailyHistoryDao.getInstance().queryStorageDomainStartTimeAndEndTimeByDays(storageDomainId);
		}
		else {
			return null;
		}
	}	
}
