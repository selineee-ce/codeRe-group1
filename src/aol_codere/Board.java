package aol_codere;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {
	int side;
	Set<Point> misses = new HashSet<>();
	ArrayList<Ship> enemyShips = new ArrayList<>();

	private final BoardRenderer renderer;
	private final ShotResolver resolver;

	Board(int side) {
		this.side = side;
		this.renderer = new BoardRenderer(this);
		this.resolver = new ShotResolver(enemyShips);
	}

	boolean shootAtEnemy(Point p) {
		ShotOutcome outcome = resolver.resolveShot(p);

		if (outcome == ShotOutcome.MISS) {
			misses.add(p);
		}

		renderer.printBoard();

		if (outcome == ShotOutcome.HIT || outcome == ShotOutcome.SUNK) {
			System.out.println("Hit! @ (" + p.x + "," + p.y + ").");
		} else {
			System.out.println("(" + p.x + "," + p.y + ") was a miss.");
		}

		if (outcome == ShotOutcome.SUNK) {
			System.out.println("Ship of length " + resolver.getLastSunkLength() + " has been sunk!");
		}

		return true;
	}

	void printBoard() {
		renderer.printBoard();
	}

	boolean isMiss(Point p) {
		return misses.contains(p);
	}

	boolean addShip(Point start, boolean vertical, int length) {
		Ship newShip = new Ship(start, vertical ? Direction.VERTICAL : Direction.HORIZONTAL, length);

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
}