package uk.ac.cam.jdb75.tick7star;

import javax.swing.JPanel;

import uk.ac.cam.acr31.life.World;

public class FilmStripPanel extends JPanel {
    
    ArrayWorld[] worlds;
    
    public FilmStripPanel() {
        update(new ArrayWorld(8, 8));
    }
    
    protected void update(World world2) {
        removeAll();
        worlds = new ArrayWorld[20];
        worlds[0] = (ArrayWorld) world2;
        for (int i = 1; i < worlds.length; i++) {
            worlds[i] = (ArrayWorld) worlds[i-1].nextGeneration();
        }
        
        for (ArrayWorld world : worlds) {
            add(new FilmStripElement(world));
            
        } 
        revalidate();
    }    
    
    
}
