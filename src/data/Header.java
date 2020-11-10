/*
 * Authors: Becker, Ryan; Cross, Joy; Erickson, Simon; Fass, Grant;
 * Class: SE 2030 - 021
 * Team: G
 * Affiliation: Milwaukee School Of Engineering (MSOE)
 * Program Name: General Transit Feed Specification Tool
 * Copyright (C): GNU GPLv3; 9 November 2020
 *
 * This file is a part of the General Transit Feed Specification Tool
 * written by Team G of class SE 2030 - 021 at MSOE.
 *
 * This is a free software: it can be redistributed and/or modified
 * as expressed in the GNU GPLv3 written by the Free Software Foundation.
 *
 * This software is distributed in hopes that it is useful but does
 * not include any warranties, not even implied warranties. There is more
 * information about this in the GNU GPLv3.
 *
 * To view the license go to <gnu.org/licenses/gpl-3.0.en.html>
 */
package data;

import java.util.Objects;

/**
 * Headers Purpose: create headers object used to map the order of file headers
 *
 * @author Grant
 * @version Created on 10/13/2020 at 11:01 AM
 */
public class Header {
    //region parameters
    private final String headerName;
    private final int headerIndex;
    //endregion

    //region constructors
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
    //endregion

    //region getters
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
    //endregion

    //region overrides
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
    //endregion
}
