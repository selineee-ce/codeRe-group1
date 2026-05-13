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
        return list.contains(p);
    }

    boolean containsPoint(Point p) {
        return tileStateAt(p) != TileState.WATER;
    }

    TileState tileStateAt(Point p) {
        if (isPointInList(dead, p)) return TileState.HIT;
        if (isPointInList(live, p)) return TileState.SHIP;
        return TileState.WATER;
    }

    boolean collidesWith(Ship s) {
        for (Point a : this.live) {
            for (Point b : s.live) {
                if (a.x == b.x && a.y == b.y) {
                    return true;
                }
            }
        }
        return false;
    }

    HitResult checkHit(Point p) {
        TileState tileState = tileStateAt(p);

        if (tileState == TileState.HIT) return HitResult.HIT;
        if (tileState == TileState.SHIP) {
            if (live.size() == 1) return HitResult.DEAD;
            return HitResult.HIT;
        }
        return HitResult.MISS;
    }

    void shotFiredAtPoint(Point p) {
        if (isPointInList(live, p)) {
            dead.add(new Point(p.x, p.y));
            live.removeIf(pt -> pt.isSame(p));
        }
    }

    boolean isDeadAtPoint(Point p) {
        return tileStateAt(p) == TileState.HIT;
    }

    boolean isLiveAtPoint(Point p) {
        return tileStateAt(p) == TileState.SHIP;
    }

    int hitCount() {
        return dead.size();
    }
}