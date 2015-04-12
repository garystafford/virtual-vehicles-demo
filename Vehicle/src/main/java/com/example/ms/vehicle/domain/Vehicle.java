package com.example.ms.vehicle.domain;

/**
 *
 * @author gstafford
 */
public class Vehicle
        extends AbstractLinkableEntity {

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
