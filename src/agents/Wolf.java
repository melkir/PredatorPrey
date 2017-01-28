package agents;

import config.Constants;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Wolf extends AbstractAgent {
    private double gain;
    private double rate;

    public Wolf() {
    	this.initParams();
    	// set a random energy and direction
        this.setEnergy(Math.random() * 2 * gain);
        this.setDirection(Math.random() * 360);
    }

    public Wolf(double energy) {
    	this.initParams();
    	// set a random energy and direction
    	this.setEnergy(energy);
        this.setDirection(Math.random() * 360);
    }
    
    private void initParams() {
        // retrieve the wolf food gain and reproduce rate from the parameters
        Parameters params = RunEnvironment.getInstance().getParameters();
        this.gain = (Double) params.getValue(Constants.PARAM_WOLF_GAIN_FOOD);
        this.rate = (Double) params.getValue(Constants.PARAM_WOLF_REPRODUCE);
    }

    @Override
    public void step() {
        move();
        // consume energy
        this.setEnergy(this.getEnergy() - 1);
        // retrieve the grid from the context and the wolf position
        Context context = ContextUtils.getContext(this);
        Grid grid = (Grid) context.getProjection(Constants.PROJECTION_GRID);
        GridPoint wolfCoords = grid.getLocation(this);
        // search for a sheep on the trajectory of the wolf
        Iterable iterable = grid.getObjectsAt(wolfCoords.getX(), wolfCoords.getY());
        Stream stream = StreamSupport.stream(iterable.spliterator(), false);
        Optional sheep = stream.filter(obj -> obj instanceof Sheep).findAny();
        // and eat if any sheep is present
        if (sheep.isPresent()) {
            ((Sheep) sheep.get()).die();
            this.setEnergy(this.getEnergy() + gain);
        }
        // spawn a new wolf randomly with the reproduction rate
        if (100 * Math.random() < rate) {
            // the parent give half of his energy to his child
            this.setEnergy(this.getEnergy() / 2);
            context.add(new Wolf(this.getEnergy()));
        }
        // decrement the energy and die if the wolf have no more energy
        if (this.getEnergy() < 0) die();
    }

}
