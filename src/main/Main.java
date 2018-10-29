package main;

public class Main {
    public static void main(String[] args){
        String puzzle = "800450600030680000000302801100000200060000050009000008907806000000029070006034009";
        long startTime = System.nanoTime();
        byte[][] puzzleArray = new Solver().solve(puzzle);
        if(puzzleArray == null){
            System.out.print("Unsolvable!");
        }else {
            System.out.println("=================");
            for (int y = 0; y <= 8; y++) {
                for (int x = 0; x <= 8; x++) {
                    if (x == 0) {
                        System.out.print("||");
                    }
                    System.out.print(puzzleArray[x][y]);
                    if ((x + 1) % 3 == 0) {
                        System.out.print("||");
                    }
                }
                System.out.println();
                if ((y + 1) % 3 == 0) {
                    System.out.println("=================");
                }
            }
        }
        System.out.println();
        System.out.println((System.nanoTime() - startTime)/1000000 + " ms elapsed.");
    }
}
