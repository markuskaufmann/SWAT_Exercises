package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.dto.BasketItem;
import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.dto.PaymentAssembly;
import ch.hslu.edu.enapp.webshop.dto.Purchase;
import ch.hslu.edu.enapp.webshop.entity.CustomerEntity;
import ch.hslu.edu.enapp.webshop.entity.PurchaseEntity;
import ch.hslu.edu.enapp.webshop.entity.PurchaseitemEntity;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;
import ch.hslu.edu.enapp.webshop.mapping.PurchaseWrapper;
import ch.hslu.edu.enapp.webshop.service.CashierServiceLocal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Stateless
public class PurchaseBean implements ch.hslu.edu.enapp.webshop.service.PurchaseServiceLocal {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CashierServiceLocal cashierService;

    public PurchaseBean() {
    }

    @Override
    public List<Purchase> getPurchasesByCustomer(final Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("The provided customer can't be null");
        }
        final TypedQuery<PurchaseEntity> purchaseQuery = this.em.createNamedQuery("getPurchasesByCustomer", PurchaseEntity.class);
        purchaseQuery.setParameter("name", customer.getName());
        final List<PurchaseEntity> purchases = purchaseQuery.getResultList();
        final List<Purchase> purchasesDTO = new ArrayList<>();
        purchases.forEach(purchaseEntity -> {
            final Purchase purchase = PurchaseWrapper.entityToDto(purchaseEntity);
            purchasesDTO.add(purchase);
        });
        return purchasesDTO;
    }

    @Override
    public List<Purchase> getPurchasesByState(final String state) {
        if (state == null) {
            throw new IllegalArgumentException("The provided state can't be null");
        }
        final TypedQuery<PurchaseEntity> purchaseQuery = this.em.createNamedQuery("getPurchasesByState", PurchaseEntity.class);
        purchaseQuery.setParameter("state", state);
        final List<PurchaseEntity> purchases = purchaseQuery.getResultList();
        final List<Purchase> purchasesDTO = new ArrayList<>();
        purchases.forEach(purchaseEntity -> {
            final Purchase purchase = PurchaseWrapper.entityToDto(purchaseEntity);
            purchasesDTO.add(purchase);
        });
        return purchasesDTO;
    }

    @Override
    public Purchase persistPurchase(final Customer customer, final List<BasketItem> basketItems) throws NoCustomerFoundException {
        if (customer == null) {
            throw new IllegalArgumentException("The provided customer can't be null");
        }
        if (basketItems == null || basketItems.isEmpty()) {
            throw new IllegalArgumentException("The provided list of basket items can't be null or empty");
        }
        final PaymentAssembly paymentAssembly = this.cashierService.processPaymentAssembly(basketItems);
        final TypedQuery<CustomerEntity> customerQuery = this.em.createNamedQuery("getCustomerByName", CustomerEntity.class);
        customerQuery.setParameter("name", customer.getName());
        final List<CustomerEntity> customerEntities = customerQuery.getResultList();
        if (customerEntities.isEmpty()) {
            throw new NoCustomerFoundException("No customer has been found - provided name: " + customer.getName());
        }
        final CustomerEntity customerEntity = customerEntities.get(0);
        final PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setCustomer(customerEntity.getName());
        purchaseEntity.setCustomerByCustomer(customerEntity);
        purchaseEntity.setDatetime(Timestamp.from(Instant.now()));
        purchaseEntity.setTotalCost(new BigDecimal(paymentAssembly.getTotalCost()));
        purchaseEntity.setState("-1");
        this.em.persist(purchaseEntity);
        this.em.flush();
        final List<PurchaseitemEntity> purchaseitems = new ArrayList<>();
        basketItems.forEach(basketItem -> {
            final PurchaseitemEntity purchaseitemEntity = new PurchaseitemEntity();
            purchaseitemEntity.setProduct(basketItem.getProduct().getItemNo());
            purchaseitemEntity.setPurchase(purchaseEntity.getId());
            purchaseitemEntity.setQuantity(basketItem.getQuantity());
            purchaseitems.add(purchaseitemEntity);
        });
        purchaseitems.forEach(purchaseitemEntity -> {
            this.em.persist(purchaseitemEntity);
        });
        purchaseEntity.setPurchaseitemsById(purchaseitems);
        this.em.flush();
        return PurchaseWrapper.entityToDto(purchaseEntity);
    }

    @Override
    public Purchase updatePayId(final int purchaseId, final String payId) {
        return updateSpecId(purchaseId, payId, UpdateIdType.PAY_ID);
    }

    @Override
    public Purchase updateCorrelationId(final int purchaseId, final String correlationId) {
        return updateSpecId(purchaseId, correlationId, UpdateIdType.CORRELATION_ID);
    }

    @Override
    public Purchase updateSalesOrderNo(final int purchaseId, final String salesOrderNo) {
        return updateSpecId(purchaseId, salesOrderNo, UpdateIdType.SALESORDER_NO);
    }

    @Override
    public Purchase updateState(final int purchaseId, final String state) {
        return updateSpecId(purchaseId, state, UpdateIdType.STATE);
    }

    private Purchase updateSpecId(final int purchaseId, final String idToUpdate, final UpdateIdType idType) {
        if (purchaseId < 0) {
            throw new IllegalArgumentException("The provided purchaseId can't be < 0");
        }
        if (idToUpdate == null || idToUpdate.trim().length() == 0) {
            throw new IllegalArgumentException("The provided " + idType.name() + " can't be null or empty");
        }
        final PurchaseEntity purchaseEntity = this.em.find(PurchaseEntity.class, purchaseId);
        idType.accept(purchaseEntity, idToUpdate);
        this.em.flush();
        return PurchaseWrapper.entityToDto(purchaseEntity);
    }

    private enum UpdateIdType implements BiConsumer<PurchaseEntity, String> {
        CORRELATION_ID {
            @Override
            public void accept(PurchaseEntity purchaseEntity, String s) {
                purchaseEntity.setCorrelationId(s.trim());
            }
        },
        PAY_ID {
            @Override
            public void accept(PurchaseEntity purchaseEntity, String s) {
                purchaseEntity.setPayId(s.trim());
            }
        },
        SALESORDER_NO {
            @Override
            public void accept(PurchaseEntity purchaseEntity, String s) {
                purchaseEntity.setSalesOrderNo(s.trim());
            }
        },
        STATE {
            @Override
            public void accept(PurchaseEntity purchaseEntity, String s) {
                purchaseEntity.setState(s.trim());
            }
        };
    }
}
