package com.software.design.cs.fivesevenfive.inventorymanagement.component;


import org.springframework.stereotype.Component;

/**
 * A component for reading configuration values for the webapp.
 *
 * TODO: Currently hard coded coniguration options. Change to read a file instead.
 * TODO: Consider using @Value annotations and .properties file
 */
@Component
public class AppConfig {
    private String inventoryServiceScheme = "http";
    private String inventoryServiceHost = "127.0.0.1";
    private int inventoryServicePort = 9000;

    public AppConfig() {
    }

    public String getInventoryServiceUrl() {
        String url = this.getInventoryServiceScheme() + "://" +
                this.getInventoryServiceHost() + ":" +
                this.getInventoryServicePort();

        return url;
    }

    public String getInventoryServiceHost() {
        return this.inventoryServiceHost;
    }

    public int getInventoryServicePort() {
        return this.inventoryServicePort;
    }

    public String getInventoryServiceScheme() {
        return this.inventoryServiceScheme;
    }
}
