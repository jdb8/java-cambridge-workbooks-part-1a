package uk.ac.cam.jdb75.tick3;

public class ArrayLife {
    public static void main(String[] args) throws Exception {
        int size = Integer.parseInt(args[0]);
        long initial = Long.decode(args[1]);
        boolean[][] world = new boolean[size][size];
        //place the long representation of the game board in the centre of "world"
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                world[i+size/2-4][j+size/2-4] = PackedLong.get(initial,i*8+j);
            }
        }
        play(world);
    }

    public static boolean getCell(boolean[][] world, int col, int row) {
        return (row < 0 || row > world.length - 1 || col < 0 || col > world[row].length - 1) ? false : world[row][col];
    }

    public static void setCell(boolean[][] world, int col, int row, boolean value) {
        if (row < 0 || row > world.length - 1 || col < 0 || col > world[row].length - 1) {} 
        else { world[row][col] = value; }
    }

    public static void print(boolean[][] world) {
        System.out.println("-");
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                System.out.print(getCell(world, col, row) ? '#' : "_");
            }
            System.out.println();
        }
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

        // liveCell is true if the cell at position (col,row) in world is live
        boolean liveCell = getCell(world, col, row);

        // neighbours is the number of live neighbours to cell (col,row)
        int neighbours = countNeighbours(world, col, row);

        // we will return this value at the end of the method to indicate whether 
        // cell (col,row) should be live in the next generation
        boolean nextCell = false;
    
        //A live cell with less than two neighbours dies (underpopulation)
        if (neighbours < 2) {
            nextCell = false;
        }
 
        //A live cell with two or three neighbours lives (a balanced population)
        if (liveCell && neighbours == 2 || liveCell && neighbours == 3) {
            nextCell = true;
        }

        //A live cell with with more than three neighbours dies (overcrowding)
        if (neighbours > 3) {
            nextCell = false;
        }

        //A dead cell with exactly three live neighbours comes alive
        if (!liveCell && neighbours == 3) {
            nextCell = true;
        }
    
        return nextCell;
    }

    public static boolean[][] nextGeneration(boolean[][] world) {
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                setCell(world, col, row, computeCell(world, col, row));
            }
        }
        return world;
    }

    public static void play(boolean[][] world) throws Exception {
        int userResponse = 0;
        while (userResponse != 'q') {
            print(world);
            userResponse = System.in.read();
            world = nextGeneration(world);
        }
    }
}