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

/**
 * MapNode - represents a node on the map. Used by path finder when searching
 * for path.
 * 
 * @author Aleksandar Ružičić
 */
public class MapNode<T> implements Comparable {

    /**
     * The parent of this node, trough which this node is reached in the search.
     */
    private MapNode<T> parent;

    /**
     * The coordinates of this node.
     */
    private T coordinate;

    /**
     * The path cost for this node.
     */
    private float cost;

    /**
     * The heuristic cost for this node.
     */
    private float heuristic;

    /**
     * The search depth of this node.
     */
    private int depth;


    /**
     * Constructs map node with specified coordinates.
     * @param coordinate    the map coordinates of this node
     */
    public MapNode(final T coordinate) {
        this(coordinate, 0, 0);
    }

    /**
     * Constructs map node with specified coordinates.
     * @param coordinate    the map coordinates of this node
     * @param cost          the path cost of this node
     * @param heuristic     the heuristic code of this node
     */
    public MapNode(final T coordinate, final float cost, final float heuristic) {
        this(coordinate, null, cost, heuristic);
    }

    /**
     * Constructs map node with specified coordinates.
     * @param coordinate    the map coordinates of this node
     * @param parent        the parent node which lead to this node while searching
     * @param cost          the path cost of this node
     * @param heuristic     the heuristic code of this node
     */
    public MapNode(final T coordinate, final MapNode<T> parent, final float cost, final float heuristic) {
        setCoordinate(coordinate);
        setCost(cost);
        setHeuristic(heuristic);
        setParent(parent);
    }

    /**
     * Returns this node's coordinates.
     * @return  coordinates of this node in map-space
     */
    public T getCoordinate() {
        return coordinate;
    }

    /**
     * Sets this node's coordinates.
     * @param coordinate    coordinates of this node in map-space
     */
    public final void setCoordinate(final T coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Returns path cost of this node.
     * @return  path cost of this node
     */
    public float getCost() {
        return cost;
    }

    /**
     * Sets path cost of this node.
     * @param cost  path cost of this node
     */
    public final void setCost(final float cost) {
        this.cost = cost;
    }

    /**
     * Returns search depth of this node.
     * @return  search depth of this node
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Sets search depth of this node
     * @param depth search depth of this node
     */
    public void setDepth(final int depth) {
        this.depth = depth;
    }

    /**
     * Returns heuristic cost of this node.
     * @return  heuristic cost of this node
     */
    public float getHeuristic() {
        return heuristic;
    }

    /**
     * Sets heuristic cost of this node.
     * @param heuristic heuristic cost of this node
     */
    public final void setHeuristic(final float heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Returns parent of this node.
     * @return  the parent node which lead to this node while searching
     */
    public MapNode<T> getParent() {
        return parent;
    }

    /**
     * Sets parent of this node.
     * @param parent the parent node which lead to this node while searching
     * @return the search depth of this node
     */
    public final int setParent(final MapNode<T> parent) {
        
        this.parent = parent;

        if (parent != null) {
            depth = parent.depth + 1;
        } else {
            depth = 0;
        }

        return depth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return coordinate.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {

        if (other == null || !getClass().isInstance(other)) {
            return false;
        }

        final MapNode<T> node = (MapNode<T>) other;

        if (coordinate == null || !coordinate.equals(node.coordinate)) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Object other) throws ClassCastException {

        if (other == null) {
            return +1;
        }

        if (!getClass().isInstance(other)) {
            throw new ClassCastException("Can't compare " + getClass().getName() + " to " + other.getClass().getName());
        }

        final MapNode<T> node = (MapNode<T>) other;

        int result = Float.compare(heuristic + cost, node.heuristic + node.cost);
        
        if (result == 0 && cost == node.cost && heuristic != node.heuristic) {
            result = Float.compare(heuristic, node.heuristic);
        }

        return result;
    }
}