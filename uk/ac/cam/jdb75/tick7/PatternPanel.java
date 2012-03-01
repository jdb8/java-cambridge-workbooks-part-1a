package uk.ac.cam.jdb75.tick7;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PatternPanel extends JPanel {

    private JList guiList;
 
    public PatternPanel() {
        super();
        setLayout(new BorderLayout());
        guiList = new JList();
        add(new JScrollPane(guiList));
    }

    public void setPatterns(List<Pattern> list) {
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
    } 
}