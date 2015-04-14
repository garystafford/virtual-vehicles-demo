package com.example.vehicle.serialization;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.strategicgains.repoexpress.mongodb.Identifiers;

/**
 *
 * @author gstafford
 */
public class ObjectIdDeserializer
        extends JsonDeserializer<ObjectId> {

    /**
     *
     * @param json
     * @param context
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public ObjectId deserialize(JsonParser json, DeserializationContext context)
            throws IOException, JsonProcessingException {
        return (ObjectId) Identifiers.MONGOID.parse(json.getText()).primaryKey();
    }
}
