package com.example.vehicle.serialization;

import com.example.vehicle.objectid.Vehicle;
import org.restexpress.serialization.xml.XstreamXmlProcessor;

/**
 *
 * @author gstafford
 */
public class XmlSerializationProcessor
        extends XstreamXmlProcessor {

    /**
     *
     */
    public XmlSerializationProcessor() {
        super();
        alias("vehicle", Vehicle.class);
        registerConverter(new XstreamOidConverter());
    }
}
