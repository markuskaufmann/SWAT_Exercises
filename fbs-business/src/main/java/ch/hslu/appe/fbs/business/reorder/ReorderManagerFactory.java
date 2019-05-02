package ch.hslu.appe.fbs.business.reorder;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifierFactory;
import ch.hslu.appe.fbs.business.item.ItemManagerFactory;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistorFactory;

public final class ReorderManagerFactory {

    private ReorderManagerFactory() {
    }

    public static ReorderManager createReorderManager() {
        return createReorderManager(AuthorisationVerifierFactory.createAuthorisationVerifier());
    }

    public static ReorderManager createReorderManager(final AuthorisationVerifier authorisationVerifier) {
        return new ReorderManagerImpl(authorisationVerifier,
                ReorderPersistorFactory.createReorderPersistor(), ItemManagerFactory.createItemManager());
    }
}
