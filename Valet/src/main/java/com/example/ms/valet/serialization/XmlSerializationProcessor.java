package com.example.ms.valet.serialization;

import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkableCollection;
import com.example.ms.valet.domain.Valet;
import com.strategicgains.restexpress.serialization.xml.DefaultXmlProcessor;

public class XmlSerializationProcessor
        extends DefaultXmlProcessor {

    public XmlSerializationProcessor() {
        super();
        alias("valet", Valet.class);
        alias("link", Link.class);
        alias("collection", LinkableCollection.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
        registerConverter(new XstreamObjectIdConverter());
    }
}
