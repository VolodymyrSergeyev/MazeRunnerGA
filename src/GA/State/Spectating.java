package GA.State;

import GA.World.Entity.MazeRunner;
import GA.World.Logger.Entity.Genome;
import GA.World.Logger.Logger;
import GA.World.Map.Map;
import GA.World.Population.Population;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Spectating implements State {

    private final StateManager stateManager;
    private Map map;
    private Logger logger;
    private Population population;
    private Genome bestGenomeInCurrentGen;
    private int currentGen = 0;
    private MazeRunner runner;
    private boolean initialized = false;

    public Spectating(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void init(Logger logger, Map map) {
        this.logger = logger;
        this.map = new Map(map);
        this.initialized = true;
        this.bestGenomeInCurrentGen = this.logger.getGenomeByGenerationNumer(this.currentGen);
        this.runner = new MazeRunner(this.bestGenomeInCurrentGen.getGenome(), this.bestGenomeInCurrentGen.getGenomeSize(), new Map (this.map.getMap()), true);
        this.map.initRendering();
        System.out.println(this.logger.getNumberOfGenerations());
    }

    @Override
    public void update() {
        this.map.draw();
        this.runner.update();
        this.runner.draw();
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
        applyGenomeToRenderedRunner(this.logger.getBestGenome());
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

    private void applyGenomeToRenderedRunner(Genome genome) {
        this.runner.resetWithNewGenome(genome.getGenome());
        System.out.println("Current max gen: " + this.logger.getNumberOfGenerations() + "   Genome Gen : " + genome.getGeneration() + "     Genome Fitness score : " + genome.getfScore() + "   Genome Changed : " + genome.getGenome().toString());
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
}
