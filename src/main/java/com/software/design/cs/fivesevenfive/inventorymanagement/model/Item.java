package com.software.design.cs.fivesevenfive.inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Item model for registering marshalled data. Instances of this class
 * are immutable.
 */
public class Item {
    public String id;
    public String name;
    public BigDecimal price;
    public String type;

    @JsonCreator
    public Item(@JsonProperty("id") String id, @JsonProperty("name") String name,
                @JsonProperty("price") BigDecimal price, @JsonProperty("type") String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
