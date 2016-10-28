package GA.State;

public class StateManager {
    private State currentState;
    private final State[] states;

    public StateManager(){
        this.states = new State[4];
        this.states[1] = new Init(this);
        this.states[2] = new MapEditor(this);
        this.states[3] = new Spectating(this);
        this.currentState = this.states[1];
    }

    public void update() {
        if(!this.currentState.isInitialized()){
            this.currentState.init();
        }else{
            this.currentState.update();
        }
    }

    public void changeToInitState(){
        this.currentState = this.states[1];
    }
    public void changeToMapEditState(){
        this.currentState = this.states[2];
    }
    public void changeToSpectatingState(){
        this.currentState = this.states[3];
    }
}