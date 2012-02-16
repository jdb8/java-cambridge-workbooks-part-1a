package uk.ac.cam.jdb75.tick5;

import uk.ac.cam.acr31.life.World;

import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;

public class TestPackedWorld implements World {

    private int generation;
    private int width;
    private int height;
    private long cells;

    public TestPackedWorld() {
        width = 8;
        height = 8;
        // set generation equal to zero
        generation = 0;
        // set cells to reference a new rectangular two-dimensional 
        //       boolean array of size height by width
        cells = 0;  
    }

    protected TestPackedWorld(TestPackedWorld prev) {
        width = 8;
        height = 8;
        // set generation equal to prev.generation+1
        generation = prev.generation+1;
        // set cells to reference a new rectangular two-dimensional 
        //       boolean array of size height by width
        cells = 0;  
    }

    public boolean getCell(int col, int row) { 
        return (col > 7 || row > 7 || col < 0 || row < 0) ? false : PackedLong.get(cells, (col + 8*row));
    }
    public void setCell(int col, int row, boolean alive) { 
        if (col > 7 || row > 7 || col < 0 || row < 0) { } else { PackedLong.set(cells, (col + 8*row), alive); };
    }
    public int getWidth()  { 
        return this.width;
    }
    public int getHeight()  { 
        return this.height;
    }
    public int getGeneration()  { 
        return this.generation;
    }
    public int getPopulation()  { return 0; }
    public void print(Writer w)  { 
        PrintWriter pw = new PrintWriter(w);
        pw.println("-");
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                pw.print(getCell(col, row) ? "#" : "_");
            }
            pw.println();
        }
        pw.flush();
    }
    public void draw(Graphics g, int width, int height)  { /*Leave empty*/ }

    private TestPackedWorld nextGeneration() {
        //Construct a new TestArrayWorld object to hold the next generation:
        TestPackedWorld world = new TestPackedWorld(this);
        // Use for loops with "setCell" and "computeCell" to populate "world"
        for (int row = 0; row < world.getHeight(); row++) {
            for (int col = 0; col < world.getWidth(); col++) {
                world.setCell(col, row, computeCell(col, row));
            }
        }
        return world;
    }

    public World nextGeneration(int log2StepSize)  { 
        TestPackedWorld world = this;
        // repeat the statement in curly brackets 2^log2StepSize times
        int times = 1 << log2StepSize;
        for (int i = 0; i < times; i++) {
            world = world.nextGeneration();
        }
        return world;
    }

    // Add any other private methods which you find helpful
    
    private int countNeighbours(int col, int row) {
        int neighbours = 0;
        
        // increase neighbours by 1 if getCell is true for each adjacent square 
        neighbours += (getCell(col-1, row-1)) ? 1 : 0;
        neighbours += (getCell(col, row-1)) ? 1 : 0;
        neighbours += (getCell(col+1, row-1)) ? 1 : 0;
        neighbours += (getCell(col-1, row)) ? 1 : 0;
        neighbours += (getCell(col+1, row)) ? 1 : 0;
        neighbours += (getCell(col-1, row+1)) ? 1 : 0;
        neighbours += (getCell(col, row+1)) ? 1 : 0;
        neighbours += (getCell(col+1, row+1)) ? 1 : 0;

        return neighbours;
    }

    private boolean computeCell(int col, int row) {

        // liveCell is true if the cell at position (col,row) in world is live
        boolean liveCell = getCell(col, row);

        // neighbours is the number of live neighbours to cell (col,row)
        int neighbours = countNeighbours(col, row);

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
}