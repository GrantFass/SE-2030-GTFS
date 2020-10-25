package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoutesTest {

    Routes routes;
    Headers expectedHeaders;

    @BeforeEach
    void setUp() {
        routes = new Routes();
        expectedHeaders = new Headers();
        expectedHeaders.addHeader(new Header("route_id", 0));
        expectedHeaders.addHeader(new Header("agency_id", 1));
        expectedHeaders.addHeader(new Header("route_short_name", 2));
        expectedHeaders.addHeader(new Header("route_long_name", 3));
        expectedHeaders.addHeader(new Header("route_desc", 4));
        expectedHeaders.addHeader(new Header("route_type", 5));
        expectedHeaders.addHeader(new Header("route_url", 6));
        expectedHeaders.addHeader(new Header("route_color", 7));
        expectedHeaders.addHeader(new Header("route_text_color", 8));


    }

    /**
     * Makes sure header is valid
     * @author Ryan Becker
     */
    @Test
    void validateHeader(){
        //Makes sure header cannot be empty
        assertThrows(IllegalArgumentException.class, () -> routes.validateHeader(""));

        //Makes sure header needs route_id
        assertThrows(IllegalArgumentException.class, () -> routes.validateHeader("agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color"));

        //Makes sure header cannot start with an empty field
        assertThrows(IllegalArgumentException.class, () -> routes.validateHeader(",route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color"));

        //Makes sure header cannot contain an empty field within
        assertThrows(IllegalArgumentException.class, () -> routes.validateHeader("agency_id,route_short_name,,route_long_name,route_desc,route_type,route_url,route_color,route_text_color"));

        //Makes sure header cannot contain an empty field at the end
        assertThrows(IllegalArgumentException.class, () -> routes.validateHeader("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color,"));

        //Makes sure header cannot have additional unspecified headers
        assertThrows(IllegalArgumentException.class, () -> routes.validateHeader("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color,additional_header"));

        //Makes sure valid header passes
        assertEquals(expectedHeaders, routes.validateHeader("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color"));



    }

    /**
     * Makes sure data line within file is valid
     * @author Ryan Becker
     */
    @Test
    void validateData(){
        //Makes sure data cannot be empty
        assertThrows(IllegalArgumentException.class, () -> routes.validateData("", new Headers()));

        //Makes sure data cannot contain less data fields than there are header fields
        assertThrows(IllegalArgumentException.class, () -> routes.validateData("0,0,0", expectedHeaders));

        //Makes sure data fields cannot exceed number of header fields
        assertThrows(IllegalArgumentException.class, () -> routes.validateData("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", expectedHeaders));

        //Makes sure route_id value cannot be missing
        assertThrows(IllegalArgumentException.class, () -> routes.validateData(",agency,short,long,desc,3,http://url,ffffff,000000", expectedHeaders));

        //Makes sure route_color cannot be an invalid value
        assertThrows(IllegalArgumentException.class, () -> routes.validateData("id,agency,short,long,desc,3,http://url,INVALID,000000", expectedHeaders));

        //Makes sure route_text_color cannot be an invalid value
        assertThrows(IllegalArgumentException.class, () -> routes.validateData("id,agency,short,long,desc,3,http://url,ffffff,INVALID", expectedHeaders));

        //Makes sure a valid data line doesn't throw an error
        assertDoesNotThrow(() -> {
            routes.validateData("id,agency,short,long,desc,0,http://url,ffffff,000000", expectedHeaders);
        });



    }

    @Disabled
    @Test
    void loadRoutes(){
        fail();
    }

    @Disabled
    @Test
    void addRoute() {
    }
    @Disabled
    @Test
    void getRoute() {
    }
    @Disabled
    @Test
    void removeRoute() {
    }

    @Test
    void testToString() {
    }
}