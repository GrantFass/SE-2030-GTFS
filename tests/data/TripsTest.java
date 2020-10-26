package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripsTest {

    private Trips trips;
    private Headers expectedHeaders;

    @BeforeEach
    void setUp() {
        trips = new Trips();
        expectedHeaders = new Headers();
        expectedHeaders.addHeader(new Header("route_id", 0));
        expectedHeaders.addHeader(new Header("service_id", 1));
        expectedHeaders.addHeader(new Header("trip_id", 2));
        expectedHeaders.addHeader(new Header("trip_headsign", 3));
        expectedHeaders.addHeader(new Header("direction_id", 4));
        expectedHeaders.addHeader(new Header("block_id", 5));
        expectedHeaders.addHeader(new Header("shape_id", 6));
    }

    @Disabled
    @Test
    void loadTrips() {
        fail();
    }

    /**
     * Example header line: route_id,service_id,trip_id,trip_headsign,
     * direction_id,block_id,shape_id
     *
     * @author Simon Erickson
     */
    @Test
    void validateHeader() {
        //throw exception if input line is empty
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader("");
        });

        //throw exception if input line does not contain route_id
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader("service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
        });

        //throw exception if input line does not contain service_id
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader("route_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
        });

        //throw exception if input line does not contain trip_id
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_headsign,direction_id,block_id,shape_id");
        });

        //throw exception if input line contains a blank header at start or middle
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader(",route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id, ");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_id,,trip_headsign,direction_id,block_id,shape_id");
        });

        //throw exception if input line contains an extra (unexpected) header
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateHeader("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id,extra_header");
        });
        assertThrows(IllegalArgumentException.class, () -> {
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
     *
     * @author Simon Erickson
     */
    @Test
    void validateData() {
        //throw exception if input line is empty
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateData("", new Headers());
        });

        //throw exception if data line field amount does not match header field amount
        assertThrows(IllegalArgumentException.class, () -> {
            trips.validateData("0,0,0", expectedHeaders);
        });
    }

    @Disabled
    @Test
    void addTrip() {
        fail();
    }

    @Disabled
    @Test
    void getTrip() {
        fail();
    }

    @Disabled
    @Test
    void removeTrip() {
        fail();
    }

    @Disabled
    @Test
    void testToString() {
        fail();
    }
}