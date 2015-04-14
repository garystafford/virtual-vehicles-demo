package com.example.restexpmongomvn.domain;

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
    private boolean clean = false;
    private boolean oilChanged = false;

    /**
     *
     */
    public Vehicle() {
        // TODO Auto-generated constructor stub
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

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getYear()
     */
    /**
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setYear(int)
     */
    /**
     *
     * @param year
     */
    public void setYear(final int year) {
        this.year = year;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getMake()
     */
    /**
     *
     * @return
     */
    public String getMake() {
        return make;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setMake(java.lang.String)
     */
    /**
     *
     * @param make
     */
    public void setMake(final String make) {
        this.make = make;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getModel()
     */
    /**
     *
     * @return
     */
    public String getModel() {
        return model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setModel(java.lang.String)
     */
    /**
     *
     * @param model
     */
    public void setModel(final String model) {
        this.model = model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getColor()
     */
    /**
     *
     * @return
     */
    public Color getColor() {
        return color;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setColor(dependinject.Color)
     */
    /**
     *
     * @param color
     */
    public void setColor(final Color color) {
        this.color = color;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#getType()
     */
    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setType(java.lang.String)
     */
    /**
     *
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#isClean()
     */
    /**
     *
     * @return
     */
    public boolean isClean() {
        return clean;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setClean(boolean)
     */
    /**
     *
     * @param clean
     */
    public void setClean(final boolean clean) {
        this.clean = clean;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#isOilChanged()
     */
    /**
     *
     * @return
     */
    public boolean isOilChanged() {
        return oilChanged;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dependinject.Vehicle#setOilChanged(boolean)
     */
    /**
     *
     * @param oilChanged
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
