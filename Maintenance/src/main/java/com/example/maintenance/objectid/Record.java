package com.example.maintenance.objectid;

import org.restexpress.plugin.hyperexpress.Linkable;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * This is a record entity identified by a MongoDB ObjectID (instead of a
 * UUID). It also contains createdAt and updatedAt properties that are
 * automatically maintained by the persistence layer (RecordRepository).
 */
@Entity("records")
public class Record
        extends AbstractMongodbEntity
        implements Linkable {

    private String vehicleId;
    private String serviceDateTime;
    private int mileage;
    private String type;
    private String notes;

    public Record() {
    }

    public Record(final String vehicleId, final String serviceDateTime,
            final int mileage, String type, final String notes) {
        this.vehicleId = vehicleId;
        this.serviceDateTime = serviceDateTime;
        this.mileage = mileage;
        this.type = type;
        this.notes = notes;
    }

    /**
     * @return the vehicleId
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * @param vehicleId the vehicleId to set
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * @return the serviceDateTime
     */
    public String getServiceDateTime() {
        return serviceDateTime;
    }

    /**
     * @param serviceDateTime the serviceDateTime to set
     */
    public void setServiceDateTime(String serviceDateTime) {
        this.serviceDateTime = serviceDateTime;
    }

    /**
     * @return the mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * @param mileage the mileage to set
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
