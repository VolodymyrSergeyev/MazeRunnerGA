package GA.State;

import GA.World.Entity.MazeRunner;
import GA.World.Entity.SpookyGhost;
import GA.World.Logger.Entity.GenomePair;
import GA.World.Logger.Logger;
import GA.World.Map.Map;
import GA.World.Population.Population;
import org.lwjgl.input.Keyboard;

class Spectating implements State {

    private final StateManager stateManager;
    private Map map;
    private Logger logger;
    private Population population;
    private GenomePair bestGenomeInCurrentGen;
    private int currentGen = 0;
    private MazeRunner runner;
    private SpookyGhost ghost;
    private boolean initialized = false;

    Spectating(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void init(Logger logger, Map map) {
        this.logger = logger;
        this.map = new Map(map);
        this.initialized = true;
        this.bestGenomeInCurrentGen = this.logger.getGenomeByGenerationNumer(this.currentGen);
        Map tmpMap = new Map (this.map.getMap());
        this.runner = new MazeRunner(this.bestGenomeInCurrentGen.getRunnerGenome(), tmpMap, true);
        this.ghost = new SpookyGhost(this.bestGenomeInCurrentGen.getGhostGenome(), tmpMap, true);
        this.map.initRendering();
    }

    @Override
    public void update() {
        this.map.draw();
        this.runner.update();
        this.ghost.update();
        this.runner.draw();
        this.ghost.draw();
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                this.stateManager.changeToInitState();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
                this.changeCurrentGeneration(true);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
                this.changeCurrentGeneration(false);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_B && Keyboard.getEventKeyState()) {
                this.changeCurrentGenerationToBestGenome();
            }

//            if (Keyboard.getEventKey() == Keyboard.KEY_D && Keyboard.getEventKeyState()) {
//                GenomePair genome = this.logger.getDebugGenomePair();
//                if (genome != null) {
//                    this.applyGenomeToRenderedRunner(genome);
//                }
//            }

            if (Keyboard.getEventKey() == Keyboard.KEY_W && Keyboard.getEventKeyState()) {
                this.runner.moveUp();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
                this.runner.moveDown();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_D && Keyboard.getEventKeyState()) {
                this.runner.moveRight();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_A && Keyboard.getEventKeyState()) {
                this.runner.moveLeft();
            }
        }
    }

    private void changeCurrentGenerationToBestGenome() {
        applyGenomeToRenderedRunner(this.logger.getBestGenomePair());
    }

    private void changeCurrentGeneration(boolean b) {

        if (b) {
            this.currentGen++;
            if (this.currentGen >= this.logger.getNumberOfGenerations()) {
                this.currentGen = 0;
            }
            this.bestGenomeInCurrentGen = logger.getGenomeByGenerationNumer(this.currentGen);
        } else {
            this.currentGen--;
            if (this.currentGen < 0) {
                this.currentGen = this.logger.getNumberOfGenerations()-1;
            }
            this.bestGenomeInCurrentGen = logger.getGenomeByGenerationNumer(this.currentGen);

        }
        applyGenomeToRenderedRunner(this.bestGenomeInCurrentGen);
    }

    private void applyGenomeToRenderedRunner(GenomePair genome) {
        this.runner.resetWithNewGenome(genome.getRunnerGenome());
        this.ghost.resetWithNewGenome(genome.getGhostGenome());
        System.out.println("Current max gen: " + this.logger.getNumberOfGenerations() + "   GenomePair Gen : " + genome.getGeneration() + "     Runner Fitness score : " + genome.getRunnerFScore() + "     Ghost Fitness score : " + genome.getGhostFScore());
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
}
