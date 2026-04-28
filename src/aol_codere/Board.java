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
		boolean hitLive = false;
		boolean hitDead= false;
		boolean miss = false;
		int shipIndex = -1;
		char x = 'x';
		
		for(int i = 0; i < enemyShips.size(); i++){
			int hitResult = enemyShips.get(i).checkHit(p);
			
			if(hitResult == 2){
				hitDead = true;
				x = 'D';
			}
			
			if(!hitDead && hitResult == 1){
				if(enemyShips.get(i).live.size() == 1){
					shipIndex = i;
				}
				hitLive = true;
				x = 'L';
				enemyShips.get(i).shotFiredAtPoint(p);
			}
			if(!hitDead && !hitLive && hitResult == 0){
				miss = true;
			}
		}
		if(hitLive){
			printBoard();
			System.out.println("Hit! @ (" + p.x + "," + p.y + ").");
		}
		if(hitDead){
			printBoard();
			System.out.println("(" + p.x + "," + p.y + ") was a miss.");
		}
		if(miss){
			boolean exists = false;
			for(int j = 0; j < misses.size(); j++){
				if(misses.get(j).x == p.x && misses.get(j).y == p.y){
					x = 'p';
					exists = true;
				}
			}
			if(!exists){
				x = 'n';
				misses.add(new Point(p.x, p.y));
			}
			printBoard();
			System.out.println("(" + p.x + "," + p.y + ") was a miss.");
		}

		if(shipIndex > -1){
			System.out.println("Ship of length " + enemyShips.get(shipIndex).length + " has been sunk!");
		}
		
		System.out.println(p.x + " " + p.y + " " + x);
		return true;
	}
	
	void printBoard(){
		for(int i = side - 1; i >= 0; i--){
			System.out.print(i +" ");
			for(int j = 0; j < side; j++){
				Point p = new Point(j, i);
				String icon = "~ ";
				
				for (Ship s : enemyShips) {
                    if (s.isDeadAtPoint(p)) { icon = "X "; break; }
                    if (s.isLiveAtPoint(p)) { icon = "~ "; break; } 
                }
                if (icon.equals("~ ")) {
                    for (Point m : misses) {
                        if (m.x == j && m.y == i) { icon = ". "; break; }
                    }
                }
                System.out.print(icon);
			}
			System.out.println();
		}
		
		System.out.print("  ");
		for(int i = 0; i < side; i++){
			System.out.print(i + " ");
		}
		System.out.println();
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
}