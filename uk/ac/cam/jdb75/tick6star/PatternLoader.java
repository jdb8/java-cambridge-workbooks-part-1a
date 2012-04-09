package uk.ac.cam.jdb75.tick6star;

import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.net.URL;
import java.net.URLConnection;

public class PatternLoader {

    public static List<Pattern> load(Reader r) throws IOException {
        String line; 
        List<Pattern> resultList = new LinkedList<Pattern>();
        BufferedReader buff = new BufferedReader(r);

        while ((line = buff.readLine()) != null) {            
            try {
                Pattern p = new Pattern(line);
                resultList.add(p);
            } catch (PatternFormatException e) {
                // do nothing
            }         
        }

        return resultList;        
    }

    public static List<Pattern> loadFromURL(String url) throws IOException {
        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        return load(new InputStreamReader(conn.getInputStream()));
    }

    public static List<Pattern> loadFromDisk(String filename) throws IOException {
        return load(new FileReader(filename));
    }
}   
