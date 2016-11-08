package GA.World.Logger.Entity;

import java.util.ArrayList;

public class GenomePair {
    private final ArrayList<Integer> runnerGenome;
    private final ArrayList<Integer> ghostGenome;
    private final int generation;
    private final double runnerFScore;
    private double ghostFScore;

    public GenomePair(ArrayList<Integer> runnerGenome, ArrayList<Integer> ghostGenome, int generation, double runnerFScore, double ghostFScore){
        this.runnerGenome = runnerGenome;
        this.ghostGenome = ghostGenome;
        this.generation = generation;
        this.runnerFScore = runnerFScore;
        this.ghostFScore = ghostFScore;
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

    public double getRunnerFScore() {
        return runnerFScore;
    }

    public double getGhostFScore() {
        return ghostFScore;
    }
}
