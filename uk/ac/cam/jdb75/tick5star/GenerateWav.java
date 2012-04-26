package uk.ac.cam.jdb75.tick5star;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import uk.ac.cam.acr31.sound.AudioSequence;
import uk.ac.cam.acr31.sound.SineWaveSound;
import uk.ac.cam.acr31.sound.SoundOverflowException;

public class GenerateWav {
    
    private ArrayWorld initialWorld;
    private ArrayWorld currentWorld;
    private int generations;
    private double timeSlotSeconds = 0.5;
    private AudioSequence as;
    private String filename;
    
    public GenerateWav(ArrayWorld w, int generations, String filename) {
        initialWorld = w;
        currentWorld = w;        
        this.generations = generations;
        this.filename = filename;
        
        as = new AudioSequence(timeSlotSeconds);
    }
    
    private void makeSlot() {
        double pitch = (double)currentWorld.getPopulation()/(double)(currentWorld.getHeight()*(double)currentWorld.getWidth());
        System.out.println(pitch + " = " + currentWorld.getPopulation() + " / " + currentWorld.getHeight() + " * " + currentWorld.getWidth());
        
        as.addSound(new SineWaveSound(pitch*3, 0.5));      
    }
    
    private void addSlots() {
        for (int i = 0; i < generations; i++) {
            makeSlot();
            as.advance();
            currentWorld = currentWorld.nextGeneration();
        }
        
    }
    
    public void create() throws FileNotFoundException, IOException, SoundOverflowException {
        addSlots();
        as.write(new FileOutputStream(filename));
    }
}
