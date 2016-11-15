package GA.State;

import GA.World.Logger.Logger;
import GA.World.Map.Map;

interface State {
    
    void init(Logger logger, Map map);

    void update();

    boolean isInitialized();
}
