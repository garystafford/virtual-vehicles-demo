package com.example.valet.objectid;

import org.restexpress.plugin.hyperexpress.Linkable;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * This is a transaction entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically
 * maintained by the persistence layer (TransactionTransactionRepository).
 */
@Entity("transactions")
public class Transaction
        extends AbstractMongodbEntity
        implements Linkable {

    private String vehicleId;
    private String dateTimeIn;
    private String dateTimeOut;
    private String parkingLot;
    private int parkingSpot;
    private String notes;

    public Transaction() {
    }

    public Transaction(final String vehicleId, final String dateTimeIn,
            final String parkingLot, final int parkingSpot,
            final String notes) {
        this.vehicleId = vehicleId;
        this.dateTimeIn = dateTimeIn;
        this.parkingLot = parkingLot;
        this.parkingSpot = parkingSpot;
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
     * @return the dateTimeIn
     */
    public String getDateTimeIn() {
        return dateTimeIn;
    }

    /**
     * @param dateTimeIn the dateTimeIn to set
     */
    public void setDateTimeIn(String dateTimeIn) {
        this.dateTimeIn = dateTimeIn;
    }

    /**
     * @return the dateTimeOut
     */
    public String getDateTimeOut() {
        return dateTimeOut;
    }

    /**
     * @param dateTimeOut the dateTimeOut to set
     */
    public void setDateTimeOut(String dateTimeOut) {
        this.dateTimeOut = dateTimeOut;
    }

    /**
     * @return the parkingLot
     */
    public String getParkingLot() {
        return parkingLot;
    }

    /**
     * @param parkingLot the parkingLot to set
     */
    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    /**
     * @return the parkingSpot
     */
    public int getParkingSpot() {
        return parkingSpot;
    }

    /**
     * @param parkingSpot the parkingSpot to set
     */
    public void setParkingSpot(int parkingSpot) {
        this.parkingSpot = parkingSpot;
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
