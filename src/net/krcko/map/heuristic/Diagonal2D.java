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
package net.krcko.map.heuristic;

import net.krcko.map.Heuristic;
import net.krcko.map.Map;
import net.krcko.map.MovingEntity;
import net.krcko.math.Point2D;

/**
 * A 2-dimensional Diagonal Distance heuristic implementation, for tile-based
 * maps which allow diagonal movement.
 * 
 * This class is only the wrapper for internal classes which implement the
 * heuristic algorithm.
 *
 * @author Aleksandar Ružičić
 */
public abstract class Diagonal2D {

    /**
     * A 2-dimensional Diagonal Distance heuristic implementation for
     * Point2D.Int coordinates.
     */
    public static class Int implements Heuristic<Point2D.Int> {

        /**
         * {@inheritDoc}
         */
        @Override
        public float getHeuristicCost(final Map<Point2D.Int> map, final MovingEntity entity, final Point2D.Int source, final Point2D.Int target) {
            return Math.max(Math.abs(source.x - target.x), Math.abs(source.y - target.y));
        }
    }

    /**
     * A 2-dimensional Diagonal Distance heuristic implementation for
     * Point2D.Float coordinates.
     */
    public static class Float implements Heuristic<Point2D.Float> {

        /**
         * {@inheritDoc}
         */
        @Override
        public float getHeuristicCost(final Map<Point2D.Float> map, final MovingEntity entity, final Point2D.Float source, final Point2D.Float target) {
            return Math.max(Math.abs(source.x - target.x), Math.abs(source.y - target.y));
        }
    }

    /**
     * A 2-dimensional Diagonal Distance heuristic implementation for
     * Point2D.Double coordinates.
     */
    public static class Double implements Heuristic<Point2D.Double> {

        /**
         * {@inheritDoc}
         */
        @Override
        public float getHeuristicCost(final Map<Point2D.Double> map, final MovingEntity entity, final Point2D.Double source, final Point2D.Double target) {
            return (float) Math.max(Math.abs(source.x - target.x), Math.abs(source.y - target.y));
        }
    }
}