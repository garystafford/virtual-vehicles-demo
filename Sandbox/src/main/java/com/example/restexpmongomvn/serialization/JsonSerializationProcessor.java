package com.example.restexpmongomvn.serialization;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.strategicgains.restexpress.serialization.json.DefaultJsonProcessor;

/**
 *
 * @author gstafford
 */
public class JsonSerializationProcessor
        extends DefaultJsonProcessor {

    /**
     *
     * @param module
     */
    @Override
    protected void initializeModule(SimpleModule module) {
        super.initializeModule(module);
        module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
    }
}
