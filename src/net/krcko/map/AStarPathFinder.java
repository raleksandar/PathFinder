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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Implements PathFinder using A* searching algorithm.
 *
 * @author Aleksandar
 */
public class AStarPathFinder<T> extends PathFinder<T> {

    /**
     * A heuristic cost provider object.
     */
    private Heuristic<T> heuristic;

    /**
     * The maximum search distance to accept before giving up.
     */
    private int maxSearchDistance;

    /**
     * List of nodes not yet fully searched.
     */
    private ArrayList<MapNode<T>> openList;

    /**
     * List of nodes that have been searched trough.
     */
    private ArrayDeque<MapNode<T>> closedList;

    /**
     * Set of all nodes that have been traversed during the search.
     */
    private HashMap<T, MapNode<T>> traversed;

    /**
     * Constructs new path finder for specified map.
     * @param map       the map object which will be searched for paths
     * @param heuristic the object providing search heuristic method
     */
    public AStarPathFinder(final Map<T> map, final Heuristic<T> heuristic) {
        this(map, heuristic, Integer.MAX_VALUE, 16);
    }

    /**
     * Constructs new path finder for specified map.
     * @param map       the map object which will be searched for paths
     * @param heuristic the object providing search heuristic method
     * @param maxSearchDistance the maximum search distance to accept before giving up
     */
    public AStarPathFinder(final Map<T> map, final Heuristic<T> heuristic, final int maxSearchDistance) {
        this(map, heuristic, maxSearchDistance, 16);
    }

    /**
     * Constructs new path finder for specified map.
     * @param map       the map object which will be searched for paths
     * @param heuristic the object providing search heuristic method
     * @param maxSearchDistance the maximum search distance to accept before giving up
     * @param initialCapacity   the initial capacity of internal node lists used for searching
     */
    public AStarPathFinder(final Map<T> map, final Heuristic<T> heuristic, final int maxSearchDistance, final int initialCapacity) {
        super(map);
        this.heuristic = heuristic;
        this.maxSearchDistance = maxSearchDistance;
        openList = new ArrayList<>(initialCapacity);
        closedList = new ArrayDeque<>(initialCapacity);
        traversed = new HashMap<>(initialCapacity);
    }

    /**
     * Returns heuristic provider.
     * @return  object providing search heuristic method
     */
    public Heuristic<T> getHeuristic() {
        return heuristic;
    }

    /**
     * Sets heuristic provider.
     * @param heuristic object providing search heuristic method
     */
    public void setHeuristic(final Heuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Returns the maximum search distance to accept before giving up.
     * @return maximum search distance to accept before giving up
     */
    public int getMaxSearchDistance() {
        return maxSearchDistance;
    }

    /**
     * Sets the maximum search distance to accept before giving up.
     * @param maxSearchDistance the maximum search distance to accept before giving up
     */
    public void setMaxSearchDistance(final int maxSearchDistance) {
        this.maxSearchDistance = maxSearchDistance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path<T> findPath(final MovingEntity entity, final T source, final T target) {

        if (!map.canMoveTo(entity, target) || source.equals(target)) {
            return null;
        }

        final MapNode<T> start = new MapNode<>(source, 0, 0);

        openList.add(start);
        traversed.put(source, start);

        int searchedDistance = 0;

        while (searchedDistance < maxSearchDistance && !openList.isEmpty()) {

            final MapNode<T> node = openList.get(0);

            if (node.getCoordinate().equals(target)) {
                break;
            }

            closedList.add(openList.remove(0));

            for (T neighbour: map.getNeighbors(node.getCoordinate(), entity)) {

                if (!map.canMoveTo(entity, neighbour)) {
                    continue;
                }

                final float cost = node.getCost() + map.getCost(entity, node.getCoordinate(), neighbour);

                if (!traversed.containsKey(neighbour)) {
                    traversed.put(neighbour, new MapNode<>(neighbour, 0, 0));
                }

                final MapNode<T> neighbourNode = traversed.get(neighbour);

                if (cost < neighbourNode.getCost()) {
                    openList.remove(neighbourNode);
                    closedList.remove(neighbourNode);
                }

                if (!openList.contains(neighbourNode) && !closedList.contains(neighbourNode)) {

                    neighbourNode.setCost(cost);
                    neighbourNode.setHeuristic(heuristic.getHeuristicCost(map, entity, neighbour, target));

                    searchedDistance = Math.max(searchedDistance, neighbourNode.setParent(node));

                    openList.add(neighbourNode);
                    Collections.sort(openList);
                }

                map.pathFinderTraversed(neighbourNode);
            }
        }

        openList.clear();
        closedList.clear();

        if (!traversed.containsKey(target)) {
            traversed.clear();
            return null;
        }

        final Path<T> path = new Path<>();

        MapNode<T> node = traversed.get(target);

        do {

            path.prepend(node.getCoordinate());
            
            node = node.getParent();

        } while (!node.getCoordinate().equals(source));

        traversed.clear();

        return path;
    }
}