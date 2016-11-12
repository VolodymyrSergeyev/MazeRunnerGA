package GA.World.Population.Entity;

import GA.World.Entity.MazeRunner;
import GA.World.Entity.SpookyGhost;


public class Specimen {
    private double runnerFScore;
    private double ghostFScore;
    private double reTestRunnerFScore;
    private double reTestGhostFScore;
    private MazeRunner runner;
    private SpookyGhost ghost;
    private MazeRunner reTestRunner;
    private SpookyGhost reTestGhost;
    private boolean tested;
    private boolean runnerIsAWinner;
    private boolean setForReTest = false;

    public Specimen(MazeRunner runner, SpookyGhost ghost, double runnerFScore, double ghostFScore){
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

    public boolean isSetForReTest() {
        return setForReTest;
    }

    public void setForReTest(boolean setForReTest) {
        this.setForReTest = setForReTest;
    }

    public void setReTestGhost(SpookyGhost reTestGhost) {
        this.reTestGhost = reTestGhost;
    }

    public void setReTestRunner(MazeRunner reTestRunner) {
        this.reTestRunner = reTestRunner;
    }

    public MazeRunner getReTestRunner() {
        return reTestRunner;
    }

    public SpookyGhost getReTestGhost() {
        return reTestGhost;
    }

    public double getReTestRunnerFScore() {
        return reTestRunnerFScore;
    }

    public void setReTestRunnerFScore(double reTestRunnerFScore) {
        this.reTestRunnerFScore = reTestRunnerFScore;
    }

    public double getReTestGhostFScore() {
        return reTestGhostFScore;
    }

    public void setReTestGhostFScore(double reTestGhostFScore) {
        this.reTestGhostFScore = reTestGhostFScore;
    }
}
