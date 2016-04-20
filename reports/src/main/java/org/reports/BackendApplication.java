package org.reports;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class BackendApplication extends Application {

    private final Set<Object> singletons = new HashSet<Object>();

    @Override
    public Set<Object> getSingletons () {
        return singletons;
    }

    public BackendApplication() {
        singletons.add(new VmReports());
        singletons.add(new HostReports());
        singletons.add(new StorageDomainReports());
    }

}