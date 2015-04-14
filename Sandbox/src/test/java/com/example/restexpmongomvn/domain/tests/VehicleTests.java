package com.example.restexpmongomvn.domain.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.restexpmongomvn.domain.Color;
import com.example.restexpmongomvn.domain.Vehicle;

/**
 *
 * @author gstafford
 */
public class VehicleTests {

	Vehicle testVehicleGetTests;
	Vehicle testVehicleSetTests;

    /**
     *
     * @throws Exception
     */
    @Before
	public void setUp() throws Exception {
		testVehicleGetTests = new Vehicle(2015, "Test", "Vehicle",
				Color.Red, "4-Door Sedan");
		testVehicleSetTests = new Vehicle();
	}

    /**
     *
     */
    @Test
	public final void testVehicle() {
		final Vehicle testVehicle = new Vehicle();
		assertNotNull(testVehicle);
	}

    /**
     *
     */
    @Test
	public final void testVehicleWithParams() {
		Vehicle testVehicle;
		testVehicle = new Vehicle(2015, "Test", "Vehicle", Color.Red,
				"Test Car Type");
		assertNotNull(testVehicle);
	}

    /**
     *
     */
    @Test
	public final void testGetYear() {
		assertEquals(2015, testVehicleGetTests.getYear());
	}

    /**
     *
     */
    @Test
	public final void testSetYear() {
		testVehicleSetTests.setYear(2000);
		assertEquals(2000, testVehicleSetTests.getYear());
	}

    /**
     *
     */
    @Test
	public final void testGetMake() {
		assertEquals("Test", testVehicleGetTests.getMake());
	}

    /**
     *
     */
    @Test
	public final void testSetMake() {
		testVehicleSetTests.setMake("Test Make");
		assertEquals("Test Make", testVehicleSetTests.getMake());
	}

    /**
     *
     */
    @Test
	public final void testGetModel() {
		assertEquals("Vehicle", testVehicleGetTests.getModel());
	}

    /**
     *
     */
    @Test
	public final void testSetModel() {
		testVehicleSetTests.setMake("Test Model");
		assertEquals("Test Model", testVehicleSetTests.getMake());
	}

    /**
     *
     */
    @Test
	public final void testGetColor() {
		assertEquals(Color.Red, testVehicleGetTests.getColor());
	}

    /**
     *
     */
    @Test
	public final void testSetColor() {
		testVehicleSetTests.setColor(Color.Black);
		assertEquals(Color.Black, testVehicleSetTests.getColor());
	}

    /**
     *
     */
    @Test
	public final void testGetType() {
		assertEquals("4-Door Sedan", testVehicleGetTests.getType());
	}

    /**
     *
     */
    @Test
	public final void testSetType() {
		testVehicleSetTests.setType("Test Vehicle Type");
		assertEquals("Test Vehicle Type", testVehicleSetTests.getType());
	}

    /**
     *
     */
    @Test
	public final void testIsClean() {
		assertFalse(testVehicleGetTests.isClean());
	}

    /**
     *
     */
    @Test
	public final void testSetClean() {
		testVehicleSetTests.setClean(true);
		assertTrue(testVehicleSetTests.isClean());
	}

    /**
     *
     */
    @Test
	public final void testIsOilChanged() {
		assertFalse(testVehicleGetTests.isOilChanged());
	}

    /**
     *
     */
    @Test
	public final void testSetOilChanged() {
		testVehicleSetTests.setOilChanged(true);
		assertTrue(testVehicleSetTests.isOilChanged());
	}

    /**
     *
     */
    @Test
	public final void testToString() {
		final String vehicleToString = "2015 Red Test Vehicle 4-Door Sedan";
		assertEquals(vehicleToString, testVehicleGetTests.toString());
	}
}
