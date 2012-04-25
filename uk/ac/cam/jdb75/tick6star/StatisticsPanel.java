package uk.ac.cam.jdb75.tick6star;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.ac.cam.acr31.life.World;

public class StatisticsPanel extends JPanel {
    
    private Statistics stats;
    private JLabel generation;
    private JLabel population;
    private JLabel maxPopulation;
    private JLabel minPopulation;
    private JLabel maxGrowthRate;
    private JLabel maxDeathRate;
    
    private Graph populationGraph;
    private Graph populationChangeGraph;
    private Graph growthDeathGraph;
    
    public StatisticsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // set the text stats
        JPanel textStats = new JPanel();
        textStats.setBorder(new EmptyBorder(0, 0, 0, 45));
        textStats.setLayout(new BoxLayout(textStats, BoxLayout.Y_AXIS));
        generation = new JLabel("Generation: 0");
        population = new JLabel("Population: 0");
        maxPopulation = new JLabel("Maximum Population: 0");
        minPopulation = new JLabel("Minimum Population: 0");
        maxGrowthRate = new JLabel("Maximum Growth Rate: 0.0000");
        maxDeathRate = new JLabel("Maximum Death Rate: 0.0000");
        textStats.add(generation);
        textStats.add(population);
        textStats.add(maxPopulation);
        textStats.add(minPopulation);
        textStats.add(maxGrowthRate);
        textStats.add(maxDeathRate);
        add(textStats);        
        
        // set the graphs
        populationGraph = new Graph("Population");
        populationChangeGraph = new Graph("Population Change");
        growthDeathGraph = new Graph("Growth/Death Rate");
        populationGraph.setBorder(new EmptyBorder(5, 25, 5, 5));
        populationChangeGraph.setBorder(new EmptyBorder(5, 25, 5, 5));
        growthDeathGraph.setBorder(new EmptyBorder(5, 25, 5, 5));
        add(populationGraph);
        add(populationChangeGraph);
        add(growthDeathGraph);       
        
    }
    
    public void update(World w) {
        if (w == null) return;
        if (stats == null) stats = new Statistics(w);
        else {stats.next(w);}
        
        // update text stats
        generation.setText("Generation: " + stats.getGeneration());
        population.setText("Population: " + stats.getCurrentPopulation());
        maxPopulation.setText("Maximum Population: " + stats.getMaxPopulation());
        minPopulation.setText("Minimum Population: " + stats.getMinPopulation());
        maxGrowthRate.setText("Maximum Growth Rate: " + stats.getMaxGrowthRate());
        maxDeathRate.setText("Maximum Death Rate: " + stats.getMaxDeathRate());
        
        //update graphs
        populationGraph.updateGraph(stats.getGeneration(), stats.getCurrentPopulation(), Color.GREEN, true, "int");
        populationChangeGraph.updateGraph(stats.getGeneration(), stats.getPopulationChange(), Color.RED, false, "int");
        growthDeathGraph.updateGraph(stats.getGeneration(), stats.getGrowthChange(), Color.BLUE, false, "double");
    }
    
    public void reset() {
        stats = null;
        populationGraph.resetData();
        populationChangeGraph.resetData();
        growthDeathGraph.resetData();
    }
}
