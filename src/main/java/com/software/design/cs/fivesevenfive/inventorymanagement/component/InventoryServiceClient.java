package com.software.design.cs.fivesevenfive.inventorymanagement.component;

import com.software.design.cs.fivesevenfive.inventorymanagement.model.Item;
import com.software.design.cs.fivesevenfive.inventorymanagement.model.Patron;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Class for connecting to the InventoryService. This makes http calls to
 * the service and then passes unmarshalled objects back to its caller.
 */
@Component
public class InventoryServiceClient {
    private final JsonMarshaller jsonMarshaller;
    private final HttpClient httpClient;

    private String serviceScheme;
    private String serviceHost;
    private int servicePort;

    @Autowired
    public InventoryServiceClient(AppConfig appConfig, JsonMarshaller jsonMarshaller) {
        this.jsonMarshaller = jsonMarshaller;

        this.httpClient = HttpClientBuilder.create().build();
        this.serviceScheme = appConfig.getInventoryServiceScheme();
        this.serviceHost = appConfig.getInventoryServiceHost();
        this.servicePort = appConfig.getInventoryServicePort();
    }

    public Patron getPatronForId(long id) throws InventoryServiceConnectionException {
        try {
            String path = "/patrons/" + id;
            URI uri = this.getBaseBuilder().setPath(path).build();

            HttpGet httpGet = new HttpGet(uri);
            Reader reader = getResponseReader(httpGet);
            Patron patron = jsonMarshaller.deserializePatron(reader);

            return patron;
        } catch (Exception e) {
            // Log fatal here
            throw new InventoryServiceConnectionException(e);
        }
    }

    public Map<String, Patron> getPatrons() throws InventoryServiceConnectionException {
        try {
            String path = "/patrons";
            URI uri = this.getBaseBuilder().setPath(path).build();

            HttpGet httpGet = new HttpGet(uri);
            Reader reader = getResponseReader(httpGet);
            Map<String, Patron> patrons = jsonMarshaller.deserializePatrons(reader);

            return patrons;
        } catch (Exception e) {
            // Log fatal here
            throw new InventoryServiceConnectionException(e);
        }
    }

    public Map<String, Item> getInventory() throws InventoryServiceConnectionException {
        try {
            String path = "/inventory";
            URI uri = this.getBaseBuilder().setPath(path).build();

            HttpGet httpGet = new HttpGet(uri);
            Reader reader = getResponseReader(httpGet);
            Map<String, Item> inventory = jsonMarshaller.deserializeInventory(reader);

            return inventory;
        } catch (Exception e) {
            // Log fatal here
            throw new InventoryServiceConnectionException(e);
        }
    }

    public Map<String, Item> getAvailableInventory() throws InventoryServiceConnectionException {
        try {
            String path = "/inventory/available";
            URI uri = this.getBaseBuilder().setPath(path).build();

            HttpGet httpGet = new HttpGet(uri);
            Reader reader = getResponseReader(httpGet);
            Map<String, Item> inventory = jsonMarshaller.deserializeInventory(reader);

            return inventory;
        } catch (Exception e) {
            // Log fatal here
            throw new InventoryServiceConnectionException(e);
        }
    }

    private Reader getResponseReader(HttpUriRequest request) throws ClientProtocolException, IOException {
        HttpResponse respone = httpClient.execute(request);

        // check status code
        StatusLine statusLine = respone.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode >= 300) {
            throw new HttpResponseException(statusCode, statusLine.getReasonPhrase());
        }

        //Get response body
        HttpEntity entity = respone.getEntity();
        if (entity == null) {
            throw new ClientProtocolException("No content in response");
        }
        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();
        Reader reader = new InputStreamReader(entity.getContent(), charset);

        return reader;
    }

    private URIBuilder getBaseBuilder() {
        URIBuilder uriBuilder = new URIBuilder().setScheme(this.serviceScheme).
                setHost(this.serviceHost).setPort(this.servicePort);

        return uriBuilder;
    }
}