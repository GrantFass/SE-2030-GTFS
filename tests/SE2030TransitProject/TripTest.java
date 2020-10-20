package SE2030TransitProject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class TripTest {
    @Disabled
    @BeforeEach
    void setUp() {
        fail();
    }
//    /**
//     * Tests the constructor for Trip
//     *
//     * @author Simon Erickson
//     */
//    @Test
//    void StopTime() {
//        /*
//        Example valid fields
//        trip_id,       arrival_time, departure_time, stop_id, stop_sequence, stop_headsign, pickup_type, drop_off_type
//        21736564_2535, 08:51:00,     08:51:00,       9113,    1,              ,             0,           0
//         */
//        //make sure exception thrown when route_id missing
//        assertThrows(IllegalArgumentException.class, () -> {
//            Trip trip = new Trip("0", "0", "0",
//                    "", "0", "0", "0", "0",
//                    "0");
//        });
//        //make sure exception thrown when trip_id missing
//        assertThrows(IllegalArgumentException.class, () -> {
//            Trip trip = new Trip("0", "0", "0",
//                    "0", "0", "0", "0", "",
//                    "0");
//        });
//        //make sure exception thrown when service_id missing
//        assertThrows(IllegalArgumentException.class, () -> {
//            Trip trip = new Trip("0", "0", "0",
//                    "0", "", "0", "0", "0",
//                    "0");
//        });
//        //make sure trip_headsign, block_id, shape_id, service_id, bikes_allowed, direction_id,
//        // and wheelchair_accessible can be empty and will not throw exception
//        try {
//            Trip trip = new Trip("", "", "",
//                    "0", "0", "", "", "0",
//                    "");
//        } catch (IllegalArgumentException e) {
//            fail();
//        }
//    }

    @Disabled
    @AfterEach
    void tearDown() {
        fail();
    }

    @Disabled
    @Test
    void testFinalize() {
        fail();
    }

    @Disabled
    @Test
    void getBikesAllowed() {
        fail();
    }

    @Disabled
    @Test
    void getBlockID() {
        fail();
    }

    @Disabled
    @Test
    void getDirectionID() {
        fail();
    }

    @Disabled
    @Test
    void getRouteID() {
        fail();
    }

    @Disabled
    @Test
    void getServiceID() {
        fail();
    }

    @Disabled
    @Test
    void getShapeID() {
        fail();
    }

    @Disabled
    @Test
    void getTripHeadsign() {
        fail();
    }

    @Disabled
    @Test
    void getTripID() {
        fail();
    }

    @Disabled
    @Test
    void getTripShortName() {
        fail();
    }

    @Disabled
    @Test
    void getWheelchairAccessible() {
        fail();
    }

    @Disabled
    @Test
    void testToString() {
        fail();
    }
}