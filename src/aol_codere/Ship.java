package aol_codere;

import java.util.ArrayList;

public class Ship{
	ArrayList<Point> live = new ArrayList<Point>();
	ArrayList<Point> dead = new ArrayList<Point>();
	int length;
	
	Ship(Point origin, boolean isVertical, int length){
		this.length = length;
		for(int i = 0; i < length; i++){
			// FIX: Menghilangkan duplikasi penulisan live.add
            int curX = isVertical ? origin.x + i : origin.x;
            int curY = isVertical ? origin.y : origin.y + i;
            live.add(new Point(curX, curY));
		}
	}
	
	// Function untuk menghilangkan duplikasi pengecekan koordinat di list
    private boolean isPointInList(ArrayList<Point> list, Point p) {
        for (Point pt : list) {
            if (pt.x == p.x && pt.y == p.y) return true;
        }
        return false;
    }
    
	boolean containsPoint(Point P){
		boolean on = false;
		for(int i = 0; i < this.length; i++){
			if((live.get(i).x == P.x && live.get(i).y == P.y)){ // || (dead.get(i).x == P.x && dead.get(i).y == P.y)){
				on = true;
			}
		}
		
		return on;
	}
	
	// Only use for initial adding
	boolean collidesWith(Ship S){
		boolean hits = false;
		for(int i = 0; i < this.live.size(); i++){
			for(int j = 0; j < S.live.size(); j++){
				if(this.live.get(i).x == S.live.get(j).x && this.live.get(i).y == S.live.get(j).y){
					hits = true;
				}
			}
		}
		return hits;
	}
	
	//FIX : Menggunakan Function untuk menghindari duplicate code
	int checkHit(Point p){
		if (isPointInList(dead, p)) return 2;
        if (isPointInList(live, p)) return 1;
        return 0;
	}
	
	void shotFiredAtPoint(Point p){
		if(isPointInList(live, p)){
			dead.add(new Point(p.x, p.y));
			live.removeIf(pt -> pt.x == p.x && pt.y == p.y);
		}
	}
	
	
	// FIX : Menggunakan function agar tidak duplicate logika loop
	boolean isDeadAtPoint(Point p){
		return isPointInList(dead, p);
	}
	
	// FIX #2: Method getter untuk menghindari akses langsung (inappropriate intimacy)
	boolean isLiveAtPoint(Point p){
		return isPointInList(live, p);
	}
	
	int hitCount(){
		return dead.size();
	}
}
