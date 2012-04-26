package uk.ac.cam.jdb75.tick7star;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import uk.ac.cam.acr31.life.World;

public abstract class GamePanel extends JPanel {

    private int zoom = 10; //Number of pixels used to represent a cell
    private int width = 1; //Width of game board in pixels
    private int height = 1;//Height of game board in pixels
    private World current = null;
    
    public GamePanel() {
        addMouseListener(new MouseAdapter(){ 
            public void mousePressed(MouseEvent me) { 
             java.awt.Point p = me.getPoint();
             toggleCell(current, p.x/zoom, p.y/zoom);
            } 
        });
    }
    
    public abstract void toggleCell(World current, int x, int y);

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    protected void paintComponent(Graphics g) {
        if (current == null) return;
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, width, height);
        current.draw(g, width, height);
        if (zoom > 4) {
            g.setColor(java.awt.Color.LIGHT_GRAY);
            for (int col = 0; col<width; col++) {
                g.drawLine(0, col*zoom, width, col*zoom);
            }
            for (int row = 0; row<height; row++) {
                g.drawLine(row*zoom, 0, row*zoom, height);
            }              
        }
    }

    private void computeSize() {
        if (current == null)  return;
        int newWidth = current.getWidth() * zoom;
        int newHeight = current.getHeight() * zoom;
        if (newWidth != width || newHeight != height) {
            width = newWidth;
            height = newHeight;
            revalidate(); //trigger the GamePanel to re-layout its components
        }
    }

    public void display(World w) {
        current = w;
        computeSize();
        repaint();
    }
}