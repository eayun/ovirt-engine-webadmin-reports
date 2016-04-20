package org.reports;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.StorageDomainDailyHistoryDao;
import org.reports.dao.StorageDomainHourlyHistoryDao;
import org.reports.dao.StorageDomainSamplesHistoryDao;

@Path("/STORAGEDOMAINREPORTS")
public class StorageDomainReports {
	@GET
	@Path("{storage_domain_id}")
	// 最后用 List 接收所有的 List
	public List<Double> getListOnHostInfo(@PathParam("storage_domain_id") UUID storageDomainId,
			@QueryParam("contentViewed") String contentViewed, @QueryParam("period") String period,
			@QueryParam("startingTime") String startingTime, @QueryParam("terminalTime") String terminalTime)
			throws Exception {
		System.out.println(
				storageDomainId + "--" + contentViewed + "--" + period + "--" + startingTime + "--" + terminalTime);
		if (contentViewed.equals("Disks")) {
			if (period.equals("HOUR")) {
				return StorageDomainSamplesHistoryDao.getInstance().queryStorageDomainByMinutes(startingTime,
						storageDomainId);
			} else if (period.equals("DAY")) {
				return StorageDomainHourlyHistoryDao.getInstance().queryStorageDomainByHours(startingTime, terminalTime,
						storageDomainId);
			} else if (period.equals("WEEK") || period.equals("MONTH") || period.equals("QUARTER")
					|| period.equals("YEAR")) {
				return StorageDomainDailyHistoryDao.getInstance().queryStorageDomainByDays(startingTime, terminalTime,
						storageDomainId);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}