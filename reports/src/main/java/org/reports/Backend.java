package org.reports;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.http.client.ClientProtocolException;
import org.ovirt.engine.sdk.exceptions.ServerException;
import org.ovirt.engine.sdk.exceptions.UnsecuredConnectionAttemptError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton(name = "Scheduler")
@Startup
public class Backend {
    private static Logger log = LoggerFactory.getLogger(Backend.class);
    // private Api api = null;
    private static DataSource ds;
    public static Connection conn;
    public static Connection locateDataSource() throws NamingException, SQLException {
    	InitialContext cxt = new InitialContext();
    	ds = (DataSource) cxt.lookup( "java:/ReportsDataSource" );
    	if ( ds == null ) {
    		throw new RuntimeException("Data source not found!");
    	}
    	conn = ds.getConnection();
    	return conn;
    }
    
    public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		Backend.conn = conn;
	}

	public static void setDs(DataSource ds) {
		Backend.ds = ds;
	}

	public static DataSource getDs() {
    	return ds;
    }

	@PostConstruct
    public void init()
            throws ClientProtocolException, ServerException, UnsecuredConnectionAttemptError, IOException, InterruptedException, SQLException {
        try {
            locateDataSource();
        } catch (NamingException e) {
            log.error("Error locating datasource.");
        }
//        int interval = Integer.parseInt(ConfigProvider.getConfig().getProperty(ConfigProvider.QUERY_INTERVAL_M));
//        api = getApi(interval);
    }
//
//    @PreDestroy
//    public void beforeShutdown() {
//        try {
//			api.close();
//		} catch (Exception e) {
//			log.error("Error closing api object", e);
//		}
//    }

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
    
}
