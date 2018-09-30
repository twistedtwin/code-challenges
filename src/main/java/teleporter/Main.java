/**
 * Main.java
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;

import teleporter.data.City;
import teleporter.data.Route;
import teleporter.parser.CitySearchLine;
import teleporter.parser.Line;
import teleporter.parser.LoopSearchLine;
import teleporter.parser.NewRouteLine;
import teleporter.parser.RouteSearchLine;
import teleporter.parser.StringParser;

/**
 * Provides the primary interface for the application.
 */
public class Main
{

    private static final String COLON = ": ";
    private static final String YES = "yes";
    private static final String NO = "no";
    private static final String EMPTY = "";
    private static final String QUIT = "QUIT";

    private final StringParser parser = new StringParser();
    private final Graph graph = new Graph();

    /**
     * Primary entry point for interactive use.
     *
     * @param args not used
     * @throws IOException if unable to process input
     */
    public static void main(String[] args) throws IOException
    {
        Main main = new Main();

        try (InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(isr))
        {
            String string = reader.readLine();

            while (string != null)
            {
                String response = main.parse(string);

                if (response != null)
                {
                    if (QUIT.equals(response))
                    {
                        break;
                    }

                    System.out.println(response);
                }

                string = reader.readLine();
            }
        }
    }

    /**
     * Parses a string and updates the graph accordingly.
     *
     * @param string the string to parse
     * @return the response from the graph (may be null) *
     */
    public String parse(String string)
    {
        Objects.requireNonNull(string, "string cannot be null");

        Line line = this.parser.parse(string);

        String response = null;

        if (line != null)
        {
            switch (line.command)
            {
            case SHOW_ROUTES:
                this.handleShowRoute();
                break;

            case NEW_ROUTE:
                this.handleAddRoute(line);
                break;

            case CITY_SEARCH:
                response = string + COLON + this.handleCitySearchRequest(line);
                break;

            case ROUTE_SEARCH:
                response = string + COLON + this.handleRouteSearchRequest(line);
                break;

            case LOOP_SEARCH:
                response = string + COLON + this.handleLoopSearchRequest(line);
                break;

            case QUIT:
                response = QUIT;
                break;

            default:
                response = "unhandled line: " + line;
                break;
            }
        }
        else
        {
            response = "unable to parse line: " + string;
        }

        return response;
    }

    private void handleShowRoute()
    {
        Set<Route> routes = this.graph.getRoutes();

        for (Route route : routes)
        {
            System.out.println(route.from.name + " - " + route.to.name);
        }
    }

    private void handleAddRoute(Line line)
    {
        if (line instanceof NewRouteLine)
        {
            NewRouteLine newRouteLine = (NewRouteLine) line;

            this.graph.addRoute(newRouteLine.route);
        }
    }

    private String handleCitySearchRequest(Line line)
    {
        String citiesString = EMPTY;

        if (line instanceof CitySearchLine)
        {
            CitySearchLine citySearchLine = (CitySearchLine) line;

            Set<City> cities = this.graph.getNeighborhood(citySearchLine.originCity, citySearchLine.maxJumps);

            StringBuilder b = new StringBuilder();
            for (City city : cities)
            {
                if (b.length() > 0)
                {
                    b.append(", ");
                }

                b.append(city.name);
            }

            citiesString = b.toString();
        }

        return citiesString;
    }

    private String handleRouteSearchRequest(Line line)
    {
        String result = EMPTY;

        if (line instanceof RouteSearchLine)
        {
            RouteSearchLine routeSearchLine = (RouteSearchLine) line;

            if (this.graph.canTeleport(routeSearchLine.from, routeSearchLine.to))
            {
                result = YES;
            }
            else
            {
                result = NO;
            }
        }

        return result;
    }

    private String handleLoopSearchRequest(Line line)
    {
        String result = EMPTY;

        if (line instanceof LoopSearchLine)
        {
            LoopSearchLine loopSearchLine = (LoopSearchLine) line;

            if (this.graph.isCityOnLoop(loopSearchLine.city))
            {
                result = YES;
            }
            else
            {
                result = NO;
            }
        }

        return result;
    }
}
