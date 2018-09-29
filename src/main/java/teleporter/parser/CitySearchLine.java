/**
 * CitySearchLine.java
 *
 * Copyright 2018 Michael G. Leatherman <michael.g.leatherman@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package teleporter.parser;

import java.util.Objects;

import teleporter.data.City;

/**
 * Represents a line requesting a city search.
 */
public final class CitySearchLine extends Line
{
    /** The origin city for the search. */
    public final City originCity;
    /** The maximum number of jumps to teleport in the search. */
    public final int maxJumps;

    /**
     * Creates a new line representation.
     * 
     * @param originCity the origin city for the search
     * @param maxJumps   the number of jumps for the search
     */
    public CitySearchLine(City originCity, int maxJumps)
    {
        super(Command.CITY_SEARCH);

        Objects.requireNonNull(originCity, "originCity cannot be null");

        if (maxJumps < 1)
        {
            throw new IllegalArgumentException("maxJumps cannot be less than one");
        }

        this.originCity = originCity;
        this.maxJumps = maxJumps;
    }

    @Override
    public String toString()
    {
        return "CitySearchLine [originCity=" + originCity + ", maxJumps=" + maxJumps + ", command=" + command + "]";
    }
}
