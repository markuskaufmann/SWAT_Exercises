package ch.hslu.appe.fbs.business.item;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifierFactory;
import ch.hslu.appe.fbs.business.stock.StockFactory;
import ch.hslu.appe.fbs.data.item.ItemPersistorFactory;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistorFactory;

public final class ItemManagerFactory {

    private ItemManagerFactory() {
    }

    public static ItemManager createItemManager() {
        return createItemManager(AuthorisationVerifierFactory.createAuthorisationVerifier());
    }

    public static ItemManager createItemManager(final AuthorisationVerifier authorisationVerifier) {
        return new ItemManagerImpl(authorisationVerifier,
                ItemPersistorFactory.createItemPersistor(), ReorderPersistorFactory.createReorderPersistor(),
                StockFactory.getStock());
    }
}
