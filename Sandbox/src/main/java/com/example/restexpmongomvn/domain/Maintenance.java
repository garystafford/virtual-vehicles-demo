package com.example.restexpmongomvn.domain;

/**
 *
 * @author gstafford
 */
public class Maintenance {

    private Vehicle vehicle;

    /**
     *
     */
    public Maintenance() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param vehicle
     */
    public Maintenance(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Maintenance#washVehicle()
     */
    /**
     *
     */
    public void washVehicle() {
        vehicle.setClean(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Maintenance#changeOil()
     */
    /**
     *
     */
    public void changeOil() {
        vehicle.setOilChanged(true);
    }

    /**
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @param vehicle the vehicle to set
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
