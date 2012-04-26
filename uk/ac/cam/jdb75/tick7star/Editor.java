package uk.ac.cam.jdb75.tick7star;

import java.awt.BorderLayout;
import java.util.Arrays;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import uk.ac.cam.acr31.life.World;

public class Editor extends JFrame {
    
    private PatternOptionsPanel patternOptionsPanel;
    private GamePanel gamePanel;
    private FilmStripPanel filmStripPanel;
    private World world;    

    public Editor() throws PatternFormatException {
        super("GuiLife");
        setSize(640, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JComponent patternOptionsPanel = createPatternOptionsPanel();
        add(patternOptionsPanel, BorderLayout.WEST);
        JComponent gamePanel = createGamePanel();
        add(gamePanel, BorderLayout.CENTER);
        JComponent filmStripPanel = createFilmStripPanel();
        add(filmStripPanel, BorderLayout.SOUTH);
    }

    private JComponent createPatternOptionsPanel() throws PatternFormatException {
        JPanel holder = new JPanel();
        addBorder(holder, "Pattern");
        patternOptionsPanel = new PatternOptionsPanel() {           
            
            @Override
            protected void onPatternChange(String newPattern) {                
                try {                    
                    patternOptionsPanel.pattern = new Pattern(newPattern);
                    Pattern p = patternOptionsPanel.pattern;
                    world = new ArrayWorld(p.getWidth(), p.getHeight());
                    p.initialise(world);
                    patternOptionsPanel.nameField.setText(p.getName());
                    patternOptionsPanel.authorField.setText(p.getAuthor());
                    patternOptionsPanel.widthSpinner.setValue(p.getWidth());
                    patternOptionsPanel.heightSpinner.setValue(p.getHeight());
                    patternOptionsPanel.startXSpinner.setValue(p.getStartCol());
                    patternOptionsPanel.startYSpinner.setValue(p.getStartRow());
                    patternOptionsPanel.rowsField.setText(p.getCells());
                    gamePanel.display(world);
                    filmStripPanel.update(world);
                } catch (PatternFormatException e) {
                    System.out.println("Not a valid pattern");
                }                
            }

            @Override
            protected void onNameChange(String text) {
                Pattern p = patternOptionsPanel.pattern;
                p.setName(text);
                patternOptionsPanel.patternField.setText(p.toString()); 
            }

            @Override
            protected void onAuthorChange(String text) {
                Pattern p = patternOptionsPanel.pattern;
                p.setAuthor(text);
                patternOptionsPanel.patternField.setText(p.toString());                
            }

            @Override
            protected void onRowsChange(String text) {
                Pattern p = patternOptionsPanel.pattern;
                p.setCells(text);
                patternOptionsPanel.patternField.setText(p.toString());
                onPatternChange(p.toString());               
            }

            @Override
            protected void onWidthChange(int val) {
                Pattern p = patternOptionsPanel.pattern;
                p.setWidth(val);
                patternOptionsPanel.patternField.setText(p.toString());
                onPatternChange(p.toString());                
            }
            
            @Override
            protected void onHeightChange(int val) {
                Pattern p = patternOptionsPanel.pattern;
                p.setHeight(val);
                patternOptionsPanel.patternField.setText(p.toString());
                onPatternChange(p.toString());                
            }
            
            @Override
            protected void onStartXChange(int val) {
                Pattern p = patternOptionsPanel.pattern;
                p.setStartCol(val);
                patternOptionsPanel.patternField.setText(p.toString());
                onPatternChange(p.toString());                
            }
            
            @Override
            protected void onStartYChange(int val) {
                Pattern p = patternOptionsPanel.pattern;
                p.setStartRow(val);
                patternOptionsPanel.patternField.setText(p.toString());
                onPatternChange(p.toString());                
            }
        };
        holder.add(patternOptionsPanel);
        return holder;
    }
    
    private JComponent createFilmStripPanel() {
        JPanel holder = new JPanel();
        addBorder(holder, "Future Generations");
        filmStripPanel = new FilmStripPanel();
        holder.add(filmStripPanel);
        JScrollPane sp = new JScrollPane(holder);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        return sp;
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch,title);
        component.setBorder(tb);
    }

    private JComponent createGamePanel() {
        JPanel holder = new JPanel();
        addBorder(holder, "Game Board Editor");
        gamePanel = new GamePanel() {

            @Override
            public void toggleCell(World current, int x, int y) {
                Pattern p = patternOptionsPanel.pattern;
                // ignore clicks on areas out of bounds
                if (y-p.getStartRow() < 0 || x-p.getStartCol() < 0) { return; };
                String cells = p.getCells();
                String[] cellsArray = cells.split(" ");                
                boolean live = current.getCell(x, y);
                current.setCell(x, y, !live);
                
                
                if (y-p.getStartRow() > cellsArray.length-1) {
                    String[] newArray = new String[y-p.getStartRow()+1];
                    Arrays.fill(newArray, "0");
                    
                    for (int i = 0; i < cellsArray.length; i++) {
                        newArray[i] = cellsArray[i];
                    }
                    
                    cellsArray = newArray;
                }
                StringBuilder row = new StringBuilder(cellsArray[y-p.getStartRow()]);
                char set = (!live) ? '1' : '0';
                while (x-p.getStartCol() > row.length()-1) {
                    row.append('0');
                }
                row.setCharAt(x-p.getStartCol(), set);
                cellsArray[y-p.getStartRow()] = row.toString();
                StringBuilder result = new StringBuilder("");
                for (String s : cellsArray) {
                    result.append(s + " ");
                }
                String resultString = result.toString().trim();

                patternOptionsPanel.onRowsChange(resultString);                
                repaint();
                
            }
            
        };
        holder.add(gamePanel);
        return new JScrollPane(holder);
    }   

    public static void main(String[] args) throws PatternFormatException {
        Editor gui = new Editor();
        Pattern p = new Pattern("::8:8:0:0:0");
        gui.world = new ArrayWorld(p.getWidth(), p.getHeight());
        p.initialise(gui.world);
        gui.gamePanel.display(gui.world);
        
        gui.setVisible(true);
    }   
}
