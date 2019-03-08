package ch.hslu.edu.enapp.webshop.mapping;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.dto.Purchase;
import ch.hslu.edu.enapp.webshop.dto.PurchaseItem;
import ch.hslu.edu.enapp.webshop.entity.PurchaseEntity;

import java.util.List;

public final class PurchaseWrapper {

    public static Purchase entityToDto(final PurchaseEntity purchaseEntity) {
        if (purchaseEntity == null) {
            throw new IllegalArgumentException("The provided purchase entity can't be null");
        }
        final Customer customer = CustomerWrapper.entityToDto(purchaseEntity.getCustomerByCustomer());
        final Purchase purchase = new Purchase(purchaseEntity.getId(), customer, purchaseEntity.getDatetime(),
                purchaseEntity.getTotalCost(), purchaseEntity.getPayId(), purchaseEntity.getCorrelationId(),
                purchaseEntity.getSalesOrderNo(), purchaseEntity.getState());
        final List<PurchaseItem> purchaseItems = purchase.getPurchaseItems();
        purchaseEntity.getPurchaseitemsById().forEach(purchaseitemEntity -> {
            final PurchaseItem purchaseItem = PurchaseItemWrapper.entityToDto(purchaseitemEntity);
            purchaseItems.add(purchaseItem);
        });
        return purchase;
    }
}
