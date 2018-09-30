/**
 * Node.java
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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Creates a new node for a graph.
 */
public class Node
{
    /** The node data. */
    public final City city;
    /** The set of adjacent nodes. */
    public final Set<Node> leaves = new HashSet<>();

    /**
     * Creates a new node with the input data.
     *
     * @param city the data
     */
    public Node(City city)
    {
        Objects.requireNonNull(city, "city cannot be null");

        this.city = city;
    }
}
