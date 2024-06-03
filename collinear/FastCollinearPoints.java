/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final int MIN_NUM = 3;
    private ArrayList<LineSegment> segmentArrayList;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("null points");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("null point");
            }
        }
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        int size = sortedPoints.length;
        for (int i = 0; i < size - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException("repetitive point");
            }
        }
        segmentArrayList = new ArrayList<>();
        if (points.length < MIN_NUM + 1) {
            return;
        }
        for (Point p : sortedPoints) {
            Point[] aux = Arrays.copyOf(sortedPoints, sortedPoints.length);
            Arrays.sort(aux, p.slopeOrder());
            double currentSlope = aux[0].slopeTo(aux[1]);
            ArrayList<Point> pointsOnCurrentLine = new ArrayList<>();
            for (int i = 1; i < aux.length; i++) {
                double slopeToI = aux[0].slopeTo(aux[i]);
                int comp = Double.compare(slopeToI, currentSlope);
                if (comp == 0
                        && i != aux.length - 1) {
                    pointsOnCurrentLine.add(aux[i]);
                }
                else {
                    if (comp == 0) {
                        pointsOnCurrentLine.add(aux[i]);
                    }
                    if (pointsOnCurrentLine.size() >= MIN_NUM) {
                        pointsOnCurrentLine.add(aux[0]);
                        Point minPoint = aux[0];
                        Point maxPoint = aux[0];
                        for (Point currentPoint : pointsOnCurrentLine) {
                            if (currentPoint.compareTo(minPoint) < 0) {
                                minPoint = currentPoint;
                            }
                            if (currentPoint.compareTo(maxPoint) > 0) {
                                maxPoint = currentPoint;
                            }
                        }
                        // naive comparing will suffice, sorting can't pass timing
                        if (aux[0].compareTo(minPoint) == 0) {
                            // compare to ensure current point is smallest in line so that there are no duplicates
                            segmentArrayList.add(new LineSegment(minPoint, maxPoint));

                        }
                    }
                    pointsOnCurrentLine.clear();
                    currentSlope = slopeToI;
                    pointsOnCurrentLine.add(aux[i]);
                }
            }
        }
    }

    public int numberOfSegments() {
        return segmentArrayList.size();
    }

    public LineSegment[] segments() {
        return segmentArrayList.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
