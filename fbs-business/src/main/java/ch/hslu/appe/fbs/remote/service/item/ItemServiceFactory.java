package ch.hslu.appe.fbs.remote.service.item;

import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.common.rmi.ItemService;

public final class ItemServiceFactory {

    private ItemServiceFactory() {
    }

    public static ItemService createItemService(final ItemManager itemManager) {
        return new ItemServiceImpl(itemManager);
    }
}
