/**
 * TestMain.java
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

/**
 * Provides a testing entry point to reproduce the example inputs. Directly
 * evaluating the string outputs is problematic because the order of returned
 * cities varies depending on the search implementation. The output could be
 * parsed. but it's not worth the effort.
 */
public class TestMain
{

    /**
     * The entry point to stimulate the application with the example inputs.
     *
     * @param args not used
     */
    public static void main(String[] args)
    {
        String[] commands = new String[]
        {
                "Fortuna - Hemingway",
                "Fortuna - Atlantis",
                "Hemingway - Chesterfield",
                "Chesterfield - Springton",
                "Los Amigos - Paristown",
                "Paristown - Oaktown",
                "Los Amigos - Oaktown",
                "Summerton - Springton",
                "Summerton - Hemingway",
                "cities from Summerton in 1 jumps",
                "cities from Summerton in 2 jumps",
                "can I teleport from Springton to Atlantis",
                "can I teleport from Oaktown to Atlantis",
                "loop possible from Oaktown",
                "loop possible from Fortuna",
        };

        Main main = new Main();

        for (String command : commands)
        {
            String response = main.parse(command);
            if (response != null)
            {
                System.out.println(response);
            }
        }
    }
}
