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

public class Sheep extends AbstractAgent {
    private double gain;
    private double rate;

    public Sheep() {
    	this.initParams();
    	// set a random energy and direction
        this.setEnergy(Math.random() * 2 * gain);
        this.setDirection(Math.random() * 360);
    }

    public Sheep(double energy) {
    	this.initParams();
    	// set a random energy and direction
        this.setEnergy(energy);
        this.setDirection(Math.random() * 360);
    }
    
    private void initParams() {
        // retrieve the sheep food gain and reproduce rate from the parameters
        Parameters params = RunEnvironment.getInstance().getParameters();
        this.gain = (Double) params.getValue(Constants.PARAM_SHEEP_GAIN_FOOD);
        this.rate = (Double) params.getValue(Constants.PARAM_SHEEP_REPRODUCE);
    }

    @Override
    public void step() {
        move();
        // consume energy
        this.setEnergy(this.getEnergy() - 1);
        // retrieve the grid from the context and the sheep position
        Context context = ContextUtils.getContext(this);
        Grid grid = (Grid) context.getProjection(Constants.PROJECTION_GRID);
        GridPoint sheepCoords = grid.getLocation(this);
        // search for a grass on the trajectory of the sheep
        Iterable iterable = grid.getObjectsAt(sheepCoords.getX(), sheepCoords.getY());
        Stream stream = StreamSupport.stream(iterable.spliterator(), false);
        Optional grass = stream.filter(obj -> obj instanceof Grass).findAny();
        // and eat if any grass is present
        if (grass.isPresent() && ((Grass) grass.get()).isAlive()) {
            ((Grass) grass.get()).consume();
            this.setEnergy(this.getEnergy() + gain);
        }
        // spawn a new sheep randomly with the reproduction rate
        if (100 * Math.random() < rate) {
            // the parent give half of his energy to his child
            final double halfEnergy = this.getEnergy() / 2;
            this.setEnergy(halfEnergy);
            context.add(new Sheep(halfEnergy));
        }
        // decrement the energy and die if the sheep have no more energy
        if (this.getEnergy() < 0) die();
    }

}
