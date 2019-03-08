package ch.hslu.edu.enapp.webshop.mapping;

import ch.hslu.edu.enapp.webshop.dto.PurchaseItem;
import ch.hslu.edu.enapp.webshop.entity.PurchaseitemEntity;

final class PurchaseItemWrapper {

    static PurchaseItem entityToDto(final PurchaseitemEntity purchaseitemEntity) {
        if (purchaseitemEntity == null) {
            throw new IllegalArgumentException("The provided purchaseitem entity can't be null");
        }
        return new PurchaseItem(purchaseitemEntity.getId(), purchaseitemEntity.getPurchase(), purchaseitemEntity.getProduct(),
                purchaseitemEntity.getQuantity());
    }
}
