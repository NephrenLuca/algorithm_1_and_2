/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> pointSet;

    public PointSET() {
        pointSet = new SET<>();
    }

    public boolean isEmpty() {
        return pointSet.size() == 0;
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        return pointSet.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point2D point : pointSet) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("null range");
        }
        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                pointsInRange.add(point);
            }
        }
        return pointsInRange;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        Point2D nearest = null;
        double minDis = Double.POSITIVE_INFINITY;
        for (Point2D point : pointSet) {
            double dis = p.distanceTo(point);
            if (dis < minDis) {
                minDis = dis;
                nearest = point;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        brute.draw();
    }
}
