package ch.hslu.edu.enapp.webshop.mapping;

import ch.hslu.edu.enapp.webshop.dto.Product;
import ch.hslu.edu.enapp.webshop.entity.ProductEntity;

public final class ProductWrapper {

    public static Product entityToDto(final ProductEntity productEntity) {
        if (productEntity == null) {
            throw new IllegalArgumentException("The provided product entity can't be null");
        }
        return new Product(productEntity.getId(), productEntity.getItemNo(), productEntity.getName(), productEntity.getDescription(),
                productEntity.getOriginalDescription(), productEntity.getMediapath(), productEntity.getUnitprice());
    }
}
