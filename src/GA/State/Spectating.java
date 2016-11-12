package GA.State;

import GA.Gfx.Window;
import GA.World.Entity.MazeRunner;
import GA.World.Entity.SpookyGhost;
import GA.World.Logger.Entity.GenomePair;
import GA.World.Logger.Logger;
import GA.World.Map.Map;
import org.lwjgl.input.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Spectating implements State {

    private final StateManager stateManager;
    private Map map;
    private Logger logger;
    private GenomePair bestGenomeInCurrentGen;
    private int currentGen = 0;
    private MazeRunner runner;
    private SpookyGhost ghost;
    private boolean initialized = false;
    private JPanel panel;
    public static final String SPECTATING_ID = "Spectating";

    private JLabel jLabel10;
    private JLabel jLabel13;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLabel9;
    private javax.swing.JTextField jTextPane1;

    private int changeCount = 0;
    private boolean change = false;

    Spectating(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void init(Logger logger, Map map) {
        this.panel = Window.getSpectatingPanel();
        this.logger = logger;
        this.map = new Map(map);
        this.initialized = true;
        this.bestGenomeInCurrentGen = this.logger.getGenomeByGenerationNumer(this.currentGen);
        Map tmpMap = new Map (this.map.getMap());
        this.runner = new MazeRunner(this.bestGenomeInCurrentGen.getRunnerGenome(), tmpMap, true);
        this.ghost = new SpookyGhost(this.bestGenomeInCurrentGen.getGhostGenome(), tmpMap, true);
        this.map.initRendering();
        initializePanel();
    }

    private void initializePanel() {

        JPanel jPanel1 = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        jTextPane1 = new javax.swing.JTextField();
        JButton jButton1 = new JButton();
        JLabel jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JLabel jLabel8 = new JLabel();
        jLabel9 = new JLabel();
        jLabel10 = new JLabel();
        JLabel jLabel11 = new JLabel();
        JLabel jLabel12 = new JLabel();
        jLabel13 = new JLabel();
        JSlider jSlider1 = new JSlider(10, 60, 50);
        JLabel jLabel14 = new JLabel();

        panel.setPreferredSize(new Dimension(Window.WIDTH, 80));

        jTextPane1.setColumns(1);
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                jTextPane1KeyTyped(evt);
            }
        });

        jScrollPane1.setViewportView(jTextPane1);

        jPanel1.setToolTipText(" You can use Left and Right arrows to change generations." +
                " Or press 'b' key to go to the current best generation.");

        jButton1.setText("Go");
        jButton1.setToolTipText("Click this button to change the current generation number to the one provided in the text field.");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jSlider1.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            if(!slider.getValueIsAdjusting()){
                int value = slider.getValue();
                changeSpeed(value);
            }
        });

        jLabel1.setText("Runner average score:");

        jLabel2.setText("0000");

        jLabel3.setText("Ghost average score:");

        jLabel4.setText("0000");

        jLabel5.setText("Current generations number:");

        jLabel6.setText("0000");

        jLabel7.setText("Ghost best score:");

        jLabel8.setText("Runner best score:");

        jLabel9.setText("0000");

        jLabel10.setText("0000");

        jLabel11.setText("Enter generation number to vizualize it:");

        jLabel12.setText("Latest available generation number:");

        jLabel13.setText("0000");

        jLabel14.setText("Speed:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel3))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel4))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel7))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel10)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel12)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel13))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel9)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel5)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel6)))
                                                .addGap(0, 298, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, 180)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel14)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(12, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jScrollPane1)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton1)
                                                        .addComponent(jLabel14)))
                                        .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel9)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel6))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jLabel12)
                                                        .addComponent(jLabel13)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel4)))
                                .addContainerGap())
        );
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 818, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }

    private void changeSpeed(int value) {
        int speed = 60 - value;
        this.runner.setSpeed(speed);
        this.ghost.setSpeed(speed);
    }

    private void jTextPane1KeyTyped(KeyEvent evt) {
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE))){
            evt.consume();
        }else {
            if(c != KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_DELETE) {
                String text = jTextPane1.getText() + Character.getNumericValue(c);
                if (this.logger.getNumberOfGenerations() >= Integer.valueOf(text) && !text.isEmpty()) {
                    this.changeCount = Integer.valueOf(text) -1;
                } else {
                    evt.consume();
                }
            }
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if(!jTextPane1.getText().isEmpty()) {
            this.change = true;
        }
    }

    @Override
    public void update() {
        if(this.change){
            changeCurrentGeneration(this.changeCount);
            this.changeCount = 0;
            jTextPane1.setText("");
            this.change = false;
        }
        this.map.draw();
        this.runner.update();
        this.ghost.update();
        this.runner.draw();
        this.ghost.draw();
        updateLabels();
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                this.stateManager.changeToInitState();
                Window.changeToInit();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
                this.changeCurrentGeneration(true);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
                this.changeCurrentGeneration(false);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_B && Keyboard.getEventKeyState()) {
                this.changeCurrentGenerationToBestGenome();
            }

//            if (Keyboard.getEventKey() == Keyboard.KEY_D && Keyboard.getEventKeyState()) {
//                GenomePair genome = this.logger.getDebugGenomePair();
//                if (genome != null) {
//                    this.applyGenomeToRenderedRunner(genome);
//                }
//            }

//            if (Keyboard.getEventKey() == Keyboard.KEY_W && Keyboard.getEventKeyState()) {
//                this.runner.moveUp();
//            }
//            if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
//                this.runner.moveDown();
//            }
//            if (Keyboard.getEventKey() == Keyboard.KEY_D && Keyboard.getEventKeyState()) {
//                this.runner.moveRight();
//            }
//            if (Keyboard.getEventKey() == Keyboard.KEY_A && Keyboard.getEventKeyState()) {
//                this.runner.moveLeft();
//            }
        }
    }

    private void updateLabels() {
        this.jLabel2.setText(""+round(this.bestGenomeInCurrentGen.getAvrgRunnerFScore(), 4));
        this.jLabel4.setText(""+round(this.bestGenomeInCurrentGen.getAvrgGhostFScore(), 4));
        this.jLabel9.setText(""+round(this.bestGenomeInCurrentGen.getBestGhostFScore(), 4));
        this.jLabel10.setText(""+round(this.bestGenomeInCurrentGen.getBestRunnerFScore(), 4));
        this.jLabel6.setText(""+(this.currentGen + 1));
        this.jLabel13.setText(""+this.logger.getNumberOfGenerations());
    }

    private void changeCurrentGenerationToBestGenome() {
        applyGenomeToRenderedRunner(this.logger.getBestGenomePair());
    }

    private void changeCurrentGeneration(boolean b) {

        if (b) {
            this.currentGen++;
        } else {
            this.currentGen--;
        }
        changeCurrentGeneration(this.currentGen);
    }

    private void changeCurrentGeneration(int b) {
        if (b >= this.logger.getNumberOfGenerations()) {
            b = logger.getNumberOfGenerations()-1;
        }
        if (b < 0) {
            b = 0;
        }
        applyGenomeToRenderedRunner(logger.getGenomeByGenerationNumer(b));
    }

    private void applyGenomeToRenderedRunner(GenomePair genome) {
        this.bestGenomeInCurrentGen = genome;
        this.currentGen = genome.getGeneration() - 1;
        this.runner.resetWithNewGenome(genome.getRunnerGenome());
        this.ghost.resetWithNewGenome(genome.getGhostGenome());
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    private static double round(double value, int places) {
        if(value != Double.NaN) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        return 0;
    }
}
