package uk.ac.cam.jdb75.tick4;

import java.util.List;
import java.io.IOException;

public class LoaderLife {
    public static void main(String[] args) throws Exception {
        try {
            String filename = args[0];
            List<Pattern> ps = filename.startsWith("http://") ? PatternLoader.loadFromURL(filename) : PatternLoader.loadFromDisk(filename);
            if (ps.isEmpty()) {throw new PatternFormatException("Error: no valid patterns found in file");}
                
            if (args.length < 2) {
                int i = 0;
                for (Pattern p: ps) {
                    // use the public methods of Pattern to retrieve the data to print (as well as converting ints)
                    System.out.println(Integer.toString(i) + ") " + p.getName() + ":" + p.getAuthor() + ":" + Integer.toString(p.getWidth()) + ":" + Integer.toString(p.getHeight()) + ":" + Integer.toString(p.getStartCol()) + ":" + Integer.toString(p.getStartRow()) + ":" + p.getCells());
                    i++;
                }
            } else {
                int chosen = Integer.parseInt(args[1]);
                Pattern p = ps.get(chosen);
                boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
                p.initialise(world);
                play(world);
            }    
                    
        } catch (ArrayIndexOutOfBoundsException b) { // initial arguments insufficient
                System.out.println("Error: must supply at least one argument");
        } catch (PatternFormatException e) {
                System.out.println(e.getMessage());
        } catch (NumberFormatException b) { // second argument cannot be parsed as int
                System.out.println("Error: second argument must be an integer");
        } catch (IndexOutOfBoundsException i) { // pattern list doesn't have the chosen pattern
                System.out.println("Error: pattern number supplied did not match any patterns loaded");
        } catch (IOException io) {
                System.out.println("Error: a problem was encountered while reading the file");
        }    
        
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
                System.out.print(getCell(world, col, row) ? "#" : "_");
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
        boolean[][] newWorld = new boolean[world.length][];

        for (int row = 0; row < world.length; row++) {
            newWorld[row] = new boolean[world[row].length];
            for (int col = 0; col < world[row].length; col++) {
                setCell(newWorld, col, row, computeCell(world, col, row));
            }
        }
        return newWorld;
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