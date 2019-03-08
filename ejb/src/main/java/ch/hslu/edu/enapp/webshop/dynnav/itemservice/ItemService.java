package ch.hslu.edu.enapp.webshop.dynnav.itemservice;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.URL;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2018-11-16T16:28:27.165+01:00
 * Generated source version: 2.7.18
 */
@WebServiceClient(name = "Item_Service",
        wsdlLocation = "file:/C:/Users/MK/Documents/Study_Repos/ENAPP/g15-webshop/ejb/src/main/resources/META-INF/wsdl/dynnav_itemservice.wsdl",
        targetNamespace = "urn:microsoft-dynamics-schemas/page/item")
public class ItemService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn:microsoft-dynamics-schemas/page/item", "Item_Service");
    public final static QName ItemPort = new QName("urn:microsoft-dynamics-schemas/page/item", "Item_Port");

    static {
        URL url = ItemService.class.getResource("META-INF/wsdl/dynnav_itemservice.wsdl");
        if (url == null) {
            url = ItemService.class.getClassLoader().getResource("META-INF/wsdl/dynnav_itemservice.wsdl");
        }
        if (url == null) {
            java.util.logging.Logger.getLogger(ItemService.class.getName())
                    .log(java.util.logging.Level.INFO, "Cannot initialize the default wsdl from {0}", "META-INF/wsdl/dynnav_itemservice.wsdl");
        }
        WSDL_LOCATION = url;
    }


    public ItemService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ItemService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ItemService() {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ItemService(WebServiceFeature... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ItemService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ItemService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * @return returns ItemPort
     */
    @WebEndpoint(name = "Item_Port")
    public ItemPort getItemPort() {
        return super.getPort(ItemPort, ItemPort.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns ItemPort
     */
    @WebEndpoint(name = "Item_Port")
    public ItemPort getItemPort(WebServiceFeature... features) {
        return super.getPort(ItemPort, ItemPort.class, features);
    }

}
