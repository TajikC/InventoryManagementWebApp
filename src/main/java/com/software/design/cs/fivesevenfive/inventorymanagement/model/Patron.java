package com.software.design.cs.fivesevenfive.inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Patron model for registering marshalled data. Instances of this class
 * are immutable.
 */
public class Patron {
    public String firstName;
    public String lastName;
    public String address;
    public String phoneNumber;

    @JsonCreator
    public Patron(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
                  @JsonProperty("address") String address, @JsonProperty("phoneNumber") String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return "{'firstName':'" + firstName + "', 'lastName':'" + lastName +
                "', 'address':'" + address + "', 'phoneNumber':'" + phoneNumber + "'}";
    }
}
