package com.example.vehicle.objectid;

import org.restexpress.plugin.hyperexpress.Linkable;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * This is a vehicle entity identified by a MongoDB ObjectID (instead of a
 * UUID). It also contains createdAt and updatedAt properties that are
 * automatically maintained by the persistence layer (VehicleRepository).
 */
@Entity("vehicles")
public class Vehicle
        extends AbstractMongodbEntity
        implements Linkable {

    private int year;
    private String make;
    private String model;
    private String color;
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
     * @param mileage
     */
    public Vehicle(final int year, final String make, final String model,
            final String color, final String type, final int mileage) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
        this.type = type;
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return getYear() + " " + getColor() + " " + getMake() + " " + getModel() + " " + getType();
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the make
     */
    public String getMake() {
        return make;
    }

    /**
     * @param make the make to set
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
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
}
