/*notable puzzles :
 * 
 * Most Time Needed: .....9.......4..5.68.........4....7....62........8.......9..8.6........3..3..52..
 * 					52806 ms
 * 
 * Less Time Needed: 8..45.6...3.68.......3.28.11.....2...6.....5...9.....89.78.6.......29.7...6.34..9
 * 					91 ms
*/
package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static String[][] puzzleArray = new String[9][9];
	static ArrayList<String> centers = new ArrayList<String>();
	
	
	public static void main(String[] args){
		//Puzzle format:from left to right, top to bottom. Use dots for empty cells.
		String puzzle = "8..45.6...3.68.......3.28.11.....2...6.....5...9.....89.78.6.......29.7...6.34..9";

		long startTime = System.nanoTime();
		//This are the centers of the nine 3x3  
		centers.add("1,1");
		centers.add("4,1");
		centers.add("7,1");
		
		centers.add("1,4");
		centers.add("4,4");
		centers.add("7,4");
		
		centers.add("1,7");
		centers.add("4,7");
		centers.add("7,7");
		
		
		
		ArrayList<String> Changable = new ArrayList<String>();
		
		int current = 0;
		int index = 0;
		for(int y = 0;y <=8;y++){
			for(int x = 0;x <=8;x++){
				puzzleArray[x][y] = puzzle.substring(index,index + 1);
				if(puzzle.substring(index,index + 1).equals(".")){
					Changable.add(x + "," + y);
				}
				index++;
			}
		}
		while(current < Changable.size()){	
			int CurrentX = Integer.parseInt(Changable.get(current).split(",")[0]);
			int CurrentY = Integer.parseInt(Changable.get(current).split(",")[1]);			
			if(is_Tile_Ok(CurrentX,CurrentY) && Integer.parseInt(puzzleArray[CurrentX][CurrentY]) <= 9){
				current++;
			}else if(puzzleArray[CurrentX][CurrentY].equals(".")){
				puzzleArray[CurrentX][CurrentY] = "1";
			}else if(Integer.parseInt(puzzleArray[CurrentX][CurrentY]) >= 9){
				puzzleArray[CurrentX][CurrentY] = ".";
				current--;
				puzzleArray[Integer.parseInt(Changable.get(current).split(",")[0])][Integer.parseInt(Changable.get(current).split(",")[1])] = Integer.toString(Integer.parseInt(puzzleArray[Integer.parseInt(Changable.get(current).split(",")[0])][Integer.parseInt(Changable.get(current).split(",")[1])]) + 1);
			}else{
				puzzleArray[CurrentX][CurrentY] = Integer.toString(Integer.parseInt(puzzleArray[CurrentX][CurrentY]) + 1);
			}
		}
		System.out.println("=================");
		for(int y = 0; y<=8;y++){
			for(int x = 0; x<=8;x++){
				if(x == 0){
					System.out.print("||");
				}
				System.out.print(puzzleArray[x][y]);
				if((x + 1) % 3 == 0){
					System.out.print("||");
				}
			}
			System.out.println();
			if((y + 1) % 3 == 0){
				System.out.println("=================");
			}
		}
		System.out.println();
		System.out.println((System.nanoTime() - startTime)/1000000 + " ms elapsed.");
	}

	private static boolean is_Tile_Ok(int x, int y) {
		int center = 0;
		
		if(puzzleArray[x][y].equals(".")){
			return false;
		}
		for(int a = 0;a<=8;a++){
			if(puzzleArray[x][y].equals(puzzleArray[a][y]) && a != x ){
				return false;
			}else if(puzzleArray[x][y].equals(puzzleArray[x][a]) && a != y){
				return false;
			}
		}

		a:
		for(int divX = -1; divX <= 1;divX++){
			for(int divY = -1; divY <= 1;divY++){
				if(centers.contains((x + divX) + "," + (y + divY))){
					center = centers.indexOf((x + divX)+ "," + (y + divY));
					break a;
				}
			}
		}
		
		for(int divX = -1; divX <= 1;divX++){
			for(int divY = -1; divY <= 1;divY++){
				if(x != Integer.parseInt(centers.get(center).split(",")[0]) + divX && y != Integer.parseInt(centers.get(center).split(",")[1]) + divY && puzzleArray[x][y].equals(puzzleArray[Integer.parseInt(centers.get(center).split(",")[0]) + divX][Integer.parseInt(centers.get(center).split(",")[1]) + divY])){
					return false;
				}
			}
		}
		
		return true;
	}
	
}
