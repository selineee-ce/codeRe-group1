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

    private boolean isPointInList(ArrayList<Point> list, Point p) {
        for (Point pt : list) {
            if (pt.equals(p)) return true;
        }
        return false;
    }

    boolean containsPoint(Point p) {
        return isPointInList(live, p) || isPointInList(dead, p);
    }

    boolean collidesWith(Ship s) {
        for (Point a : this.live) {
            for (Point b : s.live) {
                if (a.equals(b)) {
                    return true;
                }
            }
        }
        return false;
    }

    HitResult checkHit(Point p) {
        if (isPointInList(dead, p)) return HitResult.DEAD;
        if (isPointInList(live, p)) return HitResult.HIT;
        return HitResult.MISS;
    }

    void shotFiredAtPoint(Point p) {
        if (isPointInList(live, p)) {
            dead.add(new Point(p.x, p.y));
            live.removeIf(pt -> pt.equals(p));
        }
    }

    boolean isDeadAtPoint(Point p) {
        return isPointInList(dead, p);
    }

    boolean isLiveAtPoint(Point p) {
        return isPointInList(live, p);
    }

    int hitCount() {
        return dead.size();
    }
}