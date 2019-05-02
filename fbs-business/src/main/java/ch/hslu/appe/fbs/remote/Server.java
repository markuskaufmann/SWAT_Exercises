package ch.hslu.appe.fbs.remote;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifierFactory;
import ch.hslu.appe.fbs.business.bill.BillManagerFactory;
import ch.hslu.appe.fbs.business.customer.CustomerManagerFactory;
import ch.hslu.appe.fbs.business.item.ItemManagerFactory;
import ch.hslu.appe.fbs.business.order.OrderManagerFactory;
import ch.hslu.appe.fbs.business.reorder.ReorderManagerFactory;
import ch.hslu.appe.fbs.business.user.UserManagerFactory;
import ch.hslu.appe.fbs.common.rmi.*;
import ch.hslu.appe.fbs.remote.rmi.RmiConnector;
import ch.hslu.appe.fbs.remote.rmi.RmiServiceRegistry;
import ch.hslu.appe.fbs.remote.rmi.ServiceConnector;
import ch.hslu.appe.fbs.remote.rmi.ServiceRegistry;
import ch.hslu.appe.fbs.remote.service.customer.CustomerServiceFactory;
import ch.hslu.appe.fbs.remote.service.item.ItemServiceFactory;
import ch.hslu.appe.fbs.remote.service.reorder.ReorderServiceFactory;
import ch.hslu.appe.fbs.remote.service.user.UserServiceFactory;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;
import ch.hslu.appe.fbs.remote.session.UserSessionMapFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.util.Map;

public final class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private static final String RMI_HOST = "127.0.0.1";
    private static final int RMI_PORT = 1099;

    public static void main(String[] args) {
        startup();
    }

    private static void startup() {
        LOGGER.info("Launching " + Server.class.getSimpleName() + "...");
        final ServiceRegistry rmiServiceRegistry = setUpRmiServiceRegistry();
        final ServiceConnector serviceConnector = new RmiConnector(RMI_HOST, RMI_PORT);
        final Map<FBSService, Boolean> serviceMap = serviceConnector.bindServicesToRegistry(rmiServiceRegistry);
        final boolean bindResult = verifyBindResult(serviceMap);
        if (bindResult) {
            LOGGER.info("All services successfully bound.");
        } else {
            LOGGER.error("There were some errors while binding the specific services to the registry. Shutting down...");
            System.exit(1);
        }
    }

    private static ServiceRegistry setUpRmiServiceRegistry() {
        final UserSessionMap userSessionMap = UserSessionMapFactory.createUserSessionMap();
        final AuthorisationVerifier authorisationVerifier = AuthorisationVerifierFactory.createAuthorisationVerifier();
        final UserService userService = UserServiceFactory.createUserService(userSessionMap,
                UserManagerFactory.createUserManager());
        final CustomerService customerService = CustomerServiceFactory.createCustomerService(userSessionMap,
                CustomerManagerFactory.createCustomerManager(authorisationVerifier),
                OrderManagerFactory.createOrderManager(authorisationVerifier),
                BillManagerFactory.createBillManager(authorisationVerifier));
        final ItemService itemService = ItemServiceFactory.createItemService(userSessionMap,
                ItemManagerFactory.createItemManager(authorisationVerifier));
        final ReorderService reorderService = ReorderServiceFactory.createReorderService(userSessionMap,
                ReorderManagerFactory.createReorderManager(authorisationVerifier));
        return new RmiServiceRegistry(userService, customerService, itemService, reorderService);
    }

    private static boolean verifyBindResult(final Map<FBSService, Boolean> serviceMap) {
        if (serviceMap == null || serviceMap.isEmpty()) {
            return false;
        }
        for (Map.Entry<FBSService, Boolean> serviceEntry : serviceMap.entrySet()) {
            try {
                final boolean bindResult = serviceEntry.getValue();
                LOGGER.info("BindResult " + serviceEntry.getKey().getServiceName() + ": " + bindResult);
                if (!bindResult) {
                    return false;
                }
            } catch (RemoteException e) {
                LOGGER.error("Error while fetching service name", e);
            }
        }
        return true;
    }
}
