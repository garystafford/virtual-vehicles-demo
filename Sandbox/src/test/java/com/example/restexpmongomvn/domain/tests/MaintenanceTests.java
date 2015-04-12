package com.example.restexpmongomvn.domain.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.restexpmongomvn.domain.Color;
import com.example.restexpmongomvn.domain.Maintenance;
import com.example.restexpmongomvn.domain.Vehicle;

public class MaintenanceTests {

    Vehicle testVehicle;
    Maintenance maintenance;

    @Before
    public void setUp() throws Exception {
        testVehicle = new Vehicle(2015, "Test", "Vehicle", Color.Other,
                "Test Type");
        maintenance = new Maintenance(testVehicle);
    }

    @Test
    public final void testMaintenance() {
        maintenance = new Maintenance();
        assertNotNull(maintenance);
    }

    @Test
    public final void testMaintenanceVehicle() {
        assertNotNull(maintenance);
    }

    @Test
    public final void testWashVehicle() {
        maintenance.washVehicle();
        assertEquals(true, testVehicle.isClean());
    }

    @Test
    public final void testChangeOil() {
        maintenance.changeOil();
        assertEquals(true, testVehicle.isOilChanged());
    }

}
