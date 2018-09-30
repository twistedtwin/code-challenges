/**
 * City.java
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

import java.util.Objects;

/**
 * Provides a data class for a city.
 */
public class City
{
    /** The name of the city. */
    public final String name;

    /**
     * Creates a new city with the input name.
     *
     * @param name the name of the city
     */
    public City(String name)
    {
        Objects.requireNonNull(name, "name cannot be null");

        this.name = name;
    }

    @Override
    public int hashCode()
    {
        int result = 1;

        final int prime = 31;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());

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

        City other = (City) obj;
        if (this.name == null)
        {
            return other.name == null;
        }
        else
        {
            return this.name.equals(other.name);
        }
    }

    @Override
    public String toString()
    {
        return "City [name=" + this.name + "]";
    }
}
