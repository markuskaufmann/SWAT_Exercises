package ch.hslu.edu.enapp.webshop.business.postfinance;

import ch.hslu.edu.enapp.webshop.dto.BasketItem;
import ch.hslu.edu.enapp.webshop.dto.PaymentAssembly;
import ch.hslu.edu.enapp.webshop.dto.PaymentMethod;
import ch.hslu.edu.enapp.webshop.exception.PaymentFailedException;
import ch.hslu.edu.enapp.webshop.postfinance.paymentservice.Ncresponse;
import ch.hslu.edu.enapp.webshop.service.CashierServiceLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Stateless
public class PaymentServiceBean {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceBean.class);

    private static final Map<String, String> REQUEST_PARAMS = new HashMap<>();

    private static final String PARAM_KEY_PSPID = "PSPID";
    private static final String PARAM_KEY_USERID = "USERID";
    private static final String PARAM_KEY_PSWD = "PSWD";
    private static final String PARAM_KEY_SHASIGN = "SHASIGN";
    private static final String PARAM_KEY_ORDERID = "ORDERID";
    private static final String PARAM_KEY_AMOUNT = "AMOUNT";
    private static final String PARAM_KEY_CURRENCY = "CURRENCY";
    private static final String PARAM_KEY_CARD_NO = "CARDNO";
    private static final String PARAM_KEY_CARD_EXP = "ED";
    private static final String PARAM_KEY_CARD_CVC = "CVC";
    private static final String PARAM_KEY_OPERATION = "OPERATION";

    private static final String URL = "https://e-payment.postfinance.ch/ncol/test/orderdirect.asp";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String SHA1_PASS_IN = "hslu!comp@ny.websh0p";
    private static final String ORDERID_PREFIX = "g15";

    private static final String NCSTATUS_OK = "0";
    private static final String NCERROR_OK = "0";

    private static final Map<Integer, PaymentMethod> PAYMENT_METHODS = new LinkedHashMap<>();

    static {
        PAYMENT_METHODS.put(0, new PaymentMethod(0, "Mastercard", "5399999999999999", "123", "1219", "mastercard.png"));
        PAYMENT_METHODS.put(1, new PaymentMethod(1, "Visa", "4111111111111111", "345", "1220", "visa.png"));
        PAYMENT_METHODS.put(2, new PaymentMethod(2, "AmericanExpress", "374111111111111", "567", "1221", "americanexpress.png"));

        REQUEST_PARAMS.put(PARAM_KEY_PSPID, "HSLUiCompany");
        REQUEST_PARAMS.put(PARAM_KEY_USERID, "enappstudents");
        REQUEST_PARAMS.put(PARAM_KEY_PSWD, "ds2H9ZV.p!8r");
        REQUEST_PARAMS.put(PARAM_KEY_CURRENCY, "CHF");
        REQUEST_PARAMS.put(PARAM_KEY_OPERATION, "RES");
    }

    @Inject
    private CashierServiceLocal cashierService;

    public PaymentServiceBean() {
    }

    public List<PaymentMethod> getAvailablePaymentMethods() {
        return new ArrayList<>(PAYMENT_METHODS.values());
    }

    public Ncresponse sendPaymentRequest(final int paymentMethodId, final List<BasketItem> basketItems)
            throws PaymentFailedException {
        final PaymentMethod paymentMethod = PAYMENT_METHODS.get(paymentMethodId);
        if (paymentMethod == null) {
            throw new IllegalArgumentException("The provided payment method id '"
                    + String.valueOf(paymentMethodId)
                    + "' is invalid");
        }
        if (basketItems == null || basketItems.isEmpty()) {
            throw new IllegalArgumentException("The provided list of basket items can't be null or empty");
        }
        LOGGER.info("SEND PAYMENT REQUEST: " + paymentMethod.getIssuer() + ", " + basketItems.size());
        final PaymentAssembly paymentAssembly = this.cashierService.processPaymentAssembly(basketItems);
        final Map<String, String> requestParams = new TreeMap<>(REQUEST_PARAMS);
        setPaymentMethod(requestParams, paymentMethod);
        final String orderId = ORDERID_PREFIX + UUID.randomUUID().toString().replace("-", "");
        final int totalCost = new BigDecimal(paymentAssembly.getTotalCost()).multiply(BigDecimal.valueOf(100)).intValue();
        requestParams.put(PARAM_KEY_ORDERID, orderId);
        requestParams.put(PARAM_KEY_AMOUNT, String.valueOf(totalCost));
        generateSHA1Hash(requestParams);
        final Ncresponse ncresponse = processRequest(requestParams);
        final String ncStatus = ncresponse.getNCSTATUS().trim();
        final String ncError = ncresponse.getNCERROR().trim();
        final String ncErrorPlus = ncresponse.getNCERRORPLUS().trim();
        final String status = ncresponse.getSTATUS().trim();
        final String response = String.format("NCRESPONSE: PayId=%s, NCSTATUS=%s, NCERROR=%s, NCERRORPLUS=%s, status=%s",
                ncresponse.getPAYID(), ncStatus, ncError, ncErrorPlus, status);
        LOGGER.info(response);
        if (!NCSTATUS_OK.equals(ncStatus) || !NCERROR_OK.equals(ncError)) {
            throw new PaymentFailedException("Error during payment process - " + response);
        }
        return ncresponse;
    }

    private void setPaymentMethod(final Map<String, String> requestParams, final PaymentMethod paymentMethod) {
        requestParams.put(PARAM_KEY_CARD_NO, paymentMethod.getCardNo());
        requestParams.put(PARAM_KEY_CARD_EXP, paymentMethod.getExpiration());
        requestParams.put(PARAM_KEY_CARD_CVC, paymentMethod.getCvc());
    }

    private Ncresponse processRequest(final Map<String, String> requestParams) throws PaymentFailedException {
        final MultivaluedMap<String, String> formData = new MultivaluedHashMap<>(requestParams);
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(URL);
        final Invocation.Builder builder = target.request();
        final Response result = builder.header(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE).post(Entity.form(formData));
        final String xmlResponse = result.readEntity(String.class);
        return unmarshalXml(xmlResponse);
    }

    private Ncresponse unmarshalXml(final String xml) throws PaymentFailedException {
        try (final Reader reader = new StringReader(xml)) {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Ncresponse.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Ncresponse) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new PaymentFailedException("Error while unmarshalling xml response: ", e);
        }
    }

    private void generateSHA1Hash(final Map<String, String> requestParams) throws PaymentFailedException {
        final StringBuilder builder = new StringBuilder();
        LOGGER.info(String.format("REQUESTPARAMS = %s", requestParams));
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append(SHA1_PASS_IN);
        }
        LOGGER.info(String.format("REQUESTPARAMS after CONC = %s", builder.toString()));
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(builder.toString().getBytes(StandardCharsets.UTF_8));
            final String shaSignature = String.format("%040x", new BigInteger(1, digest.digest()));
            LOGGER.info(String.format("SHA signature = %s", shaSignature));
            requestParams.put(PARAM_KEY_SHASIGN, shaSignature);
        } catch (Exception e) {
            throw new PaymentFailedException("Error while generating hash: ", e);
        }
    }
}
