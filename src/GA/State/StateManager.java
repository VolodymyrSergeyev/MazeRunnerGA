package GA.State;

import GA.World.Logger.Logger;
import GA.World.Map.Map;
import GA.World.Population.Population;
import GA.World.Population.PopulationManager;

import static GA.World.Map.MapManager.loadMainMap;

public class StateManager {
    public Thread popManager;
    private final Logger logger;
    private final Population population;
    private final Map map;
    private State currentState;
    private final State[] states;

    public StateManager(){
        this.states = new State[4];
        this.states[1] = new Init(this);
        this.states[2] = new MapEditor(this);
        this.states[3] = new Spectating(this);
        this.currentState = this.states[1];
        this.map = loadMainMap();
        this.logger = new Logger();
        this.population = new Population(this.logger, new Map(this.map), 0, 300, 300, 0.8);
        this.popManager = new Thread(new PopulationManager(this.population));
        this.popManager.start();
    }

    public void update() {
        if(!this.currentState.isInitialized()){
            this.currentState.init(this.logger, new Map(this.map));
        }else{
            this.currentState.update();
        }
    }

    void changeToInitState(){
        this.currentState = this.states[1];
    }
    void changeToMapEditState(){
        this.currentState = this.states[2];
    }
    void changeToSpectatingState(){
        this.currentState = this.states[3];
    }
}