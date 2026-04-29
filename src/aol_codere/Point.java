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
		return x == p.x && y == p.y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Point)) return false;
		Point p = (Point) o;
		return x == p.x && y == p.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}