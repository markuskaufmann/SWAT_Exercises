package ch.hslu.edu.enapp.webshop.jsf;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.dto.Purchase;
import ch.hslu.edu.enapp.webshop.service.PurchaseServiceLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class PurchaseJSF {

    @Inject
    private PurchaseServiceLocal purchaseService;

    public List<Purchase> getPurchases(final Customer customer) {
        return this.purchaseService.getPurchasesByCustomer(customer);
    }
}
