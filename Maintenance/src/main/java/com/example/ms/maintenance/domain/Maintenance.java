package com.example.ms.maintenance.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Maintenance
        extends AbstractLinkableEntity {

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
