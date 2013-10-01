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
 * PathFinder object - used to find paths on the map.
 *
 * @author Aleksandar Ružičić
 */
public abstract class PathFinder<T> {

    /**
     * The map object which will be searched for paths
     */
    protected Map<T> map;

    /**
     * Constructs new path finder for specified map.
     * @param map   the map object which will be searched for paths
     */
    public PathFinder(final Map<T> map) {
        this.map = map;
    }

    /**
     * Finds path within the map from source point to the target.
     * @param entity    entity which is about to move
     * @param source    starting point on the map
     * @param target    ending point on the map
     * @return  Path which can be used by entity to move from source to target, or <tt>null</tt> if no such path can be found
     */
    public abstract Path<T> findPath(final MovingEntity entity, final T source, final T target);

    /**
     * Returns the map object which will be searched for paths.
     * @return  Map object used for searching
     */
    public Map<T> getMap() {
        return map;
    }

    /**
     * Sets the map object which will be searched for paths.
     * @param map   the map object used for searching
     */
    public void setMap(final Map<T> map) {
        this.map = map;
    }
}