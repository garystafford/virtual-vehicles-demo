package com.example.vehicle.objectid;

import org.restexpress.plugin.hyperexpress.Linkable;

import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;

/**
 * This is a vehicle entity identified by a MongoDB ObjectID (instead of a
 * UUID). It also contains createdAt and updatedAt properties that are
 * automatically maintained by the persistence layer
 * (VehicleOidEntityRepository).
 */
public class Vehicle
        extends AbstractMongodbEntity
        implements Linkable {

    private int year;
    private String make;
    private String model;
    private Color color;
    private String type;
    private int mileage;

    /**
     *
     */
    public Vehicle() {
    }

    /**
     *
     * @param year
     * @param make
     * @param model
     * @param color
     * @param type
     */
    public Vehicle(final int year, final String make, final String model,
            final Color color, final String type) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
        this.type = type;
    }

    @Override
    public String toString() {
        return year + " " + color + " " + make + " " + model + " " + type;
    }
}
