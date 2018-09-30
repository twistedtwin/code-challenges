/**
 * RouteSearchLine.java
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
 * Represents a line requesting a route search.
 */
public final class RouteSearchLine extends Line
{
    /** The starting city. */
    public final City from;
    /** The ending city. */
    public final City to;

    /**
     * Creates a new line representation.
     *
     * @param from the starting city
     * @param to   the ending city
     */
    public RouteSearchLine(City from, City to)
    {
        super(Command.ROUTE_SEARCH);

        Objects.requireNonNull(from, "from cannot be null");
        Objects.requireNonNull(to, "to cannot be null");

        this.from = from;
        this.to = to;
    }

    @Override
    public String toString()
    {
        return "RouteSearchLine [from=" + this.from + ", to=" + this.to + ", command=" + this.command + "]";
    }
}
