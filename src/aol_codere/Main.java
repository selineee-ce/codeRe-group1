package aol_codere;

import java.util.*;
import java.lang.String;


public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Welcome to battleship!\n");
		Board field = new Board(10);
		
		field.printBoard();
	
		field.addEnemy(5);
		field.addEnemy(4);
		field.addEnemy(3);
		field.addEnemy(3);
		field.addEnemy(2);
		
		String str;
		String[] coords;
		
		while(field.liveEnemies() > 0){
			System.out.println("Where will you shoot next?");
			str = input.nextLine(); 
			coords = str.replaceAll("^\\D+","").split("\\D+");
			
			field.shootAtEnemy(new Point(Integer.parseInt(coords[0]),Integer.parseInt(coords[1])));
		}
		
		System.out.println("\nYou've destroyed the enemy!");
		
		input.close();
	}

}