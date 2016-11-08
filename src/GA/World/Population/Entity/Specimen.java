package GA.World.Population.Entity;

import GA.World.Entity.MazeRunner;
import GA.World.Entity.SpookyGhost;


public class Specimen {
    private double runnerFScore;
    private double ghostFScore;
    private MazeRunner runner;
    private SpookyGhost ghost;
    private boolean tested;
    private boolean runnerIsAWinner;

    public Specimen(MazeRunner runner, SpookyGhost ghost, float runnerFScore, float ghostFScore){
        this.tested = false;
        this.runner = runner;
        this.ghost = ghost;
        this.runnerFScore = runnerFScore;
        this.ghostFScore = ghostFScore;
    }

    public MazeRunner getRunner() {
        return this.runner;
    }

    public SpookyGhost getGhost(){
        return this.ghost;
    }

    public double getRunnerFScore() {
        return this.runnerFScore;
    }
    public double getGhostFScore() {
        return this.ghostFScore;
    }

    public void setRunnerFScore(double runnerFScore) {
        this.runnerFScore = runnerFScore;
    }

    public void setGhostFScore(double ghostFScore) {
        this.ghostFScore = ghostFScore;
    }

    public boolean isTested() {
        return tested;
    }

    public void setTested(){
        this.tested = true;
    }

    public boolean isRunnerIsAWinner() {
        return runnerIsAWinner;
    }

    public void setRunnerIsAWinner(boolean runnerIsAWinner) {
        this.runnerIsAWinner = runnerIsAWinner;
    }
}
