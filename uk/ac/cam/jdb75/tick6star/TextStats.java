package uk.ac.cam.jdb75.tick6star;

import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.acr31.life.World;

public class TextStats extends JPanel {
    
    private JLabel generation;
    private JLabel population;
    private JLabel maxPopulation;
    private JLabel minPopulation;
    private JLabel maxGrowthRate;
    private JLabel maxDeathRate;
    
    public TextStats() {
        
    }
    
    public void showNew(Statistics stats, World w) {
        stats.next(w);
        
    }
}
