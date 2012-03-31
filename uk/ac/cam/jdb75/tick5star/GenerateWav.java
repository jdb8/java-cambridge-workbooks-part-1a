package uk.ac.cam.jdb75.tick5star;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.sound.AudioSequence;
import uk.ac.cam.acr31.sound.SineWaveSound;
import uk.ac.cam.acr31.sound.SoundOverflowException;

public class GenerateWav {
    
    private World initialWorld;
    private World currentWorld;
    private int generations;
    private double timeSlotSeconds = 0.2;
    private AudioSequence as;
    private String filename;
    
    public GenerateWav(World w, int generations, String filename) {
        initialWorld = w;
        currentWorld = w;        
        this.generations = generations;
        this.filename = filename;
        
        as = new AudioSequence(timeSlotSeconds);
    }
    
    private void makeSlot() {
        double pitch = currentWorld.getPopulation()/(currentWorld.getHeight()*currentWorld.getWidth());
        System.out.println(pitch + " = " + currentWorld.getPopulation() + " / " + currentWorld.getHeight() + " * " + currentWorld.getWidth());
        
        as.addSound(new SineWaveSound(pitch*6, 0.5));
    }
    
    private void addSlots() {
        for (int i = 0; i < generations; i++) {
            makeSlot();
            as.advance();
            currentWorld = currentWorld.nextGeneration(0);
        }
        
    }
    
    public void create() throws FileNotFoundException, IOException, SoundOverflowException {
        addSlots();
        as.write(new FileOutputStream(filename));
    }
}