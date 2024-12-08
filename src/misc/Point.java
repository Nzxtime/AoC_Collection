package misc;

import java.util.Objects;

public class Point implements Comparable<Point> {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0, 0);
    }

    public Point(Point point) {
        this(point.getX(), point.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incX(int a) {
        this.x += a;
    }

    public void decX(int a) {
        this.x -= a;
    }

    public void incY(int a) {
        this.y += a;
    }

    public void decY(int a) {
        this.y -= a;
    }

    public void incX() {
        incX(1);
    }

    public void decX() {
        decX(1);
    }

    public void incY() {
        incY(1);
    }

    public void decY() {
        decY(1);
    }

    public void reset() {
        this.x = 0;
        this.y = 0;
    }

    public int getManhattenDistance() {
        return Math.abs(this.x) + Math.abs(this.y);
    }

    public int getManhattenDistance(Point o) {
        return Math.abs(o.x - this.x) + Math.abs(o.y - this.y);
    }

    public double getEuclideanDistance(Point o) {
        return Math.sqrt(Math.pow(this.getX() + o.getX(), 2) + Math.pow(this.getY() + o.getY(), 2));
    }

    public boolean inRangeInclusive(int x_start, int x_end, int y_start, int y_end) {
        return (x_start <= this.x) && (this.x <= x_end) && (y_start <= this.y) && (this.y <= y_end);
    }

    public boolean inRangeExclusive(int x_start, int x_end, int y_start, int y_end) {
        return (x_start < this.x) && (this.x < x_end) && (y_start < this.y) && (this.y < y_end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }

    @Override
    public int compareTo(Point point) {
        return Integer.compare(getManhattenDistance(), point.getManhattenDistance());
    }
}
