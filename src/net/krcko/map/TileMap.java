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
package net.krcko.map;

import java.util.ArrayList;
import net.krcko.math.Point2D;

/**
 * A 2-dimensional rectangular-tile based <code>Map</code>.
 * 
 * This class is only the abstract superclass for objects that store 2D map data.
 *
 * @author Aleksandar Ružičić
 */
public abstract class TileMap extends Map<Point2D.Int> {

    /**
     * Returns number of tile columns in the map.
     * @return number of columns
     */
    public abstract int getWidthInTiles();

    /**
     * Returns number of tile rows in the map.
     * @return number of rows
     */
    public abstract int getHeightInTiles();

    /**
     * Returns <tt>true</tt> if specified <code>MovingEntity</code> can be moved
     * diagonally across the map.
     * @param entity    the entity to test for diagonal movement
     * @return  <tt>true</tt> if diagonal movement for specified entity is allowed
     */
    protected boolean canMoveDiagonaly(final MovingEntity entity) {
        return false;
    }

    /**
     * Returns <tt>true</tt> if specified location is within the map bounds.
     * @param location  the location to check for
     * @return  <tt>true</tt> if location is within the map bounds
     */
    public boolean isValidLocation(final Point2D.Int location) {
        return isValidLocation(location.x, location.y);
    }

    /**
     * Returns <tt>true</tt> if specified location is within the map bounds.
     * @param x the X coordinate of the map location
     * @param y the Y coordinate of the map location
     * @return  <tt>true</tt> if location is within the map bounds
     */
    public boolean isValidLocation(final int x, final int y) {
        return x >= 0 && x < getWidthInTiles() && y >= 0 && y < getHeightInTiles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D.Int[] getNeighbors(final Point2D.Int location, final MovingEntity entity) {
        
        final ArrayList<Point2D.Int> neighbors = new ArrayList<>();
        final boolean diagonal = canMoveDiagonaly(entity);

        for (int x = -1; x <= +1; x++) {
            for (int y = -1; y <= +1; y++) {

                if ((x == 0 && y == 0) || (!diagonal && x != 0 && y != 0)) {
                    continue;
                }

                final Point2D.Int neighbor = new Point2D.Int(location.x + x, location.y + y);
                
                if (isValidLocation(neighbor)) {
                    neighbors.add(neighbor);
                }
            }
        }       

        return neighbors.toArray(new Point2D.Int[neighbors.size()]);
    }
}