package GA.World.Logger.Entity;

import java.util.ArrayList;

public class Genome {
    private final ArrayList<Integer> genome;
    private final int generation;
    private final double fScore;

    public Genome(ArrayList<Integer> genome, int generation, double fScore){
        this.genome = genome;
        this.generation = generation;
        this.fScore = fScore;
    }

    public ArrayList<Integer> getGenome() {
        return genome;
    }

    public int getGeneration() {
        return generation;
    }

    public double getfScore() {
        return fScore;
    }

    public int getGenomeSize(){
        return genome.size();
    }
}
