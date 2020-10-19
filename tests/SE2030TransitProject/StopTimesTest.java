package SE2030TransitProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopTimesTest {

    StopTimes stopTimes;
    @BeforeEach
    void setUp() {
        stopTimes = new StopTimes();
    }

    @Test
    void loadStopTimes() {

    }

    /**
     * Verify that the header line exists
     * Verify that the header line contains the required headers for StopTime
     * Verify that the header line does not contain any extra headers
     * Verify that the header line does not contain any blank or empty headers
     * Verify that the header line works if there are spaces between the fields
     * Verify that capitalization of fields does not matter
     * Example header line: trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type
     */
    @Test
    void validateHeader() {
        String inputHeadersLine = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type";
        Headers expectedHeaders = new Headers();
        expectedHeaders.addHeader(new Header("trip_id", 0));
        expectedHeaders.addHeader(new Header("arrival_time", 1));
        expectedHeaders.addHeader(new Header("departure_time", 2));
        expectedHeaders.addHeader(new Header("stop_id", 3));
        expectedHeaders.addHeader(new Header("stop_sequence", 4));
        expectedHeaders.addHeader(new Header("stop_headsign", 5));
        expectedHeaders.addHeader(new Header("pickup_type", 6));
        expectedHeaders.addHeader(new Header("drop_off_type", 7));
        assertEquals(expectedHeaders, stopTimes.validateHeader(inputHeadersLine));







    }

    @Test
    void validateData() {

    }

    @Test
    void setDefaultDataValue() {

    }


    @Test
    void addStopTime() {

    }

    @Test
    void getStopTime() {

    }

    @Test
    void removeStopTime() {

    }

    @Test
    void clearStopTimes() {

    }
}