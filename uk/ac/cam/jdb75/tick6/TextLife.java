package uk.ac.cam.jdb75.tick6;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import uk.ac.cam.acr31.life.World;

//TODO: Write a suitable package statement and import statements
public class TextLife {

    public static void main(String[] args) {
        CommandLineOptions c = null;
        try {
            c = new CommandLineOptions(args);
        } catch (CommandLineException e4) {
            e4.getMessage();
        }
        List<Pattern> list = null;
        if (c.getSource().startsWith("http://"))
            try {
                list = PatternLoader.loadFromURL(c.getSource());
            } catch (IOException e3) {
                System.out.println("Error: The file could not be read");
            }
        else
            try {
                list = PatternLoader.loadFromDisk(c.getSource());
            } catch (IOException e2) {
                System.out.println("Error: The file could not be read");
            }
        if (c.getIndex() == null) {
            int i = 0;
            for (Pattern p : list)
                System.out.println((i++)+" "+p.getName()+" "+p.getAuthor());
        } else {
            Pattern p = list.get(c.getIndex());
            World w = null;
            if (c.getWorldType().equals(CommandLineOptions.WORLD_TYPE_AGING)) {
                w = new AgingWorld(p.getWidth(), p.getHeight());
            } else if (c.getWorldType().equals(CommandLineOptions.WORLD_TYPE_ARRAY)) {
                w = new ArrayWorld(p.getWidth(), p.getHeight());
            } else {
                w = new PackedWorld();
            }
            try {
                p.initialise(w);
            } catch (PatternFormatException e1) {
                e1.getMessage();
            }
            int userResponse = 0;
            while (userResponse != 'q') {
                w.print(new OutputStreamWriter(System.out));
                try {
                    userResponse = System.in.read();
                } catch (IOException e) {}
                w = w.nextGeneration(0);
            }
        }
    }
}
