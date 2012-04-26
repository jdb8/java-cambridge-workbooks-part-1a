package uk.ac.cam.jdb75.tick7star;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import uk.ac.cam.acr31.life.World;

public class FilmStripElement extends JPanel {
    
    private int zoom = 10; //Number of pixels used to represent a cell
    private int width = 80; //Width of game board in pixels
    private int height = 80;//Height of game board in pixels
    private World current = null;
    
    public FilmStripElement(World w) {
        current = w;        
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
    
    protected void paintComponent(Graphics g) {
        if (current.getWidth() > current.getHeight()) {
            zoom = width/current.getWidth();
        } else {
            zoom = height/current.getHeight();
        }
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, 100, 100);
        g.setColor(java.awt.Color.BLACK);
        for (int col = 0; col<current.getWidth(); col++) {
            for (int row = 0; row < current.getHeight(); row++) {
                if (current.getCell(col, row)) {
                    g.fillRect(col*zoom, row*zoom, zoom, zoom);
                }
            }
        }
        
        if (zoom > 6) {
            g.setColor(java.awt.Color.LIGHT_GRAY);
            for (int col = 0; col<width; col++) {
                g.drawLine(0, col*zoom, width, col*zoom);
            }
            for (int row = 0; row<height; row++) {
                g.drawLine(row*zoom, 0, row*zoom, height);
            }              
        }
    }    
}
