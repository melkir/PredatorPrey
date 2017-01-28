package agents;

import config.Constants;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;
import repast.simphony.valueLayer.GridValueLayer;

public class Grass extends AbstractAgent {
    private static final int ALIVE = 1, DEAD = 0;

    private final int regrowTime;
    private int countdown; // countdown timer for grass to regrow

    private boolean alive; // boolean for grass alive / dead

    public Grass(Context context, int x, int y) {
        // retrieve the regrow time from the parameters
        Parameters params = RunEnvironment.getInstance().getParameters();
        this.regrowTime = (Integer) params.getValue(Constants.PARAM_GRASS_REGROW_TIME);
        // add the grass to the context
        context.add(this);
        // set the initial countdown time
        this.countdown = (int) (Math.random() * regrowTime);
        // randomly set the grass state
        this.alive = Math.random() <= 0.5;
        // set the grass on the grid and on the continuous space
        Grid grid = (Grid) context.getProjection(Constants.PROJECTION_GRID);
        ContinuousSpace space = (ContinuousSpace) context.getProjection(Constants.PROJECTION_SPACE);
        grid.moveTo(this, x, y);
        space.moveTo(this, x, y, 0);
        // get the grid value layer from the context and set his current state
        GridValueLayer valueLayer = (GridValueLayer) context.getValueLayer(Constants.VALUE_LAYER_NAME);
        valueLayer.set(alive ? ALIVE : DEAD, grid.getLocation(this).toIntArray(null));
    }

    @Override
    public void step() {
        // if the grass is alive, nothing happen
        if (this.isAlive()) return;

        // if the countdown is not expired we decrement it
        if (countdown > 0) {
            --countdown;
        // otherwise we reset the timer and the grass regrow
        } else {
            countdown = regrowTime;
            alive = true;
            updateValueLayer();
        }
    }

    private void updateValueLayer() {
        Grid grid = (Grid) ContextUtils.getContext(this).getProjection(Constants.PROJECTION_GRID);
        GridValueLayer valueLayer = (GridValueLayer)ContextUtils.getContext(this).getValueLayer(Constants.VALUE_LAYER_NAME);
        valueLayer.set(alive ? ALIVE : DEAD, grid.getLocation(this).toIntArray(null));
    }

    /**
     * Called when a sheep eat this grass
     */
    void consume() {
        alive = false;
        updateValueLayer();
    }

    /**
     * @return true if the grass is alive
     */
    public boolean isAlive() {
        return alive;
    }

}
