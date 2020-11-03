package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripTest {

    Trip test_trip;
    //Start Valid Fields
    final String bikes_allowed = "0";
    final String block_id = "64102";
    final String direction_id = "0";
    final String route_id = "64";
    final String service_id = "17-SEP_SUN";
    final String shape_id = "17-SEP_64_0_23";
    final String trip_headsign = "60TH-VLIET";
    final String trip_id = "21736564_2535";
    final String wheelchair_accessible = "0";
    //End Valid Fields


    /**
     * Tests constructor for Trip
     *
     * @author Simon Erickson
     */
    @Test
    void Trip() {
        //throw exception if all fields are empty
        assertThrows(IllegalArgumentException.class, () -> new Trip("", "", "", "", "",
                "", "", "", ""));

        //throw exception if input line does not contain route_id
        assertThrows(IllegalArgumentException.class, () -> new Trip(bikes_allowed, block_id, direction_id, "", service_id,
                shape_id, trip_headsign, trip_id, wheelchair_accessible));

        //throw exception if input line does not contain service_id
        assertThrows(IllegalArgumentException.class, () -> new Trip(bikes_allowed, block_id, direction_id, route_id, "",
                shape_id, trip_headsign, trip_id, wheelchair_accessible));

        //throw exception if input line does not contain trip_id
        assertThrows(IllegalArgumentException.class, () -> new Trip(bikes_allowed, block_id, direction_id, route_id, service_id,
                shape_id, trip_headsign, "", wheelchair_accessible));

        //verify that headers match when properly formatted input line is passed
        try {
            new Trip(bikes_allowed, block_id, direction_id, route_id, service_id,
                    shape_id, trip_headsign, trip_id, wheelchair_accessible);
        } catch (IllegalArgumentException e) {
            fail();
        }

    }

    /**
     * Sets up initial constructor to use in tests
     *
     * @author Simon Erickson
     */
    @BeforeEach
    void setUp() {
        test_trip = new Trip(bikes_allowed, block_id, direction_id, route_id, service_id,
                shape_id, trip_headsign, trip_id, wheelchair_accessible);
    }

    /**
     * Makes sure bikes_allowed is returned correctly
     * Makes sure bikes_allowed can be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getBikesAllowed() {
        //Makes sure bikes_allowed is returned
        assertEquals(0, test_trip.getBikesAllowed().getValue());

        //Makes sure bikes_allowed can be empty
        test_trip =new Trip("", block_id, direction_id, route_id, service_id,
                shape_id, trip_headsign, trip_id, wheelchair_accessible);
        assertEquals(0, test_trip.getBikesAllowed().getValue());
    }

    /**
     * Makes sure block_id is returned correctly
     * Makes sure block_id can be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getBlockID() {
        //Makes sure block_id is returned
        assertEquals("64102", test_trip.getBlockID());

        //Makes sure block_id can be empty
        test_trip =new Trip(bikes_allowed, "", direction_id, route_id, service_id,
                shape_id, trip_headsign, trip_id, wheelchair_accessible);
        assertEquals("", test_trip.getBlockID());
    }

    /**
     * Makes sure direction_id is returned correctly
     * Makes sure direction_id can be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getDirectionID() {
        //Makes sure direction_id is returned
        assertEquals(0, test_trip.getDirectionID().getValue());

        //Makes sure direction_id can be empty
        test_trip =new Trip(bikes_allowed, block_id, "", route_id, service_id,
                shape_id, trip_headsign, trip_id, wheelchair_accessible);
        assertEquals(0, test_trip.getDirectionID().getValue());
    }

    /**
     * Makes sure route_id is returned correctly
     * Makes sure route_id cannot be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getRouteID() {
        //Makes sure route_id is returned
        assertEquals("64", test_trip.getRouteID());

        //Makes sure route_id cannot be empty
        assertThrows(IllegalArgumentException.class, () -> test_trip = new Trip(bikes_allowed, block_id, direction_id, "", service_id,
                shape_id, trip_headsign, trip_id, wheelchair_accessible));

    }

    /**
     * Makes sure service_id is returned correctly
     * Makes sure service_id cannot be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getServiceID() {
        //Makes sure service_id is returned
        assertEquals("17-SEP_SUN", test_trip.getServiceID());

        //Makes sure service_id cannot be empty
        assertThrows(IllegalArgumentException.class, () -> test_trip = new Trip(bikes_allowed, block_id, direction_id, route_id, "",
                shape_id, trip_headsign, trip_id, wheelchair_accessible));

    }

    /**
     * Makes sure shape_id is returned correctly
     * Makes sure shape_id can be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getShapeID() {
        //Makes sure shape_id is returned
        assertEquals("17-SEP_64_0_23", test_trip.getShapeID());

        //Makes sure shape_id can be empty
        test_trip = new Trip(bikes_allowed, block_id, direction_id, route_id, service_id,
                "", trip_headsign, trip_id, wheelchair_accessible);
        assertEquals("", test_trip.getShapeID());
    }

    /**
     * Makes sure trip_headsign is returned correctly
     * Makes sure trip_headsign can be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getTripHeadSign() {
        //Makes sure trip_headsign is returned
        assertEquals("60TH-VLIET", test_trip.getTripHeadsign());

        //Makes sure trip_headsign can be empty
        test_trip =new Trip(bikes_allowed, block_id, direction_id, route_id, service_id,
                shape_id, "", trip_id, wheelchair_accessible);
        assertEquals("", test_trip.getTripHeadsign());
    }

    /**
     * Makes sure trip_id is returned correctly
     * Makes sure trip_id cannot be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getTripID() {
        //Makes sure trip_id is returned
        assertEquals("21736564_2535", test_trip.getTripID());

        //Makes sure trip_id cannot be empty
        assertThrows(IllegalArgumentException.class, () -> test_trip = new Trip(bikes_allowed, block_id, direction_id, route_id, service_id,
                shape_id, trip_headsign, "", wheelchair_accessible));

    }

    /**
     * Makes sure wheelchair_accessible is returned correctly
     * Makes sure wheelchair_accessible can be empty
     *
     * @author Simon Erickson
     */
    @Test
    void getWheelchairAccessible() {
        //Makes sure wheelchair_accessible is returned
        assertEquals(0, test_trip.getWheelchairAccessible().getValue());

        //Makes sure wheelchair_accessible can be empty
        test_trip =new Trip(bikes_allowed, block_id, direction_id, route_id, service_id,
                shape_id, trip_headsign, trip_id, "");
        assertEquals(0, test_trip.getWheelchairAccessible().getValue());
    }
}