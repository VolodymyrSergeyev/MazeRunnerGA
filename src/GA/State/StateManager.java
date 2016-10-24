package GA.State;

public class StateManager {
    private State currentState;

    public StateManager(){
        this.currentState = new Init(this);
    }

    public void update() {
        if(!this.currentState.isInitialized()){
            this.currentState.init();
        }else{
            this.currentState.update();
        }
    }

    public void changeState(State state){
        this.currentState = state;
    }
}