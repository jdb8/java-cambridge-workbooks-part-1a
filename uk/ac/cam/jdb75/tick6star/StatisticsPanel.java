package uk.ac.cam.jdb75.tick6star;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.acr31.life.World;

public class StatisticsPanel extends JPanel {
    
    private Statistics stats;
    private JLabel generation;
    private JLabel population;
    private JLabel maxPopulation;
    private JLabel minPopulation;
    private JLabel maxGrowthRate;
    private JLabel maxDeathRate;
    
    public StatisticsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        generation = new JLabel("Generation: 0", JLabel.CENTER);
        population = new JLabel("Population: 0", JLabel.CENTER);
        maxPopulation = new JLabel("Maximum Population: 0", JLabel.CENTER);
        minPopulation = new JLabel("Minimum Population: 0", JLabel.CENTER);
        maxGrowthRate = new JLabel("Maximum Growth Rate: 0.0000", JLabel.CENTER);
        maxDeathRate = new JLabel("Maximum Death Rate: 0.0000", JLabel.CENTER);
        
        add(generation);
        add(population);
        add(maxPopulation);
        add(minPopulation);
        add(maxGrowthRate);
        add(maxDeathRate);
    }
    
    public void update(World w) {
        if (w == null) return;
        if (stats == null) stats = new Statistics(w);
        else { stats.next(w); }
        generation.setText("Generation: " + stats.getGeneration());
        population.setText("Population: " + stats.getCurrentPopulation());
        maxPopulation.setText("Maximum Population: " + stats.getMaxPopulation());
        minPopulation.setText("Minimum Population: " + stats.getMinPopulation());
        maxGrowthRate.setText("Maximum Growth Rate: " + stats.getMaxGrowthRate());
        maxDeathRate.setText("Maximum Death Rate: " + stats.getMaxDeathRate());
    }
    
    public void reset() {
        stats = null;
    }
}
