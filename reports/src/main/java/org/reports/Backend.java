package org.reports;

import java.io.IOException;
import java.net.NoRouteToHostException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;
import org.ovirt.engine.sdk.Api;
import org.ovirt.engine.sdk.exceptions.ServerException;
import org.ovirt.engine.sdk.exceptions.UnsecuredConnectionAttemptError;
import org.reports.utils.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton(name = "Scheduler")
@Startup
public class Backend {
    private static Logger log = LoggerFactory.getLogger(Backend.class);
    private static Api api = null;
    private static DataSource ds;

    @SuppressWarnings("deprecation")
	@PostConstruct
    public void init()
            throws ClientProtocolException, ServerException, UnsecuredConnectionAttemptError, IOException, InterruptedException {
        try {
            locateDataSource();
        } catch (NamingException e) {
            log.error("Error locating datasource.");
        }
//        int interval = Integer.parseInt(ConfigProvider.getConfig().getProperty(ConfigProvider.QUERY_INTERVAL_M));
//        api = getApi(interval);
    }

    @PreDestroy
    public void beforeShutdown() {
        try {
			api.close();
		} catch (Exception e) {
			log.error("Error closing api object", e);
		}
    }

//    @SuppressWarnings("deprecation")
//	public static Api getApi(int interval)
//            throws ClientProtocolException, ServerException, UnsecuredConnectionAttemptError, IOException, InterruptedException{
//        try {
//            return new Api(
//                    ConfigProvider.getConfig().getProperty(ConfigProvider.SDK_BASE_URL),
//                    ConfigProvider.getConfig().getProperty(ConfigProvider.SDK_USER),
//                    ConfigProvider.getConfig().getProperty(ConfigProvider.SDK_PASSWORD),
//                    null, 443, 10, true, true, false, false);
//        } catch (HttpHostConnectException|NoRouteToHostException|ServerException e) {
//            log.error("Refused while getting the api connection, retrying in " + interval/1000 + " seconds...");
//            Thread.sleep(interval);
//            return getApi(interval + 10000);
//        }
//    }
    
    public static DataSource locateDataSource() throws NamingException {
        InitialContext cxt = new InitialContext();
        ds = (DataSource) cxt.lookup( "java:/ReportsDataSource" );
        if ( ds == null ) {
           throw new RuntimeException("Data source not found!");
        }
        System.out.println("---------------------------" + ds + "-----------------------------");
        return ds;
    }

	public static DataSource getDs() {
		return ds;
	}
}
