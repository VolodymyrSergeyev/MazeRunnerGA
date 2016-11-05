package GA.World.Population;

import java.io.File;

public class PopulationManager implements Runnable {

    private Population population;

    public PopulationManager(Population population){
        this.population = population;
    }

    @Override
    public void run() {
        System.gc();
        if(this.population.isInfinite()){
            while (!this.population.goalAchieved()){
                population.natural_selection();
            }
        }else {
            for (int i = 0; i < population.getPopulationSize(); i++) {
                population.natural_selection();
            }
        }
        this.population.logger.setBestGenome(this.population.getBestRunnerGenome());
    }

}
