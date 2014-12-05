package com.software.design.cs.fivesevenfive.inventorymanagement.component.handlers;

import com.software.design.cs.fivesevenfive.inventorymanagement.component.InventoryServiceClient;
import com.software.design.cs.fivesevenfive.inventorymanagement.component.InventoryServiceConnectionException;
import com.software.design.cs.fivesevenfive.inventorymanagement.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Intermediary for connecting to the InventoryServiceClient.
 *
 * TODO: Right now is just a forwarder. Add caching to save service calls.
 * TODO: threading issues will occur on multiple updates
 */
@Component
public class PatronHandler {
    InventoryServiceClient inventoryServiceClient;
    Map<String, Patron> patronIdToPatron;

    @Autowired
    PatronHandler(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public Map<String, Patron> getPatrons() {
        try {
            this.patronIdToPatron = this.inventoryServiceClient.getPatrons();
        }
        catch (InventoryServiceConnectionException e) {
            // If no new map available, then return an empty map
            if (this.patronIdToPatron == null) {
                this.patronIdToPatron = new ConcurrentHashMap<String, Patron>();
            }
        }

        return this.patronIdToPatron;
    }

    /**
     * Get a patron with a certain id.
     *
     * @param id id of patron
     * @return patron associated with id
     */
    public Patron getPatron(long id) {
        Patron patron;

        try {
            patron = inventoryServiceClient.getPatronForId(id);

            if (this.patronIdToPatron == null) {
                this.patronIdToPatron = new ConcurrentHashMap<String, Patron>();
            }
            this.patronIdToPatron.put(Long.toString(id), patron);
        }
        catch (InventoryServiceConnectionException e)
        {
            return null;
        }

        return patron;
    }

    /**
     * Adds a patron to the pool of patrons that can rent items.
     *
     * @param id unique id of a patron
     * @param fname first name of the patron
     * @param lname last name of the patron
     * @param address address of the patron
     * @param phoneNumber phone number of the patron
     * @return
     */
    public Patron addPatron(long id, String fname, String lname, String address, String phoneNumber) {
        Patron patron;

        try {
            if (this.patronIdToPatron == null) {
                this.patronIdToPatron = new ConcurrentHashMap<String, Patron>();
            }
            patron = inventoryServiceClient.getPatronForId(id);
            this.patronIdToPatron.put(Long.toString(id), patron);
        }
        catch (InventoryServiceConnectionException e)
        {
            return null;
        }

        return patron;
    }
}
