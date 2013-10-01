/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to <http://unlicense.org/>
 */

package demo;

import net.krcko.map.MovingEntity;
import net.krcko.math.Point2D;

/**
 * Player class, tagged with MovingEntity interface which allows it to be
 * moved around the map (it can be passed to PathFinder)
 *
 * @author Aleksandar Ružičić
 */
public class Player implements MovingEntity {

    /**
     * Location on the map
     */
    private Point2D.Int location;

    /**
     * Constructs new Player object and initializes it to (0,&nbsp;0) location.
     */
    public Player() {
        this(0, 0);
    }

    /**
     * Constructs new Player object and initializes it to specified location.
     * @param x the X coordinate on the map
     * @param y the Y coordinate on the map
     */
    public Player(final int x, final int y) {
        this(new Point2D.Int(x, y));
    }

    /**
     * Constructs new Player object and initializes it to specified location.
     * @param location  the location on the map
     */
    public Player(final Point2D.Int location) {
        setLocation(location);
    }

    /**
     * Returns location of this Player on the map
     * @return  the map location
     */
    public final Point2D.Int getLocation() {
        return location;
    }

    /**
     * Sets location of this Player on the map
     * @param location  the map location
     */
    public final void setLocation(final Point2D.Int location) {
        this.location = location;
    }
}