/**
 * StringParser.java
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import teleporter.data.City;
import teleporter.data.Route;

/**
 * Parses a line to generate a Line.
 */
public class StringParser
{
    private static final Logger LOG = Logger.getLogger(StringParser.class.getName());

    private static final Pattern CITY_SEARCH_PATTERN;
    private static final Pattern LOOP_SEARCH_PATTERN;
    private static final Pattern ROUTE_SEARCH_PATTERN;

    private final Map<String, City> cityMap = new HashMap<>();
    private final Map<Route, Route> routeMap = new HashMap<>();

    static
    {
        CITY_SEARCH_PATTERN = Pattern.compile("cities from ([\\sa-zA-Z]+?) in (\\d+) jumps");
        LOOP_SEARCH_PATTERN = Pattern.compile("loop possible from ([\\sa-zA-Z]+)");
        ROUTE_SEARCH_PATTERN = Pattern.compile("can I teleport from ([\\sa-zA-Z]+?) to ([\\sa-zA-Z]+)");
    }

    /**
     * Parses the input string to a line representation.
     *
     * @param string the input string
     * @return the line representation or null if unable to parse
     */
    public Line parse(String string)
    {
        Line line = null;

        Objects.requireNonNull(string, "string cannot be null");

        String trimmed = string.trim();

        int index = trimmed.indexOf(" ");
        if (index == -1)
        {
            index = trimmed.length();
        }

        String first = trimmed.substring(0, index);

        switch (first)
        {
        case "cities":
            line = this.parseCitySearchLine(trimmed);
            break;

        case "can":
            line = this.parseRouteSearchLine(trimmed);
            break;

        case "loop":
            line = this.parseLoopSearchLine(trimmed);
            break;

        case "show":
            line = this.createShowRouteLine();
            break;

        case "quit":
            line = this.createQuitLine();
            break;

        default:
            line = this.parseNewRouteLine(trimmed);
            break;
        }

        return line;
    }

    private CitySearchLine parseCitySearchLine(String string)
    {
        CitySearchLine line = null;

        Matcher m = CITY_SEARCH_PATTERN.matcher(string);
        if (m.matches())
        {
            String cityName = m.group(1);
            String jumpString = m.group(2);

            Integer jumps = null;
            try
            {
                jumps = Integer.valueOf(jumpString);
            }
            catch (NumberFormatException ex)
            {
                LOG.log(Level.SEVERE, "unable to parse number of jumps: " + jumpString, ex);
            }

            if (jumps != null)
            {
                City city = this.getCity(cityName);
                line = new CitySearchLine(city, jumps);
            }
        }

        return line;
    }

    private LoopSearchLine parseLoopSearchLine(String string)
    {
        LoopSearchLine line = null;

        Matcher m = LOOP_SEARCH_PATTERN.matcher(string);
        if (m.matches())
        {
            String cityName = m.group(1);

            City city = this.getCity(cityName);
            line = new LoopSearchLine(city);
        }

        return line;
    }

    private NewRouteLine parseNewRouteLine(String string)
    {
        NewRouteLine line = null;

        int index = string.indexOf("-");
        if (index > 0)
        {
            String fromCity = string.substring(0, index).trim();
            String toCity = string.substring(index + 1).trim();

            if (!fromCity.equals(toCity))
            {
                Route route = this.getRoute(fromCity, toCity);

                line = new NewRouteLine(route);
            }
        }

        return line;
    }

    private QuitLine createQuitLine()
    {
        return new QuitLine();
    }

    private RouteSearchLine parseRouteSearchLine(String string)
    {
        RouteSearchLine line = null;

        Matcher m = ROUTE_SEARCH_PATTERN.matcher(string);
        if (m.matches())
        {
            String fromCityName = m.group(1);
            String toCityName = m.group(2);

            City fromCity = this.getCity(fromCityName);
            City toCity = this.getCity(toCityName);

            line = new RouteSearchLine(fromCity, toCity);
        }

        return line;
    }

    private ShowRouteLine createShowRouteLine()
    {
        return new ShowRouteLine();
    }

    private City getCity(String cityName)
    {
        return this.cityMap.computeIfAbsent(cityName, (name) -> new City(name));
    }

    private Route getRoute(String from, String to)
    {
        City fromCity = this.getCity(from);
        City toCity = this.getCity(to);

        Route key = new Route(fromCity, toCity);
        Route route = this.routeMap.computeIfAbsent(key, (r) -> r);

        return route;
    }
}
