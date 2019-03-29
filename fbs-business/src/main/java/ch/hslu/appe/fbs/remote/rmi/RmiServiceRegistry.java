package ch.hslu.appe.fbs.remote.rmi;

import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.ReorderService;
import ch.hslu.appe.fbs.common.rmi.UserService;

public class RmiServiceRegistry implements ServiceRegistry {

    private final UserService userService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final ReorderService reorderService;

    public RmiServiceRegistry(final UserService userService,
                              final CustomerService customerService,
                              final ItemService itemService,
                              final ReorderService reorderService) {
        this.userService = userService;
        this.customerService = customerService;
        this.itemService = itemService;
        this.reorderService = reorderService;
    }

    @Override
    public UserService getUserService() {
        return this.userService;
    }

    @Override
    public CustomerService getCustomerService() {
        return this.customerService;
    }

    @Override
    public ItemService getItemService() {
        return this.itemService;
    }

    @Override
    public ReorderService getReorderService() {
        return this.reorderService;
    }
}
