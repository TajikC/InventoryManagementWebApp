package com.software.design.cs.fivesevenfive.inventorymanagement.controllers;


import com.software.design.cs.fivesevenfive.inventorymanagement.component.InventoryServiceClient;
import com.software.design.cs.fivesevenfive.inventorymanagement.component.handlers.InventoryHandler;
import com.software.design.cs.fivesevenfive.inventorymanagement.component.handlers.PatronHandler;
import com.software.design.cs.fivesevenfive.inventorymanagement.model.Item;
import com.software.design.cs.fivesevenfive.inventorymanagement.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Controller for the main application.
 */
@Controller
public class RootController {
    InventoryServiceClient inventoryServiceClient;
    InventoryHandler inventoryHandler;
    PatronHandler patronHandler;

    @Autowired
    public RootController(InventoryServiceClient inventoryServiceClient, InventoryHandler inventoryHandler,
                          PatronHandler patronHandler) {
        this.inventoryServiceClient = inventoryServiceClient;
        this.inventoryHandler = inventoryHandler;
        this.patronHandler = patronHandler;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getRootApplication() {
        ModelAndView mav = new ModelAndView("root");

        // Get required variables
        Map<String, Item> inventory = inventoryHandler.getAvailableInventory();
        mav.addObject("inventory", inventory);
        Map<String, Patron> patrons = patronHandler.getPatrons();
        mav.addObject("patrons", patrons);

        return mav;
    }

    @RequestMapping(value = "/testServiceConnection/{id}")
    @ResponseBody
    public String getTestService(@PathVariable long id) {
        try {
            Patron patron = inventoryServiceClient.getPatronForId(id);
            return patron.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/rent", method = RequestMethod.POST)
    public String rent(@RequestBody String postPayload) {
        System.out.print("hello");

        return "";
    }
}
