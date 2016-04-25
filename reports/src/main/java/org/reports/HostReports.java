package org.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.HostDailyHistoryDao;
import org.reports.dao.HostHourlyHistoryDao;
import org.reports.dao.HostInterfaceDailyHistoryDao;
import org.reports.dao.HostInterfaceHourlyHistoryDao;
import org.reports.dao.HostInterfaceSamplesHistoryDao;
import org.reports.dao.HostSamplesHistoryDao;
import org.reports.model.HostInterfaceDailyHistory;
import org.reports.model.HostInterfaceHourlyHistory;
import org.reports.model.HostInterfaceSamplesHistory;

@Path("/HOSTREPORTS")
public class HostReports {
	// 最后用 List 接收说有的 List

	@GET
	@Path("{host_id}")
	public List getListOnHostInfo(@PathParam("host_id") UUID hostId, @QueryParam("contentViewed") String contentViewed,
			@QueryParam("period") String period, @QueryParam("startingTime") String startingTime,
			@QueryParam("terminalTime") String terminalTime) throws Exception {
		if (contentViewed.equals("CPU")) {
			if (period.equals("MINUTE")) {
				return HostSamplesHistoryDao.getInstance().queryCpuByMinutes(startingTime, terminalTime, hostId);
			} else if (period.equals("HOUR")) {
				return HostHourlyHistoryDao.getInstance().queryCpuByHours(startingTime, terminalTime, hostId);
			} else if (period.equals("DAY")) {
				return HostDailyHistoryDao.getInstance().queryCpuByDays(startingTime, terminalTime, hostId);
			} else {
				return null;
			}
		} else if (contentViewed.equals("Memory")) {
			if (period.equals("MINUTE")) {
				return HostSamplesHistoryDao.getInstance().queryMemoryByMinutes(startingTime, terminalTime, hostId);
			} else if (period.equals("HOUR")) {
				return HostHourlyHistoryDao.getInstance().queryMemoryByHours(startingTime, terminalTime, hostId);
			} else if (period.equals("DAY")) {
				return HostDailyHistoryDao.getInstance().queryMemoryByDays(startingTime, terminalTime, hostId);
			} else {
				return null;
			}
		} else if (contentViewed.equals("Network")) {
			List<UUID> interfaceIdsOfOneHost = new ArrayList<UUID>();
			List<List<HostInterfaceSamplesHistory>> llhish = new ArrayList<List<HostInterfaceSamplesHistory>>();
			List<List<HostInterfaceHourlyHistory>> llhihh = new ArrayList<List<HostInterfaceHourlyHistory>>();
			List<List<HostInterfaceDailyHistory>> llhidh = new ArrayList<List<HostInterfaceDailyHistory>>();
			if (period.equals("MINUTE")) {
				interfaceIdsOfOneHost = HostInterfaceSamplesHistoryDao.getInstance()
						.queryHostInterfaceIdsByHostIdAndPeriod(startingTime, terminalTime, hostId);
				for (int i = 0; i < interfaceIdsOfOneHost.size(); i++) {
					llhish.add(HostInterfaceSamplesHistoryDao.getInstance().queryNetworkRateByMinutes(startingTime, terminalTime, interfaceIdsOfOneHost.get(i)));
				}
				return llhish;
			} else if (period.equals("HOUR")) {
				interfaceIdsOfOneHost = HostInterfaceHourlyHistoryDao.getInstance().queryHostInterfaceIdsByHostIdAndPeriod(startingTime, terminalTime, hostId);
				for (int i = 0; i < interfaceIdsOfOneHost.size(); i++) {
					llhihh.add(HostInterfaceHourlyHistoryDao.getInstance().queryNetworkRateByHours(startingTime,
							terminalTime, interfaceIdsOfOneHost.get(i)));
				}
				return llhihh;
			} else if (period.equals("DAY")) {
				interfaceIdsOfOneHost = HostInterfaceDailyHistoryDao.getInstance().queryHostInterfaceIdsByHostIdAndPeriod(startingTime, terminalTime, hostId);
				for (int i = 0; i < interfaceIdsOfOneHost.size(); i++) {
					llhidh.add(HostInterfaceDailyHistoryDao.getInstance().queryNetworkRateByDays(startingTime,
							terminalTime, interfaceIdsOfOneHost.get(i)));
				}
				return llhidh;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
