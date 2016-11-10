package GA.World.Logger.Entity;

import java.util.ArrayList;

public class GenomePair {
    private final ArrayList<Integer> runnerGenome;
    private final ArrayList<Integer> ghostGenome;
    private final int generation;
    private final double runnerFScore;
    private final double avrgRunnerFScore;
    private final double avrgGhostFScore;
    private double ghostFScore;

    public GenomePair(ArrayList<Integer> runnerGenome, ArrayList<Integer> ghostGenome, int generation, double runnerFScore, double ghostFScore, double avrgRunnerFScore, double avrgGhostFScore){
        this.runnerGenome = runnerGenome;
        this.ghostGenome = ghostGenome;
        this.generation = generation;
        this.runnerFScore = runnerFScore;
        this.ghostFScore = ghostFScore;
        this.avrgGhostFScore = avrgGhostFScore;
        this.avrgRunnerFScore = avrgRunnerFScore;
    }

    public ArrayList<Integer> getRunnerGenome() {
        return runnerGenome;
    }
    public ArrayList<Integer> getGhostGenome() {
        return ghostGenome;
    }

    public int getGeneration() {
        return generation;
    }

    public double getBestRunnerFScore() {
        return runnerFScore;
    }

    public double getBestGhostFScore() {
        return ghostFScore;
    }

    public double getAvrgRunnerFScore() {
        return avrgRunnerFScore;
    }

    public double getAvrgGhostFScore() {
        return avrgGhostFScore;
    }
}
