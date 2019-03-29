package ch.hslu.appe.fbs.remote.rmi;

import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.ReorderService;
import ch.hslu.appe.fbs.common.rmi.UserService;

public interface ServiceRegistry {
    UserService getUserService();
    CustomerService getCustomerService();
    ItemService getItemService();
    ReorderService getReorderService();
}
