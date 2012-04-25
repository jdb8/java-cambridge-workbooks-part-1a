package uk.ac.cam.jdb75.tick6star;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.ac.cam.acr31.life.World;

public class Graph extends JPanel {
    
    private double max = 0;
    private double min = 0;
    private int first = 1;
    private int last = 100;
    
    private Plot plot;
    
    // to be used for ints
    private LinkedHashMap<Integer, Integer> data = new LinkedHashMap<Integer, Integer>() {
        // restrict data to maximum of 100 entries
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return this.size() > 100;   
        }
    };
    
    // to be used for doubles
    private LinkedHashMap<Integer, Double> dataDouble = new LinkedHashMap<Integer, Double>() {
        // restrict data to maximum of 100 entries
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Double> eldest) {
            return this.size() > 100;   
        }
    };
    
    public Graph(String title) {        
        
        setLayout(new BorderLayout());
        JLabel theTitle = new JLabel(title, JLabel.CENTER);
        add(theTitle, BorderLayout.NORTH);
        
        plot = new Plot();
        add(plot, BorderLayout.CENTER);
    }
    
    protected void paintComponent(Graphics g) {
        
        // draw the axis and values
        g.setColor(Color.BLACK);
        
        // y-axis
        drawString(g, Double.toString(max), 40, 12, 2, 2);
        drawString(g, Double.toString(min), 40, getHeight()-15, 2, 0);        
        g.drawLine(44, 21, 44, getHeight()-15);
        
        // x-axis 
        drawString(g, Integer.toString(first), 50, getHeight(), 0, 0);
        drawString(g, Integer.toString(last), 235, getHeight(), 0, 0);
        g.drawLine(44, getHeight()-15, 242, getHeight()-15);
               
    }
    
    protected void updateGraph(int generation, double dataPoint, Color color, Boolean fill, String type) {
        // update the axis with correct values
        if (type == "double") {
            dataDouble.put(generation, dataPoint);
            Collection<Double> c = dataDouble.values();
            max = Collections.max(c);
            min = Collections.min(c);
            Set<Entry<Integer, Double>> entrySet = dataDouble.entrySet();
            int size = entrySet.size();
            Entry[] test = entrySet.toArray(new Entry[size]);        
            int temp = (Integer) test[size-1].getKey();
            
            first = entrySet.iterator().next().getKey(); // first key of the LinkedHashMap
            last = (temp > 100) ? temp : 100; // last key of the LinkedHashMap 
        } else {
            data.put(generation, (int)dataPoint);
            Collection<Integer> c = data.values();
            max = Collections.max(c);
            min = Collections.min(c);
            Set<Entry<Integer, Integer>> entrySet = data.entrySet();
            int size = entrySet.size();
            Entry[] test = entrySet.toArray(new Entry[size]);        
            int temp = (Integer) test[size-1].getKey();
            
            first = entrySet.iterator().next().getKey(); // first key of the LinkedHashMap
            last = (temp > 100) ? temp : 100; // last key of the LinkedHashMap 
        }       
        
        // update the data on the graph
        plot.updateData(data, dataDouble, max, min, color, fill);       
        
    }
    
    protected void drawString(Graphics g, String text, int x, int y, int halign, int valign) {
        FontMetrics m = g.getFontMetrics();
        Rectangle2D r = m.getStringBounds(text, g);
        x -= r.getWidth() * halign / 2;
        y += r.getHeight() * valign / 2;
        g.drawString(text,x, y);
    }
    
    protected void resetData() {
        data.clear();
        dataDouble.clear();
    }
    
}
