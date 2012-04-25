package uk.ac.cam.jdb75.tick6star;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.JPanel;

public class Plot extends JPanel {
   
    private LinkedHashMap<Integer, Integer> data;
    private LinkedHashMap<Integer, Double> dataDouble;
    private double max;
    private double min;
    private Color color;
    private Boolean fill;
    
    private int[] xPoints = new int[102];
    private int[] yPoints = new int[102];   
        
    public Plot() {
        
    }

    protected void paintComponent(Graphics g) {       
        
        int y0 = getHeight() - 11;
        int x0 = 20;
        int y1 = 0;
        int i = 0;
        int j = 1;
        
        Arrays.fill(yPoints, y0);
        Arrays.fill(xPoints, x0);
        
        int height = y0 - y1;       
        
        // graph background
        g.setColor(Color.WHITE);
        g.fillRect(20, 0, 198, getHeight()-10);
        
        g.setColor(color);        
        
        if (data == null || data.isEmpty()) { 
            if (dataDouble == null || dataDouble.isEmpty()) {
                return;
            }
            else {
                Collection<Double> c = dataDouble.values();
                Iterator<Double> itr = c.iterator();
                while(itr.hasNext()) {
                    int value = normalize(itr.next(), height);
                    xPoints[i] = x0 + j;
                    yPoints[i] = y0 - value;
                    i++;
                    

                    j+= 2;
                }
            }
        } else {
            Collection<Integer> c = data.values();
            Iterator<Integer> itr = c.iterator();
            while(itr.hasNext()) {
                int value = normalize(itr.next(), height);
                xPoints[i] = x0 + j;
                yPoints[i] = y0 - value;
                i++;
                j+= 2;
            }
        }        
        
        if (i != 0) xPoints[i] = xPoints[i-1];
        yPoints[0] = y0;
        xPoints[0] = x0;
        if (fill != true) g.drawPolyline(xPoints, yPoints, i);
            else g.fillPolygon(xPoints, yPoints, i+1);        
        
    }
    
    private int normalize(double value, int height) {
        double normalizedValue = 1;
        double test = max - min;
        if ( test != 0) {
            normalizedValue = (value - min) / test;
        }         
        int newValue = (int) Math.round(normalizedValue * (double)height);        
        return newValue;        
    }
    
    protected void drawString(Graphics g, String text, int x, int y, int halign, int valign) {
        FontMetrics m = g.getFontMetrics();
        Rectangle2D r = m.getStringBounds(text, g);
        x -= r.getWidth() * halign / 2;
        y += r.getHeight() * valign / 2;
        g.drawString(text,x, y);
    }

    public void updateData(LinkedHashMap<Integer, Integer> dataInt, LinkedHashMap<Integer, Double> dataDouble, double max, double min, Color color, Boolean fill) {
        data = dataInt;
        this.dataDouble = dataDouble;
        this.max = max;
        this.min = min;
        this.color = color;
        this.fill = fill;
    }
}
