package uk.ac.cam.jdb75.tick5;

public class PackedWorld extends WorldImpl {
    
    private long cells;

    protected PackedWorld(int width, int height) {
        super(width, height);
        cells = 0;
    }
    
    protected PackedWorld(PackedWorld prev) {
        super(prev);
        cells = 0;
    }

    @Override
    public boolean getCell(int col, int row) {
        return (col > 7 || row > 7 || col < 0 || row < 0) ? false : PackedLong.get(cells, (col + 8*row));
    }

    @Override
    protected PackedWorld nextGeneration() {
        //Construct a new TestArrayWorld object to hold the next generation:
        PackedWorld world = new PackedWorld(this);
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
        if (col > 7 || row > 7 || col < 0 || row < 0) { } else { cells = PackedLong.set(cells, (col + 8*row), alive); };
    }

}
