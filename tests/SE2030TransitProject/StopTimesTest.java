package SE2030TransitProject;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopTimesTest {

    StopTimes stopTimes;
    Headers expectedHeaders;

    @BeforeEach
    void setUp() {
        stopTimes = new StopTimes();
        expectedHeaders = new Headers();
        expectedHeaders.addHeader(new Header("trip_id", 0));
        expectedHeaders.addHeader(new Header("arrival_time", 1));
        expectedHeaders.addHeader(new Header("departure_time", 2));
        expectedHeaders.addHeader(new Header("stop_id", 3));
        expectedHeaders.addHeader(new Header("stop_sequence", 4));
        expectedHeaders.addHeader(new Header("stop_headsign", 5));
        expectedHeaders.addHeader(new Header("pickup_type", 6));
        expectedHeaders.addHeader(new Header("drop_off_type", 7));
    }

    @Disabled
    @Test
    void loadStopTimes() {
        fail();
    }

    /**
     * Verify that the header line exists
     * Verify that the header line contains the required headers for StopTime (stop_id, trip_id, stop_sequence)
     * Verify that the header line does not contain any extra headers
     * Verify that the header line does not contain any blank or empty headers
     * Verify that the header line works if there are spaces between the fields
     * Verify that capitalization of fields does not matter
     * Example header line: trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type
     * @author Grant Fass
     */
    @Test
    void validateHeader() {
        //throw exception if input line is empty
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("");
        });

        //throw exception if input line does not contain trip_id
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
        });

        //throw exception if input line does not contain stop_id
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("trip_id,arrival_time,departure_time,stop_sequence,stop_headsign,pickup_type,drop_off_type");
        });

        //throw exception if input line does not contain stop_sequence
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("trip_id,arrival_time,departure_time,stop_id,stop_headsign,pickup_type,drop_off_type");
        });

        //throw exception if input line contains a blank header at start or middle
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader(",trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type, ");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("trip_id,arrival_time,departure_time,stop_id,,stop_sequence,stop_headsign,pickup_type,drop_off_type");
        });

        //throw exception if input line contains an extra (unexpected) header
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,extra_header");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("extra_header,trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateHeader("trip_id,arrival_time,departure_time,stop_id,stop_sequence,extra_header,stop_headsign,pickup_type,drop_off_type");
        });

        //verify that headers match when properly formatted input line is passed
        assertEquals(expectedHeaders, stopTimes.validateHeader("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type"));

        //verify that headers match when properly formatted input line with capital letters is passed
        assertEquals(expectedHeaders, stopTimes.validateHeader("tRip_id,arRival_timE,depaRture_time,Stop_id,stop_Sequence,stop_headsign,pickup_typE,dRop_off_type"));

        //verify that headers match when properly formatted input line with varying spaces between fields is passed
        assertEquals(expectedHeaders, stopTimes.validateHeader("trip_id, arrival_time,  departure_time,   stop_id,    stop_sequence,     stop_headsign,      pickup_type,       drop_off_type"));

    }

    /**
     * Verify that the data line exists
     * Verify that the data line contains the same number of fields as the header
     * Verify that the data line handles acceptable parameters correctly
     * Example data line: 21736564_2535,08:51:00,08:51:00,9113,1,,0,0
     * Matches Headers  : trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type
     * See the StopTime test in StopTimeTest for more information
     * @author Grant Fass
     */
    @Test
    void validateData() {
        //throw exception if input line is empty
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateData("", new Headers());
        });

        //throw exception if data line field amount does not match header field amount
        assertThrows(IllegalArgumentException.class, () -> {
            stopTimes.validateData("0,0,0", expectedHeaders);
        });

        StopTime actual = stopTimes.validateData("21736564_2535,08:51:00,08:51:00,9113,1,,0,0", expectedHeaders);
        StopTime expected = new StopTime("08:51:00", "", "",
                "08:51:00", "0", "0", "",
                "", "9113", "1", "", "21736564_2535");
        assertEquals(expected, actual);
    }

    @Disabled
    @Test
    void setDefaultDataValue() {
        fail();
    }

    @Disabled
    @Test
    void addStopTime() {
        fail();
    }

    @Disabled
    @Test
    void getStopTime() {
        fail();
    }

    @Disabled
    @Test
    void removeStopTime() {
        fail();
    }

    @Disabled
    @Test
    void clearStopTimes() {
        fail();
    }
}