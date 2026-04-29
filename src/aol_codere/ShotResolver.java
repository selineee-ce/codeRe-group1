package aol_codere;

import java.util.ArrayList;

/**
 * Encapsulates shot-resolution logic (divergent-change fix).
 * Determines whether a shot is a hit, sunk, repeat, or miss,
 * and updates ship state accordingly.
 */
public class ShotResolver {
    private final ArrayList<Ship> enemyShips;
    private int lastSunkLength = -1;

    ShotResolver(ArrayList<Ship> enemyShips) {
        this.enemyShips = enemyShips;
    }

    /** Returns the length of the last ship that was sunk, or -1 if none. */
    int getLastSunkLength() {
        return lastSunkLength;
    }

    /**
     * Resolves a shot at the given point against all enemy ships.
     * Updates ship state for hits and returns the outcome.
     */
    ShotOutcome resolveShot(Point p) {
        lastSunkLength = -1;
        for (Ship ship : enemyShips) {
            HitResult result = ship.checkHit(p);
            if (result == HitResult.DEAD) {
                return ShotOutcome.REPEAT;
            }
            if (result == HitResult.HIT) {
                boolean willSink = ship.live.size() == 1;
                ship.shotFiredAtPoint(p);
                if (willSink) {
                    lastSunkLength = ship.length;
                    return ShotOutcome.SUNK;
                }
                return ShotOutcome.HIT;
            }
        }
        return ShotOutcome.MISS;
    }
}
