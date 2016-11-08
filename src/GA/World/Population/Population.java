package GA.World.Population;


import GA.World.Entity.MazeRunner;
import GA.World.Entity.SpookyGhost;
import GA.World.Logger.Entity.GenomePair;
import GA.World.Logger.Logger;
import GA.World.Map.Map;
import GA.World.Population.Entity.Specimen;

import java.util.ArrayList;
import java.util.Random;

public class Population {
    private final int numOfGenerations;
    private int populationSize;
    private int genomeSize;
    private int genNum;
    private MazeRunner bestRunnerInCurrentGen = null;
    private SpookyGhost bestGhostInCurrentGen = null;

    private double bestRunnerFScoreInCurrentGen = 0;
    private double highestRunnerFitnessScore = 0;
    private double previousHighestRunnerFitnessScore = 0;
    private GenomePair bestGenomePair = null;
    private double bestGhostFScoreInCurrentGen = 0;
    private double highestGhostFitnessScore = 0;
    private double previousHighestGhostFitnessScore = 0;

    private ArrayList<Specimen> tmpSpecimen;

    private Random random;

    private SpecimenManager specimenManager;
    public Logger logger;
    private Map map;
    private boolean goalAchieved;
    private boolean endded;
    private boolean infinite;
    private boolean init;
    private boolean first = true;
    private double mutationRate;

    private int count = 0;

    public Population(Logger logger, Map map, int numOfGenerations, int populationSize, int genomeSize, double mutationRate) {
        this.numOfGenerations = numOfGenerations;
        if (populationSize <= 0) {
            this.populationSize = 500;
            this.infinite = true;
        } else {
            this.populationSize = populationSize < 10 ? 10 : populationSize;
            this.infinite = false;
        }
        this.genomeSize = genomeSize;
        this.mutationRate = mutationRate;

        this.tmpSpecimen = new ArrayList<>();

        this.random = new Random();
        this.specimenManager = new SpecimenManager(this.populationSize);
        this.logger = logger;
        this.map = map;

        this.init = true;
        this.goalAchieved = false;
        this.endded = false;

        initializeFirstRunnerGeneration();
    }

    private void initializeFirstRunnerGeneration() {
        for (int i = 0; i < this.populationSize; i++) {
            Map map = getMapCopy();
            this.specimenManager.addSpecimen(new Specimen(new MazeRunner(genomeSize, map, false), new SpookyGhost(genomeSize, map, false), 0, 0));
        }
    }

    private void calculateFitnessScoresForCurrentGen() {
        this.genNum++;
        double runnerFScore;
        double ghostFScore;
        Specimen s;
        int runnerVictoryPoints = 0;
        boolean runnerVictory;
        boolean ghostVictory;
        int size = this.specimenManager.getGeneralListOfSpecimens().size();
        for (int i = 0; i < size; i++) {
            s = this.specimenManager.getGeneralListOfSpecimens().get(i);
            calculateSpecimenFitnessScore(s);
            runnerFScore = s.getRunnerFScore();
            ghostFScore = s.getGhostFScore();

            if(s.isRunnerIsAWinner()){
                runnerVictoryPoints++;
            }

            runnerVictory = runnerVictoryPoints > size / 2;
            ghostVictory = !runnerVictory;

            if ((runnerFScore > this.bestRunnerFScoreInCurrentGen && runnerVictory) || first) {
                setBestSpecimenInCurrentGen(runnerFScore, ghostFScore, s);
            }

            if ((ghostFScore > this.bestGhostFScoreInCurrentGen && ghostVictory) || first) {
                setBestSpecimenInCurrentGen(runnerFScore, ghostFScore, s);
            }

            if((runnerFScore > this.highestRunnerFitnessScore && runnerVictory) || first){
                this.highestRunnerFitnessScore = runnerFScore;
                logCurrentBestSpecimen(runnerFScore, ghostFScore, s);
            }

            if ((ghostFScore > this.highestGhostFitnessScore && ghostVictory) || first) {
                this.highestGhostFitnessScore = ghostFScore;
                logCurrentBestSpecimen(runnerFScore, ghostFScore, s);
            }

            if(first){
                this.first = false;
            }

            if (i == this.specimenManager.getGeneralListOfSpecimens().size() - 1) {
                if (this.previousHighestRunnerFitnessScore == this.highestRunnerFitnessScore) {
                    this.count++;
                    if (this.count > 50) {
                        this.endded = true;
                    }
                } else {
                    this.previousHighestRunnerFitnessScore = this.highestRunnerFitnessScore;
                    this.count = 0;
                }
                this.logger.addGenome(new GenomePair(this.bestRunnerInCurrentGen.getGenome(), this.bestGhostInCurrentGen.getGenome(), this.genNum, this.bestRunnerFScoreInCurrentGen, this.bestGhostFScoreInCurrentGen));
            }
        }
    }

