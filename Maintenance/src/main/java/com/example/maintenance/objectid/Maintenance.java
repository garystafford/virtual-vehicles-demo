package com.example.maintenance.objectid;

import org.restexpress.plugin.hyperexpress.Linkable;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This is a MAINTENANCE entity identified by a MongoDB ObjectID (instead of a
 * UUID). It also contains createdAt and updatedAt properties that are
 * automatically maintained by the persistence layer (MaintenanceRepository).
 */
public class Maintenance
        extends AbstractMongodbEntity
        implements Linkable {

    private String vehicleId;
    private String serviceDate;
    private int mileage;
    private String type;
    private String notes;

    public Maintenance() {
    }

    public Maintenance(final String vehicleId, final int mileage, String type,
            final String notes) {
        this.vehicleId = vehicleId;
        this.serviceDate = new SimpleDateFormat("yyyy-MM-dd")
                .format(Calendar.getInstance().getTime());
        this.mileage = mileage;
        this.type = type;
        this.notes = notes;
    }
}
