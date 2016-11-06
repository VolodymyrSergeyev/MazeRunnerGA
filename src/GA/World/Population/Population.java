package GA.World.Population;


import GA.World.Entity.MazeRunner;
import GA.World.Entity.SpookyGhost;
import GA.World.Logger.Entity.Genome;
import GA.World.Logger.Logger;
import GA.World.Map.Map;
import GA.World.Population.Entity.Specimen;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Random;

public class Population {
    private final int numOfGenerations;
    private int populationSize;
    private int genomeSize;
    private int genNum;
    private MazeRunner bestRunnerInCurrentGen = null;

    private double bestRunnerFScoreInCurrentGen = 0;
    private double highestRunnerFitnessScore = 0;
    private Genome bestRunnerGenome = null;
    private ArrayList<Specimen> tmpRunners;

    private ArrayList<Specimen> tmpGhosts;
    private Random random;

    private SpecimenManager specimenManager;
    public Logger logger;
    private Map map;
    private boolean goalAchieved;
    private boolean infinite;
    private boolean init;
    private boolean first = true;
    private double mutationRate;

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

        freeTmpLists();

        this.random = new Random();
        this.specimenManager = new SpecimenManager(this.populationSize);
        this.logger = logger;
        this.map = map;

        this.init = true;
        this.goalAchieved = false;

