package uk.ac.cam.jdb75.tick3star;

import java.io.IOException;

public class AnimatedLife {
    
    private static OutputAnimatedGif gif = null;
    
    private static String formatString = null;
    static int generations = 0;
    static String filename = null;
    
    public static void main(String[] args) {        
        try {
            if (args.length != 3) {
                System.out.println("Error: 3 arguments are required: String world, int generations, String filename"); 
                return;
            }
            formatString = args[0];
            generations = Integer.parseInt(args[1]);
            filename = args[2];
        } catch (NumberFormatException e) {
            System.out.println("Error: Second argument is not an integer.");
            return;
        }
        
        Pattern p = new Pattern(formatString); 
        boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
        p.initialise(world);
        
        try {
            gif = new OutputAnimatedGif(filename);
            play(world);
            gif.close();
        } catch (IOException e) {
            System.out.println("Error: There was a problem writing to the disk.");
            return;
        }
        System.out.println("Operation complete: " + filename);
    }
    
    public static boolean getCell(boolean[][] world, int col, int row) {
        return (row < 0 || row > world.length - 1 || col < 0 || col > world[row].length - 1) ? false : world[row][col];
    }

    public static void setCell(boolean[][] world, int col, int row, boolean value) {
        if (row < 0 || row > world.length - 1 || col < 0 || col > world[row].length - 1) {} 
        else { world[row][col] = value; }
    }

    public static int countNeighbours(boolean[][] world, int col, int row) {
        int neighbours = 0;
        
        // increase neighbours by 1 if getCell is true for each adjacent square 
        neighbours += (getCell(world, col-1, row-1)) ? 1 : 0;
        neighbours += (getCell(world, col, row-1)) ? 1 : 0;
        neighbours += (getCell(world, col+1, row-1)) ? 1 : 0;
        neighbours += (getCell(world, col-1, row)) ? 1 : 0;
        neighbours += (getCell(world, col+1, row)) ? 1 : 0;
        neighbours += (getCell(world, col-1, row+1)) ? 1 : 0;
        neighbours += (getCell(world, col, row+1)) ? 1 : 0;
        neighbours += (getCell(world, col+1, row+1)) ? 1 : 0;

        return neighbours;
    }

    public static boolean computeCell(boolean[][] world, int col, int row) {

        boolean liveCell = getCell(world, col, row);
        int neighbours = countNeighbours(world, col, row);
        boolean nextCell = false;
        
        if (neighbours < 2) {
            nextCell = false;
        }
        if (liveCell && neighbours == 2 || liveCell && neighbours == 3) {
            nextCell = true;
        }
        if (neighbours > 3) {
            nextCell = false;
        }
        if (!liveCell && neighbours == 3) {
            nextCell = true;
        }
    
        return nextCell;
    }

    public static boolean[][] nextGeneration(boolean[][] world) {
        boolean[][] newWorld = new boolean[world.length][];

        for (int row = 0; row < world.length; row++) {
            newWorld[row] = new boolean[world[row].length];
            for (int col = 0; col < world[row].length; col++) {
                setCell(newWorld, col, row, computeCell(world, col, row));
            }
        }
        return newWorld;
    }

    public static void play(boolean[][] world) throws IOException {
        for (int i = 0; i<generations; i++) {
            gif.addFrame(world);
            world = nextGeneration(world);
        }
    }

}
