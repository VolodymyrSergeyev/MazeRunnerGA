package GA.State;

import GA.Gfx.HUD;
import GA.Gfx.Helper.Artist;
import GA.Gfx.Window;
import GA.World.Logger.Logger;
import GA.World.Map.Element.TileType;
import GA.World.Map.Map;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import GA.Gfx.Helper.Artist.*;

import javax.swing.*;

import java.io.File;

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

        jButton1.setIcon(new javax.swing.ImageIcon(imgPath + "floor.png")); // NOI18N
        jButton1.setText("Floor");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setIcon(new javax.swing.ImageIcon(imgPath + "wall.png")); // NOI18N
        jButton2.setText("Wall");

        jButton3.setIcon(new javax.swing.ImageIcon(imgPath + "food.png")); // NOI18N
        jButton3.setText("Food");

        jButton4.setIcon(new javax.swing.ImageIcon(imgPath + "rs.png")); // NOI18N
        jButton4.setText("Runner Spawn");

        jButton5.setIcon(new javax.swing.ImageIcon(imgPath + "gs.png")); // NOI18N
        jButton5.setText("Ghost Spawn");

        jButton6.setText("Save");

        jButton7.setText("Reset");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1, 139, 139, 139)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, 139, 139, 139)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, 139, 139, 139)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    @Override
    public void update() {
        map.draw();

        //Mouse Input hendler
        if (Mouse.isButtonDown(0)) {
            setTile();
        }
        //Keyboard Input hendler
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
                changeIndex(true);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
                changeIndex(false);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
                saveMap(map);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                this.stateManager.changeToInitState();
                Window.changeToInit();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_D && Keyboard.getEventKeyState()){
                map = loadDefaultMap();
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

    private void changeIndex(boolean i) {
        if (i) {
            index++;
        } else {
            index--;
        }
        if (index > types.length - 1) {
            index = 0;
        } else if (index < 0) {
            index = types.length - 1;
        }
    }
}
