package ch.hslu.appe.fbs.business.reorder;

import ch.hslu.appe.fbs.business.item.ItemManagerFactory;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistorFactory;

public final class ReorderManagerFactory {

    private ReorderManagerFactory() {
    }

    public static ReorderManager getReorderManager() {
        return new ReorderManagerImpl(ReorderPersistorFactory.createReorderPersistor(), ItemManagerFactory.getItemManager());
    }
}
