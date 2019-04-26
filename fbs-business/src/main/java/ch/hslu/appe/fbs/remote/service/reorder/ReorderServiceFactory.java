package ch.hslu.appe.fbs.remote.service.reorder;

import ch.hslu.appe.fbs.business.reorder.ReorderManager;
import ch.hslu.appe.fbs.common.rmi.ReorderService;
import ch.hslu.appe.fbs.remote.rmi.RmiClient;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;

public final class ReorderServiceFactory {

    private ReorderServiceFactory() {
    }

    public static ReorderService createReorderService(final UserSessionMap userSessionMap, final ReorderManager reorderManager) {
        return new ReorderServiceImpl(new RmiClient(), userSessionMap, reorderManager);
    }
}
