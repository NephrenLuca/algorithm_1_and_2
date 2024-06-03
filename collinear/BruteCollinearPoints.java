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

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segmentArrayList;

    public BruteCollinearPoints(Point[] points) {
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
        for (int p = 0; p < size; p++) {
            for (int q = p + 1; q < size; q++) {
                for (int r = q + 1; r < size; r++) {
                    for (int s = r + 1; s < size; s++) {
                        double slopePQ = sortedPoints[p].slopeTo(sortedPoints[q]);
                        double slopePR = sortedPoints[p].slopeTo(sortedPoints[r]);
                        double slopePS = sortedPoints[p].slopeTo(sortedPoints[s]);
                        if (Double.compare(slopePQ, slopePR) == 0
                                && Double.compare(slopePQ, slopePS) == 0) {
                            segmentArrayList.add(new LineSegment(sortedPoints[p], sortedPoints[s]));
                        }
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
