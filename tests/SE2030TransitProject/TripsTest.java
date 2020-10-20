package SE2030TransitProject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripsTest {
    private Trips trips;
    Headers expectedHeaders;

    /**
     * Example header line: route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id
     * @author Simon Erickson
     */
    @Test
    void validateHeader() {
        //throw exception if input line is empty
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader("");
        });

        //throw exception if input line does not contain route_id
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader("service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
        });

        //throw exception if input line does not contain service_id
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader("route_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
        });

        //throw exception if input line does not contain trip_id
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_headsign,direction_id,block_id,shape_id");
        });

        //throw exception if input line contains a blank header at start or middle
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader(",route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
        });
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id, ");
        });
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_id,,trip_headsign,direction_id,block_id,shape_id");
        });

        //throw exception if input line contains an extra (unexpected) header
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id,extra_header");
        });
        assertThrows(NullPointerException.class, () -> {
            trips.validateHeader("extra_header,route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_id, extra_header,trip_headsign,direction_id,block_id,shape_id");
        });

        //verify that headers match when properly formatted input line is passed
        assertEquals(expectedHeaders, trips.validateHeader("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id"));

    }

    /**
     * Verify that the data line exists
     * Verify that the data line contains the same number of fields as the header
     * @author Simon Erickson
     */
    @Test
    void validateData() {
        //throw exception if input line is empty
        assertThrows(NullPointerException.class, () -> {
            trips.validateData("", new Headers());
        });

        //throw exception if data line field amount does not match header field amount
        assertThrows(NullPointerException.class, () -> {
            trips.validateData("0,0,0", expectedHeaders);
        });
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFinalize() {
    }

    @Test
    void addTrip() {
    }

    @Test
    void getTrip() {
    }

    @Test
    void removeTrip() {
    }

    @Test
    void loadTrips() {
    }

    @Test
    void testToString() {
    }
}