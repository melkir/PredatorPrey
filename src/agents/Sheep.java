package agents;

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
    private static final String PARAM_SHEEP_GAIN_FOOD = "sheep_gain_food";
    private static final String PARAM_SHEEP_REPRODUCE = "sheep_reproduce";
    private double gain, rate;

    public Sheep() {
        // set a random energy and direction
        this.setEnergy(Math.random() * 2 * gain);
        this.setDirection(Math.random() * 360);
        // retrieve the sheep food gain and reproduce rate from the parameters
        Parameters params = RunEnvironment.getInstance().getParameters();
        this.gain = (Double) params.getValue(PARAM_SHEEP_GAIN_FOOD);
        this.rate = (Double) params.getValue(PARAM_SHEEP_REPRODUCE);
    }

    public Sheep(double energy) {
        this();
        this.setEnergy(energy);
    }

    @Override
    public void step() {
        move();
        // retrieve the grid from the context and the sheep position
        Context context = ContextUtils.getContext(this);
        Grid grid = (Grid) context.getProjection(AbstractAgent.PROJECTION_GRID);
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
    }

    @Override
    public AgentType getType() {
        return AgentType.Sheep;
    }

}
