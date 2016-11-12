package GA.State;

import GA.Gfx.Window;
import GA.World.Logger.Logger;
import GA.World.Map.Element.TileType;
import GA.World.Map.Map;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static GA.World.Map.MapManager.*;

public class MapEditor implements State {
    private Map map;
    private TileType[] types;
    private int index;

    private boolean initialized = false;
    private StateManager stateManager;

    public static final String MAP_EDITOR_ID = "MapEditor";
    private JPanel panel;

    private String imgPath = "res" + File.separator + "img" + File.separator;

    private boolean save = false;
    private boolean reload = false;

    MapEditor(StateManager stateManager){
        this.stateManager = stateManager;
    }

    @Override
    public void init(Logger logger, Map map) {
        this.panel = Window.getMapeditorPanel();
        this.map = map;
        this.types = new TileType[5];
        this.types[0] = TileType.Floor;
        this.types[1] = TileType.Wall;
        this.types[2] = TileType.RunnerSpawn;
        this.types[3] = TileType.GhostSpawn;
        this.types[4] = TileType.Food;
        this.index = 0;
        this.map.initRendering();

        initializePanel();

        this.initialized = true;
    }

    private void initializePanel() {
        javax.swing.JButton jButton1;
        javax.swing.JButton jButton2;
        javax.swing.JButton jButton3;
        javax.swing.JButton jButton4;
        javax.swing.JButton jButton5;
        javax.swing.JButton jButton6;
        javax.swing.JButton jButton7;
        javax.swing.JPanel jPanel1;

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        this.panel.setPreferredSize(new java.awt.Dimension(Window.WIDTH, 80));

        jButton1.setIcon(new ImageIcon(getResizedIcon("floor.png"))); // NOI18N
        jButton1.setText("Floor");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setIcon(new ImageIcon(getResizedIcon("wall.png"))); // NOI18N
        jButton2.setText("Wall");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setIcon(new ImageIcon(getResizedIcon("food.png"))); // NOI18N
        jButton3.setText("Food");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setIcon(new ImageIcon(getResizedIcon("rs.png"))); // NOI18N
        jButton4.setText("Runner Spawn");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setIcon(new ImageIcon(getResizedIcon("gs.png"))); // NOI18N
        jButton5.setText("Ghost Spawn");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButton6.setText("Save");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jButton7.setText("Reset");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4, 170, 170, 170)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5, 170, 170, 170)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButton6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                                .addComponent(jButton7))
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.panel);
        this.panel.setLayout(layout);
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

    private Image getResizedIcon(String name) {
        Image img = null;
        File f = new File(imgPath + name);
        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img != null ? img.getScaledInstance(20, 20, 0) : null;
    }

    private void jButton7ActionPerformed(ActionEvent event) {
        this.reload = true;
    }

    private void jButton6ActionPerformed(ActionEvent event) {
        this.save = true;
    }

    private void jButton5ActionPerformed(ActionEvent event) {
        changeIndex(3);
    }

    private void jButton4ActionPerformed(ActionEvent event) {
        changeIndex(2);
    }

    private void jButton3ActionPerformed(ActionEvent event) {
        changeIndex(4);
    }

    private void jButton2ActionPerformed(ActionEvent event) {
        changeIndex(1);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        changeIndex(0);
    }

    @Override
    public void update() {
        if(save){
            saveMap(map);
            save = false;
        }
        if(reload){
            map = loadDefaultMap();
            map.initRendering();
            reload = false;
        }
        map.draw();

        //Mouse Input hendler
        if (Mouse.isButtonDown(0)) {
            setTile();
        }
        //Keyboard Input hendler
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                this.stateManager.changeToInitState();
                Window.changeToInit();
            }
        }
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }


    private void setTile() {
        if (isInWindow()) {
            map.setTile((int) Math.floor(Mouse.getX() / (map.getBlockSize() * map.getSCALE())), (int) Math.floor((map.getFrameHeight() - Mouse.getY() - 1) / (map.getBlockSize() * map.getSCALE())), types[index], true);
        }
    }

    private boolean isInWindow() {
        return (int) Math.floor(Mouse.getX() / (map.getBlockSize() * map.getSCALE())) < map.getMapWidth() && (int) Math.floor((map.getFrameHeight() - Mouse.getY() - 1) / (map.getBlockSize() * map.getSCALE())) < map.getMapHeight();
    }

    private void changeIndex(int index) {
        if (index > types.length - 1) {
            index = types.length - 1;
        } else if (index < 0) {
            index = 0;
        }
        this.index = index;
    }
}
