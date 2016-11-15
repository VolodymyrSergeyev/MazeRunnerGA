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
    private Specimen bestRunnerInCurrentGen = null;

    private double bestRunnerFScoreInCurrentGen = 0;
    private double highestRunnerFitnessScore = 0;
    private double previousHighestRunnerFitnessScore = 0;
    private GenomePair bestGenomePair = null;
    private Specimen goalAchiever = null;
    private double bestGhostFScoreInCurrentGen = 0;
    private double highestGhostFitnessScore = 0;

    private ArrayList<Specimen> tmpSpecimen;

    private Random random;

    private SpecimenManager specimenManager;
    public Logger logger;
    private Map map;
    private boolean goalAchieved;
    private boolean endded;
    private boolean first = true;
    private double mutationRate;

    private int count = 0;

    public Population(Logger logger, Map map, int numOfGenerations, int populationSize, int genomeSize, double mutationRate) {
        this.numOfGenerations = numOfGenerations;
        if (populationSize <= 0) {
            this.populationSize = 500;
        } else {
            this.populationSize = populationSize < 10 ? 10 : populationSize;
        }
        this.genomeSize = genomeSize;
        this.mutationRate = mutationRate;

        this.tmpSpecimen = new ArrayList<>();

        this.random = new Random();
        this.specimenManager = new SpecimenManager(this.populationSize);
        this.logger = logger;
        this.map = map;

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
        double fScore;
        Specimen s;
        ArrayList<Double> runnerScoreList = new ArrayList<>();
        ArrayList<Double> ghostScoreList = new ArrayList<>();

        double runnerFScoreG = 0;
        double ghostFScoreG = 0;

        int size = this.specimenManager.getGeneralListOfSpecimens().size();
        for (int i = 0; i < size; i++) {
            s = this.specimenManager.getGeneralListOfSpecimens().get(i);
            calculateSpecimenFitnessScore(s);
            runnerFScore = s.getRunnerFScore();
            ghostFScore = s.getGhostFScore();
            fScore = runnerFScore + ghostFScore;

            runnerScoreList.add(runnerFScore);
            ghostScoreList.add(ghostFScore);

            if (goalAchieved) {
                this.goalAchiever = s;
                runnerFScoreG = runnerFScore;
                ghostFScoreG = ghostFScore;
                goalAchieved = false;
            }

            if ((fScore > this.bestRunnerFScoreInCurrentGen + this.bestGhostFScoreInCurrentGen) || first) {
                this.bestRunnerFScoreInCurrentGen = runnerFScore;
                this.bestGhostFScoreInCurrentGen = ghostFScore;
                this.bestRunnerInCurrentGen = s;
            }

//            if ((runnerFScore > this.bestRunnerFScoreInCurrentGen || first)) {
//                this.bestRunnerFScoreInCurrentGen = runnerFScore;
//                this.bestRunnerInCurrentGen = s;
//            }
//
//            if ((ghostFScore > this.bestGhostFScoreInCurrentGen || first)) {
//                this.bestGhostFScoreInCurrentGen = ghostFScore;
//                this.bestGhostInCurrentGen = s;
//            }

            if ((fScore > this.highestRunnerFitnessScore + this.highestGhostFitnessScore) || first) {
                this.highestRunnerFitnessScore = runnerFScore;
                this.highestGhostFitnessScore = ghostFScore;
            }

//            if ((runnerFScore > this.highestRunnerFitnessScore) || first) {
//                this.highestRunnerFitnessScore = runnerFScore;
//                this.bestRunnerSpecimen = s;
//            }

//            if ((ghostFScore > this.highestGhostFitnessScore) || first) {
//                this.highestGhostFitnessScore = ghostFScore;
//                this.bestGhostSpecimen = s;
//            }

            if (first) {
                this.first = false;
            }

            if (i == this.specimenManager.getGeneralListOfSpecimens().size() - 1) {
                if (this.previousHighestRunnerFitnessScore == this.highestRunnerFitnessScore) {
                    this.count++;
                    if (this.count > 150) {
                        this.endded = true;
                    }
                } else {
                    this.previousHighestRunnerFitnessScore = this.highestRunnerFitnessScore;
                    this.count = 0;
                }

                double avrgRunnerScore = calculateAvrgScore(runnerScoreList);
                double avrgGhostScore = calculateAvrgScore(ghostScoreList);

                if (this.goalAchiever != null) {
                    logCurrentBestSpecimen(runnerFScoreG, ghostFScoreG, avrgRunnerScore, avrgGhostScore, this.goalAchiever);
                    this.goalAchieved = true;
                } else {

                    //logCurrentBestSpecimen(this.highestRunnerFitnessScore, this.highestGhostFitnessScore, avrgRunnerScore, avrgGhostScore, );
                    this.logger.addGenome(new GenomePair(this.bestRunnerInCurrentGen.getRunner() != null ? this.bestRunnerInCurrentGen.getRunner().getGenome() : this.bestRunnerInCurrentGen.getReTestRunner().getGenome(),
                            this.bestRunnerInCurrentGen.getGhost() != null ? this.bestRunnerInCurrentGen.getGhost().getGenome() : this.bestRunnerInCurrentGen.getReTestGhost().getGenome(),
                            this.genNum, this.bestRunnerFScoreInCurrentGen, this.bestGhostFScoreInCurrentGen, avrgRunnerScore, avrgGhostScore));
//                    this.logger.addGenome(new GenomePair(this.bestGhostInCurrentGen.getRunner().getGenome(), this.bestGhostInCurrentGen.getGhost().getGenome(), this.genNum,
//                            this.bestRunnerFScoreInCurrentGen, this.bestGhostFScoreInCurrentGen, avrgRunnerScore, avrgGhostScore));
                }

            }
        }
    }

    private double calculateAvrgScore(ArrayList<Double> scoreList) {
        double result = 0;
        for (Double d : scoreList) {
            result += d;
        }
        return result / scoreList.size();
    }

    private void logCurrentBestSpecimen(double runnerFScore, double ghostFScore, double avrgRunnerFScore, double avrgGhostFScore, Specimen s) {
        this.bestGenomePair = new GenomePair(s.getRunner().getGenome(), s.getGhost().getGenome(), this.genNum, runnerFScore, ghostFScore, avrgRunnerFScore, avrgGhostFScore);
        this.logger.setBestGenomePair(this.bestGenomePair);
    }

    private void calculateSpecimenFitnessScore(Specimen specimen) {
        MazeRunner runner;
        SpookyGhost ghost;
        if (!specimen.isTested()) {
            runner = specimen.getRunner();
            ghost = specimen.getGhost();
            calculateFScore(specimen, runner, ghost);
        } else if (specimen.isSetForReTest()) {
            if (specimen.getReTestRunner() == null) {
                runner = specimen.getRunner();
                ghost = specimen.getReTestGhost();
            } else {
                runner = specimen.getReTestRunner();
                ghost = specimen.getGhost();
            }
            calculateFScore(specimen, runner, ghost);

            ArrayList<Double> fScores = new ArrayList<>();

            if (specimen.getReTestRunner() == null) {
                fScores.add(specimen.getRunnerFScore());
                fScores.add(specimen.getReTestRunnerFScore());
                specimen.setRunnerFScore(calculateAvrgScore(fScores));
                specimen.setGhostFScore(specimen.getReTestGhostFScore());
            } else {
                fScores.add(specimen.getGhostFScore());
                fScores.add(specimen.getReTestGhostFScore());
                specimen.setGhostFScore(calculateAvrgScore(fScores));
                specimen.setRunnerFScore(specimen.getReTestRunnerFScore());
            }
            specimen.setForReTest(false);

        }
    }

    private void calculateFScore(Specimen specimen, MazeRunner runner, SpookyGhost ghost) {
        double runnerFScore = 0;
        double ghostFScore = 0;
        boolean firstDeath = true;
        for (int i = 0; i < this.genomeSize; i++) {
            if (firstDeath) {
                double tmpDcrScore = ((double) (this.genomeSize - i) / (double) this.genomeSize) * 2.0;
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
//                    if(absNewPos < 10){
//                        runnerFScore -= Math.pow(tmpDcrScore, 100);
//                    }
                if (absNewPos >= absOldPos) {
                    ghostFScore -= Math.pow(tmpDcrScore, 6);
                }
                if (!runner.isAlive()) {
                    runnerFScore -= Math.pow(2.0 + tmpDcrScore, 6);
                    //ghostFScore += Math.pow(tmpDcrScore, 10);
                    //ghostFScore += Math.pow(tmpIncScore, 6);
                    //runnerFScore -= Math.pow(tmpDcrScore, 6);
                    //runnerFScore = Double.NEGATIVE_INFINITY;
                    firstDeath = false;
                }
            }
        }
        if (specimen.isSetForReTest()) {
            specimen.setReTestRunnerFScore(runnerFScore);
            specimen.setReTestGhostFScore(ghostFScore);
        } else {
            specimen.setRunnerFScore(runnerFScore);
            specimen.setGhostFScore(ghostFScore);
        }
        specimen.setRunnerIsAWinner(firstDeath);
        specimen.setTested();
        if (runner.getFoodEaten() == map.getMaxFood()) {
            this.goalAchieved = true;
        }
    }

    private ArrayList<Integer> createGenomePool(ArrayList<Specimen> specimens, boolean runners) {

        ArrayList<Integer> pool = new ArrayList<>();
        for (int i = 0; i < specimens.size(); i++) {
            double max = Math.abs(runners ? this.specimenManager.getTopBestRunnerSpecimens().get(this.specimenManager.getNumberOfBestSpecimens() - 1).getRunnerFScore() :
                    this.specimenManager.getTopBestGhostSpecimens().get(this.specimenManager.getNumberOfBestSpecimens() - 1).getGhostFScore());
            double dif = max + (runners ? specimens.get(i).getRunnerFScore() : specimens.get(i).getGhostFScore());
            int count = (int) (dif) + 10;
            for (int p = 0; p < count; p++) {
                pool.add(i);
            }
        }
        return pool;
    }

    void naturalSelection() {
        this.specimenManager.clearTops();
        calculateFitnessScoresForCurrentGen();
        this.specimenManager.calculateTopSpecimens();
        ArrayList<Integer> runnerGenomePool = createGenomePool(this.specimenManager.getTopBestRunnerSpecimens(), true);
        ArrayList<Integer> ghostGenomePool = createGenomePool(this.specimenManager.getTopBestGhostSpecimens(), false);
        changePartnersForTopSpecimen(runnerGenomePool, ghostGenomePool);
        this.specimenManager.removeWeakSpecimens();
        mutateTopBestSpecimens(runnerGenomePool, ghostGenomePool);
        for (int i = 0; i < this.populationSize - 1; i = i + 2) {
            Map map1 = getMapCopy();
            Map map2 = getMapCopy();
            performSpecimenCrossOver(runnerGenomePool, ghostGenomePool, map1, map2);
        }
        this.specimenManager.getGeneralListOfSpecimens().addAll(this.tmpSpecimen);
        this.tmpSpecimen = new ArrayList<>();
    }

    private void changePartnersForTopSpecimen(ArrayList<Integer> runnerGenomePool, ArrayList<Integer> ghostGenomePool) {
        for (Specimen s : this.specimenManager.getTopBestRunnerSpecimens()) {
            Map map = getMapCopy();
            s.setForReTest(true);
            s.getRunner().resetWithNewMap(map);
            s.setReTestGhost(getCrossOveredGhost(ghostGenomePool, map));
        }
        for (Specimen s : this.specimenManager.getTopBestGhostSpecimens()) {
            Map map = getMapCopy();
            s.setForReTest(true);
            s.getGhost().resetWithNewMap(map);
            s.setReTestRunner(getCrossOveredRunner(runnerGenomePool, map));
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

        SpookyGhost ghostParent1 = this.specimenManager.getTopBestGhostSpecimens()
                .get(ghostPool.get(getRandomNumberBetween1And(ghostPool.size()))).getGhost();
        SpookyGhost ghostParent2 = this.specimenManager.getTopBestGhostSpecimens()
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

    private SpookyGhost getCrossOveredGhost(ArrayList<Integer> genomePool, Map map) {
        ArrayList<Integer> ghostChromosome1 = new ArrayList<>();
        ArrayList<Integer> ghostChromosome2 = new ArrayList<>();
        ArrayList<Integer> ghostNewGenome1 = new ArrayList<>();

        SpookyGhost ghostParent1 = this.specimenManager.getTopBestGhostSpecimens()
                .get(genomePool.get(getRandomNumberBetween1And(genomePool.size()))).getGhost();
        SpookyGhost ghostParent2 = this.specimenManager.getTopBestGhostSpecimens()
                .get(genomePool.get(getRandomNumberBetween1And(genomePool.size()))).getGhost();

        int splitPoint = (int) (this.genomeSize * getRandomPercent(0.9));

        ghostChromosome1.addAll(ghostParent1.getGenome().subList(0, splitPoint));
        ghostChromosome2.addAll(ghostParent2.getGenome().subList(splitPoint, this.genomeSize));

        if (getRandomNumberBetween1And(101) < 50) {
            ghostNewGenome1.addAll(ghostChromosome1);
            ghostNewGenome1.addAll(ghostChromosome2);
        } else {
            ghostNewGenome1.addAll(ghostChromosome2);
            ghostNewGenome1.addAll(ghostChromosome1);
        }

        if (getRandomNumberBetween1And(101) < (int) (100 * this.mutationRate)) {
            ghostNewGenome1 = getMutatedGenome(ghostNewGenome1);
        }

        return new SpookyGhost(ghostNewGenome1, map, false);
    }

    private MazeRunner getCrossOveredRunner(ArrayList<Integer> genomePool, Map map) {
        ArrayList<Integer> chromosome1 = new ArrayList<>();
        ArrayList<Integer> chromosome2 = new ArrayList<>();
        ArrayList<Integer> newGenome1 = new ArrayList<>();

        MazeRunner parent1 = this.specimenManager.getTopBestRunnerSpecimens()
                .get(genomePool.get(getRandomNumberBetween1And(genomePool.size()))).getRunner();
        MazeRunner parent2 = this.specimenManager.getTopBestRunnerSpecimens()
                .get(genomePool.get(getRandomNumberBetween1And(genomePool.size()))).getRunner();

        int splitPoint = (int) (this.genomeSize * getRandomPercent(0.9));

        chromosome1.addAll(parent1.getGenome().subList(0, splitPoint));
        chromosome2.addAll(parent2.getGenome().subList(splitPoint, this.genomeSize));

        if (getRandomNumberBetween1And(101) < 50) {
            newGenome1.addAll(chromosome1);
            newGenome1.addAll(chromosome2);
        } else {
            newGenome1.addAll(chromosome2);
            newGenome1.addAll(chromosome1);
        }
        if (getRandomNumberBetween1And(101) < (int) (100 * this.mutationRate)) {
            newGenome1 = getMutatedGenome(newGenome1);
        }

        return new MazeRunner(newGenome1, map, false);
    }

    private void mutateTopBestSpecimens(ArrayList<Integer> runnerGenomePool, ArrayList<Integer> ghostGenomePool) {
        for (Specimen s : this.specimenManager.getGeneralListOfSpecimens()) {
            Map map = getMapCopy();
            if (s.getGhost() == null) {
                tmpSpecimen.add(new Specimen(new MazeRunner(getMutatedGenome(s.getRunner().getGenome()), map, false),
                        getCrossOveredGhost(ghostGenomePool, map), 0, 0));
            } else {
                tmpSpecimen.add(new Specimen(getCrossOveredRunner(runnerGenomePool, map),
                        new SpookyGhost(getMutatedGenome(s.getGhost().getGenome()), map, false), 0, 0));
            }
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

    int getNumOfGenerations() {
        return this.numOfGenerations;
    }

    boolean goalAchieved() {
        return this.goalAchieved;
    }

    GenomePair getBestGenomePair() {
        return this.bestGenomePair;
    }

    boolean isEndded() {
        return this.endded;
    }
}