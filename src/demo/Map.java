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


import net.krcko.map.heuristic.Manhattan2D;
import net.krcko.map.heuristic.Diagonal2D;
import net.krcko.map.heuristic.Euclidean2D;
import java.util.ArrayList;
import net.krcko.map.MapNode;
import net.krcko.map.MovingEntity;
import net.krcko.map.Path;
import net.krcko.map.TileMap;
import net.krcko.map.AStarPathFinder;
import net.krcko.math.Point2D;

/**
 * A TileMap implementation class.
 *
 * @author Aleksandar Ružičić
 */
public class Map extends TileMap {

    /**
     * A* path finding algorithm using Manhattan Distance heuristic constant
     */
    public static final int A_STAR_MANHATTAN = 0;

    /**
     * A* path finding algorithm using Diagonal Distance heuristic constant
     */
    public static final int A_STAR_DIAGONAL = 1;

    /**
     * A* path finding algorithm using Euclidean Distance heuristic constant
     */
    public static final int A_STAR_EUCLIDEAN = 2;

    /**
     * Grass tile, easiest to move by.
     */
    public static final int GRASS = 0;

    /**
     * Sand tile, hard to move by.
     */
    public static final int SAND = 1;

    /**
     * Mud tile, hardest to move by.
     */
    public static final int MUD = 2;

    /**
     * Wall tile, impossible to move by.
     */
    public static final int WALL = 3;

    /**
     * Map data.
     */
    private int[][] map = new int[0][0];

    /**
     * Controls if diagonal movement is allowed
     */
    private boolean canMoveDiagonaly = true;

    private ArrayList<MapListener> listeners = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean canMoveDiagonaly(MovingEntity entity) {
        return canMoveDiagonaly;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidthInTiles() {
        return map.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeightInTiles() {
        return map[0].length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMoveTo(MovingEntity entity, Point2D.Int target) {
        
        if (!isValidLocation(target)) {
            throw new IndexOutOfBoundsException("Tile with coordinates (" + target.x + ", " + target.y + ") is out of the map bounds.");
        }

        return map[target.x][target.y] != WALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getCost(MovingEntity entity, Point2D.Int source, Point2D.Int target) {

        if (!isValidLocation(source)) {
            throw new IndexOutOfBoundsException("Tile with coordinates (" + source.x + ", " + source.y + ") is out of the map bounds.");
        }

        if (!isValidLocation(target)) {
            throw new IndexOutOfBoundsException("Tile with coordinates (" + target.x + ", " + target.y + ") is out of the map bounds.");
        }
        
        return map[target.x][target.y] + 0.5f;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pathFinderTraversed(MapNode<Point2D.Int> node) {
        for (MapListener listener: listeners) {
            listener.onNodeTraversed(node);
        }
    }

    public void addMapListener(MapListener listener) {
        listeners.add(listener);
    }

    /**
     * Resizes map and resets all tiles to GRASS type.
     * @param width     the new width of the map
     * @param height    the new height of the map
     */
    public void resize(int width, int height) {
        map = new int[width][height];
    }

    /**
     * Sets value of the map tile
     * @param x     the X coordinate of the tile
     * @param y     the Y coordinate of the tile
     * @param value the tile value
     */
    public void setTile(int x, int y, int value) {

        if (value != GRASS && value != SAND && value != MUD && value != WALL) {
            throw new IllegalArgumentException("Unknown tile value specified. Expected GRASS, SAND, MUD or WALL.");
        }

        if (!isValidLocation(x, y)) {
            throw new IndexOutOfBoundsException("Tile with coordinates (" + x + ", " + y + ") is out of the map bounds.");
        }

        map[x][y] = value;
    }

    /**
     * Returns value of the map tile
     * @param x the X coordinate of the tile
     * @param y the Y coordinate of the tile
     * @return  the tile value
     */
    public int getTile(int x, int y) {

        if (!isValidLocation(x, y)) {
            throw new IndexOutOfBoundsException("Tile with coordinates (" + x + ", " + y + ") is out of the map bounds.");
        }

        return map[x][y];
    }

    /**
     * Returns <tt>true</tt> if diagonal movement is allowed.
     * @return <tt>true</tt> if diagonal movement is allowed.
     */
    public boolean getCanMoveDiagonaly() {
        return canMoveDiagonaly;
    }

    /**
     * Sets diagonal movement.
     * @param canMoveDiagonaly  if <tt>true</tt> diagonal movement is allowed
     */
    public void setCanMoveDiagonaly(boolean canMoveDiagonaly) {
        this.canMoveDiagonaly = canMoveDiagonaly;
    }

    /**
     * Finds a path from source to target
     * @param entity    the moving entity
     * @param source    the source tile
     * @param target    the target tile
     * @return          a path from source to target or <tt>null</tt>
     */
    public Path<Point2D.Int> findPath(MovingEntity entity, Point2D.Int source, Point2D.Int target) {
        return findPath(A_STAR_MANHATTAN, entity, source, target);
    }

    /**
     * Finds a path from source to target
     * @param pathFinderAlgorithm the path finding algorithm to use
     * @param entity    the moving entity
     * @param source    the source tile
     * @param target    the target tile
     * @return          a path from source to target or <tt>null</tt>
     */
    public Path<Point2D.Int> findPath(int pathFinderAlgorithm, MovingEntity entity, Point2D.Int source, Point2D.Int target) {

        switch (pathFinderAlgorithm) {

            case A_STAR_MANHATTAN:
                return new AStarPathFinder<>(this, new Manhattan2D.Int()).findPath(entity, source, target);

            case A_STAR_DIAGONAL:
                return new AStarPathFinder<>(this, new Diagonal2D.Int()).findPath(entity, source, target);

                case A_STAR_EUCLIDEAN:
                return new AStarPathFinder<>(this, new Euclidean2D.Int()).findPath(entity, source, target);
            
            default:
                throw new IllegalArgumentException("Unknown algorithm specified. Expected A_STAR_MANHATTAN, A_STAR_DIAGONAL or A_STAR_EUCLIDEAN.");
        }
    }
}