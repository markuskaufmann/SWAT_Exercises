package ch.hslu.appe.fbs.business.item;

import ch.hslu.appe.fbs.business.stock.StockFactory;
import ch.hslu.appe.fbs.data.item.ItemPersistorFactory;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistorFactory;

public final class ItemManagerFactory {

    private ItemManagerFactory() {
    }

    public static ItemManager createItemManager() {
        return new ItemManagerImpl(ItemPersistorFactory.createItemPersistor(), ReorderPersistorFactory.createReorderPersistor(),
                StockFactory.getStock());
    }
}
