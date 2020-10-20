package SE2030TransitProject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

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

    @Test
    void validateHeader(){
        assertThrows(IllegalArgumentException.class, () -> {
            routes.validateHeader("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            routes.validateHeader("agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            routes.validateHeader(",route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            routes.validateHeader("agency_id,route_short_name,,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            routes.validateHeader("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color,");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            routes.validateHeader("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color,additional_header");
        });

        assertEquals(expectedHeaders, routes.validateHeader("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color"));



    }

    @Test
    void validateData(){
        assertThrows(IllegalArgumentException.class, () -> {
            routes.validateData("", new Headers());
        });

        assertThrows(IllegalArgumentException.class, () -> {
            routes.validateData("0, 0, 0", expectedHeaders);
        });

        Route actual = routes.validateData("223,MCTS,223,Park Place - Bradley Woods Shuttle,,3,,008345,", expectedHeaders);
        Route expected = new Route("223", "MCTS", "223", "Park Place - Bradley Woods Shuttle", "", "3", "", "008345", "", "", "", "");
        //This assertion is not working correctly, but the console shows no differences
        assertEquals(expected, actual);
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