package uk.ac.cam.jdb75.tick6;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import uk.ac.cam.acr31.life.World;

public class TextLife {

    public static void main(String[] args) {
        CommandLineOptions c = null;
        try {
            c = new CommandLineOptions(args);
        } catch (CommandLineException e4) {
            System.out.println(e4.getMessage());
            return;
        }
        List<Pattern> list = null;
        if (c.getSource().startsWith("http://"))
            try {
                list = PatternLoader.loadFromURL(c.getSource());
            } catch (IOException e3) {
                System.out.println("Error: The file could not be read");
                return;
            }
        else
            try {
                list = PatternLoader.loadFromDisk(c.getSource());
            } catch (IOException e2) {
                System.out.println("Error: The file could not be read");
                return;
            }
        if (c.getIndex() == null) {
            int i = 0;
            for (Pattern p : list)
                System.out.println((i++)+" "+p.getName()+" "+p.getAuthor());
        } else {
            Pattern p = null;
            try {
                p = list.get(c.getIndex());
            } catch (IndexOutOfBoundsException e2) {
                System.out.println("Error: Pattern number not found in file");
                return;
            }
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
                System.out.println(e1.getMessage());
                return;
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
