package uk.ac.cam.jdb75.tick6;

import uk.ac.cam.acr31.life.World;

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

    public Pattern(String format) throws PatternFormatException {
        try {
            String[] fs = format.split(":");
            name = fs[0];
            author = fs[1];
            width = Integer.parseInt(fs[2]);
            height = Integer.parseInt(fs[3]);
            startCol = Integer.parseInt(fs[4]);
            startRow = Integer.parseInt(fs[5]);
            cells = fs[6]; 
        } catch (ArrayIndexOutOfBoundsException a) {
            throw new PatternFormatException("Error: insufficient arguments in world pattern");
        } catch (NumberFormatException b) {
            throw new PatternFormatException("Error: world pattern must contain arguments of the correct type");
        }
    }

    public void initialise(World world) throws PatternFormatException {
            String[] cellsArray = cells.split(" ");
            for (int row = 0; row<cellsArray.length; row++) {
                char[] chars = cellsArray[row].toCharArray();
                for (int col = 0; col<chars.length; col++) {
                    if (chars[col] == '1') {
                        world.setCell(col+startCol, row+startRow, true);
                    }
                    else if (chars[col] == '0') { /* do nothing */ }
                    else {
                        throw new PatternFormatException("Error: cells must be described by 1s and 0s");
                    }    
                }
            } 
        
    }
} 