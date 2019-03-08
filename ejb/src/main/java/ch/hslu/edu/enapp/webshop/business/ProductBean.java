package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.dto.Product;
import ch.hslu.edu.enapp.webshop.entity.ProductEntity;
import ch.hslu.edu.enapp.webshop.mapping.ProductWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductBean implements ch.hslu.edu.enapp.webshop.service.ProductServiceLocal {

    private static final Logger LOGGER = LogManager.getLogger(ProductBean.class);

    @PersistenceContext
    private EntityManager em;

    public ProductBean() {
    }

    @Override
    public List<Product> getAllProducts() {
        final TypedQuery<ProductEntity> query = this.em.createNamedQuery("getProducts", ProductEntity.class);
        final List<ProductEntity> productsFound = query.getResultList();
        final List<Product> productsDTO = new ArrayList<>();
        productsFound.forEach(productEntity -> {
            final Product product = ProductWrapper.entityToDto(productEntity);
            productsDTO.add(product);
        });
        return productsDTO;
    }
}
