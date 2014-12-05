package com.software.design.cs.fivesevenfive.inventorymanagement.component;

/**
 * Exception for failing a json unmarshalling.
 */
public class UnmarshallException extends Exception {
    public UnmarshallException(Throwable t) {
        super(t);
    }
}
