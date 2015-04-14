package com.example.vehicle.serialization;

import org.bson.types.ObjectId;

import com.strategicgains.repoexpress.mongodb.Identifiers;
import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * @author toddf
 * @since Feb 16, 2011
 */
public class XstreamOidConverter
        implements SingleValueConverter {

    /**
     *
     * @param aClass
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean canConvert(Class aClass) {
        return ObjectId.class.isAssignableFrom(aClass);
    }

    /**
     *
     * @param value
     * @return
     */
    @Override
    public Object fromString(String value) {
        return (ObjectId) Identifiers.MONGOID.parse(value).primaryKey();
    }

    /**
     *
     * @param objectId
     * @return
     */
    @Override
    public String toString(Object objectId) {
        return ((ObjectId) objectId).toString();
    }
}
