/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        if (y == that.y) {
            return +0.0;
        }
        return (that.y - y) * 1.0 / (that.x - x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (y < that.y || (y == that.y && x < that.x)) {
            return -1;
        }
        if (y == that.y && x == that.x) {
            return 0;
        }
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (a, b) -> Double.compare(slopeTo(a), slopeTo(b));
    }


    /**
     * Returns a string representation of this point.
     * This method is provided for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point a = new Point(1, 1);
        Point b = new Point(1, 1);
        StdOut.println(a.compareTo(b));
        StdOut.println(a.slopeTo(b));
        int n = 5;
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Point(StdRandom.uniformInt(n), StdRandom.uniformInt(n));
        }
        StdOut.println("before sort");
        for (int i = 0; i < n; i++) StdOut.print(p[i] + " ");
        StdOut.println();

        Arrays.sort(p);
        StdOut.println("sort by coord");
        for (int i = 0; i < n; i++) StdOut.print(p[i] + " ");
        StdOut.println();

        Arrays.sort(p, p[0].slopeOrder()); // sort by slope relative to (0, 0)
        StdOut.println("sort by slope");
        for (int i = 0; i < n; i++) StdOut.print(p[i] + " ");
    }
}

