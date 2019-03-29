package ch.hslu.appe.fbs.remote.service.reorder;

import ch.hslu.appe.fbs.business.reorder.ReorderManager;
import ch.hslu.appe.fbs.common.rmi.ReorderService;

public final class ReorderServiceFactory {

    private ReorderServiceFactory() {
    }

    public static ReorderService createReorderService(final ReorderManager reorderManager) {
        return new ReorderServiceImpl(reorderManager);
    }
}
