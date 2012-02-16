package uk.ac.cam.jdb75.tick5;

import uk.ac.cam.acr31.life.World;
import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;

public class TestArrayWorld implements World {

    private int generation;
    private int width;
    private int height;
    private boolean[][] cells;

    public TestArrayWorld(int w, int h) {
        width = w;
        height = h;
        // TODO: set generation equal to zero
        generation = 0;
        // TODO: set cells to reference a new rectangular two-dimensional 
        //       boolean array of size height by width
        cells = new boolean[getHeight()][getWidth()];  
    }

    protected TestArrayWorld(TestArrayWorld prev) {
        width = prev.width;
        height = prev.height;
        // TODO: set generation equal to prev.generation+1
        generation = prev.generation+1;
        // TODO: set cells to reference a new rectangular two-dimensional 
        //       boolean array of size height by width
        cells = new boolean[getHeight()][getWidth()];  
    }

    public boolean getCell(int col, int row) { 
        return (row < 0 || row > getHeight() - 1 || col < 0 || col > getWidth() - 1) ? false : cells[row][col];
    }
    public void setCell(int col, int row, boolean alive) { 
        if (row < 0 || row > getHeight() - 1 || col < 0 || col > getWidth() - 1) {} 
        else { 
            cells[row][col] = alive;
        }
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

    private TestArrayWorld nextGeneration() {
        //Construct a new TestArrayWorld object to hold the next generation:
        TestArrayWorld world = new TestArrayWorld(this);
        //TODO: Use for loops with "setCell" and "computeCell" to populate "world"
        for (int row = 0; row < world.getHeight(); row++) {
            for (int col = 0; col < world.getWidth(); col++) {
                world.setCell(col, row, computeCell(col, row));
            }
        }
        return world;
    }

    public World nextGeneration(int log2StepSize)  { 
        TestArrayWorld world = this;
        //TODO: repeat the statement in curly brackets 2^log2StepSize times
        int times = 1 << log2StepSize;
        for (int i = 0; i < times; i++) {
        world = world.nextGeneration();
        }
        return world;
    }

    //TODO: Add any other private methods which you find helpful
    
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