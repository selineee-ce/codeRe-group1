package aol_codere;

public class Point{
	int x;
	int y;
	
	Point(int m, int n){
		x = m;
		y = n;
	}	
}

boolean isSame(Point p) {
    return x == p.x && y == p.y;
}