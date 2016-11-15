package GA.World.Logger;

import GA.World.Logger.Entity.GenomePair;

import java.util.ArrayList;

public class Logger {
    private ArrayList<GenomePair> genomePairList;
    private GenomePair bestGenomePair;
    private GenomePair debugGenomePair;

    public Logger(){
        this.genomePairList = new ArrayList<>();
    }

    public void addGenome(GenomePair genomePair){
        this.genomePairList.add(genomePair);
    }

    public GenomePair getGenomeByGenerationNumer(int genNumb){
        return this.genomePairList.get(genNumb);
    }

    public int getNumberOfGenerations(){
        return this.genomePairList.size();
    }

    public GenomePair getBestGenomePair() {
        return bestGenomePair;
    }

    public void setBestGenomePair(GenomePair bestGenomePair) {
        this.bestGenomePair = bestGenomePair;
    }
}
