package ch.hslu.appe.fbs.remote.service.item;

import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.remote.rmi.RmiClient;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;

public final class ItemServiceFactory {

    private ItemServiceFactory() {
    }

    public static ItemService createItemService(final UserSessionMap userSessionMap, final ItemManager itemManager) {
        return new ItemServiceImpl(new RmiClient(), userSessionMap, itemManager);
    }
}
