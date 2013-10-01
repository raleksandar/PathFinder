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
 * Defines Map entity, which can be searched by PathFinder.
 * 
 * @author Aleksandar Ružičić
 */
public abstract class Map<T> {

    /**
     * Returns neighbor points of specified location
     * @param location  the location which neighbors should be returned
     * @param entity    the entity which is about to be moved
     * @return  array of neighbor points
     */
    public abstract T[] getNeighbors(final T location, final MovingEntity entity);

    /**
     * Checks if entity can be moved to specified target point on the map.
     * @param entity    the entity to be moved
     * @param target    the point on the map to check
     * @return  <tt>true</tt> if entity can be moved to target location
     */
    public abstract boolean canMoveTo(final MovingEntity entity, final T target);

    /**
     * Returns the path cost of moving entity from source point on the map to the target.
     * @param entity    the entity which is about to move
     * @param source    the starting point on the map
     * @param target    the ending point on the map
     * @return  the relative cost of moving from source to target
     */
    public abstract float getCost(final MovingEntity entity, final T source, final T target);

    /**
     * Called by <code>PathFinder</code> when specified point on the map is traversed.
     * This can be useful for testing and debugging purposes.
     * @param node  the map node which have just been traversed
     */
    public void pathFinderTraversed(final MapNode<T> node) {
    }
}