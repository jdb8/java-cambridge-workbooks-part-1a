package uk.ac.cam.jdb75.tick7star;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class PatternOptionsPanel extends JPanel {
    
    protected Pattern pattern = null;
    
    protected JTextField patternField;
    protected JTextField nameField;
    protected JTextField authorField;
    protected JTextField rowsField;
    
    protected JSpinner widthSpinner;
    protected JSpinner heightSpinner;
    protected JSpinner startXSpinner;
    protected JSpinner startYSpinner;
    
    public PatternOptionsPanel() throws PatternFormatException {
        super();
        pattern = new Pattern("::8:8:0:0:0");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        patternField = createNewTextField("Pattern String", "");
        patternField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onPatternChange(patternField.getText());                                  
            }
        });
        
        nameField = createNewTextField("Name", "");
        nameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onNameChange(nameField.getText());                                  
            }
        });
        
        authorField = createNewTextField("Author", "");
        authorField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAuthorChange(authorField.getText());                                  
            }
        });
        
        widthSpinner = createNewSpinner("Width", 8, 8, 1024);
        widthSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                onWidthChange((Integer)widthSpinner.getValue());
              }
        });
        
        heightSpinner = createNewSpinner("Height", 8, 8, 1024);
        heightSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                onHeightChange((Integer)heightSpinner.getValue());
              }
        });
        
        startXSpinner = createNewSpinner("StartX", 0, 0, 1024);
        startXSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                onStartXChange((Integer)startXSpinner.getValue());
              }
        });
        
        startYSpinner = createNewSpinner("StartY", 0, 0, 1024);
        startYSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                onStartYChange((Integer)startYSpinner.getValue());
              }
        });
        
        rowsField = createNewTextField("Rows", "");
        rowsField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRowsChange(rowsField.getText());                                  
            }
        });
        
    }
    
    // implemented in GuiLife
    protected abstract void onPatternChange(String text);
    protected abstract void onNameChange(String text);
    protected abstract void onAuthorChange(String text);
    protected abstract void onRowsChange(String text);
    protected abstract void onWidthChange(int val);
    protected abstract void onHeightChange(int val);
    protected abstract void onStartXChange(int val);
    protected abstract void onStartYChange(int val);

    private JTextField createNewTextField(String title, String content) {
        add(new JLabel(title));
        JTextField textField = new JTextField(content, 20);
        add(textField);
        
        return textField;
    }
    
    private JSpinner createNewSpinner(String title, int initial, int min, int max) {
        add(new JLabel(title));
        JSpinner spin = new JSpinner(new SpinnerNumberModel(initial, min, max, 1));
        add(spin);
        
        return spin;
    }
    
}
