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

    private final Map<String, City> cityMap = new HashMap<>();
    private final Map<Route, Route> routeMap = new HashMap<>();

    public Line parse(String string)
    {
        Line line = null;

        Objects.requireNonNull(string, "string cannot be null");

        String lower = string.trim().toLowerCase();

        int index = lower.indexOf(" ");

        if (index != -1)
        {
            String first = lower.substring(0, index);

            switch (first)
            {
            case "cities":
                line = parseCitySearchLine(lower);
                break;

            case "can":
                line = parseRouteSearchLine(lower);
                break;

            case "loop":
                line = parseLoopSearchLine(lower);
                break;

            case "show":
                line = createShowRouteLine();
                break;

            case "quit":
                line = createQuitLine();
                break;

            default:
                line = parseNewRouteLine(lower);
                break;
            }
        }

        return line;
    }

    private CitySearchLine parseCitySearchLine(String string)
    {
        CitySearchLine line = null;

        Pattern p = Pattern.compile("cities from ([\\sa-zA-Z]+?) in (\\d+) jumps");
        Matcher m = p.matcher(string);

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

        Pattern p = Pattern.compile("loop possible from ([\\sa-zA-Z]+)");
        Matcher m = p.matcher(string);

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

            Route route = this.getRoute(fromCity, toCity);

            line = new NewRouteLine(route);
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

        Pattern p = Pattern.compile("can I teleport from ([\\\\sa-zA-Z]+?) to ([\\\\sa-zA-Z]+)");
        Matcher m = p.matcher(string);

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
        return this.cityMap.getOrDefault(cityName, new City(cityName));
    }

    private Route getRoute(String from, String to)
    {
        Route route = null;

        City fromCity = this.getCity(from);
        City toCity = this.getCity(to);

        Route key = new Route(fromCity, toCity);
        route = this.routeMap.get(key);

        if (route == null)
        {
            key = new Route(toCity, fromCity);

            route = this.routeMap.get(key);
        }

        if (route == null)
        {
            route = key;

            this.routeMap.put(route, route);
        }

        return route;
    }
}
