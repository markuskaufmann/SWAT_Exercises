package ch.hslu.edu.enapp.webshop.business.dynnav;

import ch.hslu.edu.enapp.webshop.dto.Purchase;
import ch.hslu.edu.enapp.webshop.dynnav.statusservice.Salesorder;
import ch.hslu.edu.enapp.webshop.service.CustomerServiceLocal;
import ch.hslu.edu.enapp.webshop.service.PurchaseServiceLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;

@Startup
@Singleton
public class StatusCheckServiceBean {

    private static final Logger LOGGER = LogManager.getLogger(StatusCheckServiceBean.class);

    private static final String URL = "http://enapp-daemons.el.eee.intern:9080/EnappDaemonWeb/rest/salesorder/corr/";
    private static final String RESPONSE_STATUS_PENDING = "00";
    private static final int PENDING_COUNT_MAX = 5;

    @Inject
    private PurchaseServiceLocal purchaseService;

    @Inject
    private CustomerServiceLocal customerService;

    public StatusCheckServiceBean() {
    }

    @Asynchronous
    public void checkStatus(final Purchase purchase) {
        final int purchaseId = purchase.getId();
        LOGGER.info(String.format("CHECK STATUS: purchaseId: %s", purchaseId));
        final String correlationId = purchase.getCorrelationId();
        int pendingCount = 0;
        String responseStatus = RESPONSE_STATUS_PENDING;
        while (responseStatus.equals(RESPONSE_STATUS_PENDING) && pendingCount < PENDING_COUNT_MAX) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
            final Optional<Salesorder> optSalesOrder = processRequest(correlationId);
            if (optSalesOrder.isPresent()) {
                final Salesorder salesOrder = optSalesOrder.get();
                responseStatus = salesOrder.getStatus();
                try {
                    this.purchaseService.updateSalesOrderNo(purchaseId, salesOrder.getDynNAVSalesOrderNo());
                    Purchase updated = this.purchaseService.updateState(purchaseId, responseStatus);
                    LOGGER.info(String.format("CHECK STATUS: UPDATEDPURCHASE purchaseId: %s, salesOrderNo: %s, state: %s",
                            updated.getId(), updated.getSalesOrderNo(), updated.getState()));
                    LOGGER.info(String.format("CHECK STATUS: UPDATECUSTOMER purchaseId: %s, customer: %s", updated.getId(),
                            updated.getCustomer().getName()));
                    this.customerService.updateDynNAVId(updated.getCustomer().getName(), salesOrder.getDynNAVCustomerNo());
                } catch (Exception e) {
                    LOGGER.error("Error while updating purchase / customer data: " + e);
                }
            } else {
                LOGGER.error(String.format("Purchase: %s, CorrelationId: %s, Status: %s - SalesOrder is not present!",
                        purchaseId, correlationId, purchase.getState()));
            }
            pendingCount += 1;
        }
        if (responseStatus.equals(RESPONSE_STATUS_PENDING)) {
            LOGGER.error(String.format("Purchase: %s, CorrelationId: %s, Status: %s - Status is still pending -> Cancel",
                    purchaseId, correlationId, purchase.getState()));
        }
    }

    private Optional<Salesorder> processRequest(final String correlationId) {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(URL + correlationId);
        final Invocation.Builder builder = target.request("application/xml");
        final Response result = builder.get();
        final String xmlResponse = result.readEntity(String.class);
        LOGGER.info("XMLRESPONSE: " + xmlResponse);
        return unmarshalXml(xmlResponse);
    }

    private Optional<Salesorder> unmarshalXml(final String xml) {
        try (final Reader reader = new StringReader(xml)) {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Salesorder.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return Optional.of((Salesorder) unmarshaller.unmarshal(reader));
        } catch (Exception e) {
            LOGGER.error("Error while unmarshalling SalesOrder: " + e);
        }
        return Optional.empty();
    }
}
