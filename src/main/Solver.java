package main;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Solves sudokus using backtracking.
 * @author Panagiotis Karapas
 * @version 2.0
 */
@SuppressWarnings("WeakerAccess")
public class Solver {
    private byte[][] puzzleArray = new byte[9][9];//we use byte since our values go from 0-9 and an int would be overkill
    //I have to use short and not byte because if i used byte it would make the x -8 when it should be 8
    // (because java doesn't support unsigned values)
    private ArrayList<Short> changeableCords = new ArrayList<>();//the first 4 bits are the x cord and the second 4 are the y cord.


    private void parseTheInput(@NotNull String puzzle) {
        if(puzzle == null)throw new NullPointerException("The puzzle can't be null!");
        if(puzzle.length() != 81) throw new IllegalArgumentException("Puzzle must have a length of exactly 81!");
        if(Arrays.stream(puzzle.split("")).anyMatch(x->!"0123456789".contains(x)))throw new IllegalArgumentException("Only numbers 0-9 are allowed in the input puzzle!");
        int index = 0;
        for(byte y = 0;y <=8;y++){
            for(byte x = 0;x <=8;x++){
                puzzleArray[x][y] = (byte) (puzzle.charAt(index) - '0');
                if(puzzle.charAt(index) == '0'){
                    changeableCords.add((short) ((x<<4) | y));
                }
                index++;
            }
        }
    }

    /**
     * Solves the provided puzzle, returns a byte array representing the solved sudoku or null if no solution could be found.
     * @param puzzle the puzzle to be solved. The representation is left to right, top to bottom, using 0s as empty cells.
     * @return a byte array representing the solved sudoku or null if no solution could be found.
     */
    public byte[][] solve(@NotNull String puzzle){
        parseTheInput(puzzle);
        byte current = 0;
        while(current < changeableCords.size()){
            byte currentX = (byte) (changeableCords.get(current) >> 4);
            byte currentY = (byte) (changeableCords.get(current) & 0b00001111);
            if(is_Tile_Ok(currentX,currentY) && puzzleArray[currentX][currentY] <= 9){
                current++;
            }else if(puzzleArray[currentX][currentY] == 0){
                puzzleArray[currentX][currentY] = 1;
            }else if(puzzleArray[currentX][currentY] >= 9){
                puzzleArray[currentX][currentY] = 0;
                if(--current == -1)return null;
                byte prevX = (byte) (changeableCords.get(current) >> 4);
                byte prevY = (byte) (changeableCords.get(current) & 0b00001111);

                puzzleArray[prevX][prevY] = (byte) (puzzleArray[prevX][prevY] + 1);
            }else{
                puzzleArray[currentX][currentY] = (byte) (puzzleArray[currentX][currentY] + 1);
            }
        }
        return puzzleArray;
    }

    private boolean is_Tile_Ok(byte x, byte y) {
        if(puzzleArray[x][y]==0)return false;

        for(byte a = 0;a<=8;a++){
            if((puzzleArray[x][y] == puzzleArray[a][y] && a != x) ||
                    (puzzleArray[x][y] == puzzleArray[x][a] && a != y)){
                return false;
            }
        }
        byte[] center = new byte[2];

        if(x <= 2) center[0] = 1;
        else if(x <= 5) center[0] = 4;
        else center[0] = 7;

        if(y <= 2) center[1] = 1;
        else if(y <= 5) center[1] = 4;
        else center[1] = 7;

        for(int divX = -1; divX <= 1;divX++){
            for(int divY = -1; divY <= 1;divY++){

                if(x != center[0] + divX && y != center[1] + divY &&
                        puzzleArray[x][y]==(puzzleArray[center[0] + divX][center[1] + divY])){
                    return false;
                }
            }
        }


        return true;
    }
}
