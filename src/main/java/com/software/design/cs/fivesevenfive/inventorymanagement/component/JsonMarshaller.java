package com.software.design.cs.fivesevenfive.inventorymanagement.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.software.design.cs.fivesevenfive.inventorymanagement.model.Item;
import com.software.design.cs.fivesevenfive.inventorymanagement.model.Patron;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * For marshalling and unmarshalling json received from the inventory management service.
 * This spring bean will abstract the mapper calls from components that use this.
 */
@Component
public class JsonMarshaller {
    private ObjectMapper mapper;

    public JsonMarshaller() {
        this.mapper = new ObjectMapper();
    }

    public Patron deserializePatron(Reader reader) throws UnmarshallException {
        Patron patron = (Patron)deserialize(reader, Patron.class);

        return patron;
    }

    public Map<String, Patron> deserializePatrons(Reader reader) throws UnmarshallException {
        TypeFactory factory = mapper.getTypeFactory();
        MapType mapType = factory.constructMapType(HashMap.class, String.class, Patron.class);
        Map<String, Patron> patrons = (Map<String, Patron>)deserialize(reader, mapType);

        return patrons;
    }

    public Map<String, Item> deserializeInventory(Reader reader) throws UnmarshallException {
        TypeFactory factory = mapper.getTypeFactory();
        MapType mapType = factory.constructMapType(HashMap.class, String.class, Item.class);
        Map<String, Item> items = (Map<String, Item>)deserialize(reader, mapType);

        return items;
    }

    private Object deserialize(Reader reader, Class clazz) throws UnmarshallException {
        Object object;

        try {
            object = this.mapper.readValue(reader, clazz);
            return object;
        }
        catch (IOException e) {
            // TODO: some logging here
            throw new UnmarshallException(e);
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {
                // log failure here
            }
        }
    }

    private Object deserialize(Reader reader, MapType mapType) throws UnmarshallException {
        Object object;

        try {
            object = this.mapper.readValue(reader, mapType);
            return object;
        }
        catch (IOException e) {
            // TODO: some logging here
            throw new UnmarshallException(e);
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {
                // log failure here
            }
        }
    }
}