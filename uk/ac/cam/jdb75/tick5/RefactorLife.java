package uk.ac.cam.jdb75.tick5;

import uk.ac.cam.acr31.life.World;
import java.util.List;
import java.io.IOException;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class RefactorLife {
    public static void main(String[] args) {
        try {
            // valid arguments in the form --array http://... 42
            //   or --long http://... 42
            //   or http://... 42 
            //   or http://...
            int argsLength = args.length;
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
                    world = new TestArrayWorld(p.getWidth(), p.getHeight());
                } else if (worldType.equals("--long")) {
                    world = new TestPackedWorld(); 
                } else {
                    System.out.println("Error: unknown world type supplied. First argument must be either --array or --long");
                    return; 
                }
                p.initialise(world);
                play(world);
            }    
                    
        } catch (PatternFormatException e) {
                System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException b) { // initial arguments insufficient
                System.out.println("Error: must supply at least one argument");
        } catch (NumberFormatException b) { // second argument cannot be parsed as int
                System.out.println("Error: second argument must be an integer");
        } catch (IndexOutOfBoundsException i) { // pattern list doesn't have the chosen pattern
                System.out.println("Error: pattern number supplied did not match any patterns loaded");
        } catch (IOException io) {
                System.out.println("Error: a problem was encountered while reading the file");
        }    
        
    }

    public static void play(World world) throws IOException {
        int userResponse = 0;
        while (userResponse != 'q') { // this loop doesn't work properly in Windows, prints patterns twice
            Writer w = new OutputStreamWriter(System.out);            
            world.print(w);
            userResponse = System.in.read();
            world = world.nextGeneration(0);
        }
    }
}