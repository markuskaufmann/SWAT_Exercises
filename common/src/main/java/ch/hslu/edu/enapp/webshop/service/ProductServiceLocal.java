package ch.hslu.edu.enapp.webshop.service;

import ch.hslu.edu.enapp.webshop.dto.Product;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProductServiceLocal {
    List<Product> getAllProducts();
}
