package uk.ac.cam.jdb75.tick4star;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class StatisticsLife {
    
    public static Statistics analyse(Pattern p) throws PatternFormatException {
        boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
        p.initialise(world);
        Statistics stats = new Statistics(world, p.getName());
        return stats;        
    }
    
    public static void main(String[] args) {
        ArrayList<Statistics> statList = new ArrayList<Statistics>();
        int longestStart = 0; String longestStartName = null;
        int longestCycle = 0; String longestCycleName = null;
        double biggestGrowthRate = 0; String biggestGrowthRateName = null;
        double biggestDeathRate = 0; String biggestDeathRateName = null;
        int largestPopulation = 0; String largestPopulationName = null;
        
        int curLongestStart;
        int curLongestCycle;
        double curBiggestGrowthRate;
        double curBiggestDeathRate;
        int curLargestPopulation;        
        
        if (args.length < 1) {
            System.out.println("Error: Must supply a file to analyse.");
        }
        String filename = args[0];
        List<Pattern> ps;
        try {
            ps = filename.startsWith("http://") ? PatternLoader.loadFromURL(filename) : PatternLoader.loadFromDisk(filename);
        } catch (IOException e) {
            System.out.println("Error: The specified file could not be read.");
            return;
        }
        
        for (Pattern p : ps) {
            try {
                statList.add(analyse(p));
            } catch (PatternFormatException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        
        for (Statistics stats : statList) {
            System.out.println("Analysing " + stats.name);
            curLongestStart = stats.getLoopStart();
            curLongestCycle = stats.getLoopEnd() - stats.getLoopStart();
            curBiggestGrowthRate = stats.getMaximumGrowthRate();
            curBiggestDeathRate = stats.getMaximumDeathRate();
            curLargestPopulation = stats.getMaximumPopulation();
            
            if (curLongestStart > longestStart) {
                longestStart = curLongestStart;
                longestStartName = stats.name;
            }
            
            if (curLongestCycle > longestCycle) {
                longestCycle = curLongestCycle;
                longestCycleName = stats.name;
            }
            
            if (curBiggestGrowthRate > biggestGrowthRate) {
                biggestGrowthRate = curBiggestGrowthRate;
                biggestGrowthRateName = stats.name;
            }
            
            if (curBiggestDeathRate > biggestDeathRate) {
                biggestDeathRate = curBiggestDeathRate;
                biggestDeathRateName = stats.name;
            }
            
            if (curLargestPopulation > largestPopulation) {
                largestPopulation = curLargestPopulation;
                largestPopulationName = stats.name;
            }
            
            //System.out.println("TESTING - curLongestCycle: " + stats.name + " (" + curLongestCycle + ")");
            //System.out.println("TESTING - getLoopStart: " + stats.name + " (" + stats.getLoopStart() + ")");
            //System.out.println("TESTING - getLoopEnd: " + stats.name + " (" + stats.getLoopEnd() + ")");
            
            stats.clear();
        }
        
        System.out.println(" ");
        System.out.println("Longest start: " + longestStartName + " (" + longestStart + ")");
        System.out.println("Longest cycle: " + longestCycleName + " (" + longestCycle + ")");
        System.out.println("Biggest growth rate: " + biggestGrowthRateName + " (" + biggestGrowthRate + ")");
        System.out.println("Biggest death rate: " + biggestDeathRateName + " (" + biggestDeathRate + ")");
        System.out.println("Largest population: " + largestPopulationName + " (" + largestPopulation + ")");                    
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
}