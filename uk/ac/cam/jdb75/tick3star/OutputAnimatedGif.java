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

    public OutputAnimatedGif(String file) throws IOException {
        this.output = new FileImageOutputStream(new File(file)); 
        this.writer = ImageIO.getImageWritersByMIMEType("image/gif").next();
        this.writer.setOutput(output);
        this.writer.prepareWriteSequence(null);
    }

    private BufferedImage makeFrame(boolean[][] world) {
        //TODO: complete this method
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