package uk.ac.cam.jdb75.tick6;

import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

public class GuiLife extends JFrame {

    public GuiLife() {
        super("GuiLife");
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JComponent optionsPanel = createOptionsPanel();
        add(optionsPanel, BorderLayout.WEST);
        JComponent gamePanel = createGamePanel();
        add(gamePanel, BorderLayout.CENTER);
    }

    private JComponent createOptionsPanel() {
        Box result = Box.createVerticalBox();
        result.add(createSourcePanel());
        result.add(createPatternPanel());
        result.add(createControlPanel());
        return result;
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch,title);
        component.setBorder(tb);
    }

    private JComponent createGamePanel() {
        JPanel holder = new JPanel();
        addBorder(holder,Strings.PANEL_GAMEVIEW);
        JPanel result = new JPanel();
        holder.add(result);
        return new JScrollPane(holder);
    }

    private JComponent createSourcePanel() {
        JPanel result = new SourcePanel();
        addBorder(result,Strings.PANEL_SOURCE);
        return result; 
    }

    private JComponent createPatternPanel() { 
        JPanel result = new JPanel();
        addBorder(result, Strings.PANEL_PATTERN);
        return result;
    }
    private JComponent createControlPanel() { 
        JPanel result = new JPanel();
        addBorder(result, Strings.PANEL_CONTROL);
        return result;
    }

    public static void main(String[] args) {
        GuiLife gui = new GuiLife();
        gui.setVisible(true);
    }  
}
