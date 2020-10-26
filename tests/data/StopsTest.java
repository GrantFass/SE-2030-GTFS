package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopsTest {

    Stops stops;
    Headers headers = new Headers();

    @BeforeEach
    void setUp() {
        stops = new Stops();
        //stop_id,stop_name,stop_desc,stop_lat,stop_lon
        headers.addHeader(new Header("stop_id", 6));
        headers.addHeader(new Header("stop_name", 9));
        headers.addHeader(new Header("stop_description", 5));
        headers.addHeader(new Header("stop_latitude", 7));
        headers.addHeader(new Header("stop_longitude", 8));
        headers.addHeader(new Header("wheelchair_boarding", 12));
        headers.addHeader(new Header("stop_url", 11));
        headers.addHeader(new Header("stop_timezone", 10));
        headers.addHeader(new Header("stop_code", 4));
        headers.addHeader(new Header("platform_code", 3));
        headers.addHeader(new Header("level_id", 0));
        headers.addHeader(new Header("parent_station", 2));
        headers.addHeader(new Header("location_type", 1));
    }

    /**
     * Test to verify the following:
     * Verify that an empty header throws exception -
     * Verify that the header throws exception if stop_id, stop_lat, stop_lat is missing -
     * Verify that the header does not contain any extra headers that shouldn't be there -
     * Verify capitalization of headers don't matter -
     * Verify that a header line works with spaces in-between fields -
     * Verify spelling of stop_lat and stop_lon and stop_desc can be either long or short form -
     * @author Joy Cross
     */
    @Test
    void validateHeader() {
        // throw exception if empty header inputted
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateHeader("");
        });

        // throw exception if stop_id missing
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateHeader("stop_name,stop_desc,stop_lat,stop_lon");
        });

        // throw exception if stop_lat missing
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateHeader("stop_id,stop_name,stop_desc,stop_lon");
        });

        // throw exception if stop_lon missing
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateHeader("stop_id,stop_name,stop_desc,stop_lat");
        });

        // throw exception if extra header that isn't valid
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateHeader("stop_id,stop_name,stop_desc,stop_lat,stop_lon,test");
        });

        // verify capitalization in headers don't matter
        assertDoesNotThrow(() -> {
            stops.validateHeader("STOP_ID,stop_name,STOP_DESC,stop_lat,stop_lon");
        });

        // verify spaces in header line don't matter
        assertDoesNotThrow(() -> {
            stops.validateHeader("stop_id   ,stop_name  ,    stop_desc,stop_lat,stop_lon");
        });

        // verify stop_desc works with long version name as well
        assertDoesNotThrow(() -> {
            stops.validateHeader("stop_id,stop_name,stop_description,stop_lat,stop_lon");
        });

        // verify stop_lat works with long version name as well
        assertDoesNotThrow(() -> {
            stops.validateHeader("stop_id,stop_name,stop_desc,stop_latitude,stop_lon");
        });

        // verify stop_lon works with long version name as well
        assertDoesNotThrow(() -> {
            stops.validateHeader("stop_id,stop_name,stop_desc,stop_lat,stop_longitude");
        });
    }

    /**
     * Test to verify the following:
     * Verify exception is thrown with empty data -
     * Verify that having more data fields then headers throws exception -
     * Verify that invalid data throws exception -
     * @author Joy Cross
     */
    @Test
    void validateData() {
        //throw exception if empty data
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("", new Headers());
        });

        //throw exception if data fields have more then headers
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", headers);
        });

        //throw exception if data fields have less then headers
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("0,0,0", headers);
        });
        // check that no exception is thrown with all valid data
        assertDoesNotThrow(() -> {
            stops.validateData("test,0,test,test,test,test,6712,43.0444475,-87.9779369,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,0", headers);
        });
        // check if exception is thrown with stop_id missing
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,test,test,,43.0444475,-87.9779369,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,0", headers);
        });
        // check if exception is thrown with stop_lat missing
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,"+
                    "test,,6712,,-87.9779369,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,"+
                    "0", headers);
        });
        // check if exception is thrown with stop_lon missing
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,"+
                    "test,test,6712,43.0444475,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,0", headers);
        });
        // check if exception is thrown with putting string for stop_lat
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,"+
                    "test,test,6712,test,-87.9779369,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,"+
                    "0", headers);
        });
        // check if exception is thrown with putting string for stop_lon
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,"+
                    "test,test,6712,43.0444475,test,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,"+
                    "0", headers);
        });
        // check if exception is thrown with putting string for wheelchair_boarding enum
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,"+
                    "test,test,6712,43.0444475,-87.9779369,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,"+
                    "test", headers);
        });
        // check if exception is thrown with putting string for location_type enum
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test, test,test,test,"+
                    "test,test,6712,43.0444475,-87.9779369,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,"+
                    "0", headers);
        });
        // check if exception is thrown with putting wrong format for timezone
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,"+
                    "test,,6712,43.0444475,-87.9779369,"+
                    "STATE & 5101 #6712,test,https://www.google.com/,"+
                    "0", headers);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,"+
                    "test,Description,671,43.0444475,-87.9779369,"+
                    "STATE & 5101 #671,00:00:01 CST,https://www.google.com/,"+
                    "0", headers);
        });

        // check if exception is thrown with putting wrong format for URL
        assertThrows(IllegalArgumentException.class, () -> {
            stops.validateData("test,0,test,test,"+
                    "test,Description,6712,43.0444475,-87.9779369,"+
                    "STATE & 5101 #6712,CST,.com/,"+
                    "0", headers);
        });

        // check to make sure doesn't throw exception if description field is empty
        assertDoesNotThrow(() -> {
            stops.validateData("test,0,test,test,"+
                    "test,,6712,43.0444475,-87.9779369,"+
                    "STATE & 5101 #6712,CST,https://www.google.com/,"+
                    "0", headers);
        });
    }

    @Disabled
    @Test
    void addStop() {
    }

    @Disabled
    @Test
    void getStop() {
    }

    @Disabled
    @Test
    void removeStop() {
    }

    @Disabled
    @Test
    void removeStops() {
    }

    @Disabled
    @Test
    void loadStops() {
    }

    @Disabled
    @Test
    void testToString() {
    }
}