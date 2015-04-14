package com.example.valet.objectid;

import org.restexpress.plugin.hyperexpress.Linkable;

import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;

/**
 * This is a valet entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically
 * maintained by the persistence layer (ValetRepository).
 */
public class Valet
        extends AbstractMongodbEntity
        implements Linkable {

    private String vehicleId;
    private String dateTimeIn;
    private String dateTimeOut;
    private String parkingLot;
    private int parkingSpot;
    private String notes;

    public Valet() {
    }

    public Valet(final String vehicleId, final String dateTimeIn,
            final String parkingLot, final int parkingSpot,
            final String notes) {
        this.vehicleId = vehicleId;
        this.dateTimeIn = dateTimeIn;
        this.parkingLot = parkingLot;
        this.parkingSpot = parkingSpot;
        this.notes = notes;
    }
}
