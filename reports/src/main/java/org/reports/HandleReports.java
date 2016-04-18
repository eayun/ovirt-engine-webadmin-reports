package org.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.ovirt.engine.sdk.Api;
import org.ovirt.engine.sdk.decorators.HostNIC;
import org.ovirt.engine.sdk.decorators.HostNICs;
import org.ovirt.engine.sdk.decorators.VMNIC;
import org.ovirt.engine.sdk.decorators.VMNICs;
import org.ovirt.engine.sdk.decorators.Host;
import org.ovirt.engine.sdk.entities.VM;
import org.ovirt.engine.sdk.exceptions.ServerException;
import org.ovirt.engine.sdk.exceptions.UnsecuredConnectionAttemptError;

public class HandleReports {
	private List<UUID> interfaceIdList = new ArrayList<UUID>();
	//new Api("https://192.168.9.167/api", "admin@internal", "abc123", null, 443, 10, false, true, false, false);
	
	// 根据虚拟机的 id，找到该虚拟机所有网络接口的 id,没有考虑时间的问题，因为在某个时间段内用户是可以对虚拟机的接口进行增删的。
//	public List<UUID> getVmInterfaceIdsByVmId(UUID vm_id) throws InterruptedException, ClientProtocolException, ServerException, UnsecuredConnectionAttemptError, IOException{
//		Api api = Backend.getApi(10000);
//		VM vm = api.getVMs().get(vm_id);
//		VMNICs nics = (VMNICs)vm.getNics();
//		List<VMNIC> lvmnic = nics.list();
//		for(int i = 0; i < lvmnic.size(); i ++){
//			interfaceIdList.add(UUID.fromString(lvmnic.get(i).getId()));
//		}
//		return interfaceIdList;
//    }
//	
//	// 根据主机的 id，找到该主机所有网络接口的 id
//	public List<UUID> getHostInterfaceIdsByHostId(UUID host_id) throws ClientProtocolException, ServerException, UnsecuredConnectionAttemptError, IOException, InterruptedException{
//		Api api = Backend.getApi(10000);
//		Host host = api.getHosts().get(host_id);
//		HostNICs nics = host.getHostNics();
//		List<HostNIC> lhostnic = nics.list();
//		for (int i = 0; i < lhostnic.size(); i ++){
//			interfaceIdList.add(UUID.fromString(lhostnic.get(i).getId()));
//		}
//		return interfaceIdList;
//	}
	
	
}
