package aol_codere;

import java.util.ArrayList;

public class Ship {
    ArrayList<Point> live = new ArrayList<>();
    ArrayList<Point> dead = new ArrayList<>();
    int length;

    Ship(Point origin, Direction direction, int length) {
        this.length = length;

        for (int i = 0; i < length; i++) {
            int curX = origin.x;
            int curY = origin.y;

            if (direction == Direction.VERTICAL) {
                curY += i;
            } else {
                curX += i;
            }

            live.add(new Point(curX, curY));
        }
    }

    boolean containsPoint(Point p) {
        return live.contains(p) || dead.contains(p);
    }

    boolean collidesWith(Ship s) {
        for (Point a : this.live) {
            if (s.live.contains(a)) return true;
        }
        return false;
    }

    HitResult checkHit(Point p) {
        if (dead.contains(p)) return HitResult.DEAD;
        if (live.contains(p)) return HitResult.HIT;
        return HitResult.MISS;
    }

    void shotFiredAtPoint(Point p) {
        if (live.contains(p)) {
            dead.add(new Point(p.x, p.y));
            live.removeIf(pt -> pt.equals(p));
        }
    }

    boolean isDeadAtPoint(Point p) {
        return dead.contains(p);
    }

    boolean isLiveAtPoint(Point p) {
        return live.contains(p);
    }

    int hitCount() {
        return dead.size();
    }
}