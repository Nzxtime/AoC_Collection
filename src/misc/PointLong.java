package misc;

import java.util.Objects;

public class PointLong implements Comparable<PointLong> {
    private long x;
    private long y;

    public PointLong(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public PointLong() {
        this(0, 0);
    }

    public PointLong(PointLong point) {
        this(point.getX(), point.getY());
    }

    public long getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public long getY() {
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

    public long getManhattenDistance() {
        return Math.abs(this.x) + Math.abs(this.y);
    }

    public long getManhattenDistance(PointLong o) {
        return Math.abs(o.x - this.x) + Math.abs(o.y - this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointLong point = (PointLong) o;
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
    public int compareTo(PointLong point) {
        return Long.compare(getManhattenDistance(), point.getManhattenDistance());
    }
}
