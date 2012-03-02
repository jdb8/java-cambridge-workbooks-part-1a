package uk.ac.cam.jdb75.tick7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import uk.ac.cam.acr31.life.World;

public class GuiLife extends JFrame {
    
    private PatternPanel patternPanel;
    private ControlPanel controlPanel;
    private GamePanel gamePanel;
    private World world;
    private int timeDelay = 500; //delay between updates (millisecs)
    private int timeStep = 0;    //progress by (2 ^ timeStep) each time

    private Timer playTimer = new Timer(timeDelay, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            doTimeStep();
        }
    });

    void doTimeStep() {
        if (world != null) {
            world = world.nextGeneration(timeStep);
            gamePanel.display(world);
        }
    }
    
    private void resetWorld() {
        Pattern current = patternPanel.getCurrentPattern();
        world = null;
        if (current != null) {
            try {
                world = controlPanel.initialiseWorld(current);
            } catch (PatternFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Error initialising world",
                        "An error occurred when initialising the world. "+e.getMessage(),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        gamePanel.display(world);
        repaint();
    }

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
        JPanel result = new SourcePanel(){
            protected boolean setSourceFile() {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    try {
                        List<Pattern> list = PatternLoader.load(new FileReader(f));
                        patternPanel.setPatterns(list);
                        resetWorld();
                        return true;
                    } catch (IOException ioe) {}
                }
                return false;
            }
            protected boolean setSourceNone() {
                world = null;
                patternPanel.setPatterns(null);
                resetWorld();
                return true;
            }
            protected boolean setSourceLibrary() {
                String u = "http://www.cl.cam.ac.uk/teaching/current/ProgJava/nextlife.txt";
                return setSourceWeb(u);
            }
            private boolean setSourceWeb(String url) {
                try {
                    List<Pattern> list = PatternLoader.loadFromURL(url);
                    patternPanel.setPatterns(list);
                    resetWorld();
                    return true;
                } catch (IOException ioe) {}
                return false;
            }
        };
        addBorder(result,Strings.PANEL_SOURCE);
        return result; 
    }

    private JComponent createPatternPanel() { 
        patternPanel = new PatternPanel(){
            public void onPatternChange(){
                resetWorld();
            }
        };
        addBorder(patternPanel, Strings.PANEL_PATTERN);
        return patternPanel;
    }
    private JComponent createControlPanel() { 
        controlPanel = new ControlPanel(){
            protected void onSpeedChange(int value) {
                playTimer.setDelay(1+(100-value)*10);
            }
            protected void onStepChange(int value) {
                timeStep = value;
            }
            protected void onZoomChange(int value) {
                gamePanel.setZoom(value);
            }
        };
        addBorder(controlPanel,Strings.PANEL_CONTROL);
        return controlPanel;
    }

    public static void main(String[] args) {
        GuiLife gui = new GuiLife();
        gui.playTimer.start();
        gui.resetWorld();
        gui.setVisible(true);
    }   
}
