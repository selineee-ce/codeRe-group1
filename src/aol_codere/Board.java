package aol_codere;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    int side;
    ArrayList<Point> misses = new ArrayList<Point>();
    ArrayList<Ship> enemyShips = new ArrayList<Ship>();

    Board(int side) {
        this.side = side;
    }

    boolean shootAtEnemy(Point p) {
        Ship sunkShip = null;

        for (Ship ship : enemyShips) {
            HitResult result = ship.checkHit(p);

            if (result == HitResult.DEAD) {
                ship.shotFiredAtPoint(p);
                sunkShip = ship;
                printBoard();
                System.out.println("Hit! @ (" + p.x + "," + p.y + ").");
                System.out.println("Ship of length " + ship.length + " has been sunk!");
                return true;
            }

            if (result == HitResult.HIT) {
                ship.shotFiredAtPoint(p);
                printBoard();
                System.out.println("Hit! @ (" + p.x + "," + p.y + ").");
                return true;
            }
        }

        handleMiss(p);
        printBoard();
        System.out.println("(" + p.x + "," + p.y + ") was a miss.");
        return true;
    }

    void printBoard() {
        for (int i = side - 1; i >= 0; i--) {
            System.out.print(i + " ");

            for (int j = 0; j < side; j++) {
                System.out.print(getCell(new Point(j, i)));
            }

            System.out.println();
        }

        System.out.print("  ");
        for (int i = 0; i < side; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    String getCell(Point p) {
        for (Ship s : enemyShips) {
            TileState tileState = s.tileStateAt(p);

            if (tileState == TileState.HIT) return "X ";
            if (tileState == TileState.SHIP) return "~ ";
        }

        if (isMiss(p)) return ". ";
        return "~ ";
    }

    boolean addShip(Point start, boolean vertical, int length) {
        Direction dir = vertical ? Direction.VERTICAL : Direction.HORIZONTAL;
        Ship newShip = new Ship(start, dir, length);

        for (Ship s : enemyShips) {
            if (newShip.collidesWith(s)) return true;
        }
        enemyShips.add(newShip);
        return false;
    }

    void addEnemy(int length) {
        Random rand = new Random();
        boolean bump = true;

        while (bump) {
            boolean vertical = rand.nextBoolean();
            int x = rand.nextInt(side - (vertical ? length : 0));
            int y = rand.nextInt(side - (!vertical ? length : 0));
            bump = addShip(new Point(x, y), vertical, length);
        }
    }

    int liveEnemies() {
        int tiles = 0;
        for (Ship ship : enemyShips) {
            tiles += ship.live.size();
        }
        return tiles;
    }

    boolean isMiss(Point p) {
        return misses.contains(p);
    }

    void handleMiss(Point p) {
        if (!misses.contains(p)) {
            misses.add(new Point(p.x, p.y));
        }
    }
}