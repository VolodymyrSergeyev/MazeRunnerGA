package GA.World.Population.Entity;

import GA.World.Entity.Entity;
import GA.World.Entity.MazeRunner;
import GA.World.Entity.SpookyGhost;


public class Specimen {
    private float fScore;
    private MazeRunner runner;
    private SpookyGhost ghost;
    private boolean isRunner;
    private boolean tested;

    public Specimen(MazeRunner runner, SpookyGhost ghost, float fScore){
        this.tested = false;
        if(runner != null){
            this.isRunner = true;
            this.runner = runner;
            this.ghost = null;
        }else {
            this.isRunner = false;
            this.ghost = ghost;
            this.runner = null;
        }
        this.fScore = fScore;
    }

    public MazeRunner getRunner() {
        return this.runner;
    }

    public SpookyGhost getGhost(){
        return this.ghost;
    }

    public float getFScore() {
        return this.fScore;
    }

    public boolean isRunner() {
        return isRunner;
    }

    public void setfScore(float fScore) {
        this.fScore = fScore;
    }

    public boolean isTested() {
        return tested;
    }

    public void setTested(){
        this.tested = true;
    }
}
