package com.example.authentication.serialization;

import com.example.authentication.objectid.Client;
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
        alias("client", Client.class);
        registerConverter(new XstreamOidConverter());
    }
}
