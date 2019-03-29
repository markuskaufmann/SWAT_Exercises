package ch.hslu.appe.fbs.remote.rmi;

import ch.hslu.appe.fbs.common.rmi.FBSService;

import java.util.Map;

public interface ServiceConnector {
    Map<FBSService, Boolean> bindServicesToRegistry(final ServiceRegistry serviceRegistry);
}