    private void setBestSpecimenInCurrentGen(double runnerFScore, double ghostFScore, Specimen s) {
        this.bestRunnerFScoreInCurrentGen = runnerFScore;
        this.bestRunnerInCurrentGen = s.getRunner();

        this.bestGhostFScoreInCurrentGen = ghostFScore;
        this.bestGhostInCurrentGen = s.getGhost();
    }

    private void logCurrentBestSpecimen(double runnerFScore, double ghostFScore, Specimen s) {
            this.bestGenomePair = new GenomePair(s.getRunner().getGenome(), s.getGhost().getGenome(), this.genNum, runnerFScore, ghostFScore);
            this.logger.setBestGenomePair(this.bestGenomePair);
    }

    private void calculateSpecimenFitnessScore(Specimen specimen) {
        double runnerFScore = 0;
        double ghostFScore = 0;
        if (!specimen.isTested()) {
            MazeRunner runner = specimen.getRunner();
            SpookyGhost ghost = specimen.getGhost();
            boolean firstDeath = true;
            for (int i = 0; i < this.genomeSize; i++) {
                if(firstDeath) {
                    double tmpDcrScore = ((double) (this.genomeSize - i) / (double) this.genomeSize) * 100.0;
                    double tmpIncScore = ((double) i / (double) this.genomeSize) * 100.0;
                    int oldFoodState = runner.getFoodEaten();
                    double oldXPosState = Math.pow(runner.getX() - ghost.getX(), 2.0);
                    double oldYPosState = Math.pow(runner.getY() - ghost.getY(), 2.0);
                    double absOldPos = oldXPosState + oldYPosState;
                    runner.moveByGenomeId(i);
                    ghost.moveByGenomeId(i);
                    double newXPosState = Math.pow(runner.getX() - ghost.getX(), 2.0);
                    double newYPosState = Math.pow(runner.getY() - ghost.getY(), 2.0);
                    double absNewPos = newXPosState + newYPosState;
                    if (runner.getFoodEaten() <= oldFoodState) {
                        runnerFScore -= Math.pow(tmpDcrScore, 6);
                    }
                    if (absNewPos >= absOldPos) {
                        ghostFScore -= Math.pow(tmpDcrScore, 6);
                    }
                    if (!runner.isAlive()) {
                        //runnerFScore -= Math.pow(tmpDcrScore, 10);
                        //ghostFScore += Math.pow(tmpDcrScore, 10);
                        //ghostFScore += Math.pow(tmpIncScore, 6);
                        //runnerFScore -= Math.pow(tmpDcrScore, 6);
                        runnerFScore = Double.NEGATIVE_INFINITY;
                        firstDeath = false;
                    }
                }
            }
            specimen.setRunnerIsAWinner(firstDeath);
            specimen.setRunnerFScore(runnerFScore);
            specimen.setGhostFScore(ghostFScore);
            specimen.setTested();
            if (runner.getFoodEaten() == map.getMaxFood()) {
                this.goalAchieved = true;
            }
        }
    }

    private ArrayList<Integer> createGenomePool(ArrayList<Specimen> specimens) {

        ArrayList<Integer> pool = new ArrayList<>();
        for (int i = 0; i < specimens.size(); i++) {
            int count = ((specimens.size() + 1) - i) * 10;
            for (int p = 0; p < count; p++) {
                pool.add(i);
            }
        }
        return pool;
    }

    /* cross-over by half genomes */
    void naturalSelection() {
        calculateFitnessScoresForCurrentGen();
        this.specimenManager.calculateTopSpecimens();
        this.specimenManager.removeWeakSpecimens();
        ArrayList<Integer> runnerGenomePool = createGenomePool(this.specimenManager.getTopBestRunnerSpecimens());
        ArrayList<Integer> ghostGenomePool = createGenomePool(this.specimenManager.getTopBestGhostSpecimens());
        for (int i = 0; i < this.populationSize - 1; i = i + 2) {
            Map map1 = getMapCopy();
            Map map2 = getMapCopy();
            performSpecimenCrossOver(runnerGenomePool, ghostGenomePool, map1, map2);
        }
        mutateTopBestSpecimens();
        this.specimenManager.getGeneralListOfSpecimens().addAll(this.tmpSpecimen);
        this.tmpSpecimen = new ArrayList<>();
    }

