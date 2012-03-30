package uk.ac.cam.jdb75.tick5star;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.WorldViewer;
import java.util.List;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;

public class SoundLife {
    
    public static void main(String[] args) {
        
        try {
            
            if (args.length != 3) {
                System.out.println("Error: Must supply 3 arguments - pattern, number of generations, filename.wav");
                return;
            }
            
            Pattern p = new Pattern(args[0]);
            int generations = Integer.parseInt(args[1]);
            String filename = args[2];
            
            
        } catch (PatternFormatException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException b) { // second argument cannot be parsed as int
            System.out.println("Error: you must use an integer argument to choose a pattern");
        } 
        
    }
    
}