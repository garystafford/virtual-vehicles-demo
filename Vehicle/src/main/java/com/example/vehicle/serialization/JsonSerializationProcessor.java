package com.example.vehicle.serialization;

import org.bson.types.ObjectId;
import org.restexpress.ContentType;
import org.restexpress.serialization.json.JacksonJsonProcessor;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.strategicgains.hyperexpress.domain.hal.HalResource;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceDeserializer;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceSerializer;

/**
 *
 * @author gstafford
 */
public class JsonSerializationProcessor
        extends JacksonJsonProcessor {

    /**
     *
     */
    public JsonSerializationProcessor() {
        super();
        addSupportedMediaTypes(ContentType.HAL_JSON);
    }

    /**
     *
     * @param module
     */
    @Override
    protected void initializeModule(SimpleModule module) {
        super.initializeModule(module);
        // For MongoDB ObjectId as entity identifiers...
        module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());

        // Support HalResource (de)serialization.
        module.addDeserializer(HalResource.class, new HalResourceDeserializer());
        module.addSerializer(HalResource.class, new HalResourceSerializer());
    }
}
