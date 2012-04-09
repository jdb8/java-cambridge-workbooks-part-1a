package uk.ac.cam.jdb75.tick6star;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class PatternPanel extends JPanel {

    private JList guiList;
    private Pattern currentPattern;
    private List<Pattern> patternList;    
    
    public Pattern getCurrentPattern() {
        return currentPattern;
    }
 
    public PatternPanel() {
        super();
        currentPattern = null;
        setLayout(new BorderLayout());
        guiList = new JList();
        add(new JScrollPane(guiList));
        guiList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && (patternList != null)) {
                    int sel = guiList.getSelectedIndex();
                    if (sel != -1) {
                        currentPattern = patternList.get(sel);
                        onPatternChange();
                    }
                }
            }
        });
    }

    public void setPatterns(List<Pattern> list) {
        patternList = list;
        if (patternList == null) {
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
        
        for (Pattern p : patternList) {
            names.add(p.getName() + " (" + p.getAuthor() + ")");
        }
  
        guiList.setListData(names.toArray());
        currentPattern = patternList.get(0); //select first element in list
        guiList.setSelectedIndex(0);  //select first element in guiList
    } 
    
    public abstract void onPatternChange();
}