    private void reInitializeRunnerPopulation() {
        this.specimenManager.removeWeakSpecimens();
        initializeFirstRunnerGeneration();
        calculateFitnessScoresForCurrentGen();
        if (this.bestRunnerFScoreInCurrentGen == 0) {
            reInitializeRunnerPopulation();
        }
    }

    private void performSpecimenCrossOver(ArrayList<Integer> runnerPool, ArrayList<Integer> ghostPool, Map map1, Map map2) {
        ArrayList<Integer> runnerChromosome1 = new ArrayList<>();
        ArrayList<Integer> runnerChromosome2 = new ArrayList<>();
        ArrayList<Integer> runnerNewGenome1 = new ArrayList<>();
        ArrayList<Integer> runnerNewGenome2 = new ArrayList<>();
        ArrayList<Integer> ghostChromosome1 = new ArrayList<>();
        ArrayList<Integer> ghostChromosome2 = new ArrayList<>();
        ArrayList<Integer> ghostNewGenome1 = new ArrayList<>();
        ArrayList<Integer> ghostNewGenome2 = new ArrayList<>();

        MazeRunner runnerParent1 = this.specimenManager.getTopBestRunnerSpecimens()
                .get(runnerPool.get(getRandomNumberBetween1And(runnerPool.size()))).getRunner();
        MazeRunner runnerParent2 = this.specimenManager.getTopBestRunnerSpecimens()
                .get(runnerPool.get(getRandomNumberBetween1And(runnerPool.size()))).getRunner();

        SpookyGhost ghostParent1 = this.specimenManager.getTopBestRunnerSpecimens()
                .get(ghostPool.get(getRandomNumberBetween1And(ghostPool.size()))).getGhost();
        SpookyGhost ghostParent2 = this.specimenManager.getTopBestRunnerSpecimens()
                .get(ghostPool.get(getRandomNumberBetween1And(ghostPool.size()))).getGhost();


        int splitPoint = (int) (this.genomeSize * getRandomPercent(0.9));

        runnerChromosome1.addAll(runnerParent1.getGenome().subList(0, splitPoint));
        runnerChromosome2.addAll(runnerParent2.getGenome().subList(splitPoint, this.genomeSize));

        ghostChromosome1.addAll(ghostParent1.getGenome().subList(0, splitPoint));
        ghostChromosome2.addAll(ghostParent2.getGenome().subList(splitPoint, this.genomeSize));

        runnerNewGenome1.addAll(runnerChromosome1);
        runnerNewGenome1.addAll(runnerChromosome2);

        ghostNewGenome1.addAll(ghostChromosome1);
        ghostNewGenome1.addAll(ghostChromosome2);


        if (getRandomNumberBetween1And(101) < (int) (100 * this.mutationRate)) {
            runnerNewGenome1 = getMutatedGenome(runnerNewGenome1);
        }

        if (getRandomNumberBetween1And(101) < (int) (100 * this.mutationRate)) {
            ghostNewGenome1 = getMutatedGenome(ghostNewGenome1);
        }

        this.tmpSpecimen.add(new Specimen(new MazeRunner(runnerNewGenome1, map1, false), new SpookyGhost(ghostNewGenome1, map1, false), 0, 0));


        runnerNewGenome2.addAll(runnerChromosome2);
        runnerNewGenome2.addAll(runnerChromosome1);

        ghostNewGenome2.addAll(ghostChromosome2);
        ghostNewGenome2.addAll(ghostChromosome1);

        if (getRandomNumberBetween1And(101) < (int) (100 * this.mutationRate)) {
            runnerNewGenome2 = getMutatedGenome(runnerNewGenome2);
        }

        if (getRandomNumberBetween1And(101) < (int) (100 * this.mutationRate)) {
            ghostNewGenome2 = getMutatedGenome(ghostNewGenome2);
        }

        this.tmpSpecimen.add(new Specimen(new MazeRunner(runnerNewGenome2, map2, false), new SpookyGhost(ghostNewGenome2, map2, false), 0, 0));
    }

