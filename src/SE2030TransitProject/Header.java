/*
 * GTFS Transit Lab
 * File Header Contains Class Headers
 * Name: Grant
 * Created 10/13/2020
 */
package SE2030TransitProject;

import java.util.Objects;

/**
 * Headers Purpose: create headers object used to map the order of file headers
 *
 * @author Grant
 * @version Created on 10/13/2020 at 11:01 AM
 */
public class Header {
    private String headerName;
    private int headerIndex;

    /**
     * Constructor for the Headers class
     * @param headerName the name of the header to set
     * @param headerIndex the index of the header to set
     * @author Grant Fass
     */
    public Header(String headerName, int headerIndex) {
        this.headerName = headerName;
        this.headerIndex = headerIndex;
    }

    /**
     * @author Grant Fass
     * @return the value of the headerIndex
     */
    public int getHeaderIndex() {
        return headerIndex;
    }

    /**
     * @author Grant Fass
     * @return the value of the headerName
     */
    public String getHeaderName() {
        return headerName;
    }

    /**
     * override equals method
     * @param o object to check against this header
     * @return if objects are equal
     * @author Grant Fass
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Header header = (Header) o;
        return headerIndex == header.headerIndex &&
                header.headerName.equals(headerName);
    }

    /**
     * @author Grant Fass
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(headerName, headerIndex);
    }
}
