package com.example.restexpmongomvn.domain;

public class Vehicle
        extends AbstractLinkableEntity {

    private int year;
    private String make;
    private String model;
    private Color color;
    private String type;
    private boolean clean = false;
    private boolean oilChanged = false;

    public Vehicle() {
        // TODO Auto-generated constructor stub
    }

    public Vehicle(final int year, final String make, final String model,
            final Color color, final String type) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getYear()
     */
    public int getYear() {
        return year;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setYear(int)
     */
    public void setYear(final int year) {
        this.year = year;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getMake()
     */
    public String getMake() {
        return make;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setMake(java.lang.String)
     */
    public void setMake(final String make) {
        this.make = make;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getModel()
     */
    public String getModel() {
        return model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setModel(java.lang.String)
     */
    public void setModel(final String model) {
        this.model = model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getColor()
     */
    public Color getColor() {
        return color;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setColor(dependinject.Color)
     */
    public void setColor(final Color color) {
        this.color = color;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getType()
     */
    public String getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setType(java.lang.String)
     */
    public void setType(final String type) {
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#isClean()
     */
    public boolean isClean() {
        return clean;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setClean(boolean)
     */
    public void setClean(final boolean clean) {
        this.clean = clean;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#isOilChanged()
     */
    public boolean isOilChanged() {
        return oilChanged;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setOilChanged(boolean)
     */
    public void setOilChanged(final boolean oilChanged) {
        this.oilChanged = oilChanged;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#toString()
     */
    @Override
    public String toString() {
        return year + " " + color + " " + make + " " + model + " " + type;
    }
}
