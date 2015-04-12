package com.example.ms.vehicle.serialization;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkableCollection;
import com.example.ms.vehicle.domain.Vehicle;
import com.strategicgains.restexpress.serialization.xml.DefaultXmlProcessor;

public class XmlSerializationProcessor
        extends DefaultXmlProcessor {

    public XmlSerializationProcessor() {
        super();
        alias("vehicle", Vehicle.class);
        alias("link", Link.class);
        alias("collection", LinkableCollection.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
        registerConverter(new XstreamObjectIdConverter());
    }
}
