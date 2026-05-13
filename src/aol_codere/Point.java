package aol_codere;

import java.util.Objects;

public class Point {
    int x;
    int y;

    Point(int m, int n) {
        x = m;
        y = n;
    }

    boolean isSame(Point p) {
        return equals(p);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;

        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}