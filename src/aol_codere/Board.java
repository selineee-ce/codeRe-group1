package aol_codere;

import java.util.ArrayList;
import java.util.Random;

public class Board{
	int side;
	ArrayList<Point> misses = new ArrayList<Point>();
	ArrayList<Ship> enemyShips = new ArrayList<Ship>();
	
	Board(int side){
		this.side = side;
	}
	
	boolean shootAtEnemy(Point p){
		boolean hit = false;
		boolean dead = false;
		int shipIndex = -1;

		for(int i = 0; i < enemyShips.size(); i++){

			HitResult result = enemyShips.get(i).checkHit(p);

			if(result == HitResult.HIT){
				hit = true;
				enemyShips.get(i).shotFiredAtPoint(p);
			}

			if(result == HitResult.DEAD){
				dead = true;
			}

			if(result == HitResult.HIT && enemyShips.get(i).live.size() == 1){
				shipIndex = i;
			}
		}

		if(hit){
			printBoard();
			System.out.println("Hit! @ (" + p.x + "," + p.y + ").");
		}

		if(dead){
			printBoard();
			System.out.println("(" + p.x + "," + p.y + ") was a miss.");
		}

		if(!hit && !dead){
			handleMiss(p);
			printBoard();
			System.out.println("(" + p.x + "," + p.y + ") was a miss.");
		}

		if(shipIndex > -1){
			System.out.println("Ship of length " +
				enemyShips.get(shipIndex).length + " has been sunk!");
		}

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
			if (s.isDeadAtPoint(p)) return "X ";
			if (s.isLiveAtPoint(p)) return "~ ";
		}

		if (isMiss(p)) return ". ";
		return "~ ";
	}

	boolean addShip(Point start, boolean vertical, int length){
		Ship newShip = new Ship (start, vertical, length);
		
		for (Ship s : enemyShips) {
            if (newShip.collidesWith(s)) return true;
        }
        enemyShips.add(newShip);
        return false;
	}
	
	void addEnemy(int length){
		Random rand = new Random();
		boolean bump = true;
		
		while (bump) {
			boolean vertical = rand.nextBoolean();
			int x = rand.nextInt(side - (vertical ? length : 0));
            int y = rand.nextInt(side - (!vertical ? length : 0));
            bump = addShip(new Point(x, y), vertical, length);
		}
	}
	
	int liveEnemies(){
		int tiles = 0;
			for(int i = 0; i < enemyShips.size(); i++){
				tiles = tiles + enemyShips.get(i).live.size();
			}
		return tiles;
	}

	boolean isMiss(Point p) {
		for (Point m : misses) {
			if (m.x == p.x && m.y == p.y) return true;
		}
		return false;
	}

	void handleMiss(Point p) {
		boolean exists = false;

		for (Point m : misses) {
			if (m.x == p.x && m.y == p.y) {
				exists = true;
				break;
			}
		}

		if (!exists) {
			misses.add(new Point(p.x, p.y));
		}
	}
}