    private void mutateTopBestSpecimens() {
        for (Specimen s : this.specimenManager.getGeneralListOfSpecimens()) {
            Map map = getMapCopy();
            tmpSpecimen.add(new Specimen(new MazeRunner(getMutatedGenome(s.getRunner().getGenome()), map, false),
                    new SpookyGhost(getMutatedGenome(s.getGhost().getGenome()), map, false), 0, 0));
        }
    }

    private ArrayList<Integer> getMutatedGenome(ArrayList<Integer> genome) {
        ArrayList<Integer> mutatedGenome = new ArrayList<>();
        for (Integer i : genome) {
            if (getRandomNumberBetween1And(101) < 2) {
                mutatedGenome.add(getRandomNumberBetween1And(5) - 1);
            } else {
                mutatedGenome.add(i);
            }
        }
        return mutatedGenome;
    }

    private ArrayList<Integer> mutateGenome(Specimen specimen) {
        return null;
    }

    private Map getMapCopy() {
        return new Map(this.map);
    }

    private int getRandomNumberBetween1And(int size) {

        int result = this.random.nextInt(size);
        if (result > 0 && result < size) {
            return result;
        }
        return getRandomNumberBetween1And(size);
    }

    private double getRandomPercent(double max) {
        double result = this.random.nextDouble();
        if (result > 0.0 && result < max) {
            return result;
        }
        return getRandomPercent(max);
    }

    /* cross-over choosing random dnas */
    public void natural_selection2() {
        Random random = new Random();
        float[] fitness_values = new float[populationSize];
        highestRunnerFitnessScore = 0f;
        //fitness_values = calculateFitnessScoresForCurrentGen();

        System.out.println("Avg: " + get_average_fitness(fitness_values)
                + " Max: " + highestRunnerFitnessScore);

//        System.out.println("Fitness values: " + get_sFitness_values(fitness_values)
//        + " Highest: " + highestRunnerFitnessScore );

        //ArrayList<MazeRunner> wheel = createGenomePool(fitness_values);

        int speciment;
/*
        for (int i = 0; i < runners.length - 1; i = i + 2) {

            ArrayList<Integer> chrom_1 = new ArrayList<>();
            ArrayList<Integer> chrom_2 = new ArrayList<>();
            ArrayList<Integer> new_genome1 = new ArrayList<>();
            ArrayList<Integer> new_genome2 = new ArrayList<>();

            speciment = random.nextInt(wheel.size()) + 1;
            chrom_1.addAll(wheel.get(i).getGenome().subList(0, genomeSize / 2));
            chrom_2.addAll(wheel.get(i).getGenome().subList(genomeSize / 2, genomeSize));

            new_genome1.addAll(chrom_1);
            new_genome1.addAll(chrom_2);
            //new_genome1 = mutation(new_genome1);

            runners[i] = new MazeRunner(new_genome1, genomeSize, new Map(this.map), false);


            new_genome2.addAll(chrom_2);
            new_genome2.addAll(chrom_1);
            //new_genome2 = mutation(new_genome2);

            runners[i + 1] = new MazeRunner(new_genome2, genomeSize, new Map(this.map), false);

            //System.out.println(runners[i].get_sGenome());
            //System.out.println(runners[i + 1].get_sGenome());
        }
        */
    }

    public void print_players() {
        /*
        for (int i = 0; i < runners.length; i++) {
            System.out.print(i + ": ");
            for (int p = 0; p < genomeSize; p++) {
                System.out.print(runners[i].getGenome().get(p) + ", ");
            }
            System.out.println("");
        }
        */
    }

    public String get_sFitness_values(float[] values) {
        String sValues = "";
        for (float value : values) {
            sValues = sValues + value + ", ";
        }
        return sValues;
    }

    private float get_average_fitness(float[] values) {
        float average = 0f;
        for (float value : values) {
            average = average + value;
        }
        return average / values.length;
    }

    int getNumOfGenerations() {
        return this.numOfGenerations;
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    boolean goalAchieved() {
        return this.goalAchieved;
    }

    GenomePair getBestGenomePair() {
        return this.bestGenomePair;
    }

    public boolean isInfinite() {
        return this.infinite;
    }

    boolean isEndded() {
        return this.endded;
    }
}