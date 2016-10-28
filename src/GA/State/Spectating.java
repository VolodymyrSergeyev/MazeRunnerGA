package GA.State;

import GA.World.Map.Map;
import org.lwjgl.input.Keyboard;

import static GA.World.Map.MapManager.*;

/**
 *
 * @author Vovaxs
 */
public class Spectating implements State {

    private final StateManager stateManager;
    private Map map;
    
    private boolean initialized = false;

    public Spectating(StateManager stateManager){
        this.stateManager = stateManager;
    }
    
    @Override
    public void init() {
        this.map = this.levelInit();
        this.initialized = true;
    }

    @Override
    public void update() {
        this.map.draw();
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                this.stateManager.changeToInitState();
            }
        }
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
    
    private Map levelInit(){
        Map currentlevel = loadMainMap();
        return currentlevel;
    }
    
}
