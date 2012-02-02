package uk.ac.cam.jdb75.tick3;

public class Pattern {

    private String name;
    private String author;
    private int width;
    private int height;
    private int startCol;
    private int startRow;
    private String cells;
    
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getStartCol() {
        return startCol;
    }
    public int getStartRow() {
        return startRow;
    }
    public String getCells() {
        return cells;
    }

    public Pattern(String format) {
        String[] fs = format.split(":");
        name = fs[0];
        author = fs[1];
        width = Integer.parseInt(fs[2]);
        height = Integer.parseInt(fs[3]);
        startCol = Integer.parseInt(fs[4]);
        startRow = Integer.parseInt(fs[5]);
        cells = fs[6];
    }

    public void initialise(boolean[][] world) {
        String[] cellsArray = cells.split(" ");
        for (int row = 0; row<cellsArray.length; row++) {
            char[] chars = cellsArray[row].toCharArray();
            for (int col = 0; col<chars.length; col++) {
                if (chars[col] == '1') {
                    world[row+startRow][col+startCol] = true;
                }    
            }
        }
    }
} 