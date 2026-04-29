package aol_codere;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {
	int side;
	Set<Point> misses = new HashSet<>();
	ArrayList<Ship> enemyShips = new ArrayList<Ship>();

	private final BoardRenderer renderer = new BoardRenderer(this);
	private final ShotResolver resolver = new ShotResolver();

	Board(int side) {
		this.side = side;
	}

	boolean shootAtEnemy(Point p) {
		ShotOutcome outcome = resolver.resolveShot(this, p);
		renderer.printBoard();

		switch (outcome.type) {
			case HIT:
				System.out.println("Hit! @ (" + p.x + "," + p.y + ").");
				break;
			case SUNK:
				System.out.println("Hit! @ (" + p.x + "," + p.y + ").");
				System.out.println("Ship of length " + outcome.sunkShipLength + " has been sunk!");
				break;
			default:
				System.out.println("(" + p.x + "," + p.y + ") was a miss.");
				break;
		}

		return true;
	}

	void printBoard() {
		renderer.printBoard();
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
		for (int i = 0; i < enemyShips.size(); i++) {
			tiles = tiles + enemyShips.get(i).live.size();
		}
		return tiles;
	}

	boolean isMiss(Point p) {
		return misses.contains(p);
	}
}