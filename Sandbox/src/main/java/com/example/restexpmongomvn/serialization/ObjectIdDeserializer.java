package com.example.restexpmongomvn.serialization;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

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
        return new ObjectId(json.getText());
    }
}
