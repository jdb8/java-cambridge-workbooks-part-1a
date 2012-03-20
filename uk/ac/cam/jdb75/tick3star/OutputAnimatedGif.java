package uk.ac.cam.jdb75.tick3star;

// Tell the compiler where to find the additional classes used in this file
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

public class OutputAnimatedGif {

    private FileImageOutputStream output;
    private ImageWriter writer;
    private float hue = 0; // current hue of colour
    private int[][] prevIntWorld;

    public OutputAnimatedGif(String file) throws IOException {
        this.output = new FileImageOutputStream(new File(file)); 
        this.writer = ImageIO.getImageWritersByMIMEType("image/gif").next();
        this.writer.setOutput(output);
        this.writer.prepareWriteSequence(null);
    }

    private BufferedImage makeFrame(boolean[][] world) {
        int w = world[0].length; // assuming a rectangular or square board - no weird shapes!
        int h = world.length;
        int cellSize = 5; // size of each cell square
        float s = 0.8f; // colour saturation 
        float b = 0.3f; // colour brightness
        int cellMemory = 100;
        
        if (prevIntWorld == null) {
            prevIntWorld = new int[h][w];
        }
        
        int[][] prevIntWorldCopy = prevIntWorld; // copy prevIntWorld so we can update it while accessing old elements
        
        Color c = Color.getHSBColor(hue, s, b);
        hue += 0.01f;

        BufferedImage image = new BufferedImage(w*cellSize, h*cellSize, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics(); //create a new graphics context
        
        //g.setColor(c);
        g.setColor(Color.black);
        g.fillRect(0, 0, w*cellSize, h*cellSize); // set the background with the current HSB value
        
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                float cur = (float)prevIntWorldCopy[row][col];
                if (cur != 0) { 
                    cur -= 1;
                    prevIntWorld[row][col] -= 1;
                    //g.setColor(new Color(255, 255, 255, (int)(70/10 *cur))); // cur ranges from 1-9
                    g.setColor(Color.getHSBColor(cur*0.02f, 0.6f, cur*0.005f));
                    g.fillRect(col*cellSize, row*cellSize, cellSize, cellSize);
                }
                if (AnimatedLife.getCell(world, col, row)) {
                    g.setColor(Color.white);
                    //g.fillRect(col*cellSize+1, row*cellSize+1, cellSize, cellSize); // shadow
                    
                    g.setColor(Color.getHSBColor(0f, 0.6f, 0.6f));
                    g.fillRect(col*cellSize, row*cellSize, cellSize, cellSize); // cell
                    
                    g.setColor(Color.white);
                    //g.drawRect(col*cellSize, row*cellSize, cellSize, cellSize); // border
                    prevIntWorld[row][col] = cellMemory;
                }                
            }
        }

        g.dispose(); // free up resources used by the graphics context
        
        return image;
    }
    
    public void addFrame(boolean[][] world) throws IOException {
        BufferedImage image = makeFrame(world);
        try {
            IIOMetadataNode node = new IIOMetadataNode("javax_imageio_gif_image_1.0");
            IIOMetadataNode extension = new IIOMetadataNode("GraphicControlExtension");
            extension.setAttribute("disposalMethod", "none");
            extension.setAttribute("userInputFlag", "FALSE");
            extension.setAttribute("transparentColorFlag", "FALSE");
            extension.setAttribute("delayTime", "1");
            extension.setAttribute("transparentColorIndex", "255");
            node.appendChild(extension);
            IIOMetadataNode appExtensions = new IIOMetadataNode("ApplicationExtensions");
            IIOMetadataNode appExtension = new IIOMetadataNode("ApplicationExtension");
            appExtension.setAttribute("applicationID", "NETSCAPE");
            appExtension.setAttribute("authenticationCode", "2.0");
            byte[] b = "\u0021\u00ff\u000bNETSCAPE2.0\u0003\u0001\u0000\u0000\u0000".getBytes();
            appExtension.setUserObject(b);
            appExtensions.appendChild(appExtension);
            node.appendChild(appExtensions);

            IIOMetadata metadata;
            metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
            metadata.mergeTree("javax_imageio_gif_image_1.0", node);

            IIOImage t = new IIOImage(image, null, metadata);
            writer.writeToSequence(t, null);
        }
        catch (IIOInvalidTreeException e) {
            throw new IOException(e);
        }
    }

    public void close() throws IOException {
        writer.endWriteSequence();
    }
}