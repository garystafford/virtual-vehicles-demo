package com.example.maintenance.serialization;

import com.example.maintenance.objectid.Record;
import org.restexpress.serialization.xml.XstreamXmlProcessor;

public class XmlSerializationProcessor
        extends XstreamXmlProcessor {

    public XmlSerializationProcessor() {
        super();
        alias("maintenance", Record.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
        registerConverter(new XstreamOidConverter());
    }
}
