/**
 * Route.java
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
package teleporter.data;

import java.util.Map;
import java.util.Objects;

/**
 * Provides a data class for storing a route between two cities.
 */
public final class Route
{
    /** The starting city. */
    public final City from;
    /** The ending city. */
    public final City to;

    /**
     * Creates a new route.
     *
     * @param from the starting city
     * @param to   the ending city
     */
    public Route(City from, City to)
    {
        Objects.requireNonNull(from, "from cannot be null");
        Objects.requireNonNull(to, "to cannot be null");

        this.from = from;
        this.to = to;
    }

    public static Route getRoute(Map<Route, Route> map, City from, City to)
    {
        Route key = new Route(from, to);
        Route route = map.get(key);

        if (route == null)
        {
            route = map.get(new Route(to, from));
        }

        if (route == null)
        {
            route = key;

            map.put(key, key);
        }

        return route;
    }

    @Override
    public int hashCode()
    {
        int result = 1;

        final int prime = 31;
        result = prime * result + ((this.from == null) ? 0 : this.from.hashCode());
        result = prime * result + ((this.to == null) ? 0 : this.to.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj == null)
        {
            return false;
        }

        if (this.getClass() != obj.getClass())
        {
            return false;
        }

        Route other = (Route) obj;
        if (this.from == null)
        {
            if (other.from != null)
            {
                return false;
            }
        }
        else if (!this.from.equals(other.from))
        {
            return false;
        }

        if (this.to == null)
        {
            if (other.to != null)
            {
                return false;
            }
        }
        else if (!this.to.equals(other.to))
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "Route [from=" + this.from + ", to=" + this.to + "]";
    }
}
