package GA.State;

import GA.World.Logger.Logger;
import GA.World.Map.Map;

public interface State {
    
    public void init(Logger logger, Map map);

    public void update();

    public boolean isInitialized();
}
