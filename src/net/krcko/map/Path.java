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

/**
 * Path - represents list of steps required to travel from starting point on the
 * map to the end point.
 *
 * @author Aleksandar Ružičić
 */
public class Path<T> {

    /**
     * Internal step list
     */
    private ArrayList<T> list;

    /**
     * Constructs an empty path with initial capacity of ten.
     */
    public Path() {
        this(10);
    }

    /**
     * Constructs an empty path with the specified initial capacity.
     * @param initialCapacity   initial capacity of the path
     */
    public Path(final int initialCapacity) {
        list = new ArrayList<>(initialCapacity);
    }
    
    /**
     * Constructs path containing the steps of the specified path.
     * @param path  the path whose steps are to be placed into this path
     */
    public Path(Path<T> path) {
        list = new ArrayList<>(path.list);
    }

    /**
     * Returns the number of steps in this path.
     * @return  the number of steps in this path
     */
    public int getLength() {
        return list.size();
    }

    /**
     * Returns step at the specified position in this path.
     * @param index index of the step to return
     * @return step at the specified position in this path
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= getLength())
     */
    public T getStep(final int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    /**
     * Appends specified step to the end of this path.
     * @param step  step to append to path
     */
    public void append(final T step) {
        list.add(step);
    }

    /**
     * Prepends specified step to the begining of this list.
     * @param step  step to prepend to path
     */
    public void prepend(final T step) {
        list.add(0, step);
    }

    /**
     * Returns <tt>true</tt> if this path contains the specified step.
     * @param step  step whose presence in this path is to be tested
     * @return  <tt>true</tt> if this list contains the specified element
     */
    public boolean contains(final T step) {
        return list.contains(step);
    }
}