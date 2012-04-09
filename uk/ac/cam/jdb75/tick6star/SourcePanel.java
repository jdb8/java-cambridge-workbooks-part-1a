package uk.ac.cam.jdb75.tick6star;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public abstract class SourcePanel extends JPanel {
    
    private JRadioButton current;

    public SourcePanel() {
        super();
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        final JRadioButton none = new JRadioButton(Strings.BUTTON_SOURCE_NONE, true);
        none.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (setSourceNone())
                    current = none; 
                else
                    //unsuccessful: re-enable previous source choice
                    current.setSelected(true); 
                }
        });
        final JRadioButton file = new JRadioButton(Strings.BUTTON_SOURCE_FILE, true);
        file.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (setSourceFile())
                    //successful: file found and patterns loaded
                    current = file; 
                else
                    //unsuccessful: re-enable previous source choice
                    current.setSelected(true); 
                }
        });
        final JRadioButton library = new JRadioButton(Strings.BUTTON_SOURCE_LIBRARY, true);
        library.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (setSourceLibrary())
                    current = library; 
                else
                    //unsuccessful: re-enable previous source choice
                    current.setSelected(true); 
                }
        });
        JRadioButton fourStar = new JRadioButton(Strings.BUTTON_SOURCE_FOURSTAR, true);  
        //add RadioButtons to this JPanel
        add(none);
        add(file);
        add(library);
        add(fourStar);
        //create a ButtonGroup containing all four buttons
        //Only one Button in a ButtonGroup can be selected at once
        ButtonGroup group = new ButtonGroup();
        group.add(none);
        group.add(file);
        group.add(library);
        group.add(fourStar);
        current = none;
    }
    
    protected abstract boolean setSourceNone();
    protected abstract boolean setSourceFile();
    protected abstract boolean setSourceLibrary();
}