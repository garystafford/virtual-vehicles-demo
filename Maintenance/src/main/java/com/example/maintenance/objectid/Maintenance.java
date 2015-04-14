package com.example.maintenance.objectid;

import org.restexpress.plugin.hyperexpress.Linkable;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;

/**
 * This is a MAINTENANCE entity identified by a MongoDB ObjectID (instead of a
 * UUID). It also contains createdAt and updatedAt properties that are
 * automatically maintained by the persistence layer (MaintenanceRepository).
 */
public class Maintenance
        extends AbstractMongodbEntity
        implements Linkable {

    private String vehicleId;
    private String serviceDateTime;
    private int mileage;
    private String type;
    private String notes;

    public Maintenance() {
    }

    public Maintenance(final String vehicleId, final String serviceDateTime,
            final int mileage, String type, final String notes) {
        this.vehicleId = vehicleId;
        this.serviceDateTime = serviceDateTime;
        this.mileage = mileage;
        this.type = type;
        this.notes = notes;
    }
}
