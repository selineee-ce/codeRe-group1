package aol_codere;

public class ShotResolver {

    ShotOutcome resolveShot(Board board, Point p) {
        boolean hit = false;
        boolean alreadyHit = false;
        int sunkShipLength = -1;

        for (int i = 0; i < board.enemyShips.size(); i++) {
            HitResult result = board.enemyShips.get(i).checkHit(p);

            if (result == HitResult.HIT) {
                hit = true;
                board.enemyShips.get(i).shotFiredAtPoint(p);
                if (board.enemyShips.get(i).live.size() == 1) {
                    sunkShipLength = board.enemyShips.get(i).length;
                }
            }

            if (result == HitResult.DEAD) {
                alreadyHit = true;
            }
        }

        if (hit) {
            if (sunkShipLength > 0) {
                return ShotOutcome.sunk(sunkShipLength);
            }
            return ShotOutcome.hit();
        }

        if (alreadyHit) {
            return ShotOutcome.alreadyHit();
        }

        board.misses.add(p);
        return ShotOutcome.miss();
    }
}
