package uk.ac.cam.jdb75.tick6star;

import uk.ac.cam.acr31.life.World;

public class Statistics {
    
    private int generation = 0;    
    private int currentPopulation = 0; // number of cells currently alive
    private int maxPopulation = 0;
    private int minPopulation = 0;
    private double maxGrowthRate = 0;
    private double maxDeathRate = 0;
    private int populationChange = 0;
    private double growthChange = 0;
    
    private int prevAlive; // number of cells previously alive
    private World currentWorld;
    
    /**
     * @return the generation
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * @return the currentPopulation
     */
    public int getCurrentPopulation() {
        return currentPopulation;
    }

    /**
     * @return the maxPopulation
     */
    public int getMaxPopulation() {
        return maxPopulation;
    }

    /**
     * @return the minPopulation
     */
    public int getMinPopulation() {
        return minPopulation;
    }

    /**
     * @return the maxGrowthRate
     */
    public double getMaxGrowthRate() {
        return maxGrowthRate;
    }

    /**
     * @return the maxDeathRate
     */
    public double getMaxDeathRate() {
        return maxDeathRate;
    }
    
    /**
     * @return the populationChange
     */
    public int getPopulationChange() {
        return populationChange;
    }
    
    /**
     * @return the growthChange
     */
    public double getGrowthChange() {
        return growthChange;
    }
    
    public Statistics(World world) {
        currentWorld = world;
        minPopulation = liveCells();
        next(currentWorld);
    }
    
    private int liveCells() {
        int count = 0;
        for (int row = 0; row < currentWorld.getHeight(); row++) {
            for (int col = 0; col < currentWorld.getWidth(); col++) {
                // update the count if a live cell is found
                count += (currentWorld.getCell(col, row)) ? 1 : 0;
            }
        }
        return count;
    }
    
    public void next(World w) {
        prevAlive = currentPopulation;
        currentWorld = w;
        generation += 1;        
        currentPopulation = liveCells();
        double newMaxGrowthRate = (double)Math.round(calcMaximumGrowthRate() * 10000) / 10000;
        double newMaxDeathRate = (double)Math.round(calcMaximumDeathRate() * 10000) / 10000;
        populationChange = (generation == 1) ? 0 : currentPopulation - prevAlive;
        growthChange = newMaxGrowthRate;
        if (currentPopulation > maxPopulation) {
            maxPopulation = currentPopulation;
        }
        if (currentPopulation < minPopulation) {
            minPopulation = currentPopulation;
        }
        if (newMaxGrowthRate > maxGrowthRate) {
            maxGrowthRate = newMaxGrowthRate;
        }
        if (newMaxDeathRate > maxDeathRate) {
            maxDeathRate = newMaxDeathRate;
        }
        
    }
    
    private double calcMaximumGrowthRate() {
        double result = (prevAlive == 0) ? 0 : ( (double)(currentPopulation - prevAlive) / (double)prevAlive ); // discount infinities
        return result;     
    }
    
    private double calcMaximumDeathRate() {
        double result = (prevAlive == 0) ? 0 : ( (double)(prevAlive - currentPopulation) / (double)prevAlive ); // discount infinities
        return result;        
    }   

}
