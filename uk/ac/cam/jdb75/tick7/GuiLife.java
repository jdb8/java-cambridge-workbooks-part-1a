package uk.ac.cam.jdb75.tick7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
        controlPanel = new ControlPanel(){
            protected void onSpeedChange(int value) {
                playTimer.setDelay(1+(100-value)*10);
            }
            protected void onStepChange(int value) {
                timeStep = value;
            }
            protected void onZoomChange(int value) {
                System.out.println(value);
                gamePanel.setZoom(value);
            }
        };
        addBorder(controlPanel,Strings.PANEL_CONTROL);
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
            try {
                gui.world = gui.controlPanel.initialiseWorld(list.get(options.getIndex()));
            } catch (PatternFormatException e) {
                e.getMessage();
            }
            gui.gamePanel.display(gui.world);
        }
        gui.playTimer.start();
        gui.setVisible(true);
    }
    
    
}
