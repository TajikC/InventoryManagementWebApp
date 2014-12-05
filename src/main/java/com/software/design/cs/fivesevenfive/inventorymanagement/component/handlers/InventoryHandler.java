package com.software.design.cs.fivesevenfive.inventorymanagement.component.handlers;

import com.software.design.cs.fivesevenfive.inventorymanagement.component.InventoryServiceClient;
import com.software.design.cs.fivesevenfive.inventorymanagement.component.InventoryServiceConnectionException;
import com.software.design.cs.fivesevenfive.inventorymanagement.model.Item;
import com.software.design.cs.fivesevenfive.inventorymanagement.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Intermediary for connecting to the InventoryServiceClient.
 * <p/>
 * TODO: Right now is just a forwarder. Add caching to save service calls.
 * TODO: threading issues will occur on multiple updates
 */
@Component
public class InventoryHandler {
    InventoryServiceClient inventoryServiceClient;
    Map<String, Item> inventoryIdToItem;
    Map<String, Item> availableInventoryIdToItem;

    @Autowired
    InventoryHandler(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public Map<String, Item> getInventory() {
        try {
            this.inventoryIdToItem = this.inventoryServiceClient.getInventory();
        }
        catch (InventoryServiceConnectionException e) {
            // If no new map available, then return an empty map
            if (this.inventoryIdToItem == null) {
                this.inventoryIdToItem = new ConcurrentHashMap<String, Item>();
            }
        }

        return this.inventoryIdToItem;
    }

    public Map<String, Item> getAvailableInventory() {
        try {
            this.availableInventoryIdToItem = this.inventoryServiceClient.getAvailableInventory();
        }
        catch (InventoryServiceConnectionException e) {
            // If no new map available, then return an empty map
            if (this.availableInventoryIdToItem == null) {
                this.availableInventoryIdToItem = new ConcurrentHashMap<String, Item>();
            }
        }

        return this.availableInventoryIdToItem;
    }
}