        initializeFirstRunnerGeneration();
    }

    public void initializeFirstRunnerGeneration() {
        for (int i = 0; i < this.populationSize; i++) {
            this.specimenManager.addSpecimen(new Specimen(new MazeRunner(genomeSize, getMapCopy(), false), null, 0));
        }
    }

    //TODO: create initializeFirstGhostGeneration()

    public void calculateFitnessScoresForCurrentGen() {
        this.genNum++;
        double fScore = 0;
        Specimen s;
        for (int i = 0; i < this.specimenManager.getGeneralListOfSpecimens().size(); i++) {
            s = this.specimenManager.getGeneralListOfSpecimens().get(i);
            calculateSpecimenFitnessScore(s);
            if (s.isRunner()) {
                fScore = s.getFScore();
                //Logging the best runner in current generation
                //Temporary storing best runner in the generations and its score
                if (fScore > this.bestRunnerFScoreInCurrentGen || first) {
                    this.bestRunnerFScoreInCurrentGen = fScore;
                    this.bestRunnerInCurrentGen = s.getRunner();
                }
                if (i == this.specimenManager.getGeneralListOfSpecimens().size() - 1) {
                    this.logger.addGenome(new Genome(this.bestRunnerInCurrentGen.getGenome(), this.genNum, this.bestRunnerFScoreInCurrentGen));
                }
                if (fScore > this.highestRunnerFitnessScore || first) {
                    this.first = false;
                    this.highestRunnerFitnessScore = fScore;
                    this.bestRunnerGenome = new Genome(s.getRunner().getGenome(), this.genNum, fScore);
                    this.logger.setBestGenome(this.bestRunnerGenome);
                }
            } else {
                //TODO: Ghost
            }
        }
    }

    public void calculateSpecimenFitnessScore(Specimen specimen) {
        double fScore = 0;
        if(!specimen.isTested()) {
            if (specimen.isRunner()) {
                MazeRunner runner = specimen.getRunner();
                for (int i = 0; i < this.genomeSize; i++) {
                    int oldFoodState = runner.getFoodEaten();
                    runner.moveByGenomeId(i);
                    if(runner.getFoodEaten() <= oldFoodState){
                        double tmpScore = ((double) (this.genomeSize - i) / (double) this.genomeSize) * 100.0;
                        fScore -= Math.pow(tmpScore,4);
                    }
                }
                if(!specimen.getRunner().isAlive()){
                    fScore = Double.NEGATIVE_INFINITY;
                }
                specimen.setfScore(fScore);
                specimen.setTested();
                if (runner.getFoodEaten() == map.getMaxFood()) {
                    this.goalAchieved = true;
                }
            } else {
                //TODO: Ghost
                SpookyGhost ghost = specimen.getGhost();
            }
        }
    }

    public ArrayList<Integer> createGenomePool(ArrayList<Specimen> specimens) {

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
    public void naturalSelection() {
        calculateFitnessScoresForCurrentGen();
        this.specimenManager.calculateTopSpecimens();
        this.specimenManager.removeWeakSpecimens();
        ArrayList<Integer> runnerGenomePool = createGenomePool(this.specimenManager.getTopBestRunnerSpecimens());
        ArrayList<Integer> ghostGenomePool = createGenomePool(this.specimenManager.getTopBestGhostSpecimens());
        for (int i = 0; i < this.populationSize - 1; i = i + 2) {
            Map map1 = getMapCopy();
            Map map2 = getMapCopy();
            performRunnerCrossOver(runnerGenomePool, map1, map2);
            performGhostNaturalSelection(ghostGenomePool, map1, map2);
        }
        mutateTopBestSpecimens();
        addTmpListsToGeneralSpecimenList();
        freeTmpLists();
    }

    private void reInitializeRunnerPopulation() {
        this.specimenManager.removeWeakSpecimens();
        initializeFirstRunnerGeneration();
        calculateFitnessScoresForCurrentGen();
        if(this.bestRunnerFScoreInCurrentGen == 0){
            reInitializeRunnerPopulation();
        }
    }

    private void addTmpListsToGeneralSpecimenList() {
        this.specimenManager.getGeneralListOfSpecimens().addAll(this.tmpRunners);
        this.specimenManager.getGeneralListOfSpecimens().addAll(this.tmpGhosts);
    }

    private void freeTmpLists() {
        this.tmpRunners = new ArrayList<>();
        this.tmpGhosts = new ArrayList<>();
    }

    private void performGhostNaturalSelection(ArrayList<Integer> pool, Map map1, Map map2) {

    }

    private void performRunnerCrossOver(ArrayList<Integer> pool, Map map1, Map map2) {
        ArrayList<Integer> chromosome1 = new ArrayList<>();
        ArrayList<Integer> chromosome2 = new ArrayList<>();
        ArrayList<Integer> newGenome1 = new ArrayList<>();
        ArrayList<Integer> newGenome2 = new ArrayList<>();

        MazeRunner parent1 = this.specimenManager.getTopBestRunnerSpecimens()
                .get(pool.get(getRandomNumberBetween1And(pool.size()))).getRunner();
        MazeRunner parent2 = this.specimenManager.getTopBestRunnerSpecimens()
                .get(pool.get(getRandomNumberBetween1And(pool.size()))).getRunner();


        int splitPoint = (int) (this.genomeSize * getRandomPercent(0.9));

        chromosome1.addAll(parent1.getGenome().subList(0, splitPoint));
        chromosome2.addAll(parent2.getGenome().subList(splitPoint, this.genomeSize));

        newGenome1.addAll(chromosome1);
        newGenome1.addAll(chromosome2);

        if(getRandomNumberBetween1And(101) < (int)(100 * this.mutationRate)){
            newGenome1 = getMutatedGenome(newGenome1);
        }

        this.tmpRunners.add(new Specimen(new MazeRunner(newGenome1, map1, false), null, 0));


        newGenome2.addAll(chromosome2);
        newGenome2.addAll(chromosome1);

        if(getRandomNumberBetween1And(101) < (int)(100 * this.mutationRate)){
            newGenome2 = getMutatedGenome(newGenome2);
        }

        this.tmpRunners.add(new Specimen(new MazeRunner(newGenome2, map2, false), null, 0));
    }

    private void mutateTopBestSpecimens() {
        for (Specimen s: this.specimenManager.getGeneralListOfSpecimens()){
            tmpRunners.add(new Specimen(new MazeRunner(getMutatedGenome(s.getRunner().getGenome()), getMapCopy(), false), null, 0));
        }

        /* TODO: fix this make so new Specimen created
        ArrayList<Specimen> waitingForPairGhost = new ArrayList<>();
        ArrayList<Specimen> waitingForPairRunner = new ArrayList<>();
        for (Specimen s : this.specimenManager.getGeneralListOfSpecimens()) {
            if (s.isRunner()) {
                waitingForPairGhost.add(s);
            } else {
                waitingForPairRunner.add(s);
            }
            if(waitingForPairGhost.size() > 0 && waitingForPairRunner.size() > 0){
                Map map = getMapCopy();
                Specimen runner = waitingForPairGhost.get(0);
                Specimen ghost = waitingForPairRunner.get(0);
                waitingForPairGhost.remove(0);
                waitingForPairRunner.remove(0);
                runner.mutateGenome(getRandomPercent());
                ghost.mutateGenome(getRandomPercent());
                tmpRunners.add(runner);
                tmpGhosts.add(ghost);
            }
        }
        */
    }

    private ArrayList<Integer> getMutatedGenome(ArrayList<Integer> genome) {
        ArrayList<Integer> mutatedGenome = new ArrayList<>();
        for(Integer i: genome){
            if(getRandomNumberBetween1And(101) < 2){
                mutatedGenome.add(getRandomNumberBetween1And(5) - 1);
            }else {
                mutatedGenome.add(i);
            }
        }
        return mutatedGenome;
    }

    private ArrayList<Integer> mutateGenome(Specimen specimen) {
        return null;
    }

    private Map getMapCopy(){
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
        for (int i = 0; i < values.length; i++) {
            sValues = sValues + values[i] + ", ";
        }
        return sValues;
    }

    private float get_average_fitness(float[] values) {
        float average = 0f;
        for (int i = 0; i < values.length; i++) {
            average = average + values[i];
        }
        return average / values.length;
    }

    public int getNumOfGenerations(){
        return this.numOfGenerations;
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public boolean goalAchieved() {
        return this.goalAchieved;
    }

    public Genome getBestRunnerGenome() {
        return this.bestRunnerGenome;
    }

    public boolean isInfinite() {
        return this.infinite;
    }
}