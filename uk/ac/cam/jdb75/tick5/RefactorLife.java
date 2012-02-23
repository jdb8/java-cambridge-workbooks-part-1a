package uk.ac.cam.jdb75.tick5;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.WorldViewer;
import java.util.List;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;

public class RefactorLife {
    public static void main(String[] args) {
        try {
            // valid arguments in the form --array http://... 42
            //   or --long http://... 42
            //   or http://... 42 
            //   or http://...
            int argsLength = args.length;
            if (argsLength == 0) { System.out.println("Error: must supply at least one argument"); return; }
            
            String worldType = argsLength == 3 ? args[0] : "--array"; 
            String filename = argsLength == 3 ? args[1] : args[0];
            List<Pattern> ps = filename.startsWith("http://") ? PatternLoader.loadFromURL(filename) : PatternLoader.loadFromDisk(filename);
            if (ps.isEmpty()) {
                System.out.println("Error: no valid patterns found in file");
                return;
            }           
            
            if (args.length == 1) {
                int i = 0;
                for (Pattern p: ps) {
                    // use the public methods of Pattern to retrieve the data to print (as well as converting ints)
                    System.out.println(Integer.toString(i) + ") " + p.getName() + ":" + p.getAuthor() + ":" + Integer.toString(p.getWidth()) + ":" + Integer.toString(p.getHeight()) + ":" + Integer.toString(p.getStartCol()) + ":" + Integer.toString(p.getStartRow()) + ":" + p.getCells());
                    i++;
                }
            } else {
                int chosen = argsLength == 3 ? Integer.parseInt(args[2]) : Integer.parseInt(args[1]);
                Pattern p = ps.get(chosen);
                World world = null;
                if (worldType.equals("--array")) {
                    world = new ArrayWorld(p.getWidth(), p.getHeight());
                } else if (worldType.equals("--long")) {
                    world = new PackedWorld(); 
                } else if (worldType.equals("--aging")) {
                    world = new AgingWorld(p.getWidth(), p.getHeight()); 
                } else {
                    System.out.println("Error: unknown world type supplied. First argument must be either --array, --long or --aging");
                    return; 
                }
                p.initialise(world);
                play(world);
                
            }    
                    
        } catch (PatternFormatException e) {
                System.out.println(e.getMessage());
        } catch (NumberFormatException b) { // second argument cannot be parsed as int
                System.out.println("Error: you must use an integer argument to choose a pattern");
        } catch (IndexOutOfBoundsException i) { // pattern list doesn't have the chosen pattern
                System.out.println("Error: pattern number supplied did not match any patterns loaded");
        } catch (IOException io) {
                System.out.println("Error: a problem was encountered while reading the file");
        }
        
    }

    public static void play(World world) throws IOException {
        int userResponse = 0;
        WorldViewer viewer = new WorldViewer();
        while (userResponse != 'q') { // this loop doesn't work properly in Windows, prints patterns twice
            Writer w = new OutputStreamWriter(System.out);            
            world.print(w);
            viewer.show(world);
            userResponse = System.in.read();
            world = world.nextGeneration(0);
        }
        viewer.close();
    }
}