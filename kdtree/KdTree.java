/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {
    private static final int HORIZONTAL = 1;
    private static final int VERTICAL = 0;

    private class Node {
        Node leftChild;
        Node rightChild;
        Point2D point;
        RectHV rect;
        int status; // How the lines are placed

        public Node(Point2D p, int s, double xmin, double xmax, double ymin, double ymax) {
            leftChild = null;
            rightChild = null;
            point = p;
            status = s;
            rect = new RectHV(xmin, ymin, xmax, ymax);
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        if (contains(p)) {
            return;
        }
        size++;
        double xmin = 0, xmax = 1, ymin = 0, ymax = 1;
        if (root == null) {
            root = new Node(p, VERTICAL, xmin, xmax, ymin, ymax);
            return;
        }
        Node cur = root;
        Node lastCur = null;
        while (cur != null) {
            lastCur = cur;
            if (cur.status == VERTICAL) {
                if (p.x() < cur.point.x()) {
                    xmax = cur.point.x();
                    cur = cur.leftChild;
                }
                else {
                    xmin = cur.point.x();
                    cur = cur.rightChild;
                }
            }
            else {
                if (p.y() < cur.point.y()) {
                    ymax = cur.point.y();
                    cur = cur.leftChild;
                }
                else {
                    ymin = cur.point.y();
                    cur = cur.rightChild;
                }
            }
        }
        Node newNode = new Node(p, 1 - lastCur.status, xmin, xmax, ymin, ymax);
        if (lastCur.status == VERTICAL) {
            if (p.x() < lastCur.point.x()) {
                lastCur.leftChild = newNode;
            }
            else {
                lastCur.rightChild = newNode;
            }
        }
        else {
            if (p.y() < lastCur.point.y()) {
                lastCur.leftChild = newNode;
            }
            else {
                lastCur.rightChild = newNode;
            }
        }
    }

    public boolean contains(Point2D p) {
        Node cur = root;
        while (cur != null) {
            if (cur.point == p) {
                return true;
            }
            if (cur.status == VERTICAL) {
                if (p.x() < cur.point.x()) {
                    cur = cur.leftChild;
                }
                else {
                    cur = cur.rightChild;
                }
            }
            else {
                if (p.y() < cur.point.y()) {
                    cur = cur.leftChild;
                }
                else {
                    cur = cur.rightChild;
                }
            }
        }
        return false;
    }

    private void draw(Node now) {
        if (now == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(now.point.x(), now.point.y());
        StdDraw.setPenRadius();
        if (now.status == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(now.point.x(), now.rect.ymin(), now.point.x(), now.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(now.rect.xmin(), now.point.y(), now.rect.xmax(), now.point.y());
        }
        draw(now.leftChild);
        draw(now.rightChild);
    }

    public void draw() {
        draw(root);
    }

    private void range(Node cur, RectHV rect, ArrayList<Point2D> list) {
        if (cur == null) {
            return;
        }
        if (rect.contains(cur.point)) {
            list.add(cur.point);
        }
        if (cur.rightChild != null && rect.intersects(cur.rightChild.rect)) {
            range(cur.rightChild, rect, list);
        }
        if (cur.leftChild != null && rect.intersects(cur.leftChild.rect)) {
            range(cur.leftChild, rect, list);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        range(root, rect, pointsInRange);
        return pointsInRange;
    }

    private class Champion {
        double minDis;
        Point2D champ;

        public Champion() {
            minDis = Double.POSITIVE_INFINITY;
            champ = null;
        }
    }

    private void nearest(Point2D p, Node cur, Champion champion) {
        double dis = cur.point.distanceTo(p);
        if (dis < champion.minDis) {
            champion.minDis = dis;
            champion.champ = cur.point;
        }
        boolean leftFirst = false;
        if ((cur.status == VERTICAL && p.x() < cur.point.x()) ||
                (cur.status == HORIZONTAL && p.y() < cur.point.y())) {
            leftFirst = true;
        }
        if (leftFirst) {
            if (cur.leftChild != null && champion.minDis > cur.leftChild.rect.distanceTo(p)) {
                nearest(p, cur.leftChild, champion);
            }
            if (cur.rightChild != null && champion.minDis > cur.rightChild.rect.distanceTo(p)) {
                nearest(p, cur.rightChild, champion);
            }
        }
        else {
            if (cur.rightChild != null && champion.minDis > cur.rightChild.rect.distanceTo(p)) {
                nearest(p, cur.rightChild, champion);
            }
            if (cur.leftChild != null && champion.minDis > cur.leftChild.rect.distanceTo(p)) {
                nearest(p, cur.leftChild, champion);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        Champion champion = new Champion();
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        nearest(p, root, champion);
        return champion.champ;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(0.81, 0.30);
        Point2D nearest = kdtree.nearest(new Point2D(0.81, 0.30));
        StdDraw.point(nearest.x(), nearest.y());
        StdOut.println(nearest);
    }
}
