package com.example.valet.serialization;

import com.example.valet.objectid.Transaction;
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
        alias("valet", Transaction.class);
        registerConverter(new XstreamOidConverter());
    }
}
