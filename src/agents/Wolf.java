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
    private final double rate;

    public Wolf() {
        // set a random energy and direction
        this.setEnergy(Math.random() * 2 * gain);
        this.setDirection(Math.random() * 360);
        // retrieve the wolf food gain and reproduce rate from the parameters
        Parameters params = RunEnvironment.getInstance().getParameters();
        this.gain = (Double) params.getValue(Constants.PARAM_WOLF_GAIN_FOOD);
        this.rate = (Double) params.getValue(Constants.PARAM_WOLF_REPRODUCE);
    }

    public Wolf(double energy) {
        this();
        this.setEnergy(energy);
    }

    @Override
    public void step() {
        move();
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
            final double halfEnergy = this.getEnergy() / 2;
            this.setEnergy(halfEnergy);
            context.add(new Wolf(halfEnergy));
        }
    }

}
