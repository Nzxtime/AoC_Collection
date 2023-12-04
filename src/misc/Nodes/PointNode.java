package misc.Nodes;

import misc.Point;

import java.util.Objects;

public class PointNode implements Node<Point>, Comparable<PointNode> {
    private Point point;

    public PointNode(Point point) {
        this.point = new Point(point);
    }

    @Override
    public Point getIdentifier() {
        return point;
    }

    @Override
    public void setIdentifier(Point identifier) {
        this.point = new Point(identifier);
    }

    @Override
    public String toString() {
        return point.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointNode pointNode = (PointNode) o;
        return Objects.equals(point, pointNode.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }

    @Override
    public int compareTo(PointNode o) {
        return this.point.compareTo(o.point);
    }
}
