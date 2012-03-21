package uk.ac.cam.jdb75.tick4star;

import java.util.ArrayList;
import java.util.Arrays;

public class Statistics {
    
    public String name;
    
    private int alive; // number of cells currently alive
    private double prevAlive; // number of cells previously alive
    private boolean[][] initialWorld;
    private boolean[][] currentWorld;
    private ArrayList<boolean[][]> previousWorlds = new ArrayList<boolean[][]>();
    private int max = 100; // max iterations - results vary depending on this value!
    
    public void clear() { // used for avoiding heap overflow
        previousWorlds.clear();
    }
    
    public Statistics(boolean[][] world, String name) {
        this.name = name;
        initialWorld = world;
        currentWorld = world;        
        liveCells();
    }
    
    private void liveCells() {
        int count = 0;
        for (boolean[] i : currentWorld) {
            for (boolean j : i) {
                // update the count if a live cell is found
                count += (true == j) ? 1 : 0;
            }
        }
        alive = count;
    }
    
    private void next() {
        previousWorlds.add(currentWorld);
        currentWorld = StatisticsLife.nextGeneration(currentWorld);
        liveCells();
    }
    
    public double getMaximumGrowthRate() {
        double rate = 0;
        double test;                        
        for (int i = 0; i < max; i++) {
            prevAlive = alive;
            next();
            test = (prevAlive == 0) ? 0 : ((alive - prevAlive)/prevAlive); // discount infinities
            if (test > rate) {
                rate = test;
            }
        }
        currentWorld = initialWorld;
        clear();
        return rate;
        
    }
    
    public double getMaximumDeathRate() {
        double rate = 0;
        double test;                        
        for (int i = 0; i < max; i++) {
            prevAlive = alive;
            next();
            test = (prevAlive == 0) ? 0 : ((prevAlive - alive)/prevAlive); // discount infinities
            if (test > rate) {
                rate = test;
            }
        }
        currentWorld = initialWorld;
        clear();
        return rate;
        
    }
    
    public int getLoopStart() {
        for(int i = 0; i < max; i++) {
            int start = 0;
            for (boolean[][] world: previousWorlds) {
                if (Arrays.deepEquals(currentWorld, world)) {
                    currentWorld = initialWorld;
                    clear();
                    return start;
                }
                start++;
            }
            next();
        }
        currentWorld = initialWorld;
        clear();
        return -1;       
    }
    
    public int getLoopEnd() {
        int end = 0;
        for(int i = 0; i < max; i++) {
            end += 1;
            for (boolean[][] world: previousWorlds) {                
                if (Arrays.deepEquals(currentWorld, world)) {
                    currentWorld = initialWorld;
                    clear();
                    return (end - 2);
                }                
            }
            next();
        }
        currentWorld = initialWorld;
        clear();
        return -1;
        
    }
    
    public int getMaximumPopulation() {
        int maxLive = 0;
        for (int i = 0; i < max; i++) {
            if (alive > maxLive) {
                maxLive = alive;
            }
            next();
        }
        currentWorld = initialWorld;
        clear();
        return maxLive;        
    }

}
