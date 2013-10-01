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
package net.krcko.math;

import java.io.Serializable;

/**
 * The <code>Point2D</code> class defines a point representing a location
 * in {@code (x,y)} coordinate space.
 * <p>
 * This class is only the abstract superclass for all objects that
 * store a 2D coordinate.
 * The actual storage representation of the coordinates is left to
 * the subclass.
 * <p>
 * Basically this is only extension of java.awt.geom.Point2D class which adds few
 * useful methods like the point rotation. In addition, Point2D.Int class is
 * implemented as an integer precision point.
 *
 * @see java.awt.geom.Point2D
 * @author Aleksandar Ružičić
 */
public abstract class Point2D extends java.awt.geom.Point2D {

    /**
     * Sets the X coordinate of this <code>Point2D</code> in <code>double</code> precision.
     * @param x the X coordinate of this point
     */
    public abstract void setX(final double x);

    /**
     * Sets the Y coordinate of this <code>Point2D</code> in <code>double</code> precision.
     * @param y the Y coordinate of this point
     */
    public abstract void setY(final double y);

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocation(final double x, final double y) {
        setX(x);
        setY(y);
    }

    /**
     * Rotates this <code>Point2D</code> around the origin by the specified angle.
     * @param originX   the X coordinate of the origin of rotation
     * @param originY   the Y coordinate of the origin of rotation
     * @param angle     the angle (in radians) to rotate the point by
     */
    public void rotate(final double originX, final double originY, final double angle) {

        double dX = getX() - originX;
        double dY = getY() - originY;
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        setX(originX + cos * dX - sin * dY);
        setY(originY + sin * dX + cos * dY);
    }

    /**
     * Rotates this <code>Point2D</code> around the origin by the specified angle.
     * @param origin    the origin of rotation
     * @param angle     the angle (in radians) to rotate the point by
     */
    public void rotate(final Point2D origin, final double angle) {
        rotate(origin.getX(), origin.getY(), angle);
    }

    /**
     * Rotates this <code>Point2D</code> around the <code>(0,&nbsp;0)</code> origin by the specified angle.
     * @param angle     the angle (in radians) to rotate the point by
     */
    public void rotate(final double angle) {
        rotate(0, 0, angle);
    }

    /**
     * Rotates a <code>Point2D</code> around the origin by the specified angle.
     * @param point     the point to be rotated
     * @param origin    the origin of rotation
     * @param angle     the angle (in radians) to rotate the point by
     * @return  rotated copy of specified point
     */
    public Point2D rotate(final Point2D point, final Point2D origin, final double angle) {
        final Point2D result = (Point2D) point.clone();
        result.rotate(origin, angle);
        return result;
    }

    /**
     * Implements <code>Point2D</code> with <code>int</code> precision.
     *
     * @author Aleksandar Ružičić
     */
    public static class Int extends Point2D implements Serializable {

        /**
         * The X coordinate of this <code>Point2D</code>.
         * @serial
         */
        public int x;

        /**
         * The Y coordinate of this <code>Point2D</code>.
         * @serial
         */
        public int y;

        /**
         * Constructs new <code>Point2D.Int</code> and initializes it to the
         * <code>(0,&nbsp;0)</code> coordinates.
         */
        public Int() {
            this(0, 0);
        }

        /**
         * Constructs new <code>Point2D.Int</code> and initializes it to the
         * specified coordinates.
         * @param x     the X coordinate
         * @param y     the Y coordinate
         */
        public Int(final int x, final int y) {
            super();
            this.x = x;
            this.y = y;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setX(final double x) {
            this.x = (int) x;
        }

        /**
         * Sets the X coordinate of this <code>Point2D</code> in <code>int</code> precision.
         * @param x the X coordinate of this point
         */
        public void setX(final int x) {
            this.x = x;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setY(final double y) {
            this.y = (int) y;
        }

        /**
         * Sets the Y coordinate of this <code>Point2D</code> in <code>int</code> precision.
         * @param y the Y coordinate of this point
         */
        public void setY(final int y) {
            this.y = y;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getX() {
            return x;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getY() {
            return y;
        }

        /**
	 * Returns a <code>String</code> that represents the value
         * of this <code>Point2D</code>.
         * @return a string representation of this <code>Point2D</code>.
	 */
        @Override
	public String toString() {
	    return "Point2D.Int[" + x + ", " + y + "]";
	}
    }

    /**
     * Implements <code>Point2D</code> with <code>float</code> precision.
     *
     * @author Aleksandar Ružičić
     */
    public static class Float extends Point2D implements Serializable {

        /**
         * The X coordinate of this <code>Point2D</code>.
         * @serial
         */
        public float x;

        /**
         * The Y coordinate of this <code>Point2D</code>.
         * @serial
         */
        public float y;

        /**
         * Constructs new <code>Point2D.Float</code> and initializes it to the
         * <code>(0,&nbsp;0)</code> coordinates.
         */
        public Float() {
            this(0.0f, 0.0f);
        }

        /**
         * Constructs new <code>Point2D.Float</code> and initializes it to the
         * specified coordinates.
         * @param x     the X coordinate
         * @param y     the Y coordinate
         */
        public Float(final float x, final float y) {
            this.x = x;
            this.y = y;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setX(final double x) {
            this.x = (float) x;
        }

        /**
         * Sets the X coordinate of this <code>Point2D</code> in <code>float</code> precision.
         * @param x the X coordinate of this point
         */
        public void setX(final float x) {
            this.x = x;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setY(final double y) {
            this.y = (float) y;
        }

        /**
         * Sets the Y coordinate of this <code>Point2D</code> in <code>float</code> precision.
         * @param y the Y coordinate of this point
         */
        public void setY(final float y) {
            this.y = y;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getX() {
            return x;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getY() {
            return y;
        }

        /**
	 * Returns a <code>String</code> that represents the value
         * of this <code>Point2D</code>.
         * @return a string representation of this <code>Point2D</code>.
	 */
        @Override
	public String toString() {
	    return "Point2D.Float[" + x + ", " + y + "]";
	}
    }

    /**
     * Implements <code>Point2D</code> with <code>double</code> precision.
     *
     * @author Aleksandar Ružičić
     */
    public static class Double extends Point2D implements Serializable {

        /**
         * The X coordinate of this <code>Point2D</code>.
         * @serial
         */
        public double x;

        /**
         * The Y coordinate of this <code>Point2D</code>.
         * @serial
         */
        public double y;

        /**
         * Constructs new <code>Point2D.Double</code> and initializes it to the
         * <code>(0,&nbsp;0)</code> coordinates.
         */
        public Double() {
            this(0.0, 0.0);
        }

        /**
         * Constructs new <code>Point2D.Double</code> and initializes it to the
         * specified coordinates.
         * @param x     the X coordinate
         * @param y     the Y coordinate
         */
        public Double(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setX(final double x) {
            this.x = x;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setY(final double y) {
            this.y = y;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getX() {
            return x;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getY() {
            return y;
        }

        /**
	 * Returns a <code>String</code> that represents the value
         * of this <code>Point2D</code>.
         * @return a string representation of this <code>Point2D</code>.
	 */
        @Override
	public String toString() {
	    return "Point2D.Double[" + x + ", " + y + "]";
	}
    }
}