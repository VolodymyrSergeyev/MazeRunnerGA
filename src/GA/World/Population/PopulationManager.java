package GA.World.Population;

public class PopulationManager implements Runnable {

    private Population population;

    public PopulationManager(Population population){
        this.population = population;
    }

    @Override
    public void run() {
        if(!Thread.currentThread().isInterrupted()) {
            if (this.population.getNumOfGenerations() == 0) {
                while (!this.population.goalAchieved() && !this.population.isEndded() && !Thread.currentThread().isInterrupted()) {
                    population.naturalSelection();
                }
            } else {
                for (int i = 0; i < population.getNumOfGenerations(); i++) {
                    population.naturalSelection();
                }
            }
            this.population.logger.setBestGenomePair(this.population.getBestGenomePair());
        }
    }

}
