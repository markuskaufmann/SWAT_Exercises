package ch.hslu.appe.fbs.remote.rmi;

import ch.hslu.appe.fbs.common.rmi.FBSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public final class RmiConnector implements ServiceConnector {

    private static final Logger LOGGER = LogManager.getLogger(RmiConnector.class);

    private static final String PROPERTY_RMI_SERVER_HOSTNAME = "java.rmi.server.hostname";

    private final String host;
    private final int port;

    private Registry registry;

    public RmiConnector(final String hostOrIpAddress, final int port) {
        this.host = hostOrIpAddress;
        this.port = port;
    }

    @Override
    public Map<FBSService, Boolean> bindServicesToRegistry(final ServiceRegistry serviceRegistry) {
        if(serviceRegistry == null) {
            return new HashMap<>();
        }
        final Map<FBSService, Boolean> serviceMap = initializeServiceMap(serviceRegistry);
        setUpRegistry();
        if(this.registry == null) {
            LOGGER.error("The registry wasn't set up properly. Cancel startup process...");
            return serviceMap;
        }
        for(final Map.Entry<FBSService, Boolean> serviceEntry : serviceMap.entrySet()) {
            try {
                bindServiceToRegistry(serviceEntry.getKey());
                serviceEntry.setValue(true);
            } catch (RemoteException | AlreadyBoundException e) {
                try {
                    LOGGER.error("Error while binding service '" + serviceEntry.getKey().getServiceName() + "'", e);
                } catch (RemoteException e1) {
                    LOGGER.error("Error while fetching service name", e1);
                }
                serviceEntry.setValue(false);
            }
        }
        return serviceMap;
    }

    private Map<FBSService, Boolean> initializeServiceMap(final ServiceRegistry serviceRegistry) {
        final Map<FBSService, Boolean> serviceMap = new HashMap<>();
        serviceMap.put(serviceRegistry.getUserService(), false);
        serviceMap.put(serviceRegistry.getCustomerService(), false);
        serviceMap.put(serviceRegistry.getItemService(), false);
        serviceMap.put(serviceRegistry.getReorderService(), false);
        return serviceMap;
    }

    private void setUpRegistry() {
        LOGGER.info("Setting up registry on " + this.host + ":" + this.port);
        System.setProperty(PROPERTY_RMI_SERVER_HOSTNAME, this.host);
        try {
            this.registry = LocateRegistry.createRegistry(this.port);
        } catch (RemoteException e) {
            LOGGER.error("Error while creating registry", e);
            try {
                this.registry = LocateRegistry.getRegistry(this.host, this.port);
            } catch (RemoteException e1) {
                LOGGER.error("Error while lookup registry", e1);
            }
        }
    }

    private void bindServiceToRegistry(final FBSService fbsService) throws RemoteException, AlreadyBoundException {
        final FBSService serviceStub = (FBSService) UnicastRemoteObject.exportObject(fbsService, 0);
        this.registry.bind(serviceStub.getServiceName(), serviceStub);
    }
}
