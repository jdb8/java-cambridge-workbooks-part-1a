package uk.ac.cam.jdb75.tick6;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import uk.ac.cam.acr31.life.World;

public class GuiLife extends JFrame {
    
    private PatternPanel patternPanel;
    private ControlPanel controlPanel;
    private GamePanel gamePanel;

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
        gamePanel = new GamePanel();
        holder.add(gamePanel);
        return new JScrollPane(holder);
    }

    private JComponent createSourcePanel() {
        JPanel result = new SourcePanel();
        addBorder(result,Strings.PANEL_SOURCE);
        return result; 
    }

    private JComponent createPatternPanel() { 
        patternPanel = new PatternPanel();
        addBorder(patternPanel, Strings.PANEL_PATTERN);
        return patternPanel;
    }
    private JComponent createControlPanel() { 
        controlPanel = new ControlPanel();
        addBorder(controlPanel, Strings.PANEL_CONTROL);
        return controlPanel;
    }

    public static void main(String[] args) {
        GuiLife gui = new GuiLife();
        CommandLineOptions options = null;
        try {
            options = new CommandLineOptions(args);
        } catch (CommandLineException e1) {
            System.out.println(e1.getMessage());
            return;
        }
        try {
            String source = options.getSource();
            List<Pattern> list = null;
            if (source.startsWith("http://"))
                try {
                    list = PatternLoader.loadFromURL(source);
                } catch (IOException e3) {
                    System.out.println("Error: The file could not be read");
                }
            else
                try {
                    list = PatternLoader.loadFromDisk(source);
                } catch (IOException e2) {
                    System.out.println("Error: The file could not be read");
                }
            gui.patternPanel.setPatterns(list);
            if (null != options.getIndex()) {
                World w = gui.controlPanel.initialiseWorld(list.get(options.getIndex()));
                gui.gamePanel.display(w);
            }            
            
        } catch (PatternFormatException e) {
            System.out.println(e.getMessage());
        }
        gui.setVisible(true);
    }  
}
