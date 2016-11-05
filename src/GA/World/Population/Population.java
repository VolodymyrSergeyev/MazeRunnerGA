package GA.World.Population;


import GA.World.Entity.MazeRunner;
import GA.World.Entity.SpookyGhost;
import GA.World.Logger.Entity.Genome;
import GA.World.Logger.Logger;
import GA.World.Map.Map;
import GA.World.Population.Entity.Specimen;

import java.util.ArrayList;
import java.util.Random;

public class Population {

    private final SpecimenManager specimenManager;
    SpookyGhost[] ghosts;

    private int populationSize = 4;
    private int genomeSize = 10;
    private float mutationRate = 0.1f;
    private float highestRunnerFitnessScore = 0f;
    private Genome bestRunnerGenome;
    private int genNum = 0;

    private MazeRunner bestRunnerInCurrentGen = null;
    private float bestRunnerFScoreInCurrentGen = 0;

    public Logger logger;
    private Map map;
    private boolean goalAchieved;
    private boolean infinite;
    private boolean init;
    private int popSize;

    public Population(Logger logger, Map map, int populationSize, int genomeSize, float mutationRate) {
        this.init = true;
        this.logger = logger;
        this.map = map;
        if(populationSize <= 0){
            this.populationSize = 500;
            this.infinite = true;
        }else {
            this.populationSize = populationSize < 10 ? 10 : populationSize;
            this.infinite = false;
        }
        this.genomeSize = genomeSize;
        this.mutationRate = mutationRate;
        this.goalAchieved = false;
        this.specimenManager = new SpecimenManager(this.populationSize);
        initializeFirstRunnerGeneration();
    }

    public void initializeFirstRunnerGeneration() {
        for (int i = 0; i < this.populationSize; i++) {
            this.specimenManager.addSpecimen(new Specimen(new MazeRunner(genomeSize, new Map(this.map), false), null, 0));
        }
    }

    //TODO: create initializeFirstGhostGeneration()

    public void calculateFitnessScoresForCurrentGen() {
        this.genNum++;
        float fScore = 0;
        Specimen s;
        for (int i = 0; i < this.populationSize; i++) {
            s = this.specimenManager.getGeneralListOfSpecimens().get(i);
            calculateSpecimenFitnessScore(s);
            if(s.isRunner()){
                fScore = s.getFScore();
                //Logging the best runner in current generation
                //Temporary storing best runner in the generations and its score
                if(fScore > this.bestRunnerFScoreInCurrentGen){
                    this.bestRunnerFScoreInCurrentGen = fScore;
                    this.bestRunnerInCurrentGen = s.getRunner();
                }
                if(i == this.populationSize - 1){
                    this.logger.addGenome(new Genome(this.bestRunnerInCurrentGen.getGenome(), this.genNum, this.bestRunnerFScoreInCurrentGen));
                }
                if (fScore > this.highestRunnerFitnessScore){
                    this.highestRunnerFitnessScore = fScore;
                    this.bestRunnerGenome = new Genome (s.getRunner().getGenome(), this.genNum, fScore);
                }
            }else {
                //TODO: Ghost
            }
        }
    }

    public void calculateSpecimenFitnessScore(Specimen specimen) {
        float fScore;
        if(specimen.isRunner()) {
            MazeRunner runner = specimen.getRunner();
            for (int i = 0; i < genomeSize; i++) {
                runner.moveByGenomeId(i);
            }
            fScore = ((float) runner.getFoodEaten() / (float) this.map.getMaxFood() * 1000) + runner.getMovesMade();
            specimen.setfScore(fScore);
            specimen.setTested();
            if (fScore - runner.getMovesMade() == 1000) {
                this.goalAchieved = true;
            }
        }else {
            //TODO: Ghost
            SpookyGhost ghost = specimen.getGhost();
        }
    }

    public ArrayList<MazeRunner> createRunnerRouletteWheel(ArrayList<Specimen> specimens) {

        ArrayList<MazeRunner> pool = new ArrayList<>();
        for (int i = 0; i < specimens.size(); i++) {
            int count = (int) (specimens.get(i).getFScore() * 10);
            for (int p = 0; p < count; p++) {
                pool.add(specimens.get(i).getRunner());
            }
        }
        return pool;
    }

    /* cross-over by half genomes */
    public void natural_selection() {
        calculateFitnessScoresForCurrentGen();
        this.specimenManager.calculateTopSpecimens();
        this.specimenManager.removeWeakSpecimens();
        performRunnerNaturalSelection(this.specimenManager.getTopBestRunnerSpecimens());
        performGhostNaturalSelection(this.specimenManager.getTopBestGhostSpecimens());
    }

    private void performGhostNaturalSelection(ArrayList<Specimen> ghostSpecimens) {

    }

    private void performRunnerNaturalSelection(ArrayList<Specimen> runnerSpecimens) {
        ArrayList<MazeRunner> wheel = createRunnerRouletteWheel(runnerSpecimens);

        for (int i = 0; i < this.populationSize - 1; i = i + 2) {
            //TODO: Mutate some.
            ArrayList<Integer> chrom_1 = new ArrayList<>();
            ArrayList<Integer> chrom_2 = new ArrayList<>();
            ArrayList<Integer> new_genome1 = new ArrayList<>();
            ArrayList<Integer> new_genome2 = new ArrayList<>();

            chrom_1.addAll(wheel.get(getRandomSpecimen(wheel.size())).getGenome().subList(0, genomeSize / 2));
            chrom_2.addAll(wheel.get(getRandomSpecimen(wheel.size())).getGenome().subList(genomeSize / 2, genomeSize));

            new_genome1.addAll(chrom_1);
            new_genome1.addAll(chrom_2);
            //new_genome1 = mutation(new_genome1);

            this.specimenManager.addSpecimen(new Specimen(new MazeRunner(new_genome1, genomeSize, new Map(this.map), false), null, 0));


            new_genome2.addAll(chrom_2);
            new_genome2.addAll(chrom_1);
            //new_genome2 = mutation(new_genome2);

            this.specimenManager.addSpecimen(new Specimen(new MazeRunner(new_genome1, genomeSize, new Map(this.map), false), null, 0));

            //System.out.println(runners[i].get_sGenome());
            //System.out.println(runners[i + 1].get_sGenome());
        }
    }

    private int getRandomSpecimen(int size) {
        Random random = new Random();
        int result = random.nextInt(size);
        if(result > 0 && result < size){
            return result;
        }
        return getRandomSpecimen(size);
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

        //ArrayList<MazeRunner> wheel = createRunnerRouletteWheel(fitness_values);

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


    public ArrayList<Integer> mutation(ArrayList<Integer> genome) {
        Random random = new Random();
        ArrayList<Integer> new_genome = genome;
        int dna = 0;
        int num_of_mutations = (int) mutationRate;
        for (int i = 0; i < num_of_mutations; i++) {
            dna = random.nextInt(genomeSize);
            new_genome.set(dna, random.nextInt(4));
        }
        return new_genome;
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

    private float get_average_fitness(float[] values){
        float average= 0f;
        for (int i = 0; i<values.length; i++){
            average = average + values[i];
        }
        return average / values.length;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public boolean goalAchieved() {
        return this.goalAchieved;
    }

    public Genome getBestRunnerGenome() {
        return bestRunnerGenome;
    }

    public boolean isInfinite() {
        return infinite;
    }
}