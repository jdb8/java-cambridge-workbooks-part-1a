package uk.ac.cam.jdb75.tick4;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

public class PatternLoader {

    public static List<Pattern> load(Reader r) throws IOException {
        //TODO: Complete the implementation of this method.
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
}   
