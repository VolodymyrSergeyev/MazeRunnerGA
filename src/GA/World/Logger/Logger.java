package GA.World.Logger;

import GA.World.Logger.Entity.Genome;

import java.util.ArrayList;

public class Logger {
    private ArrayList<Genome> genomeList;
    private Genome bestGenome;

    public Logger(){
        this.genomeList = new ArrayList<>();
    }

    public void addGenome(Genome genome){
        this.genomeList.add(genome);
    }

    public Genome getGenomeByGenerationNumer(int genNumb){
        return this.genomeList.get(genNumb);
    }

    public int getNumberOfGenerations(){
        return this.genomeList.size();
    }

    public Genome getBestGenome() {
        return bestGenome;
    }

    public void setBestGenome(Genome bestGenome) {
        this.bestGenome = bestGenome;
    }
}
