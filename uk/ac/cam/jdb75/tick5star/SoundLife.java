package uk.ac.cam.jdb75.tick5star;

import uk.ac.cam.acr31.sound.SoundOverflowException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SoundLife {
    
    public static void main(String[] args) throws FileNotFoundException, IOException, SoundOverflowException {
        
        try {
            
            if (args.length != 3) {
                System.out.println("Error: Must supply 3 arguments - pattern, number of generations, filename.wav");
                return;
            }
            
            Pattern p = new Pattern(args[0]);
            int generations = Integer.parseInt(args[1]);
            String filename = args[2];
            
            ArrayWorld aWorld = new ArrayWorld(p.getHeight(), p.getWidth());
            p.initialise(aWorld);
            System.out.println(aWorld.getPopulation());
            
            GenerateWav wav = new GenerateWav(aWorld, generations, filename);
            wav.create();
            
            
        } catch (PatternFormatException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException b) { // second argument cannot be parsed as int
            System.out.println("Error: you must use an integer argument to choose a pattern");
        } 
        
    }
    
}