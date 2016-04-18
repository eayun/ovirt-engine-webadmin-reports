package org.reports;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.VmDailyHistoryDao;
import org.reports.dao.VmDisksUsageDailyHistoryDao;
import org.reports.dao.VmDisksUsageHourlyHistoryDao;
import org.reports.dao.VmDisksUsageSamplesHistoryDao;
import org.reports.dao.VmHourlyHistoryDao;
import org.reports.dao.VmInterfaceDailyHistoryDao;
import org.reports.dao.VmInterfaceHourlyHistoryDao;
import org.reports.dao.VmInterfaceSamplesHistoryDao;
import org.reports.dao.VmSampleHistoryDao;
import org.reports.model.VmInterfaceDailyHistory;
import org.reports.model.VmInterfaceHourlyHistory;
import org.reports.model.VmInterfaceSamplesHistory;

@Path("/vmReports")
public class VmReports {
	@GET
	@Path("{vm_id}")
	// 通过虚拟机的 id，要查看的内容（cpu, mem, ...），日期，从数据库中获取数据
	public List getListOnVmInfo(@PathParam("vm_id") UUID vmId,
			 										@QueryParam("contentViewed") String contentViewed,
			 										@QueryParam("period") String period, 
			 										@QueryParam("startingTime") String startingTime,
			 										@QueryParam("terminalTime") String terminalTime) throws Exception{
		System.out.println(vmId + "--" + contentViewed + "--" + period + "--" + startingTime);
		if (contentViewed.equals("CPU")){
			// 获取一个小时内的数据？
			if (period.equals("HOUR")){
				return VmSampleHistoryDao.getInstance().queryCpuByMinutes(startingTime, vmId);
			}
			// 获取一天内所有每小时的数据（或者几个小时内的数据）
			else if (period.equals("DAY")){
				return VmHourlyHistoryDao.getInstance().queryCpuByHours(startingTime, terminalTime, vmId);
			}
			else if (period.equals("WEEK") || period.equals("MONTH") || period.equals("QUARTER") || period.equals("YEAR")){
				return VmDailyHistoryDao.getInstance().queryCpuByDays(startingTime, terminalTime, vmId);
			}
			else{
				return null;
			}
		} 
		else if (contentViewed.equals("Memory")){
			// 获取一个小时内的数据？（每分钟变化一次）
			if (period.equals("时")){
				return VmSampleHistoryDao.getInstance().queryMemoryByTime(startingTime, vmId);
			}
			else if (period.equals("DAY")){
				return VmHourlyHistoryDao.getInstance().queryMemoryByHours(startingTime, terminalTime, vmId);
			}
			else if (period.equals("WEEK") || period.equals("MONTH") || period.equals("QUARTER") || period.equals("YEAR")) {
				return VmDailyHistoryDao.getInstance().queryMemoryByDays(startingTime, terminalTime, vmId);
			}
			else {
				return null;
 			}
		} 
		else if (contentViewed.equals("Network")) {
			// 将 vm 中所有的网络接口封装成一个 List 传给前端。
			List<UUID> interfaceIdsOfOneVm = new ArrayList<UUID>();
			List<List<VmInterfaceSamplesHistory>> llvish = new ArrayList<List<VmInterfaceSamplesHistory>>();
			List<List<VmInterfaceHourlyHistory>> llvihh = new ArrayList<List<VmInterfaceHourlyHistory>>();
			List<List<VmInterfaceDailyHistory>> llvidh = new ArrayList<List<VmInterfaceDailyHistory>>();
 			if (period.equals("HOUR")) {
 				interfaceIdsOfOneVm = VmInterfaceSamplesHistoryDao.getInstance().queryVmInterfaceIdsByVmIdAndPeriod(startingTime, vmId);
				for (int i = 0; i < interfaceIdsOfOneVm.size(); i ++) {
					llvish.add(VmInterfaceSamplesHistoryDao.getInstance().queryNetworkRateByMinutes(startingTime, interfaceIdsOfOneVm.get(i)));
				}
				return llvish;
			}
			else if (period.equals("DAY")) {
				interfaceIdsOfOneVm = VmInterfaceHourlyHistoryDao.getInstance().queryVmInterfaceIdsByVmIdAndPeriod(startingTime, terminalTime, vmId);
				for (int i = 0; i < interfaceIdsOfOneVm.size(); i ++) {
					llvihh.add(VmInterfaceHourlyHistoryDao.getInstance().queryNetworkRateByHours(startingTime, terminalTime, interfaceIdsOfOneVm.get(i)));
				}
				return llvihh;
			}
			else if (period.equals("WEEK") || period.equals("MONTH") || period.equals("QUARTER") || period.equals("YEAR")) {
				interfaceIdsOfOneVm = VmInterfaceHourlyHistoryDao.getInstance().queryVmInterfaceIdsByVmIdAndPeriod(startingTime, terminalTime, vmId);
				for (int i = 0; i < interfaceIdsOfOneVm.size(); i ++) {
					 llvidh.add(VmInterfaceDailyHistoryDao.getInstance().queryNetworkRateByDays(startingTime, terminalTime, interfaceIdsOfOneVm.get(i)));
				}
				return llvidh;
			}
			else {
				return null;
			}
		}
		else if (contentViewed.equals("Disks")) {
			// 将 vm 中所有的磁盘的使用率封装成一个 List 传给前端。
			if (period.equals("HOUR")){
				return VmDisksUsageSamplesHistoryDao.getInstance().queryDisksByMinutes(startingTime, vmId);
			}
			else if (period.equals("DAY")) {
				return VmDisksUsageHourlyHistoryDao.getInstance().queryDisksByHours(startingTime, terminalTime, vmId);
			}
			else if (period.equals("WEEK") || period.equals("MONTH") || period.equals("QUARTER") || period.equals("YEAR")) {
				return VmDisksUsageDailyHistoryDao.getInstance().queryDisksByDays(startingTime, terminalTime, vmId);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
}