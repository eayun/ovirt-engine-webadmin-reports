package org.reports;

import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.reports.dao.VHSConfigurationDao;

@Path("/GETCREATEDDATE")
public class GetCreatedDate {
	@GET
	@Path("{id}") // vm_id, host_id, storage domain id
	public String getCreatedDate(@PathParam("id") UUID Id, @QueryParam("entity") String Entity) throws SQLException{
		return VHSConfigurationDao.getInstance().queryVmOrHostOrStorageDomainCreatedDate(Id, Entity);
	}		
}
