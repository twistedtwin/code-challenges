/**
 * Graph.java
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
package teleporter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import teleporter.data.City;
import teleporter.data.Node;
import teleporter.data.Route;

/**
 *
 */
public class Graph
{
    private final Map<City, Node> vertexMap = new HashMap<>();
    private final Function<City, Node> cityToNode = (city) -> new Node(city);

    public void addRoute(Route route)
    {
        Node fromNode = this.vertexMap.computeIfAbsent(route.from, this.cityToNode);
        Node toNode = this.vertexMap.computeIfAbsent(route.to, this.cityToNode);

        fromNode.neighbors.add(toNode);
        toNode.neighbors.add(fromNode);
    }

    public Set<Route> getRoutes()
    {
        Set<Route> routes = new HashSet<>();

        for (Node node : this.vertexMap.values())
        {
            for (Node neighbor : node.neighbors)
            {
                routes.add(new Route(node.city, neighbor.city));
            }
        }

        return routes;
    }

    public Set<City> getNeighborhood(City originCity, int maxJumps)
    {
        Set<City> neighborhood = this.getNeighborhood(
                new HashSet<>(),
                this.vertexMap.computeIfAbsent(originCity, this.cityToNode),
                0,
                maxJumps);

        neighborhood.remove(originCity);

        return neighborhood;
    }

    private Set<City> getNeighborhood(Set<City> visited, Node node, int jumps, int maxJumps)
    {
        visited.add(node.city);

        for (Node neighbor : node.neighbors)
        {
            City city = neighbor.city;
            if (!visited.contains(city)
                    && jumps < maxJumps)
            {
                this.getNeighborhood(visited, neighbor, jumps + 1, maxJumps);
            }
        }

        return visited;
    }

    public boolean canTeleport(City fromCity, City toCity)
    {
        Node fromNode = this.vertexMap.computeIfAbsent(fromCity, this.cityToNode);

        return this.canTeleport(new HashSet<>(), fromNode, toCity);
    }

    private boolean canTeleport(Set<City> visited, Node fromNode, City toCity)
    {
        boolean canTeleport = false;

        visited.add(fromNode.city);

        for (Node neighbor : fromNode.neighbors)
        {
            City city = neighbor.city;

            if (!visited.contains(city))
            {
                if (city.equals(toCity)
                        || this.canTeleport(visited, neighbor, toCity))
                {
                    canTeleport = true;
                    break;
                }
            }
        }

        return canTeleport;
    }

    public boolean isCityOnLoop(City city)
    {
        Node fromNode = this.vertexMap.computeIfAbsent(city, this.cityToNode);

        return this.isCityOnLoop(new HashSet<>(), fromNode, city);
    }

    private boolean isCityOnLoop(Set<Route> visited, Node fromNode, City toCity)
    {
        boolean cityOnLoop = false;

        City fromCity = fromNode.city;

        for (Node neighbor : fromNode.neighbors)
        {
            City city = neighbor.city;

            Route route = new Route(fromCity, city);

            if (!visited.contains(route))
            {
                visited.add(route);

                if (city.equals(toCity)
                        || this.isCityOnLoop(visited, neighbor, toCity))
                {
                    cityOnLoop = true;
                    break;
                }
            }
        }

        return cityOnLoop;
    }
}
