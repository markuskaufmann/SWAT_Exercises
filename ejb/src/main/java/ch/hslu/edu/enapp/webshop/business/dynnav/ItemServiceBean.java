package ch.hslu.edu.enapp.webshop.business.dynnav;

import ch.hslu.edu.enapp.webshop.dynnav.itemservice.*;
import ch.hslu.edu.enapp.webshop.entity.ProductEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.namespace.QName;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton
public class ItemServiceBean {

    private static final Logger LOGGER = LogManager.getLogger(ItemServiceBean.class);

    private static final String WSDL_LOCATION = "http://enapp-was-global02.el.eee.intern:7047/DynamicsNAVTest/WS/iCompany%20HSLU%20T%26A/Page/Item";

    private static final String DOMAIN = "ICOMPANY";
    private static final String USER = "icDynNAVWsStudentSvc";
    private static final String PASSWORD = "ic0mp@ny";

    private static final String NAMESPACE_URI = "urn:microsoft-dynamics-schemas/page/item";
    private static final String SERVICE_NAME = "Item_Service";

    private static final String ITEM_FILTER = "MP3";

    @PersistenceContext
    private EntityManager em;

    private List<Item> items;

    public ItemServiceBean() {
    }

    @PostConstruct
    private void init() {
        loadItemsIntoStock();
    }

    @Schedule(hour = "*", persistent = false)
    private void loadItemsIntoStock() {
        readItemsFromDynNAV();
        persistItemsToStock();
    }

    private void persistItemsToStock() {
        if (this.items == null || this.items.isEmpty()) {
            LOGGER.error("There were no items loaded from the webservice. Please check the connection specifics.");
            return;
        }
        try {
            this.em.createQuery("DELETE FROM ProductEntity p").executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while truncating product table: " + e);
            return;
        }
        this.items.forEach(item -> {
            String itemName = "";
            String itemDesc = "";
            final String description = item.getDescription() != null ? item.getDescription().trim() : "";
            final String[] itemDescParts = description.split(" - ");
            if (itemDescParts.length > 1) {
                if (itemDescParts.length == 3) {
                    itemName = itemDescParts[2];
                    itemDesc = String.format("Author: %s, Track No.: %s", itemDescParts[0], itemDescParts[1]);
                } else if (itemDescParts.length == 2) {
                    itemName = itemDescParts[1];
                    itemDesc = String.format("Author: %s", itemDescParts[0]);
                }
            } else {
                final int idxOpenBracket = description.indexOf("(");
                final int idxCloseBracket = description.indexOf(")");
                if (idxOpenBracket != -1) {
                    itemName = description.substring(0, idxOpenBracket).trim();
                    itemDesc = String.format("Author: %s", description.substring(idxOpenBracket + 1,
                            idxCloseBracket != -1 ? idxCloseBracket : description.length()).trim());
                } else {
                    itemName = itemDesc = itemDescParts[0];
                }
            }
            final ProductEntity productEntity = new ProductEntity();
            productEntity.setItemNo(item.getNo());
            productEntity.setName(itemName);
            productEntity.setDescription(itemDesc);
            productEntity.setOriginalDescription(item.getDescription());
            productEntity.setMediapath(item.getMediafileName());
            productEntity.setUnitprice(item.getUnitPrice());
            this.em.persist(productEntity);
            this.em.flush();
        });
        LOGGER.info("The local stock has been updated with the items from the webservice");
    }

    private void readItemsFromDynNAV() {
        try {
            final URL wsdl = new URL(WSDL_LOCATION);
            final QName itemPageQName = new QName(NAMESPACE_URI, SERVICE_NAME);
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(DOMAIN + "\\" + USER, PASSWORD.toCharArray());
                }
            });
            final ItemService itemService = new ItemService(wsdl, itemPageQName);
            final ItemPort itemPort = itemService.getItemPort();
            final List<ItemFilter> filterList = new ArrayList<>();
            final ItemFilter itemFilter = new ItemFilter();
            itemFilter.setField(ItemFields.PRODUCT_GROUP_CODE);
            itemFilter.setCriteria(ITEM_FILTER);
            filterList.add(itemFilter);
            final ItemList itemList = itemPort.readMultiple(filterList, null, 0);
            this.items = itemList.getItem();
        } catch (Exception e) {
            LOGGER.error("Error while reading items from web service: ", e);
        }
    }
}
