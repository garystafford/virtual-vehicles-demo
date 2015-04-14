package com.example.vehicle.serialization;

import org.restexpress.response.ErrorResponseWrapper;
import org.restexpress.response.ResponseWrapper;
import org.restexpress.serialization.AbstractSerializationProvider;
import org.restexpress.serialization.SerializationProcessor;

/**
 *
 * @author gstafford
 */
public class SerializationProvider
        extends AbstractSerializationProvider {
    // SECTION: CONSTANTS

    private static final SerializationProcessor JSON_SERIALIZER = new JsonSerializationProcessor();
    private static final SerializationProcessor XML_SERIALIZER = new XmlSerializationProcessor();
    private static final ResponseWrapper RESPONSE_WRAPPER = new ErrorResponseWrapper();

    /**
     *
     */
    public SerializationProvider() {
        super();
        add(JSON_SERIALIZER, RESPONSE_WRAPPER, true);
        add(XML_SERIALIZER, RESPONSE_WRAPPER);
    }

    /**
     *
     * @return
     */
    public static SerializationProcessor json() {
        return JSON_SERIALIZER;
    }

    /**
     *
     * @return
     */
    public static SerializationProcessor xml() {
        return XML_SERIALIZER;
    }
}
