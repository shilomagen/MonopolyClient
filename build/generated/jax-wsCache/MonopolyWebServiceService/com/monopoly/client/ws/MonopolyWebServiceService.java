
package com.monopoly.client.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "MonopolyWebServiceService", targetNamespace = "http://monopoly.ws/", wsdlLocation = "http://localhost:8080/monopolyapi/MonopolyWebServiceService?wsdl")
public class MonopolyWebServiceService
    extends Service
{

    private final static URL MONOPOLYWEBSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException MONOPOLYWEBSERVICESERVICE_EXCEPTION;
    private final static QName MONOPOLYWEBSERVICESERVICE_QNAME = new QName("http://monopoly.ws/", "MonopolyWebServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/monopolyapi/MonopolyWebServiceService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MONOPOLYWEBSERVICESERVICE_WSDL_LOCATION = url;
        MONOPOLYWEBSERVICESERVICE_EXCEPTION = e;
    }

    public MonopolyWebServiceService() {
        super(__getWsdlLocation(), MONOPOLYWEBSERVICESERVICE_QNAME);
    }

    public MonopolyWebServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), MONOPOLYWEBSERVICESERVICE_QNAME, features);
    }

    public MonopolyWebServiceService(URL wsdlLocation) {
        super(wsdlLocation, MONOPOLYWEBSERVICESERVICE_QNAME);
    }

    public MonopolyWebServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MONOPOLYWEBSERVICESERVICE_QNAME, features);
    }

    public MonopolyWebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MonopolyWebServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MonopolyWebService
     */
    @WebEndpoint(name = "MonopolyWebServicePort")
    public MonopolyWebService getMonopolyWebServicePort() {
        return super.getPort(new QName("http://monopoly.ws/", "MonopolyWebServicePort"), MonopolyWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MonopolyWebService
     */
    @WebEndpoint(name = "MonopolyWebServicePort")
    public MonopolyWebService getMonopolyWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://monopoly.ws/", "MonopolyWebServicePort"), MonopolyWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MONOPOLYWEBSERVICESERVICE_EXCEPTION!= null) {
            throw MONOPOLYWEBSERVICESERVICE_EXCEPTION;
        }
        return MONOPOLYWEBSERVICESERVICE_WSDL_LOCATION;
    }

}
