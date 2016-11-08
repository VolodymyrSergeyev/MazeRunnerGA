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

import static GA.World.Map.MapManager.*;

class MapEditor implements State {
    private Map map;
    private TileType[] types;
    private int index;

    private boolean initialized = false;
    private StateManager stateManager;

    MapEditor(StateManager stateManager){
        this.stateManager = stateManager;
    }

    @Override
    public void init(Logger logger, Map map) {
        this.map = map;
        this.types = new TileType[5];
        this.types[0] = TileType.Floor;
        this.types[1] = TileType.Wall;
        this.types[2] = TileType.RunnerSpawn;
        this.types[3] = TileType.GhostSpawn;
        this.types[4] = TileType.Food;
        this.index = 0;
        this.map.initRendering();
        this.initialized = true;
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
