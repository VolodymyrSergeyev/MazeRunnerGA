package GA.State;

import GA.World.Map.Map;
import static GA.World.Map.MapManager.*;

/**
 *
 * @author Vovaxs
 */
public class Spectating implements State {

    private Map map;
    
    private boolean initialized = false;
    
    @Override
    public void init() {
        this.map = this.levelInit();
        this.initialized = true;
    }

    @Override
    public void update() {
        this.map.draw();
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
