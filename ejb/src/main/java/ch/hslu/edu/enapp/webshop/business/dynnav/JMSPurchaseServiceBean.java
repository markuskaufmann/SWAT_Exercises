package ch.hslu.edu.enapp.webshop.business.dynnav;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.dto.Product;
import ch.hslu.edu.enapp.webshop.dto.Purchase;
import ch.hslu.edu.enapp.webshop.dto.PurchaseItem;
import ch.hslu.edu.enapp.webshop.dynnav.purchaseservice.DNPurchaseCustomer;
import ch.hslu.edu.enapp.webshop.dynnav.purchaseservice.DNPurchaseItem;
import ch.hslu.edu.enapp.webshop.dynnav.purchaseservice.PurchaseMessage;
import ch.hslu.edu.enapp.webshop.entity.ProductEntity;
import ch.hslu.edu.enapp.webshop.exception.PurchaseMessageSendException;
import ch.hslu.edu.enapp.webshop.mapping.ProductWrapper;
import ch.hslu.edu.enapp.webshop.postfinance.paymentservice.Ncresponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stateless
public class JMSPurchaseServiceBean {

    private static final Logger LOGGER = LogManager.getLogger(JMSPurchaseServiceBean.class);

    private static final String STUD_ID = "ibkaufma";
    private static final String CORR_ID_PREFIX = "g15";

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "jms/enappQueueAMQ")
    private Queue enappQueue;

    @Resource(name = "WebshopRemoteJmsQueueConnectionFactory")
    private ConnectionFactory connectionFactory;

    public JMSPurchaseServiceBean() {
    }

    public String sendJMSPurchaseMessage(final Purchase purchase, final Ncresponse ncresponse) throws PurchaseMessageSendException {
        if (purchase == null) {
            throw new IllegalArgumentException("The provided purchase can't be null");
        }
        if (ncresponse == null) {
            throw new IllegalArgumentException("The provided ncresponse can't be null");
        }
        try (final Connection connection = this.connectionFactory.createConnection();
             final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            final PurchaseMessage purchaseMessage = constructPurchaseMessage(purchase, ncresponse);
            final String marshalledPurchaseMessage = marshalPurchaseMessage(purchaseMessage);
            LOGGER.info("Marshalled purchase message: " + marshalledPurchaseMessage);
            final MessageProducer messageProducer = session.createProducer(this.enappQueue);
            final TextMessage textMessage = session.createTextMessage(marshalledPurchaseMessage);
            textMessage.setStringProperty("MessageFormat", "Version 1.5");
            final String correlationId = CORR_ID_PREFIX + UUID.randomUUID().toString().replace("-", "").trim();
            textMessage.setJMSCorrelationID(correlationId);
            messageProducer.send(textMessage);
            return correlationId;
        } catch (Exception e) {
            LOGGER.error("Error while sending purchase message: " + e);
            throw new PurchaseMessageSendException("Error while sending purchase message: ", e);
        }
    }

    private PurchaseMessage constructPurchaseMessage(final Purchase purchase, final Ncresponse ncresponse)
            throws PurchaseMessageSendException {
        final PurchaseMessage purchaseMessage = new PurchaseMessage(ncresponse.getPAYID(), purchase.getId(), STUD_ID,
                purchase.getTotalCost(), purchase.getDatetime());
        final Customer customer = purchase.getCustomer();
        final String dynNavId = customer.getDynNAVId() == null ? "" : customer.getDynNAVId();
        final String name = customer.getFirstname() + " " + customer.getLastname();
        final DNPurchaseCustomer purchaseCustomer = new DNPurchaseCustomer(dynNavId, name, customer.getAddress(), "", "",
                customer.getName());
        purchaseMessage.setPurchaseCustomer(purchaseCustomer);
        final List<DNPurchaseItem> purchaseItems = new ArrayList<>();
        for (final PurchaseItem purchaseItem : purchase.getPurchaseItems()) {
            final String productNo = purchaseItem.getProductNo();
            final TypedQuery<ProductEntity> productQuery = this.em.createNamedQuery("getProductByItemNo", ProductEntity.class);
            productQuery.setParameter("itemNo", productNo);
            final List<ProductEntity> productEntities = productQuery.getResultList();
            if (productEntities.isEmpty()) {
                throw new PurchaseMessageSendException(String.format("No product with ItemNo %s found.", productNo));
            }
            final Product product = ProductWrapper.entityToDto(productEntities.get(0));
            final String origDesc = product.getOriginalDescription();
            final String productDesc = origDesc.length() > 50 ? origDesc.substring(0, 50) : origDesc;
            final int quantity = purchaseItem.getQuantity();
            final BigDecimal totalLineCost = product.getUnitprice().multiply(BigDecimal.valueOf(quantity));
            final DNPurchaseItem dnPurchaseItem = new DNPurchaseItem(product.getItemNo(), productDesc, quantity, totalLineCost);
            purchaseItems.add(dnPurchaseItem);
        }
        purchaseMessage.setPurchaseItems(purchaseItems);
        return purchaseMessage;
    }

    private String marshalPurchaseMessage(final PurchaseMessage purchaseMessage) throws JAXBException {
        if (purchaseMessage == null) {
            throw new IllegalArgumentException("The provided purchase message can't be null");
        }
        final JAXBContext jaxbContext = JAXBContext.newInstance(PurchaseMessage.class);
        final StringWriter writer = new StringWriter();
        final Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(purchaseMessage, writer);
        return writer.toString();
    }
}
