package com.example.ms.valet.domain;

/**
 *
 * @author gstafford
 */
public class Valet
        extends AbstractLinkableEntity {

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
