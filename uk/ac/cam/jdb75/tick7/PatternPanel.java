package uk.ac.cam.jdb75.tick7;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PatternPanel extends JPanel {

    private JList guiList;
    private Pattern currentPattern;
    
    public Pattern getCurrentPattern() {
        return currentPattern;
    }
 
    public PatternPanel() {
        super();
        currentPattern = null;
        setLayout(new BorderLayout());
        guiList = new JList();
        add(new JScrollPane(guiList));
    }

    public void setPatterns(List<Pattern> list) {
        if (list == null) {
            currentPattern = null; //if list is null, then no valid pattern
            guiList.setListData(new String[]{}); //no list item to select
            return;
        }
        ArrayList<String> names = new ArrayList<String>();

        //      Using a for loop which iterates over the items
        //      in "list" and adds the pattern name and pattern
        //      author to "names". For example, if the pattern
        //      name and author is "Glider" and "Richard Guy 1970"
        //      then you should add the string:
        //
        //                "Glider (Richard Guy 1970)"
        //
        //      to "names" using the method "add" on "names".
        
        for (Pattern p : list) {
            names.add(p.getName() + " (" + p.getAuthor() + ")");
        }
  
        guiList.setListData(names.toArray());
        currentPattern = list.get(0); //select first element in list
        guiList.setSelectedIndex(0);  //select first element in guiList
    } 
}