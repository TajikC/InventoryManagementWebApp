package com.software.design.cs.fivesevenfive.inventorymanagement.component;

/**
 * Exception when connecting to InventoryService fails.
 */
public class InventoryServiceConnectionException extends Exception {
    public InventoryServiceConnectionException(Throwable t) {
        super(t);
    }
}