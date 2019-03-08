package ch.hslu.edu.enapp.webshop.jsf;

import ch.hslu.edu.enapp.webshop.dto.Product;
import ch.hslu.edu.enapp.webshop.service.ProductServiceLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ProductJSF {

    @Inject
    private ProductServiceLocal productService;

    public List<Product> getProducts() {
        return this.productService.getAllProducts();
    }
}
