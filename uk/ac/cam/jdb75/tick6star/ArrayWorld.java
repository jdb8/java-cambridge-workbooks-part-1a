package uk.ac.cam.jdb75.tick6star;

public class ArrayWorld extends WorldImpl {
    
    private boolean[][] cells;

    protected ArrayWorld(int width, int height) {
        super(width, height);
        cells = new boolean[getWidth()][getHeight()];
    }
    
    protected ArrayWorld(ArrayWorld prev) {
        super(prev);
        cells = new boolean[getWidth()][getHeight()];
    }

    @Override
    public boolean getCell(int col, int row) {
        return (row < 0 || row > getHeight() - 1 || col < 0 || col > getWidth() - 1) ? false : cells[col][row];
    }

    @Override
    protected ArrayWorld nextGeneration() {
        //Construct a new TestArrayWorld object to hold the next generation:
        ArrayWorld world = new ArrayWorld(this);
        // Use for loops with "setCell" and "computeCell" to populate "world"
        for (int row = 0; row < world.getHeight(); row++) {
            for (int col = 0; col < world.getWidth(); col++) {
                world.setCell(col, row, computeCell(col, row));
            }
        }
        return world;
    }

    @Override
    public void setCell(int col, int row, boolean alive) {
        if (row < 0 || row > getHeight() - 1 || col < 0 || col > getWidth() - 1) {} 
        else { 
            cells[col][row] = alive;
        }
    }

}
