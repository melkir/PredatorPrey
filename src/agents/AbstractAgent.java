package agents;

import config.Constants;
import repast.simphony.annotate.AgentAnnot;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

@AgentAnnot(displayName = "Agent")
public abstract class AbstractAgent {
    private double energy;
    private double direction;

    @ScheduledMethod(start = 1, interval = 1)
    public abstract void step();

    public void move() {
        // retrieve the context, the grid and the continuous space of the agent
        Context context = ContextUtils.getContext(this);
        Grid grid = (Grid) context.getProjection(Constants.PROJECTION_GRID);
        ContinuousSpace space = (ContinuousSpace) context.getProjection(Constants.PROJECTION_SPACE);

        // get the location of the agent on the continuous space
        NdPoint point = space.getLocation(this);
        double sgn = Math.random() - 0.5;
        double degree = Math.random() * 50;

        // randomly set the direction of the agent between the interval [-50° <= x <= 50°]
        direction = sgn > 0 ? direction + degree : direction - degree;
        space.moveByVector(this, 1, Math.toRadians(direction), 0, 0);
        // move the agent to its new position on the grid
        grid.moveTo(this, (int) point.getX(), (int) point.getY());

        // decrement the energy and die if the agent have no more energy
        if (--this.energy <= 0) die();
    }

    /** Kill the agent */
    void die() {
        Context context = ContextUtils.getContext(this);
        // remove the agent from the context if the context is not empty
        if (context.size() > 1) context.remove(this);
        // otherwise end the simulation
        else RunEnvironment.getInstance().endRun();
    }

    public double getEnergy() {
        return energy;
    }

    void setEnergy(double energy) {
        this.energy = energy;
    }

    void setDirection(double direction) {
        this.direction = direction;
    }

}
